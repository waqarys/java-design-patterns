# Factory Method

**Season:** 1 · Creational · **Category:** GoF · **Level:** Beginner · **Runtime:** ~10–12 min · **Channel:** Build with Waqar

## Problem
**Create Email/SMS/Push notifiers without client coupling**

Key types: `EmailNotifier`, `PushNotifier`.

## Hook
Picture this: you are mid-demo and «Create Email/SMS/Push notifiers without client coupling» blows up the naive way — you see `caught=unknown channel fax`. That is the pain. In the next ten minutes we introduce **Factory Method** with real types (EmailNotifier, PushNotifier), a runnable `ProblemDemo`, and the failure path locked in by tests. No abstract UML first — problem first.

## Intent
Use **Factory Method** to cleanly solve: Create Email/SMS/Push notifiers without client coupling.

**When to use:** the force behind «Create Email/SMS/Push notifiers without client coupling» shows up more than once and a simpler language feature does not erase it.

**When to refuse:** you are pattern-collecting, the boundary is still unclear, or the team cannot yet test the abstraction (compensations, half-open recovery, interpreters).

## The Pain — Naive Design
Naive: client switches on channel strings and news concrete types.

```java
package com.buildwithwaqar.patterns.gof.creational.factorymethod.problem;

/** Naive: client switches on channel strings and news concrete types. */
public final class NaiveNotify {
  public static String send(String channel, String to, String body) {
    if ("email".equals(channel)) return "email:" + to + ":" + body;
    if ("sms".equals(channel)) return "sms:" + to + ":" + body;
    if ("push".equals(channel)) return "push:" + to + ":" + body;
    throw new IllegalArgumentException("unknown channel " + channel);
  }
}

```

On camera, point at the smell: missing compensations, leaked XML, null at the boundary,
illegal transitions, or hard-wired `System.out` that you cannot assert.

## Pattern Structure
```mermaid
flowchart LR
  Client[Client] --> Create[Create]
  Create[Create] --> Email[Email]
  Email[Email] --> Result[Result]
```

These roles are for **«Create Email/SMS/Push notifiers without client coupling»** — not a generic Client→Pattern poster. Keep them on screen
while you type the solution.

## Refactor to the Pattern

### Stage 1 — Name the boundary
Introduce the client-facing API the rest of the app should depend on. Hide the incompatible or
distributed details behind it. For this episode that boundary is what makes «Create Email/SMS/Push notifiers without client coupling» testable.

### Stage 2 — Encode the solution
```java
package com.buildwithwaqar.patterns.gof.creational.factorymethod.problem;
public record EmailNotifier() implements Notifier {
  public String send(String to, String body) { return "email:" + to + ":" + body; }
}


package com.buildwithwaqar.patterns.gof.creational.factorymethod.problem;
public record PushNotifier() implements Notifier {
  public String send(String to, String body) { return "push:" + to + ":" + body; }
}

```

### Stage 3 — Runnable main
```java
package com.buildwithwaqar.patterns.gof.creational.factorymethod.problem;

public final class ProblemDemo {
  public static void main(String[] args) {
    System.out.println("=== Factory Method: Email/SMS/Push notifiers ===");
    System.out.println("--- naive ---");
    System.out.println(NaiveNotify.send("email", "a@b.c", "hi"));
    try { NaiveNotify.send("fax", "a", "x"); } catch (IllegalArgumentException e) { System.out.println("caught=" + e.getMessage()); }
    System.out.println("--- factory method ---");
    NotifierFactory email = new EmailFactory();
    NotifierFactory sms = new SmsFactory();
    System.out.println(email.notifyUser("a@b.c", "hi"));
    System.out.println(sms.notifyUser("+1", "hi"));
    System.out.println(new PushFactory().notifyUser("device", "hi"));
  }
}

```

## Runnable Output (Verified on JDK 25)
Narrate each line. Do not invent numbers on camera.

```text
=== Factory Method: Email/SMS/Push notifiers ===
--- naive ---
email:a@b.c:hi
caught=unknown channel fax
--- factory method ---
email:a@b.c:hi
sms:+1:hi
push:device:hi
```

