# Circuit Breaker

**Season:** 4 · Enterprise · **Category:** Enterprise · **Level:** Intermediate · **Target runtime:** ~10–12 min · **Channel:** Build with Waqar

## Hook
Welcome back to **Build with Waqar**. In this episode we treat **Circuit Breaker** as a product problem inside StreamForge — our fictional creator platform — not as a trivia card. By the end you will have a runnable `Demo`, a hand-traced walkthrough with real JDK 25 stdout, a modern Java 25 or Zazr variant, trade-offs, related patterns, interview drills, and a full spoken script timed for OBS and CapCut.

If you only remember one sentence: patterns name **forces**. Circuit Breaker exists because a particular force kept showing up in real systems. We will feel that force in a naive design first, then earn the pattern.

## Intent
Circuit Breaker gives StreamForge a clear, teachable structure for a recurring design force. In interviews you should be able to state the force, sketch the roles, and — critically — say when you would refuse the pattern.

## Real-World Scenario (Runs Through the Whole Episode)
Picture StreamForge the week before a product launch. Creators upload tutorials, the dashboard flips between light and dark themes, notifications fan out to email/SMS/push, billing must not double-charge, and workers transcode video. Somewhere in that stack, the force behind **Circuit Breaker** appears: either object creation is messy, structure is rigid, collaborators are tangled, failures are noisy, concurrency is unsafe, or domain logic is impure.

We will stay inside one concrete story for this episode — the same names will appear in the naive code, the refactor stages, the Demo output, and the CapCut slides — so you can teach without swapping examples mid-video.

## The Pain — Naive Design
Before the pattern, teams usually reach for the most local fix: more `if` statements, more constructors, more shared mutable statics, more null checks, or more threads. That feels fast on day one and expensive on day thirty.

```java
// NAIVE sketch for Circuit Breaker
// (Illustrative — the live refactor uses the repository sources below.)
public class NaiveStreamForgeBit {
  // duplicated construction, leaked details, or unsafe shared state
  public Object doIt(Object... args) {
    if (args == null) return null;
    // copy-paste variant #4 lives in another class
    return args[0];
  }
}
```

Symptoms you should call out on camera: illegal states that still compile; tests that need `Thread.sleep`; god classes that know every subsystem; `instanceof` forests; null at API boundaries; or undo/retry logic scattered across controllers.

## Pattern Structure
```mermaid
flowchart LR
  Client --> Pattern
  Pattern --> Collaborators
```

Roles matter more than class counts. On the diagram slide we draw the collaboration for **Circuit Breaker** so viewers can point at boxes while you type.

## Refactor to the Pattern (Step by Step)

### Stage 1 — Name the roles
Identify Client, the pattern entry point, and the collaborators. Delete the shortcuts that couple them. In StreamForge this is usually where a controller stops `new`-ing infrastructure.

### Stage 2 — Encode the pattern in idiomatic Java 25
Here is the primary implementation from this repository (trimmed for the episode):

```java
// file: src/main/java/com/buildwithwaqar/patterns/enterprise/circuitbreaker/CircuitBreaker.java
package com.buildwithwaqar.patterns.enterprise.circuitbreaker;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.Callable;
import java.util.function.Supplier;

public final class CircuitBreaker {
    public enum State { CLOSED, OPEN, HALF_OPEN }

    private final int failureThreshold;
    private final Duration openTimeout;
    private final Supplier<Instant> clock;

    private State state = State.CLOSED;
    private int failures;
    private Instant openedAt = Instant.EPOCH;

    public CircuitBreaker(int failureThreshold, Duration openTimeout, Supplier<Instant> clock) {
        this.failureThreshold = failureThreshold;
        this.openTimeout = openTimeout;
        this.clock = clock;
    }

    public State state() { return state; }

    public <T> T call(Callable<T> action) throws Exception {
        if (state == State.OPEN) {
            if (clock.get().isAfter(openedAt.plus(openTimeout))) {
                state = State.HALF_OPEN;
            } else {
                throw new IllegalStateException("circuit open");
            }
        }
        try {
            var result = action.call();
            failures = 0;
            state = State.CLOSED;
            return result;
        } catch (Exception e) {
            failures++;
            if (failures >= failureThreshold || state == State.HALF_OPEN) {
                state = State.OPEN;
                openedAt = clock.get();
            }
            throw e;
        }
    }
}
```

Walk this top to bottom on camera. Highlight sealed types, records, and switch expressions when they appear. If the classic GoF encoding still exists beside a modern variant, show both and say which you would merge to `main` in a real service.

### Stage 3 — Make it runnable
Every pattern package ships a `Demo` with a `main`. That is what we run for the walkthrough — not a fictional console.

```java
package com.buildwithwaqar.patterns.enterprise.circuitbreaker;
import java.time.*;
import java.util.concurrent.atomic.AtomicReference;
public final class Demo {
  public static void main(String[] a) throws Exception {
    System.out.println("=== Circuit Breaker walkthrough ===");
    var now = new AtomicReference<>(Instant.parse("2026-01-01T00:00:00Z"));
    var cb = new CircuitBreaker(2, Duration.ofSeconds(30), now::get);
    for (int i = 0; i < 2; i++) {
      try { cb.call(() -> { throw new RuntimeException("down"); }); }
      catch (Exception e) { System.out.println("fail" + i + " state=" + cb.state()); }
    }
    try { cb.call(() -> "ok"); } catch (Exception e) { System.out.println("shortCircuit=" + e.getMessage()); }
    now.set(now.get().plusSeconds(31));
    System.out.println("afterTimeout=" + cb.call(() -> "ok") + " state=" + cb.state());
  }
}

```

