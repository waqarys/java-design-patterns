# Read-Write Lock

**Season:** 5 · Concurrency · **Category:** Concurrency · **Level:** Intermediate · **Runtime:** ~10–12 min · **Channel:** Build with Waqar

## Problem
**Cache manifest with many readers**

Key types: `CachedManifest`.

## Hook
Picture this: you are mid-demo and «Cache manifest with many readers» blows up the naive way — you see `Failure path is asserted in ProblemTest (injected fault / invalid input)`. That is the pain. In the next ten minutes we introduce **Read-Write Lock** with real types (CachedManifest), a runnable `ProblemDemo`, and the failure path locked in by tests. No abstract UML first — problem first.

## Intent
Use **Read-Write Lock** to cleanly solve: Cache manifest with many readers.

**When to use:** the force behind «Cache manifest with many readers» shows up more than once and a simpler language feature does not erase it.

**When to refuse:** you are pattern-collecting, the boundary is still unclear, or the team cannot yet test the abstraction (compensations, half-open recovery, interpreters).

## The Pain — Naive Design
Naive version fails to cleanly solve: Cache manifest with many readers

```java
package com.buildwithwaqar.patterns.concurrency.readwritelock.problem;

import java.util.*;

public final class NaiveCache {
  private String v="v1"; public String read(){ return v; } public void write(String x){ v=x; }
}

```

On camera, point at the smell: missing compensations, leaked XML, null at the boundary,
illegal transitions, or hard-wired `System.out` that you cannot assert.

## Pattern Structure
```mermaid
flowchart LR
  Client[Client] --> Cache[Cache]
  Cache[Cache] --> manifest[manifest]
  manifest[manifest] --> Result[Result]
```

These roles are for **«Cache manifest with many readers»** — not a generic Client→Pattern poster. Keep them on screen
while you type the solution.

## Refactor to the Pattern

### Stage 1 — Name the boundary
Introduce the client-facing API the rest of the app should depend on. Hide the incompatible or
distributed details behind it. For this episode that boundary is what makes «Cache manifest with many readers» testable.

### Stage 2 — Encode the solution
```java
package com.buildwithwaqar.patterns.concurrency.readwritelock.problem;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.util.concurrent.locks.*;
import java.util.function.*;

final class CachedManifest {
  private final ReadWriteLock lock=new ReentrantReadWriteLock();
  private String value="v1";
  String read(){ lock.readLock().lock(); try { return value; } finally { lock.readLock().unlock(); } }
  void write(String v){ lock.writeLock().lock(); try { value=v; } finally { lock.writeLock().unlock(); } }
}

```

### Stage 3 — Runnable main
```java
package com.buildwithwaqar.patterns.concurrency.readwritelock.problem;
import java.util.*; import java.util.concurrent.*; import java.util.concurrent.atomic.*;
public final class ProblemDemo {
  public static void main(String[] args) throws Exception {
    System.out.println("=== Read-Write Lock: manifest cache ===");
    System.out.println("--- naive ---"); var n=new NaiveCache(); System.out.println(n.read()); n.write("v2"); System.out.println(n.read());
System.out.println("--- rw lock ---");
var c=new CachedManifest();
ExecutorService ex=Executors.newFixedThreadPool(4);
List<Future<String>> reads=new ArrayList<>();
for(int i=0;i<10;i++) reads.add(ex.submit(c::read));
c.write("v2");
System.out.println("final="+c.read());
ex.shutdown();
  }
}

```

## Runnable Output (Verified on JDK 25)
Narrate each line. Do not invent numbers on camera.

```text
=== Read-Write Lock: manifest cache ===
--- naive ---
v1
v2
--- rw lock ---
final=v2
```

Happy-path cue: `v1`.
Failure-path cue: `Failure path is asserted in ProblemTest (injected fault / invalid input)`.
Fork the repo, set `JAVA_HOME` to JDK 25, and re-run the same `ProblemDemo` to verify.

## Modern Java 25 / Zazr Variant
For **Read-Write Lock**, prefer records for results, sealed types for closed variants, and Zazr `Option`/`Either`/`Validation` when the force is absence or accumulated errors. Keep the classic shape when interview literacy matters.

