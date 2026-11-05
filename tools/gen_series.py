#!/usr/bin/env python3
"""Generate series/*/explanation.md for the YouTube playlist."""
from __future__ import annotations
from pathlib import Path
ROOT = Path(__file__).resolve().parents[1]

# (num, slug, title, season, category, level, intent, analogy, when, when_not, pitfalls, code_path, modern_note, interview_q, interview_a)
EPS = []

def add(num, slug, title, season, category, level, intent, analogy, when, when_not, pitfalls, code_hint, modern, iq, ia, naive="New things up with conditionals and duplicated construction.", structure="Client -> Pattern -> Collaborators"):
    EPS.append(dict(num=num, slug=slug, title=title, season=season, category=category, level=level,
                    intent=intent, analogy=analogy, when=when, when_not=when_not, pitfalls=pitfalls,
                    code_hint=code_hint, modern=modern, iq=iq, ia=ia, naive=naive, structure=structure))

# Season 0 intro
add(0, "series-intro", "Series Intro: Java 25 Design Patterns", "0 · Intro", "Overview", "Beginner",
    "Map the journey from GoF classics through enterprise/concurrency to pure FP with Zazr and VAVR.",
    "A gym curriculum: fundamentals, compounds, then advanced Olympic lifts.",
    "Starting interview prep or modernizing a Java codebase.",
    "Skipping straight to Free monads before understanding Strategy.",
    ["Don't memorize UML — understand the force that the pattern resolves.", "Prefer composition and sealed types over deep inheritance."],
    "README.md + pom.xml (Java 25, Zazr 0.4.0, VAVR 1.1.0)",
    "Java 25: records, sealed interfaces, pattern matching, virtual threads. Zazr is a Vavr fork built for those features.",
    "Why learn patterns in 2026?",
    "They name recurring design forces; interviews and code reviews still use that vocabulary. Modern Java makes many patterns lighter.")

add(1, "java25-features", "Java 25 Features for Patterns", "0 · Intro", "Language", "Beginner",
    "Use records, sealed types, pattern matching, and virtual threads as first-class pattern tools.",
    "New power tools that replace half the boilerplate older pattern books needed.",
    "Any new pattern implementation on JDK 25+.",
    "Blindly enabling preview flags you do not need.",
    ["Sealed + switch is often better than Visitor.", "Prefer virtual threads over hand-rolled pools for blocking I/O."],
    "Used throughout src/",
    "No preview features required in this repo — all on standard Java 25.",
    "Records vs classes for DTOs?",
    "Records for transparent data carriers; classes when you need identity or rich mutable behavior.")

add(2, "zazr-and-vavr", "Zazr and VAVR Tour", "0 · Intro", "FP Libraries", "Beginner",
    "Introduce Zazr (primary) and VAVR (comparison) control types and collections.",
    "Two toolboxes: VAVR is the classic; Zazr is the Java 25 redesign (sealed Option/Either/Try/Validation).",
    "Writing null-hostile, immutable, effect-aware Java.",
    "Mixing both libraries in one module without a clear boundary.",
    ["Zazr Option rejects null — use Unit markers, not null.", "Prefer Validation.zipWith to collect errors."],
    "functional/vavrcompare/VavrVsZazr.java",
    "Pattern-match Zazr Option.Some / Option.None; VAVR uses map/getOrElse.",
    "Why Zazr over VAVR on Java 25?",
    "Sealed records + exhaustive switch, Scala 2.13 collections, ZIO-style names, no runtime deps.")

# Season 1 Creational
add(3, "singleton", "Singleton", "1 · Creational", "GoF", "Beginner",
    "One shared instance with a global access point.",
    "One official passport office for the country.",
    "True process-wide shared resource (metrics, feature flags).",
    "Request-scoped state; anything you need to mock easily.",
    ["Enum singleton is preferred.", "Mutable singleton state is a concurrency hazard.", "Prefer DI at edges."],
    "gof/creational/singleton/",
    "Inject Clock instead of System.currentTimeMillis(); use virtual threads safely with holder idiom.",
    "Enum vs double-checked locking?",
    "Enum is serialization/reflection-safe; DCL is easy to get wrong — prefer holder or enum.")

add(4, "factory-method", "Factory Method", "1 · Creational", "GoF", "Beginner",
    "Defer instantiation to subclasses (or a switch on a sealed channel).",
    "A restaurant kitchen: the waiter asks for 'dessert'; the pastry chef decides which cake.",
    "Clients must not depend on concrete product classes.",
    "When a simple constructor or enum switch already suffices.",
    ["Don't explode the hierarchy for one-off products.", "Modern Java: sealed enum + switch."],
    "gof/creational/factorymethod/",
    "NotificationChannel enum with switch replaces Notifier subclasses.",
    "Factory Method vs Abstract Factory?",
    "Factory Method: one product. Abstract Factory: families of related products.")

