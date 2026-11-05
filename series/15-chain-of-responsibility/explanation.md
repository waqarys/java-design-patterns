# Chain of Responsibility

**Season:** 3 · Behavioral · **Category:** GoF · **Level:** Beginner · **Runtime:** ~10–12 min · **Channel:** Build with Waqar

## Problem
**Route support tickets billing→tech→general**

Key types: `Handler`, `BillingHandler`, `TechHandler`, `GeneralHandler`.

## Hook
Picture this: you are mid-demo and «Route support tickets billing→tech→general» blows up the naive way — you see `Failure path is asserted in ProblemTest (injected fault / invalid input)`. That is the pain. In the next ten minutes we introduce **Chain** with real types (Handler, BillingHandler, TechHandler), a runnable `ProblemDemo`, and the failure path locked in by tests. No abstract UML first — problem first.

## Intent
Use **Chain** to cleanly solve: Route support tickets billing→tech→general.

**When to use:** the force behind «Route support tickets billing→tech→general» shows up more than once and a simpler language feature does not erase it.

**When to refuse:** you are pattern-collecting, the boundary is still unclear, or the team cannot yet test the abstraction (compensations, half-open recovery, interpreters).

## The Pain — Naive Design
Naive version fails to cleanly solve: Route support tickets billing→tech→general

```java
package com.buildwithwaqar.patterns.gof.behavioral.chain.problem;

import java.util.*;

public final class NaiveRouter {
  public static String route(String type){
    if("billing".equals(type)) return "billing-desk";
    if("tech".equals(type)) return "tech-desk";
    return "general-desk";
  }
}

```

On camera, point at the smell: missing compensations, leaked XML, null at the boundary,
illegal transitions, or hard-wired `System.out` that you cannot assert.

## Pattern Structure
```mermaid
flowchart LR
  Client[Client] --> Route[Route]
  Route[Route] --> support[support]
  support[support] --> Result[Result]
```

These roles are for **«Route support tickets billing→tech→general»** — not a generic Client→Pattern poster. Keep them on screen
while you type the solution.

## Refactor to the Pattern

### Stage 1 — Name the boundary
Introduce the client-facing API the rest of the app should depend on. Hide the incompatible or
distributed details behind it. For this episode that boundary is what makes «Route support tickets billing→tech→general» testable.

### Stage 2 — Encode the solution
```java
package com.buildwithwaqar.patterns.gof.behavioral.chain.problem;

import java.util.*;
import java.util.concurrent.*;
import java.util.function.*;

abstract class Handler {
  private Handler next;
  Handler link(Handler n){ next=n; return n; }
  final String handle(String type){
    String r = doHandle(type);
    if (r!=null) return r;
    return next==null? "unhandled": next.handle(type);
  }
  abstract String doHandle(String type);
}
final class BillingHandler extends Handler { String doHandle(String t){ return "billing".equals(t)? "billing-desk": null; } }
final class TechHandler extends Handler { String doHandle(String t){ return "tech".equals(t)? "tech-desk": null; } }
final class GeneralHandler extends Handler { String doHandle(String t){ return "general-desk"; } }

```

### Stage 3 — Runnable main
```java
package com.buildwithwaqar.patterns.gof.behavioral.chain.problem;

import java.util.*;
import java.util.concurrent.*;

public final class ProblemDemo {
  public static void main(String[] args) {
    System.out.println("=== Chain: support ticket routing ===");
    System.out.println("--- naive ---");
System.out.println(NaiveRouter.route("billing"));
System.out.println("--- chain ---");
var h=new BillingHandler(); h.link(new TechHandler()).link(new GeneralHandler());
System.out.println(h.handle("billing"));
System.out.println(h.handle("tech"));
System.out.println(h.handle("other"));
  }
}

```

## Runnable Output (Verified on JDK 25)
Narrate each line. Do not invent numbers on camera.

```text
=== Chain: support ticket routing ===
--- naive ---
billing-desk
--- chain ---
billing-desk
tech-desk
general-desk
```

Happy-path cue: `billing-desk`.
Failure-path cue: `Failure path is asserted in ProblemTest (injected fault / invalid input)`.
Fork the repo, set `JAVA_HOME` to JDK 25, and re-run the same `ProblemDemo` to verify.

