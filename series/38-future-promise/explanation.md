# Future & Promise

**Season:** 5 · Concurrency · **Category:** Concurrency · **Level:** Intermediate · **Runtime:** ~10–12 min · **Channel:** Build with Waqar

## Problem
**Async thumbnail then compose URL**

Key types: `ThumbService`.

## Hook
Picture this: you are mid-demo and «Async thumbnail then compose URL» blows up the naive way — you see `caught=id`. That is the pain. In the next ten minutes we introduce **Future/Promise** with real types (ThumbService), a runnable `ProblemDemo`, and the failure path locked in by tests. No abstract UML first — problem first.

## Intent
Use **Future/Promise** to cleanly solve: Async thumbnail then compose URL.

**When to use:** the force behind «Async thumbnail then compose URL» shows up more than once and a simpler language feature does not erase it.

**When to refuse:** you are pattern-collecting, the boundary is still unclear, or the team cannot yet test the abstraction (compensations, half-open recovery, interpreters).

## The Pain — Naive Design
Naive version fails to cleanly solve: Async thumbnail then compose URL

```java
package com.buildwithwaqar.patterns.concurrency.futurepromise.problem;

import java.util.*;

public final class NaiveThumb {
  public static String url(String id){ return "https://cdn/"+id+".jpg"; }
}

```

On camera, point at the smell: missing compensations, leaked XML, null at the boundary,
illegal transitions, or hard-wired `System.out` that you cannot assert.

## Pattern Structure
```mermaid
flowchart LR
  Client[Client] --> Async[Async]
  Async[Async] --> thumbnail[thumbnail]
  thumbnail[thumbnail] --> Result[Result]
```

These roles are for **«Async thumbnail then compose URL»** — not a generic Client→Pattern poster. Keep them on screen
while you type the solution.

## Refactor to the Pattern

### Stage 1 — Name the boundary
Introduce the client-facing API the rest of the app should depend on. Hide the incompatible or
distributed details behind it. For this episode that boundary is what makes «Async thumbnail then compose URL» testable.

### Stage 2 — Encode the solution
```java
package com.buildwithwaqar.patterns.concurrency.futurepromise.problem;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.util.concurrent.locks.*;
import java.util.function.*;

final class ThumbService {
  CompletableFuture<String> render(String id){
    return CompletableFuture.supplyAsync(() -> {
      if (id==null || id.isBlank()) throw new IllegalArgumentException("id");
      return id+".bin";
    });
  }
  CompletableFuture<String> toUrl(String blob){ return CompletableFuture.completedFuture("https://cdn/"+blob.replace(".bin",".jpg")); }
}

```

### Stage 3 — Runnable main
```java
package com.buildwithwaqar.patterns.concurrency.futurepromise.problem;
import java.util.*; import java.util.concurrent.*; import java.util.concurrent.atomic.*;
public final class ProblemDemo {
  public static void main(String[] args) throws Exception {
    System.out.println("=== Future/Promise: async thumbnail URL ===");
    System.out.println("--- naive ---"); System.out.println(NaiveThumb.url("a"));
System.out.println("--- future ---");
var s=new ThumbService();
System.out.println(s.render("vid").thenCompose(s::toUrl).join());
try { s.render("").join(); } catch(CompletionException e){ System.out.println("caught="+e.getCause().getMessage()); }
  }
}

```

## Runnable Output (Verified on JDK 25)
Narrate each line. Do not invent numbers on camera.

```text
=== Future/Promise: async thumbnail URL ===
--- naive ---
https://cdn/a.jpg
--- future ---
https://cdn/vid.jpg
caught=id
```

Happy-path cue: `https://cdn/a.jpg`.
Failure-path cue: `caught=id`.
Fork the repo, set `JAVA_HOME` to JDK 25, and re-run the same `ProblemDemo` to verify.

## Modern Java 25 / Zazr Variant
`CompletableFuture` composition covers the async variant; prefer virtual threads when the work is blocking I/O.

## Trade-offs
- **Clarity vs ceremony:** extra types for «Async thumbnail then compose URL» only pay off when the force repeats.
- **Testability:** inject collaborators; assert the failure path, not only the happy path.
- **Distributed / concurrent reality:** demos omit metrics, backpressure, and multi-region clocks.
- **Refuse Future/Promise** when a sealed switch, a function value, or a simpler library call already erases the force.
- **Neighbor:** Virtual Threads — Futures compose async results; VTs make blocking code scale.

## Interview Q&A
**Q1. What concrete problem does Future/Promise solve here?**
A: Async thumbnail then compose URL.

**Q2. What does the naive design get wrong?**
A: Naive version fails to cleanly solve: Async thumbnail then compose URL

**Q3. How do you test the failure path?**
A: Inject the fault and assert the observable outcome — e.g. `caught=id`.

**Q4. When is Future/Promise the wrong tool?**
A: When a smaller language feature erases the force, or when you really needed Virtual Threads.


## Key Takeaways
- Lead with the problem: Async thumbnail then compose URL.
- Show naive pain (`caught=id`) before naming Future/Promise.
- Run the Demo; trust JDK 25 stdout (`https://cdn/a.jpg`).
- Teach when NOT to use Future/Promise.

## Video Script (≈10–12 minutes)
Record for **Build with Waqar**. OBS for capture, CapCut for assembly. Import `slides.pdf`.
Facecam: full-screen for the hook, framed PiP over code slides.

**[0:00]** *(slide 01 title + talking head)*
Hey everyone — today we solve one concrete problem with **Future/Promise**:
*Async thumbnail then compose URL*. Keep these names in your head: ThumbService.

**[0:45]** *(problem + hook slides)*
I am not starting with a textbook definition. Here is the product pain. By the end you will have
seen the naive leak, the pattern boundary, and real JDK 25 stdout.

**[2:00]** *(naive code)*
IDE time. This is the naive version. Call out the smell — that is what Future/Promise exists to erase.

**[3:30]** *(structure diagram)*
Roles for **this** problem, not a generic UML poster. Point at each box while you talk.

**[4:30]** *(refactor / solution code)*
Stage the solution. Highlight failure injection and the tests that lock it in.

**[7:00]** *(walkthrough output)*
Real stdout. Happy path cue: `https://cdn/a.jpg`.
Failure cue: `caught=id`.
No invented numbers on camera.

**[8:30]** *(modern Java 25 / Zazr + trade-offs)*
Show the modernization, then slow down for when NOT to use Future/Promise. That is what seniors listen for.

**[10:30]** *(Q&A + recap + outro)*
Two interview questions, three takeaways, link the GitHub episode folder. I am Waqar — see you in the next one.


## Walkthrough Beats (Say These Out Loud)
1. **Setup:** open `series/38-future-promise/` and `run-output.txt` beside the IDE.
2. **Naive:** reproduce the pain until you can point at `caught=id`.
3. **Structure:** on the diagram, trace `Client` → `Async`, `Async` → `thumbnail`, `thumbnail` → `Result`.
4. **Refactor:** encode the boundary, then run `ProblemDemo` — match `https://cdn/a.jpg`.
5. **Break it:** flip one `ProblemTest` assertion, watch red, restore it.
6. **Refuse:** Contrast with **Virtual Threads**: Futures compose async results; VTs make blocking code scale.

## Practice Drill
- Clone `waqarys/java-design-patterns`, set `JAVA_HOME` to JDK 25.
- Run the episode `ProblemDemo` (see `EP_MAIN` in `tools/enrich/build_problems.py`).
- Mutate one collaborator to fail; confirm the observable compensation / error path.
- Explain Future/Promise for «Async thumbnail then compose URL» in sixty seconds without notes.
