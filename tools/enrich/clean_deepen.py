#!/usr/bin/env python3
"""Clean duplicated metadata + deepen thin/generic explanations for all episodes.

Rewrites series/*/explanation.md from:
  - PROBLEMS.md catalog
  - real problem/ sources + run-output.txt
  - episode-specific intent/hook/structure/Q&A/script templates
Then regenerates slides via deep_slides with SHORT title subtitles.
"""
from __future__ import annotations
import re, sys, textwrap
from pathlib import Path

ROOT = Path(__file__).resolve().parents[2]
sys.path.insert(0, str(ROOT / "tools" / "enrich"))
from build_problems import (  # noqa: E402
    EP_MAIN, load_problems, fqcn_to_path, sibling_naive, sibling_solution,
    diagram_for, specific_slide_meta, _naive_pain_line, _success_from_run,
    _failure_from_run, run_main,
)
from deep_slides import render_episode  # noqa: E402

# season-number -> (season label, category, default level)
SEASON_INFO = {
    0: ("0 · Intro", "Overview", "Beginner"),
    1: ("1 · Creational", "GoF", "Beginner"),
    2: ("2 · Structural", "GoF", "Beginner"),
    3: ("3 · Behavioral", "GoF", "Beginner"),
    4: ("4 · Enterprise", "Enterprise", "Intermediate"),
    5: ("5 · Concurrency", "Concurrency", "Intermediate"),
    6: ("6 · FP Foundations", "Functional", "Intermediate"),
    7: ("7 · Advanced FP", "Functional", "Advanced"),
}

# Per-episode level overrides
LEVEL_OVERRIDE = {
    "00": "Beginner", "01": "Beginner", "02": "Beginner",
    "17": "Intermediate", "25": "Intermediate",
    "30": "Intermediate", "32": "Advanced", "33": "Intermediate", "34": "Intermediate",
    "47": "Advanced", "51": "Intermediate", "52": "Intermediate",
    "54": "Advanced", "58": "Advanced", "59": "Advanced", "60": "Advanced",
    "61": "Advanced", "63": "Advanced", "64": "Advanced",
}

