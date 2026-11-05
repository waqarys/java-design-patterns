# VAVR vs Zazr Deep Dive

**Season:** 7 · Advanced FP · **Category:** Functional · **Level:** Advanced · **Target runtime:** ~10–12 min · **Channel:** Build with Waqar · **Target runtime:** ~10–12 min · **Channel:** Build with Waqar · **Target runtime:** ~10–12 min · **Channel:** Build with Waqar · **Target runtime:** ~10–12 min · **Channel:** Build with Waqar

## Problem
**Same Option describe in both libraries**

This episode is built around that concrete problem — not a shared StreamForge framing.
You will see a real naive implementation that hurts, a full pattern-based solution, a
runnable main with meaningful stdout, and JUnit failure-path coverage where the pattern lives.

## Hook
Welcome back to **Build with Waqar**. Today we earn **VAVR vs Zazr** by solving one problem well:
*Same Option describe in both libraries*. By the end you will have walked naive pain → pattern refactor → real JDK 25
stdout → trade-offs, with CapCut slides and a spoken script.

## Intent
VAVR vs Zazr exists because this force kept showing up: same option describe in both libraries. Name the force, sketch
the roles, and — critically — know when you would refuse the pattern.

## The Pain — Naive Design
Before the pattern, teams usually thread details everywhere, leak resources on partial
failure, or couple call sites to a third-party shape. That feels fast on day one and expensive
on day thirty.

```java
package com.buildwithwaqar.patterns.functional.vavrcompare.problem;

import java.util.*;

public final class NaiveOpt {
  public static String describe(String s){ return s==null? "none": "some:"+s; }
}

```

Call out the smell on camera: duplicated envelopes, missing compensations, parameter drilling,
illegal transitions, or null at the boundary.

## Pattern Structure
```mermaid
flowchart LR
  Client --> Pattern
  Pattern --> Collaborators
```

Roles matter more than class counts. The diagram slide draws the collaboration for **this**
problem so viewers can point at boxes while you type.

## Refactor to the Pattern

### Stage 1 — Name the boundary
Identify the client-facing API the rest of the app should depend on. Hide the incompatible
or distributed details behind it.

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

## Hand-Traced Walkthrough (Verified on JDK 25)
Narrate each line. Do not invent numbers on camera.

```text
=== VAVR vs Zazr: real Option APIs ===
--- naive null String ---
none
some:Hi
--- describe (some/none) ---
vavr-some:x
zazr-some:x
vavr-none
zazr-none
--- map/flatMap pipeline ---
vavr-some:hello
zazr-some:hello
vavr-none
zazr-none
vavr-none
zazr-none
```

Trace: match each printed line to a stage above. Fork the repo, set `JAVA_HOME` to JDK 25,
and re-run the same main class to verify.

## Modern Java 25 / Zazr Variant
Prefer records for results, sealed types for closed variants, pattern matching for exhaustive
handling, virtual threads for blocking fan-out, and Zazr for Option/Either/Try/Validation
when the force is about absence or accumulated errors. Keep the classic shape when interview
literacy matters.

## Trade-offs
- **Clarity vs ceremony:** five types for a two-line problem is a fail.
- **Testability:** inject collaborators; for Saga, inject failure at each step.
- **Distributed reality:** teaching demos omit metrics, poison pills, and multi-region clocks.
- **Refuse the pattern** when a sealed switch, a function value, or a simpler library call
  already erases the force.

## When to Use / When Not to Use
**Use when** the problem above shows up more than once and a simpler language feature does
not erase it.

**Avoid when** you are pattern-collecting, when the boundary is still unclear, or when the
team cannot yet test the abstraction (especially compensations and half-open recovery).

## Related Patterns
Contrast VAVR vs Zazr with its usual twin (Adapter vs Facade, Saga vs Outbox, Reader vs DI/Cake,
State vs Strategy, Free vs Tagless). Say the distinguishing sentence out loud.

## Interview Q&A
**Q1. What concrete problem does VAVR vs Zazr solve here?**
A: Same Option describe in both libraries.

**Q2. What does the naive design get wrong?**
A: Point at the naive code smells from this episode.

**Q3. How do you test failure paths?**
A: Inject a failing collaborator (Saga step, SOAP fault, missing file) and assert the
observable outcome — compensation order, FAILED result, or thrown IllegalStateException.

**Q4. When is VAVR vs Zazr the wrong tool?**
A: Give a smaller counterexample where a function, Option, or facade is enough.

**Q5. How does Java 25 change the encoding?**
A: Records, sealed types, virtual threads, Zazr control types — pick what applies.

## Key Takeaways
- Lead with the problem: Same Option describe in both libraries.
- Show naive pain before naming VAVR vs Zazr.
- Run the Demo; trust JDK 25 stdout.
- Teach when NOT to use it.

## Video Script (≈10–12 minutes)
Record for **Build with Waqar**. OBS for capture, CapCut for assembly. Import `slides.pdf`.

**[0:00]** *(slide 01 title)*
Hey everyone, welcome back to Build with Waqar. Today we solve a real problem with VAVR vs Zazr:
Same Option describe in both libraries.

**[0:40]** *(slide 02–03 problem)*
I am not starting with a definition. Here is the product pain. Keep these names — they
reappear in the Demo output.

**[2:00]** *(naive code)*
IDE time. This is the naive version. Call out the smell.

**[3:30]** *(diagram)*
Structure for **this** problem. Point at each role.

**[4:30]** *(refactor stages)*
Stage by stage into the pattern. Highlight tests and failure injection.

**[7:00]** *(walkthrough output)*
Real stdout from JDK 25. Line by line. No fake numbers.

**[8:30]** *(modern + trade-offs)*
Java 25 / Zazr take, then slow down for when NOT to use VAVR vs Zazr.

**[10:30]** *(interview + recap)*
Two interview questions, three takeaways, link the GitHub episode folder. I am Waqar —
see you in the next one.
