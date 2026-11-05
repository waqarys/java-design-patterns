# Writer Monad

**Season:** 7 · Advanced FP · **Category:** Functional · **Level:** Advanced · **Runtime:** ~10–12 min · **Channel:** Build with Waqar

## Problem
**Compute totals while accumulating audit log**

Key types: `Writer`, `Audited`.

## Hook
Picture this: you are mid-demo and «Compute totals while accumulating audit log» blows up the naive way — you see `Failure path is asserted in ProblemTest (injected fault / invalid input)`. That is the pain. In the next ten minutes we introduce **Writer** with real types (Writer, Audited), a runnable `ProblemDemo`, and the failure path locked in by tests. No abstract UML first — problem first.

## Intent
Use **Writer** to cleanly solve: Compute totals while accumulating audit log.

**When to use:** the force behind «Compute totals while accumulating audit log» shows up more than once and a simpler language feature does not erase it.

**When to refuse:** you are pattern-collecting, the boundary is still unclear, or the team cannot yet test the abstraction (compensations, half-open recovery, interpreters).

## The Pain — Naive Design
Naive version fails to cleanly solve: Compute totals while accumulating audit log

```java
package com.buildwithwaqar.patterns.functional.writer.problem;

import java.util.*;

public final class NaiveAudit {
  public static int total(List<Integer> xs){ return xs.stream().mapToInt(i->i).sum(); }
}

```

On camera, point at the smell: missing compensations, leaked XML, null at the boundary,
illegal transitions, or hard-wired `System.out` that you cannot assert.

## Pattern Structure
```mermaid
flowchart LR
  Client[Client] --> Compute[Compute]
  Compute[Compute] --> totals[totals]
  totals[totals] --> Result[Result]
```

These roles are for **«Compute totals while accumulating audit log»** — not a generic Client→Pattern poster. Keep them on screen
while you type the solution.

## Refactor to the Pattern

### Stage 1 — Name the boundary
Introduce the client-facing API the rest of the app should depend on. Hide the incompatible or
distributed details behind it. For this episode that boundary is what makes «Compute totals while accumulating audit log» testable.

### Stage 2 — Encode the solution
```java
package com.buildwithwaqar.patterns.functional.writer.problem;

import java.util.*;
import java.util.function.*;

record Writer<W,A>(A value, List<W> log){
  static <W,A> Writer<W,A> pure(A a){ return new Writer<>(a, List.of()); }
  <B> Writer<W,B> map(Function<A,B> f){ return new Writer<>(f.apply(value), log); }
  <B> Writer<W,B> flatMap(Function<A, Writer<W,B>> f){
    var next=f.apply(value); List<W> merged=new ArrayList<>(log); merged.addAll(next.log); return new Writer<>(next.value, merged);
  }
  static <W,A> Writer<W,A> tell(W w, A a){ return new Writer<>(a, List.of(w)); }
}
final class Audited {
  static Writer<String,Integer> addLine(int cents){ return Writer.tell("line="+cents, cents); }
  static Writer<String,Integer> total(List<Integer> lines){
    Writer<String,Integer> acc=Writer.pure(0);
    for(int c: lines){ acc = acc.flatMap(sum -> addLine(c).map(v -> sum+v)); }
    return acc;
  }
}

```

### Stage 3 — Runnable main
```java
package com.buildwithwaqar.patterns.functional.writer.problem;
import java.util.*; import java.util.function.*;
public final class ProblemDemo {
  public static void main(String[] args) {
    System.out.println("=== Writer: totals with audit log ===");
    System.out.println("--- naive ---"); System.out.println(NaiveAudit.total(List.of(10,20)));
System.out.println("--- writer ---");
var w=Audited.total(List.of(10,20,30));
System.out.println("value="+w.value()); System.out.println("log="+w.log());
  }
}

```

## Runnable Output (Verified on JDK 25)
Narrate each line. Do not invent numbers on camera.

```text
=== Writer: totals with audit log ===
--- naive ---
30
--- writer ---
value=60
log=[line=10, line=20, line=30]
```

