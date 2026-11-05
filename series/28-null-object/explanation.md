# Null Object

**Season:** 4 · Enterprise · **Category:** Enterprise · **Level:** Intermediate · **Runtime:** ~10–12 min · **Channel:** Build with Waqar

## Problem
**Safe logger when monitoring disabled**

Key types: `Logger`, `ConsoleLogger`, `NullLogger`, `OrderService`.

## Hook
Picture this: you are mid-demo and «Safe logger when monitoring disabled» blows up the naive way — you see `Failure path is asserted in ProblemTest (injected fault / invalid input)`. That is the pain. In the next ten minutes we introduce **Null Object** with real types (Logger, ConsoleLogger, NullLogger), a runnable `ProblemDemo`, and the failure path locked in by tests. No abstract UML first — problem first.

## Intent
Use **Null Object** to cleanly solve: Safe logger when monitoring disabled.

**When to use:** the force behind «Safe logger when monitoring disabled» shows up more than once and a simpler language feature does not erase it.

**When to refuse:** you are pattern-collecting, the boundary is still unclear, or the team cannot yet test the abstraction (compensations, half-open recovery, interpreters).

## The Pain — Naive Design
Naive: logger may be null — every call site null-checks.

```java
package com.buildwithwaqar.patterns.enterprise.nullobject.problem;

/** Naive: logger may be null — every call site null-checks. */
public final class NaiveOrderService {
  private final Logger log;
  public NaiveOrderService(Logger log){ this.log=log; }
  public void place(String id){ if(log!=null) log.info("placed "+id); }
}

```

On camera, point at the smell: missing compensations, leaked XML, null at the boundary,
illegal transitions, or hard-wired `System.out` that you cannot assert.

## Pattern Structure
```mermaid
flowchart LR
  Client[Client] --> Safe[Safe]
  Safe[Safe] --> logger[logger]
  logger[logger] --> Result[Result]
```

These roles are for **«Safe logger when monitoring disabled»** — not a generic Client→Pattern poster. Keep them on screen
while you type the solution.

## Refactor to the Pattern

### Stage 1 — Name the boundary
Introduce the client-facing API the rest of the app should depend on. Hide the incompatible or
distributed details behind it. For this episode that boundary is what makes «Safe logger when monitoring disabled» testable.

### Stage 2 — Encode the solution
```java
package com.buildwithwaqar.patterns.enterprise.nullobject.problem;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.util.concurrent.locks.*;
import java.util.function.*;

interface Logger { void info(String m); }
final class ConsoleLogger implements Logger { private final List<String> lines=new ArrayList<>();
  public void info(String m){ lines.add(m); } List<String> lines(){ return List.copyOf(lines); } }
final class NullLogger implements Logger { public void info(String m){ /* no-op */ } }
final class OrderService {
  private final Logger log; OrderService(Logger log){ this.log=Objects.requireNonNull(log); }
  void place(String id){ log.info("placed "+id); }
}

```

### Stage 3 — Runnable main
```java
package com.buildwithwaqar.patterns.enterprise.nullobject.problem;
import java.util.*; import java.util.concurrent.*;
public final class ProblemDemo {
  public static void main(String[] args) throws Exception {
    System.out.println("=== Null Object: safe logger ===");
    System.out.println("--- naive null check ---");
new NaiveOrderService(null).place("o1");
System.out.println("--- null object ---");
var console=new ConsoleLogger();
new OrderService(console).place("o1");
new OrderService(new NullLogger()).place("o2");
System.out.println(console.lines());
  }
}

```

## Runnable Output (Verified on JDK 25)
Narrate each line. Do not invent numbers on camera.

```text
=== Null Object: safe logger ===
--- naive null check ---
--- null object ---
[placed o1]
```

Happy-path cue: `[placed o1]`.
Failure-path cue: `Failure path is asserted in ProblemTest (injected fault / invalid input)`.
Fork the repo, set `JAVA_HOME` to JDK 25, and re-run the same `ProblemDemo` to verify.

