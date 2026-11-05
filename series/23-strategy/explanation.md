# Strategy

**Season:** 3 · Behavioral · **Category:** GoF · **Level:** Beginner · **Runtime:** ~10–12 min · **Channel:** Build with Waqar

## Problem
**Swap cart pricing (percent vs flat vs half)**

Key types: `Pricing`, `PercentOff`, `FlatOff`, `HalfOff`, `Cart`.

## Hook
Picture this: you are mid-demo and «Swap cart pricing (percent vs flat vs half)» blows up the naive way — you see `Failure path is asserted in ProblemTest (injected fault / invalid input)`. That is the pain. In the next ten minutes we introduce **Strategy** with real types (Pricing, PercentOff, FlatOff), a runnable `ProblemDemo`, and the failure path locked in by tests. No abstract UML first — problem first.

## Intent
Swap cart pricing (percent / flat / half) without an if-else forest.

**When to use:** the force behind «Swap cart pricing (percent vs flat vs half)» shows up more than once and a simpler language feature does not erase it.

**When to refuse:** you are pattern-collecting, the boundary is still unclear, or the team cannot yet test the abstraction (compensations, half-open recovery, interpreters).

## The Pain — Naive Design
Naive version fails to cleanly solve: Swap cart pricing (percent vs flat vs half)

```java
package com.buildwithwaqar.patterns.gof.behavioral.strategy.problem;

import java.util.*;

public final class NaiveCart {
  public static int total(int cents, String mode){
    if("pct".equals(mode)) return cents*90/100;
    if("flat".equals(mode)) return Math.max(0,cents-200);
    return cents/2;
  }
}

```

On camera, point at the smell: missing compensations, leaked XML, null at the boundary,
illegal transitions, or hard-wired `System.out` that you cannot assert.

## Pattern Structure
```mermaid
flowchart LR
  Client[Client] --> Swap[Swap]
  Swap[Swap] --> cart[cart]
  cart[cart] --> Result[Result]
```

These roles are for **«Swap cart pricing (percent vs flat vs half)»** — not a generic Client→Pattern poster. Keep them on screen
while you type the solution.

## Refactor to the Pattern

### Stage 1 — Name the boundary
Introduce the client-facing API the rest of the app should depend on. Hide the incompatible or
distributed details behind it. For this episode that boundary is what makes «Swap cart pricing (percent vs flat vs half)» testable.

### Stage 2 — Encode the solution
```java
package com.buildwithwaqar.patterns.gof.behavioral.strategy.problem;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.util.function.*;

interface Pricing { int apply(int cents); }
final class PercentOff implements Pricing { private final int p; PercentOff(int p){this.p=p;} public int apply(int c){ return c*(100-p)/100; } }
final class FlatOff implements Pricing { private final int f; FlatOff(int f){this.f=f;} public int apply(int c){ return Math.max(0,c-f); } }
final class HalfOff implements Pricing { public int apply(int c){ return c/2; } }
final class Cart {
  private final Pricing pricing; Cart(Pricing p){ pricing=p; }
  int total(int cents){ return pricing.apply(cents); }
}

```

### Stage 3 — Runnable main
```java
package com.buildwithwaqar.patterns.gof.behavioral.strategy.problem;
import java.util.*; import java.util.concurrent.*;
public final class ProblemDemo {
  public static void main(String[] args) throws Exception {
    System.out.println("=== Strategy: cart pricing ===");
    System.out.println("--- naive ---"); System.out.println(NaiveCart.total(1000,"pct"));
System.out.println("--- strategy ---");
System.out.println(new Cart(new PercentOff(10)).total(1000));
System.out.println(new Cart(new FlatOff(200)).total(1000));
System.out.println(new Cart(new HalfOff()).total(1000));
  }
}

```

## Runnable Output (Verified on JDK 25)
Narrate each line. Do not invent numbers on camera.

```text
=== Strategy: cart pricing ===
--- naive ---
900
--- strategy ---
900
800
500
```

Happy-path cue: `900`.
Failure-path cue: `Failure path is asserted in ProblemTest (injected fault / invalid input)`.
Fork the repo, set `JAVA_HOME` to JDK 25, and re-run the same `ProblemDemo` to verify.

