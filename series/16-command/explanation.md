# Command

**Season:** 3 · Behavioral · **Category:** GoF · **Level:** Intermediate

## Intent
Encapsulate a request as an object (queue, undo, log).

## Problem
Without this pattern, code tends toward: New things up with conditionals and duplicated construction.

## Real-world Analogy
Restaurant ticket the kitchen executes and can void.

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
Primary code lives under `src/main/java/com/buildwithwaqar/patterns/gof/behavioral/command/`.

```java
package com.buildwithwaqar.patterns.gof.behavioral.command;

import java.util.ArrayDeque;
import java.util.Deque;

interface Command { void execute(); void undo(); }

final class Editor {
    private final StringBuilder text = new StringBuilder();
    void append(String s) { text.append(s); }
    void deleteLast(int n) { text.delete(Math.max(0, text.length() - n), text.length()); }
    String text() { return text.toString(); }
}

record WriteCommand(Editor editor, String chunk) implements Command {
    public void execute() { editor.append(chunk); }
    public void undo() { editor.deleteLast(chunk.length()); }
}

/** Invoker with undo stack. */
public final class CommandBus {
    private final Deque<Command> history = new ArrayDeque<>();

    public void run(Command command) {
        command.execute();
        history.push(command);
    }

    public void undo() {
        if (!history.isEmpty()) history.pop().undo();
    }
}
```

## Modern Java 25 / Functional
Java 25 pattern matching often replaces Visitor/Interpreter boilerplate.

## Worked Example
```text
See the JUnit test beside the implementation.
Run: mvn -Dtest=*Test test
Focus package matching: gof/behavioral/command/
```

## Pitfalls
- Prefer sealed + switch over classic Visitor in Java 25.
- Strategy as a function value.
- Observer: unsubscribe to avoid leaks.

## Interview Q&A
**Q: Key interview angle for Command?**
A: Name the force (undo, open/closed, open for extension) and a modern alternative.

**Q: How would you test this?**
A: Prefer injecting interfaces or interpreters; keep demos deterministic (fixed clocks, canned inputs).

## Key Takeaways
- Encapsulate a request as an object (queue, undo, log).
- Prefer Java 25 sealed types / records / virtual threads where they simplify the classic shape.
- Ship working tests with every episode.

## Video Script
**[0:00] Hook** — Open with the analogy: Restaurant ticket the kitchen executes and can void.
**[0:40] Intent** — State the design force in one sentence: Encapsulate a request as an object (queue, undo, log).
**[1:30] Naive pain** — Show the messy approach: New things up with conditionals and duplicated construction.
**[3:00] Pattern** — Walk the code under `gof/behavioral/command/`; draw the mermaid structure on-screen.
**[6:00] Modern take** — Java 25 pattern matching often replaces Visitor/Interpreter boilerplate.
**[8:00] Pitfalls & interview** — Cover: Key interview angle for Command?
**[9:30] Outro** — Tease the next episode; point viewers to the GitHub repo and slides PDF.
