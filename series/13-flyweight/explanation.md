# Flyweight

**Season:** 2 · Structural · **Category:** GoF · **Level:** Beginner · **Runtime:** ~10–12 min · **Channel:** Build with Waqar

## Problem
**Share glyph styles across a million characters**

Key types: `GlyphStyle`, `GlyphFactory`, `Glyph`.

## Hook
Picture this: you are mid-demo and «Share glyph styles across a million characters» blows up the naive way — you see `Failure path is asserted in ProblemTest (injected fault / invalid input)`. That is the pain. In the next ten minutes we introduce **Flyweight** with real types (GlyphStyle, GlyphFactory, Glyph), a runnable `ProblemDemo`, and the failure path locked in by tests. No abstract UML first — problem first.

## Intent
Use **Flyweight** to cleanly solve: Share glyph styles across a million characters.

**When to use:** the force behind «Share glyph styles across a million characters» shows up more than once and a simpler language feature does not erase it.

**When to refuse:** you are pattern-collecting, the boundary is still unclear, or the team cannot yet test the abstraction (compensations, half-open recovery, interpreters).

## The Pain — Naive Design
Naive version fails to cleanly solve: Share glyph styles across a million characters

```java
package com.buildwithwaqar.patterns.gof.structural.flyweight.problem;

import java.util.*;

public final class NaiveGlyphs {
  public static int render(int n){
    int styles=0; for(int i=0;i<n;i++) styles++; // new style each glyph
    return styles;
  }
}

```

On camera, point at the smell: missing compensations, leaked XML, null at the boundary,
illegal transitions, or hard-wired `System.out` that you cannot assert.

## Pattern Structure
```mermaid
flowchart LR
  Client[Client] --> Share[Share]
  Share[Share] --> glyph[glyph]
  glyph[glyph] --> Result[Result]
```

These roles are for **«Share glyph styles across a million characters»** — not a generic Client→Pattern poster. Keep them on screen
while you type the solution.

## Refactor to the Pattern

### Stage 1 — Name the boundary
Introduce the client-facing API the rest of the app should depend on. Hide the incompatible or
distributed details behind it. For this episode that boundary is what makes «Share glyph styles across a million characters» testable.

### Stage 2 — Encode the solution
```java
package com.buildwithwaqar.patterns.gof.structural.flyweight.problem;

import java.util.*;
import java.util.concurrent.*;
import java.util.function.*;

final class GlyphStyle {
  private final String font; private final int size;
  GlyphStyle(String font, int size){ this.font=font; this.size=size; }
  String key(){ return font+":"+size; }
}
final class GlyphFactory {
  private final Map<String, GlyphStyle> cache = new HashMap<>();
  GlyphStyle style(String font, int size){
    return cache.computeIfAbsent(font+":"+size, k -> new GlyphStyle(font, size));
  }
  int cached(){ return cache.size(); }
}
final class Glyph {
  private final char c; private final GlyphStyle style;
  Glyph(char c, GlyphStyle style){ this.c=c; this.style=style; }
}

```

### Stage 3 — Runnable main
```java
package com.buildwithwaqar.patterns.gof.structural.flyweight.problem;

import java.util.*;
import java.util.concurrent.*;

public final class ProblemDemo {
  public static void main(String[] args) {
    System.out.println("=== Flyweight: share glyph styles ===");
    System.out.println("--- naive ---");
System.out.println("stylesCreated="+NaiveGlyphs.render(1000));
System.out.println("--- flyweight ---");
var f=new GlyphFactory();
for (int i=0;i<1000;i++) new Glyph((char)('a'+(i%26)), f.style("Inter", 12));
System.out.println("cachedStyles="+f.cached());
  }
}

```

## Runnable Output (Verified on JDK 25)
Narrate each line. Do not invent numbers on camera.

```text
=== Flyweight: share glyph styles ===
--- naive ---
stylesCreated=1000
--- flyweight ---
cachedStyles=1
```

Happy-path cue: `stylesCreated=1000`.
Failure-path cue: `Failure path is asserted in ProblemTest (injected fault / invalid input)`.
Fork the repo, set `JAVA_HOME` to JDK 25, and re-run the same `ProblemDemo` to verify.

