# Tagless Final

**Season:** 7 · Advanced FP · **Category:** Functional · **Level:** Advanced · **Runtime:** ~10–12 min · **Channel:** Build with Waqar

## Problem
**User registration against UserRepo/Email/Logger algebras; IO + pure test interpreters**

Key types: `Algebras`, `User`, `Kind`, `Monad`, `UserRepo`, `EmailSender`.

**Tagless nuance:** algebras describe *what* (`UserRepo[F].save`, `EmailSender[F].send`); interpreters describe *how*. Write `register` once; run it under IO in prod and under a pure `TestState` in tests.

## Hook
Picture this: you are mid-demo and «User registration against UserRepo/Email/Logger algebras; IO + pure test interpreters» blows up the naive way — you see `Failure path is asserted in ProblemTest (injected fault / invalid input)`. That is the pain. In the next ten minutes we introduce **Tagless Final** with real types (Algebras, User, Kind), a runnable `ProblemDemo`, and the failure path locked in by tests. No abstract UML first — problem first.

## Intent
Write user registration once against algebras (`UserRepo[F]`, `EmailSender[F]`, `Logger[F]`) and run it under IO or a pure test interpreter.

**When to use:** the force behind «User registration against UserRepo/Email/Logger algebras; IO + pure test interpreters» shows up more than once and a simpler language feature does not erase it.

**When to refuse:** you are pattern-collecting, the boundary is still unclear, or the team cannot yet test the abstraction (compensations, half-open recovery, interpreters).

## The Pain — Naive Design
Naive: registration hard-wires Map DB + System.out email — untestable without mocks frameworks.

```java
package com.buildwithwaqar.patterns.functional.tagless.problem;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/** Naive: registration hard-wires Map DB + System.out email — untestable without mocks frameworks. */
public final class NaiveRegistration {
    private final Map<String, Algebras.User> db = new LinkedHashMap<>();
    private final List<String> outbox = new ArrayList<>();

    public Algebras.User register(String id, String email) {
        if (db.containsKey(email)) {
            System.out.println("dup " + email); // side effect baked in
            return db.get(email);
        }
        var u = new Algebras.User(id, email);
        db.put(email, u);
        outbox.add("welcome->" + email); // cannot swap for test assertion easily in unit style
        System.out.println("sent welcome to " + email);
        return u;
    }

    public List<String> outbox() { return List.copyOf(outbox); }
}

```

On camera, point at the smell: missing compensations, leaked XML, null at the boundary,
illegal transitions, or hard-wired `System.out` that you cannot assert.

## Pattern Structure
```mermaid
flowchart LR
  register[register] --> UserRepo_F_[UserRepo[F]]
  register[register] --> Email_F_[Email[F]]
  register[register] --> Logger_F_[Logger[F]]
  IO_interp[IO interp] --> register[register]
  Test_interp[Test interp] --> register[register]
```

These roles are for **«User registration against UserRepo/Email/Logger algebras; IO + pure test interpreters»** — not a generic Client→Pattern poster. Keep them on screen
while you type the solution.

## Refactor to the Pattern

### Stage 1 — Name the boundary
Introduce the client-facing API the rest of the app should depend on. Hide the incompatible or
distributed details behind it. For this episode that boundary is what makes «User registration against UserRepo/Email/Logger algebras; IO + pure test interpreters» testable.

