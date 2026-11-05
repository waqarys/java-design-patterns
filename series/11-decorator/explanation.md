# Decorator

**Season:** 2 · Structural · **Category:** GoF · **Level:** Beginner · **Runtime:** ~10–12 min · **Channel:** Build with Waqar

## Problem
**Stack compression + encryption on a data store**

Key types: `DataSource`, `InMemoryDataSource`, `DataSourceDecorator`, `EncryptionDecorator`, `CompressionDecorator`.

## Hook
Picture this: you are mid-demo and «Stack compression + encryption on a data store» blows up the naive way — you see `Failure path is asserted in ProblemTest (injected fault / invalid input)`. That is the pain. In the next ten minutes we introduce **Decorator** with real types (DataSource, InMemoryDataSource, DataSourceDecorator), a runnable `ProblemDemo`, and the failure path locked in by tests. No abstract UML first — problem first.

## Intent
Use **Decorator** to cleanly solve: Stack compression + encryption on a data store.

**When to use:** the force behind «Stack compression + encryption on a data store» shows up more than once and a simpler language feature does not erase it.

**When to refuse:** you are pattern-collecting, the boundary is still unclear, or the team cannot yet test the abstraction (compensations, half-open recovery, interpreters).

## The Pain — Naive Design
Naive version fails to cleanly solve: Stack compression + encryption on a data store

```java
package com.buildwithwaqar.patterns.gof.structural.decorator.problem;

import java.util.*;

public final class NaiveStore {
  private String data="";
  public void write(String s){ data="enc("+s+")"; } // one hard-wired transform
  public String read(){ return data; }
}

```

On camera, point at the smell: missing compensations, leaked XML, null at the boundary,
illegal transitions, or hard-wired `System.out` that you cannot assert.

## Pattern Structure
```mermaid
flowchart LR
  Client[Client] --> Stack[Stack]
  Stack[Stack] --> compression[compression]
  compression[compression] --> Result[Result]
```

These roles are for **«Stack compression + encryption on a data store»** — not a generic Client→Pattern poster. Keep them on screen
while you type the solution.

## Refactor to the Pattern

### Stage 1 — Name the boundary
Introduce the client-facing API the rest of the app should depend on. Hide the incompatible or
distributed details behind it. For this episode that boundary is what makes «Stack compression + encryption on a data store» testable.

### Stage 2 — Encode the solution
```java
package com.buildwithwaqar.patterns.gof.structural.decorator.problem;

import java.util.*;

interface DataSource { String read(); void write(String data); }
final class InMemoryDataSource implements DataSource {
  private String data=""; public String read(){return data;} public void write(String d){data=d;}
}
abstract class DataSourceDecorator implements DataSource {
  protected final DataSource wrappee; protected DataSourceDecorator(DataSource w){ wrappee=w; }
  public String read(){ return wrappee.read(); } public void write(String d){ wrappee.write(d); }
}
final class EncryptionDecorator extends DataSourceDecorator {
  public EncryptionDecorator(DataSource w){ super(w); }
  public void write(String d){ wrappee.write("enc("+d+")"); }
  public String read(){ String s=wrappee.read(); return s.startsWith("enc(")? s.substring(4,s.length()-1):s; }
}
final class CompressionDecorator extends DataSourceDecorator {
  public CompressionDecorator(DataSource w){ super(w); }
  public void write(String d){ wrappee.write("zip("+d+")"); }
  public String read(){ String s=wrappee.read(); return s.startsWith("zip(")? s.substring(4,s.length()-1):s; }
}

```

### Stage 3 — Runnable main
```java
package com.buildwithwaqar.patterns.gof.structural.decorator.problem;

import java.util.*;

public final class ProblemDemo {
  public static void main(String[] args) {
    System.out.println("=== Decorator: compress+encrypt store ===");
    System.out.println("--- naive ---");
    var n=new NaiveStore(); n.write("secret"); System.out.println(n.read());
    System.out.println("--- decorator stack ---");
    var mem = new InMemoryDataSource();
    DataSource ds = new EncryptionDecorator(new CompressionDecorator(mem));
    ds.write("secret");
    System.out.println("stored-view=" + mem.read());
    System.out.println("read=" + ds.read());
  }
}

```

## Runnable Output (Verified on JDK 25)
Narrate each line. Do not invent numbers on camera.

```text
=== Decorator: compress+encrypt store ===
--- naive ---
enc(secret)
--- decorator stack ---
stored-view=zip(enc(secret))
read=secret
```

