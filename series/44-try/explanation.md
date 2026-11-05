# Try

**Season:** 6 · FP Foundations · **Category:** Functional · **Level:** Intermediate · **Runtime:** ~10–12 min · **Channel:** Build with Waqar

## Problem
**Parse config int with recovery**

Key types: `Try`, `Success`, `Failure`, `Tries`.

## Hook
Picture this: you are mid-demo and «Parse config int with recovery» blows up the naive way — you see `Failure path is asserted in ProblemTest (injected fault / invalid input)`. That is the pain. In the next ten minutes we introduce **Try** with real types (Try, Success, Failure), a runnable `ProblemDemo`, and the failure path locked in by tests. No abstract UML first — problem first.

## Intent
Use **Try** to cleanly solve: Parse config int with recovery.

**When to use:** the force behind «Parse config int with recovery» shows up more than once and a simpler language feature does not erase it.

**When to refuse:** you are pattern-collecting, the boundary is still unclear, or the team cannot yet test the abstraction (compensations, half-open recovery, interpreters).

## The Pain — Naive Design
Naive version fails to cleanly solve: Parse config int with recovery

```java
package com.buildwithwaqar.patterns.functional.try_.problem;

import java.util.*;

public final class NaiveCfg {
  public static int port(String s){ return Integer.parseInt(s); }
}

```

On camera, point at the smell: missing compensations, leaked XML, null at the boundary,
illegal transitions, or hard-wired `System.out` that you cannot assert.

## Pattern Structure
```mermaid
flowchart LR
  Client[Client] --> Parse[Parse]
  Parse[Parse] --> config[config]
  config[config] --> Result[Result]
```

These roles are for **«Parse config int with recovery»** — not a generic Client→Pattern poster. Keep them on screen
while you type the solution.

## Refactor to the Pattern

### Stage 1 — Name the boundary
Introduce the client-facing API the rest of the app should depend on. Hide the incompatible or
distributed details behind it. For this episode that boundary is what makes «Parse config int with recovery» testable.

### Stage 2 — Encode the solution
```java
package com.buildwithwaqar.patterns.functional.try_.problem;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.util.concurrent.locks.*;
import java.util.function.*;

sealed interface Try<A> permits Success, Failure { A getOrElse(A fallback); boolean isSuccess(); }
record Success<A>(A value) implements Try<A> { public A getOrElse(A f){return value;} public boolean isSuccess(){return true;} }
record Failure<A>(Throwable error) implements Try<A> { public A getOrElse(A f){return f;} public boolean isSuccess(){return false;} }
final class Tries {
  static <A> Try<A> of(Callable<A> c){ try { return new Success<>(c.call()); } catch(Exception e){ return new Failure<>(e); } }
}

```

### Stage 3 — Runnable main
```java
package com.buildwithwaqar.patterns.functional.try_.problem;
import java.util.*; import java.util.concurrent.*; import java.util.concurrent.atomic.*;
public final class ProblemDemo {
  public static void main(String[] args) throws Exception {
    System.out.println("=== Try: parse config int with recovery ===");
    System.out.println("--- naive ---");
try { System.out.println(NaiveCfg.port("x")); } catch(Exception e){ System.out.println("caught"); }
System.out.println("--- try ---");
System.out.println(Tries.of(() -> Integer.parseInt("8080")).getOrElse(-1));
System.out.println(Tries.of(() -> Integer.parseInt("x")).getOrElse(8080));
  }
}

```

## Runnable Output (Verified on JDK 25)
Narrate each line. Do not invent numbers on camera.

```text
=== Try: parse config int with recovery ===
--- naive ---
caught
--- try ---
8080
8080
```

Happy-path cue: `caught`.
Failure-path cue: `Failure path is asserted in ProblemTest (injected fault / invalid input)`.
Fork the repo, set `JAVA_HOME` to JDK 25, and re-run the same `ProblemDemo` to verify.