add(5, "abstract-factory", "Abstract Factory", "1 · Creational", "GoF", "Intermediate",
    "Create families of related products without naming concrete classes.",
    "IKEA room kits: Scandinavian or Industrial — sofa and lamp match the theme.",
    "UI themes, cloud provider SDKs, multi-brand product lines.",
    "Only one product type; Factory Method is enough.",
    ["Keep families consistent.", "Wire with DI."],
    "gof/creational/abstractfactory/",
    "UiFactory.of(Theme) + records for Light/Dark products.",
    "How do you test Abstract Factory?",
    "Inject a fake factory returning test doubles for the whole family.")

add(6, "builder", "Builder", "1 · Creational", "GoF", "Beginner",
    "Construct complex immutable objects step by step.",
    "A sandwich order form: bread, fillings, sauces — then 'build'.",
    "Many optional parameters; telescoping constructors hurt.",
    "Two-field records — just use the canonical constructor.",
    ["Validate in build().", "Prefer immutable results (Map.copyOf)."],
    "gof/creational/builder/",
    "Records with withers (ApiCall.withBody) for lighter builders.",
    "Builder vs telescoping constructors?",
    "Builder scales; telescoping explodes combinations and invites arg-swap bugs.")

add(7, "prototype", "Prototype", "1 · Creational", "GoF", "Beginner",
    "Clone a preconfigured object instead of re-building it.",
    "Photocopying a filled form and changing only the name.",
    "Costly setup; many similar instances.",
    "Cheap constructors; prefer factories.",
    ["Deep vs shallow copy.", "Records + List.copyOf simplify immutability."],
    "gof/creational/prototype/",
    "SlideDeck record with customized() copy.",
    "Cloneable pitfalls?",
    "Cloneable is broken-by-design; prefer copy constructors or records.")

# Season 2 Structural
for num, slug, title, intent, analogy, code in [
 (8,"adapter","Adapter","Convert one interface into another clients expect.","A travel plug adapter between US and EU sockets.","gof/structural/adapter/"),
 (9,"bridge","Bridge","Decouple abstraction from implementation so both vary independently.","Remote control (abstraction) vs TV brand (implementor).","gof/structural/bridge/"),
 (10,"composite","Composite","Treat individual objects and compositions uniformly.","Folders contain files and folders.","gof/structural/composite/"),
 (11,"decorator","Decorator","Attach responsibilities dynamically by wrapping.","Stacking phone cases: waterproof + battery.","gof/structural/decorator/"),
 (12,"facade","Facade","Provide a simple interface to a noisy subsystem.","Hotel concierge books taxi, dinner, tickets.","gof/structural/facade/"),
 (13,"flyweight","Flyweight","Share intrinsic state across many fine-grained objects.","Same glyph style reused for thousands of characters.","gof/structural/flyweight/"),
 (14,"proxy","Proxy","Surrogate controlling access to a real subject (lazy, remote, secure).","Hotel key card opens only your floor.","gof/structural/proxy/"),
]:
    add(num, slug, title, "2 · Structural", "GoF", "Beginner" if num!=13 else "Intermediate",
        intent, analogy, "See Intent.", "When a simpler redesign removes the need.",
        ["Keep adapters thin.", "Decorators should preserve the interface.", "Facades must not become god objects."],
        code, "Prefer sealed interfaces + records; virtual proxy uses synchronized lazy init.",
        f"When is {title} the wrong choice?", "When you can change the adaptee/subject API or collapse the hierarchy.")

