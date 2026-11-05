# Lenses & Optics

**Season:** 7 · Advanced FP · **Category:** Functional · **Level:** Advanced · **Runtime:** ~10–12 min · **Channel:** Build with Waqar

## Problem
**Update nested city in immutable Person/Address**

Key types: `Address`, `Person`, `Lens`, `PersonLenses`.

## Hook
Picture this: you are mid-demo and «Update nested city in immutable Person/Address» blows up the naive way — you see `Failure path is asserted in ProblemTest (injected fault / invalid input)`. That is the pain. In the next ten minutes we introduce **Lenses** with real types (Address, Person, Lens), a runnable `ProblemDemo`, and the failure path locked in by tests. No abstract UML first — problem first.

## Intent
Use **Lenses** to cleanly solve: Update nested city in immutable Person/Address.

**When to use:** the force behind «Update nested city in immutable Person/Address» shows up more than once and a simpler language feature does not erase it.

**When to refuse:** you are pattern-collecting, the boundary is still unclear, or the team cannot yet test the abstraction (compensations, half-open recovery, interpreters).

## The Pain — Naive Design
Naive version fails to cleanly solve: Update nested city in immutable Person/Address

```java
package com.buildwithwaqar.patterns.functional.lens.problem;

import java.util.*;

public final class NaivePerson {
  public static String move(String personJson, String city){ return personJson.replace("Old", city); }
}

```

On camera, point at the smell: missing compensations, leaked XML, null at the boundary,
illegal transitions, or hard-wired `System.out` that you cannot assert.

## Pattern Structure
```mermaid
flowchart LR
  Client[Client] --> Update[Update]
  Update[Update] --> nested[nested]
  nested[nested] --> Result[Result]
```

These roles are for **«Update nested city in immutable Person/Address»** — not a generic Client→Pattern poster. Keep them on screen
while you type the solution.

## Refactor to the Pattern

### Stage 1 — Name the boundary
Introduce the client-facing API the rest of the app should depend on. Hide the incompatible or
distributed details behind it. For this episode that boundary is what makes «Update nested city in immutable Person/Address» testable.

### Stage 2 — Encode the solution
```java
package com.buildwithwaqar.patterns.functional.lens.problem;

import java.util.*;
import java.util.function.*;

record Address(String city, String zip){}
record Person(String name, Address address){}
record Lens<S,A>(Function<S,A> get, BiFunction<S,A,S> set){
  A getA(S s){ return get.apply(s); }
  S setA(S s, A a){ return set.apply(s,a); }
  <B> Lens<S,B> andThen(Lens<A,B> other){
    return new Lens<>(s -> other.get.apply(get.apply(s)), (s,b) -> set.apply(s, other.set.apply(get.apply(s), b)));
  }
}
final class PersonLenses {
  static final Lens<Person,Address> address = new Lens<>(Person::address, (p,a)-> new Person(p.name(), a));
  static final Lens<Address,String> city = new Lens<>(Address::city, (a,c)-> new Address(c, a.zip()));
  static final Lens<Person,String> personCity = address.andThen(city);
}

```

### Stage 3 — Runnable main
```java
package com.buildwithwaqar.patterns.functional.lens.problem;
import java.util.*; import java.util.function.*;
public final class ProblemDemo {
  public static void main(String[] args) {
    System.out.println("=== Lenses: nested city update ===");
    System.out.println("--- naive ---"); System.out.println(NaivePerson.move("{city:Old}", "NYC"));
System.out.println("--- lenses ---");
var p=new Person("Ada", new Address("Old", "10001"));
var moved=PersonLenses.personCity.setA(p, "NYC");
System.out.println(p); System.out.println(moved);
System.out.println("city="+PersonLenses.personCity.getA(moved));
  }
}

```

## Runnable Output (Verified on JDK 25)
Narrate each line. Do not invent numbers on camera.

```text
=== Lenses: nested city update ===
--- naive ---
{city:NYC}
--- lenses ---
Person[name=Ada, address=Address[city=Old, zip=10001]]
Person[name=Ada, address=Address[city=NYC, zip=10001]]
city=NYC
```

