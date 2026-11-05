# Writer Monad

**Season:** 7 · Advanced FP · **Category:** Functional · **Level:** Advanced

## Intent
Produce a value and an accumulated log.

## Problem
Without this pattern, code tends toward: New things up with conditionals and duplicated construction.

## Real-world Analogy
Lab notebook beside the experiment.

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
Primary code lives under `src/main/java/com/buildwithwaqar/patterns/functional/writer/`.

```java
package com.buildwithwaqar.patterns.functional.writer;

import com.buildwithwaqar.patterns.functional.monoid.Monoid;

import java.util.function.Function;

public record Writer<W, A>(W log, A value) {
    public <B> Writer<W, B> map(Function<? super A, ? extends B> f) {
        return new Writer<>(log, f.apply(value));
    }

    public <B> Writer<W, B> flatMap(Monoid<W> monoid, Function<? super A, Writer<W, B>> f) {
        var next = f.apply(value);
        return new Writer<>(monoid.combine(log, next.log), next.value);
    }

    public static <W, A> Writer<W, A> pure(Monoid<W> monoid, A a) {
        return new Writer<>(monoid.empty(), a);
    }

    public static <W> Writer<W, Void> tell(W w) { return new Writer<>(w, null); }
}
```

## Modern Java 25 / Functional
Witness types emulate higher-kinded types.

## Worked Example
```text
See the JUnit test beside the implementation.
Run: mvn -Dtest=*Test test
Focus package matching: functional/writer/
```

## Pitfalls
- Encodings are educational — production may use libraries.
- Tagless Final needs Kind encoding without HKTs.

## Interview Q&A
**Q: When to pick Writer Monad?**
A: When you need testable effect boundaries or pure state threading.

**Q: How would you test this?**
A: Prefer injecting interfaces or interpreters; keep demos deterministic (fixed clocks, canned inputs).

## Key Takeaways
- Produce a value and an accumulated log.
- Prefer Java 25 sealed types / records / virtual threads where they simplify the classic shape.
- Ship working tests with every episode.

## Video Script
**[0:00] Hook** — Open with the analogy: Lab notebook beside the experiment.
**[0:40] Intent** — State the design force in one sentence: Produce a value and an accumulated log.
**[1:30] Naive pain** — Show the messy approach: New things up with conditionals and duplicated construction.
**[3:00] Pattern** — Walk the code under `functional/writer/`; draw the mermaid structure on-screen.
**[6:00] Modern take** — Witness types emulate higher-kinded types.
**[8:00] Pitfalls & interview** — Cover: When to pick Writer Monad?
**[9:30] Outro** — Tease the next episode; point viewers to the GitHub repo and slides PDF.