## Modern Java 25 / Zazr Variant
For **Flyweight**, prefer records for results, sealed types for closed variants, and Zazr `Option`/`Either`/`Validation` when the force is absence or accumulated errors. Keep the classic shape when interview literacy matters.

## Trade-offs
- **Clarity vs ceremony:** extra types for «Share glyph styles across a million characters» only pay off when the force repeats.
- **Testability:** inject collaborators; assert the failure path, not only the happy path.
- **Distributed / concurrent reality:** demos omit metrics, backpressure, and multi-region clocks.
- **Refuse Flyweight** when a sealed switch, a function value, or a simpler library call already erases the force.
- **Neighbor:** Singleton — Flyweight shares many fine-grained immutables; Singleton is one identity.

## Interview Q&A
**Q1. What concrete problem does Flyweight solve here?**
A: Share glyph styles across a million characters.

**Q2. What does the naive design get wrong?**
A: Naive version fails to cleanly solve: Share glyph styles across a million characters

**Q3. How do you test the failure path?**
A: Inject the fault and assert the observable outcome — e.g. `Failure path is asserted in ProblemTest (injected fault / invalid input)`.

**Q4. When is Flyweight the wrong tool?**
A: When a smaller language feature erases the force, or when you really needed Singleton.


## Key Takeaways
- Lead with the problem: Share glyph styles across a million characters.
- Show naive pain (`Failure path is asserted in ProblemTest (injected fault / invalid input)`) before naming Flyweight.
- Run the Demo; trust JDK 25 stdout (`stylesCreated=1000`).
- Teach when NOT to use Flyweight.

## Video Script (≈10–12 minutes)
Record for **Build with Waqar**. OBS for capture, CapCut for assembly. Import `slides.pdf`.
Facecam: full-screen for the hook, framed PiP over code slides.

**[0:00]** *(slide 01 title + talking head)*
Hey everyone — today we solve one concrete problem with **Flyweight**:
*Share glyph styles across a million characters*. Keep these names in your head: GlyphStyle, GlyphFactory, Glyph.

**[0:45]** *(problem + hook slides)*
I am not starting with a textbook definition. Here is the product pain. By the end you will have
seen the naive leak, the pattern boundary, and real JDK 25 stdout.

**[2:00]** *(naive code)*
IDE time. This is the naive version. Call out the smell — that is what Flyweight exists to erase.

**[3:30]** *(structure diagram)*
Roles for **this** problem, not a generic UML poster. Point at each box while you talk.

**[4:30]** *(refactor / solution code)*
Stage the solution. Highlight failure injection and the tests that lock it in.

**[7:00]** *(walkthrough output)*
Real stdout. Happy path cue: `stylesCreated=1000`.
Failure cue: `Failure path is asserted in ProblemTest (injected fault / invalid input)`.
No invented numbers on camera.

**[8:30]** *(modern Java 25 / Zazr + trade-offs)*
Show the modernization, then slow down for when NOT to use Flyweight. That is what seniors listen for.

**[10:30]** *(Q&A + recap + outro)*
Two interview questions, three takeaways, link the GitHub episode folder. I am Waqar — see you in the next one.


## Walkthrough Beats (Say These Out Loud)
1. **Setup:** open `series/13-flyweight/` and `run-output.txt` beside the IDE.
2. **Naive:** reproduce the pain until you can point at `Failure path is asserted in ProblemTest (injected fault / invalid input)`.
3. **Structure:** on the diagram, trace `Client` → `Share`, `Share` → `glyph`, `glyph` → `Result`.
4. **Refactor:** encode the boundary, then run `ProblemDemo` — match `stylesCreated=1000`.
5. **Break it:** flip one `ProblemTest` assertion, watch red, restore it.
6. **Refuse:** Contrast with **Singleton**: Flyweight shares many fine-grained immutables; Singleton is one identity.

## Practice Drill
- Clone `waqarys/java-design-patterns`, set `JAVA_HOME` to JDK 25.
- Run the episode `ProblemDemo` (see `EP_MAIN` in `tools/enrich/build_problems.py`).
- Mutate one collaborator to fail; confirm the observable compensation / error path.
- Explain Flyweight for «Share glyph styles across a million characters» in sixty seconds without notes.