## Modern Java 25 / Zazr Variant
For **Null Object**, prefer records for results, sealed types for closed variants, and Zazr `Option`/`Either`/`Validation` when the force is absence or accumulated errors. Keep the classic shape when interview literacy matters.

## Trade-offs
- **Clarity vs ceremony:** extra types for «Safe logger when monitoring disabled» only pay off when the force repeats.
- **Testability:** inject collaborators; assert the failure path, not only the happy path.
- **Distributed / concurrent reality:** demos omit metrics, backpressure, and multi-region clocks.
- **Refuse Null Object** when a sealed switch, a function value, or a simpler library call already erases the force.
- **Neighbor:** Option — Null Object is a do-nothing collaborator; Option models absence of a value.

## Interview Q&A
**Q1. What concrete problem does Null Object solve here?**
A: Safe logger when monitoring disabled.

**Q2. What does the naive design get wrong?**
A: Naive: logger may be null — every call site null-checks.

**Q3. How do you test the failure path?**
A: Inject the fault and assert the observable outcome — e.g. `Failure path is asserted in ProblemTest (injected fault / invalid input)`.

**Q4. When is Null Object the wrong tool?**
A: When a smaller language feature erases the force, or when you really needed Option.


## Key Takeaways
- Lead with the problem: Safe logger when monitoring disabled.
- Show naive pain (`Failure path is asserted in ProblemTest (injected fault / invalid input)`) before naming Null Object.
- Run the Demo; trust JDK 25 stdout (`[placed o1]`).
- Teach when NOT to use Null Object.

## Video Script (≈10–12 minutes)
Record for **Build with Waqar**. OBS for capture, CapCut for assembly. Import `slides.pdf`.
Facecam: full-screen for the hook, framed PiP over code slides.

**[0:00]** *(slide 01 title + talking head)*
Hey everyone — today we solve one concrete problem with **Null Object**:
*Safe logger when monitoring disabled*. Keep these names in your head: Logger, ConsoleLogger, NullLogger, OrderService.

**[0:45]** *(problem + hook slides)*
I am not starting with a textbook definition. Here is the product pain. By the end you will have
seen the naive leak, the pattern boundary, and real JDK 25 stdout.

**[2:00]** *(naive code)*
IDE time. This is the naive version. Call out the smell — that is what Null Object exists to erase.

**[3:30]** *(structure diagram)*
Roles for **this** problem, not a generic UML poster. Point at each box while you talk.

**[4:30]** *(refactor / solution code)*
Stage the solution. Highlight failure injection and the tests that lock it in.

**[7:00]** *(walkthrough output)*
Real stdout. Happy path cue: `[placed o1]`.
Failure cue: `Failure path is asserted in ProblemTest (injected fault / invalid input)`.
No invented numbers on camera.

**[8:30]** *(modern Java 25 / Zazr + trade-offs)*
Show the modernization, then slow down for when NOT to use Null Object. That is what seniors listen for.

**[10:30]** *(Q&A + recap + outro)*
Two interview questions, three takeaways, link the GitHub episode folder. I am Waqar — see you in the next one.


## Walkthrough Beats (Say These Out Loud)
1. **Setup:** open `series/28-null-object/` and `run-output.txt` beside the IDE.
2. **Naive:** reproduce the pain until you can point at `Failure path is asserted in ProblemTest (injected fault / invalid input)`.
3. **Structure:** on the diagram, trace `Client` → `Safe`, `Safe` → `logger`, `logger` → `Result`.
4. **Refactor:** encode the boundary, then run `ProblemDemo` — match `[placed o1]`.
5. **Break it:** flip one `ProblemTest` assertion, watch red, restore it.
6. **Refuse:** Contrast with **Option**: Null Object is a do-nothing collaborator; Option models absence of a value.

## Practice Drill
- Clone `waqarys/java-design-patterns`, set `JAVA_HOME` to JDK 25.
- Run the episode `ProblemDemo` (see `EP_MAIN` in `tools/enrich/build_problems.py`).
- Mutate one collaborator to fail; confirm the observable compensation / error path.
- Explain Null Object for «Safe logger when monitoring disabled» in sixty seconds without notes.
