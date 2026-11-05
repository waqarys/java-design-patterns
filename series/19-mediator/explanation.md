# Mediator

**Season:** 3 · Behavioral · **Category:** GoF · **Level:** Intermediate

## Intent
Centralize complex colleague communication.

## Problem
Without this pattern, code tends toward: New things up with conditionals and duplicated construction.

## Real-world Analogy
Air traffic control tower.

## When to Use
- See Intent.
- The design force named in Intent shows up more than once.

## When Not to Use
- Overkill for one-off behavior.
- A simpler language feature (record, sealed switch, function value) already solves it.

## Naive Approach
```text
New things up with conditionals and duplicated construction.
```

## Pattern Structure
```mermaid
flowchart LR
  Client --> Pattern
  Pattern --> Collaborators
```
Client -> Pattern -> Collaborators

## Classic Implementation
Primary code lives under `src/main/java/com/buildwithwaqar/patterns/gof/behavioral/mediator/`.

```java
package com.buildwithwaqar.patterns.gof.behavioral.mediator;

import java.util.ArrayList;
import java.util.List;

interface ChatMediator {
    void register(User user);
    void send(String from, String message);
}

public final class ChatRoom implements ChatMediator {
    private final List<User> users = new ArrayList<>();
    private final List<String> log = new ArrayList<>();

    public void register(User user) { users.add(user); }

    public void send(String from, String message) {
        var line = from + ": " + message;
        log.add(line);
        for (var u : users) if (!u.name().equals(from)) u.receive(line);
    }

    List<String> log() { return List.copyOf(log); }
}

final class User {
    private final String name;
    private final ChatMediator mediator;
    private final List<String> inbox = new ArrayList<>();

    User(String name, ChatMediator mediator) {
        this.name = name;
        this.mediator = mediator;
        mediator.register(this);
    }

    String name() { return name; }
    void send(String msg) { mediator.send(name, msg); }
    void receive(String msg) { inbox.add(msg); }
    List<String> inbox() { return List.copyOf(inbox); }
}
```

## Modern Java 25 / Functional
Java 25 pattern matching often replaces Visitor/Interpreter boilerplate.

## Worked Example
```text
See the JUnit test beside the implementation.
Run: mvn -Dtest=*Test test
Focus package matching: gof/behavioral/mediator/
```

## Pitfalls
- Prefer sealed + switch over classic Visitor in Java 25.
- Strategy as a function value.
- Observer: unsubscribe to avoid leaks.

## Interview Q&A
**Q: Key interview angle for Mediator?**
A: Name the force (undo, open/closed, open for extension) and a modern alternative.

**Q: How would you test this?**
A: Prefer injecting interfaces or interpreters; keep demos deterministic (fixed clocks, canned inputs).

## Key Takeaways
- Centralize complex colleague communication.
- Prefer Java 25 sealed types / records / virtual threads where they simplify the classic shape.
- Ship working tests with every episode.

## Video Script
**[0:00] Hook** — Open with the analogy: Air traffic control tower.
**[0:40] Intent** — State the design force in one sentence: Centralize complex colleague communication.
**[1:30] Naive pain** — Show the messy approach: New things up with conditionals and duplicated construction.
**[3:00] Pattern** — Walk the code under `gof/behavioral/mediator/`; draw the mermaid structure on-screen.
**[6:00] Modern take** — Java 25 pattern matching often replaces Visitor/Interpreter boilerplate.
**[8:00] Pitfalls & interview** — Cover: Key interview angle for Mediator?
**[9:30] Outro** — Tease the next episode; point viewers to the GitHub repo and slides PDF.
