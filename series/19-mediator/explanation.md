# Mediator

**Season:** 3 · Behavioral · **Category:** GoF · **Level:** Beginner · **Runtime:** ~10–12 min · **Channel:** Build with Waqar

## Problem
**Chat room without user-to-user coupling**

Key types: `ChatRoom`.

## Hook
Picture this: you are mid-demo and «Chat room without user-to-user coupling» blows up the naive way — you see `caught=missing:Cara`. That is the pain. In the next ten minutes we introduce **Mediator** with real types (ChatRoom), a runnable `ProblemDemo`, and the failure path locked in by tests. No abstract UML first — problem first.

## Intent
Use **Mediator** to cleanly solve: Chat room without user-to-user coupling.

**When to use:** the force behind «Chat room without user-to-user coupling» shows up more than once and a simpler language feature does not erase it.

**When to refuse:** you are pattern-collecting, the boundary is still unclear, or the team cannot yet test the abstraction (compensations, half-open recovery, interpreters).

## The Pain — Naive Design
Naive version fails to cleanly solve: Chat room without user-to-user coupling

```java
package com.buildwithwaqar.patterns.gof.behavioral.mediator.problem;

import java.util.*;

public final class NaiveChat {
  public static void dm(String from, String to, String msg){ System.out.println(from+"->"+to+":"+msg); }
}

```

On camera, point at the smell: missing compensations, leaked XML, null at the boundary,
illegal transitions, or hard-wired `System.out` that you cannot assert.

## Pattern Structure
```mermaid
flowchart LR
  Client[Client] --> Chat[Chat]
  Chat[Chat] --> room[room]
  room[room] --> Result[Result]
```

These roles are for **«Chat room without user-to-user coupling»** — not a generic Client→Pattern poster. Keep them on screen
while you type the solution.

## Refactor to the Pattern

### Stage 1 — Name the boundary
Introduce the client-facing API the rest of the app should depend on. Hide the incompatible or
distributed details behind it. For this episode that boundary is what makes «Chat room without user-to-user coupling» testable.

### Stage 2 — Encode the solution
```java
package com.buildwithwaqar.patterns.gof.behavioral.mediator.problem;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.util.function.*;

final class ChatRoom {
  private final Map<String, Consumer<String>> users = new HashMap<>();
  private final List<String> log = new ArrayList<>();
  void join(String name, Consumer<String> inbox){ users.put(name, inbox); }
  void send(String from, String to, String msg){
    var inbox = users.get(to);
    if (inbox==null) throw new IllegalArgumentException("missing:"+to);
    String line = from+"->"+to+":"+msg; log.add(line); inbox.accept(line);
  }
  List<String> log(){ return List.copyOf(log); }
}

```

### Stage 3 — Runnable main
```java
package com.buildwithwaqar.patterns.gof.behavioral.mediator.problem;
import java.util.*; import java.util.concurrent.*;
public final class ProblemDemo {
  public static void main(String[] args) throws Exception {
    System.out.println("=== Mediator: chat room ===");
    System.out.println("--- naive ---"); NaiveChat.dm("Ada","Bob","hi");
System.out.println("--- mediator ---");
var room=new ChatRoom(); List<String> bob=new ArrayList<>();
room.join("Ada", m->{}); room.join("Bob", bob::add);
room.send("Ada","Bob","hi");
System.out.println("bobInbox="+bob); System.out.println("log="+room.log());
try { room.send("Ada","Cara","x"); } catch (IllegalArgumentException e){ System.out.println("caught="+e.getMessage()); }
  }
}

```

## Runnable Output (Verified on JDK 25)
Narrate each line. Do not invent numbers on camera.

```text
=== Mediator: chat room ===
--- naive ---
Ada->Bob:hi
--- mediator ---
bobInbox=[Ada->Bob:hi]
log=[Ada->Bob:hi]
caught=missing:Cara
```

Happy-path cue: `Ada->Bob:hi`.
Failure-path cue: `caught=missing:Cara`.
Fork the repo, set `JAVA_HOME` to JDK 25, and re-run the same `ProblemDemo` to verify.

