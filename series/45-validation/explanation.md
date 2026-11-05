# Validation

**Season:** 6 · FP Foundations · **Category:** Functional · **Level:** Intermediate · **Target runtime:** ~10–12 min · **Channel:** Build with Waqar · **Target runtime:** ~10–12 min · **Channel:** Build with Waqar · **Target runtime:** ~10–12 min · **Channel:** Build with Waqar · **Target runtime:** ~10–12 min · **Channel:** Build with Waqar

## Problem
**Signup form accumulates all field errors**

This episode is built around that concrete problem — not a shared StreamForge framing.
You will see a real naive implementation that hurts, a full pattern-based solution, a
runnable main with meaningful stdout, and JUnit failure-path coverage where the pattern lives.

## Hook
Welcome back to **Build with Waqar**. Today we earn **Validation** by solving one problem well:
*Signup form accumulates all field errors*. By the end you will have walked naive pain → pattern refactor → real JDK 25
stdout → trade-offs, with CapCut slides and a spoken script.

## Intent
Validation exists because this force kept showing up: signup form accumulates all field errors. Name the force, sketch
the roles, and — critically — know when you would refuse the pattern.

## The Pain — Naive Design
Before the pattern, teams usually thread details everywhere, leak resources on partial
failure, or couple call sites to a third-party shape. That feels fast on day one and expensive
on day thirty.

```java
package com.buildwithwaqar.patterns.functional.validation.problem;

import java.util.*;

public final class NaiveSignup {
  public static String signup(String email, int age){
    if(!email.contains("@")) throw new IllegalArgumentException("email");
    if(age<18) throw new IllegalArgumentException("age");
    return "ok:"+email;
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
package com.buildwithwaqar.patterns.functional.validation.problem;

import java.util.*;
import java.util.function.*;

sealed interface Validation<E,A> permits Valid, Invalid {
  boolean isValid(); A get(); List<E> errors();
  default <B> Validation<E,B> map(Function<A,B> f){ return isValid()? new Valid<>(f.apply(get())): new Invalid<>(errors()); }
  default <B> Validation<E,B> zipWith(Validation<E,B> other, BiFunction<A,B,?> ignored){ throw new UnsupportedOperationException(); }
}
record Valid<E,A>(A value) implements Validation<E,A> {
  public boolean isValid(){return true;} public A get(){return value;} public List<E> errors(){return List.of();}
}
record Invalid<E,A>(List<E> errs) implements Validation<E,A> {
  public boolean isValid(){return false;} public A get(){throw new IllegalStateException();} public List<E> errors(){return errs;}
}
final class Signup {
  static Validation<String,String> email(String e){ return e!=null && e.contains("@")? new Valid<>(e): new Invalid<>(List.of("email has no @")); }
  static Validation<String,Integer> age(int a){ return a>=18? new Valid<>(a): new Invalid<>(List.of("age under 18")); }
  static Validation<String,String> form(String e, int a){
    var ve=email(e); var va=age(a);
    if (ve.isValid() && va.isValid()) return new Valid<>("welcome "+ve.get());
    List<String> errs=new ArrayList<>(); errs.addAll(ve.errors()); errs.addAll(va.errors());
    return new Invalid<>(errs);
  }
}

```

### Stage 3 — Runnable main
```java
package com.buildwithwaqar.patterns.functional.validation.problem;
import java.util.*; import java.util.function.*;
public final class ProblemDemo {
  public static void main(String[] args) {
    System.out.println("=== Validation: signup accumulates errors ===");
    System.out.println("--- naive ---");
try { System.out.println(NaiveSignup.signup("bad", 10)); } catch(Exception e){ System.out.println("caught="+e.getMessage()); }
System.out.println("--- validation ---");
System.out.println(Signup.form("a@b.c", 20));
System.out.println(Signup.form("bad", 10));
  }
}

```

## Hand-Traced Walkthrough (Verified on JDK 25)
Narrate each line. Do not invent numbers on camera.

```text
=== Validation: signup accumulates errors ===
--- naive ---
caught=email
--- validation ---
Valid[value=welcome a@b.c]
Invalid[errs=[email has no @, age under 18]]
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
Contrast Validation with its usual twin (Adapter vs Facade, Saga vs Outbox, Reader vs DI/Cake,
State vs Strategy, Free vs Tagless). Say the distinguishing sentence out loud.

## Interview Q&A
**Q1. What concrete problem does Validation solve here?**
A: Signup form accumulates all field errors.

**Q2. What does the naive design get wrong?**
A: Point at the naive code smells from this episode.

**Q3. How do you test failure paths?**
A: Inject a failing collaborator (Saga step, SOAP fault, missing file) and assert the
observable outcome — compensation order, FAILED result, or thrown IllegalStateException.

**Q4. When is Validation the wrong tool?**
A: Give a smaller counterexample where a function, Option, or facade is enough.

**Q5. How does Java 25 change the encoding?**
A: Records, sealed types, virtual threads, Zazr control types — pick what applies.

## Key Takeaways
- Lead with the problem: Signup form accumulates all field errors.
- Show naive pain before naming Validation.
- Run the Demo; trust JDK 25 stdout.
- Teach when NOT to use it.

## Video Script (≈10–12 minutes)
Record for **Build with Waqar**. OBS for capture, CapCut for assembly. Import `slides.pdf`.

**[0:00]** *(slide 01 title)*
Hey everyone, welcome back to Build with Waqar. Today we solve a real problem with Validation:
Signup form accumulates all field errors.

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
Java 25 / Zazr take, then slow down for when NOT to use Validation.

**[10:30]** *(interview + recap)*
Two interview questions, three takeaways, link the GitHub episode folder. I am Waqar —
see you in the next one.
