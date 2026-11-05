# Lazy & IO

**Season:** 6 · FP Foundations · **Category:** Functional · **Level:** Intermediate

## Intent
Defer effects; run at the edge.

## Problem
Without this pattern, code tends toward: New things up with conditionals and duplicated construction.

## Real-world Analogy
A recipe card vs cooking the meal.

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
Primary code lives under `src/main/java/com/buildwithwaqar/patterns/functional/lazy/ + functional/io/`.

```java
package com.buildwithwaqar.patterns.functional.io;

import java.util.function.Function;
import java.util.function.Supplier;

/** Tiny IO effect — suspend side effects, interpret at the edge. */
public final class IO<A> {
    private final Supplier<A> thunk;

    private IO(Supplier<A> thunk) { this.thunk = thunk; }

    public static <A> IO<A> delay(Supplier<A> thunk) { return new IO<>(thunk); }
    public static <A> IO<A> pure(A a) { return new IO<>(() -> a); }

    public <B> IO<B> map(Function<? super A, ? extends B> f) {
        return new IO<>(() -> f.apply(thunk.get()));
    }

    public <B> IO<B> flatMap(Function<? super A, IO<B>> f) {
        return new IO<>(() -> f.apply(thunk.get()).unsafeRun());
    }

    /** Unsafe: runs effects. Call only at the program edge. */
    public A unsafeRun() { return thunk.get(); }
}
```

## Modern Java 25 / Functional
Primary examples use Zazr; VAVR shown in comparison episode.

## Worked Example
```text
See the JUnit test beside the implementation.
Run: mvn -Dtest=*Test test
Focus package matching: functional/lazy/ + functional/io/
```

## Pitfalls
- Zazr rejects null inside Option/collections.
- Keep interpreters at the edge.

## Interview Q&A
**Q: Interview: what problem does Lazy & IO solve?**
A: Defer effects; run at the edge.

**Q: How would you test this?**
A: Prefer injecting interfaces or interpreters; keep demos deterministic (fixed clocks, canned inputs).

## Key Takeaways
- Defer effects; run at the edge.
- Prefer Java 25 sealed types / records / virtual threads where they simplify the classic shape.
- Ship working tests with every episode.

## Video Script
**[0:00] Hook** — Open with the analogy: A recipe card vs cooking the meal.
**[0:40] Intent** — State the design force in one sentence: Defer effects; run at the edge.
**[1:30] Naive pain** — Show the messy approach: New things up with conditionals and duplicated construction.
**[3:00] Pattern** — Walk the code under `functional/lazy/ + functional/io/`; draw the mermaid structure on-screen.
**[6:00] Modern take** — Primary examples use Zazr; VAVR shown in comparison episode.
**[8:00] Pitfalls & interview** — Cover: Interview: what problem does Lazy & IO solve?
**[9:30] Outro** — Tease the next episode; point viewers to the GitHub repo and slides PDF.
