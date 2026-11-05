# Currying, Partial Application & Composition

**Season:** 6 · FP Foundations · **Category:** Functional · **Level:** Intermediate · **Runtime:** ~10–12 min · **Channel:** Build with Waqar

## Problem
**Build price pipeline from small functions**

Key types: `Fns`.

## Hook
Picture this: you are mid-demo and «Build price pipeline from small functions» blows up the naive way — you see `Failure path is asserted in ProblemTest (injected fault / invalid input)`. That is the pain. In the next ten minutes we introduce **Currying/Composition** with real types (Fns), a runnable `ProblemDemo`, and the failure path locked in by tests. No abstract UML first — problem first.

## Intent
Use **Currying/Composition** to cleanly solve: Build price pipeline from small functions.

**When to use:** the force behind «Build price pipeline from small functions» shows up more than once and a simpler language feature does not erase it.

**When to refuse:** you are pattern-collecting, the boundary is still unclear, or the team cannot yet test the abstraction (compensations, half-open recovery, interpreters).

## The Pain — Naive Design
Naive version fails to cleanly solve: Build price pipeline from small functions

```java
package com.buildwithwaqar.patterns.functional.functions.problem;

import java.util.*;

public final class NaivePrice {
  public static int price(int base, int pct, int flat){ return Math.max(0, base*(100-pct)/100 - flat); }
}

```

On camera, point at the smell: missing compensations, leaked XML, null at the boundary,
illegal transitions, or hard-wired `System.out` that you cannot assert.

## Pattern Structure
```mermaid
flowchart LR
  Client[Client] --> Build[Build]
  Build[Build] --> price[price]
  price[price] --> Result[Result]
```

These roles are for **«Build price pipeline from small functions»** — not a generic Client→Pattern poster. Keep them on screen
while you type the solution.

## Refactor to the Pattern

### Stage 1 — Name the boundary
Introduce the client-facing API the rest of the app should depend on. Hide the incompatible or
distributed details behind it. For this episode that boundary is what makes «Build price pipeline from small functions» testable.

### Stage 2 — Encode the solution
```java
package com.buildwithwaqar.patterns.functional.functions.problem;

import java.util.*;
import java.util.function.*;

final class Fns {
  static Function<Integer, Function<Integer, Integer>> curryAdd(){ return a -> b -> a+b; }
  static <A,B,C> Function<A,C> compose(Function<B,C> g, Function<A,B> f){ return a -> g.apply(f.apply(a)); }
  static Function<Integer,Integer> pricePipeline(int pct, int flat){
    Function<Integer,Integer> percent = c -> c*(100-pct)/100;
    Function<Integer,Integer> flatOff = c -> Math.max(0, c-flat);
    return compose(flatOff, percent);
  }
}

```

### Stage 3 — Runnable main
```java
package com.buildwithwaqar.patterns.functional.functions.problem;
import java.util.*; import java.util.function.*;
public final class ProblemDemo {
  public static void main(String[] args) {
    System.out.println("=== Currying/Composition: price pipeline ===");
    System.out.println("--- naive ---"); System.out.println(NaivePrice.price(1000,10,50));
System.out.println("--- curry/compose ---");
System.out.println(Fns.curryAdd().apply(2).apply(3));
System.out.println(Fns.pricePipeline(10,50).apply(1000));
  }
}

```

## Runnable Output (Verified on JDK 25)
Narrate each line. Do not invent numbers on camera.

```text
=== Currying/Composition: price pipeline ===
--- naive ---
850
--- curry/compose ---
5
850
```

Happy-path cue: `850`.
Failure-path cue: `Failure path is asserted in ProblemTest (injected fault / invalid input)`.
Fork the repo, set `JAVA_HOME` to JDK 25, and re-run the same `ProblemDemo` to verify.

## Modern Java 25 / Zazr Variant
For **Currying/Composition**, prefer records for results, sealed types for closed variants, and Zazr `Option`/`Either`/`Validation` when the force is absence or accumulated errors. Keep the classic shape when interview literacy matters.