## Modern Java 25 / Zazr Variant
For **Chain**, prefer records for results, sealed types for closed variants, and Zazr `Option`/`Either`/`Validation` when the force is absence or accumulated errors. Keep the classic shape when interview literacy matters.

## Trade-offs
- **Clarity vs ceremony:** extra types for «Route support tickets billing→tech→general» only pay off when the force repeats.
- **Testability:** inject collaborators; assert the failure path, not only the happy path.
- **Distributed / concurrent reality:** demos omit metrics, backpressure, and multi-region clocks.
- **Refuse Chain** when a sealed switch, a function value, or a simpler library call already erases the force.
- **Neighbor:** Decorator — Chain routes/handles or passes on; Decorator always wraps and adds.

## Interview Q&A
**Q1. What concrete problem does Chain solve here?**
A: Route support tickets billing→tech→general.

**Q2. What does the naive design get wrong?**
A: Naive version fails to cleanly solve: Route support tickets billing→tech→general

**Q3. How do you test the failure path?**
A: Inject the fault and assert the observable outcome — e.g. `Failure path is asserted in ProblemTest (injected fault / invalid input)`.

**Q4. When is Chain the wrong tool?**
A: When a smaller language feature erases the force, or when you really needed Decorator.


## Key Takeaways
- Lead with the problem: Route support tickets billing→tech→general.
- Show naive pain (`Failure path is asserted in ProblemTest (injected fault / invalid input)`) before naming Chain.
- Run the Demo; trust JDK 25 stdout (`billing-desk`).
- Teach when NOT to use Chain.

## Video Script (≈10–12 minutes)
Record for **Build with Waqar**. OBS for capture, CapCut for assembly. Import `slides.pdf`.
Facecam: full-screen for the hook, framed PiP over code slides.

**[0:00]** *(slide 01 title + talking head)*
Hey everyone — today we solve one concrete problem with **Chain**:
*Route support tickets billing→tech→general*. Keep these names in your head: Handler, BillingHandler, TechHandler, GeneralHandler.

**[0:45]** *(problem + hook slides)*
I am not starting with a textbook definition. Here is the product pain. By the end you will have
seen the naive leak, the pattern boundary, and real JDK 25 stdout.

**[2:00]** *(naive code)*
IDE time. This is the naive version. Call out the smell — that is what Chain exists to erase.

**[3:30]** *(structure diagram)*
Roles for **this** problem, not a generic UML poster. Point at each box while you talk.

**[4:30]** *(refactor / solution code)*
Stage the solution. Highlight failure injection and the tests that lock it in.

**[7:00]** *(walkthrough output)*
Real stdout. Happy path cue: `billing-desk`.
Failure cue: `Failure path is asserted in ProblemTest (injected fault / invalid input)`.
No invented numbers on camera.

**[8:30]** *(modern Java 25 / Zazr + trade-offs)*
Show the modernization, then slow down for when NOT to use Chain. That is what seniors listen for.

**[10:30]** *(Q&A + recap + outro)*
Two interview questions, three takeaways, link the GitHub episode folder. I am Waqar — see you in the next one.


## Walkthrough Beats (Say These Out Loud)
1. **Setup:** open `series/15-chain-of-responsibility/` and `run-output.txt` beside the IDE.
2. **Naive:** reproduce the pain until you can point at `Failure path is asserted in ProblemTest (injected fault / invalid input)`.
3. **Structure:** on the diagram, trace `Client` → `Route`, `Route` → `support`, `support` → `Result`.
4. **Refactor:** encode the boundary, then run `ProblemDemo` — match `billing-desk`.
5. **Break it:** flip one `ProblemTest` assertion, watch red, restore it.
6. **Refuse:** Contrast with **Decorator**: Chain routes/handles or passes on; Decorator always wraps and adds.

## Practice Drill
- Clone `waqarys/java-design-patterns`, set `JAVA_HOME` to JDK 25.
- Run the episode `ProblemDemo` (see `EP_MAIN` in `tools/enrich/build_problems.py`).
- Mutate one collaborator to fail; confirm the observable compensation / error path.
- Explain Chain for «Route support tickets billing→tech→general» in sixty seconds without notes.