## Modern Java 25 / Zazr Variant
For **Strategy**, prefer records for results, sealed types for closed variants, and Zazr `Option`/`Either`/`Validation` when the force is absence or accumulated errors. Keep the classic shape when interview literacy matters.

## Trade-offs
- **Clarity vs ceremony:** extra types for «Swap cart pricing (percent vs flat vs half)» only pay off when the force repeats.
- **Testability:** inject collaborators; assert the failure path, not only the happy path.
- **Distributed / concurrent reality:** demos omit metrics, backpressure, and multi-region clocks.
- **Refuse Strategy** when a sealed switch, a function value, or a simpler library call already erases the force.
- **Neighbor:** State — Strategy is injected; State owns the next transition.

## Interview Q&A
**Q1. What concrete problem does Strategy solve here?**
A: Swap cart pricing (percent vs flat vs half).

**Q2. What does the naive design get wrong?**
A: Naive version fails to cleanly solve: Swap cart pricing (percent vs flat vs half)

**Q3. How do you test the failure path?**
A: Inject the fault and assert the observable outcome — e.g. `Failure path is asserted in ProblemTest (injected fault / invalid input)`.

**Q4. When is Strategy the wrong tool?**
A: When a smaller language feature erases the force, or when you really needed State.


## Key Takeaways
- Lead with the problem: Swap cart pricing (percent vs flat vs half).
- Show naive pain (`Failure path is asserted in ProblemTest (injected fault / invalid input)`) before naming Strategy.
- Run the Demo; trust JDK 25 stdout (`900`).
- Teach when NOT to use Strategy.

## Video Script (≈10–12 minutes)
Record for **Build with Waqar**. OBS for capture, CapCut for assembly. Import `slides.pdf`.
Facecam: full-screen for the hook, framed PiP over code slides.

**[0:00]** *(slide 01 title + talking head)*
Hey everyone — today we solve one concrete problem with **Strategy**:
*Swap cart pricing (percent vs flat vs half)*. Keep these names in your head: Pricing, PercentOff, FlatOff, HalfOff.

**[0:45]** *(problem + hook slides)*
I am not starting with a textbook definition. Here is the product pain. By the end you will have
seen the naive leak, the pattern boundary, and real JDK 25 stdout.

**[2:00]** *(naive code)*
IDE time. This is the naive version. Call out the smell — that is what Strategy exists to erase.

**[3:30]** *(structure diagram)*
Roles for **this** problem, not a generic UML poster. Point at each box while you talk.

**[4:30]** *(refactor / solution code)*
Stage the solution. Highlight failure injection and the tests that lock it in.

**[7:00]** *(walkthrough output)*
Real stdout. Happy path cue: `900`.
Failure cue: `Failure path is asserted in ProblemTest (injected fault / invalid input)`.
No invented numbers on camera.

**[8:30]** *(modern Java 25 / Zazr + trade-offs)*
Show the modernization, then slow down for when NOT to use Strategy. That is what seniors listen for.

**[10:30]** *(Q&A + recap + outro)*
Two interview questions, three takeaways, link the GitHub episode folder. I am Waqar — see you in the next one.


## Walkthrough Beats (Say These Out Loud)
1. **Setup:** open `series/23-strategy/` and `run-output.txt` beside the IDE.
2. **Naive:** reproduce the pain until you can point at `Failure path is asserted in ProblemTest (injected fault / invalid input)`.
3. **Structure:** on the diagram, trace `Client` → `Swap`, `Swap` → `cart`, `cart` → `Result`.
4. **Refactor:** encode the boundary, then run `ProblemDemo` — match `900`.
5. **Break it:** flip one `ProblemTest` assertion, watch red, restore it.
6. **Refuse:** Contrast with **State**: Strategy is injected; State owns the next transition.

## Practice Drill
- Clone `waqarys/java-design-patterns`, set `JAVA_HOME` to JDK 25.
- Run the episode `ProblemDemo` (see `EP_MAIN` in `tools/enrich/build_problems.py`).
- Mutate one collaborator to fail; confirm the observable compensation / error path.
- Explain Strategy for «Swap cart pricing (percent vs flat vs half)» in sixty seconds without notes.