### Stage 2 — Encode the solution
```java
package com.buildwithwaqar.patterns.functional.tagless.problem;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

/**
 * Tagless Final algebras for user registration.
 * Programs are written once against UserRepo/EmailSender/Logger; interpreters choose the effect.
 */
public final class Algebras {
    private Algebras() {}

    public record User(String id, String email) {}

    /** Minimal "effect" wrapper — either Identity (prod sync) or accumulating TestState. */
    public interface Kind<F, A> {
        A unsafeRun();
    }

    public interface Monad<F> {
        <A> Kind<F, A> pure(A a);
        <A, B> Kind<F, B> flatMap(Kind<F, A> fa, Function<A, Kind<F, B>> f);
        default <A, B> Kind<F, B> map(Kind<F, A> fa, Function<A, B> f) {
            return flatMap(fa, a -> pure(f.apply(a)));
        }
    }

    public interface UserRepo<F> {
        Kind<F, Optional<User>> findByEmail(String email);
        Kind<F, User> save(User user);
    }

    public interface EmailSender<F> {
        Kind<F, Void> send(String to, String subject, String body);
    }

    public interface Logger<F> {
        Kind<F, Void> info(String msg);
    }

    /** Shared registration program — no concrete effect type. */
    public static <F> Kind<F, User> register(
            Monad<F> M, UserRepo<F> users, EmailSender<F> email, Logger<F> log,
            String id, String address) {
        return M.flatMap(log.info("register " + address), ignored ->
                M.flatMap(users.findByEmail(address), existing -> {
                    if (existing.isPresent()) {
                        return M.flatMap(log.info("duplicate " + address), x ->
                                M.pure(existing.get())); // return existing — caller may treat as failure
                    }
                    var user = new User(id, address);
                    return M.flatMap(users.save(user), saved ->
                            M.flatMap(email.send(address, "Welcome", "Hi " + id), sent ->
                                    M.flatMap(log.info("registered " + id), y -> M.pure(saved))));
                }));
    }

    // ---- Identity / "IO-style" interpreter (eager, side-effecting) ----
    public enum IoWitness { W }
    public record Io<A>(A value) implements Kind<IoWitness, A> {
        public A unsafeRun() { return value; }
    }
    public static final Monad<IoWitness> IO = new Monad<>() {
        public <A> Kind<IoWitness, A> pure(A a) { return new Io<>(a); }
        public <A, B> Kind<IoWitness, B> flatMap(Kind<IoWitness, A> fa, Function<A, Kind<IoWitness, B>> f) {
            return f.apply(fa.unsafeRun());
        }
    };

    public static final class IoUserRepo implements UserRepo<IoWitness> {
        private final Map<String, User> db = new LinkedHashMap<>();
        public Kind<IoWitness, Optional<User>> findByEmail(String email) {
            return new Io<>(Optional.ofNullable(db.get(email)));
        }
        public Kind<IoWitness, User> save(User user) {
            db.put(user.email(), user);
            return new Io<>(user);
        }
 
```

### Stage 3 — Runnable main
```java
package com.buildwithwaqar.patterns.functional.tagless.problem;

/** Problem: same registration program under IO-style and pure test interpreters. */
public final class ProblemDemo {
    public static void main(String[] args) {
        System.out.println("=== Tagless Final: user registration algebras ===");
        System.out.println("--- naive (hard-wired Map + System.out) ---");
        var naive = new NaiveRegistration();
        System.out.println(naive.register("u1", "a@b.c"));
        System.out.println("outbox=" + naive.outbox());

        System.out.println("--- IO-style interpreter ---");
        var repo = new Algebras.IoUserRepo();
        var mail = new Algebras.IoEmail();
        var log = new Algebras.IoLogger();
        var user = Algebras.register(Algebras.IO, repo, mail, log, "u2", "x@y.z").unsafeRun();
        System.out.println("user=" + user);
        System.out.println("emails=" + mail.sent());
        System.out.println("logs=" + log.lines());

        System.out.println("--- pure test interpreter (shared state) ---");
        var state = Algebras.TestState.empty();
        var prog = Algebras.register(
                Algebras.TEST, new Algebras.TestUserRepo(), new Algebras.TestEmail(), new Algebras.TestLogger(),
                "u3", "t@est.io");
        var saved = ((Algebras.Test<Algebras.User>) prog).runWith(state);
        System.out.println("user=" + saved);
        System.out.println("emails=" + state.emails());
        System.out.println("logs=" + state.logs());
        System.out.println("db=" + state.users().keySet());
    }
}

```

## Runnable Output (Verified on JDK 25)
Narrate each line. Do not invent numbers on camera.