## Modern Java 25 / Zazr Variant
This solution already leans on **sealed types** — keep the exhaustive `switch`. **Records** carry the immutable results (`StepResult`, DTOs) with almost no boilerplate.

## Trade-offs
- **Clarity vs ceremony:** extra types for «Parse config int with recovery» only pay off when the force repeats.
- **Testability:** inject collaborators; assert the failure path, not only the happy path.
- **Distributed / concurrent reality:** demos omit metrics, backpressure, and multi-region clocks.
- **Refuse Try** when a sealed switch, a function value, or a simpler library call already erases the force.
- **Neighbor:** Either — Try catches Throwable; Either is for expected domain errors.

## Interview Q&A
**Q1. What concrete problem does Try solve here?**
A: Parse config int with recovery.

**Q2. What does the naive design get wrong?**
A: Naive version fails to cleanly solve: Parse config int with recovery

**Q3. How do you test the failure path?**
A: Inject the fault and assert the observable outcome — e.g. `Failure path is asserted in ProblemTest (injected fault / invalid input)`.

**Q4. When is Try the wrong tool?**
A: When a smaller language feature erases the force, or when you really needed Either.


## Key Takeaways
- Lead with the problem: Parse config int with recovery.
- Show naive pain (`Failure path is asserted in ProblemTest (injected fault / invalid input)`) before naming Try.
- Run the Demo; trust JDK 25 stdout (`caught`).
- Teach when NOT to use Try.

## Video Script (≈10–12 minutes)
Record for **Build with Waqar**. OBS for capture, CapCut for assembly. Import `slides.pdf`.
Facecam: full-screen for the hook, framed PiP over code slides.

**[0:00]** *(slide 01 title + talking head)*
Hey everyone — today we solve one concrete problem with **Try**:
*Parse config int with recovery*. Keep these names in your head: Try, Success, Failure, Tries.

**[0:45]** *(problem + hook slides)*
I am not starting with a textbook definition. Here is the product pain. By the end you will have
seen the naive leak, the pattern boundary, and real JDK 25 stdout.

**[2:00]** *(naive code)*
IDE time. This is the naive version. Call out the smell — that is what Try exists to erase.

**[3:30]** *(structure diagram)*
Roles for **this** problem, not a generic UML poster. Point at each box while you talk.

**[4:30]** *(refactor / solution code)*
Stage the solution. Highlight failure injection and the tests that lock it in.

**[7:00]** *(walkthrough output)*
Real stdout. Happy path cue: `caught`.
Failure cue: `Failure path is asserted in ProblemTest (injected fault / invalid input)`.
No invented numbers on camera.

**[8:30]** *(modern Java 25 / Zazr + trade-offs)*
Show the modernization, then slow down for when NOT to use Try. That is what seniors listen for.

**[10:30]** *(Q&A + recap + outro)*
Two interview questions, three takeaways, link the GitHub episode folder. I am Waqar — see you in the next one.


## Walkthrough Beats (Say These Out Loud)
1. **Setup:** open `series/44-try/` and `run-output.txt` beside the IDE.
2. **Naive:** reproduce the pain until you can point at `Failure path is asserted in ProblemTest (injected fault / invalid input)`.
3. **Structure:** on the diagram, trace `Client` → `Parse`, `Parse` → `config`, `config` → `Result`.
4. **Refactor:** encode the boundary, then run `ProblemDemo` — match `caught`.
5. **Break it:** flip one `ProblemTest` assertion, watch red, restore it.
6. **Refuse:** Contrast with **Either**: Try catches Throwable; Either is for expected domain errors.

## Practice Drill
- Clone `waqarys/java-design-patterns`, set `JAVA_HOME` to JDK 25.
- Run the episode `ProblemDemo` (see `EP_MAIN` in `tools/enrich/build_problems.py`).
- Mutate one collaborator to fail; confirm the observable compensation / error path.
- Explain Try for «Parse config int with recovery» in sixty seconds without notes.