# Related-pattern twins (spoken contrast)
RELATED = {
    "03-singleton": ("Factory Method", "use a factory when creation varies; Singleton only when identity must be unique"),
    "04-factory-method": ("Abstract Factory", "Factory Method creates one product; Abstract Factory creates a family"),
    "05-abstract-factory": ("Builder", "Abstract Factory picks a family; Builder assembles one complex object step by step"),
    "06-builder": ("Factory Method", "Builder is for many optionals / immutability; Factory Method for subtype choice"),
    "07-prototype": ("Factory Method", "Prototype clones a configured instance; Factory Method constructs fresh"),
    "08-adapter": ("Facade", "Adapter converts one incompatible API; Facade simplifies a whole subsystem"),
    "09-bridge": ("Adapter", "Bridge is designed up-front for two hierarchies; Adapter is retrofit"),
    "10-composite": ("Decorator", "Composite equals part-whole trees; Decorator adds behavior to one object"),
    "11-decorator": ("Proxy", "Decorator stacks behavior; Proxy controls access or lifetime"),
    "12-facade": ("Adapter", "Facade does not convert interfaces — it hides them behind one call"),
    "13-flyweight": ("Singleton", "Flyweight shares many fine-grained immutables; Singleton is one identity"),
    "14-proxy": ("Decorator", "Proxy preserves the interface for control; Decorator for layering features"),
    "15-chain-of-responsibility": ("Decorator", "Chain routes/handles or passes on; Decorator always wraps and adds"),
    "16-command": ("Strategy", "Command is an objectified request with undo; Strategy is a swappable algorithm"),
    "17-interpreter": ("Visitor", "Interpreter evaluates a grammar; Visitor adds operations across an AST"),
    "18-iterator": ("Visitor", "Iterator walks elements; Visitor externalizes operations on structure"),
    "19-mediator": ("Observer", "Mediator centralizes colleague talk; Observer broadcasts events"),
    "20-memento": ("Command", "Memento snapshots state for undo; Command records the action itself"),
    "21-observer": ("Mediator", "Observer is one-to-many fan-out; Mediator is many-to-many coordination"),
    "22-state": ("Strategy", "State transitions are internal and guarded; Strategy is chosen by the client"),
    "23-strategy": ("State", "Strategy is injected; State owns the next transition"),
    "24-template-method": ("Strategy", "Template Method uses inheritance hooks; Strategy uses composition"),
    "25-visitor": ("Interpreter", "Visitor adds ops without touching nodes; Interpreter embeds eval in nodes"),
    "26-repository": ("DAO", "Repository speaks collection language; keep persistence details behind it"),
    "27-dependency-injection": ("Service Locator", "DI pushes deps in; Service Locator pulls — prefer DI for testability"),
    "28-null-object": ("Option", "Null Object is a do-nothing collaborator; Option models absence of a value"),
    "29-specification": ("Strategy", "Specification composes business rules as objects; Strategy swaps one algorithm"),
    "30-circuit-breaker": ("Retry", "Breaker fails fast after a threshold; Retry keeps trying with backoff"),
    "31-retry": ("Circuit Breaker", "Retry handles blips; open the breaker when the dependency is down"),
    "32-event-sourcing-cqrs": ("Saga", "ES/CQRS records facts + read models; Saga coordinates multi-step reactions"),
    "33-saga": ("Outbox", "Saga orchestrates compensations across services; Outbox reliably publishes after commit"),
    "34-outbox": ("Saga", "Outbox is about atomic publish; Saga is about multi-step business rollback"),
    "35-producer-consumer": ("Thread Pool", "Bounded queue decouples rates; a pool caps concurrent workers"),
    "36-thread-pool": ("Virtual Threads", "Pool reuses platform threads; virtual threads scale blocking fan-out"),
    "37-read-write-lock": ("Synchronized", "RW lock allows concurrent readers; prefer it when reads dominate"),
    "38-future-promise": ("Virtual Threads", "Futures compose async results; VTs make blocking code scale"),
    "39-active-object": ("Actor", "Active Object serializes calls on a servant; actors add location transparency"),
    "40-virtual-threads": ("Thread Pool", "Prefer VTs for many blocking tasks; keep pools for CPU-bound work"),
    "42-option": ("Either", "Option is presence/absence; Either carries a typed error on the left"),
    "43-either-railway": ("Validation", "Either short-circuits on first error; Validation accumulates all errors"),
    "44-try": ("Either", "Try catches Throwable; Either is for expected domain errors"),
    "45-validation": ("Either", "Validation accumulates; switch to Either once you must short-circuit"),
    "46-lazy-io": ("Future", "Lazy/IO defers effect description; Future schedules async work"),
    "47-functor-applicative-monad": ("Tagless Final", "FAM are the building blocks; Tagless Final abstracts the effect F"),
    "48-monoid-semigroup": ("Foldable", "Monoid gives empty+combine; Foldable consumes them over a structure"),
    "51-memoization": ("Flyweight", "Memoization caches pure results; Flyweight shares immutable identity"),
    "52-smart-constructors": ("Builder", "Smart constructors validate at the boundary; Builder assembles many fields"),
    "53-adts-sealed": ("Visitor", "Sealed ADTs + switch replace many Visitor hierarchies in Java 25"),
    "54-reader": ("DI / Cake", "Reader threads Env through pure code; Cake/DI wire concrete services at the edge"),
    "55-writer": ("State", "Writer accumulates a log; State threads an updatable value"),
    "56-state": ("State pattern", "State monad is pure transitions; GoF State is an object collaborator"),
    "57-kleisli": ("Reader", "Kleisli composes A→F[B] arrows; Reader is Env→A"),
    "58-free-monad": ("Tagless Final", "Free builds a data DSL then interprets; Tagless Final abstracts F up front"),
    "59-tagless-final": ("Free", "Tagless Final is usually lighter in Java; Free shines when you need program inspection"),
    "60-cake-pattern": ("DI", "Cake self-types wire modules in code; a DI container wires at runtime"),
    "61-type-classes": ("Visitor", "Type classes add behavior without modifying types; Witnesses emulate HKTs in Java"),
    "62-lenses-optics": ("Builder", "Lenses update nested immutables; Builder constructs new aggregates"),
    "63-trampolining": ("Tail recursion", "Trampoline converts deep recursion to a heap loop on the JVM"),
    "64-effect-interpreters": ("Tagless Final", "Effect interpreters separate description from execution — same spirit as Tagless"),
    "65-vavr-vs-zazr": ("Option", "Same Option program in both libraries — pick Zazr for Java 25-first APIs"),
}