## Hand-Traced Walkthrough (Verified on JDK 25)
We compiled with Temurin JDK 25 and executed the Demo. The stdout below is the **actual capture** stored in `run-output.txt` for this episode. Narrate each line; do not invent numbers on camera.

```text
=== Circuit Breaker walkthrough ===
fail0 state=CLOSED
fail1 state=OPEN
shortCircuit=circuit open
afterTimeout=ok state=CLOSED
```

Trace: match each printed line to a stage above. If a viewer forks the repo and sees different output, they should `git pull` `main` and re-run on JDK 25.

## Modern Java 25 / Zazr Variant
Classic encodings were fighting older Java. On Java 25 we prefer records for data, sealed interfaces for closed variants, pattern matching for exhaustive handling, virtual threads for blocking fan-out, and Zazr for Option/Either/Try/Validation and persistent collections. When **Circuit Breaker** has a functional dual, show it: Strategy as a function, Observer as a stream, Interpreter as Free/Tagless, null object as Option.

```java
// Modern direction for Circuit Breaker
// - prefer composition + sealed types over deep inheritance
// - prefer injecting interfaces at the edge over hidden singletons
// - prefer Zazr Option/Either/Validation over null
// - prefer virtual threads over hand-rolled pools for blocking work
```

If this episode already uses Zazr (Option, Either, Validation, Vector, Lazy), linger on the sealed `switch` — that is the Java 25 payoff versus older VAVR code.

## Trade-offs
- **Clarity vs ceremony:** a pattern that needs five types for a two-line problem is a fail.
- **Testability:** hidden globals and static singletons fight unit tests; inject instead.
- **Extensibility:** sealed hierarchies are closed; SPI plugins may need a different story.
- **Performance:** flyweight/caching help; premature pooling and speculative asynchrony hurt.
- **Distributed systems:** Saga/Outbox/Circuit Breaker demos are teaching-sized — production needs idempotency keys, metrics, and poison-pill handling.

## When to Use / When Not to Use
**Use when** the force behind Circuit Breaker shows up more than once and a simpler language feature does not erase it.

**Avoid when** you are pattern-collecting, when a sealed switch or a function value already communicates the design, or when the team cannot yet test the abstraction.

## Related Patterns
Neighbor patterns share forces. Contrast Circuit Breaker with its usual twin (for example Factory Method vs Abstract Factory, Strategy vs State, Proxy vs Decorator, Free vs Tagless Final). Say the distinguishing sentence out loud — that is an interview soundbite.

## Interview Q&A
**Q1. What force does Circuit Breaker resolve?**
A: State the force in product language (StreamForge), then in pattern language.

**Q2. How would you implement Circuit Breaker on Java 25 differently than in Java 8?**
A: Records, sealed types, pattern matching, virtual threads, Zazr control types — pick the ones that apply.

**Q3. When is Circuit Breaker the wrong tool?**
A: Give a concrete counterexample from StreamForge (too small, wrong boundary, fights the framework).

**Q4. How do you test it?**
A: Inject interfaces; use fixed clocks; capture Demo stdout; assert collaborator interactions.

**Q5. What production pitfall should seniors watch for?**
A: Concurrency, idempotency, resource lifetime, or over-abstraction — pick the honest one for this pattern.

**Q6. What related pattern is commonly confused with it?**
A: Name it and separate the forces.

**Q7. Can you sketch the roles from memory?**
A: Yes — Client, pattern entry, collaborators — matching the diagram slide.

## Key Takeaways
- Feel the StreamForge pain before naming Circuit Breaker.
- Refactor in stages; run the Demo; trust JDK 25 stdout.
- Modernize with Java 25 / Zazr where it shrinks ceremony.
- Teach trade-offs and "when not" as loudly as the happy path.

## Video Script (≈10–12 minutes)
Record for **Build with Waqar**. OBS for capture, CapCut for assembly. Import `slides.pdf` or the PNG sequence.

**[0:00]** *(slide 01 title)*  
Hey everyone, welcome back to Build with Waqar. Today we go deep on Circuit Breaker with working Java 25 code, a real Demo run, and slides you can drop straight into CapCut.

**[0:40]** *(slide 02 hook)*  
I am not going to start with a definition. I will start with a StreamForge product pain so you feel why this pattern exists.

**[1:30]** *(slide 03–04 problem/scenario)*  
Here is the scenario we will carry for the whole episode. Keep these names — they reappear in the Demo output.

**[3:00]** *(slide naive code + IDE)*  
Switch to the IDE. This is the naive version. Call out the smell: coupling, duplication, illegal states, or unsafe sharing.

**[4:30]** *(diagram slide)*  
Back to slides — structure diagram. Point at each role. If viewers remember only the boxes, they can still rebuild the code.

**[5:30]** *(IDE refactor stages)*  
Now we refactor stage by stage. Highlight sealed types, records, and tests as they appear.

**[7:30]** *(walkthrough output slide)*  
Here is the real stdout from JDK 25. I will read it line by line. No fake numbers.

**[8:45]** *(modern variant)*  
Modern Java 25 / Zazr take. Same force, less ceremony. Mention when you would still keep the classic shape for interview literacy.

**[9:45]** *(trade-offs + when not)*  
Slow down. This is the senior section. When would we refuse Circuit Breaker?

**[10:45]** *(interview Q&A)*  
Rapid-fire two interview questions. Encourage viewers to pause and answer aloud.

**[11:30]** *(recap + outro)*  
Recap three bullets, point to the GitHub folder for this episode, and tease the next pattern in the season. I am Waqar — I will see you in the next one. Like and subscribe if this helps your interview prep.
