# Kleisli Composition

**Season:** 7 · Advanced FP · **Category:** Functional · **Level:** Advanced

## Intent
Compose A → F&lt;B&gt; arrows.

## Problem
Without this pattern, code tends toward: New things up with conditionals and duplicated construction.

## Real-world Analogy
Effectful function pipelines.

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
Primary code lives under `src/main/java/com/buildwithwaqar/patterns/functional/kleisli/`.

```java
package com.buildwithwaqar.patterns.functional.kleisli;

import com.buildwithwaqar.patterns.functional.typeclass.Kind;
import com.buildwithwaqar.patterns.functional.typeclass.Monad;

import java.util.function.Function;

/** Kleisli arrow A =&gt; F&lt;B&gt; — composable effectful functions. */
public record Kleisli<F, A, B>(Function<A, Kind<F, B>> run) {
    public Kind<F, B> apply(A a) { return run.apply(a); }

    public <C> Kleisli<F, A, C> andThen(Monad<F> M, Kleisli<F, B, C> next) {
        return new Kleisli<>(a -> M.flatMap(run.apply(a), next.run));
    }

    public static <F, A, B> Kleisli<F, A, B> of(Function<A, Kind<F, B>> f) {
        return new Kleisli<>(f);
    }
}
```

## Modern Java 25 / Functional
Witness types emulate higher-kinded types.

## Worked Example
```text
See the JUnit test beside the implementation.
Run: mvn -Dtest=*Test test
Focus package matching: functional/kleisli/
```

## Pitfalls
- Encodings are educational — production may use libraries.
- Tagless Final needs Kind encoding without HKTs.

## Interview Q&A
**Q: When to pick Kleisli Composition?**
A: When you need testable effect boundaries or pure state threading.

**Q: How would you test this?**
A: Prefer injecting interfaces or interpreters; keep demos deterministic (fixed clocks, canned inputs).

## Key Takeaways
- Compose A → F&lt;B&gt; arrows.
- Prefer Java 25 sealed types / records / virtual threads where they simplify the classic shape.
- Ship working tests with every episode.

## Video Script
**[0:00] Hook** — Open with the analogy: Effectful function pipelines.
**[0:40] Intent** — State the design force in one sentence: Compose A → F&lt;B&gt; arrows.
**[1:30] Naive pain** — Show the messy approach: New things up with conditionals and duplicated construction.
**[3:00] Pattern** — Walk the code under `functional/kleisli/`; draw the mermaid structure on-screen.
**[6:00] Modern take** — Witness types emulate higher-kinded types.
**[8:00] Pitfalls & interview** — Cover: When to pick Kleisli Composition?
**[9:30] Outro** — Tease the next episode; point viewers to the GitHub repo and slides PDF.
