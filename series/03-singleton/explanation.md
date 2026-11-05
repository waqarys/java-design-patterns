# Singleton

**Season:** 1 · Creational · **Category:** GoF · **Level:** Beginner · **Runtime:** ~10–12 min · **Channel:** Build with Waqar

## Problem
**One MetricsRegistry across request threads**

Key types: `singleton`, `AppConfig`.

## Hook
Picture this: you are mid-demo and «One MetricsRegistry across request threads» blows up the naive way — you see `Failure path is asserted in ProblemTest (injected fault / invalid input)`. That is the pain. In the next ten minutes we introduce **Singleton** with real types (singleton, AppConfig), a runnable `ProblemDemo`, and the failure path locked in by tests. No abstract UML first — problem first.

## Intent
Guarantee one `MetricsRegistry` identity across request threads without subtle double-checked bugs.

**When to use:** the force behind «One MetricsRegistry across request threads» shows up more than once and a simpler language feature does not erase it.

**When to refuse:** you are pattern-collecting, the boundary is still unclear, or the team cannot yet test the abstraction (compensations, half-open recovery, interpreters).

## The Pain — Naive Design
Naive: each service constructs its own counter — totals disagree.

```java
package com.buildwithwaqar.patterns.gof.creational.singleton.problem;

/** Naive: each service constructs its own counter — totals disagree. */
public final class NaiveMetrics {
    private long hits;
    public void hit() { hits++; }
    public long hits() { return hits; }
}

```

On camera, point at the smell: missing compensations, leaked XML, null at the boundary,
illegal transitions, or hard-wired `System.out` that you cannot assert.

## Pattern Structure
```mermaid
flowchart LR
  ThreadA[ThreadA] --> MetricsRegistry[MetricsRegistry]
  ThreadB[ThreadB] --> MetricsRegistry[MetricsRegistry]
```

These roles are for **«One MetricsRegistry across request threads»** — not a generic Client→Pattern poster. Keep them on screen
while you type the solution.

## Refactor to the Pattern

### Stage 1 — Name the boundary
Introduce the client-facing API the rest of the app should depend on. Hide the incompatible or
distributed details behind it. For this episode that boundary is what makes «One MetricsRegistry across request threads» testable.

### Stage 2 — Encode the solution
```java
package com.buildwithwaqar.patterns.gof.creational.singleton;

/** Preferred: enum singleton — serialization-safe, reflection-safe. */
public enum AppConfig {
    INSTANCE;

    private String env = "prod";

    public String env() { return env; }
    public void setEnv(String env) { this.env = env; }
    public String greeting() { return "hello from " + env; }
}

```

### Stage 3 — Runnable main
```java
package com.buildwithwaqar.patterns.gof.creational.singleton.problem;

public final class ProblemDemo {
    public static void main(String[] args) {
        System.out.println("=== Singleton: one MetricsRegistry ===");
        var a = new NaiveMetrics(); var b = new NaiveMetrics();
        a.hit(); a.hit(); b.hit();
        System.out.println("naiveA=" + a.hits() + " naiveB=" + b.hits());
        var reg = com.buildwithwaqar.patterns.gof.creational.singleton.MetricsRegistry.getInstance();
        reg.reset(); reg.hit(); reg.hit(); reg.hit();
        System.out.println("singletonHits=" + reg.hits());
        System.out.println("same=" + (reg == com.buildwithwaqar.patterns.gof.creational.singleton.MetricsRegistry.getInstance()));

    }
}

```

## Runnable Output (Verified on JDK 25)
Narrate each line. Do not invent numbers on camera.

```text
=== Singleton: one MetricsRegistry ===
naiveA=2 naiveB=1
singletonHits=3
same=true
```

Happy-path cue: `naiveA=2 naiveB=1`.
Failure-path cue: `Failure path is asserted in ProblemTest (injected fault / invalid input)`.
Fork the repo, set `JAVA_HOME` to JDK 25, and re-run the same `ProblemDemo` to verify.

