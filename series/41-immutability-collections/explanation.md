# Immutability & Persistent Collections

**Season:** 6 · FP Foundations · **Category:** Functional · **Level:** Intermediate · **Runtime:** ~10–12 min · **Channel:** Build with Waqar

## Problem
**Immutable playlist edits sharing structure**

Key types: `Playlist`.

## Hook
Picture this: you are mid-demo and «Immutable playlist edits sharing structure» blows up the naive way — you see `Failure path is asserted in ProblemTest (injected fault / invalid input)`. That is the pain. In the next ten minutes we introduce **Persistent Collections** with real types (Playlist), a runnable `ProblemDemo`, and the failure path locked in by tests. No abstract UML first — problem first.

## Intent
Use **Persistent Collections** to cleanly solve: Immutable playlist edits sharing structure.

**When to use:** the force behind «Immutable playlist edits sharing structure» shows up more than once and a simpler language feature does not erase it.

**When to refuse:** you are pattern-collecting, the boundary is still unclear, or the team cannot yet test the abstraction (compensations, half-open recovery, interpreters).

## The Pain — Naive Design
Naive version fails to cleanly solve: Immutable playlist edits sharing structure

```java
package com.buildwithwaqar.patterns.functional.collections.problem;

import java.util.*;

public final class NaivePlaylist {
  public final List<String> songs=new ArrayList<>();
  public void add(String s){ songs.add(s); }
}

```

On camera, point at the smell: missing compensations, leaked XML, null at the boundary,
illegal transitions, or hard-wired `System.out` that you cannot assert.

## Pattern Structure
```mermaid
flowchart LR
  Client[Client] --> Immutable[Immutable]
  Immutable[Immutable] --> playlist[playlist]
  playlist[playlist] --> Result[Result]
```

These roles are for **«Immutable playlist edits sharing structure»** — not a generic Client→Pattern poster. Keep them on screen
while you type the solution.

## Refactor to the Pattern

### Stage 1 — Name the boundary
Introduce the client-facing API the rest of the app should depend on. Hide the incompatible or
distributed details behind it. For this episode that boundary is what makes «Immutable playlist edits sharing structure» testable.

### Stage 2 — Encode the solution
```java
package com.buildwithwaqar.patterns.functional.collections.problem;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.util.concurrent.locks.*;
import java.util.function.*;

final class Playlist {
  private final List<String> songs;
  Playlist(List<String> songs){ this.songs=List.copyOf(songs); }
  Playlist add(String s){ var n=new ArrayList<>(songs); n.add(s); return new Playlist(n); }
  List<String> songs(){ return songs; }
}

```

### Stage 3 — Runnable main
```java
package com.buildwithwaqar.patterns.functional.collections.problem;
import java.util.*; import java.util.concurrent.*; import java.util.concurrent.atomic.*;
public final class ProblemDemo {
  public static void main(String[] args) throws Exception {
    System.out.println("=== Persistent collections: playlist edits ===");
    System.out.println("--- naive ---"); var n=new NaivePlaylist(); n.add("a"); System.out.println(n.songs);
System.out.println("--- persistent ---");
var p=new Playlist(List.of("a")); var p2=p.add("b");
System.out.println(p.songs()); System.out.println(p2.songs());
  }
}

```

## Runnable Output (Verified on JDK 25)
Narrate each line. Do not invent numbers on camera.

```text
=== Persistent collections: playlist edits ===
--- naive ---
[a]
--- persistent ---
[a]
[a, b]
```

Happy-path cue: `[a]`.
Failure-path cue: `Failure path is asserted in ProblemTest (injected fault / invalid input)`.
Fork the repo, set `JAVA_HOME` to JDK 25, and re-run the same `ProblemDemo` to verify.

## Modern Java 25 / Zazr Variant
For **Persistent Collections**, prefer records for results, sealed types for closed variants, and Zazr `Option`/`Either`/`Validation` when the force is absence or accumulated errors. Keep the classic shape when interview literacy matters.

