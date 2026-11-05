# State Monad

**Season:** 7 · Advanced FP · **Category:** Functional · **Level:** Advanced

## Intent
Purely thread mutable state.

## Problem
Without this pattern, code tends toward: New things up with conditionals and duplicated construction.

## Real-world Analogy
Game score threaded through pure moves.

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
Primary code lives under `src/main/java/com/buildwithwaqar/patterns/functional/state/`.

```java
package com.buildwithwaqar.patterns.functional.state;

import java.util.function.Function;

public record State<S, A>(Function<S, Result<S, A>> run) {
    public record Result<S, A>(S state, A value) {}

    public Result<S, A> run(S s) { return run.apply(s); }

    public <B> State<S, B> map(Function<? super A, ? extends B> f) {
        return new State<>(s -> {
            var r = run(s);
            return new Result<>(r.state(), f.apply(r.value()));
        });
    }

    public <B> State<S, B> flatMap(Function<? super A, State<S, B>> f) {
        return new State<>(s -> {
            var r = run(s);
            return f.apply(r.value()).run(r.state());
        });
    }

    public static <S, A> State<S, A> pure(A a) { return new State<>(s -> new Result<>(s, a)); }
    public static <S> State<S, S> get() { return new State<>(s -> new Result<>(s, s)); }
    public static <S> State<S, Void> set(S s) { return new State<>(ignored -> new Result<>(s, null)); }
    public static <S> State<S, Void> modify(Function<S, S> f) {
        return new State<>(s -> new Result<>(f.apply(s), null));
    }
}
```

## Modern Java 25 / Functional
Witness types emulate higher-kinded types.

## Worked Example
```text
See the JUnit test beside the implementation.
Run: mvn -Dtest=*Test test
Focus package matching: functional/state/
```

## Pitfalls
- Encodings are educational — production may use libraries.
- Tagless Final needs Kind encoding without HKTs.

## Interview Q&A
**Q: When to pick State Monad?**
A: When you need testable effect boundaries or pure state threading.

**Q: How would you test this?**
A: Prefer injecting interfaces or interpreters; keep demos deterministic (fixed clocks, canned inputs).

## Key Takeaways
- Purely thread mutable state.
- Prefer Java 25 sealed types / records / virtual threads where they simplify the classic shape.
- Ship working tests with every episode.

## Video Script
**[0:00] Hook** — Open with the analogy: Game score threaded through pure moves.
**[0:40] Intent** — State the design force in one sentence: Purely thread mutable state.
**[1:30] Naive pain** — Show the messy approach: New things up with conditionals and duplicated construction.
**[3:00] Pattern** — Walk the code under `functional/state/`; draw the mermaid structure on-screen.
**[6:00] Modern take** — Witness types emulate higher-kinded types.
**[8:00] Pitfalls & interview** — Cover: When to pick State Monad?
**[9:30] Outro** — Tease the next episode; point viewers to the GitHub repo and slides PDF.
