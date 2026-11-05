# Reader Monad

**Season:** 7 · Advanced FP · **Category:** Functional · **Level:** Advanced

## Intent
Thread an environment through a computation.

## Problem
Without this pattern, code tends toward: New things up with conditionals and duplicated construction.

## Real-world Analogy
Every function reads the same config clipboard.

## When to Use
- Serious FP architecture in Java.
- The design force named in Intent shows up more than once.

## When Not to Use
- CRUD apps that don't need effect algebras.
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
Primary code lives under `src/main/java/com/buildwithwaqar/patterns/functional/reader/`.

```java
package com.buildwithwaqar.patterns.functional.reader;

import java.util.function.Function;

/** Reader monad — computation that depends on an environment R. */
public record Reader<R, A>(Function<R, A> run) {
    public A run(R env) { return run.apply(env); }

    public <B> Reader<R, B> map(Function<? super A, ? extends B> f) {
        return new Reader<>(env -> f.apply(run(env)));
    }

    public <B> Reader<R, B> flatMap(Function<? super A, Reader<R, B>> f) {
        return new Reader<>(env -> f.apply(run(env)).run(env));
    }

    public static <R, A> Reader<R, A> pure(A a) { return new Reader<>(env -> a); }
    public static <R> Reader<R, R> ask() { return new Reader<>(Function.identity()); }
}

record Config(String greeting, String name) {}
```

## Modern Java 25 / Functional
Witness types emulate higher-kinded types.

## Worked Example
```text
See the JUnit test beside the implementation.
Run: mvn -Dtest=*Test test
Focus package matching: functional/reader/
```

## Pitfalls
- Encodings are educational — production may use libraries.
- Tagless Final needs Kind encoding without HKTs.

## Interview Q&A
**Q: When to pick Reader Monad?**
A: When you need testable effect boundaries or pure state threading.

**Q: How would you test this?**
A: Prefer injecting interfaces or interpreters; keep demos deterministic (fixed clocks, canned inputs).

## Key Takeaways
- Thread an environment through a computation.
- Prefer Java 25 sealed types / records / virtual threads where they simplify the classic shape.
- Ship working tests with every episode.

## Video Script
**[0:00] Hook** — Open with the analogy: Every function reads the same config clipboard.
**[0:40] Intent** — State the design force in one sentence: Thread an environment through a computation.
**[1:30] Naive pain** — Show the messy approach: New things up with conditionals and duplicated construction.
**[3:00] Pattern** — Walk the code under `functional/reader/`; draw the mermaid structure on-screen.
**[6:00] Modern take** — Witness types emulate higher-kinded types.
**[8:00] Pitfalls & interview** — Cover: When to pick Reader Monad?
**[9:30] Outro** — Tease the next episode; point viewers to the GitHub repo and slides PDF.
