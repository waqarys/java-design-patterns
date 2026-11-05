# Producer-Consumer

**Season:** 5 · Concurrency · **Category:** Concurrency · **Level:** Intermediate · **Runtime:** ~10–12 min · **Channel:** Build with Waqar

## Problem
**Bounded queue between encoder and uploader**

Key types: `BoundedQueue`.

## Hook
Picture this: you are mid-demo and «Bounded queue between encoder and uploader» blows up the naive way — you see `Failure path is asserted in ProblemTest (injected fault / invalid input)`. That is the pain. In the next ten minutes we introduce **Producer-Consumer** with real types (BoundedQueue), a runnable `ProblemDemo`, and the failure path locked in by tests. No abstract UML first — problem first.

## Intent
Use **Producer-Consumer** to cleanly solve: Bounded queue between encoder and uploader.

**When to use:** the force behind «Bounded queue between encoder and uploader» shows up more than once and a simpler language feature does not erase it.

**When to refuse:** you are pattern-collecting, the boundary is still unclear, or the team cannot yet test the abstraction (compensations, half-open recovery, interpreters).

## The Pain — Naive Design
Naive version fails to cleanly solve: Bounded queue between encoder and uploader

```java
package com.buildwithwaqar.patterns.concurrency.producerconsumer.problem;

import java.util.*;

public final class NaivePipe {
  public static void run(List<String> jobs){ for(String j: jobs) System.out.println("upload "+j); }
}

```

On camera, point at the smell: missing compensations, leaked XML, null at the boundary,
illegal transitions, or hard-wired `System.out` that you cannot assert.

## Pattern Structure
```mermaid
flowchart LR
  Client[Client] --> Bounded[Bounded]
  Bounded[Bounded] --> queue[queue]
  queue[queue] --> Result[Result]
```

These roles are for **«Bounded queue between encoder and uploader»** — not a generic Client→Pattern poster. Keep them on screen
while you type the solution.

## Refactor to the Pattern

### Stage 1 — Name the boundary
Introduce the client-facing API the rest of the app should depend on. Hide the incompatible or
distributed details behind it. For this episode that boundary is what makes «Bounded queue between encoder and uploader» testable.

### Stage 2 — Encode the solution
```java
package com.buildwithwaqar.patterns.concurrency.producerconsumer.problem;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.util.concurrent.locks.*;
import java.util.function.*;

final class BoundedQueue<T> {
  private final ArrayBlockingQueue<T> q;
  BoundedQueue(int cap){ q=new ArrayBlockingQueue<>(cap); }
  void put(T t) throws InterruptedException { q.put(t); }
  T take() throws InterruptedException { return q.take(); }
  int size(){ return q.size(); }
}

```

### Stage 3 — Runnable main
```java
package com.buildwithwaqar.patterns.concurrency.producerconsumer.problem;
import java.util.*; import java.util.concurrent.*; import java.util.concurrent.atomic.*;
public final class ProblemDemo {
  public static void main(String[] args) throws Exception {
    System.out.println("=== Producer-Consumer: encoder→uploader queue ===");
    System.out.println("--- naive ---"); NaivePipe.run(List.of("a","b"));
System.out.println("--- producer-consumer ---");
var q=new BoundedQueue<String>(2);
var done=new CountDownLatch(1); List<String> uploaded=new ArrayList<>();
Thread consumer=Thread.ofVirtual().start(() -> { try {
  for(int i=0;i<3;i++) uploaded.add(q.take());
  done.countDown();
} catch(InterruptedException e){ Thread.currentThread().interrupt(); }});
q.put("a"); q.put("b"); q.put("c"); done.await(2, TimeUnit.SECONDS);
System.out.println("uploaded="+uploaded);
  }
}

```

## Runnable Output (Verified on JDK 25)
Narrate each line. Do not invent numbers on camera.

```text
=== Producer-Consumer: encoder→uploader queue ===
--- naive ---
upload a
upload b
--- producer-consumer ---
uploaded=[a, b, c]
```

Happy-path cue: `upload a`.
Failure-path cue: `Failure path is asserted in ProblemTest (injected fault / invalid input)`.
Fork the repo, set `JAVA_HOME` to JDK 25, and re-run the same `ProblemDemo` to verify.

