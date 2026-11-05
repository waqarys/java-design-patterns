# Series Intro: Java 25 Design Patterns

**Season:** 0 · Intro · **Category:** Overview · **Level:** Beginner

## Intent
Map the journey from GoF classics through enterprise/concurrency to pure FP with Zazr and VAVR.

## Problem
Without this pattern, code tends toward: New things up with conditionals and duplicated construction.

## Real-world Analogy
A gym curriculum: fundamentals, compounds, then advanced Olympic lifts.

## When to Use
- Starting interview prep or modernizing a Java codebase.
- The design force named in Intent shows up more than once.

## When Not to Use
- Skipping straight to Free monads before understanding Strategy.
- A simpler language feature (record, sealed switch, function value) already solves it.

## Naive Approach
```text
New things up with conditionals and duplicated construction.
```

## Pattern Structure
```mermaid
flowchart LR
  Client --> Pattern
  Pattern --> Collaborators
```
Client -> Pattern -> Collaborators

## Classic Implementation
Primary code lives under `src/main/java/com/buildwithwaqar/patterns/README.md + pom.xml (Java 25, Zazr 0.4.0, VAVR 1.1.0)`.

```java
// see README.md + pom.xml (Java 25, Zazr 0.4.0, VAVR 1.1.0)
```

## Modern Java 25 / Functional
Java 25: records, sealed interfaces, pattern matching, virtual threads. Zazr is a Vavr fork built for those features.

## Worked Example
```text
See the JUnit test beside the implementation.
Run: mvn -Dtest=*Test test
Focus package matching: README.md + pom.xml (Java 25, Zazr 0.4.0, VAVR 1.1.0)
```

## Pitfalls
- Don't memorize UML — understand the force that the pattern resolves.
- Prefer composition and sealed types over deep inheritance.

## Interview Q&A
**Q: Why learn patterns in 2026?**
A: They name recurring design forces; interviews and code reviews still use that vocabulary. Modern Java makes many patterns lighter.

**Q: How would you test this?**
A: Prefer injecting interfaces or interpreters; keep demos deterministic (fixed clocks, canned inputs).

## Key Takeaways
- Map the journey from GoF classics through enterprise/concurrency to pure FP with Zazr and VAVR.
- Prefer Java 25 sealed types / records / virtual threads where they simplify the classic shape.
- Ship working tests with every episode.

## Video Script
**[0:00] Hook** — Open with the analogy: A gym curriculum: fundamentals, compounds, then advanced Olympic lifts.
**[0:40] Intent** — State the design force in one sentence: Map the journey from GoF classics through enterprise/concurrency to pure FP with Zazr and VAVR.
**[1:30] Naive pain** — Show the messy approach: New things up with conditionals and duplicated construction.
**[3:00] Pattern** — Walk the code under `README.md + pom.xml (Java 25, Zazr 0.4.0, VAVR 1.1.0)`; draw the mermaid structure on-screen.
**[6:00] Modern take** — Java 25: records, sealed interfaces, pattern matching, virtual threads. Zazr is a Vavr fork built for those features.
**[8:00] Pitfalls & interview** — Cover: Why learn patterns in 2026?
**[9:30] Outro** — Tease the next episode; point viewers to the GitHub repo and slides PDF.
