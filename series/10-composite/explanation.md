# Composite

**Season:** 2 · Structural · **Category:** GoF · **Level:** Beginner · **Runtime:** ~10–12 min · **Channel:** Build with Waqar

## Problem
**Compute total size of nested project folders**

Key types: `FsNode`, `FileNode`, `DirNode`.

## Hook
Picture this: you are mid-demo and «Compute total size of nested project folders» blows up the naive way — you see `Failure path is asserted in ProblemTest (injected fault / invalid input)`. That is the pain. In the next ten minutes we introduce **Composite** with real types (FsNode, FileNode, DirNode), a runnable `ProblemDemo`, and the failure path locked in by tests. No abstract UML first — problem first.

## Intent
Use **Composite** to cleanly solve: Compute total size of nested project folders.

**When to use:** the force behind «Compute total size of nested project folders» shows up more than once and a simpler language feature does not erase it.

**When to refuse:** you are pattern-collecting, the boundary is still unclear, or the team cannot yet test the abstraction (compensations, half-open recovery, interpreters).

## The Pain — Naive Design
Naive version fails to cleanly solve: Compute total size of nested project folders

```java
package com.buildwithwaqar.patterns.gof.structural.composite.problem;

import java.util.*;

public final class NaiveSize {
  public static int total(Map<String,Integer> flat){ return flat.values().stream().mapToInt(i->i).sum(); }
}

```

On camera, point at the smell: missing compensations, leaked XML, null at the boundary,
illegal transitions, or hard-wired `System.out` that you cannot assert.

## Pattern Structure
```mermaid
flowchart LR
  Client[Client] --> Compute[Compute]
  Compute[Compute] --> total[total]
  total[total] --> Result[Result]
```

These roles are for **«Compute total size of nested project folders»** — not a generic Client→Pattern poster. Keep them on screen
while you type the solution.

## Refactor to the Pattern

### Stage 1 — Name the boundary
Introduce the client-facing API the rest of the app should depend on. Hide the incompatible or
distributed details behind it. For this episode that boundary is what makes «Compute total size of nested project folders» testable.

### Stage 2 — Encode the solution
```java
package com.buildwithwaqar.patterns.gof.structural.composite.problem;

import java.util.*;

import java.util.*;
sealed interface FsNode permits FileNode, DirNode { String name(); int size(); }
record FileNode(String name, int size) implements FsNode {}
final class DirNode implements FsNode {
  private final String name; private final List<FsNode> kids = new ArrayList<>();
  public DirNode(String name){ this.name=name; }
  public DirNode add(FsNode n){ kids.add(n); return this; }
  public String name(){ return name; }
  public int size(){ return kids.stream().mapToInt(FsNode::size).sum(); }
}

```

### Stage 3 — Runnable main
```java
package com.buildwithwaqar.patterns.gof.structural.composite.problem;

import java.util.*;

public final class ProblemDemo {
  public static void main(String[] args) {
    System.out.println("=== Composite: nested folder sizes ===");
    System.out.println("--- naive flat map ---");
System.out.println(NaiveSize.total(Map.of("a.txt",10,"b.txt",20)));
System.out.println("--- composite ---");
var root = new DirNode("src").add(new FileNode("A.java",10))
  .add(new DirNode("util").add(new FileNode("B.java",25)));
System.out.println("size="+root.size());
  }
}

```

## Runnable Output (Verified on JDK 25)
Narrate each line. Do not invent numbers on camera.

```text
=== Composite: nested folder sizes ===
--- naive flat map ---
30
--- composite ---
size=35
```

Happy-path cue: `30`.
Failure-path cue: `Failure path is asserted in ProblemTest (injected fault / invalid input)`.
Fork the repo, set `JAVA_HOME` to JDK 25, and re-run the same `ProblemDemo` to verify.

## Modern Java 25 / Zazr Variant
This solution already leans on **sealed types** — keep the exhaustive `switch`. **Records** carry the immutable results (`StepResult`, DTOs) with almost no boilerplate.

