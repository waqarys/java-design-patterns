# Java 25 Features for Patterns

**Season:** 0 · Intro · **Category:** Language · **Level:** Beginner

## Intent
Use records, sealed types, pattern matching, and virtual threads as first-class pattern tools.

## Problem
Without this pattern, code tends toward: New things up with conditionals and duplicated construction.

## Real-world Analogy
New power tools that replace half the boilerplate older pattern books needed.

## When to Use
- Any new pattern implementation on JDK 25+.
- The design force named in Intent shows up more than once.

## When Not to Use
- Blindly enabling preview flags you do not need.
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
Primary code lives under `src/main/java/com/buildwithwaqar/patterns/Used throughout src/`.

```java
// see Used throughout src/
```

## Modern Java 25 / Functional
No preview features required in this repo — all on standard Java 25.

## Worked Example
```text
See the JUnit test beside the implementation.
Run: mvn -Dtest=*Test test
Focus package matching: Used throughout src/
```

## Pitfalls
- Sealed + switch is often better than Visitor.
- Prefer virtual threads over hand-rolled pools for blocking I/O.

## Interview Q&A
**Q: Records vs classes for DTOs?**
A: Records for transparent data carriers; classes when you need identity or rich mutable behavior.

**Q: How would you test this?**
A: Prefer injecting interfaces or interpreters; keep demos deterministic (fixed clocks, canned inputs).

## Key Takeaways
- Use records, sealed types, pattern matching, and virtual threads as first-class pattern tools.
- Prefer Java 25 sealed types / records / virtual threads where they simplify the classic shape.
- Ship working tests with every episode.

## Video Script
**[0:00] Hook** — Open with the analogy: New power tools that replace half the boilerplate older pattern books needed.
**[0:40] Intent** — State the design force in one sentence: Use records, sealed types, pattern matching, and virtual threads as first-class pattern tools.
**[1:30] Naive pain** — Show the messy approach: New things up with conditionals and duplicated construction.
**[3:00] Pattern** — Walk the code under `Used throughout src/`; draw the mermaid structure on-screen.
**[6:00] Modern take** — No preview features required in this repo — all on standard Java 25.
**[8:00] Pitfalls & interview** — Cover: Records vs classes for DTOs?
**[9:30] Outro** — Tease the next episode; point viewers to the GitHub repo and slides PDF.
