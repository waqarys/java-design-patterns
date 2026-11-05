# Flyweight

**Season:** 2 · Structural · **Category:** GoF · **Level:** Intermediate

## Intent
Share intrinsic state across many fine-grained objects.

## Problem
Without this pattern, code tends toward: New things up with conditionals and duplicated construction.

## Real-world Analogy
Same glyph style reused for thousands of characters.

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
Primary code lives under `src/main/java/com/buildwithwaqar/patterns/gof/structural/flyweight/`.

```java
package com.buildwithwaqar.patterns.gof.structural.flyweight;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

record GlyphStyle(String font, int size, String color) {}

record Glyph(char character, GlyphStyle style, int x, int y) {}

/** Flyweight factory pools intrinsic glyph styles. */
public final class GlyphFactory {
    private final Map<GlyphStyle, GlyphStyle> pool = new ConcurrentHashMap<>();

    public GlyphStyle style(String font, int size, String color) {
        var key = new GlyphStyle(font, size, color);
        return pool.computeIfAbsent(key, k -> k);
    }

    public Glyph glyph(char c, GlyphStyle style, int x, int y) {
        return new Glyph(c, style, x, y);
    }

    public int pooled() { return pool.size(); }
}
```

## Modern Java 25 / Functional
Prefer sealed interfaces + records; virtual proxy uses synchronized lazy init.

## Worked Example
```text
See the JUnit test beside the implementation.
Run: mvn -Dtest=*Test test
Focus package matching: gof/structural/flyweight/
```

## Pitfalls
- Keep adapters thin.
- Decorators should preserve the interface.
- Facades must not become god objects.

## Interview Q&A
**Q: When is Flyweight the wrong choice?**
A: When you can change the adaptee/subject API or collapse the hierarchy.

**Q: How would you test this?**
A: Prefer injecting interfaces or interpreters; keep demos deterministic (fixed clocks, canned inputs).

## Key Takeaways
- Share intrinsic state across many fine-grained objects.
- Prefer Java 25 sealed types / records / virtual threads where they simplify the classic shape.
- Ship working tests with every episode.

## Video Script
**[0:00] Hook** — Open with the analogy: Same glyph style reused for thousands of characters.
**[0:40] Intent** — State the design force in one sentence: Share intrinsic state across many fine-grained objects.
**[1:30] Naive pain** — Show the messy approach: New things up with conditionals and duplicated construction.
**[3:00] Pattern** — Walk the code under `gof/structural/flyweight/`; draw the mermaid structure on-screen.
**[6:00] Modern take** — Prefer sealed interfaces + records; virtual proxy uses synchronized lazy init.
**[8:00] Pitfalls & interview** — Cover: When is Flyweight the wrong choice?
**[9:30] Outro** — Tease the next episode; point viewers to the GitHub repo and slides PDF.