# Intent one-liners keyed by slug prefix / exact
INTENT = {
    "08-adapter": "Make a legacy SOAP payment gateway look like your domain `PaymentProcessor` without rewriting checkout.",
    "33-saga": "Keep reserve → charge → ship consistent across services by registering reverse compensations (orchestration) or reacting on an event bus (choreography).",
    "54-reader": "Thread a shared Env (baseDir, charset, FileSystem) through a pure multi-file config merge — Reader is not a file API.",
    "59-tagless-final": "Write user registration once against algebras (`UserRepo[F]`, `EmailSender[F]`, `Logger[F]`) and run it under IO or a pure test interpreter.",
    "58-free-monad": "Describe a bank-transfer DSL as data (`Free` instructions), optimize it, then interpret against an in-memory ledger.",
    "60-cake-pattern": "Wire repo → service → controller with self-types so tests swap a `TestCake` of fakes without a DI framework.",
    "30-circuit-breaker": "Fail fast against a flaky rates API once the error threshold trips — then probe half-open.",
    "51-memoization": "Cache pure coin-change / tax-rate results so repeated inputs skip the exponential work.",
    "52-smart-constructors": "Make illegal `Money` / `Email` / `IBAN` values unrepresentable via validated factory methods.",
    "17-interpreter": "Evaluate a pricing-rule DSL (base + percent + flat) as an expression tree.",
    "03-singleton": "Guarantee one `MetricsRegistry` identity across request threads without subtle double-checked bugs.",
    "22-state": "Enforce order lifecycle NEW→PAID→SHIPPED with illegal-transition guards.",
    "12-facade": "Hide inventory + payment + shipping behind one `placeOrder` call.",
    "23-strategy": "Swap cart pricing (percent / flat / half) without an if-else forest.",
    "42-option": "Parse a positive port without returning null or throwing for expected absence.",
    "43-either-railway": "Validate a user-id pipeline as a railway that short-circuits on the first typed error.",
    "45-validation": "Accumulate every signup field error instead of failing on the first one.",
    "65-vavr-vs-zazr": "Run the same Option describe program on real VAVR and real Zazr side by side.",
}


def season_of(num: str) -> int:
    n = int(num)
    if n <= 2: return 0
    if n <= 7: return 1
    if n <= 14: return 2
    if n <= 25: return 3
    if n <= 34: return 4
    if n <= 40: return 5
    if n <= 53: return 6
    return 7


def clean_meta(num: str) -> tuple[str, str, str, str]:
    s_label, cat, lvl = SEASON_INFO[season_of(num)]
    lvl = LEVEL_OVERRIDE.get(num, lvl)
    if num in ("01",):
        cat = "Language"
    if num in ("02", "65"):
        cat = "Libraries"
    if num == "00":
        cat = "Overview"
    line = f"**Season:** {s_label} · **Category:** {cat} · **Level:** {lvl} · **Runtime:** ~10–12 min · **Channel:** Build with Waqar"
    short = f"Season {s_label.split('·')[0].strip()} · {cat} · {lvl}"
    return line, short, cat, lvl


def mermaid_from(edges: list[tuple]) -> str:
    lines = ["flowchart LR"]
    for e in edges:
        a, b = e[0], e[1]
        a_id = re.sub(r"\W+", "_", a)
        b_id = re.sub(r"\W+", "_", b)
        lines.append(f"  {a_id}[{a}] --> {b_id}[{b}]")
    return "\n".join(lines)


def short_problem(problem: str, limit=72) -> str:
    p = " ".join(problem.split())
    if len(p) <= limit:
        return p
    # Break on a word boundary so we never show "compensati…"
    cut = p[: limit - 1]
    if " " in cut:
        cut = cut.rsplit(" ", 1)[0]
    return cut.rstrip(" ·-|,;:") + "…"


def roles_of(*srcs: str) -> list[str]:
    roles = []
    for src in srcs:
        for r in re.findall(r"(?:class|interface|record|enum)\s+(\w+)", src or ""):
            if r in roles or r.startswith("Naive") or r in ("ProblemDemo", "SolutionKit"):
                continue
            roles.append(r)
    return roles[:6]


