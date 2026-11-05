# Smart Constructors

**Season:** 6 · FP Foundations · **Category:** Functional · **Level:** Intermediate

## Intent
Make illegal states unrepresentable.

## Problem
Without this pattern, code tends toward: New things up with conditionals and duplicated construction.

## Real-world Analogy
Turnstiles that only admit valid tickets.

## When to Use
- Pure functional style in Java 25 + Zazr.
- The design force named in Intent shows up more than once.

## When Not to Use
- Throwing exceptions everywhere is 'fine' for a script.
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
Primary code lives under `src/main/java/com/buildwithwaqar/patterns/functional/adt/Email`.

```java
package com.buildwithwaqar.patterns.functional.adt;

import dev.zazr.control.Either;

/** Smart constructor — prefer {@link #parse}; canonical ctor still validates. */
public record Email(String value) {
    public Email {
        if (value == null || !value.contains("@")) {
            throw new IllegalArgumentException("invalid email");
        }
    }

    public static Either<String, Email> parse(String raw) {
        if (raw == null || !raw.contains("@")) return Either.left("invalid email");
        return Either.right(new Email(raw));
    }
}
```

## Modern Java 25 / Functional
Primary examples use Zazr; VAVR shown in comparison episode.

## Worked Example
```text
See the JUnit test beside the implementation.
Run: mvn -Dtest=*Test test
Focus package matching: functional/adt/Email
```

## Pitfalls
- Zazr rejects null inside Option/collections.
- Keep interpreters at the edge.

## Interview Q&A
**Q: Interview: what problem does Smart Constructors solve?**
A: Make illegal states unrepresentable.

**Q: How would you test this?**
A: Prefer injecting interfaces or interpreters; keep demos deterministic (fixed clocks, canned inputs).

## Key Takeaways
- Make illegal states unrepresentable.
- Prefer Java 25 sealed types / records / virtual threads where they simplify the classic shape.
- Ship working tests with every episode.

## Video Script
**[0:00] Hook** — Open with the analogy: Turnstiles that only admit valid tickets.
**[0:40] Intent** — State the design force in one sentence: Make illegal states unrepresentable.
**[1:30] Naive pain** — Show the messy approach: New things up with conditionals and duplicated construction.
**[3:00] Pattern** — Walk the code under `functional/adt/Email`; draw the mermaid structure on-screen.
**[6:00] Modern take** — Primary examples use Zazr; VAVR shown in comparison episode.
**[8:00] Pitfalls & interview** — Cover: Interview: what problem does Smart Constructors solve?
**[9:30] Outro** — Tease the next episode; point viewers to the GitHub repo and slides PDF.
