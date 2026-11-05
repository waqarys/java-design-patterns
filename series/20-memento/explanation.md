# Memento

**Season:** 3 · Behavioral · **Category:** GoF · **Level:** Beginner · **Runtime:** ~10–12 min · **Channel:** Build with Waqar

## Problem
**Save/restore text editor snapshots**

Key types: `DocMemento`, `Doc`, `History`.

## Hook
Picture this: you are mid-demo and «Save/restore text editor snapshots» blows up the naive way — you see `Failure path is asserted in ProblemTest (injected fault / invalid input)`. That is the pain. In the next ten minutes we introduce **Memento** with real types (DocMemento, Doc, History), a runnable `ProblemDemo`, and the failure path locked in by tests. No abstract UML first — problem first.

## Intent
Use **Memento** to cleanly solve: Save/restore text editor snapshots.

**When to use:** the force behind «Save/restore text editor snapshots» shows up more than once and a simpler language feature does not erase it.

**When to refuse:** you are pattern-collecting, the boundary is still unclear, or the team cannot yet test the abstraction (compensations, half-open recovery, interpreters).

## The Pain — Naive Design
Naive version fails to cleanly solve: Save/restore text editor snapshots

```java
package com.buildwithwaqar.patterns.gof.behavioral.memento.problem;

import java.util.*;

public final class NaiveDoc {
  public String text="";
  public String backup; // single slot only
  public void save(){ backup=text; }
  public void restore(){ text=backup; }
}

```

On camera, point at the smell: missing compensations, leaked XML, null at the boundary,
illegal transitions, or hard-wired `System.out` that you cannot assert.

## Pattern Structure
```mermaid
flowchart LR
  Client[Client] --> Save[Save]
  Save[Save] --> restore[restore]
  restore[restore] --> Result[Result]
```

These roles are for **«Save/restore text editor snapshots»** — not a generic Client→Pattern poster. Keep them on screen
while you type the solution.

## Refactor to the Pattern

### Stage 1 — Name the boundary
Introduce the client-facing API the rest of the app should depend on. Hide the incompatible or
distributed details behind it. For this episode that boundary is what makes «Save/restore text editor snapshots» testable.

### Stage 2 — Encode the solution
```java
package com.buildwithwaqar.patterns.gof.behavioral.memento.problem;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.util.function.*;

final class DocMemento { final String text; DocMemento(String t){ text=t; } }
final class Doc {
  private String text="";
  void write(String s){ text+=s; }
  String text(){ return text; }
  DocMemento save(){ return new DocMemento(text); }
  void restore(DocMemento m){ text=m.text; }
}
final class History {
  private final Deque<DocMemento> stack=new ArrayDeque<>();
  void push(DocMemento m){ stack.push(m); }
  DocMemento pop(){ if(stack.isEmpty()) throw new IllegalStateException("empty"); return stack.pop(); }
}

```

### Stage 3 — Runnable main
```java
package com.buildwithwaqar.patterns.gof.behavioral.memento.problem;
import java.util.*; import java.util.concurrent.*;
public final class ProblemDemo {
  public static void main(String[] args) throws Exception {
    System.out.println("=== Memento: editor snapshots ===");
    System.out.println("--- naive ---");
var n=new NaiveDoc(); n.text="a"; n.save(); n.text="ab"; n.restore(); System.out.println(n.text);
System.out.println("--- memento ---");
var d=new Doc(); var h=new History(); d.write("Hello"); h.push(d.save()); d.write("!");
System.out.println(d.text()); d.restore(h.pop()); System.out.println("restored="+d.text());
  }
}

```

## Runnable Output (Verified on JDK 25)
Narrate each line. Do not invent numbers on camera.

```text
=== Memento: editor snapshots ===
--- naive ---
a
--- memento ---
Hello!
restored=Hello
```

Happy-path cue: `a`.
Failure-path cue: `Failure path is asserted in ProblemTest (injected fault / invalid input)`.
Fork the repo, set `JAVA_HOME` to JDK 25, and re-run the same `ProblemDemo` to verify.

