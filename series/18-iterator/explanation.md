# Iterator

**Season:** 3 · Behavioral · **Category:** GoF · **Level:** Beginner · **Runtime:** ~10–12 min · **Channel:** Build with Waqar

## Problem
**Round-robin assign jobs to workers**

Key types: `RoundRobin`.

## Hook
Picture this: you are mid-demo and «Round-robin assign jobs to workers» blows up the naive way — you see `Failure path is asserted in ProblemTest (injected fault / invalid input)`. That is the pain. In the next ten minutes we introduce **Iterator** with real types (RoundRobin), a runnable `ProblemDemo`, and the failure path locked in by tests. No abstract UML first — problem first.

## Intent
Use **Iterator** to cleanly solve: Round-robin assign jobs to workers.

**When to use:** the force behind «Round-robin assign jobs to workers» shows up more than once and a simpler language feature does not erase it.

**When to refuse:** you are pattern-collecting, the boundary is still unclear, or the team cannot yet test the abstraction (compensations, half-open recovery, interpreters).

## The Pain — Naive Design
Naive version fails to cleanly solve: Round-robin assign jobs to workers

```java
package com.buildwithwaqar.patterns.gof.behavioral.iterator.problem;

import java.util.*;

public final class NaiveAssign {
  private int i; private final List<String> w;
  public NaiveAssign(List<String> w){ this.w=w; }
  public String next(){ return w.get(i++ % w.size()); }
}

```

On camera, point at the smell: missing compensations, leaked XML, null at the boundary,
illegal transitions, or hard-wired `System.out` that you cannot assert.

## Pattern Structure
```mermaid
flowchart LR
  Client[Client] --> Round[Round]
  Round[Round] --> robin[robin]
  robin[robin] --> Result[Result]
```

These roles are for **«Round-robin assign jobs to workers»** — not a generic Client→Pattern poster. Keep them on screen
while you type the solution.

## Refactor to the Pattern

### Stage 1 — Name the boundary
Introduce the client-facing API the rest of the app should depend on. Hide the incompatible or
distributed details behind it. For this episode that boundary is what makes «Round-robin assign jobs to workers» testable.

### Stage 2 — Encode the solution
```java
package com.buildwithwaqar.patterns.gof.behavioral.iterator.problem;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.util.function.*;

final class RoundRobin implements Iterable<String> {
  private final List<String> workers; private int i;
  RoundRobin(List<String> workers){ this.workers=List.copyOf(workers); }
  public Iterator<String> iterator(){
    return new Iterator<>(){
      public boolean hasNext(){ return !workers.isEmpty(); }
      public String next(){ if(workers.isEmpty()) throw new NoSuchElementException();
        String w=workers.get(i % workers.size()); i++; return w; }
    };
  }
}

```

### Stage 3 — Runnable main
```java
package com.buildwithwaqar.patterns.gof.behavioral.iterator.problem;
import java.util.*; import java.util.concurrent.*;
public final class ProblemDemo {
  public static void main(String[] args) throws Exception {
    System.out.println("=== Iterator: round-robin workers ===");
    System.out.println("--- naive ---");
var n=new NaiveAssign(List.of("w1","w2")); System.out.println(n.next()+" "+n.next()+" "+n.next());
System.out.println("--- iterator ---");
var it=new RoundRobin(List.of("w1","w2")).iterator();
System.out.println(it.next()+" "+it.next()+" "+it.next());
  }
}

```

## Runnable Output (Verified on JDK 25)
Narrate each line. Do not invent numbers on camera.

```text
=== Iterator: round-robin workers ===
--- naive ---
w1 w2 w1
--- iterator ---
w1 w2 w1
```

Happy-path cue: `w1 w2 w1`.
Failure-path cue: `Failure path is asserted in ProblemTest (injected fault / invalid input)`.
Fork the repo, set `JAVA_HOME` to JDK 25, and re-run the same `ProblemDemo` to verify.