## Trade-offs
- **Clarity vs ceremony:** extra types for «Build price pipeline from small functions» only pay off when the force repeats.
- **Testability:** inject collaborators; assert the failure path, not only the happy path.
- **Distributed / concurrent reality:** demos omit metrics, backpressure, and multi-region clocks.
- **Refuse Currying/Composition** when a sealed switch, a function value, or a simpler library call already erases the force.
- **Neighbor:** name the pattern usually confused with Currying/Composition and separate the forces.

## Interview Q&A
**Q1. What concrete problem does Currying/Composition solve here?**
A: Build price pipeline from small functions.

**Q2. What does the naive design get wrong?**
A: Naive version fails to cleanly solve: Build price pipeline from small functions

**Q3. How do you test the failure path?**
A: Inject the fault and assert the observable outcome — e.g. `Failure path is asserted in ProblemTest (injected fault / invalid input)`.

**Q4. When is Currying/Composition the wrong tool?**
A: When a smaller language feature erases the force, or when you really needed a neighboring pattern.


## Key Takeaways
- Lead with the problem: Build price pipeline from small functions.
- Show naive pain (`Failure path is asserted in ProblemTest (injected fault / invalid input)`) before naming Currying/Composition.
- Run the Demo; trust JDK 25 stdout (`850`).
- Teach when NOT to use Currying/Composition.

## Video Script (≈10–12 minutes)
Record for **Build with Waqar**. OBS for capture, CapCut for assembly. Import `slides.pdf`.
Facecam: full-screen for the hook, framed PiP over code slides.

**[0:00]** *(slide 01 title + talking head)*
Hey everyone — today we solve one concrete problem with **Currying/Composition**:
*Build price pipeline from small functions*. Keep these names in your head: Fns.

**[0:45]** *(problem + hook slides)*
I am not starting with a textbook definition. Here is the product pain. By the end you will have
seen the naive leak, the pattern boundary, and real JDK 25 stdout.

**[2:00]** *(naive code)*
IDE time. This is the naive version. Call out the smell — that is what Currying/Composition exists to erase.

**[3:30]** *(structure diagram)*
Roles for **this** problem, not a generic UML poster. Point at each box while you talk.

**[4:30]** *(refactor / solution code)*
Stage the solution. Highlight failure injection and the tests that lock it in.

**[7:00]** *(walkthrough output)*
Real stdout. Happy path cue: `850`.
Failure cue: `Failure path is asserted in ProblemTest (injected fault / invalid input)`.
No invented numbers on camera.

**[8:30]** *(modern Java 25 / Zazr + trade-offs)*
Show the modernization, then slow down for when NOT to use Currying/Composition. That is what seniors listen for.

**[10:30]** *(Q&A + recap + outro)*
Two interview questions, three takeaways, link the GitHub episode folder. I am Waqar — see you in the next one.


## Walkthrough Beats (Say These Out Loud)
1. **Setup:** open `series/50-currying-composition/` and `run-output.txt` beside the IDE.
2. **Naive:** reproduce the pain until you can point at `Failure path is asserted in ProblemTest (injected fault / invalid input)`.
3. **Structure:** on the diagram, trace `Client` → `Build`, `Build` → `price`, `price` → `Result`.
4. **Refactor:** encode the boundary, then run `ProblemDemo` — match `850`.
5. **Break it:** flip one `ProblemTest` assertion, watch red, restore it.
6. **Refuse:** Name the neighbor pattern usually confused with Currying/Composition.

## Practice Drill
- Clone `waqarys/java-design-patterns`, set `JAVA_HOME` to JDK 25.
- Run the episode `ProblemDemo` (see `EP_MAIN` in `tools/enrich/build_problems.py`).
- Mutate one collaborator to fail; confirm the observable compensation / error path.
- Explain Currying/Composition for «Build price pipeline from small functions» in sixty seconds without notes.
