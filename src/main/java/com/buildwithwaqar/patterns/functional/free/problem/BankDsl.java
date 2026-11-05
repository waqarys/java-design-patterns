package com.buildwithwaqar.patterns.functional.free.problem;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/** Free-style bank DSL: Balance / Withdraw / Deposit / Transfer as an AST of instructions. */
public final class BankDsl {
    private BankDsl() {}

    public sealed interface Instr permits Balance, Withdraw, Deposit, Transfer {}
    public record Balance(String account) implements Instr {}
    public record Withdraw(String account, int cents) implements Instr {}
    public record Deposit(String account, int cents) implements Instr {}
    public record Transfer(String from, String to, int cents) implements Instr {}

    public abstract static class Free<A> {
        private Free() {}
        public abstract <B> Free<B> flatMap(Function<? super A, Free<B>> f);
        public final <B> Free<B> map(Function<? super A, ? extends B> f) {
            return flatMap(a -> pure(f.apply(a)));
        }
        public static <A> Free<A> pure(A a) { return new Pure<>(a); }
        public static Free<Integer> balance(String acct) { return new Suspend<>(new Balance(acct)); }
        public static Free<Void> withdraw(String acct, int cents) { return new Suspend<>(new Withdraw(acct, cents)); }
        public static Free<Void> deposit(String acct, int cents) { return new Suspend<>(new Deposit(acct, cents)); }
        public static Free<Void> transfer(String from, String to, int cents) {
            return new Suspend<>(new Transfer(from, to, cents));
        }
    }

    private static final class Pure<A> extends Free<A> {
        final A value;
        Pure(A value) { this.value = value; }
        public <B> Free<B> flatMap(Function<? super A, Free<B>> f) { return f.apply(value); }
    }
    private static final class Suspend<A> extends Free<A> {
        final Instr instr;
        Suspend(Instr instr) { this.instr = instr; }
        public <B> Free<B> flatMap(Function<? super A, Free<B>> f) { return new Bind<>(this, f); }
    }
    private static final class Bind<A, B> extends Free<B> {
        final Free<A> prev;
        final Function<? super A, Free<B>> f;
        Bind(Free<A> prev, Function<? super A, Free<B>> f) { this.prev = prev; this.f = f; }
        public <C> Free<C> flatMap(Function<? super B, Free<C>> g) {
            return new Bind<>(prev, a -> f.apply(a).flatMap(g));
        }
    }

    /** Optimize Transfer into Withdraw+Deposit for the Map interpreter. */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public static <A> Free<A> optimize(Free<A> program) {
        // rewrite Suspend(Transfer) into withdraw.flatMap(_ -> deposit)
        Free cur = program;
        if (cur instanceof Suspend<?> s && s.instr instanceof Transfer t) {
            return (Free<A>) Free.withdraw(t.from(), t.cents())
                    .flatMap(x -> Free.deposit(t.to(), t.cents()));
        }
        if (cur instanceof Bind<?, ?> b) {
            Free optPrev = optimize(b.prev);
            return new Bind<>(optPrev, a -> { @SuppressWarnings("unchecked") Function f = b.f; return optimize((Free) f.apply(a)); });
        }
        return program;
    }

    public static <A> A interpretMap(Free<A> program, Map<String, Integer> ledger) {
        return interpretMap(program, ledger, null);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    public static <A> A interpretMap(Free<A> program, Map<String, Integer> ledger, List<String> log) {
        Free cur = program;
        while (true) {
            if (cur instanceof Pure<?> p) return (A) p.value;
            if (cur instanceof Suspend<?> s) {
                Object result = runInstr(s.instr, ledger, log);
                cur = Free.pure(result);
                continue;
            }
            if (cur instanceof Bind<?, ?> b) {
                Object inner = interpretMap(b.prev, ledger, log);
                @SuppressWarnings("unchecked") Function f = b.f;
                cur = (Free) f.apply(inner);
                continue;
            }
            throw new IllegalStateException(cur.getClass().getName());
        }
    }

    private static Object runInstr(Instr instr, Map<String, Integer> ledger, List<String> log) {
        if (log != null) log.add(instr.toString());
        return switch (instr) {
            case Balance(String a) -> ledger.getOrDefault(a, 0);
            case Withdraw(String a, int c) -> {
                int bal = ledger.getOrDefault(a, 0);
                if (bal < c) throw new IllegalStateException("insufficient:" + a);
                ledger.put(a, bal - c);
                yield null;
            }
            case Deposit(String a, int c) -> {
                ledger.put(a, ledger.getOrDefault(a, 0) + c);
                yield null;
            }
            case Transfer(String from, String to, int c) -> {
                runInstr(new Withdraw(from, c), ledger, log);
                runInstr(new Deposit(to, c), ledger, log);
                yield null;
            }
        };
    }

    public static Free<Integer> paydayBonus(String from, String to, int cents) {
        return Free.transfer(from, to, cents).flatMap(x -> Free.balance(to));
    }
}
