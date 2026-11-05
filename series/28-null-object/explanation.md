# Null Object

**Season:** 4 · Enterprise · **Category:** Enterprise · **Level:** Intermediate

## Intent
Provide a do-nothing stand-in instead of null.

## Problem
Without this pattern, code tends toward: New things up with conditionals and duplicated construction.

## Real-world Analogy
Mute button that still is a speaker interface.

## When to Use
- Microservices / modular monoliths.
- The design force named in Intent shows up more than once.

## When Not to Use
- Greenfield CRUD without distribution.
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
Primary code lives under `src/main/java/com/buildwithwaqar/patterns/enterprise/nullobject/`.

```java
package com.buildwithwaqar.patterns.enterprise.nullobject;

interface Logger {
    void info(String msg);
}

final class ConsoleLogger implements Logger {
    private final StringBuilder sink;
    ConsoleLogger(StringBuilder sink) { this.sink = sink; }
    public void info(String msg) { sink.append(msg).append('\n'); }
}

final class NullLogger implements Logger {
    static final NullLogger INSTANCE = new NullLogger();
    public void info(String msg) { /* no-op */ }
}

/** Prefer Null Object over null checks at call sites. */
public final class OrderService {
    private final Logger logger;

    public OrderService(Logger logger) {
        this.logger = logger == null ? NullLogger.INSTANCE : logger;
    }

    public String place(String id) {
        logger.info("placing " + id);
        return "ok:" + id;
    }
}
```

## Modern Java 25 / Functional
Sealed events + pattern matching for projections.

## Worked Example
```text
See the JUnit test beside the implementation.
Run: mvn -Dtest=*Test test
Focus package matching: enterprise/nullobject/
```

## Pitfalls
- Keep demos minimal — production needs idempotency and observability.
- Saga compensations must be careful.

## Interview Q&A
**Q: Production concern for Null Object?**
A: Idempotency, observability, and failure modes.

**Q: How would you test this?**
A: Prefer injecting interfaces or interpreters; keep demos deterministic (fixed clocks, canned inputs).

## Key Takeaways
- Provide a do-nothing stand-in instead of null.
- Prefer Java 25 sealed types / records / virtual threads where they simplify the classic shape.
- Ship working tests with every episode.

## Video Script
**[0:00] Hook** — Open with the analogy: Mute button that still is a speaker interface.
**[0:40] Intent** — State the design force in one sentence: Provide a do-nothing stand-in instead of null.
**[1:30] Naive pain** — Show the messy approach: New things up with conditionals and duplicated construction.
**[3:00] Pattern** — Walk the code under `enterprise/nullobject/`; draw the mermaid structure on-screen.
**[6:00] Modern take** — Sealed events + pattern matching for projections.
**[8:00] Pitfalls & interview** — Cover: Production concern for Null Object?
**[9:30] Outro** — Tease the next episode; point viewers to the GitHub repo and slides PDF.
