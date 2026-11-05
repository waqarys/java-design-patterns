# Retry

**Season:** 4 · Enterprise · **Category:** Enterprise · **Level:** Intermediate · **Runtime:** ~10–12 min · **Channel:** Build with Waqar

## Problem
**Publish event despite transient broker errors**

Key types: `Retry`.

## Hook
Picture this: you are mid-demo and «Publish event despite transient broker errors» blows up the naive way — you see `caught=broker`. That is the pain. In the next ten minutes we introduce **Retry** with real types (Retry), a runnable `ProblemDemo`, and the failure path locked in by tests. No abstract UML first — problem first.

## Intent
Use **Retry** to cleanly solve: Publish event despite transient broker errors.

**When to use:** the force behind «Publish event despite transient broker errors» shows up more than once and a simpler language feature does not erase it.

**When to refuse:** you are pattern-collecting, the boundary is still unclear, or the team cannot yet test the abstraction (compensations, half-open recovery, interpreters).

## The Pain — Naive Design
Naive version fails to cleanly solve: Publish event despite transient broker errors

```java
package com.buildwithwaqar.patterns.enterprise.retry.problem;

import java.util.function.Supplier;

public final class NaivePublish {
  public static String once(Supplier<String> s){ return s.get(); }
}

```

On camera, point at the smell: missing compensations, leaked XML, null at the boundary,
illegal transitions, or hard-wired `System.out` that you cannot assert.

## Pattern Structure
```mermaid
flowchart LR
  Client[Client] --> Publish[Publish]
  Publish[Publish] --> event[event]
  event[event] --> Result[Result]
```

These roles are for **«Publish event despite transient broker errors»** — not a generic Client→Pattern poster. Keep them on screen
while you type the solution.

## Refactor to the Pattern

### Stage 1 — Name the boundary
Introduce the client-facing API the rest of the app should depend on. Hide the incompatible or
distributed details behind it. For this episode that boundary is what makes «Publish event despite transient broker errors» testable.

### Stage 2 — Encode the solution
```java
package com.buildwithwaqar.patterns.enterprise.retry.problem;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.util.concurrent.locks.*;
import java.util.function.*;

final class Retry {
  static String call(int maxAttempts, Supplier<String> action){
    RuntimeException last=null;
    for (int i=1;i<=maxAttempts;i++){
      try { return action.get(); } catch (RuntimeException e){ last=e; }
    }
    throw last;
  }
}

```

### Stage 3 — Runnable main
```java
package com.buildwithwaqar.patterns.enterprise.retry.problem;

import java.util.concurrent.atomic.AtomicInteger;

public final class ProblemDemo {
  public static void main(String[] args) {
    System.out.println("=== Retry: transient broker errors ===");
    System.out.println("--- naive ---");
    try { NaivePublish.once(() -> { throw new RuntimeException("broker"); }); } catch(Exception e){ System.out.println("caught="+e.getMessage()); }
    System.out.println("--- retry ---");
    AtomicInteger n=new AtomicInteger();
    System.out.println(Retry.call(3, () -> { if(n.incrementAndGet()<3) throw new RuntimeException("broker"); return "published"; }));
    try { Retry.call(2, () -> { throw new RuntimeException("still"); }); } catch(RuntimeException e){ System.out.println("caught="+e.getMessage()); }
  }
}

```

## Runnable Output (Verified on JDK 25)
Narrate each line. Do not invent numbers on camera.

```text
=== Retry: transient broker errors ===
--- naive ---
caught=broker
--- retry ---
published
caught=still
```

Happy-path cue: `caught=broker`.
Failure-path cue: `caught=broker`.
Fork the repo, set `JAVA_HOME` to JDK 25, and re-run the same `ProblemDemo` to verify.

## Modern Java 25 / Zazr Variant
For **Retry**, prefer records for results, sealed types for closed variants, and Zazr `Option`/`Either`/`Validation` when the force is absence or accumulated errors. Keep the classic shape when interview literacy matters.

## Trade-offs
- **Clarity vs ceremony:** extra types for «Publish event despite transient broker errors» only pay off when the force repeats.
- **Testability:** inject collaborators; assert the failure path, not only the happy path.
- **Distributed / concurrent reality:** demos omit metrics, backpressure, and multi-region clocks.
- **Refuse Retry** when a sealed switch, a function value, or a simpler library call already erases the force.
- **Neighbor:** Circuit Breaker — Retry handles blips; open the breaker when the dependency is down.

## Interview Q&A
**Q1. What concrete problem does Retry solve here?**
A: Publish event despite transient broker errors.

**Q2. What does the naive design get wrong?**
A: Naive version fails to cleanly solve: Publish event despite transient broker errors

**Q3. How do you test the failure path?**
A: Inject the fault and assert the observable outcome — e.g. `caught=broker`.

**Q4. When is Retry the wrong tool?**
A: When a smaller language feature erases the force, or when you really needed Circuit Breaker.


## Key Takeaways
- Lead with the problem: Publish event despite transient broker errors.
- Show naive pain (`caught=broker`) before naming Retry.
- Run the Demo; trust JDK 25 stdout (`caught=broker`).
- Teach when NOT to use Retry.

## Video Script (≈10–12 minutes)
Record for **Build with Waqar**. OBS for capture, CapCut for assembly. Import `slides.pdf`.
Facecam: full-screen for the hook, framed PiP over code slides.

**[0:00]** *(slide 01 title + talking head)*
Hey everyone — today we solve one concrete problem with **Retry**:
*Publish event despite transient broker errors*. Keep these names in your head: Retry.

**[0:45]** *(problem + hook slides)*
I am not starting with a textbook definition. Here is the product pain. By the end you will have
seen the naive leak, the pattern boundary, and real JDK 25 stdout.

**[2:00]** *(naive code)*
IDE time. This is the naive version. Call out the smell — that is what Retry exists to erase.

**[3:30]** *(structure diagram)*
Roles for **this** problem, not a generic UML poster. Point at each box while you talk.

**[4:30]** *(refactor / solution code)*
Stage the solution. Highlight failure injection and the tests that lock it in.

**[7:00]** *(walkthrough output)*
Real stdout. Happy path cue: `caught=broker`.
Failure cue: `caught=broker`.
No invented numbers on camera.

**[8:30]** *(modern Java 25 / Zazr + trade-offs)*
Show the modernization, then slow down for when NOT to use Retry. That is what seniors listen for.

**[10:30]** *(Q&A + recap + outro)*
Two interview questions, three takeaways, link the GitHub episode folder. I am Waqar — see you in the next one.


## Walkthrough Beats (Say These Out Loud)
1. **Setup:** open `series/31-retry/` and `run-output.txt` beside the IDE.
2. **Naive:** reproduce the pain until you can point at `caught=broker`.
3. **Structure:** on the diagram, trace `Client` → `Publish`, `Publish` → `event`, `event` → `Result`.
4. **Refactor:** encode the boundary, then run `ProblemDemo` — match `caught=broker`.
5. **Break it:** flip one `ProblemTest` assertion, watch red, restore it.
6. **Refuse:** Contrast with **Circuit Breaker**: Retry handles blips; open the breaker when the dependency is down.

## Practice Drill
- Clone `waqarys/java-design-patterns`, set `JAVA_HOME` to JDK 25.
- Run the episode `ProblemDemo` (see `EP_MAIN` in `tools/enrich/build_problems.py`).
- Mutate one collaborator to fail; confirm the observable compensation / error path.
- Explain Retry for «Publish event despite transient broker errors» in sixty seconds without notes.
