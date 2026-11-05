# Immutability & Persistent Collections

**Season:** 6 · FP Foundations · **Category:** Functional · **Level:** Intermediate

## Intent
Share structure safely with Zazr Vector / NonEmptyVector.

## Problem
Without this pattern, code tends toward: New things up with conditionals and duplicated construction.

## Real-world Analogy
Lego sets you can rebuild without melting old bricks.

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
Primary code lives under `src/main/java/com/buildwithwaqar/patterns/functional/collections/`.

```java
package com.buildwithwaqar.patterns.functional.collections;

import dev.zazr.collection.NonEmptyVector;
import dev.zazr.collection.Vector;

public final class ZazrCollectionsDemo {
    private ZazrCollectionsDemo() {}

    public static Vector<Integer> doubleAll(Vector<Integer> xs) {
        return xs.map(n -> n * 2);
    }

    public static int maxScore(NonEmptyVector<Integer> scores) {
        return scores.max(Integer::compare);
    }

    public static Vector<Integer> buildFast(int n) {
        var b = Vector.<Integer>newBuilder();
        for (int i = 0; i < n; i++) b.add(i);
        return b.result();
    }
}
```

## Modern Java 25 / Functional
Primary examples use Zazr; VAVR shown in comparison episode.

## Worked Example
```text
See the JUnit test beside the implementation.
Run: mvn -Dtest=*Test test
Focus package matching: functional/collections/
```

## Pitfalls
- Zazr rejects null inside Option/collections.
- Keep interpreters at the edge.

## Interview Q&A
**Q: Interview: what problem does Immutability & Persistent Collections solve?**
A: Share structure safely with Zazr Vector / NonEmptyVector.

**Q: How would you test this?**
A: Prefer injecting interfaces or interpreters; keep demos deterministic (fixed clocks, canned inputs).

## Key Takeaways
- Share structure safely with Zazr Vector / NonEmptyVector.
- Prefer Java 25 sealed types / records / virtual threads where they simplify the classic shape.
- Ship working tests with every episode.

## Video Script
**[0:00] Hook** — Open with the analogy: Lego sets you can rebuild without melting old bricks.
**[0:40] Intent** — State the design force in one sentence: Share structure safely with Zazr Vector / NonEmptyVector.
**[1:30] Naive pain** — Show the messy approach: New things up with conditionals and duplicated construction.
**[3:00] Pattern** — Walk the code under `functional/collections/`; draw the mermaid structure on-screen.
**[6:00] Modern take** — Primary examples use Zazr; VAVR shown in comparison episode.
**[8:00] Pitfalls & interview** — Cover: Interview: what problem does Immutability & Persistent Collections solve?
**[9:30] Outro** — Tease the next episode; point viewers to the GitHub repo and slides PDF.