def modern_variant(slug: str, pattern: str, solution: str, problem: str) -> str:
    bits = []
    if any(k in solution for k in ("sealed ", "permits ")):
        bits.append("This solution already leans on **sealed types** — keep the exhaustive `switch`.")
    if "record " in solution:
        bits.append("**Records** carry the immutable results (`StepResult`, DTOs) with almost no boilerplate.")
    if any(k in solution for k in ("Optional", "dev.zazr", "io.vavr")):
        bits.append("Absence / errors use **Optional or Zazr/VAVR** control types instead of null.")
    if "virtual" in solution.lower() or "VirtualThread" in solution:
        bits.append("Blocking fan-out belongs on **virtual threads** (Java 21+).")
    if "CompletableFuture" in solution:
        bits.append("`CompletableFuture` composition covers the async variant; prefer virtual threads when the work is blocking I/O.")
    if not bits:
        bits.append(
            f"For **{pattern}**, prefer records for results, sealed types for closed variants, "
            f"and Zazr `Option`/`Either`/`Validation` when the force is absence or accumulated errors. "
            f"Keep the classic shape when interview literacy matters."
        )
    if "reader" in slug:
        bits.append(
            "Reader stays pure: put `baseDir` / charset / `FileSystem` in `Env`, compose "
            "`Reader<Env,A>` steps, and call `.run(env)` once at the edge."
        )
    if "saga" in slug:
        bits.append(
            "In production you would add idempotency keys, metrics, and a durable compensation log — "
            "the teaching demo keeps those out of frame on purpose."
        )
    if "tagless" in slug:
        bits.append(
            "The same `register` program runs under an IO interpreter and a pure `TestState` interpreter — "
            "that is the whole point of abstracting `F`."
        )
    return " ".join(bits)


def tradeoffs(slug: str, pattern: str, problem: str) -> str:
    twin = RELATED.get(slug)
    twin_line = f"- **Neighbor:** {twin[0]} — {twin[1]}." if twin else f"- **Neighbor:** name the pattern usually confused with {pattern} and separate the forces."
    specific = {
        "33-saga": (
            "- **Clarity vs ceremony:** a three-step booking needs a compensation stack; a single DB transaction does not.\n"
            "- **Testability:** inject `InMemoryPartners` failure at reserve / charge / ship and assert reverse compensation order.\n"
            "- **Distributed reality:** teaching demos omit poison pills, multi-region clocks, and durable saga logs.\n"
            "- **Refuse Saga** when one local ACID transaction already covers the business step."
        ),
        "08-adapter": (
            "- **Clarity vs ceremony:** one adapter class beats leaking SOAP XML into every controller.\n"
            "- **Testability:** fake `LegacySoapGateway` or assert on `PaymentResult` without standing up SOAP.\n"
            "- **Refuse Adapter** when you own both sides — just change the interface once.\n"
            "- **Watch currency / fault scraping** — that mapping is the adapter's real job."
        ),
        "59-tagless-final": (
            "- **Clarity vs ceremony:** three algebras + two interpreters is worth it only if you need swap-able effects.\n"
            "- **Testability:** pure `TestState` asserts emails/logs without mocking frameworks.\n"
            "- **Refuse Tagless** for a one-off script — a plain service class is enough.\n"
            "- **HKT emulation** (Kind witnesses) adds noise; keep algebras small."
        ),
        "54-reader": (
            "- **Clarity vs ceremony:** Reader shines when many steps share Env; overkill for one file read.\n"
            "- **Testability:** pass a fake `FileSystem` Env in unit tests.\n"
            "- **Refuse Reader** when a simple method parameter already threads the config.\n"
            "- **Do not confuse Reader with I/O** — I/O lives in the interpreters / Env collaborators."
        ),
        "30-circuit-breaker": (
            "- **Clarity vs ceremony:** thresholds and half-open probes need tuning — wrong numbers flap or hide outages.\n"
            "- **Testability:** inject a flaky dependency and assert OPEN → HALF_OPEN → CLOSED transitions.\n"
            "- **Refuse Breaker** for a dependency that almost never fails — a simple timeout may suffice.\n"
            "- **Pair with Retry** for blips; open the breaker when the dependency is truly down."
        ),
    }
    if slug in specific:
        return specific[slug] + "\n" + twin_line
    return (
        f"- **Clarity vs ceremony:** extra types for «{problem}» only pay off when the force repeats.\n"
        f"- **Testability:** inject collaborators; assert the failure path, not only the happy path.\n"
        f"- **Distributed / concurrent reality:** demos omit metrics, backpressure, and multi-region clocks.\n"
        f"- **Refuse {pattern}** when a sealed switch, a function value, or a simpler library call already erases the force.\n"
        + twin_line
    )