## Modern Java 25 / Zazr Variant
For **Memento**, prefer records for results, sealed types for closed variants, and Zazr `Option`/`Either`/`Validation` when the force is absence or accumulated errors. Keep the classic shape when interview literacy matters.

## Trade-offs
- **Clarity vs ceremony:** extra types for «Save/restore text editor snapshots» only pay off when the force repeats.
- **Testability:** inject collaborators; assert the failure path, not only the happy path.
- **Distributed / concurrent reality:** demos omit metrics, backpressure, and multi-region clocks.
- **Refuse Memento** when a sealed switch, a function value, or a simpler library call already erases the force.
- **Neighbor:** Command — Memento snapshots state for undo; Command records the action itself.

## Interview Q&A
**Q1. What concrete problem does Memento solve here?**
A: Save/restore text editor snapshots.

**Q2. What does the naive design get wrong?**
A: Naive version fails to cleanly solve: Save/restore text editor snapshots

**Q3. How do you test the failure path?**
A: Inject the fault and assert the observable outcome — e.g. `Failure path is asserted in ProblemTest (injected fault / invalid input)`.

**Q4. When is Memento the wrong tool?**
A: When a smaller language feature erases the force, or when you really needed Command.


## Key Takeaways
- Lead with the problem: Save/restore text editor snapshots.
- Show naive pain (`Failure path is asserted in ProblemTest (injected fault / invalid input)`) before naming Memento.
- Run the Demo; trust JDK 25 stdout (`a`).
- Teach when NOT to use Memento.

## Video Script (≈10–12 minutes)
Record for **Build with Waqar**. OBS for capture, CapCut for assembly. Import `slides.pdf`.
Facecam: full-screen for the hook, framed PiP over code slides.

**[0:00]** *(slide 01 title + talking head)*
Hey everyone — today we solve one concrete problem with **Memento**:
*Save/restore text editor snapshots*. Keep these names in your head: DocMemento, Doc, History.

**[0:45]** *(problem + hook slides)*
I am not starting with a textbook definition. Here is the product pain. By the end you will have
seen the naive leak, the pattern boundary, and real JDK 25 stdout.

**[2:00]** *(naive code)*
IDE time. This is the naive version. Call out the smell — that is what Memento exists to erase.

**[3:30]** *(structure diagram)*
Roles for **this** problem, not a generic UML poster. Point at each box while you talk.

**[4:30]** *(refactor / solution code)*
Stage the solution. Highlight failure injection and the tests that lock it in.

**[7:00]** *(walkthrough output)*
Real stdout. Happy path cue: `a`.
Failure cue: `Failure path is asserted in ProblemTest (injected fault / invalid input)`.
No invented numbers on camera.

**[8:30]** *(modern Java 25 / Zazr + trade-offs)*
Show the modernization, then slow down for when NOT to use Memento. That is what seniors listen for.

**[10:30]** *(Q&A + recap + outro)*
Two interview questions, three takeaways, link the GitHub episode folder. I am Waqar — see you in the next one.


## Walkthrough Beats (Say These Out Loud)
1. **Setup:** open `series/20-memento/` and `run-output.txt` beside the IDE.
2. **Naive:** reproduce the pain until you can point at `Failure path is asserted in ProblemTest (injected fault / invalid input)`.
3. **Structure:** on the diagram, trace `Client` → `Save`, `Save` → `restore`, `restore` → `Result`.
4. **Refactor:** encode the boundary, then run `ProblemDemo` — match `a`.
5. **Break it:** flip one `ProblemTest` assertion, watch red, restore it.
6. **Refuse:** Contrast with **Command**: Memento snapshots state for undo; Command records the action itself.

## Practice Drill
- Clone `waqarys/java-design-patterns`, set `JAVA_HOME` to JDK 25.
- Run the episode `ProblemDemo` (see `EP_MAIN` in `tools/enrich/build_problems.py`).
- Mutate one collaborator to fail; confirm the observable compensation / error path.
- Explain Memento for «Save/restore text editor snapshots» in sixty seconds without notes.
