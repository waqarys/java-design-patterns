# Proxy

**Season:** 2 · Structural · **Category:** GoF · **Level:** Beginner · **Runtime:** ~10–12 min · **Channel:** Build with Waqar

## Problem
**Lazy-load heavy image assets**

Key types: `Image`, `RealImage`, `LazyImageProxy`.

## Hook
Picture this: you are mid-demo and «Lazy-load heavy image assets» blows up the naive way — you see `Failure path is asserted in ProblemTest (injected fault / invalid input)`. That is the pain. In the next ten minutes we introduce **Proxy** with real types (Image, RealImage, LazyImageProxy), a runnable `ProblemDemo`, and the failure path locked in by tests. No abstract UML first — problem first.

## Intent
Use **Proxy** to cleanly solve: Lazy-load heavy image assets.

**When to use:** the force behind «Lazy-load heavy image assets» shows up more than once and a simpler language feature does not erase it.

**When to refuse:** you are pattern-collecting, the boundary is still unclear, or the team cannot yet test the abstraction (compensations, half-open recovery, interpreters).

## The Pain — Naive Design
Naive version fails to cleanly solve: Lazy-load heavy image assets

```java
package com.buildwithwaqar.patterns.gof.structural.proxy.problem;

import java.util.*;

public final class NaiveImage {
  public NaiveImage(String path){ System.out.println("LOAD "+path); }
  public String display(){ return "shown"; }
}

```

On camera, point at the smell: missing compensations, leaked XML, null at the boundary,
illegal transitions, or hard-wired `System.out` that you cannot assert.

## Pattern Structure
```mermaid
flowchart LR
  Client[Client] --> Lazy[Lazy]
  Lazy[Lazy] --> load[load]
  load[load] --> Result[Result]
```

These roles are for **«Lazy-load heavy image assets»** — not a generic Client→Pattern poster. Keep them on screen
while you type the solution.

## Refactor to the Pattern

### Stage 1 — Name the boundary
Introduce the client-facing API the rest of the app should depend on. Hide the incompatible or
distributed details behind it. For this episode that boundary is what makes «Lazy-load heavy image assets» testable.

### Stage 2 — Encode the solution
```java
package com.buildwithwaqar.patterns.gof.structural.proxy.problem;

import java.util.*;
import java.util.concurrent.*;
import java.util.function.*;

interface Image { String display(); boolean loaded(); }
final class RealImage implements Image {
  private final String path; RealImage(String path){ this.path=path; System.out.println("LOAD "+path); }
  public String display(){ return "shown:"+path; } public boolean loaded(){ return true; }
}
final class LazyImageProxy implements Image {
  private final String path; private RealImage real;
  LazyImageProxy(String path){ this.path=path; }
  public String display(){ if(real==null) real=new RealImage(path); return real.display(); }
  public boolean loaded(){ return real!=null; }
}

```

### Stage 3 — Runnable main
```java
package com.buildwithwaqar.patterns.gof.structural.proxy.problem;

import java.util.*;
import java.util.concurrent.*;

public final class ProblemDemo {
  public static void main(String[] args) {
    System.out.println("=== Proxy: lazy-load heavy image ===");
    System.out.println("--- naive (loads immediately) ---");
var n=new NaiveImage("/big.png"); System.out.println(n.display());
System.out.println("--- proxy ---");
Image p=new LazyImageProxy("/big.png");
System.out.println("loadedBefore="+p.loaded());
System.out.println(p.display());
System.out.println("loadedAfter="+p.loaded());
  }
}

```

## Runnable Output (Verified on JDK 25)
Narrate each line. Do not invent numbers on camera.

```text
=== Proxy: lazy-load heavy image ===
--- naive (loads immediately) ---
LOAD /big.png
shown
--- proxy ---
loadedBefore=false
LOAD /big.png
shown:/big.png
loadedAfter=true
```

Happy-path cue: `LOAD /big.png`.
Failure-path cue: `Failure path is asserted in ProblemTest (injected fault / invalid input)`.
Fork the repo, set `JAVA_HOME` to JDK 25, and re-run the same `ProblemDemo` to verify.