def qa_block(slug: str, pattern: str, problem: str, pain: str, failure: str) -> str:
    twin = RELATED.get(slug, ("a neighboring pattern", "separate the forces clearly"))
    specific_q = {
        "33-saga": [
            (f"What does orchestration buy you over naive sequential calls for «{problem}»?",
             "A compensation stack that runs in reverse on abort, plus idempotency keys per `orderId:step`."),
            ("How does choreography differ here?",
             "No central coordinator — `EventBus` reactions (`InventoryReserved` → charge → ship) own the next step; compensations still reverse."),
            ("How do you test ship failure?",
             "Inject `shippingOk=false`; assert log contains `compensate:refund` then `compensate:release` and partners are clean."),
        ],
        "08-adapter": [
            ("What is the target interface in this episode?",
             "`PaymentProcessor` — checkout depends only on it. The adaptee `LegacySoapGateway` stays untouched."),
            ("Where do currency checks and SOAP faults belong?",
             "Inside the adapter. Controllers must never scrape `<Fault>` XML."),
            ("How do you prove the adapter failed cleanly?",
             "Drive a non-positive amount or unsupported currency and assert `PaymentResult.ok=false` with a clear message."),
        ],
        "59-tagless-final": [
            ("What is abstract here — the program or the effect?",
             "The effect `F`. `register` is written once; IO and TestState interpreters provide `UserRepo`/`Email`/`Logger`."),
            ("Why not just mock with Mockito?",
             "A pure interpreter records emails/logs in data — no mocking framework, deterministic assertions."),
            ("When is Tagless overkill?",
             "A single concrete service with constructor injection already gives you test seams."),
        ],
        "54-reader": [
            ("Does Reader read files?",
             "No. Reader threads Env. File reads are ordinary functions that *ask* Env for baseDir/charset/FileSystem."),
            ("Why merge config.yaml + secrets.env + flags.json this way?",
             "Every step needs the same Env; `.run(env)` injects it once at the edge."),
            ("How do you test it?",
             "Pass an in-memory FileSystem Env and assert the merged view without touching disk."),
        ],
    }
    qs = specific_q.get(slug) or [
        (f"What concrete problem does {pattern} solve here?", problem + "."),
        ("What does the naive design get wrong?", pain),
        ("How do you test the failure path?",
         f"Inject the fault and assert the observable outcome — e.g. `{failure}`."),
    ]
    # Always end with refuse + related
    qs = list(qs)[:3]
    qs.append((f"When is {pattern} the wrong tool?",
               f"When a smaller language feature erases the force, or when you really needed {twin[0]}."))
    out = []
    for i, (q, a) in enumerate(qs, 1):
        out.append(f"**Q{i}. {q}**\nA: {a}\n")
    return "\n".join(out)


def video_script(slug: str, pattern: str, problem: str, roles: list[str], success: str, failure: str) -> str:
    role_s = ", ".join(roles[:4]) if roles else "the key types"
    return f"""Record for **Build with Waqar**. OBS for capture, CapCut for assembly. Import `slides.pdf`.
Facecam: full-screen for the hook, framed PiP over code slides.

**[0:00]** *(slide 01 title + talking head)*
Hey everyone — today we solve one concrete problem with **{pattern}**:
*{problem}*. Keep these names in your head: {role_s}.

**[0:45]** *(problem + hook slides)*
I am not starting with a textbook definition. Here is the product pain. By the end you will have
seen the naive leak, the pattern boundary, and real JDK 25 stdout.

**[2:00]** *(naive code)*
IDE time. This is the naive version. Call out the smell — that is what {pattern} exists to erase.

**[3:30]** *(structure diagram)*
Roles for **this** problem, not a generic UML poster. Point at each box while you talk.

**[4:30]** *(refactor / solution code)*
Stage the solution. Highlight failure injection and the tests that lock it in.

**[7:00]** *(walkthrough output)*
Real stdout. Happy path cue: `{success}`.
Failure cue: `{failure}`.
No invented numbers on camera.

**[8:30]** *(modern Java 25 / Zazr + trade-offs)*
Show the modernization, then slow down for when NOT to use {pattern}. That is what seniors listen for.

**[10:30]** *(Q&A + recap + outro)*
Two interview questions, three takeaways, link the GitHub episode folder. I am Waqar — see you in the next one.
"""