## Modern Java 25 / Zazr Variant
For **Producer-Consumer**, prefer records for results, sealed types for closed variants, and Zazr `Option`/`Either`/`Validation` when the force is absence or accumulated errors. Keep the classic shape when interview literacy matters.

## Trade-offs
- **Clarity vs ceremony:** extra types for «Bounded queue between encoder and uploader» only pay off when the force repeats.
- **Testability:** inject collaborators; assert the failure path, not only the happy path.
- **Distributed / concurrent reality:** demos omit metrics, backpressure, and multi-region clocks.
- **Refuse Producer-Consumer** when a sealed switch, a function value, or a simpler library call already erases the force.
- **Neighbor:** Thread Pool — Bounded queue decouples rates; a pool caps concurrent workers.

## Interview Q&A
**Q1. What concrete problem does Producer-Consumer solve here?**
A: Bounded queue between encoder and uploader.

**Q2. What does the naive design get wrong?**
A: Naive version fails to cleanly solve: Bounded queue between encoder and uploader

**Q3. How do you test the failure path?**
A: Inject the fault and assert the observable outcome — e.g. `Failure path is asserted in ProblemTest (injected fault / invalid input)`.

**Q4. When is Producer-Consumer the wrong tool?**
A: When a smaller language feature erases the force, or when you really needed Thread Pool.


## Key Takeaways
- Lead with the problem: Bounded queue between encoder and uploader.
- Show naive pain (`Failure path is asserted in ProblemTest (injected fault / invalid input)`) before naming Producer-Consumer.
- Run the Demo; trust JDK 25 stdout (`upload a`).
- Teach when NOT to use Producer-Consumer.

## Video Script (≈10–12 minutes)
Record for **Build with Waqar**. OBS for capture, CapCut for assembly. Import `slides.pdf`.
Facecam: full-screen for the hook, framed PiP over code slides.

**[0:00]** *(slide 01 title + talking head)*
Hey everyone — today we solve one concrete problem with **Producer-Consumer**:
*Bounded queue between encoder and uploader*. Keep these names in your head: BoundedQueue.

**[0:45]** *(problem + hook slides)*
I am not starting with a textbook definition. Here is the product pain. By the end you will have
seen the naive leak, the pattern boundary, and real JDK 25 stdout.

**[2:00]** *(naive code)*
IDE time. This is the naive version. Call out the smell — that is what Producer-Consumer exists to erase.

**[3:30]** *(structure diagram)*
Roles for **this** problem, not a generic UML poster. Point at each box while you talk.

**[4:30]** *(refactor / solution code)*
Stage the solution. Highlight failure injection and the tests that lock it in.

**[7:00]** *(walkthrough output)*
Real stdout. Happy path cue: `upload a`.
Failure cue: `Failure path is asserted in ProblemTest (injected fault / invalid input)`.
No invented numbers on camera.

**[8:30]** *(modern Java 25 / Zazr + trade-offs)*
Show the modernization, then slow down for when NOT to use Producer-Consumer. That is what seniors listen for.

**[10:30]** *(Q&A + recap + outro)*
Two interview questions, three takeaways, link the GitHub episode folder. I am Waqar — see you in the next one.


## Walkthrough Beats (Say These Out Loud)
1. **Setup:** open `series/35-producer-consumer/` and `run-output.txt` beside the IDE.
2. **Naive:** reproduce the pain until you can point at `Failure path is asserted in ProblemTest (injected fault / invalid input)`.
3. **Structure:** on the diagram, trace `Client` → `Bounded`, `Bounded` → `queue`, `queue` → `Result`.
4. **Refactor:** encode the boundary, then run `ProblemDemo` — match `upload a`.
5. **Break it:** flip one `ProblemTest` assertion, watch red, restore it.
6. **Refuse:** Contrast with **Thread Pool**: Bounded queue decouples rates; a pool caps concurrent workers.

## Practice Drill
- Clone `waqarys/java-design-patterns`, set `JAVA_HOME` to JDK 25.
- Run the episode `ProblemDemo` (see `EP_MAIN` in `tools/enrich/build_problems.py`).
- Mutate one collaborator to fail; confirm the observable compensation / error path.
- Explain Producer-Consumer for «Bounded queue between encoder and uploader» in sixty seconds without notes.
