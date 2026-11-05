# Retry

**Season:** 4 · Enterprise · **Category:** Enterprise · **Level:** Intermediate

## Intent
Re-execute transient failures with policy.

## Problem
Without this pattern, code tends toward: New things up with conditionals and duplicated construction.

## Real-world Analogy
Redialing a busy phone line.

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
Primary code lives under `src/main/java/com/buildwithwaqar/patterns/enterprise/retry/`.

```java
package com.buildwithwaqar.patterns.enterprise.retry;

import java.util.concurrent.Callable;
import java.util.function.Predicate;

public final class Retry {
    private final int maxAttempts;
    private final Predicate<Exception> retryOn;

    public Retry(int maxAttempts, Predicate<Exception> retryOn) {
        this.maxAttempts = maxAttempts;
        this.retryOn = retryOn;
    }

    public <T> T execute(Callable<T> action) throws Exception {
        Exception last = null;
        for (int i = 1; i <= maxAttempts; i++) {
            try {
                return action.call();
            } catch (Exception e) {
                last = e;
                if (i == maxAttempts || !retryOn.test(e)) throw e;
            }
        }
        throw last;
    }
}
```

## Modern Java 25 / Functional
Sealed events + pattern matching for projections.

## Worked Example
```text
See the JUnit test beside the implementation.
Run: mvn -Dtest=*Test test
Focus package matching: enterprise/retry/
```

## Pitfalls
- Keep demos minimal — production needs idempotency and observability.
- Saga compensations must be careful.

## Interview Q&A
**Q: Production concern for Retry?**
A: Idempotency, observability, and failure modes.

**Q: How would you test this?**
A: Prefer injecting interfaces or interpreters; keep demos deterministic (fixed clocks, canned inputs).

## Key Takeaways
- Re-execute transient failures with policy.
- Prefer Java 25 sealed types / records / virtual threads where they simplify the classic shape.
- Ship working tests with every episode.

## Video Script
**[0:00] Hook** — Open with the analogy: Redialing a busy phone line.
**[0:40] Intent** — State the design force in one sentence: Re-execute transient failures with policy.
**[1:30] Naive pain** — Show the messy approach: New things up with conditionals and duplicated construction.
**[3:00] Pattern** — Walk the code under `enterprise/retry/`; draw the mermaid structure on-screen.
**[6:00] Modern take** — Sealed events + pattern matching for projections.
**[8:00] Pitfalls & interview** — Cover: Production concern for Retry?
**[9:30] Outro** — Tease the next episode; point viewers to the GitHub repo and slides PDF.