## Modern Java 25 / Zazr Variant
For **Iterator**, prefer records for results, sealed types for closed variants, and Zazr `Option`/`Either`/`Validation` when the force is absence or accumulated errors. Keep the classic shape when interview literacy matters.

## Trade-offs
- **Clarity vs ceremony:** extra types for «Round-robin assign jobs to workers» only pay off when the force repeats.
- **Testability:** inject collaborators; assert the failure path, not only the happy path.
- **Distributed / concurrent reality:** demos omit metrics, backpressure, and multi-region clocks.
- **Refuse Iterator** when a sealed switch, a function value, or a simpler library call already erases the force.
- **Neighbor:** Visitor — Iterator walks elements; Visitor externalizes operations on structure.

## Interview Q&A
**Q1. What concrete problem does Iterator solve here?**
A: Round-robin assign jobs to workers.

**Q2. What does the naive design get wrong?**
A: Naive version fails to cleanly solve: Round-robin assign jobs to workers

**Q3. How do you test the failure path?**
A: Inject the fault and assert the observable outcome — e.g. `Failure path is asserted in ProblemTest (injected fault / invalid input)`.

**Q4. When is Iterator the wrong tool?**
A: When a smaller language feature erases the force, or when you really needed Visitor.


## Key Takeaways
- Lead with the problem: Round-robin assign jobs to workers.
- Show naive pain (`Failure path is asserted in ProblemTest (injected fault / invalid input)`) before naming Iterator.
- Run the Demo; trust JDK 25 stdout (`w1 w2 w1`).
- Teach when NOT to use Iterator.

## Video Script (≈10–12 minutes)
Record for **Build with Waqar**. OBS for capture, CapCut for assembly. Import `slides.pdf`.
Facecam: full-screen for the hook, framed PiP over code slides.

**[0:00]** *(slide 01 title + talking head)*
Hey everyone — today we solve one concrete problem with **Iterator**:
*Round-robin assign jobs to workers*. Keep these names in your head: RoundRobin.

**[0:45]** *(problem + hook slides)*
I am not starting with a textbook definition. Here is the product pain. By the end you will have
seen the naive leak, the pattern boundary, and real JDK 25 stdout.

**[2:00]** *(naive code)*
IDE time. This is the naive version. Call out the smell — that is what Iterator exists to erase.

**[3:30]** *(structure diagram)*
Roles for **this** problem, not a generic UML poster. Point at each box while you talk.

**[4:30]** *(refactor / solution code)*
Stage the solution. Highlight failure injection and the tests that lock it in.

**[7:00]** *(walkthrough output)*
Real stdout. Happy path cue: `w1 w2 w1`.
Failure cue: `Failure path is asserted in ProblemTest (injected fault / invalid input)`.
No invented numbers on camera.

**[8:30]** *(modern Java 25 / Zazr + trade-offs)*
Show the modernization, then slow down for when NOT to use Iterator. That is what seniors listen for.

**[10:30]** *(Q&A + recap + outro)*
Two interview questions, three takeaways, link the GitHub episode folder. I am Waqar — see you in the next one.


## Walkthrough Beats (Say These Out Loud)
1. **Setup:** open `series/18-iterator/` and `run-output.txt` beside the IDE.
2. **Naive:** reproduce the pain until you can point at `Failure path is asserted in ProblemTest (injected fault / invalid input)`.
3. **Structure:** on the diagram, trace `Client` → `Round`, `Round` → `robin`, `robin` → `Result`.
4. **Refactor:** encode the boundary, then run `ProblemDemo` — match `w1 w2 w1`.
5. **Break it:** flip one `ProblemTest` assertion, watch red, restore it.
6. **Refuse:** Contrast with **Visitor**: Iterator walks elements; Visitor externalizes operations on structure.

## Practice Drill
- Clone `waqarys/java-design-patterns`, set `JAVA_HOME` to JDK 25.
- Run the episode `ProblemDemo` (see `EP_MAIN` in `tools/enrich/build_problems.py`).
- Mutate one collaborator to fail; confirm the observable compensation / error path.
- Explain Iterator for «Round-robin assign jobs to workers» in sixty seconds without notes.
