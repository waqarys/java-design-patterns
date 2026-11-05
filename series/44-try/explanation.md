# Try

**Season:** 6 · FP Foundations · **Category:** Functional · **Level:** Intermediate

## Intent
Capture exceptions as values.

## Problem
Without this pattern, code tends toward: New things up with conditionals and duplicated construction.

## Real-world Analogy
Safety net under a trapeze.

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
Primary code lives under `src/main/java/com/buildwithwaqar/patterns/functional/try_/`.

```java
package com.buildwithwaqar.patterns.functional.try_;

import dev.zazr.control.Try;

public final class ZazrTryDemo {
    private ZazrTryDemo() {}

    public static Try<Integer> parse(String raw) {
        return Try.of(() -> Integer.parseInt(raw));
    }

    public static int portOrDefault(String raw, int fallback) {
        return parse(raw).catchAll(err -> fallback).get();
    }
}
```

## Modern Java 25 / Functional
Primary examples use Zazr; VAVR shown in comparison episode.

## Worked Example
```text
See the JUnit test beside the implementation.
Run: mvn -Dtest=*Test test
Focus package matching: functional/try_/
```

## Pitfalls
- Zazr rejects null inside Option/collections.
- Keep interpreters at the edge.

## Interview Q&A
**Q: Interview: what problem does Try solve?**
A: Capture exceptions as values.

**Q: How would you test this?**
A: Prefer injecting interfaces or interpreters; keep demos deterministic (fixed clocks, canned inputs).

## Key Takeaways
- Capture exceptions as values.
- Prefer Java 25 sealed types / records / virtual threads where they simplify the classic shape.
- Ship working tests with every episode.

## Video Script
**[0:00] Hook** — Open with the analogy: Safety net under a trapeze.
**[0:40] Intent** — State the design force in one sentence: Capture exceptions as values.
**[1:30] Naive pain** — Show the messy approach: New things up with conditionals and duplicated construction.
**[3:00] Pattern** — Walk the code under `functional/try_/`; draw the mermaid structure on-screen.
**[6:00] Modern take** — Primary examples use Zazr; VAVR shown in comparison episode.
**[8:00] Pitfalls & interview** — Cover: Interview: what problem does Try solve?
**[9:30] Outro** — Tease the next episode; point viewers to the GitHub repo and slides PDF.
