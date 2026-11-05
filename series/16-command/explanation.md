# Command

**Season:** 3 · Behavioral · **Category:** GoF · **Level:** Beginner · **Runtime:** ~10–12 min · **Channel:** Build with Waqar

## Problem
**Undoable editor keystrokes**

Key types: `Editor`, `Command`, `WriteCommand`, `CommandBus`.

## Hook
Picture this: you are mid-demo and «Undoable editor keystrokes» blows up the naive way — you see `Failure path is asserted in ProblemTest (injected fault / invalid input)`. That is the pain. In the next ten minutes we introduce **Command** with real types (Editor, Command, WriteCommand), a runnable `ProblemDemo`, and the failure path locked in by tests. No abstract UML first — problem first.

## Intent
Use **Command** to cleanly solve: Undoable editor keystrokes.

**When to use:** the force behind «Undoable editor keystrokes» shows up more than once and a simpler language feature does not erase it.

**When to refuse:** you are pattern-collecting, the boundary is still unclear, or the team cannot yet test the abstraction (compensations, half-open recovery, interpreters).

## The Pain — Naive Design
Naive version fails to cleanly solve: Undoable editor keystrokes

```java
package com.buildwithwaqar.patterns.gof.behavioral.command.problem;

import java.util.*;

public final class NaiveEditor {
  private String text="";
  public void write(String s){ text+=s; }
  public void undo(){ /* cannot undo */ }
  public String text(){ return text; }
}

```

On camera, point at the smell: missing compensations, leaked XML, null at the boundary,
illegal transitions, or hard-wired `System.out` that you cannot assert.

## Pattern Structure
```mermaid
flowchart LR
  Client[Client] --> Undoable[Undoable]
  Undoable[Undoable] --> editor[editor]
  editor[editor] --> Result[Result]
```

These roles are for **«Undoable editor keystrokes»** — not a generic Client→Pattern poster. Keep them on screen
while you type the solution.

## Refactor to the Pattern

### Stage 1 — Name the boundary
Introduce the client-facing API the rest of the app should depend on. Hide the incompatible or
distributed details behind it. For this episode that boundary is what makes «Undoable editor keystrokes» testable.

### Stage 2 — Encode the solution
```java
package com.buildwithwaqar.patterns.gof.behavioral.command.problem;

import java.util.*;
import java.util.concurrent.*;
import java.util.function.*;

final class Editor { private final StringBuilder sb=new StringBuilder();
  void write(String s){ sb.append(s); } void deleteLast(int n){ sb.delete(Math.max(0,sb.length()-n), sb.length()); }
  String text(){ return sb.toString(); }
}
interface Command { void execute(); void undo(); }
final class WriteCommand implements Command {
  private final Editor ed; private final String chunk;
  WriteCommand(Editor ed, String chunk){ this.ed=ed; this.chunk=chunk; }
  public void execute(){ ed.write(chunk); } public void undo(){ ed.deleteLast(chunk.length()); }
}
final class CommandBus {
  private final Deque<Command> hist=new ArrayDeque<>();
  void run(Command c){ c.execute(); hist.push(c); }
  void undo(){ if(!hist.isEmpty()) hist.pop().undo(); }
}

```

### Stage 3 — Runnable main
```java
package com.buildwithwaqar.patterns.gof.behavioral.command.problem;

import java.util.*;
import java.util.concurrent.*;

public final class ProblemDemo {
  public static void main(String[] args) {
    System.out.println("=== Command: undoable editor ===");
    System.out.println("--- naive ---");
var n=new NaiveEditor(); n.write("Hi"); n.undo(); System.out.println(n.text());
System.out.println("--- command ---");
var ed=new Editor(); var bus=new CommandBus();
bus.run(new WriteCommand(ed,"Hello")); bus.run(new WriteCommand(ed,"!"));
System.out.println(ed.text()); bus.undo(); System.out.println("afterUndo="+ed.text());
  }
}

```

## Runnable Output (Verified on JDK 25)
Narrate each line. Do not invent numbers on camera.

```text
=== Command: undoable editor ===
--- naive ---
Hi
--- command ---
Hello!
afterUndo=Hello
```

