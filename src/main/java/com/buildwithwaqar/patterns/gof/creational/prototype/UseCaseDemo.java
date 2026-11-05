package com.buildwithwaqar.patterns.gof.creational.prototype;

import com.buildwithwaqar.patterns.gof.creational.prototype.DocumentTemplate;
import java.util.List;

/** Problem: clone a weekly sync template per team without rebuilding sections. */
public final class UseCaseDemo {
    public static void main(String[] args) {
        System.out.println("=== Prototype problem: clone meeting template ===");
        var proto = new DocumentTemplate("Weekly Sync", List.of("Agenda", "Notes"));
        var teamA = proto.clone();
        teamA.setTitle("Weekly Sync — Platform");
        teamA.sections().add("Action Items");
        System.out.println("proto=" + proto.title() + proto.sections());
        System.out.println("clone=" + teamA.title() + teamA.sections());
    }
}
