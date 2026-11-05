package com.buildwithwaqar.patterns.functional.tagless.problem;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/** Naive: registration hard-wires Map DB + System.out email — untestable without mocks frameworks. */
public final class NaiveRegistration {
    private final Map<String, Algebras.User> db = new LinkedHashMap<>();
    private final List<String> outbox = new ArrayList<>();

    public Algebras.User register(String id, String email) {
        if (db.containsKey(email)) {
            System.out.println("dup " + email); // side effect baked in
            return db.get(email);
        }
        var u = new Algebras.User(id, email);
        db.put(email, u);
        outbox.add("welcome->" + email); // cannot swap for test assertion easily in unit style
        System.out.println("sent welcome to " + email);
        return u;
    }

    public List<String> outbox() { return List.copyOf(outbox); }
}