## Modern Java 25 / Zazr Variant
For **Singleton**, prefer records for results, sealed types for closed variants, and Zazr `Option`/`Either`/`Validation` when the force is absence or accumulated errors. Keep the classic shape when interview literacy matters.

## Trade-offs
- **Clarity vs ceremony:** extra types for «One MetricsRegistry across request threads» only pay off when the force repeats.
- **Testability:** inject collaborators; assert the failure path, not only the happy path.
- **Distributed / concurrent reality:** demos omit metrics, backpressure, and multi-region clocks.
- **Refuse Singleton** when a sealed switch, a function value, or a simpler library call already erases the force.
- **Neighbor:** Factory Method — use a factory when creation varies; Singleton only when identity must be unique.

## Interview Q&A
**Q1. What concrete problem does Singleton solve here?**
A: One MetricsRegistry across request threads.

**Q2. What does the naive design get wrong?**
A: Naive: each service constructs its own counter — totals disagree.

**Q3. How do you test the failure path?**
A: Inject the fault and assert the observable outcome — e.g. `Failure path is asserted in ProblemTest (injected fault / invalid input)`.

**Q4. When is Singleton the wrong tool?**
A: When a smaller language feature erases the force, or when you really needed Factory Method.


## Key Takeaways
- Lead with the problem: One MetricsRegistry across request threads.
- Show naive pain (`Failure path is asserted in ProblemTest (injected fault / invalid input)`) before naming Singleton.
- Run the Demo; trust JDK 25 stdout (`naiveA=2 naiveB=1`).
- Teach when NOT to use Singleton.

## Video Script (≈10–12 minutes)
Record for **Build with Waqar**. OBS for capture, CapCut for assembly. Import `slides.pdf`.
Facecam: full-screen for the hook, framed PiP over code slides.

**[0:00]** *(slide 01 title + talking head)*
Hey everyone — today we solve one concrete problem with **Singleton**:
*One MetricsRegistry across request threads*. Keep these names in your head: singleton, AppConfig.

**[0:45]** *(problem + hook slides)*
I am not starting with a textbook definition. Here is the product pain. By the end you will have
seen the naive leak, the pattern boundary, and real JDK 25 stdout.

**[2:00]** *(naive code)*
IDE time. This is the naive version. Call out the smell — that is what Singleton exists to erase.

**[3:30]** *(structure diagram)*
Roles for **this** problem, not a generic UML poster. Point at each box while you talk.

**[4:30]** *(refactor / solution code)*
Stage the solution. Highlight failure injection and the tests that lock it in.

**[7:00]** *(walkthrough output)*
Real stdout. Happy path cue: `naiveA=2 naiveB=1`.
Failure cue: `Failure path is asserted in ProblemTest (injected fault / invalid input)`.
No invented numbers on camera.

**[8:30]** *(modern Java 25 / Zazr + trade-offs)*
Show the modernization, then slow down for when NOT to use Singleton. That is what seniors listen for.

**[10:30]** *(Q&A + recap + outro)*
Two interview questions, three takeaways, link the GitHub episode folder. I am Waqar — see you in the next one.


## Walkthrough Beats (Say These Out Loud)
1. **Setup:** open `series/03-singleton/` and `run-output.txt` beside the IDE.
2. **Naive:** reproduce the pain until you can point at `Failure path is asserted in ProblemTest (injected fault / invalid input)`.
3. **Structure:** on the diagram, trace `ThreadA` → `MetricsRegistry`, `ThreadB` → `MetricsRegistry`.
4. **Refactor:** encode the boundary, then run `ProblemDemo` — match `naiveA=2 naiveB=1`.
5. **Break it:** flip one `ProblemTest` assertion, watch red, restore it.
6. **Refuse:** Contrast with **Factory Method**: use a factory when creation varies; Singleton only when identity must be unique.

## Practice Drill
- Clone `waqarys/java-design-patterns`, set `JAVA_HOME` to JDK 25.
- Run the episode `ProblemDemo` (see `EP_MAIN` in `tools/enrich/build_problems.py`).
- Mutate one collaborator to fail; confirm the observable compensation / error path.
- Explain Singleton for «One MetricsRegistry across request threads» in sixty seconds without notes.