## Trade-offs
- **Clarity vs ceremony:** extra types for «Immutable playlist edits sharing structure» only pay off when the force repeats.
- **Testability:** inject collaborators; assert the failure path, not only the happy path.
- **Distributed / concurrent reality:** demos omit metrics, backpressure, and multi-region clocks.
- **Refuse Persistent Collections** when a sealed switch, a function value, or a simpler library call already erases the force.
- **Neighbor:** name the pattern usually confused with Persistent Collections and separate the forces.

## Interview Q&A
**Q1. What concrete problem does Persistent Collections solve here?**
A: Immutable playlist edits sharing structure.

**Q2. What does the naive design get wrong?**
A: Naive version fails to cleanly solve: Immutable playlist edits sharing structure

**Q3. How do you test the failure path?**
A: Inject the fault and assert the observable outcome — e.g. `Failure path is asserted in ProblemTest (injected fault / invalid input)`.

**Q4. When is Persistent Collections the wrong tool?**
A: When a smaller language feature erases the force, or when you really needed a neighboring pattern.


## Key Takeaways
- Lead with the problem: Immutable playlist edits sharing structure.
- Show naive pain (`Failure path is asserted in ProblemTest (injected fault / invalid input)`) before naming Persistent Collections.
- Run the Demo; trust JDK 25 stdout (`[a]`).
- Teach when NOT to use Persistent Collections.

## Video Script (≈10–12 minutes)
Record for **Build with Waqar**. OBS for capture, CapCut for assembly. Import `slides.pdf`.
Facecam: full-screen for the hook, framed PiP over code slides.

**[0:00]** *(slide 01 title + talking head)*
Hey everyone — today we solve one concrete problem with **Persistent Collections**:
*Immutable playlist edits sharing structure*. Keep these names in your head: Playlist.

**[0:45]** *(problem + hook slides)*
I am not starting with a textbook definition. Here is the product pain. By the end you will have
seen the naive leak, the pattern boundary, and real JDK 25 stdout.

**[2:00]** *(naive code)*
IDE time. This is the naive version. Call out the smell — that is what Persistent Collections exists to erase.

**[3:30]** *(structure diagram)*
Roles for **this** problem, not a generic UML poster. Point at each box while you talk.

**[4:30]** *(refactor / solution code)*
Stage the solution. Highlight failure injection and the tests that lock it in.

**[7:00]** *(walkthrough output)*
Real stdout. Happy path cue: `[a]`.
Failure cue: `Failure path is asserted in ProblemTest (injected fault / invalid input)`.
No invented numbers on camera.

**[8:30]** *(modern Java 25 / Zazr + trade-offs)*
Show the modernization, then slow down for when NOT to use Persistent Collections. That is what seniors listen for.

**[10:30]** *(Q&A + recap + outro)*
Two interview questions, three takeaways, link the GitHub episode folder. I am Waqar — see you in the next one.


## Walkthrough Beats (Say These Out Loud)
1. **Setup:** open `series/41-immutability-collections/` and `run-output.txt` beside the IDE.
2. **Naive:** reproduce the pain until you can point at `Failure path is asserted in ProblemTest (injected fault / invalid input)`.
3. **Structure:** on the diagram, trace `Client` → `Immutable`, `Immutable` → `playlist`, `playlist` → `Result`.
4. **Refactor:** encode the boundary, then run `ProblemDemo` — match `[a]`.
5. **Break it:** flip one `ProblemTest` assertion, watch red, restore it.
6. **Refuse:** Name the neighbor pattern usually confused with Persistent Collections.

## Practice Drill
- Clone `waqarys/java-design-patterns`, set `JAVA_HOME` to JDK 25.
- Run the episode `ProblemDemo` (see `EP_MAIN` in `tools/enrich/build_problems.py`).
- Mutate one collaborator to fail; confirm the observable compensation / error path.
- Explain Persistent Collections for «Immutable playlist edits sharing structure» in sixty seconds without notes.
