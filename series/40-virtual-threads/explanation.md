# Virtual Threads

**Season:** 5 · Concurrency · **Category:** Concurrency · **Level:** Intermediate

## Intent
Lightweight threads for massive concurrency (Java 21+).

## Problem
Without this pattern, code tends toward: New things up with conditionals and duplicated construction.

## Real-world Analogy
Millions of cheap coroutines scheduled on carriers.

## When to Use
- Concurrent / parallel workloads.
- The design force named in Intent shows up more than once.

## When Not to Use
- Pure CPU-bound work already parallelized.
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
Primary code lives under `src/main/java/com/buildwithwaqar/patterns/concurrency/virtualthreads/`.

```java
package com.buildwithwaqar.patterns.concurrency.virtualthreads;

import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/** Java 21+ virtual threads — prefer over hand-rolled pools for blocking work. */
public final class VirtualThreadDemo {
    private VirtualThreadDemo() {}

    public static int fanOut(int tasks) throws InterruptedException {
        var hits = new AtomicInteger();
        try (var pool = Executors.newVirtualThreadPerTaskExecutor()) {
            for (int i = 0; i < tasks; i++) {
                pool.submit(hits::incrementAndGet);
            }
        }
        return hits.get();
    }
}
```

## Modern Java 25 / Functional
Executors.newVirtualThreadPerTaskExecutor() is the modern default for blocking tasks.

## Worked Example
```text
See the JUnit test beside the implementation.
Run: mvn -Dtest=*Test test
Focus package matching: concurrency/virtualthreads/
```

## Pitfalls
- Prefer java.util.concurrent and virtual threads over custom pools.
- Always interrupt/shutdown cleanly.

## Interview Q&A
**Q: Virtual threads vs platform threads?**
A: Virtual threads excel at blocking I/O concurrency; still bound by CPU for pure compute.

**Q: How would you test this?**
A: Prefer injecting interfaces or interpreters; keep demos deterministic (fixed clocks, canned inputs).

## Key Takeaways
- Lightweight threads for massive concurrency (Java 21+).
- Prefer Java 25 sealed types / records / virtual threads where they simplify the classic shape.
- Ship working tests with every episode.

## Video Script
**[0:00] Hook** — Open with the analogy: Millions of cheap coroutines scheduled on carriers.
**[0:40] Intent** — State the design force in one sentence: Lightweight threads for massive concurrency (Java 21+).
**[1:30] Naive pain** — Show the messy approach: New things up with conditionals and duplicated construction.
**[3:00] Pattern** — Walk the code under `concurrency/virtualthreads/`; draw the mermaid structure on-screen.
**[6:00] Modern take** — Executors.newVirtualThreadPerTaskExecutor() is the modern default for blocking tasks.
**[8:00] Pitfalls & interview** — Cover: Virtual threads vs platform threads?
**[9:30] Outro** — Tease the next episode; point viewers to the GitHub repo and slides PDF.