## Trade-offs
- **Clarity vs ceremony:** extra types for «Compute total size of nested project folders» only pay off when the force repeats.
- **Testability:** inject collaborators; assert the failure path, not only the happy path.
- **Distributed / concurrent reality:** demos omit metrics, backpressure, and multi-region clocks.
- **Refuse Composite** when a sealed switch, a function value, or a simpler library call already erases the force.
- **Neighbor:** Decorator — Composite equals part-whole trees; Decorator adds behavior to one object.

## Interview Q&A
**Q1. What concrete problem does Composite solve here?**
A: Compute total size of nested project folders.

**Q2. What does the naive design get wrong?**
A: Naive version fails to cleanly solve: Compute total size of nested project folders

**Q3. How do you test the failure path?**
A: Inject the fault and assert the observable outcome — e.g. `Failure path is asserted in ProblemTest (injected fault / invalid input)`.

**Q4. When is Composite the wrong tool?**
A: When a smaller language feature erases the force, or when you really needed Decorator.


## Key Takeaways
- Lead with the problem: Compute total size of nested project folders.
- Show naive pain (`Failure path is asserted in ProblemTest (injected fault / invalid input)`) before naming Composite.
- Run the Demo; trust JDK 25 stdout (`30`).
- Teach when NOT to use Composite.

## Video Script (≈10–12 minutes)
Record for **Build with Waqar**. OBS for capture, CapCut for assembly. Import `slides.pdf`.
Facecam: full-screen for the hook, framed PiP over code slides.

**[0:00]** *(slide 01 title + talking head)*
Hey everyone — today we solve one concrete problem with **Composite**:
*Compute total size of nested project folders*. Keep these names in your head: FsNode, FileNode, DirNode.

**[0:45]** *(problem + hook slides)*
I am not starting with a textbook definition. Here is the product pain. By the end you will have
seen the naive leak, the pattern boundary, and real JDK 25 stdout.

**[2:00]** *(naive code)*
IDE time. This is the naive version. Call out the smell — that is what Composite exists to erase.

**[3:30]** *(structure diagram)*
Roles for **this** problem, not a generic UML poster. Point at each box while you talk.

**[4:30]** *(refactor / solution code)*
Stage the solution. Highlight failure injection and the tests that lock it in.

**[7:00]** *(walkthrough output)*
Real stdout. Happy path cue: `30`.
Failure cue: `Failure path is asserted in ProblemTest (injected fault / invalid input)`.
No invented numbers on camera.

**[8:30]** *(modern Java 25 / Zazr + trade-offs)*
Show the modernization, then slow down for when NOT to use Composite. That is what seniors listen for.

**[10:30]** *(Q&A + recap + outro)*
Two interview questions, three takeaways, link the GitHub episode folder. I am Waqar — see you in the next one.


## Walkthrough Beats (Say These Out Loud)
1. **Setup:** open `series/10-composite/` and `run-output.txt` beside the IDE.
2. **Naive:** reproduce the pain until you can point at `Failure path is asserted in ProblemTest (injected fault / invalid input)`.
3. **Structure:** on the diagram, trace `Client` → `Compute`, `Compute` → `total`, `total` → `Result`.
4. **Refactor:** encode the boundary, then run `ProblemDemo` — match `30`.
5. **Break it:** flip one `ProblemTest` assertion, watch red, restore it.
6. **Refuse:** Contrast with **Decorator**: Composite equals part-whole trees; Decorator adds behavior to one object.

## Practice Drill
- Clone `waqarys/java-design-patterns`, set `JAVA_HOME` to JDK 25.
- Run the episode `ProblemDemo` (see `EP_MAIN` in `tools/enrich/build_problems.py`).
- Mutate one collaborator to fail; confirm the observable compensation / error path.
- Explain Composite for «Compute total size of nested project folders» in sixty seconds without notes.
