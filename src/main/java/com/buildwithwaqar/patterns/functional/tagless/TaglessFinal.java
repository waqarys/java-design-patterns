package com.buildwithwaqar.patterns.functional.tagless;

import com.buildwithwaqar.patterns.functional.typeclass.Kind;
import com.buildwithwaqar.patterns.functional.typeclass.Monad;
import com.buildwithwaqar.patterns.functional.typeclass.OptionKind;
import dev.zazr.control.Option;

/**
 * Tagless Final: programs are written against an algebra (interface of capabilities),
 * not a concrete effect type. Interpreters provide the algebra for Option, IO, etc.
 */
public final class TaglessFinal {
    private TaglessFinal() {}

    /** Unit type — Zazr Option rejects null, so Void is awkward; use a singleton. */
    public enum Unit { INSTANCE }

    public interface Console<F> {
        Kind<F, String> readLine();
        Kind<F, Unit> printLine(String s);
    }

    public static <F> Kind<F, String> greet(Monad<F> M, Console<F> console) {
        return M.flatMap(console.printLine("What is your name?"), ignored ->
                M.flatMap(console.readLine(), name ->
                        M.flatMap(console.printLine("Hello, " + name), ignored2 ->
                                M.pure(name))));
    }

    /** Test interpreter using Option as a trivial effect (pre-canned input). */
    public static final class OptionConsole implements Console<OptionKind.Witness> {
        private final String input;
        public OptionConsole(String input) { this.input = input; }

        public Kind<OptionKind.Witness, String> readLine() {
            return OptionKind.lift(Option.some(input));
        }

        public Kind<OptionKind.Witness, Unit> printLine(String s) {
            return OptionKind.lift(Option.some(Unit.INSTANCE));
        }
    }
}
