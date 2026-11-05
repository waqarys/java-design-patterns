package com.buildwithwaqar.patterns.gof.creational.prototype;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class PrototypeTest {
    @Test void cloneIsDeepAndIndependent() {
        var proto = new DocumentTemplate("Weekly", List.of("Agenda", "Notes"));
        var copy = proto.clone();
        copy.setTitle("Weekly — Team A");
        copy.sections().add("Action Items");
        assertEquals("Weekly", proto.title());
        assertEquals(2, proto.sections().size());
        assertEquals("Weekly — Team A", copy.title());
        assertEquals(3, copy.sections().size());
    }

    @Test void recordCopyPreservesImmutability() {
        var deck = new SlideDeck("Kickoff", List.of("Why", "How"));
        var copy = deck.customized("Kickoff — EU");
        assertEquals("Kickoff", deck.title());
        assertEquals("Kickoff — EU", copy.title());
        assertSame(deck.slides(), copy.slides()); // both immutable shared list is fine
    }
}
