# Monoid & Semigroup

**Season:** 6 · FP Foundations · **Category:** Functional · **Level:** Intermediate · **Runtime:** ~10–12 min · **Channel:** Build with Waqar

## Problem
**Fold invoice line totals / log strings**

Key types: `Monoid`, `Monoids`.

## Hook
Picture this: you are mid-demo and «Fold invoice line totals / log strings» blows up the naive way — you see `Failure path is asserted in ProblemTest (injected fault / invalid input)`. That is the pain. In the next ten minutes we introduce **Monoid** with real types (Monoid, Monoids), a runnable `ProblemDemo`, and the failure path locked in by tests. No abstract UML first — problem first.

## Intent
Use **Monoid** to cleanly solve: Fold invoice line totals / log strings.

**When to use:** the force behind «Fold invoice line totals / log strings» shows up more than once and a simpler language feature does not erase it.

**When to refuse:** you are pattern-collecting, the boundary is still unclear, or the team cannot yet test the abstraction (compensations, half-open recovery, interpreters).

## The Pain — Naive Design
Naive version fails to cleanly solve: Fold invoice line totals / log strings

```java
package com.buildwithwaqar.patterns.functional.monoid.problem;

import java.util.*;

public final class NaiveSum {
  public static int sum(List<Integer> xs){ int s=0; for(int x:xs)s+=x; return s; }
}

```

On camera, point at the smell: missing compensations, leaked XML, null at the boundary,
illegal transitions, or hard-wired `System.out` that you cannot assert.

## Pattern Structure
```mermaid
flowchart LR
  Client[Client] --> Fold[Fold]
  Fold[Fold] --> invoice[invoice]
  invoice[invoice] --> Result[Result]
```

These roles are for **«Fold invoice line totals / log strings»** — not a generic Client→Pattern poster. Keep them on screen
while you type the solution.

## Refactor to the Pattern

### Stage 1 — Name the boundary
Introduce the client-facing API the rest of the app should depend on. Hide the incompatible or
distributed details behind it. For this episode that boundary is what makes «Fold invoice line totals / log strings» testable.

### Stage 2 — Encode the solution
```java
package com.buildwithwaqar.patterns.functional.monoid.problem;

import java.util.*;
import java.util.function.*;

interface Monoid<A> { A empty(); A combine(A a, A b); }
final class Monoids {
  static final Monoid<Integer> INT_ADD = new Monoid<>(){ public Integer empty(){return 0;} public Integer combine(Integer a,Integer b){return a+b;} };
  static final Monoid<String> STRING = new Monoid<>(){ public String empty(){return "";} public String combine(String a,String b){return a+b;} };
  static <A> A fold(Monoid<A> m, List<A> xs){ A acc=m.empty(); for(A x: xs) acc=m.combine(acc,x); return acc; }
}

```

### Stage 3 — Runnable main
```java
package com.buildwithwaqar.patterns.functional.monoid.problem;
import java.util.*; import java.util.function.*;
public final class ProblemDemo {
  public static void main(String[] args) {
    System.out.println("=== Monoid: fold invoice totals ===");
    System.out.println("--- naive ---"); System.out.println(NaiveSum.sum(List.of(10,20,30)));
System.out.println("--- monoid ---");
System.out.println(Monoids.fold(Monoids.INT_ADD, List.of(10,20,30)));
System.out.println(Monoids.fold(Monoids.STRING, List.of("a","b","c")));
  }
}

```

## Runnable Output (Verified on JDK 25)
Narrate each line. Do not invent numbers on camera.

```text
=== Monoid: fold invoice totals ===
--- naive ---
60
--- monoid ---
60
abc
```

Happy-path cue: `60`.
Failure-path cue: `Failure path is asserted in ProblemTest (injected fault / invalid input)`.
Fork the repo, set `JAVA_HOME` to JDK 25, and re-run the same `ProblemDemo` to verify.

## Modern Java 25 / Zazr Variant
For **Monoid**, prefer records for results, sealed types for closed variants, and Zazr `Option`/`Either`/`Validation` when the force is absence or accumulated errors. Keep the classic shape when interview literacy matters.

