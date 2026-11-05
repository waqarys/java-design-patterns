# Adapter

**Season:** 2 · Structural · **Category:** GoF · **Level:** Beginner

## Intent
Convert one interface into another clients expect.

## Problem
Without this pattern, code tends toward: New things up with conditionals and duplicated construction.

## Real-world Analogy
A travel plug adapter between US and EU sockets.

## When to Use
- See Intent.
- The design force named in Intent shows up more than once.

## When Not to Use
- When a simpler redesign removes the need.
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
Primary code lives under `src/main/java/com/buildwithwaqar/patterns/gof/structural/adapter/`.

```java
package com.buildwithwaqar.patterns.gof.structural.adapter;

interface JsonLogger {
    void logJson(String json);
}

final class LegacyXmlLogger {
    String last;
    void logXml(String xml) { last = xml; }
}

/** Object adapter — wraps adaptee, translates calls. */
public final class XmlToJsonLoggerAdapter implements JsonLogger {
    private final LegacyXmlLogger legacy = new LegacyXmlLogger();

    @Override
    public void logJson(String json) {
        legacy.logXml("<payload>" + json + "</payload>");
    }

    String lastXml() { return legacy.last; }
}
```

## Modern Java 25 / Functional
Prefer sealed interfaces + records; virtual proxy uses synchronized lazy init.

## Worked Example
```text
See the JUnit test beside the implementation.
Run: mvn -Dtest=*Test test
Focus package matching: gof/structural/adapter/
```

## Pitfalls
- Keep adapters thin.
- Decorators should preserve the interface.
- Facades must not become god objects.

## Interview Q&A
**Q: When is Adapter the wrong choice?**
A: When you can change the adaptee/subject API or collapse the hierarchy.

**Q: How would you test this?**
A: Prefer injecting interfaces or interpreters; keep demos deterministic (fixed clocks, canned inputs).

## Key Takeaways
- Convert one interface into another clients expect.
- Prefer Java 25 sealed types / records / virtual threads where they simplify the classic shape.
- Ship working tests with every episode.

## Video Script
**[0:00] Hook** — Open with the analogy: A travel plug adapter between US and EU sockets.
**[0:40] Intent** — State the design force in one sentence: Convert one interface into another clients expect.
**[1:30] Naive pain** — Show the messy approach: New things up with conditionals and duplicated construction.
**[3:00] Pattern** — Walk the code under `gof/structural/adapter/`; draw the mermaid structure on-screen.
**[6:00] Modern take** — Prefer sealed interfaces + records; virtual proxy uses synchronized lazy init.
**[8:00] Pitfalls & interview** — Cover: When is Adapter the wrong choice?
**[9:30] Outro** — Tease the next episode; point viewers to the GitHub repo and slides PDF.
