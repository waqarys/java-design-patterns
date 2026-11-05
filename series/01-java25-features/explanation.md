# Java 25 Features for Patterns

**Season:** 0 · Intro · **Category:** Language · **Level:** Beginner · **Runtime:** ~10–12 min · **Channel:** Build with Waqar

## Problem
**Model Payment ADT without illegal states**

Key types: `Payment`, `Card`, `Cash`, `Wire`, `Payments`.

> Intro episode: lighter showcase of the theme. Later episodes go full naive→pattern depth.

## Hook
Picture this: you are mid-demo and «Model Payment ADT without illegal states» blows up the naive way — you see `Failure path is asserted in ProblemTest (injected fault / invalid input)`. That is the pain. In the next ten minutes we introduce **Java 25 Features** with real types (Payment, Card, Cash), a runnable `ProblemDemo`, and the failure path locked in by tests. No abstract UML first — problem first.

## Intent
Use **Java 25 Features** to cleanly solve: Model Payment ADT without illegal states.

**When to use:** the force behind «Model Payment ADT without illegal states» shows up more than once and a simpler language feature does not erase it.

**When to refuse:** you are pattern-collecting, the boundary is still unclear, or the team cannot yet test the abstraction (compensations, half-open recovery, interpreters).

## The Pain — Naive Design
Naive version fails to cleanly solve: Model Payment ADT without illegal states

```java
package com.buildwithwaqar.patterns.functional.adt.problem;

import java.util.*;

public final class NaivePay {
  public static String describe(String type, int cents){
    if("card".equals(type)) return "card:"+cents;
    if("cash".equals(type)) return "cash:"+cents;
    return "other";
  }
}

```

On camera, point at the smell: missing compensations, leaked XML, null at the boundary,
illegal transitions, or hard-wired `System.out` that you cannot assert.

## Pattern Structure
```mermaid
flowchart LR
  Client[Client] --> Model[Model]
  Model[Model] --> Payment[Payment]
  Payment[Payment] --> Result[Result]
```

These roles are for **«Model Payment ADT without illegal states»** — not a generic Client→Pattern poster. Keep them on screen
while you type the solution.

## Refactor to the Pattern

### Stage 1 — Name the boundary
Introduce the client-facing API the rest of the app should depend on. Hide the incompatible or
distributed details behind it. For this episode that boundary is what makes «Model Payment ADT without illegal states» testable.

### Stage 2 — Encode the solution
```java
package com.buildwithwaqar.patterns.functional.adt.problem;

import java.util.*;
import java.util.function.*;

sealed interface Payment permits Card, Cash, Wire {}
record Card(String last4, int cents) implements Payment {}
record Cash(int cents) implements Payment {}
record Wire(String iban, int cents) implements Payment {}
final class Payments {
  static String describe(Payment p){
    return switch (p) {
      case Card(String last4, int cents) -> "card ****"+last4+" "+cents;
      case Cash(int cents) -> "cash "+cents;
      case Wire(String iban, int cents) -> "wire "+iban+" "+cents;
    };
  }
}

```

### Stage 3 — Runnable main
```java
package com.buildwithwaqar.patterns.functional.adt.problem;
import java.util.*; import java.util.function.*;
public final class ProblemDemo {
  public static void main(String[] args) {
    System.out.println("=== ADTs: exhaustive Payment describe ===");
    System.out.println("--- naive ---"); System.out.println(NaivePay.describe("card", 10));
System.out.println("--- ADT ---");
System.out.println(Payments.describe(new Card("1234", 1999)));
System.out.println(Payments.describe(new Cash(500)));
System.out.println(Payments.describe(new Wire("DE89", 10000)));
  }
}

```

## Runnable Output (Verified on JDK 25)
Narrate each line. Do not invent numbers on camera.

```text
=== ADTs: exhaustive Payment describe ===
--- naive ---
card:10
--- ADT ---
card ****1234 1999
cash 500
wire DE89 10000
```

Happy-path cue: `card:10`.
Failure-path cue: `Failure path is asserted in ProblemTest (injected fault / invalid input)`.
Fork the repo, set `JAVA_HOME` to JDK 25, and re-run the same `ProblemDemo` to verify.

## Modern Java 25 / Zazr Variant
This solution already leans on **sealed types** — keep the exhaustive `switch`. **Records** carry the immutable results (`StepResult`, DTOs) with almost no boilerplate.

## Trade-offs
- **Clarity vs ceremony:** extra types for «Model Payment ADT without illegal states» only pay off when the force repeats.
- **Testability:** inject collaborators; assert the failure path, not only the happy path.
- **Distributed / concurrent reality:** demos omit metrics, backpressure, and multi-region clocks.
- **Refuse Java 25 Features** when a sealed switch, a function value, or a simpler library call already erases the force.
- **Neighbor:** name the pattern usually confused with Java 25 Features and separate the forces.

## Interview Q&A
**Q1. What concrete problem does Java 25 Features solve here?**
A: Model Payment ADT without illegal states.

**Q2. What does the naive design get wrong?**
A: Naive version fails to cleanly solve: Model Payment ADT without illegal states

**Q3. How do you test the failure path?**
A: Inject the fault and assert the observable outcome — e.g. `Failure path is asserted in ProblemTest (injected fault / invalid input)`.

**Q4. When is Java 25 Features the wrong tool?**
A: When a smaller language feature erases the force, or when you really needed a neighboring pattern.


## Key Takeaways
- Lead with the problem: Model Payment ADT without illegal states.
- Show naive pain (`Failure path is asserted in ProblemTest (injected fault / invalid input)`) before naming Java 25 Features.
- Run the Demo; trust JDK 25 stdout (`card:10`).
- Teach when NOT to use Java 25 Features.

## Video Script (≈10–12 minutes)
Record for **Build with Waqar**. OBS for capture, CapCut for assembly. Import `slides.pdf`.
Facecam: full-screen for the hook, framed PiP over code slides.

**[0:00]** *(slide 01 title + talking head)*
Hey everyone — today we solve one concrete problem with **Java 25 Features**:
*Model Payment ADT without illegal states*. Keep these names in your head: Payment, Card, Cash, Wire.

**[0:45]** *(problem + hook slides)*
I am not starting with a textbook definition. Here is the product pain. By the end you will have
seen the naive leak, the pattern boundary, and real JDK 25 stdout.

**[2:00]** *(naive code)*
IDE time. This is the naive version. Call out the smell — that is what Java 25 Features exists to erase.

**[3:30]** *(structure diagram)*
Roles for **this** problem, not a generic UML poster. Point at each box while you talk.

**[4:30]** *(refactor / solution code)*
Stage the solution. Highlight failure injection and the tests that lock it in.

**[7:00]** *(walkthrough output)*
Real stdout. Happy path cue: `card:10`.
Failure cue: `Failure path is asserted in ProblemTest (injected fault / invalid input)`.
No invented numbers on camera.

**[8:30]** *(modern Java 25 / Zazr + trade-offs)*
Show the modernization, then slow down for when NOT to use Java 25 Features. That is what seniors listen for.

**[10:30]** *(Q&A + recap + outro)*
Two interview questions, three takeaways, link the GitHub episode folder. I am Waqar — see you in the next one.