## Trade-offs
- **Clarity vs ceremony:** extra types for «Fold invoice line totals / log strings» only pay off when the force repeats.
- **Testability:** inject collaborators; assert the failure path, not only the happy path.
- **Distributed / concurrent reality:** demos omit metrics, backpressure, and multi-region clocks.
- **Refuse Monoid** when a sealed switch, a function value, or a simpler library call already erases the force.
- **Neighbor:** Foldable — Monoid gives empty+combine; Foldable consumes them over a structure.

## Interview Q&A
**Q1. What concrete problem does Monoid solve here?**
A: Fold invoice line totals / log strings.

**Q2. What does the naive design get wrong?**
A: Naive version fails to cleanly solve: Fold invoice line totals / log strings

**Q3. How do you test the failure path?**
A: Inject the fault and assert the observable outcome — e.g. `Failure path is asserted in ProblemTest (injected fault / invalid input)`.

**Q4. When is Monoid the wrong tool?**
A: When a smaller language feature erases the force, or when you really needed Foldable.


## Key Takeaways
- Lead with the problem: Fold invoice line totals / log strings.
- Show naive pain (`Failure path is asserted in ProblemTest (injected fault / invalid input)`) before naming Monoid.
- Run the Demo; trust JDK 25 stdout (`60`).
- Teach when NOT to use Monoid.

## Video Script (≈10–12 minutes)
Record for **Build with Waqar**. OBS for capture, CapCut for assembly. Import `slides.pdf`.
Facecam: full-screen for the hook, framed PiP over code slides.

**[0:00]** *(slide 01 title + talking head)*
Hey everyone — today we solve one concrete problem with **Monoid**:
*Fold invoice line totals / log strings*. Keep these names in your head: Monoid, Monoids.

**[0:45]** *(problem + hook slides)*
I am not starting with a textbook definition. Here is the product pain. By the end you will have
seen the naive leak, the pattern boundary, and real JDK 25 stdout.

**[2:00]** *(naive code)*
IDE time. This is the naive version. Call out the smell — that is what Monoid exists to erase.

**[3:30]** *(structure diagram)*
Roles for **this** problem, not a generic UML poster. Point at each box while you talk.

**[4:30]** *(refactor / solution code)*
Stage the solution. Highlight failure injection and the tests that lock it in.

**[7:00]** *(walkthrough output)*
Real stdout. Happy path cue: `60`.
Failure cue: `Failure path is asserted in ProblemTest (injected fault / invalid input)`.
No invented numbers on camera.

**[8:30]** *(modern Java 25 / Zazr + trade-offs)*
Show the modernization, then slow down for when NOT to use Monoid. That is what seniors listen for.

**[10:30]** *(Q&A + recap + outro)*
Two interview questions, three takeaways, link the GitHub episode folder. I am Waqar — see you in the next one.


## Walkthrough Beats (Say These Out Loud)
1. **Setup:** open `series/48-monoid-semigroup/` and `run-output.txt` beside the IDE.
2. **Naive:** reproduce the pain until you can point at `Failure path is asserted in ProblemTest (injected fault / invalid input)`.
3. **Structure:** on the diagram, trace `Client` → `Fold`, `Fold` → `invoice`, `invoice` → `Result`.
4. **Refactor:** encode the boundary, then run `ProblemDemo` — match `60`.
5. **Break it:** flip one `ProblemTest` assertion, watch red, restore it.
6. **Refuse:** Contrast with **Foldable**: Monoid gives empty+combine; Foldable consumes them over a structure.

## Practice Drill
- Clone `waqarys/java-design-patterns`, set `JAVA_HOME` to JDK 25.
- Run the episode `ProblemDemo` (see `EP_MAIN` in `tools/enrich/build_problems.py`).
- Mutate one collaborator to fail; confirm the observable compensation / error path.
- Explain Monoid for «Fold invoice line totals / log strings» in sixty seconds without notes.