Happy-path cue: `Hi`.
Failure-path cue: `Failure path is asserted in ProblemTest (injected fault / invalid input)`.
Fork the repo, set `JAVA_HOME` to JDK 25, and re-run the same `ProblemDemo` to verify.

## Modern Java 25 / Zazr Variant
For **Command**, prefer records for results, sealed types for closed variants, and Zazr `Option`/`Either`/`Validation` when the force is absence or accumulated errors. Keep the classic shape when interview literacy matters.

## Trade-offs
- **Clarity vs ceremony:** extra types for «Undoable editor keystrokes» only pay off when the force repeats.
- **Testability:** inject collaborators; assert the failure path, not only the happy path.
- **Distributed / concurrent reality:** demos omit metrics, backpressure, and multi-region clocks.
- **Refuse Command** when a sealed switch, a function value, or a simpler library call already erases the force.
- **Neighbor:** Strategy — Command is an objectified request with undo; Strategy is a swappable algorithm.

## Interview Q&A
**Q1. What concrete problem does Command solve here?**
A: Undoable editor keystrokes.

**Q2. What does the naive design get wrong?**
A: Naive version fails to cleanly solve: Undoable editor keystrokes

**Q3. How do you test the failure path?**
A: Inject the fault and assert the observable outcome — e.g. `Failure path is asserted in ProblemTest (injected fault / invalid input)`.

**Q4. When is Command the wrong tool?**
A: When a smaller language feature erases the force, or when you really needed Strategy.


## Key Takeaways
- Lead with the problem: Undoable editor keystrokes.
- Show naive pain (`Failure path is asserted in ProblemTest (injected fault / invalid input)`) before naming Command.
- Run the Demo; trust JDK 25 stdout (`Hi`).
- Teach when NOT to use Command.

## Video Script (≈10–12 minutes)
Record for **Build with Waqar**. OBS for capture, CapCut for assembly. Import `slides.pdf`.
Facecam: full-screen for the hook, framed PiP over code slides.

**[0:00]** *(slide 01 title + talking head)*
Hey everyone — today we solve one concrete problem with **Command**:
*Undoable editor keystrokes*. Keep these names in your head: Editor, Command, WriteCommand, CommandBus.

**[0:45]** *(problem + hook slides)*
I am not starting with a textbook definition. Here is the product pain. By the end you will have
seen the naive leak, the pattern boundary, and real JDK 25 stdout.

**[2:00]** *(naive code)*
IDE time. This is the naive version. Call out the smell — that is what Command exists to erase.

**[3:30]** *(structure diagram)*
Roles for **this** problem, not a generic UML poster. Point at each box while you talk.

**[4:30]** *(refactor / solution code)*
Stage the solution. Highlight failure injection and the tests that lock it in.

**[7:00]** *(walkthrough output)*
Real stdout. Happy path cue: `Hi`.
Failure cue: `Failure path is asserted in ProblemTest (injected fault / invalid input)`.
No invented numbers on camera.

**[8:30]** *(modern Java 25 / Zazr + trade-offs)*
Show the modernization, then slow down for when NOT to use Command. That is what seniors listen for.

**[10:30]** *(Q&A + recap + outro)*
Two interview questions, three takeaways, link the GitHub episode folder. I am Waqar — see you in the next one.


## Walkthrough Beats (Say These Out Loud)
1. **Setup:** open `series/16-command/` and `run-output.txt` beside the IDE.
2. **Naive:** reproduce the pain until you can point at `Failure path is asserted in ProblemTest (injected fault / invalid input)`.
3. **Structure:** on the diagram, trace `Client` → `Undoable`, `Undoable` → `editor`, `editor` → `Result`.
4. **Refactor:** encode the boundary, then run `ProblemDemo` — match `Hi`.
5. **Break it:** flip one `ProblemTest` assertion, watch red, restore it.
6. **Refuse:** Contrast with **Strategy**: Command is an objectified request with undo; Strategy is a swappable algorithm.

## Practice Drill
- Clone `waqarys/java-design-patterns`, set `JAVA_HOME` to JDK 25.
- Run the episode `ProblemDemo` (see `EP_MAIN` in `tools/enrich/build_problems.py`).
- Mutate one collaborator to fail; confirm the observable compensation / error path.
- Explain Command for «Undoable editor keystrokes» in sixty seconds without notes.