## Trade-offs
- **Clarity vs ceremony:** extra types for «Cache manifest with many readers» only pay off when the force repeats.
- **Testability:** inject collaborators; assert the failure path, not only the happy path.
- **Distributed / concurrent reality:** demos omit metrics, backpressure, and multi-region clocks.
- **Refuse Read-Write Lock** when a sealed switch, a function value, or a simpler library call already erases the force.
- **Neighbor:** Synchronized — RW lock allows concurrent readers; prefer it when reads dominate.

## Interview Q&A
**Q1. What concrete problem does Read-Write Lock solve here?**
A: Cache manifest with many readers.

**Q2. What does the naive design get wrong?**
A: Naive version fails to cleanly solve: Cache manifest with many readers

**Q3. How do you test the failure path?**
A: Inject the fault and assert the observable outcome — e.g. `Failure path is asserted in ProblemTest (injected fault / invalid input)`.

**Q4. When is Read-Write Lock the wrong tool?**
A: When a smaller language feature erases the force, or when you really needed Synchronized.


## Key Takeaways
- Lead with the problem: Cache manifest with many readers.
- Show naive pain (`Failure path is asserted in ProblemTest (injected fault / invalid input)`) before naming Read-Write Lock.
- Run the Demo; trust JDK 25 stdout (`v1`).
- Teach when NOT to use Read-Write Lock.

## Video Script (≈10–12 minutes)
Record for **Build with Waqar**. OBS for capture, CapCut for assembly. Import `slides.pdf`.
Facecam: full-screen for the hook, framed PiP over code slides.

**[0:00]** *(slide 01 title + talking head)*
Hey everyone — today we solve one concrete problem with **Read-Write Lock**:
*Cache manifest with many readers*. Keep these names in your head: CachedManifest.

**[0:45]** *(problem + hook slides)*
I am not starting with a textbook definition. Here is the product pain. By the end you will have
seen the naive leak, the pattern boundary, and real JDK 25 stdout.

**[2:00]** *(naive code)*
IDE time. This is the naive version. Call out the smell — that is what Read-Write Lock exists to erase.

**[3:30]** *(structure diagram)*
Roles for **this** problem, not a generic UML poster. Point at each box while you talk.

**[4:30]** *(refactor / solution code)*
Stage the solution. Highlight failure injection and the tests that lock it in.

**[7:00]** *(walkthrough output)*
Real stdout. Happy path cue: `v1`.
Failure cue: `Failure path is asserted in ProblemTest (injected fault / invalid input)`.
No invented numbers on camera.

**[8:30]** *(modern Java 25 / Zazr + trade-offs)*
Show the modernization, then slow down for when NOT to use Read-Write Lock. That is what seniors listen for.

**[10:30]** *(Q&A + recap + outro)*
Two interview questions, three takeaways, link the GitHub episode folder. I am Waqar — see you in the next one.


## Walkthrough Beats (Say These Out Loud)
1. **Setup:** open `series/37-read-write-lock/` and `run-output.txt` beside the IDE.
2. **Naive:** reproduce the pain until you can point at `Failure path is asserted in ProblemTest (injected fault / invalid input)`.
3. **Structure:** on the diagram, trace `Client` → `Cache`, `Cache` → `manifest`, `manifest` → `Result`.
4. **Refactor:** encode the boundary, then run `ProblemDemo` — match `v1`.
5. **Break it:** flip one `ProblemTest` assertion, watch red, restore it.
6. **Refuse:** Contrast with **Synchronized**: RW lock allows concurrent readers; prefer it when reads dominate.

## Practice Drill
- Clone `waqarys/java-design-patterns`, set `JAVA_HOME` to JDK 25.
- Run the episode `ProblemDemo` (see `EP_MAIN` in `tools/enrich/build_problems.py`).
- Mutate one collaborator to fail; confirm the observable compensation / error path.
- Explain Read-Write Lock for «Cache manifest with many readers» in sixty seconds without notes.