Happy-path cue: `30`.
Failure-path cue: `Failure path is asserted in ProblemTest (injected fault / invalid input)`.
Fork the repo, set `JAVA_HOME` to JDK 25, and re-run the same `ProblemDemo` to verify.

## Modern Java 25 / Zazr Variant
**Records** carry the immutable results (`StepResult`, DTOs) with almost no boilerplate.

## Trade-offs
- **Clarity vs ceremony:** extra types for «Compute totals while accumulating audit log» only pay off when the force repeats.
- **Testability:** inject collaborators; assert the failure path, not only the happy path.
- **Distributed / concurrent reality:** demos omit metrics, backpressure, and multi-region clocks.
- **Refuse Writer** when a sealed switch, a function value, or a simpler library call already erases the force.
- **Neighbor:** State — Writer accumulates a log; State threads an updatable value.

## Interview Q&A
**Q1. What concrete problem does Writer solve here?**
A: Compute totals while accumulating audit log.

**Q2. What does the naive design get wrong?**
A: Naive version fails to cleanly solve: Compute totals while accumulating audit log

**Q3. How do you test the failure path?**
A: Inject the fault and assert the observable outcome — e.g. `Failure path is asserted in ProblemTest (injected fault / invalid input)`.

**Q4. When is Writer the wrong tool?**
A: When a smaller language feature erases the force, or when you really needed State.


## Key Takeaways
- Lead with the problem: Compute totals while accumulating audit log.
- Show naive pain (`Failure path is asserted in ProblemTest (injected fault / invalid input)`) before naming Writer.
- Run the Demo; trust JDK 25 stdout (`30`).
- Teach when NOT to use Writer.

## Video Script (≈10–12 minutes)
Record for **Build with Waqar**. OBS for capture, CapCut for assembly. Import `slides.pdf`.
Facecam: full-screen for the hook, framed PiP over code slides.

**[0:00]** *(slide 01 title + talking head)*
Hey everyone — today we solve one concrete problem with **Writer**:
*Compute totals while accumulating audit log*. Keep these names in your head: Writer, Audited.

**[0:45]** *(problem + hook slides)*
I am not starting with a textbook definition. Here is the product pain. By the end you will have
seen the naive leak, the pattern boundary, and real JDK 25 stdout.

**[2:00]** *(naive code)*
IDE time. This is the naive version. Call out the smell — that is what Writer exists to erase.

**[3:30]** *(structure diagram)*
Roles for **this** problem, not a generic UML poster. Point at each box while you talk.

**[4:30]** *(refactor / solution code)*
Stage the solution. Highlight failure injection and the tests that lock it in.

**[7:00]** *(walkthrough output)*
Real stdout. Happy path cue: `30`.
Failure cue: `Failure path is asserted in ProblemTest (injected fault / invalid input)`.
No invented numbers on camera.

**[8:30]** *(modern Java 25 / Zazr + trade-offs)*
Show the modernization, then slow down for when NOT to use Writer. That is what seniors listen for.

**[10:30]** *(Q&A + recap + outro)*
Two interview questions, three takeaways, link the GitHub episode folder. I am Waqar — see you in the next one.


## Walkthrough Beats (Say These Out Loud)
1. **Setup:** open `series/55-writer/` and `run-output.txt` beside the IDE.
2. **Naive:** reproduce the pain until you can point at `Failure path is asserted in ProblemTest (injected fault / invalid input)`.
3. **Structure:** on the diagram, trace `Client` → `Compute`, `Compute` → `totals`, `totals` → `Result`.
4. **Refactor:** encode the boundary, then run `ProblemDemo` — match `30`.
5. **Break it:** flip one `ProblemTest` assertion, watch red, restore it.
6. **Refuse:** Contrast with **State**: Writer accumulates a log; State threads an updatable value.

## Practice Drill
- Clone `waqarys/java-design-patterns`, set `JAVA_HOME` to JDK 25.
- Run the episode `ProblemDemo` (see `EP_MAIN` in `tools/enrich/build_problems.py`).
- Mutate one collaborator to fail; confirm the observable compensation / error path.
- Explain Writer for «Compute totals while accumulating audit log» in sixty seconds without notes.
