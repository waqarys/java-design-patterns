package com.buildwithwaqar.patterns.functional.io.problem;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * Effect program: read a file → call a service → write a result.
 * Same AST runs under ProdInterpreter (in-memory FS+HTTP) and TestInterpreter (scripted).
 */
public final class EffectProgram {
    private EffectProgram() {}

    public sealed interface Eff permits ReadFile, HttpGet, WriteFile, Pure {}
    public record ReadFile(String path) implements Eff {}
    public record HttpGet(String url, String body) implements Eff {} // body = request payload from file
    public record WriteFile(String path, String content) implements Eff {}
    public record Pure(String value) implements Eff {}

    public abstract static class Prog<A> {
        private Prog() {}
        public abstract <B> Prog<B> flatMap(Function<? super A, Prog<B>> f);
        public final <B> Prog<B> map(Function<? super A, ? extends B> f) {
            return flatMap(a -> pure(f.apply(a)));
        }
        public static <A> Prog<A> pure(A a) { return new Return<>(a); }
        public static Prog<String> readFile(String path) { return new Suspend<>(new ReadFile(path)); }
        public static Prog<String> httpGet(String url, String body) { return new Suspend<>(new HttpGet(url, body)); }
        public static Prog<Void> writeFile(String path, String content) { return new Suspend<>(new WriteFile(path, content)); }
    }

    private static final class Return<A> extends Prog<A> {
        final A value; Return(A value) { this.value = value; }
        public <B> Prog<B> flatMap(Function<? super A, Prog<B>> f) { return f.apply(value); }
    }
    private static final class Suspend<A> extends Prog<A> {
        final Eff eff; Suspend(Eff eff) { this.eff = eff; }
        public <B> Prog<B> flatMap(Function<? super A, Prog<B>> f) { return new Bind<>(this, f); }
    }
    private static final class Bind<A, B> extends Prog<B> {
        final Prog<A> prev; final Function<? super A, Prog<B>> f;
        Bind(Prog<A> prev, Function<? super A, Prog<B>> f) { this.prev = prev; this.f = f; }
        public <C> Prog<C> flatMap(Function<? super B, Prog<C>> g) {
            return new Bind<>(prev, a -> f.apply(a).flatMap(g));
        }
    }

    public static Prog<String> reportPipeline(String inPath, String url, String outPath) {
        return Prog.readFile(inPath).flatMap(content ->
                Prog.httpGet(url, content).flatMap(resp ->
                        Prog.writeFile(outPath, resp).map(v -> resp)));
    }

    public interface Interpreter {
        <A> A run(Prog<A> program);
    }

    public static final class ProdInterpreter implements Interpreter {
        private final Map<String, String> fs;
        private final Function<String, String> http;
        private final List<String> log = new ArrayList<>();
        public ProdInterpreter(Map<String, String> fs, Function<String, String> http) {
            this.fs = fs; this.http = http;
        }
        public List<String> log() { return List.copyOf(log); }
        public Map<String, String> fs() { return Map.copyOf(fs); }
        @SuppressWarnings({"rawtypes", "unchecked"})
        public <A> A run(Prog<A> program) {
            Prog cur = program;
            while (true) {
                if (cur instanceof Return<?> r) return (A) r.value;
                if (cur instanceof Suspend<?> s) {
                    Object v = exec(s.eff);
                    cur = Prog.pure(v);
                    continue;
                }
                if (cur instanceof Bind<?, ?> b) {
                    Object inner = run(b.prev);
                    @SuppressWarnings("unchecked") java.util.function.Function f = b.f;
                    cur = (Prog) f.apply(inner);
                    continue;
                }
                throw new IllegalStateException();
            }
        }
        private Object exec(Eff eff) {
            return switch (eff) {
                case ReadFile(String path) -> {
                    log.add("read:" + path);
                    String v = fs.get(path);
                    if (v == null) throw new IllegalStateException("missing:" + path);
                    yield v;
                }
                case HttpGet(String url, String body) -> {
                    log.add("http:" + url);
                    yield http.apply(body);
                }
                case WriteFile(String path, String content) -> {
                    log.add("write:" + path);
                    fs.put(path, content);
                    yield null;
                }
                case Pure(String value) -> value;
            };
        }
    }

    /** Test interpreter with scripted responses / failures. */
    public static final class TestInterpreter implements Interpreter {
        private final Map<String, String> reads;
        private final String httpResponse;
        private final boolean failHttp;
        private final Map<String, String> writes = new LinkedHashMap<>();
        private final List<String> log = new ArrayList<>();
        public TestInterpreter(Map<String, String> reads, String httpResponse, boolean failHttp) {
            this.reads = reads; this.httpResponse = httpResponse; this.failHttp = failHttp;
        }
        public Map<String, String> writes() { return Map.copyOf(writes); }
        public List<String> log() { return List.copyOf(log); }
        @SuppressWarnings({"rawtypes", "unchecked"})
        public <A> A run(Prog<A> program) {
            Prog cur = program;
            while (true) {
                if (cur instanceof Return<?> r) return (A) r.value;
                if (cur instanceof Suspend<?> s) {
                    Object v = exec(s.eff);
                    cur = Prog.pure(v);
                    continue;
                }
                if (cur instanceof Bind<?, ?> b) {
                    Object inner = run(b.prev);
                    @SuppressWarnings("unchecked") java.util.function.Function f = b.f;
                    cur = (Prog) f.apply(inner);
                    continue;
                }
                throw new IllegalStateException();
            }
        }
        private Object exec(Eff eff) {
            return switch (eff) {
                case ReadFile(String path) -> {
                    log.add("read:" + path);
                    String v = reads.get(path);
                    if (v == null) throw new IllegalStateException("missing:" + path);
                    yield v;
                }
                case HttpGet(String url, String body) -> {
                    log.add("http:" + url);
                    if (failHttp) throw new IllegalStateException("http-down");
                    yield httpResponse;
                }
                case WriteFile(String path, String content) -> {
                    log.add("write:" + path);
                    writes.put(path, content);
                    yield null;
                }
                case Pure(String value) -> value;
            };
        }
    }
}
