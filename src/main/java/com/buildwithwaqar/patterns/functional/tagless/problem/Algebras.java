package com.buildwithwaqar.patterns.functional.tagless.problem;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

/**
 * Tagless Final algebras for user registration.
 * Programs are written once against UserRepo/EmailSender/Logger; interpreters choose the effect.
 */
public final class Algebras {
    private Algebras() {}

    public record User(String id, String email) {}

    /** Minimal "effect" wrapper — either Identity (prod sync) or accumulating TestState. */
    public interface Kind<F, A> {
        A unsafeRun();
    }

    public interface Monad<F> {
        <A> Kind<F, A> pure(A a);
        <A, B> Kind<F, B> flatMap(Kind<F, A> fa, Function<A, Kind<F, B>> f);
        default <A, B> Kind<F, B> map(Kind<F, A> fa, Function<A, B> f) {
            return flatMap(fa, a -> pure(f.apply(a)));
        }
    }

    public interface UserRepo<F> {
        Kind<F, Optional<User>> findByEmail(String email);
        Kind<F, User> save(User user);
    }

    public interface EmailSender<F> {
        Kind<F, Void> send(String to, String subject, String body);
    }

    public interface Logger<F> {
        Kind<F, Void> info(String msg);
    }

    /** Shared registration program — no concrete effect type. */
    public static <F> Kind<F, User> register(
            Monad<F> M, UserRepo<F> users, EmailSender<F> email, Logger<F> log,
            String id, String address) {
        return M.flatMap(log.info("register " + address), ignored ->
                M.flatMap(users.findByEmail(address), existing -> {
                    if (existing.isPresent()) {
                        return M.flatMap(log.info("duplicate " + address), x ->
                                M.pure(existing.get())); // return existing — caller may treat as failure
                    }
                    var user = new User(id, address);
                    return M.flatMap(users.save(user), saved ->
                            M.flatMap(email.send(address, "Welcome", "Hi " + id), sent ->
                                    M.flatMap(log.info("registered " + id), y -> M.pure(saved))));
                }));
    }

    // ---- Identity / "IO-style" interpreter (eager, side-effecting) ----
    public enum IoWitness { W }
    public record Io<A>(A value) implements Kind<IoWitness, A> {
        public A unsafeRun() { return value; }
    }
    public static final Monad<IoWitness> IO = new Monad<>() {
        public <A> Kind<IoWitness, A> pure(A a) { return new Io<>(a); }
        public <A, B> Kind<IoWitness, B> flatMap(Kind<IoWitness, A> fa, Function<A, Kind<IoWitness, B>> f) {
            return f.apply(fa.unsafeRun());
        }
    };

    public static final class IoUserRepo implements UserRepo<IoWitness> {
        private final Map<String, User> db = new LinkedHashMap<>();
        public Kind<IoWitness, Optional<User>> findByEmail(String email) {
            return new Io<>(Optional.ofNullable(db.get(email)));
        }
        public Kind<IoWitness, User> save(User user) {
            db.put(user.email(), user);
            return new Io<>(user);
        }
        public Map<String, User> db() { return Map.copyOf(db); }
    }
    public static final class IoEmail implements EmailSender<IoWitness> {
        private final List<String> sent = new ArrayList<>();
        public Kind<IoWitness, Void> send(String to, String subject, String body) {
            sent.add(to + "|" + subject + "|" + body);
            return new Io<>(null);
        }
        public List<String> sent() { return List.copyOf(sent); }
    }
    public static final class IoLogger implements Logger<IoWitness> {
        private final List<String> lines = new ArrayList<>();
        public Kind<IoWitness, Void> info(String msg) { lines.add(msg); return new Io<>(null); }
        public List<String> lines() { return List.copyOf(lines); }
    }

    // ---- Pure test interpreter (Writer-like state) ----
    public enum TestWitness { W }
    public record TestState(Map<String, User> users, List<String> emails, List<String> logs) {
        public static TestState empty() {
            return new TestState(new LinkedHashMap<>(), new ArrayList<>(), new ArrayList<>());
        }
    }
    public record Test<A>(Function<TestState, A> run) implements Kind<TestWitness, A> {
        public A unsafeRun() { return run.apply(TestState.empty()); }
        public A runWith(TestState s) { return run.apply(s); }
    }
    public static final Monad<TestWitness> TEST = new Monad<>() {
        public <A> Kind<TestWitness, A> pure(A a) { return new Test<>(s -> a); }
        public <A, B> Kind<TestWitness, B> flatMap(Kind<TestWitness, A> fa, Function<A, Kind<TestWitness, B>> f) {
            return new Test<>(s -> {
                A a = ((Test<A>) fa).runWith(s);
                return ((Test<B>) f.apply(a)).runWith(s);
            });
        }
    };
    public static final class TestUserRepo implements UserRepo<TestWitness> {
        public Kind<TestWitness, Optional<User>> findByEmail(String email) {
            return new Test<>(s -> Optional.ofNullable(s.users().get(email)));
        }
        public Kind<TestWitness, User> save(User user) {
            return new Test<>(s -> { s.users().put(user.email(), user); return user; });
        }
    }
    public static final class TestEmail implements EmailSender<TestWitness> {
        public Kind<TestWitness, Void> send(String to, String subject, String body) {
            return new Test<>(s -> { s.emails().add(to + "|" + subject); return null; });
        }
    }
    public static final class TestLogger implements Logger<TestWitness> {
        public Kind<TestWitness, Void> info(String msg) {
            return new Test<>(s -> { s.logs().add(msg); return null; });
        }
    }
}
