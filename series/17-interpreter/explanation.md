# Interpreter

**Season:** 3 · Behavioral · **Category:** GoF · **Level:** Intermediate

## Intent
Map a grammar to classes that interpret sentences.

## Problem
Without this pattern, code tends toward: New things up with conditionals and duplicated construction.

## Real-world Analogy
Spreadsheet formula AST.

## When to Use
- See Intent.
- The design force named in Intent shows up more than once.

## When Not to Use
- Overkill for one-off behavior.
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
Primary code lives under `src/main/java/com/buildwithwaqar/patterns/gof/behavioral/interpreter/`.

```java
package com.buildwithwaqar.patterns.gof.behavioral.interpreter;

import java.util.Map;

public sealed interface Expression permits NumberExpr, AddExpr, MulExpr {
    int evaluate(Map<String, Integer> ctx);
}

record NumberExpr(int value) implements Expression {
    public int evaluate(Map<String, Integer> ctx) { return value; }
}

record AddExpr(Expression left, Expression right) implements Expression {
    public int evaluate(Map<String, Integer> ctx) {
        return left.evaluate(ctx) + right.evaluate(ctx);
    }
}

record MulExpr(Expression left, Expression right) implements Expression {
    public int evaluate(Map<String, Integer> ctx) {
        return left.evaluate(ctx) * right.evaluate(ctx);
    }
}
```

## Modern Java 25 / Functional
Java 25 pattern matching often replaces Visitor/Interpreter boilerplate.

## Worked Example
```text
See the JUnit test beside the implementation.
Run: mvn -Dtest=*Test test
Focus package matching: gof/behavioral/interpreter/
```

## Pitfalls
- Prefer sealed + switch over classic Visitor in Java 25.
- Strategy as a function value.
- Observer: unsubscribe to avoid leaks.

## Interview Q&A
**Q: Key interview angle for Interpreter?**
A: Name the force (undo, open/closed, open for extension) and a modern alternative.

**Q: How would you test this?**
A: Prefer injecting interfaces or interpreters; keep demos deterministic (fixed clocks, canned inputs).

## Key Takeaways
- Map a grammar to classes that interpret sentences.
- Prefer Java 25 sealed types / records / virtual threads where they simplify the classic shape.
- Ship working tests with every episode.

## Video Script
**[0:00] Hook** — Open with the analogy: Spreadsheet formula AST.
**[0:40] Intent** — State the design force in one sentence: Map a grammar to classes that interpret sentences.
**[1:30] Naive pain** — Show the messy approach: New things up with conditionals and duplicated construction.
**[3:00] Pattern** — Walk the code under `gof/behavioral/interpreter/`; draw the mermaid structure on-screen.
**[6:00] Modern take** — Java 25 pattern matching often replaces Visitor/Interpreter boilerplate.
**[8:00] Pitfalls & interview** — Cover: Key interview angle for Interpreter?
**[9:30] Outro** — Tease the next episode; point viewers to the GitHub repo and slides PDF.
