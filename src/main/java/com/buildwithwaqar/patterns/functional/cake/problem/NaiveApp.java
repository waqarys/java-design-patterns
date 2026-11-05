package com.buildwithwaqar.patterns.functional.cake.problem;

import java.util.LinkedHashMap;
import java.util.Map;

/** Naive: controller news service news repo — cannot swap fakes without rewriting. */
public final class NaiveApp {
    private final Map<String, String> db = new LinkedHashMap<>();
    public String postRegister(String id, String email) {
        if (db.containsKey(id)) return "409:exists";
        db.put(id, email);
        System.out.println("email " + email); // hard-wired
        return "201:" + id;
    }
}