## Modern Java 25 / Zazr Variant
For **Proxy**, prefer records for results, sealed types for closed variants, and Zazr `Option`/`Either`/`Validation` when the force is absence or accumulated errors. Keep the classic shape when interview literacy matters.

## Trade-offs
- **Clarity vs ceremony:** extra types for «Lazy-load heavy image assets» only pay off when the force repeats.
- **Testability:** inject collaborators; assert the failure path, not only the happy path.
- **Distributed / concurrent reality:** demos omit metrics, backpressure, and multi-region clocks.
- **Refuse Proxy** when a sealed switch, a function value, or a simpler library call already erases the force.
- **Neighbor:** Decorator — Proxy preserves the interface for control; Decorator for layering features.

## Interview Q&A
**Q1. What concrete problem does Proxy solve here?**
A: Lazy-load heavy image assets.

**Q2. What does the naive design get wrong?**
A: Naive version fails to cleanly solve: Lazy-load heavy image assets

**Q3. How do you test the failure path?**
A: Inject the fault and assert the observable outcome — e.g. `Failure path is asserted in ProblemTest (injected fault / invalid input)`.

**Q4. When is Proxy the wrong tool?**
A: When a smaller language feature erases the force, or when you really needed Decorator.


## Key Takeaways
- Lead with the problem: Lazy-load heavy image assets.
- Show naive pain (`Failure path is asserted in ProblemTest (injected fault / invalid input)`) before naming Proxy.
- Run the Demo; trust JDK 25 stdout (`LOAD /big.png`).
- Teach when NOT to use Proxy.

## Video Script (≈10–12 minutes)
Record for **Build with Waqar**. OBS for capture, CapCut for assembly. Import `slides.pdf`.
Facecam: full-screen for the hook, framed PiP over code slides.

**[0:00]** *(slide 01 title + talking head)*
Hey everyone — today we solve one concrete problem with **Proxy**:
*Lazy-load heavy image assets*. Keep these names in your head: Image, RealImage, LazyImageProxy.

**[0:45]** *(problem + hook slides)*
I am not starting with a textbook definition. Here is the product pain. By the end you will have
seen the naive leak, the pattern boundary, and real JDK 25 stdout.

**[2:00]** *(naive code)*
IDE time. This is the naive version. Call out the smell — that is what Proxy exists to erase.

**[3:30]** *(structure diagram)*
Roles for **this** problem, not a generic UML poster. Point at each box while you talk.

**[4:30]** *(refactor / solution code)*
Stage the solution. Highlight failure injection and the tests that lock it in.

**[7:00]** *(walkthrough output)*
Real stdout. Happy path cue: `LOAD /big.png`.
Failure cue: `Failure path is asserted in ProblemTest (injected fault / invalid input)`.
No invented numbers on camera.

**[8:30]** *(modern Java 25 / Zazr + trade-offs)*
Show the modernization, then slow down for when NOT to use Proxy. That is what seniors listen for.

**[10:30]** *(Q&A + recap + outro)*
Two interview questions, three takeaways, link the GitHub episode folder. I am Waqar — see you in the next one.


## Walkthrough Beats (Say These Out Loud)
1. **Setup:** open `series/14-proxy/` and `run-output.txt` beside the IDE.
2. **Naive:** reproduce the pain until you can point at `Failure path is asserted in ProblemTest (injected fault / invalid input)`.
3. **Structure:** on the diagram, trace `Client` → `Lazy`, `Lazy` → `load`, `load` → `Result`.
4. **Refactor:** encode the boundary, then run `ProblemDemo` — match `LOAD /big.png`.
5. **Break it:** flip one `ProblemTest` assertion, watch red, restore it.
6. **Refuse:** Contrast with **Decorator**: Proxy preserves the interface for control; Decorator for layering features.

## Practice Drill
- Clone `waqarys/java-design-patterns`, set `JAVA_HOME` to JDK 25.
- Run the episode `ProblemDemo` (see `EP_MAIN` in `tools/enrich/build_problems.py`).
- Mutate one collaborator to fail; confirm the observable compensation / error path.
- Explain Proxy for «Lazy-load heavy image assets» in sixty seconds without notes.