```text
=== Tagless Final: user registration algebras ===
--- naive (hard-wired Map + System.out) ---
sent welcome to a@b.c
User[id=u1, email=a@b.c]
outbox=[welcome->a@b.c]
--- IO-style interpreter ---
user=User[id=u2, email=x@y.z]
emails=[x@y.z|Welcome|Hi u2]
logs=[register x@y.z, registered u2]
--- pure test interpreter (shared state) ---
user=User[id=u3, email=t@est.io]
emails=[t@est.io|Welcome]
logs=[register t@est.io, registered u3]
db=[t@est.io]
```

Happy-path cue: `sent welcome to a@b.c`.
Failure-path cue: `Failure path is asserted in ProblemTest (injected fault / invalid input)`.
Fork the repo, set `JAVA_HOME` to JDK 25, and re-run the same `ProblemDemo` to verify.

## Modern Java 25 / Zazr Variant
**Records** carry the immutable results (`StepResult`, DTOs) with almost no boilerplate. Absence / errors use **Optional or Zazr/VAVR** control types instead of null. The same `register` program runs under an IO interpreter and a pure `TestState` interpreter — that is the whole point of abstracting `F`.

## Trade-offs
- **Clarity vs ceremony:** three algebras + two interpreters is worth it only if you need swap-able effects.
- **Testability:** pure `TestState` asserts emails/logs without mocking frameworks.
- **Refuse Tagless** for a one-off script — a plain service class is enough.
- **HKT emulation** (Kind witnesses) adds noise; keep algebras small.
- **Neighbor:** Free — Tagless Final is usually lighter in Java; Free shines when you need program inspection.

## Interview Q&A
**Q1. What is abstract here — the program or the effect?**
A: The effect `F`. `register` is written once; IO and TestState interpreters provide `UserRepo`/`Email`/`Logger`.

**Q2. Why not just mock with Mockito?**
A: A pure interpreter records emails/logs in data — no mocking framework, deterministic assertions.

**Q3. When is Tagless overkill?**
A: A single concrete service with constructor injection already gives you test seams.

**Q4. When is Tagless Final the wrong tool?**
A: When a smaller language feature erases the force, or when you really needed Free.


## Key Takeaways
- Lead with the problem: User registration against UserRepo/Email/Logger algebras; IO + pure test interpreters.
- Show naive pain (`Failure path is asserted in ProblemTest (injected fault / invalid input)`) before naming Tagless Final.
- Run the Demo; trust JDK 25 stdout (`sent welcome to a@b.c`).
- Teach when NOT to use Tagless Final.

## Video Script (≈10–12 minutes)
Record for **Build with Waqar**. OBS for capture, CapCut for assembly. Import `slides.pdf`.
Facecam: full-screen for the hook, framed PiP over code slides.

**[0:00]** *(slide 01 title + talking head)*
Hey everyone — today we solve one concrete problem with **Tagless Final**:
*User registration against UserRepo/Email/Logger algebras; IO + pure test interpreters*. Keep these names in your head: Algebras, User, Kind, Monad.

**[0:45]** *(problem + hook slides)*
I am not starting with a textbook definition. Here is the product pain. By the end you will have
seen the naive leak, the pattern boundary, and real JDK 25 stdout.

**[2:00]** *(naive code)*
IDE time. This is the naive version. Call out the smell — that is what Tagless Final exists to erase.

**[3:30]** *(structure diagram)*
Roles for **this** problem, not a generic UML poster. Point at each box while you talk.

**[4:30]** *(refactor / solution code)*
Stage the solution. Highlight failure injection and the tests that lock it in.

**[7:00]** *(walkthrough output)*
Real stdout. Happy path cue: `sent welcome to a@b.c`.
Failure cue: `Failure path is asserted in ProblemTest (injected fault / invalid input)`.
No invented numbers on camera.

**[8:30]** *(modern Java 25 / Zazr + trade-offs)*
Show the modernization, then slow down for when NOT to use Tagless Final. That is what seniors listen for.

**[10:30]** *(Q&A + recap + outro)*
Two interview questions, three takeaways, link the GitHub episode folder. I am Waqar — see you in the next one.