Happy-path cue: `enc(secret)`.
Failure-path cue: `Failure path is asserted in ProblemTest (injected fault / invalid input)`.
Fork the repo, set `JAVA_HOME` to JDK 25, and re-run the same `ProblemDemo` to verify.

## Modern Java 25 / Zazr Variant
For **Decorator**, prefer records for results, sealed types for closed variants, and Zazr `Option`/`Either`/`Validation` when the force is absence or accumulated errors. Keep the classic shape when interview literacy matters.

## Trade-offs
- **Clarity vs ceremony:** extra types for «Stack compression + encryption on a data store» only pay off when the force repeats.
- **Testability:** inject collaborators; assert the failure path, not only the happy path.
- **Distributed / concurrent reality:** demos omit metrics, backpressure, and multi-region clocks.
- **Refuse Decorator** when a sealed switch, a function value, or a simpler library call already erases the force.
- **Neighbor:** Proxy — Decorator stacks behavior; Proxy controls access or lifetime.

## Interview Q&A
**Q1. What concrete problem does Decorator solve here?**
A: Stack compression + encryption on a data store.

**Q2. What does the naive design get wrong?**
A: Naive version fails to cleanly solve: Stack compression + encryption on a data store

**Q3. How do you test the failure path?**
A: Inject the fault and assert the observable outcome — e.g. `Failure path is asserted in ProblemTest (injected fault / invalid input)`.

**Q4. When is Decorator the wrong tool?**
A: When a smaller language feature erases the force, or when you really needed Proxy.


## Key Takeaways
- Lead with the problem: Stack compression + encryption on a data store.
- Show naive pain (`Failure path is asserted in ProblemTest (injected fault / invalid input)`) before naming Decorator.
- Run the Demo; trust JDK 25 stdout (`enc(secret)`).
- Teach when NOT to use Decorator.

## Video Script (≈10–12 minutes)
Record for **Build with Waqar**. OBS for capture, CapCut for assembly. Import `slides.pdf`.
Facecam: full-screen for the hook, framed PiP over code slides.

**[0:00]** *(slide 01 title + talking head)*
Hey everyone — today we solve one concrete problem with **Decorator**:
*Stack compression + encryption on a data store*. Keep these names in your head: DataSource, InMemoryDataSource, DataSourceDecorator, EncryptionDecorator.

**[0:45]** *(problem + hook slides)*
I am not starting with a textbook definition. Here is the product pain. By the end you will have
seen the naive leak, the pattern boundary, and real JDK 25 stdout.

**[2:00]** *(naive code)*
IDE time. This is the naive version. Call out the smell — that is what Decorator exists to erase.

**[3:30]** *(structure diagram)*
Roles for **this** problem, not a generic UML poster. Point at each box while you talk.

**[4:30]** *(refactor / solution code)*
Stage the solution. Highlight failure injection and the tests that lock it in.

**[7:00]** *(walkthrough output)*
Real stdout. Happy path cue: `enc(secret)`.
Failure cue: `Failure path is asserted in ProblemTest (injected fault / invalid input)`.
No invented numbers on camera.

**[8:30]** *(modern Java 25 / Zazr + trade-offs)*
Show the modernization, then slow down for when NOT to use Decorator. That is what seniors listen for.

**[10:30]** *(Q&A + recap + outro)*
Two interview questions, three takeaways, link the GitHub episode folder. I am Waqar — see you in the next one.


## Walkthrough Beats (Say These Out Loud)
1. **Setup:** open `series/11-decorator/` and `run-output.txt` beside the IDE.
2. **Naive:** reproduce the pain until you can point at `Failure path is asserted in ProblemTest (injected fault / invalid input)`.
3. **Structure:** on the diagram, trace `Client` → `Stack`, `Stack` → `compression`, `compression` → `Result`.
4. **Refactor:** encode the boundary, then run `ProblemDemo` — match `enc(secret)`.
5. **Break it:** flip one `ProblemTest` assertion, watch red, restore it.
6. **Refuse:** Contrast with **Proxy**: Decorator stacks behavior; Proxy controls access or lifetime.

## Practice Drill
- Clone `waqarys/java-design-patterns`, set `JAVA_HOME` to JDK 25.
- Run the episode `ProblemDemo` (see `EP_MAIN` in `tools/enrich/build_problems.py`).
- Mutate one collaborator to fail; confirm the observable compensation / error path.
- Explain Decorator for «Stack compression + encryption on a data store» in sixty seconds without notes.
