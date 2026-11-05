package com.buildwithwaqar.patterns.functional.kleisli;

import com.buildwithwaqar.patterns.functional.typeclass.OptionKind;
import dev.zazr.control.Option;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class KleisliTest {
    @Test void composes() {
        Kleisli<OptionKind.Witness, String, Integer> parse = Kleisli.of(s -> {
            try { return OptionKind.lift(Option.some(Integer.parseInt(s))); }
            catch (Exception e) { return OptionKind.lift(Option.none()); }
        });
        Kleisli<OptionKind.Witness, Integer, Integer> twice = Kleisli.of(n -> OptionKind.lift(Option.some(n * 2)));
        var pipeline = parse.andThen(OptionKind.MONAD, twice);
        assertEquals(Option.some(84), OptionKind.lower(pipeline.apply("42")));
        assertTrue(OptionKind.lower(pipeline.apply("x")).isEmpty());
    }
}
