# Singleton

**Season:** 1 · Creational · **Category:** GoF · **Level:** Beginner

## Intent
One shared instance with a global access point.

## Problem
Without this pattern, code tends toward: New things up with conditionals and duplicated construction.

## Real-world Analogy
One official passport office for the country.

## When to Use
- True process-wide shared resource (metrics, feature flags).
- The design force named in Intent shows up more than once.

## When Not to Use
- Request-scoped state; anything you need to mock easily.
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
Primary code lives under `src/main/java/com/buildwithwaqar/patterns/gof/creational/singleton/`.

```java
package com.buildwithwaqar.patterns.gof.creational.singleton;

import java.time.Instant;

/** Prefer injecting a Clock over a time singleton. */
public interface Clock {
    Instant now();

    record SystemClock() implements Clock {
        @Override public Instant now() { return Instant.now(); }
    }

    record FixedClock(Instant instant) implements Clock {
        @Override public Instant now() { return instant; }
    }
}
```

## Modern Java 25 / Functional
Inject Clock instead of System.currentTimeMillis(); use virtual threads safely with holder idiom.

## Worked Example
```text
See the JUnit test beside the implementation.
Run: mvn -Dtest=*Test test
Focus package matching: gof/creational/singleton/
```

## Pitfalls
- Enum singleton is preferred.
- Mutable singleton state is a concurrency hazard.
- Prefer DI at edges.

## Interview Q&A
**Q: Enum vs double-checked locking?**
A: Enum is serialization/reflection-safe; DCL is easy to get wrong — prefer holder or enum.

**Q: How would you test this?**
A: Prefer injecting interfaces or interpreters; keep demos deterministic (fixed clocks, canned inputs).

## Key Takeaways
- One shared instance with a global access point.
- Prefer Java 25 sealed types / records / virtual threads where they simplify the classic shape.
- Ship working tests with every episode.

## Video Script
**[0:00] Hook** — Open with the analogy: One official passport office for the country.
**[0:40] Intent** — State the design force in one sentence: One shared instance with a global access point.
**[1:30] Naive pain** — Show the messy approach: New things up with conditionals and duplicated construction.
**[3:00] Pattern** — Walk the code under `gof/creational/singleton/`; draw the mermaid structure on-screen.
**[6:00] Modern take** — Inject Clock instead of System.currentTimeMillis(); use virtual threads safely with holder idiom.
**[8:00] Pitfalls & interview** — Cover: Enum vs double-checked locking?
**[9:30] Outro** — Tease the next episode; point viewers to the GitHub repo and slides PDF.