# Season 3 Behavioral
for num, slug, title, intent, analogy, code in [
 (15,"chain-of-responsibility","Chain of Responsibility","Pass a request along a chain until a handler processes it.","Support tiers: billing → tech → general.","gof/behavioral/chain/"),
 (16,"command","Command","Encapsulate a request as an object (queue, undo, log).","Restaurant ticket the kitchen executes and can void.","gof/behavioral/command/"),
 (17,"interpreter","Interpreter","Map a grammar to classes that interpret sentences.","Spreadsheet formula AST.","gof/behavioral/interpreter/"),
 (18,"iterator","Iterator","Access aggregates sequentially without exposing representation.","TV channel remote next/prev.","gof/behavioral/iterator/"),
 (19,"mediator","Mediator","Centralize complex colleague communication.","Air traffic control tower.","gof/behavioral/mediator/"),
 (20,"memento","Memento","Capture and restore an object's internal state.","Video game save slot.","gof/behavioral/memento/"),
 (21,"observer","Observer","Notify dependents of state changes.","YouTube subscribe bell.","gof/behavioral/observer/"),
 (22,"state","State","Alter behavior when internal state changes.","Order: NEW → PAID → SHIPPED.","gof/behavioral/state/"),
 (23,"strategy","Strategy","Swap algorithms behind a common interface.","Maps app: car vs bike routing.","gof/behavioral/strategy/"),
 (24,"template-method","Template Method","Define algorithm skeleton; subclasses fill steps.","Airline boarding process with airline-specific checks.","gof/behavioral/templatemethod/"),
 (25,"visitor","Visitor","Add operations to object structures without changing classes.","Airport security scanning different bag types.","gof/behavioral/visitor/"),
]:
    add(num, slug, title, "3 · Behavioral", "GoF", "Intermediate",
        intent, analogy, "See Intent.", "Overkill for one-off behavior.",
        ["Prefer sealed + switch over classic Visitor in Java 25.", "Strategy as a function value.", "Observer: unsubscribe to avoid leaks."],
        code, "Java 25 pattern matching often replaces Visitor/Interpreter boilerplate.",
        f"Key interview angle for {title}?", "Name the force (undo, open/closed, open for extension) and a modern alternative.")

# Season 4 Enterprise
for num, slug, title, intent, analogy, code in [
 (26,"repository","Repository","Collection-like interface over persistence.","Librarian finds books without exposing the stacks.","enterprise/repository/"),
 (27,"dependency-injection","Dependency Injection","Supply collaborators from the outside.","Hiring a chef instead of the restaurant growing its own vegetables.","enterprise/di/"),
 (28,"null-object","Null Object","Provide a do-nothing stand-in instead of null.","Mute button that still is a speaker interface.","enterprise/nullobject/"),
 (29,"specification","Specification","Composable business rules as predicates.","Filter listings: price AND in-stock.","enterprise/specification/"),
 (30,"circuit-breaker","Circuit Breaker","Fail fast when a dependency is down.","Electrical breaker trips to protect the house.","enterprise/circuitbreaker/"),
 (31,"retry","Retry","Re-execute transient failures with policy.","Redialing a busy phone line.","enterprise/retry/"),
 (32,"event-sourcing-cqrs","Event Sourcing & CQRS","Persist events; separate write/read models.","Bank ledger vs balance view.","enterprise/eventsourcing/"),
 (33,"saga","Saga","Coordinate distributed steps with compensations.","Travel booking: flight + hotel, cancel if payment fails.","enterprise/saga/"),
 (34,"outbox","Transactional Outbox","Atomically store message with business row; publish later.","Outbox tray: write letter, mailman picks up later.","enterprise/outbox/"),
]:
    add(num, slug, title, "4 · Enterprise", "Enterprise", "Intermediate",
        intent, analogy, "Microservices / modular monoliths.", "Greenfield CRUD without distribution.",
        ["Keep demos minimal — production needs idempotency and observability.", "Saga compensations must be careful."],
        code, "Sealed events + pattern matching for projections.",
        f"Production concern for {title}?", "Idempotency, observability, and failure modes.")

# Season 5 Concurrency
for num, slug, title, intent, analogy, code in [
 (35,"producer-consumer","Producer-Consumer","Decouple producers and consumers via a bounded buffer.","Assembly line with a parts bin.","concurrency/producerconsumer/"),
 (36,"thread-pool","Thread Pool","Reuse workers instead of spawning unbounded threads.","Taxi stand with a fixed fleet.","concurrency/threadpool/"),
 (37,"read-write-lock","Read-Write Lock","Many readers or one writer.","Library: many readers, exclusive re-shelving.","concurrency/readwritelock/"),
 (38,"future-promise","Future & Promise","Represent a value that will arrive asynchronously.","Order tracking number before delivery.","concurrency/futurepromise/"),
 (39,"active-object","Active Object","Method calls become async requests on a servant thread.","Personal assistant queues your tasks.","concurrency/activeobject/"),
 (40,"virtual-threads","Virtual Threads","Lightweight threads for massive concurrency (Java 21+).","Millions of cheap coroutines scheduled on carriers.","concurrency/virtualthreads/"),
]:
    add(num, slug, title, "5 · Concurrency", "Concurrency", "Intermediate",
        intent, analogy, "Concurrent / parallel workloads.", "Pure CPU-bound work already parallelized.",
        ["Prefer java.util.concurrent and virtual threads over custom pools.", "Always interrupt/shutdown cleanly."],
        code, "Executors.newVirtualThreadPerTaskExecutor() is the modern default for blocking tasks.",
        "Virtual threads vs platform threads?", "Virtual threads excel at blocking I/O concurrency; still bound by CPU for pure compute.")

