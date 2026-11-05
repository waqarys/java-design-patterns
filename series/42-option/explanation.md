# Option / Maybe

**Season:** 6 · FP Foundations · **Category:** Functional · **Level:** Intermediate

## Intent
Model absence without null.

## Problem
Without this pattern, code tends toward: New things up with conditionals and duplicated construction.

## Real-world Analogy
A sealed box that is Some or None.

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
Primary code lives under `src/main/java/com/buildwithwaqar/patterns/functional/option/`.

```java
package com.buildwithwaqar.patterns.functional.option;

import dev.zazr.control.Option;

import static dev.zazr.control.Option.none;
import static dev.zazr.control.Option.some;

/** Option / Maybe with Zazr — sealed Some/None, pattern-matchable. */
public final class ZazrOptionDemo {
    private ZazrOptionDemo() {}

    public static Option<Integer> parsePositive(String raw) {
        try {
            int n = Integer.parseInt(raw);
            return n > 0 ? some(n) : none();
        } catch (NumberFormatException e) {
            return none();
        }
    }

    public static String describe(Option<Integer> opt) {
        return switch (opt) {
            case Option.Some(var n) -> "got " + n;
            case Option.None() -> "missing";
        };
    }

    public static Option<Integer> sum3(Option<Integer> a, Option<Integer> b, Option<Integer> c) {
        return Option.zipWith(a, b, c, (x, y, z) -> x + y + z);
    }
}
```

## Modern Java 25 / Functional
Primary examples use Zazr; VAVR shown in comparison episode.

## Worked Example
```text
See the JUnit test beside the implementation.
Run: mvn -Dtest=*Test test
Focus package matching: functional/option/
```

## Pitfalls
- Zazr rejects null inside Option/collections.
- Keep interpreters at the edge.

## Interview Q&A
**Q: Interview: what problem does Option / Maybe solve?**
A: Model absence without null.

**Q: How would you test this?**
A: Prefer injecting interfaces or interpreters; keep demos deterministic (fixed clocks, canned inputs).

## Key Takeaways
- Model absence without null.
- Prefer Java 25 sealed types / records / virtual threads where they simplify the classic shape.
- Ship working tests with every episode.

## Video Script
**[0:00] Hook** — Open with the analogy: A sealed box that is Some or None.
**[0:40] Intent** — State the design force in one sentence: Model absence without null.
**[1:30] Naive pain** — Show the messy approach: New things up with conditionals and duplicated construction.
**[3:00] Pattern** — Walk the code under `functional/option/`; draw the mermaid structure on-screen.
**[6:00] Modern take** — Primary examples use Zazr; VAVR shown in comparison episode.
**[8:00] Pitfalls & interview** — Cover: Interview: what problem does Option / Maybe solve?
**[9:30] Outro** — Tease the next episode; point viewers to the GitHub repo and slides PDF.