Happy-path cue: `{city:NYC}`.
Failure-path cue: `Failure path is asserted in ProblemTest (injected fault / invalid input)`.
Fork the repo, set `JAVA_HOME` to JDK 25, and re-run the same `ProblemDemo` to verify.

## Modern Java 25 / Zazr Variant
**Records** carry the immutable results (`StepResult`, DTOs) with almost no boilerplate.

## Trade-offs
- **Clarity vs ceremony:** extra types for «Update nested city in immutable Person/Address» only pay off when the force repeats.
- **Testability:** inject collaborators; assert the failure path, not only the happy path.
- **Distributed / concurrent reality:** demos omit metrics, backpressure, and multi-region clocks.
- **Refuse Lenses** when a sealed switch, a function value, or a simpler library call already erases the force.
- **Neighbor:** Builder — Lenses update nested immutables; Builder constructs new aggregates.

## Interview Q&A
**Q1. What concrete problem does Lenses solve here?**
A: Update nested city in immutable Person/Address.

**Q2. What does the naive design get wrong?**
A: Naive version fails to cleanly solve: Update nested city in immutable Person/Address

**Q3. How do you test the failure path?**
A: Inject the fault and assert the observable outcome — e.g. `Failure path is asserted in ProblemTest (injected fault / invalid input)`.

**Q4. When is Lenses the wrong tool?**
A: When a smaller language feature erases the force, or when you really needed Builder.


## Key Takeaways
- Lead with the problem: Update nested city in immutable Person/Address.
- Show naive pain (`Failure path is asserted in ProblemTest (injected fault / invalid input)`) before naming Lenses.
- Run the Demo; trust JDK 25 stdout (`{city:NYC}`).
- Teach when NOT to use Lenses.

## Video Script (≈10–12 minutes)
Record for **Build with Waqar**. OBS for capture, CapCut for assembly. Import `slides.pdf`.
Facecam: full-screen for the hook, framed PiP over code slides.

**[0:00]** *(slide 01 title + talking head)*
Hey everyone — today we solve one concrete problem with **Lenses**:
*Update nested city in immutable Person/Address*. Keep these names in your head: Address, Person, Lens, PersonLenses.

**[0:45]** *(problem + hook slides)*
I am not starting with a textbook definition. Here is the product pain. By the end you will have
seen the naive leak, the pattern boundary, and real JDK 25 stdout.

**[2:00]** *(naive code)*
IDE time. This is the naive version. Call out the smell — that is what Lenses exists to erase.

**[3:30]** *(structure diagram)*
Roles for **this** problem, not a generic UML poster. Point at each box while you talk.

**[4:30]** *(refactor / solution code)*
Stage the solution. Highlight failure injection and the tests that lock it in.

**[7:00]** *(walkthrough output)*
Real stdout. Happy path cue: `{city:NYC}`.
Failure cue: `Failure path is asserted in ProblemTest (injected fault / invalid input)`.
No invented numbers on camera.

**[8:30]** *(modern Java 25 / Zazr + trade-offs)*
Show the modernization, then slow down for when NOT to use Lenses. That is what seniors listen for.

**[10:30]** *(Q&A + recap + outro)*
Two interview questions, three takeaways, link the GitHub episode folder. I am Waqar — see you in the next one.


## Walkthrough Beats (Say These Out Loud)
1. **Setup:** open `series/62-lenses-optics/` and `run-output.txt` beside the IDE.
2. **Naive:** reproduce the pain until you can point at `Failure path is asserted in ProblemTest (injected fault / invalid input)`.
3. **Structure:** on the diagram, trace `Client` → `Update`, `Update` → `nested`, `nested` → `Result`.
4. **Refactor:** encode the boundary, then run `ProblemDemo` — match `{city:NYC}`.
5. **Break it:** flip one `ProblemTest` assertion, watch red, restore it.
6. **Refuse:** Contrast with **Builder**: Lenses update nested immutables; Builder constructs new aggregates.

## Practice Drill
- Clone `waqarys/java-design-patterns`, set `JAVA_HOME` to JDK 25.
- Run the episode `ProblemDemo` (see `EP_MAIN` in `tools/enrich/build_problems.py`).
- Mutate one collaborator to fail; confirm the observable compensation / error path.
- Explain Lenses for «Update nested city in immutable Person/Address» in sixty seconds without notes.
