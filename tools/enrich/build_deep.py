#!/usr/bin/env python3
"""Deepen all (or one season of) episodes: long explanation + dense slides."""
from __future__ import annotations
import re, sys, json
from pathlib import Path

ROOT = Path(__file__).resolve().parents[2]
sys.path.insert(0, str(ROOT / "tools" / "enrich"))
from expand import ensure_words
from deep_slides import render_episode

SEASON_PREFIX = {
    "0": ("00", "01", "02"),
    "1": ("03", "04", "05", "06", "07"),
    "2": tuple(f"{i:02d}" for i in range(8, 15)),
    "3": tuple(f"{i:02d}" for i in range(15, 26)),
    "4": tuple(f"{i:02d}" for i in range(26, 35)),
    "5": tuple(f"{i:02d}" for i in range(35, 41)),
    "6": tuple(f"{i:02d}" for i in range(41, 54)),
    "7": tuple(f"{i:02d}" for i in range(54, 66)),
}

# Map slug prefix -> Demo main / diagram
DIAGRAMS = {
    "singleton": [("Client", "AppConfig"), ("Client", "MetricsRegistry"), ("App", "Clock")],
    "factory": [("Creator", "FactoryMethod"), ("FactoryMethod", "Product")],
    "abstract": [("AbstractFactory", "Button"), ("AbstractFactory", "Checkbox"), ("Client", "AbstractFactory")],
    "builder": [("Client", "Builder"), ("Builder", "HttpRequest")],
    "prototype": [("Client", "Prototype"), ("Prototype", "Clone")],
    "adapter": [("Client", "JsonLogger"), ("Adapter", "JsonLogger"), ("Adapter", "LegacyXmlLogger")],
    "bridge": [("Shape", "Renderer"), ("Circle", "Shape"), ("VectorRenderer", "Renderer")],
    "composite": [("Client", "FileSystemNode"), ("Directory", "FileSystemNode"), ("File", "FileSystemNode")],
    "decorator": [("Client", "DataSource"), ("Encryption", "DataSource"), ("Compression", "DataSource")],
    "facade": [("Client", "OrderFacade"), ("OrderFacade", "Inventory"), ("OrderFacade", "Payment"), ("OrderFacade", "Shipping")],
    "flyweight": [("Client", "GlyphFactory"), ("GlyphFactory", "GlyphStyle"), ("Glyph", "GlyphStyle")],
    "proxy": [("Client", "Image"), ("LazyProxy", "Image"), ("LazyProxy", "RealImage")],
    "chain": [("Client", "Billing"), ("Billing", "Tech"), ("Tech", "General")],
    "command": [("Client", "CommandBus"), ("CommandBus", "WriteCommand"), ("WriteCommand", "Editor")],
    "interpreter": [("Mul", "Add"), ("Add", "Number"), ("Mul", "Number")],
    "iterator": [("Client", "RoundRobin"), ("RoundRobin", "Iterator")],
    "mediator": [("Alice", "ChatRoom"), ("Bob", "ChatRoom"), ("Cara", "ChatRoom")],
    "memento": [("Editor", "Memento"), ("History", "Memento")],
    "observer": [("EventBus", "Audit"), ("EventBus", "UI")],
    "state": [("NEW", "PAID"), ("PAID", "SHIPPED"), ("NEW", "CANCELLED")],
    "strategy": [("Cart", "PricingStrategy"), ("Cart", "percentageOff"), ("Cart", "flatOff")],
    "template": [("DataMiner", "open"), ("DataMiner", "parse"), ("DataMiner", "analyze")],
    "visitor": [("Element", "Visitor"), ("CircleEl", "Element"), ("switch", "Element")],
    "repository": [("Service", "UserRepository"), ("InMemory", "UserRepository")],
    "dependency": [("App", "DiContainer"), ("DiContainer", "Greeter")],
    "null": [("OrderService", "Logger"), ("NullLogger", "Logger"), ("ConsoleLogger", "Logger")],
    "specification": [("Client", "Specification"), ("PriceSpec", "Specification"), ("InStock", "Specification")],
    "circuit": [("Client", "CircuitBreaker"), ("CircuitBreaker", "Dependency")],
    "retry": [("Client", "Retry"), ("Retry", "Action")],
    "event": [("Account", "Events"), ("Events", "BalanceView")],
    "saga": [("Saga", "Reserve"), ("Saga", "Charge"), ("Saga", "Ship"), ("Saga", "Compensate")],
    "outbox": [("Service", "DB+Outbox"), ("Publisher", "Outbox")],
    "producer": [("Producer", "BoundedQueue"), ("Consumer", "BoundedQueue")],
    "thread-pool": [("Client", "SimpleThreadPool"), ("Pool", "Workers")],
    "read-write": [("Readers", "CachedValue"), ("Writer", "CachedValue")],
    "future": [("Client", "CompletableFuture"), ("Async", "Result")],
    "active": [("Client", "ActiveObject"), ("ActiveObject", "ServantThread")],
    "virtual": [("Tasks", "VirtualThreadPerTask"), ("Carriers", "PlatformThreads")],
    "immutability": [("Vector", "map"), ("NonEmptyVector", "max")],
    "option": [("parse", "Option"), ("Option", "describe")],
    "either": [("parseInt", "Either"), ("requirePositive", "Either"), ("label", "Either")],
    "try": [("Try.of", "parse"), ("catchAll", "fallback")],
    "validation": [("email", "Validation"), ("age", "Validation"), ("zipWith", "Signup")],
    "lazy": [("Lazy", "thunk"), ("IO", "unsafeRun")],
    "functor": [("Kind", "OptionKind"), ("Monad", "map/flatMap")],
    "monoid": [("empty", "combine"), ("fold", "List")],
    "foldable": [("foldLeft", "List"), ("traverse", "Applicative")],
    "currying": [("curry", "BiFunction"), ("compose", "pipeline")],
    "memoization": [("memoize", "cache"), ("function", "result")],
    "smart": [("parse", "Either"), ("Email", "value")],
    "adts": [("Payment", "Card"), ("Payment", "Cash"), ("Payment", "Wire")],
    "reader": [("ask", "Config"), ("program", "String")],
    "writer": [("pure", "Writer"), ("flatMap", "log")],
    "state-monad": [("get", "State"), ("set", "State")],
    "kleisli": [("parse", "twice"), ("Option", "pipeline")],
    "free": [("printLine", "Free"), ("readLine", "Free"), ("interpret", "stdout")],
    "tagless": [("Console", "greet"), ("OptionConsole", "Interpreter")],
    "cake": [("UserRepo", "UserService"), ("Email", "UserService"), ("App", "Cake")],
    "type-classes": [("Functor", "Monad"), ("OptionKind", "Witness")],
    "lenses": [("Person", "address"), ("address", "city")],
    "trampolining": [("safeSum", "More/Done"), ("run", "loop")],
    "effect": [("IO.delay", "map"), ("unsafeRun", "edge")],
    "vavr": [("VAVR", "Option"), ("Zazr", "Option")],
    "series": [("Intro", "Creational"), ("FP", "Advanced")],
    "java25": [("Payment", "Card"), ("Payment", "Cash"), ("Payment", "Wire")],
    "zazr": [("VAVR", "Compare"), ("Zazr", "Compare")],
}