# Season 6 FP foundations
for num, slug, title, intent, analogy, code in [
 (41,"immutability-collections","Immutability & Persistent Collections","Share structure safely with Zazr Vector / NonEmptyVector.","Lego sets you can rebuild without melting old bricks.","functional/collections/"),
 (42,"option","Option / Maybe","Model absence without null.","A sealed box that is Some or None.","functional/option/"),
 (43,"either-railway","Either & Railway-Oriented Programming","Errors as values on the left track.","Railway switch: success track vs error track.","functional/either/"),
 (44,"try","Try","Capture exceptions as values.","Safety net under a trapeze.","functional/try_/"),
 (45,"validation","Validation","Accumulate all errors, not just the first.","Form validation highlighting every bad field.","functional/validation/"),
 (46,"lazy-io","Lazy & IO","Defer effects; run at the edge.","A recipe card vs cooking the meal.","functional/lazy/ + functional/io/"),
 (47,"functor-applicative-monad","Functor, Applicative, Monad","Type-class dictionaries with Kind&lt;F,A&gt; witnesses.","Lego adapters that let you map/flatMap uniformly.","functional/typeclass/"),
 (48,"monoid-semigroup","Monoid & Semigroup","Associative combine with (optional) empty.","Addition of numbers; concatenation of strings.","functional/monoid/"),
 (49,"foldable-traverse","Foldable & Traverse","Generic folds and effectful walks over structures.","Vacuuming every room (fold) vs inspecting each with a checklist (traverse).","functional/foldable/"),
 (50,"currying-composition","Currying, Partial Application & Composition","Build pipelines from small functions.","Garden hose adapters.","functional/functions/"),
 (51,"memoization","Memoization","Cache pure function results.","Remembering the answer to 12×12.","functional/functions/Fn.memoize"),
 (52,"smart-constructors","Smart Constructors","Make illegal states unrepresentable.","Turnstiles that only admit valid tickets.","functional/adt/Email"),
 (53,"adts-sealed","Algebraic Data Types with Sealed Records","Sum and product types with exhaustive switch.","Payment = Card | Cash | Wire.","functional/adt/Payment"),
]:
    add(num, slug, title, "6 · FP Foundations", "Functional", "Intermediate",
        intent, analogy, "Pure functional style in Java 25 + Zazr.", "Throwing exceptions everywhere is 'fine' for a script.",
        ["Zazr rejects null inside Option/collections.", "Keep interpreters at the edge."],
        code, "Primary examples use Zazr; VAVR shown in comparison episode.",
        f"Interview: what problem does {title} solve?", intent)

# Season 7 Advanced FP
for num, slug, title, intent, analogy, code in [
 (54,"reader","Reader Monad","Thread an environment through a computation.","Every function reads the same config clipboard.","functional/reader/"),
 (55,"writer","Writer Monad","Produce a value and an accumulated log.","Lab notebook beside the experiment.","functional/writer/"),
 (56,"state","State Monad","Purely thread mutable state.","Game score threaded through pure moves.","functional/state/"),
 (57,"kleisli","Kleisli Composition","Compose A → F&lt;B&gt; arrows.","Effectful function pipelines.","functional/kleisli/"),
 (58,"free-monad","Free Monad","Build programs from an instruction set; interpret later.","Sheet music played by different orchestras.","functional/free/"),
 (59,"tagless-final","Tagless Final","Program against algebras; swap interpreters.","Plug the same script into Stage or Rehearsal mode.","functional/tagless/"),
 (60,"cake-pattern","Cake Pattern","Mixin components via interfaces (self-type emulation).","Layered cake of UserRepo + Email + UserService.","functional/cake/"),
 (61,"type-classes","Type Classes via Dictionaries","Pass Functor/Monad instances explicitly.","TypeScript interfaces without implicits.","functional/typeclass/"),
 (62,"lenses-optics","Lenses & Optics","Composable immutable updates deep in a structure.","Surgical tweezers for nested records.","functional/lens/"),
 (63,"trampolining","Trampolining & Stack Safety","Convert deep recursion into a loop.","Bouncing instead of stacking trampolines.","functional/trampoline/"),
 (64,"effect-interpreters","Effect Systems & Interpreters","Separate describing effects from running them.","Screenplay vs performance.","functional/io/ + tagless + free"),
 (65,"vavr-vs-zazr","VAVR vs Zazr Deep Dive","Choose the right FP library on Java 25.","Classic toolkit vs redesigned sealed toolkit.","functional/vavrcompare/"),
]:
    add(num, slug, title, "7 · Advanced FP", "Functional", "Advanced",
        intent, analogy, "Serious FP architecture in Java.", "CRUD apps that don't need effect algebras.",
        ["Encodings are educational — production may use libraries.", "Tagless Final needs Kind encoding without HKTs."],
        code, "Witness types emulate higher-kinded types.",
        f"When to pick {title}?", "When you need testable effect boundaries or pure state threading.")

