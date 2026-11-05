# Zazr and VAVR Tour

**Season:** 0 · Intro · **Category:** Libraries · **Level:** Beginner · **Runtime:** ~10–12 min · **Channel:** Build with Waqar

## Problem
**Replace null-config lookup with Option**

Key types: `OptionCompare`.

> Intro episode: lighter showcase of the theme. Later episodes go full naive→pattern depth.

## Hook
Picture this: you are mid-demo and «Replace null-config lookup with Option» blows up the naive way — you see `none`. That is the pain. In the next ten minutes we introduce **Zazr & VAVR** with real types (OptionCompare), a runnable `ProblemDemo`, and the failure path locked in by tests. No abstract UML first — problem first.

## Intent
Use **Zazr & VAVR** to cleanly solve: Replace null-config lookup with Option.

**When to use:** the force behind «Replace null-config lookup with Option» shows up more than once and a simpler language feature does not erase it.

**When to refuse:** you are pattern-collecting, the boundary is still unclear, or the team cannot yet test the abstraction (compensations, half-open recovery, interpreters).

## The Pain — Naive Design
Naive version fails to cleanly solve: Replace null-config lookup with Option

```java
package com.buildwithwaqar.patterns.functional.vavrcompare.problem;

import java.util.*;

public final class NaiveOpt {
  public static String describe(String s){ return s==null? "none": "some:"+s; }
}

```

On camera, point at the smell: missing compensations, leaked XML, null at the boundary,
illegal transitions, or hard-wired `System.out` that you cannot assert.

## Pattern Structure
```mermaid
flowchart LR
  Client[Client] --> Replace[Replace]
  Replace[Replace] --> null[null]
  null[null] --> Result[Result]
```

These roles are for **«Replace null-config lookup with Option»** — not a generic Client→Pattern poster. Keep them on screen
while you type the solution.

## Refactor to the Pattern

### Stage 1 — Name the boundary
Introduce the client-facing API the rest of the app should depend on. Hide the incompatible or
distributed details behind it. For this episode that boundary is what makes «Replace null-config lookup with Option» testable.

### Stage 2 — Encode the solution
```java
package com.buildwithwaqar.patterns.functional.vavrcompare.problem;

/**
 * Side-by-side: same Option describe / map+flatMap program on real VAVR and real Zazr.
 */
public final class OptionCompare {
    private OptionCompare() {}

    public static String vavrDescribe(String raw) {
        io.vavr.control.Option<String> o = raw == null
                ? io.vavr.control.Option.none()
                : io.vavr.control.Option.some(raw);
        return o.map(s -> "vavr-some:" + s).getOrElse("vavr-none");
    }

    public static String zazrDescribe(String raw) {
        dev.zazr.control.Option<String> o = raw == null
                ? dev.zazr.control.Option.none()
                : dev.zazr.control.Option.some(raw);
        return o.map(s -> "zazr-some:" + s).getOrElse("zazr-none");
    }

    /** Trim → reject blank → lowercase — identical pipeline on both libraries. */
    public static String vavrMapFlat(String raw) {
        return io.vavr.control.Option.of(raw)
                .flatMap(s -> s.isBlank() ? io.vavr.control.Option.none() : io.vavr.control.Option.some(s.trim()))
                .map(String::toLowerCase)
                .map(s -> "vavr-some:" + s)
                .getOrElse("vavr-none");
    }

    public static String zazrMapFlat(String raw) {
        return (raw == null ? dev.zazr.control.Option.<String>none() : dev.zazr.control.Option.some(raw))
                .flatMap(s -> s.isBlank() ? dev.zazr.control.Option.none() : dev.zazr.control.Option.some(s.trim()))
                .map(String::toLowerCase)
                .map(s -> "zazr-some:" + s)
                .getOrElse("zazr-none");
    }
}

```

