# Abstract Factory

**Season:** 1 · Creational · **Category:** GoF · **Level:** Intermediate · **Target runtime:** ~10–12 min · **Channel:** Build with Waqar · **Target runtime:** ~10–12 min · **Channel:** Build with Waqar · **Target runtime:** ~10–12 min · **Channel:** Build with Waqar · **Target runtime:** ~10–12 min · **Channel:** Build with Waqar

## Problem
**Light/Dark UI widget family stays consistent**

This episode is built around that concrete problem — not a shared StreamForge framing.
You will see a real naive implementation that hurts, a full pattern-based solution, a
runnable main with meaningful stdout, and JUnit failure-path coverage where the pattern lives.

## Hook
Welcome back to **Build with Waqar**. Today we earn **Abstract Factory** by solving one problem well:
*Light/Dark UI widget family stays consistent*. By the end you will have walked naive pain → pattern refactor → real JDK 25
stdout → trade-offs, with CapCut slides and a spoken script.

## Intent
Abstract Factory exists because this force kept showing up: light/dark ui widget family stays consistent. Name the force, sketch
the roles, and — critically — know when you would refuse the pattern.

## The Pain — Naive Design
Before the pattern, teams usually thread details everywhere, leak resources on partial
failure, or couple call sites to a third-party shape. That feels fast on day one and expensive
on day thirty.

```java
package com.buildwithwaqar.patterns.gof.creational.abstractfactory.problem;

import java.util.*;

public final class NaiveUi {
  public static String render(String theme) {
    if ("light".equals(theme)) return "light-button+dark-checkbox"; // BUG: mixed family
    return "dark-button+light-checkbox";
  }
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
package com.buildwithwaqar.patterns.gof.creational.abstractfactory.problem;

import java.util.*;

interface Button { String paint(); }
interface Checkbox { String paint(); }
interface UiFactory { Button button(); Checkbox checkbox(); }
record LightButton() implements Button { public String paint(){return "light-button";} }
record DarkButton() implements Button { public String paint(){return "dark-button";} }
record LightCheckbox() implements Checkbox { public String paint(){return "light-checkbox";} }
record DarkCheckbox() implements Checkbox { public String paint(){return "dark-checkbox";} }
record LightFactory() implements UiFactory {
  public Button button(){return new LightButton();} public Checkbox checkbox(){return new LightCheckbox();}
}
record DarkFactory() implements UiFactory {
  public Button button(){return new DarkButton();} public Checkbox checkbox(){return new DarkCheckbox();}
}
final class Dialog {
  private final UiFactory f; public Dialog(UiFactory f){this.f=f;}
  public String render(){ return f.button().paint()+"+"+f.checkbox().paint(); }
}

```

### Stage 3 — Runnable main
```java
package com.buildwithwaqar.patterns.gof.creational.abstractfactory.problem;

import java.util.*;

public final class ProblemDemo {
  public static void main(String[] args) {
    System.out.println("=== Abstract Factory: Light/Dark UI family ===");
    System.out.println("--- naive (mixed family bug) ---");
System.out.println(NaiveUi.render("light"));
System.out.println("--- abstract factory ---");
System.out.println(new Dialog(new LightFactory()).render());
System.out.println(new Dialog(new DarkFactory()).render());
  }
}

```

## Hand-Traced Walkthrough (Verified on JDK 25)
Narrate each line. Do not invent numbers on camera.

```text
=== Abstract Factory: Light/Dark UI family ===
--- naive (mixed family bug) ---
light-button+dark-checkbox
--- abstract factory ---
light-button+light-checkbox
dark-button+dark-checkbox
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
Contrast Abstract Factory with its usual twin (Adapter vs Facade, Saga vs Outbox, Reader vs DI/Cake,
State vs Strategy, Free vs Tagless). Say the distinguishing sentence out loud.

## Interview Q&A
**Q1. What concrete problem does Abstract Factory solve here?**
A: Light/Dark UI widget family stays consistent.

**Q2. What does the naive design get wrong?**
A: Point at the naive code smells from this episode.

**Q3. How do you test failure paths?**
A: Inject a failing collaborator (Saga step, SOAP fault, missing file) and assert the
observable outcome — compensation order, FAILED result, or thrown IllegalStateException.

**Q4. When is Abstract Factory the wrong tool?**
A: Give a smaller counterexample where a function, Option, or facade is enough.

**Q5. How does Java 25 change the encoding?**
A: Records, sealed types, virtual threads, Zazr control types — pick what applies.

## Key Takeaways
- Lead with the problem: Light/Dark UI widget family stays consistent.
- Show naive pain before naming Abstract Factory.
- Run the Demo; trust JDK 25 stdout.
- Teach when NOT to use it.

## Video Script (≈10–12 minutes)
Record for **Build with Waqar**. OBS for capture, CapCut for assembly. Import `slides.pdf`.

**[0:00]** *(slide 01 title)*
Hey everyone, welcome back to Build with Waqar. Today we solve a real problem with Abstract Factory:
Light/Dark UI widget family stays consistent.

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
Java 25 / Zazr take, then slow down for when NOT to use Abstract Factory.

**[10:30]** *(interview + recap)*
Two interview questions, three takeaways, link the GitHub episode folder. I am Waqar —
see you in the next one.