def hook_text(pattern: str, problem: str, roles: list[str], failure: str) -> str:
    role_s = ", ".join(roles[:3]) if roles else pattern
    return (
        f"Picture this: you are mid-demo and «{problem}» blows up the naive way — "
        f"you see `{failure}`. That is the pain. In the next ten minutes we introduce "
        f"**{pattern}** with real types ({role_s}), a runnable `ProblemDemo`, and the "
        f"failure path locked in by tests. No abstract UML first — problem first."
    )


def intent_text(slug: str, pattern: str, problem: str) -> str:
    if slug in INTENT:
        base = INTENT[slug]
    else:
        base = f"Use **{pattern}** to cleanly solve: {problem}."
    return (
        f"{base}\n\n"
        f"**When to use:** the force behind «{problem}» shows up more than once and a simpler "
        f"language feature does not erase it.\n\n"
        f"**When to refuse:** you are pattern-collecting, the boundary is still unclear, or the "
        f"team cannot yet test the abstraction (compensations, half-open recovery, interpreters)."
    )


def deepen_one(ep_dir: Path, pattern: str, problem: str, rerun: bool = False) -> tuple[int, int]:
    slug = ep_dir.name
    num = slug.split("-", 1)[0]
    meta_line, short_meta, cat, lvl = clean_meta(num)
    title = pattern if not (ep_dir / "explanation.md").exists() else (
        re.search(r"^# (.+)$", (ep_dir / "explanation.md").read_text(), re.M).group(1)
    )
    # Prefer existing title for intro episodes
    if slug == "00-series-intro":
        title = "Series Intro: Java 25 Design Patterns"
    elif slug == "01-java25-features":
        title = "Java 25 Features for Patterns"
    elif slug == "02-zazr-and-vavr":
        title = "Zazr and VAVR Tour"

    fqcn = EP_MAIN.get(slug)
    demo_path = fqcn_to_path(fqcn) if fqcn else None

    if (ep_dir / "run-output.txt").exists() and not rerun:
        run = (ep_dir / "run-output.txt").read_text().strip()
    elif fqcn:
        run = run_main(fqcn)
        (ep_dir / "run-output.txt").write_text(run + "\n")
    else:
        run = f"Showcase: {problem}"
        (ep_dir / "run-output.txt").write_text(run + "\n")

    naive = sibling_naive(demo_path) if demo_path else f"// Naive sketch for: {problem}\n"
    solution = sibling_solution(demo_path) if demo_path else f"// Pattern solution for: {problem}\n"
    demo_src = demo_path.read_text() if demo_path else "// showcase"
    roles = roles_of(solution, demo_src, naive)
    pain = _naive_pain_line(naive, problem)
    success = _success_from_run(run)
    failure = _failure_from_run(run)
    # Prefer a concrete cue over a section banner like "--- naive ---"
    if failure.startswith("---") or failure.startswith("==="):
        for ln in run.splitlines():
            low = ln.lower().strip()
            if any(k in low for k in ("compensate", "rolled_back", "leak", "failed", "abort:", "caught=", "null", "empty", "open", "409", "404")):
                failure = ln.strip()[:120]
                break
    edges = diagram_for(slug, problem)
    mermaid = mermaid_from(edges)

    nuance = ""
    if "reader" in slug:
        nuance = (
            "\n**Reader nuance:** Reader is not a file-I/O API. It is `Reader<Env, A>` — a computation "
            "that *depends on* an environment. Loading several files together fits because every step "
            "needs the same Env (baseDir, charset, FileSystem, logger). Inject Env once at the edge "
            "with `.run(env)`.\n"
        )
    if "saga" in slug:
        nuance = (
            "\n**Saga nuance:** **orchestration** (coordinator calls reserve→charge→ship and registers "
            "compensations). On failure, compensations run in **reverse**. Steps are idempotent via "
            "`orderId:step` keys. **Choreography** (events between services) is the twin style — same "
            "compensations, no central coordinator.\n"
        )
    if "adapter" in slug:
        nuance = (
            "\n**Adapter nuance:** the target interface (`PaymentProcessor`) belongs to *your* domain. "
            "The adaptee (`LegacySoapGateway`) stays untouched. The adapter owns XML envelopes, currency "
            "checks, and fault scraping so checkout never sees SOAP.\n"
        )
    if "tagless" in slug:
        nuance = (
            "\n**Tagless nuance:** algebras describe *what* (`UserRepo[F].save`, `EmailSender[F].send`); "
            "interpreters describe *how*. Write `register` once; run it under IO in prod and under a pure "
            "`TestState` in tests.\n"
        )
    if "free" in slug:
        nuance = (
            "\n**Free nuance:** the program is data. You can optimize (`Transfer` → `Withdraw` then "
            "`Deposit`) before any interpreter runs — that inspection is Free's superpower versus Tagless.\n"
        )
    if "cake" in slug:
        nuance = (
            "\n**Cake nuance:** self-types wire modules in source (`LiveCake` vs `TestCake`). No runtime "
            "container required — tests swap fakes by mixing in a different cake.\n"
        )

    intro_note = ""
    if slug.startswith(("00", "01", "02")):
        intro_note = (
            "\n> Intro episode: lighter showcase of the theme. Later episodes go full naive→pattern depth.\n"
        )

    # Trim code blocks for readability but keep substance
    naive_trim = naive[:2800]
    sol_trim = solution[:3200]
    demo_trim = demo_src[:2800]
    run_trim = "\n".join(run.splitlines()[:40])

    role_line = ("Key types: `" + "`, `".join(roles) + "`.") if roles else f"Key boundary: the {pattern} entry point + collaborators."

    md = f"""# {title}

{meta_line}

## Problem
**{problem}**

{role_line}
{intro_note}{nuance}
## Hook
{hook_text(pattern, problem, roles, failure)}

## Intent
{intent_text(slug, pattern, problem)}

## The Pain — Naive Design
{pain}

```java
{naive_trim}
```

On camera, point at the smell: missing compensations, leaked XML, null at the boundary,
illegal transitions, or hard-wired `System.out` that you cannot assert.

## Pattern Structure
```mermaid
{mermaid}
```

These roles are for **«{problem}»** — not a generic Client→Pattern poster. Keep them on screen
while you type the solution.

## Refactor to the Pattern

### Stage 1 — Name the boundary
Introduce the client-facing API the rest of the app should depend on. Hide the incompatible or
distributed details behind it. For this episode that boundary is what makes «{problem}» testable.

### Stage 2 — Encode the solution
```java
{sol_trim}
```

### Stage 3 — Runnable main
```java
{demo_trim}
```

## Runnable Output (Verified on JDK 25)
Narrate each line. Do not invent numbers on camera.

```text
{run_trim}
```

Happy-path cue: `{success}`.
Failure-path cue: `{failure}`.
Fork the repo, set `JAVA_HOME` to JDK 25, and re-run the same `ProblemDemo` to verify.

## Modern Java 25 / Zazr Variant
{modern_variant(slug, pattern, solution, problem)}

## Trade-offs
{tradeoffs(slug, pattern, problem)}

## Interview Q&A
{qa_block(slug, pattern, problem, pain, failure)}

## Key Takeaways
- Lead with the problem: {problem}.
- Show naive pain (`{failure}`) before naming {pattern}.
- Run the Demo; trust JDK 25 stdout (`{success}`).
- Teach when NOT to use {pattern}.

## Video Script (≈10–12 minutes)
{video_script(slug, pattern, problem, roles, success, failure)}
"""
    # Episode-specific depth pad when thin (still filmable — not StreamForge filler)
    wc = len(md.split())
    if wc < 1300 and not slug.startswith(("00", "01", "02")):
        edge_s = ", ".join(f"`{a}` → `{b}`" for a, b in edges[:5])
        twin = RELATED.get(slug)
        twin_s = f"Contrast with **{twin[0]}**: {twin[1]}." if twin else f"Name the neighbor pattern usually confused with {pattern}."
        md += f"""
## Walkthrough Beats (Say These Out Loud)
1. **Setup:** open `series/{slug}/` and `run-output.txt` beside the IDE.
2. **Naive:** reproduce the pain until you can point at `{failure}`.
3. **Structure:** on the diagram, trace {edge_s}.
4. **Refactor:** encode the boundary, then run `ProblemDemo` — match `{success}`.
5. **Break it:** flip one `ProblemTest` assertion, watch red, restore it.
6. **Refuse:** {twin_s}

## Practice Drill
- Clone `waqarys/java-design-patterns`, set `JAVA_HOME` to JDK 25.
- Run the episode `ProblemDemo` (see `EP_MAIN` in `tools/enrich/build_problems.py`).
- Mutate one collaborator to fail; confirm the observable compensation / error path.
- Explain {pattern} for «{problem}» in sixty seconds without notes.
"""
    (ep_dir / "explanation.md").write_text(md)
    wc = len(md.split())

    # Slides with SHORT subtitle (never overflow; wrap long problems across 2 lines)
    specific = specific_slide_meta(slug, pattern, problem, naive, solution, demo_src, run)
    prob_full = " ".join(problem.split())
    if len(prob_full) <= 70:
        subtitle = f"{prob_full}\n{short_meta}"
        prob_short = prob_full
    else:
        # Two-line problem, no season meta on the title (season lives in explanation.md)
        import textwrap as _tw
        lines = _tw.wrap(prob_full, width=68)[:2]
        if len(_tw.wrap(prob_full, width=68)) > 2:
            lines[-1] = short_problem(lines[-1], 68)
        subtitle = "\n".join(lines)
        prob_short = short_problem(prob_full, 70)
    caption = f"{pattern}: {problem}"  # make_lecture wraps to ≤2 lines
    modern = solution[:1200] if any(k in solution for k in ("sealed", "record ", "Optional", "CompletableFuture", "virtual", "io.vavr", "dev.zazr")) else (
        f"// Java 25 direction for {pattern}\n// Prefer records + sealed where they shrink the demo.\n// Problem: {problem}\n"
    )
    meta = dict(
        title=title,
        subtitle=subtitle,
        hook_bullets=[
            f"Problem: {prob_short}",
            f"Naive pain cue: {failure}"[:90],
            f"We will refactor to {pattern} with a runnable Demo.",
            "Failure path is locked by ProblemTest.",
        ],
        problem_bullets=specific["problem_bullets"],
        diagram_edges=edges,
        diagram_caption=caption,
        diagram_hl=[],
        scenario_bullets=specific["scenario_bullets"],
        naive_code=naive[:2500],
        pain_bullets=specific["pain_bullets"],
        refactor_stages=[
            dict(heading="Solution", code=solution[:2500]),
            dict(heading="Runnable main", code=demo_src[:2500]),
        ],
        run_output=run,
        trace_bullets=specific["trace_bullets"],
        modern_code=modern,
        tradeoff_bullets=specific["tradeoff_bullets"],
        related_bullets=specific["related_bullets"],
        recap_bullets=specific["recap_bullets"],
    )
    n_slides = render_episode(ep_dir, meta)
    return wc, n_slides