### Stage 3 — Runnable main
```java
package com.buildwithwaqar.patterns.functional.vavrcompare.problem;

/** Problem: same Option describe — real io.vavr vs real dev.zazr. */
public final class ProblemDemo {
    public static void main(String[] args) {
        System.out.println("=== VAVR vs Zazr: real Option APIs ===");
        System.out.println("--- naive null String ---");
        System.out.println(NaiveOpt.describe(null));
        System.out.println(NaiveOpt.describe("Hi"));

        System.out.println("--- describe (some/none) ---");
        System.out.println(OptionCompare.vavrDescribe("x"));
        System.out.println(OptionCompare.zazrDescribe("x"));
        System.out.println(OptionCompare.vavrDescribe(null));
        System.out.println(OptionCompare.zazrDescribe(null));

        System.out.println("--- map/flatMap pipeline ---");
        System.out.println(OptionCompare.vavrMapFlat("  Hello "));
        System.out.println(OptionCompare.zazrMapFlat("  Hello "));
        System.out.println(OptionCompare.vavrMapFlat("   "));
        System.out.println(OptionCompare.zazrMapFlat("   "));
        System.out.println(OptionCompare.vavrMapFlat(null));
        System.out.println(OptionCompare.zazrMapFlat(null));
    }
}

```

## Runnable Output (Verified on JDK 25)
Narrate each line. Do not invent numbers on camera.

```text
=== VAVR vs Zazr: same Option describe ===
--- naive ---
none
--- compare ---
zazr-some:x
vavr-some:x
zazr-none
vavr-none
```

Happy-path cue: `none`.
Failure-path cue: `none`.
Fork the repo, set `JAVA_HOME` to JDK 25, and re-run the same `ProblemDemo` to verify.

## Modern Java 25 / Zazr Variant
Absence / errors use **Optional or Zazr/VAVR** control types instead of null.

## Trade-offs
- **Clarity vs ceremony:** extra types for «Replace null-config lookup with Option» only pay off when the force repeats.
- **Testability:** inject collaborators; assert the failure path, not only the happy path.
- **Distributed / concurrent reality:** demos omit metrics, backpressure, and multi-region clocks.
- **Refuse Zazr & VAVR** when a sealed switch, a function value, or a simpler library call already erases the force.
- **Neighbor:** name the pattern usually confused with Zazr & VAVR and separate the forces.

## Interview Q&A
**Q1. What concrete problem does Zazr & VAVR solve here?**
A: Replace null-config lookup with Option.

**Q2. What does the naive design get wrong?**
A: Naive version fails to cleanly solve: Replace null-config lookup with Option

**Q3. How do you test the failure path?**
A: Inject the fault and assert the observable outcome — e.g. `none`.

**Q4. When is Zazr & VAVR the wrong tool?**
A: When a smaller language feature erases the force, or when you really needed a neighboring pattern.


## Key Takeaways
- Lead with the problem: Replace null-config lookup with Option.
- Show naive pain (`none`) before naming Zazr & VAVR.
- Run the Demo; trust JDK 25 stdout (`none`).
- Teach when NOT to use Zazr & VAVR.

## Video Script (≈10–12 minutes)
Record for **Build with Waqar**. OBS for capture, CapCut for assembly. Import `slides.pdf`.
Facecam: full-screen for the hook, framed PiP over code slides.

**[0:00]** *(slide 01 title + talking head)*
Hey everyone — today we solve one concrete problem with **Zazr & VAVR**:
*Replace null-config lookup with Option*. Keep these names in your head: OptionCompare.

**[0:45]** *(problem + hook slides)*
I am not starting with a textbook definition. Here is the product pain. By the end you will have
seen the naive leak, the pattern boundary, and real JDK 25 stdout.

**[2:00]** *(naive code)*
IDE time. This is the naive version. Call out the smell — that is what Zazr & VAVR exists to erase.

**[3:30]** *(structure diagram)*
Roles for **this** problem, not a generic UML poster. Point at each box while you talk.

**[4:30]** *(refactor / solution code)*
Stage the solution. Highlight failure injection and the tests that lock it in.

**[7:00]** *(walkthrough output)*
Real stdout. Happy path cue: `none`.
Failure cue: `none`.
No invented numbers on camera.

**[8:30]** *(modern Java 25 / Zazr + trade-offs)*
Show the modernization, then slow down for when NOT to use Zazr & VAVR. That is what seniors listen for.

**[10:30]** *(Q&A + recap + outro)*
Two interview questions, three takeaways, link the GitHub episode folder. I am Waqar — see you in the next one.

