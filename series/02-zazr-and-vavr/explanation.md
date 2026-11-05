# Zazr and VAVR Tour

**Season:** 0 · Intro · **Category:** FP Libraries · **Level:** Beginner

## Intent
Introduce Zazr (primary) and VAVR (comparison) control types and collections.

## Problem
Without this pattern, code tends toward: New things up with conditionals and duplicated construction.

## Real-world Analogy
Two toolboxes: VAVR is the classic; Zazr is the Java 25 redesign (sealed Option/Either/Try/Validation).

## When to Use
- Writing null-hostile, immutable, effect-aware Java.
- The design force named in Intent shows up more than once.

## When Not to Use
- Mixing both libraries in one module without a clear boundary.
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
Primary code lives under `src/main/java/com/buildwithwaqar/patterns/functional/vavrcompare/VavrVsZazr.java`.

```java
package com.buildwithwaqar.patterns.functional.vavrcompare;

/** Side-by-side Option usage — VAVR (classic) vs Zazr (Java 25 sealed/records). */
public final class VavrVsZazr {
    private VavrVsZazr() {}

    public static String vavrDescribe(io.vavr.control.Option<Integer> opt) {
        return opt.map(n -> "got " + n).getOrElse("missing");
    }

    public static String zazrDescribe(dev.zazr.control.Option<Integer> opt) {
        return switch (opt) {
            case dev.zazr.control.Option.Some(var n) -> "got " + n;
            case dev.zazr.control.Option.None() -> "missing";
        };
    }
}
```

## Modern Java 25 / Functional
Pattern-match Zazr Option.Some / Option.None; VAVR uses map/getOrElse.

## Worked Example
```text
See the JUnit test beside the implementation.
Run: mvn -Dtest=*Test test
Focus package matching: functional/vavrcompare/VavrVsZazr.java
```

## Pitfalls
- Zazr Option rejects null — use Unit markers, not null.
- Prefer Validation.zipWith to collect errors.

## Interview Q&A
**Q: Why Zazr over VAVR on Java 25?**
A: Sealed records + exhaustive switch, Scala 2.13 collections, ZIO-style names, no runtime deps.

**Q: How would you test this?**
A: Prefer injecting interfaces or interpreters; keep demos deterministic (fixed clocks, canned inputs).

## Key Takeaways
- Introduce Zazr (primary) and VAVR (comparison) control types and collections.
- Prefer Java 25 sealed types / records / virtual threads where they simplify the classic shape.
- Ship working tests with every episode.

## Video Script
**[0:00] Hook** — Open with the analogy: Two toolboxes: VAVR is the classic; Zazr is the Java 25 redesign (sealed Option/Either/Try/Validation).
**[0:40] Intent** — State the design force in one sentence: Introduce Zazr (primary) and VAVR (comparison) control types and collections.
**[1:30] Naive pain** — Show the messy approach: New things up with conditionals and duplicated construction.
**[3:00] Pattern** — Walk the code under `functional/vavrcompare/VavrVsZazr.java`; draw the mermaid structure on-screen.
**[6:00] Modern take** — Pattern-match Zazr Option.Some / Option.None; VAVR uses map/getOrElse.
**[8:00] Pitfalls & interview** — Cover: Why Zazr over VAVR on Java 25?
**[9:30] Outro** — Tease the next episode; point viewers to the GitHub repo and slides PDF.
