# Observer

**Season:** 3 · Behavioral · **Category:** GoF · **Level:** Intermediate

## Intent
Notify dependents of state changes.

## Problem
Without this pattern, code tends toward: New things up with conditionals and duplicated construction.

## Real-world Analogy
YouTube subscribe bell.

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
Primary code lives under `src/main/java/com/buildwithwaqar/patterns/gof/behavioral/observer/`.

```java
package com.buildwithwaqar.patterns.gof.behavioral.observer;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public final class EventBus<T> {
    private final List<Consumer<T>> subscribers = new ArrayList<>();

    public AutoCloseable subscribe(Consumer<T> listener) {
        subscribers.add(listener);
        return () -> subscribers.remove(listener);
    }

    public void publish(T event) {
        for (var s : List.copyOf(subscribers)) s.accept(event);
    }
}
```

## Modern Java 25 / Functional
Java 25 pattern matching often replaces Visitor/Interpreter boilerplate.

## Worked Example
```text
See the JUnit test beside the implementation.
Run: mvn -Dtest=*Test test
Focus package matching: gof/behavioral/observer/
```

## Pitfalls
- Prefer sealed + switch over classic Visitor in Java 25.
- Strategy as a function value.
- Observer: unsubscribe to avoid leaks.

## Interview Q&A
**Q: Key interview angle for Observer?**
A: Name the force (undo, open/closed, open for extension) and a modern alternative.

**Q: How would you test this?**
A: Prefer injecting interfaces or interpreters; keep demos deterministic (fixed clocks, canned inputs).

## Key Takeaways
- Notify dependents of state changes.
- Prefer Java 25 sealed types / records / virtual threads where they simplify the classic shape.
- Ship working tests with every episode.

## Video Script
**[0:00] Hook** — Open with the analogy: YouTube subscribe bell.
**[0:40] Intent** — State the design force in one sentence: Notify dependents of state changes.
**[1:30] Naive pain** — Show the messy approach: New things up with conditionals and duplicated construction.
**[3:00] Pattern** — Walk the code under `gof/behavioral/observer/`; draw the mermaid structure on-screen.
**[6:00] Modern take** — Java 25 pattern matching often replaces Visitor/Interpreter boilerplate.
**[8:00] Pitfalls & interview** — Cover: Key interview angle for Observer?
**[9:30] Outro** — Tease the next episode; point viewers to the GitHub repo and slides PDF.