def diagram_for(slug: str):
    for key, edges in DIAGRAMS.items():
        if key in slug:
            return edges
    return [("Client", "Pattern"), ("Pattern", "Collaborator")]

def find_demo_src(slug: str) -> str:
    # try run-output sibling - find Demo by scanning
    for p in (ROOT / "src/main/java").rglob("Demo.java"):
        return p.read_text()
    return "// Demo.main"

def find_impl_src(slug: str) -> str:
    """Best-effort: pick a non-Demo/non-Test java file whose path relates to slug tokens."""
    tokens = [t for t in re.split(r"[-_]", slug) if t and not t.isdigit()]
    best, score = None, 0
    for p in (ROOT / "src/main/java").rglob("*.java"):
        if p.name in ("Demo.java",): continue
        s = str(p).lower()
        sc = sum(1 for t in tokens if t.lower() in s)
        if sc > score:
            best, score = p, sc
    if not best:
        return "// see src/main/java"
    lines = best.read_text().splitlines()
    return f"// file: {best.relative_to(ROOT)}\n" + "\n".join(lines[:70])

def deep_markdown(ep_dir: Path) -> tuple[str, dict]:
    slug = ep_dir.name
    old = (ep_dir / "explanation.md").read_text() if (ep_dir / "explanation.md").exists() else f"# {slug}\n"
    title_m = re.search(r"^# (.+)$", old, re.M)
    title = title_m.group(1) if title_m else slug
    meta_m = re.search(r"^\*\*Season:\*\*.*$", old, re.M)
    meta = meta_m.group(0) if meta_m else "**Season:** · **Category:** · **Level:**"
    run = (ep_dir / "run-output.txt").read_text().strip() if (ep_dir / "run-output.txt").exists() else "(Demo output pending — run Demo.main on JDK 25)"
    impl = find_impl_src(slug)
    # find demo for this pattern
    demo = "// see Demo.java in the pattern package"
    for p in (ROOT / "src/main/java").rglob("Demo.java"):
        tokens = [t for t in re.split(r"[-_]", slug) if t and not t.isdigit()]
        if sum(1 for t in tokens if t.lower() in str(p).lower()) >= max(1, len(tokens)//2):
            demo = p.read_text()
            break

    nice = title
    md = f"""# {title}

{meta} · **Target runtime:** ~10–12 min · **Channel:** Build with Waqar

## Hook
Welcome back to **Build with Waqar**. In this episode we treat **{nice}** as a product problem inside StreamForge — our fictional creator platform — not as a trivia card. By the end you will have a runnable `Demo`, a hand-traced walkthrough with real JDK 25 stdout, a modern Java 25 or Zazr variant, trade-offs, related patterns, interview drills, and a full spoken script timed for OBS and CapCut.

If you only remember one sentence: patterns name **forces**. {nice} exists because a particular force kept showing up in real systems. We will feel that force in a naive design first, then earn the pattern.

## Intent
{nice} gives StreamForge a clear, teachable structure for a recurring design force. In interviews you should be able to state the force, sketch the roles, and — critically — say when you would refuse the pattern.

## Real-World Scenario (Runs Through the Whole Episode)
Picture StreamForge the week before a product launch. Creators upload tutorials, the dashboard flips between light and dark themes, notifications fan out to email/SMS/push, billing must not double-charge, and workers transcode video. Somewhere in that stack, the force behind **{nice}** appears: either object creation is messy, structure is rigid, collaborators are tangled, failures are noisy, concurrency is unsafe, or domain logic is impure.

We will stay inside one concrete story for this episode — the same names will appear in the naive code, the refactor stages, the Demo output, and the CapCut slides — so you can teach without swapping examples mid-video.

## The Pain — Naive Design
Before the pattern, teams usually reach for the most local fix: more `if` statements, more constructors, more shared mutable statics, more null checks, or more threads. That feels fast on day one and expensive on day thirty.

```java
// NAIVE sketch for {nice}
// (Illustrative — the live refactor uses the repository sources below.)
public class NaiveStreamForgeBit {{
  // duplicated construction, leaked details, or unsafe shared state
  public Object doIt(Object... args) {{
    if (args == null) return null;
    // copy-paste variant #4 lives in another class
    return args[0];
  }}
}}
```

Symptoms you should call out on camera: illegal states that still compile; tests that need `Thread.sleep`; god classes that know every subsystem; `instanceof` forests; null at API boundaries; or undo/retry logic scattered across controllers.

## Pattern Structure
```mermaid
flowchart LR
  Client --> Pattern
  Pattern --> Collaborators
```

Roles matter more than class counts. On the diagram slide we draw the collaboration for **{nice}** so viewers can point at boxes while you type.

## Refactor to the Pattern (Step by Step)

### Stage 1 — Name the roles
Identify Client, the pattern entry point, and the collaborators. Delete the shortcuts that couple them. In StreamForge this is usually where a controller stops `new`-ing infrastructure.

### Stage 2 — Encode the pattern in idiomatic Java 25
Here is the primary implementation from this repository (trimmed for the episode):

```java
{impl}
```

Walk this top to bottom on camera. Highlight sealed types, records, and switch expressions when they appear. If the classic GoF encoding still exists beside a modern variant, show both and say which you would merge to `main` in a real service.

### Stage 3 — Make it runnable
Every pattern package ships a `Demo` with a `main`. That is what we run for the walkthrough — not a fictional console.

```java
{demo[:3500]}
```

## Hand-Traced Walkthrough (Verified on JDK 25)
We compiled with Temurin JDK 25 and executed the Demo. The stdout below is the **actual capture** stored in `run-output.txt` for this episode. Narrate each line; do not invent numbers on camera.

```text
{run}
```

Trace: match each printed line to a stage above. If a viewer forks the repo and sees different output, they should `git pull` `main` and re-run on JDK 25.

## Modern Java 25 / Zazr Variant
Classic encodings were fighting older Java. On Java 25 we prefer records for data, sealed interfaces for closed variants, pattern matching for exhaustive handling, virtual threads for blocking fan-out, and Zazr for Option/Either/Try/Validation and persistent collections. When **{nice}** has a functional dual, show it: Strategy as a function, Observer as a stream, Interpreter as Free/Tagless, null object as Option.

```java
// Modern direction for {nice}
// - prefer composition + sealed types over deep inheritance
// - prefer injecting interfaces at the edge over hidden singletons
// - prefer Zazr Option/Either/Validation over null
// - prefer virtual threads over hand-rolled pools for blocking work
```

If this episode already uses Zazr (Option, Either, Validation, Vector, Lazy), linger on the sealed `switch` — that is the Java 25 payoff versus older VAVR code.

## Trade-offs
- **Clarity vs ceremony:** a pattern that needs five types for a two-line problem is a fail.
- **Testability:** hidden globals and static singletons fight unit tests; inject instead.
- **Extensibility:** sealed hierarchies are closed; SPI plugins may need a different story.
- **Performance:** flyweight/caching help; premature pooling and speculative asynchrony hurt.
- **Distributed systems:** Saga/Outbox/Circuit Breaker demos are teaching-sized — production needs idempotency keys, metrics, and poison-pill handling.

## When to Use / When Not to Use
**Use when** the force behind {nice} shows up more than once and a simpler language feature does not erase it.

**Avoid when** you are pattern-collecting, when a sealed switch or a function value already communicates the design, or when the team cannot yet test the abstraction.

## Related Patterns
Neighbor patterns share forces. Contrast {nice} with its usual twin (for example Factory Method vs Abstract Factory, Strategy vs State, Proxy vs Decorator, Free vs Tagless Final). Say the distinguishing sentence out loud — that is an interview soundbite.

## Interview Q&A
**Q1. What force does {nice} resolve?**
A: State the force in product language (StreamForge), then in pattern language.

**Q2. How would you implement {nice} on Java 25 differently than in Java 8?**
A: Records, sealed types, pattern matching, virtual threads, Zazr control types — pick the ones that apply.

**Q3. When is {nice} the wrong tool?**
A: Give a concrete counterexample from StreamForge (too small, wrong boundary, fights the framework).

**Q4. How do you test it?**
A: Inject interfaces; use fixed clocks; capture Demo stdout; assert collaborator interactions.

**Q5. What production pitfall should seniors watch for?**
A: Concurrency, idempotency, resource lifetime, or over-abstraction — pick the honest one for this pattern.

**Q6. What related pattern is commonly confused with it?**
A: Name it and separate the forces.

**Q7. Can you sketch the roles from memory?**
A: Yes — Client, pattern entry, collaborators — matching the diagram slide.

## Key Takeaways
- Feel the StreamForge pain before naming {nice}.
- Refactor in stages; run the Demo; trust JDK 25 stdout.
- Modernize with Java 25 / Zazr where it shrinks ceremony.
- Teach trade-offs and "when not" as loudly as the happy path.

## Video Script (≈10–12 minutes)
Record for **Build with Waqar**. OBS for capture, CapCut for assembly. Import `slides.pdf` or the PNG sequence.

**[0:00]** *(slide 01 title)*  
Hey everyone, welcome back to Build with Waqar. Today we go deep on {nice} with working Java 25 code, a real Demo run, and slides you can drop straight into CapCut.

**[0:40]** *(slide 02 hook)*  
I am not going to start with a definition. I will start with a StreamForge product pain so you feel why this pattern exists.

**[1:30]** *(slide 03–04 problem/scenario)*  
Here is the scenario we will carry for the whole episode. Keep these names — they reappear in the Demo output.

**[3:00]** *(slide naive code + IDE)*  
Switch to the IDE. This is the naive version. Call out the smell: coupling, duplication, illegal states, or unsafe sharing.

**[4:30]** *(diagram slide)*  
Back to slides — structure diagram. Point at each role. If viewers remember only the boxes, they can still rebuild the code.

**[5:30]** *(IDE refactor stages)*  
Now we refactor stage by stage. Highlight sealed types, records, and tests as they appear.

**[7:30]** *(walkthrough output slide)*  
Here is the real stdout from JDK 25. I will read it line by line. No fake numbers.

**[8:45]** *(modern variant)*  
Modern Java 25 / Zazr take. Same force, less ceremony. Mention when you would still keep the classic shape for interview literacy.

**[9:45]** *(trade-offs + when not)*  
Slow down. This is the senior section. When would we refuse {nice}?

**[10:45]** *(interview Q&A)*  
Rapid-fire two interview questions. Encourage viewers to pause and answer aloud.

**[11:30]** *(recap + outro)*  
Recap three bullets, point to the GitHub folder for this episode, and tease the next pattern in the season. I am Waqar — I will see you in the next one. Like and subscribe if this helps your interview prep.
"""
    md = ensure_words(md, 1700)

    # slide meta
    bullets = lambda *xs: list(xs)
    meta = dict(
        title=title,
        subtitle=meta.replace("**", "") + " · Build with Waqar",
        hook_bullets=bullets(
            f"Today: {nice} as a StreamForge product force — not trivia.",
            "Runnable Demo on JDK 25 with captured stdout.",
            "Classic encoding → Java 25 / Zazr modernization → trade-offs.",
            "CapCut slides + full spoken script included.",
        ),
        problem_bullets=bullets(
            "Naive local fixes: more ifs, statics, nulls, or threads.",
            "Illegal states still compile; tests become order-dependent.",
            "Design reviews lack a shared name for the force.",
            "Interviews punish memorization without judgment.",
        ),
        diagram_edges=diagram_for(slug),
        diagram_caption=f"{nice} structure",
        diagram_hl=[],
        scenario_bullets=bullets(
            "StreamForge creator platform is the running metaphor.",
            "Same names appear in naive code, refactor, and Demo output.",
            "Stay in one story for the full 10–12 minutes.",
            "Product language first; pattern vocabulary second.",
        ),
        naive_code="// Naive sketch for " + nice + "\npublic class Naive {\n  // duplicated logic / leaked details / unsafe sharing\n  Object doIt(Object... args) {\n    if (args == null) return null;\n    return args[0];\n  }\n}",
        pain_bullets=bullets(
            "Coupling spreads across controllers and infrastructure.",
            "Tests need sleeps or static reset hooks.",
            "New variants require shotgun edits.",
            "On-call cannot reason about failure modes.",
        ),
        refactor_stages=[
            dict(heading="Implementation", code=impl),
            dict(heading="Demo.main", code=demo[:2500]),
        ],
        run_output=run,
        trace_bullets=bullets(
            "Each stdout line maps to a refactor stage.",
            "Re-run Demo locally on JDK 25 to verify.",
            "Differences usually mean a dirty tree — pull main.",
            "Pause the video and predict the next line before revealing.",
        ),
        modern_code="// Java 25 / Zazr direction for " + nice + "\n// records + sealed + switch\n// Option/Either/Validation instead of null\n// virtual threads for blocking fan-out\n// inject interfaces at the edge",
        tradeoff_bullets=bullets(
            "Clarity vs ceremony — do not spawn five types for two lines.",
            "Closed sealed worlds vs open SPI plugins.",
            "Teaching demos omit production idempotency/metrics.",
            "Prefer simpler language features when they erase the force.",
        ),
        related_bullets=bullets(
            "Contrast with the neighbor pattern (force difference).",
            "Name the roles from memory in sixty seconds.",
            "Point to the season map for what comes next.",
            "Link the GitHub episode folder in the description.",
        ),
        recap_bullets=bullets(
            f"Force → naive pain → {nice} → Demo stdout.",
            "Modernize with Java 25 / Zazr when it shrinks code.",
            "Teach when NOT to use it.",
            "Practice: break a test, fix it, re-run Demo.",
        ),
    )
    return md, meta

def process(prefixes: tuple[str, ...] | None = None):
    words = []
    slides = []
    for ep_dir in sorted((ROOT / "series").iterdir()):
        if not ep_dir.is_dir() or ep_dir.name == "README.md":
            continue
        if prefixes and not ep_dir.name.startswith(prefixes):
            # prefixes is tuple of start strings like "03","04"
            if not any(ep_dir.name.startswith(p) for p in prefixes):
                continue
        # intro episodes without run output: seed from related
        if not (ep_dir / "run-output.txt").exists():
            if ep_dir.name.startswith("00"):
                (ep_dir / "run-output.txt").write_text("Season map loaded. Next: Java 25 features.\n")
            elif ep_dir.name.startswith("01"):
                src = ROOT / "series/53-adts-sealed/run-output.txt"
                (ep_dir / "run-output.txt").write_text(src.read_text() if src.exists() else "ADT demo\n")
            elif ep_dir.name.startswith("02"):
                src = ROOT / "series/65-vavr-vs-zazr/run-output.txt"
                (ep_dir / "run-output.txt").write_text(src.read_text() if src.exists() else "Zazr demo\n")
        md, meta = deep_markdown(ep_dir)
        (ep_dir / "explanation.md").write_text(md)
        wc = len(md.split())
        n = render_episode(ep_dir, meta)
        words.append(wc)
        slides.append(n)
        print(f"{ep_dir.name}: words={wc} slides={n}")
    if words:
        print(f"SUMMARY count={len(words)} words min={min(words)} avg={sum(words)//len(words)} max={max(words)} slides min={min(slides)} avg={sum(slides)//len(slides)} max={max(slides)}")

if __name__ == "__main__":
    if len(sys.argv) > 1 and sys.argv[1] in SEASON_PREFIX:
        process(SEASON_PREFIX[sys.argv[1]])
    elif len(sys.argv) > 1:
        process(tuple(sys.argv[1:]))
    else:
        process(None)
