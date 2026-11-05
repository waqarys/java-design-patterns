package com.buildwithwaqar.patterns.gof.creational.prototype;

import java.util.List;

public final class Demo {
    public static void main(String[] args) {
        System.out.println("=== Prototype walkthrough ===");
        var proto = new DocumentTemplate("Weekly Sync", List.of("Agenda", "Notes"));
        var teamA = proto.clone();
        teamA.setTitle("Weekly Sync — Team A");
        teamA.sections().add("Action Items");
        System.out.println("protoTitle=" + proto.title() + " sections=" + proto.sections());
        System.out.println("cloneTitle=" + teamA.title() + " sections=" + teamA.sections());
        var deck = new SlideDeck("Kickoff", List.of("Why", "How"));
        var eu = deck.customized("Kickoff — EU");
        System.out.println("deck=" + deck.title() + " eu=" + eu.title() + " sharedSlides=" + (deck.slides() == eu.slides()));
    }
}