## Modern Java 25 / Zazr Variant
For **Mediator**, prefer records for results, sealed types for closed variants, and Zazr `Option`/`Either`/`Validation` when the force is absence or accumulated errors. Keep the classic shape when interview literacy matters.

## Trade-offs
- **Clarity vs ceremony:** extra types for «Chat room without user-to-user coupling» only pay off when the force repeats.
- **Testability:** inject collaborators; assert the failure path, not only the happy path.
- **Distributed / concurrent reality:** demos omit metrics, backpressure, and multi-region clocks.
- **Refuse Mediator** when a sealed switch, a function value, or a simpler library call already erases the force.
- **Neighbor:** Observer — Mediator centralizes colleague talk; Observer broadcasts events.

## Interview Q&A
**Q1. What concrete problem does Mediator solve here?**
A: Chat room without user-to-user coupling.

**Q2. What does the naive design get wrong?**
A: Naive version fails to cleanly solve: Chat room without user-to-user coupling

**Q3. How do you test the failure path?**
A: Inject the fault and assert the observable outcome — e.g. `caught=missing:Cara`.

**Q4. When is Mediator the wrong tool?**
A: When a smaller language feature erases the force, or when you really needed Observer.


## Key Takeaways
- Lead with the problem: Chat room without user-to-user coupling.
- Show naive pain (`caught=missing:Cara`) before naming Mediator.
- Run the Demo; trust JDK 25 stdout (`Ada->Bob:hi`).
- Teach when NOT to use Mediator.

## Video Script (≈10–12 minutes)
Record for **Build with Waqar**. OBS for capture, CapCut for assembly. Import `slides.pdf`.
Facecam: full-screen for the hook, framed PiP over code slides.

**[0:00]** *(slide 01 title + talking head)*
Hey everyone — today we solve one concrete problem with **Mediator**:
*Chat room without user-to-user coupling*. Keep these names in your head: ChatRoom.

**[0:45]** *(problem + hook slides)*
I am not starting with a textbook definition. Here is the product pain. By the end you will have
seen the naive leak, the pattern boundary, and real JDK 25 stdout.

**[2:00]** *(naive code)*
IDE time. This is the naive version. Call out the smell — that is what Mediator exists to erase.

**[3:30]** *(structure diagram)*
Roles for **this** problem, not a generic UML poster. Point at each box while you talk.

**[4:30]** *(refactor / solution code)*
Stage the solution. Highlight failure injection and the tests that lock it in.

**[7:00]** *(walkthrough output)*
Real stdout. Happy path cue: `Ada->Bob:hi`.
Failure cue: `caught=missing:Cara`.
No invented numbers on camera.

**[8:30]** *(modern Java 25 / Zazr + trade-offs)*
Show the modernization, then slow down for when NOT to use Mediator. That is what seniors listen for.

**[10:30]** *(Q&A + recap + outro)*
Two interview questions, three takeaways, link the GitHub episode folder. I am Waqar — see you in the next one.


## Walkthrough Beats (Say These Out Loud)
1. **Setup:** open `series/19-mediator/` and `run-output.txt` beside the IDE.
2. **Naive:** reproduce the pain until you can point at `caught=missing:Cara`.
3. **Structure:** on the diagram, trace `Client` → `Chat`, `Chat` → `room`, `room` → `Result`.
4. **Refactor:** encode the boundary, then run `ProblemDemo` — match `Ada->Bob:hi`.
5. **Break it:** flip one `ProblemTest` assertion, watch red, restore it.
6. **Refuse:** Contrast with **Observer**: Mediator centralizes colleague talk; Observer broadcasts events.

## Practice Drill
- Clone `waqarys/java-design-patterns`, set `JAVA_HOME` to JDK 25.
- Run the episode `ProblemDemo` (see `EP_MAIN` in `tools/enrich/build_problems.py`).
- Mutate one collaborator to fail; confirm the observable compensation / error path.
- Explain Mediator for «Chat room without user-to-user coupling» in sixty seconds without notes.