def read_code_snippet(hint: str) -> str:
    # try to find a java file under src matching hint
    base = ROOT / "src/main/java/com/buildwithwaqar/patterns"
    candidates = []
    h = hint.replace("\\", "/")
    for p in base.rglob("*.java"):
        rel = str(p.relative_to(base)).replace("\\", "/")
        if any(part.strip() and part.strip() in rel for part in h.replace("+", " ").split() if "/" in part or part.endswith(".java") or part.endswith("/")):
            candidates.append(p)
    # looser: last path segment
    key = h.split("+")[0].strip().rstrip("/")
    if key:
        for p in base.rglob("*.java"):
            if key in str(p).replace("\\", "/"):
                candidates.append(p)
    if not candidates:
        return "// see " + hint
    p = sorted(set(candidates), key=lambda x: len(str(x)))[0]
    lines = p.read_text().splitlines()
    # skip package/imports for brevity on slides; keep in explanation
    body = "\n".join(lines[:80])
    if len(lines) > 80:
        body += "\n// ..."
    return body

def render_ep(e: dict):
    code = read_code_snippet(e["code_hint"])
    pitfalls = "\n".join(f"- {p}" for p in e["pitfalls"])
    when = e["when"] if isinstance(e["when"], str) else "\n".join(f"- {x}" for x in e["when"])
    md = f"""# {e['title']}

**Season:** {e['season']} · **Category:** {e['category']} · **Level:** {e['level']}

## Intent
{e['intent']}

## Problem
Without this pattern, code tends toward: {e['naive']}

## Real-world Analogy
{e['analogy']}

## When to Use
- {when}
- The design force named in Intent shows up more than once.

## When Not to Use
- {e['when_not']}
- A simpler language feature (record, sealed switch, function value) already solves it.

## Naive Approach
```text
{e['naive']}
```

## Pattern Structure
```mermaid
flowchart LR
  Client --> Pattern
  Pattern --> Collaborators
```
{e['structure']}

## Classic Implementation
Primary code lives under `src/main/java/com/buildwithwaqar/patterns/{e['code_hint']}`.

```java
{code}
```

## Modern Java 25 / Functional
{e['modern']}

## Worked Example
```text
See the JUnit test beside the implementation.
Run: mvn -Dtest=*Test test
Focus package matching: {e['code_hint']}
```

## Pitfalls
{pitfalls}

## Interview Q&A
**Q: {e['iq']}**
A: {e['ia']}

**Q: How would you test this?**
A: Prefer injecting interfaces or interpreters; keep demos deterministic (fixed clocks, canned inputs).

## Key Takeaways
- {e['intent']}
- Prefer Java 25 sealed types / records / virtual threads where they simplify the classic shape.
- Ship working tests with every episode.

## Video Script
**[0:00] Hook** — Open with the analogy: {e['analogy']}
**[0:40] Intent** — State the design force in one sentence: {e['intent']}
**[1:30] Naive pain** — Show the messy approach: {e['naive']}
**[3:00] Pattern** — Walk the code under `{e['code_hint']}`; draw the mermaid structure on-screen.
**[6:00] Modern take** — {e['modern']}
**[8:00] Pitfalls & interview** — Cover: {e['iq']}
**[9:30] Outro** — Tease the next episode; point viewers to the GitHub repo and slides PDF.
"""
    out = ROOT / "series" / f"{e['num']:02d}-{e['slug']}" / "explanation.md"
    out.parent.mkdir(parents=True, exist_ok=True)
    out.write_text(md)
    print("episode", out.parent.name)

def main():
    for e in EPS:
        render_ep(e)
    # index
    lines = ["# Series Index — Build with Waqar\n", "Suggested playlist order (beginner → advanced).\n"]
    cur = None
    for e in EPS:
        if e["season"] != cur:
            cur = e["season"]
            lines.append(f"\n## Season {cur}\n")
        lines.append(f"- [{e['num']:02d}. {e['title']}]({e['num']:02d}-{e['slug']}/) — {e['level']}")
    (ROOT / "series" / "README.md").write_text("\n".join(lines) + "\n")
    print("total episodes", len(EPS))

if __name__ == "__main__":
    main()