Happy-path cue: `email:a@b.c:hi`.
Failure-path cue: `caught=unknown channel fax`.
Fork the repo, set `JAVA_HOME` to JDK 25, and re-run the same `ProblemDemo` to verify.

## Modern Java 25 / Zazr Variant
**Records** carry the immutable results (`StepResult`, DTOs) with almost no boilerplate.

## Trade-offs
- **Clarity vs ceremony:** extra types for «Create Email/SMS/Push notifiers without client coupling» only pay off when the force repeats.
- **Testability:** inject collaborators; assert the failure path, not only the happy path.
- **Distributed / concurrent reality:** demos omit metrics, backpressure, and multi-region clocks.
- **Refuse Factory Method** when a sealed switch, a function value, or a simpler library call already erases the force.
- **Neighbor:** Abstract Factory — Factory Method creates one product; Abstract Factory creates a family.

## Interview Q&A
**Q1. What concrete problem does Factory Method solve here?**
A: Create Email/SMS/Push notifiers without client coupling.

**Q2. What does the naive design get wrong?**
A: Naive: client switches on channel strings and news concrete types.

**Q3. How do you test the failure path?**
A: Inject the fault and assert the observable outcome — e.g. `caught=unknown channel fax`.

**Q4. When is Factory Method the wrong tool?**
A: When a smaller language feature erases the force, or when you really needed Abstract Factory.


## Key Takeaways
- Lead with the problem: Create Email/SMS/Push notifiers without client coupling.
- Show naive pain (`caught=unknown channel fax`) before naming Factory Method.
- Run the Demo; trust JDK 25 stdout (`email:a@b.c:hi`).
- Teach when NOT to use Factory Method.

## Video Script (≈10–12 minutes)
Record for **Build with Waqar**. OBS for capture, CapCut for assembly. Import `slides.pdf`.
Facecam: full-screen for the hook, framed PiP over code slides.

**[0:00]** *(slide 01 title + talking head)*
Hey everyone — today we solve one concrete problem with **Factory Method**:
*Create Email/SMS/Push notifiers without client coupling*. Keep these names in your head: EmailNotifier, PushNotifier.

**[0:45]** *(problem + hook slides)*
I am not starting with a textbook definition. Here is the product pain. By the end you will have
seen the naive leak, the pattern boundary, and real JDK 25 stdout.

**[2:00]** *(naive code)*
IDE time. This is the naive version. Call out the smell — that is what Factory Method exists to erase.

**[3:30]** *(structure diagram)*
Roles for **this** problem, not a generic UML poster. Point at each box while you talk.

**[4:30]** *(refactor / solution code)*
Stage the solution. Highlight failure injection and the tests that lock it in.

**[7:00]** *(walkthrough output)*
Real stdout. Happy path cue: `email:a@b.c:hi`.
Failure cue: `caught=unknown channel fax`.
No invented numbers on camera.

**[8:30]** *(modern Java 25 / Zazr + trade-offs)*
Show the modernization, then slow down for when NOT to use Factory Method. That is what seniors listen for.

**[10:30]** *(Q&A + recap + outro)*
Two interview questions, three takeaways, link the GitHub episode folder. I am Waqar — see you in the next one.


## Walkthrough Beats (Say These Out Loud)
1. **Setup:** open `series/04-factory-method/` and `run-output.txt` beside the IDE.
2. **Naive:** reproduce the pain until you can point at `caught=unknown channel fax`.
3. **Structure:** on the diagram, trace `Client` → `Create`, `Create` → `Email`, `Email` → `Result`.
4. **Refactor:** encode the boundary, then run `ProblemDemo` — match `email:a@b.c:hi`.
5. **Break it:** flip one `ProblemTest` assertion, watch red, restore it.
6. **Refuse:** Contrast with **Abstract Factory**: Factory Method creates one product; Abstract Factory creates a family.

## Practice Drill
- Clone `waqarys/java-design-patterns`, set `JAVA_HOME` to JDK 25.
- Run the episode `ProblemDemo` (see `EP_MAIN` in `tools/enrich/build_problems.py`).
- Mutate one collaborator to fail; confirm the observable compensation / error path.
- Explain Factory Method for «Create Email/SMS/Push notifiers without client coupling» in sixty seconds without notes.