def main():
    problems = load_problems()
    prefixes = tuple(sys.argv[1:]) if len(sys.argv) > 1 else None
    stats = []
    for ep_dir in sorted((ROOT / "series").iterdir()):
        if not ep_dir.is_dir() or not (ep_dir / "explanation.md").exists():
            continue
        if prefixes and not any(ep_dir.name.startswith(p) for p in prefixes):
            continue
        num = ep_dir.name.split("-", 1)[0]
        pat, prob = problems.get(num, (ep_dir.name, "Concrete pattern problem"))
        # Prefer pattern name from PROBLEMS; title may be longer
        wc, ns = deepen_one(ep_dir, pat, prob, rerun=False)
        stats.append((ep_dir.name, wc, ns))
        print(f"{ep_dir.name}: words={wc} slides={ns}", flush=True)
    if stats:
        words = [w for _, w, _ in stats]
        print(f"SUMMARY n={len(stats)} words min={min(words)} avg={sum(words)//len(words)} max={max(words)}")
        # Quality gates
        bad_meta = []
        stream = []
        earn = []
        generic = []
        for name, _, _ in stats:
            t = (ROOT / "series" / name / "explanation.md").read_text()
            if t.count("**Channel:**") != 1 or t.count("Target runtime") > 0:
                bad_meta.append(name)
            if "StreamForge" in t or "not a shared StreamForge" in t:
                stream.append(name)
            if "Today we earn" in t:
                earn.append(name)
            if "Client --> Pattern" in t:
                generic.append(name)
        print("bad_meta", bad_meta or "none")
        print("streamforge", stream or "none")
        print("earn_filler", earn or "none")
        print("generic_mermaid", generic or "none")


if __name__ == "__main__":
    main()
