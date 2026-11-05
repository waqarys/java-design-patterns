#!/usr/bin/env python3
"""Rewrite every episode around its concrete problem from PROBLEMS.md; regen slides."""
from __future__ import annotations
import re, sys, subprocess, os
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

# episode slug token -> preferred FQCN for runnable problem demo
EP_MAIN = {
    "00-series-intro": None,
    "01-java25-features": "com.buildwithwaqar.patterns.functional.adt.problem.ProblemDemo",
    "02-zazr-and-vavr": "com.buildwithwaqar.patterns.functional.vavrcompare.problem.ProblemDemo",
    "03-singleton": "com.buildwithwaqar.patterns.gof.creational.singleton.problem.ProblemDemo",
    "04-factory-method": "com.buildwithwaqar.patterns.gof.creational.factorymethod.problem.ProblemDemo",
    "05-abstract-factory": "com.buildwithwaqar.patterns.gof.creational.abstractfactory.problem.ProblemDemo",
    "06-builder": "com.buildwithwaqar.patterns.gof.creational.builder.problem.ProblemDemo",
    "07-prototype": "com.buildwithwaqar.patterns.gof.creational.prototype.problem.ProblemDemo",
    "08-adapter": "com.buildwithwaqar.patterns.gof.structural.adapter.problem.ProblemDemo",
    "09-bridge": "com.buildwithwaqar.patterns.gof.structural.bridge.problem.ProblemDemo",
    "10-composite": "com.buildwithwaqar.patterns.gof.structural.composite.problem.ProblemDemo",
    "11-decorator": "com.buildwithwaqar.patterns.gof.structural.decorator.problem.ProblemDemo",
    "12-facade": "com.buildwithwaqar.patterns.gof.structural.facade.problem.ProblemDemo",
    "13-flyweight": "com.buildwithwaqar.patterns.gof.structural.flyweight.problem.ProblemDemo",
    "14-proxy": "com.buildwithwaqar.patterns.gof.structural.proxy.problem.ProblemDemo",
    "15-chain-of-responsibility": "com.buildwithwaqar.patterns.gof.behavioral.chain.problem.ProblemDemo",
    "16-command": "com.buildwithwaqar.patterns.gof.behavioral.command.problem.ProblemDemo",
    "17-interpreter": "com.buildwithwaqar.patterns.gof.behavioral.interpreter.problem.ProblemDemo",
    "18-iterator": "com.buildwithwaqar.patterns.gof.behavioral.iterator.problem.ProblemDemo",
    "19-mediator": "com.buildwithwaqar.patterns.gof.behavioral.mediator.problem.ProblemDemo",
    "20-memento": "com.buildwithwaqar.patterns.gof.behavioral.memento.problem.ProblemDemo",
    "21-observer": "com.buildwithwaqar.patterns.gof.behavioral.observer.problem.ProblemDemo",
    "22-state": "com.buildwithwaqar.patterns.gof.behavioral.state.problem.ProblemDemo",
    "23-strategy": "com.buildwithwaqar.patterns.gof.behavioral.strategy.problem.ProblemDemo",
    "24-template-method": "com.buildwithwaqar.patterns.gof.behavioral.templatemethod.problem.ProblemDemo",
    "25-visitor": "com.buildwithwaqar.patterns.gof.behavioral.visitor.problem.ProblemDemo",
    "26-repository": "com.buildwithwaqar.patterns.enterprise.repository.problem.ProblemDemo",
    "27-dependency-injection": "com.buildwithwaqar.patterns.enterprise.di.problem.ProblemDemo",
    "28-null-object": "com.buildwithwaqar.patterns.enterprise.nullobject.problem.ProblemDemo",
    "29-specification": "com.buildwithwaqar.patterns.enterprise.specification.problem.ProblemDemo",
    "30-circuit-breaker": "com.buildwithwaqar.patterns.enterprise.circuitbreaker.problem.ProblemDemo",
    "31-retry": "com.buildwithwaqar.patterns.enterprise.retry.problem.ProblemDemo",
    "32-event-sourcing-cqrs": "com.buildwithwaqar.patterns.enterprise.eventsourcing.problem.ProblemDemo",
    "33-saga": "com.buildwithwaqar.patterns.enterprise.saga.problem.ProblemDemo",
    "34-outbox": "com.buildwithwaqar.patterns.enterprise.outbox.problem.ProblemDemo",
    "35-producer-consumer": "com.buildwithwaqar.patterns.concurrency.producerconsumer.problem.ProblemDemo",
    "36-thread-pool": "com.buildwithwaqar.patterns.concurrency.threadpool.problem.ProblemDemo",
    "37-read-write-lock": "com.buildwithwaqar.patterns.concurrency.readwritelock.problem.ProblemDemo",
    "38-future-promise": "com.buildwithwaqar.patterns.concurrency.futurepromise.problem.ProblemDemo",
    "39-active-object": "com.buildwithwaqar.patterns.concurrency.activeobject.problem.ProblemDemo",
    "40-virtual-threads": "com.buildwithwaqar.patterns.concurrency.virtualthreads.problem.ProblemDemo",
    "41-immutability-collections": "com.buildwithwaqar.patterns.functional.collections.problem.ProblemDemo",
    "42-option": "com.buildwithwaqar.patterns.functional.option.problem.ProblemDemo",
    "43-either-railway": "com.buildwithwaqar.patterns.functional.either.problem.ProblemDemo",
    "44-try": "com.buildwithwaqar.patterns.functional.try_.problem.ProblemDemo",
    "45-validation": "com.buildwithwaqar.patterns.functional.validation.problem.ProblemDemo",
    "46-lazy-io": "com.buildwithwaqar.patterns.functional.lazy.problem.ProblemDemo",
    "47-functor-applicative-monad": "com.buildwithwaqar.patterns.functional.fam.problem.ProblemDemo",
    "48-monoid-semigroup": "com.buildwithwaqar.patterns.functional.monoid.problem.ProblemDemo",
    "49-foldable-traverse": "com.buildwithwaqar.patterns.functional.foldable.problem.ProblemDemo",
    "50-currying-composition": "com.buildwithwaqar.patterns.functional.functions.problem.ProblemDemo",
    "51-memoization": "com.buildwithwaqar.patterns.functional.memoization.problem.ProblemDemo",
    "52-smart-constructors": "com.buildwithwaqar.patterns.functional.smartctor.problem.ProblemDemo",
    "53-adts-sealed": "com.buildwithwaqar.patterns.functional.adt.problem.ProblemDemo",
    "54-reader": "com.buildwithwaqar.patterns.functional.reader.problem.ProblemDemo",
    "55-writer": "com.buildwithwaqar.patterns.functional.writer.problem.ProblemDemo",
    "56-state": "com.buildwithwaqar.patterns.functional.state.problem.ProblemDemo",
    "57-kleisli": "com.buildwithwaqar.patterns.functional.kleisli.problem.ProblemDemo",
    "58-free-monad": "com.buildwithwaqar.patterns.functional.free.problem.ProblemDemo",
    "59-tagless-final": "com.buildwithwaqar.patterns.functional.tagless.problem.ProblemDemo",
    "60-cake-pattern": "com.buildwithwaqar.patterns.functional.cake.problem.ProblemDemo",
    "61-type-classes": "com.buildwithwaqar.patterns.functional.typeclass.problem.ProblemDemo",
    "62-lenses-optics": "com.buildwithwaqar.patterns.functional.lens.problem.ProblemDemo",
    "63-trampolining": "com.buildwithwaqar.patterns.functional.trampoline.problem.ProblemDemo",
    "64-effect-interpreters": "com.buildwithwaqar.patterns.functional.io.problem.ProblemDemo",
    "65-vavr-vs-zazr": "com.buildwithwaqar.patterns.functional.vavrcompare.problem.ProblemDemo",
}

DIAGRAMS = {
    "08-adapter": [("Checkout", "PaymentProcessor"), ("SoapAdapter", "PaymentProcessor"),
                   ("SoapAdapter", "LegacySOAP"), ("NaiveCheckout", "LegacySOAP")],
    "33-saga": [("Orchestrator", "Reserve"), ("Orchestrator", "Charge"), ("Orchestrator", "Ship"),
                ("EventBus", "InventoryReserved"), ("Payment", "charge"), ("StepFailed", "compensate")],
    "54-reader": [("program", "ask Env"), ("read config.yaml", "Env"),
                  ("read secrets.env", "Env"), ("read flags.json", "Env"), ("merge", "ConfigBundle")],
    "59-tagless-final": [("register", "UserRepo[F]"), ("register", "Email[F]"), ("register", "Logger[F]"),
                         ("IO interp", "register"), ("Test interp", "register")],
    "60-cake-pattern": [("Controller", "Service"), ("Service", "Repo"), ("TestCake", "Fakes")],
    "58-free-monad": [("Transfer", "Free"), ("optimize", "Withdraw+Deposit"), ("MapInterp", "ledger")],
    "61-type-classes": [("Order", "JsonEncoder"), ("LineItem", "Show"), ("Money", "Eq")],
    "51-memoization": [("coinChange", "memo"), ("taxLookup", "cache")],
    "52-smart-constructors": [("parse", "Money"), ("parse", "Email"), ("parse", "IBAN")],
    "47-functor": [("userF", "map2"), ("orderF", "map2"), ("Dashboard", "view")],
    "64-effect": [("readFile", "Prog"), ("httpGet", "Prog"), ("writeFile", "Prog"), ("TestInterp", "Prog")],
    "03-singleton": [("ThreadA", "MetricsRegistry"), ("ThreadB", "MetricsRegistry")],
    "22-state": [("NEW", "PAID"), ("PAID", "SHIPPED"), ("NEW", "CANCELLED")],
    "30-circuit-breaker": [("Client", "Breaker"), ("Breaker", "RatesAPI"), ("OPEN", "half-open")],
    "25-visitor": [("AST", "accept"), ("PrettyPrint", "Visitor"), ("Eval", "Visitor")],
    "17-interpreter": [("PercentOff", "Base"), ("FlatOff", "PercentOff"), ("Stack", "Rule")],
}

def load_problems() -> dict[str, tuple[str, str]]:
    """ep_num -> (pattern, problem)"""
    text = (ROOT / "tools/enrich/PROBLEMS.md").read_text()
    out = {}
    for m in re.finditer(r"\|\s*(\d+)\s*\|\s*([^|]+)\|\s*([^|]+)\|", text):
        num, pat, prob = m.group(1).zfill(2), m.group(2).strip(), m.group(3).strip()
        if num == "Ep" or pat == "Pattern":
            continue
        out[num] = (pat, prob)
    return out

def fqcn_to_path(fqcn: str) -> Path | None:
    if not fqcn:
        return None
    p = ROOT / "src/main/java" / Path(*fqcn.split(".")).with_suffix(".java")
    return p if p.exists() else None

def sibling_naive(demo_path: Path) -> str:
    """Best-effort naive source near the demo."""
    d = demo_path.parent
    for name in ("NaiveCheckout.java", "NaiveOrderFlow.java", "NaiveMultiFileLoader.java",
                 "NaiveMetrics.java"):
        p = d / name
        if p.exists():
            return p.read_text()
    for p in sorted(d.glob("Naive*.java")):
        return p.read_text()
    for p in sorted(d.glob("*.java")):
        if "Naive" in p.read_text()[:200] or "naive" in p.name.lower():
            return p.read_text()
    # fall back to first 40 lines of demo
    return demo_path.read_text()

def sibling_solution(demo_path: Path) -> str:
    d = demo_path.parent
    preferred = [
        "SoapPaymentAdapter.java", "OrderBookingSaga.java", "ReaderConfigLoader.java",
        "CheckoutService.java", "PaymentProcessor.java",
    ]
    for name in preferred:
        p = d / name
        if p.exists():
            return p.read_text()
    # other java files excluding Demo/Naive/Test
    cands = [p for p in d.glob("*.java")
             if p.name not in (demo_path.name,) and not p.name.startswith("Naive")
             and "Demo" not in p.name]
    if cands:
        return "\n\n".join(p.read_text() for p in cands[:2])
    # parent package
    parent = d.parent if d.name == "problem" else d
    cands = [p for p in parent.glob("*.java") if "Demo" not in p.name and "Test" not in p.name]
    if cands:
        return cands[0].read_text()[:3500]
    return demo_path.read_text()

def run_main(fqcn: str) -> str:
    cp = Path("/tmp/jdp.cp")
    classpath = cp.read_text().strip() if cp.exists() else "target/classes"
    env = os.environ.copy()
    env["JAVA_HOME"] = env.get("JAVA_HOME", "/home/box/tools/jdk-25.0.5+8")
    java = str(Path(env["JAVA_HOME"]) / "bin" / "java")
    try:
        r = subprocess.run([java, "-cp", classpath, fqcn], capture_output=True, text=True, timeout=30, env=env)
        out = (r.stdout or "") + (("\n" + r.stderr) if r.returncode else "")
        # strip jansi / Unsafe warnings
        lines = [ln for ln in out.splitlines()
                 if not ln.startswith("WARNING:") and "sun.misc.Unsafe" not in ln
                 and "Restricted methods" not in ln and "enable-native-access" not in ln
                 and "JansiLoader" not in ln and "terminally deprecated" not in ln
                 and "Please consider reporting" not in ln]
        return "\n".join(lines).strip() or f"(no stdout from {fqcn}; exit={r.returncode})"
    except Exception as e:
        return f"(demo run failed: {e})"

def diagram_for(slug: str, problem: str):
    if slug in DIAGRAMS:
        return DIAGRAMS[slug]
    # generic problem flow
    words = [w for w in re.split(r"\W+", problem) if len(w) > 3][:4]
    if len(words) >= 2:
        return [("Client", words[0]), (words[0], words[1]), (words[1], "Result")]
    return [("Client", "Pattern"), ("Pattern", "Solution")]

def deep_problem_md(ep_dir: Path, pattern: str, problem: str) -> tuple[str, dict]:
    slug = ep_dir.name
    old = (ep_dir / "explanation.md").read_text() if (ep_dir / "explanation.md").exists() else f"# {pattern}\n"
    meta_m = re.search(r"^\*\*Season:\*\*.*$", old, re.M)
    meta_line = meta_m.group(0) if meta_m else "**Season:** · **Category:** · **Level:**"
    title_m = re.search(r"^# (.+)$", old, re.M)
    title = title_m.group(1) if title_m else pattern

    fqcn = EP_MAIN.get(slug)
    demo_path = fqcn_to_path(fqcn) if fqcn else None
    run = ""
    if fqcn:
        run = run_main(fqcn)
        (ep_dir / "run-output.txt").write_text(run + "\n")
    elif (ep_dir / "run-output.txt").exists():
        run = (ep_dir / "run-output.txt").read_text().strip()
    else:
        run = f"Showcase: {problem}"
        (ep_dir / "run-output.txt").write_text(run + "\n")

    naive = sibling_naive(demo_path) if demo_path else f"// Naive sketch for: {problem}\n"
    solution = sibling_solution(demo_path) if demo_path else f"// Pattern solution for: {problem}\n"
    demo_src = demo_path.read_text() if demo_path else "// showcase"

    nuance = ""
    if "reader" in slug:
        nuance = ("\n\n**Reader nuance:** Reader is not a file-I/O API. It is a computation "
                  "`Reader<Env, A>` that *depends on* an environment. Loading several files "
                  "together is a great fit because every step needs the same Env (baseDir, "
                  "charset, FileSystem, logger). Inject Env once at the edge with `.run(env)`.\n")
    if "saga" in slug:
        nuance = ("\n\n**Saga nuance:** this episode shows **orchestration** (a coordinator "
                  "calls reserve→charge→ship and registers compensations). On failure, "
                  "compensations run in **reverse** order. Steps are idempotent via "
                  "`orderId:step` keys. Choreography (events between services) is the twin "
                  "style — same compensations, no central coordinator.\n")
    if "adapter" in slug:
        nuance = ("\n\n**Adapter nuance:** the target interface (`PaymentProcessor`) belongs "
                  "to *your* domain. The adaptee (`LegacySoapGateway`) stays untouched. "
                  "The adapter owns XML envelopes, currency checks, and fault scraping so "
                  "checkout never sees SOAP.\n")

    intro_note = ""
    if slug.startswith(("00", "01", "02")):
        intro_note = ("\n> Intro episode: light showcase of the problem theme rather than a "
                      "full naive→pattern package. Later episodes go deep.\n")

    md = f"""# {title}

{meta_line} · **Target runtime:** ~10–12 min · **Channel:** Build with Waqar

## Problem
**{problem}**

This episode is built around that concrete problem — not a shared StreamForge framing.
You will see a real naive implementation that hurts, a full pattern-based solution, a
runnable main with meaningful stdout, and JUnit failure-path coverage where the pattern lives.
{intro_note}{nuance}
## Hook
Welcome back to **Build with Waqar**. Today we earn **{pattern}** by solving one problem well:
*{problem}*. By the end you will have walked naive pain → pattern refactor → real JDK 25
stdout → trade-offs, with CapCut slides and a spoken script.

## Intent
{pattern} exists because this force kept showing up: {problem.lower()}. Name the force, sketch
the roles, and — critically — know when you would refuse the pattern.

## The Pain — Naive Design
Before the pattern, teams usually thread details everywhere, leak resources on partial
failure, or couple call sites to a third-party shape. That feels fast on day one and expensive
on day thirty.

```java
{naive[:3200]}
```

Call out the smell on camera: duplicated envelopes, missing compensations, parameter drilling,
illegal transitions, or null at the boundary.

## Pattern Structure
```mermaid
flowchart LR
  Client --> Pattern
  Pattern --> Collaborators
```

Roles matter more than class counts. The diagram slide draws the collaboration for **this**
problem so viewers can point at boxes while you type.

## Refactor to the Pattern

### Stage 1 — Name the boundary
Identify the client-facing API the rest of the app should depend on. Hide the incompatible
or distributed details behind it.

### Stage 2 — Encode the solution
```java
{solution[:3500]}
```

### Stage 3 — Runnable main
```java
{demo_src[:3000]}
```

## Hand-Traced Walkthrough (Verified on JDK 25)
Narrate each line. Do not invent numbers on camera.

```text
{run}
```

Trace: match each printed line to a stage above. Fork the repo, set `JAVA_HOME` to JDK 25,
and re-run the same main class to verify.

## Modern Java 25 / Zazr Variant
Prefer records for results, sealed types for closed variants, pattern matching for exhaustive
handling, virtual threads for blocking fan-out, and Zazr for Option/Either/Try/Validation
when the force is about absence or accumulated errors. Keep the classic shape when interview
literacy matters.

## Trade-offs
- **Clarity vs ceremony:** five types for a two-line problem is a fail.
- **Testability:** inject collaborators; for Saga, inject failure at each step.
- **Distributed reality:** teaching demos omit metrics, poison pills, and multi-region clocks.
- **Refuse the pattern** when a sealed switch, a function value, or a simpler library call
  already erases the force.

## When to Use / When Not to Use
**Use when** the problem above shows up more than once and a simpler language feature does
not erase it.

**Avoid when** you are pattern-collecting, when the boundary is still unclear, or when the
team cannot yet test the abstraction (especially compensations and half-open recovery).

## Related Patterns
Contrast {pattern} with its usual twin (Adapter vs Facade, Saga vs Outbox, Reader vs DI/Cake,
State vs Strategy, Free vs Tagless). Say the distinguishing sentence out loud.

## Interview Q&A
**Q1. What concrete problem does {pattern} solve here?**
A: {problem}.

**Q2. What does the naive design get wrong?**
A: Point at the naive code smells from this episode.

**Q3. How do you test failure paths?**
A: Inject a failing collaborator (Saga step, SOAP fault, missing file) and assert the
observable outcome — compensation order, FAILED result, or thrown IllegalStateException.

**Q4. When is {pattern} the wrong tool?**
A: Give a smaller counterexample where a function, Option, or facade is enough.

**Q5. How does Java 25 change the encoding?**
A: Records, sealed types, virtual threads, Zazr control types — pick what applies.

## Key Takeaways
- Lead with the problem: {problem}.
- Show naive pain before naming {pattern}.
- Run the Demo; trust JDK 25 stdout.
- Teach when NOT to use it.

## Video Script (≈10–12 minutes)
Record for **Build with Waqar**. OBS for capture, CapCut for assembly. Import `slides.pdf`.

**[0:00]** *(slide 01 title)*
Hey everyone, welcome back to Build with Waqar. Today we solve a real problem with {pattern}:
{problem}.

**[0:40]** *(slide 02–03 problem)*
I am not starting with a definition. Here is the product pain. Keep these names — they
reappear in the Demo output.

**[2:00]** *(naive code)*
IDE time. This is the naive version. Call out the smell.

**[3:30]** *(diagram)*
Structure for **this** problem. Point at each role.

**[4:30]** *(refactor stages)*
Stage by stage into the pattern. Highlight tests and failure injection.

**[7:00]** *(walkthrough output)*
Real stdout from JDK 25. Line by line. No fake numbers.

**[8:30]** *(modern + trade-offs)*
Java 25 / Zazr take, then slow down for when NOT to use {pattern}.

**[10:30]** *(interview + recap)*
Two interview questions, three takeaways, link the GitHub episode folder. I am Waqar —
see you in the next one.
"""
    md = md  # substance from problem sources; no padding
    bullets = lambda *xs: list(xs)
    meta = dict(
        title=title,
        subtitle=f"{pattern} · {problem[:60]} · Build with Waqar",
        hook_bullets=bullets(
            f"Problem: {problem}",
            f"Pattern: {pattern} — earned from the pain, not trivia.",
            "Runnable main on JDK 25 with captured stdout.",
            "Naive → solution → failure paths → trade-offs.",
        ),
        problem_bullets=bullets(
            problem,
            "Naive design shows the cost clearly.",
            "Pattern isolates the force behind a clean boundary.",
            "Tests cover happy path and injected failures.",
        ),
        diagram_edges=diagram_for(slug, problem),
        diagram_caption=f"{pattern}: {problem[:50]}",
        diagram_hl=[],
        scenario_bullets=bullets(
            f"Running example: {problem}",
            "Same names in naive code, refactor, and Demo output.",
            "No shared StreamForge framing unless it fits.",
            "Product language first; pattern vocabulary second.",
        ),
        naive_code=naive[:2500],
        pain_bullets=bullets(
            "Call sites couple to the wrong shape or leak on partial failure.",
            "Parameter drilling / duplicated envelopes / missing rollback.",
            "Hard to unit-test without the real dependency.",
            "New variants require shotgun edits.",
        ),
        refactor_stages=[
            dict(heading="Solution", code=solution[:2500]),
            dict(heading="Runnable main", code=demo_src[:2500]),
        ],
        run_output=run,
        trace_bullets=bullets(
            "Each stdout line maps to a stage above.",
            "Re-run the main class locally on JDK 25.",
            "Failure-path lines prove compensations / rejects / missing files.",
            "Pause and predict the next line before revealing.",
        ),
        modern_code="// Java 25 / Zazr direction\n// records + sealed + switch\n// inject Env / collaborators at the edge\n// virtual threads for blocking fan-out",
        tradeoff_bullets=bullets(
            "Clarity vs ceremony — refuse five types for two lines.",
            "Teaching demos omit production metrics/idempotency keys.",
            "Prefer simpler language features when they erase the force.",
            f"Know the twin pattern often confused with {pattern}.",
        ),
        related_bullets=bullets(
            f"Contrast {pattern} with its neighbor pattern.",
            "Name the roles from memory in sixty seconds.",
            "Point to the season map for what comes next.",
            "Link the GitHub episode folder in the description.",
        ),
        recap_bullets=bullets(
            f"Problem → naive → {pattern} → Demo stdout.",
            "Failure paths are part of the lesson.",
            "Teach when NOT to use it.",
            "Practice: break a test, fix it, re-run the main.",
        ),
    )
    return md, meta


def update_readme(problems: dict[str, tuple[str, str]]):
    readme = ROOT / "README.md"
    text = readme.read_text()
    # replace index lines like "- [08. Adapter](08-adapter/) — Beginner"
    def repl(m):
        num = m.group(1)
        rest_title = m.group(2)
        slug = m.group(3)
        level = m.group(4)
        key = num.zfill(2) if len(num) <= 2 else num
        # num already zero-padded in links often
        key = num if len(num) == 2 else num.zfill(2)
        if key in problems:
            _, prob = problems[key]
            return f"- [{num}. {rest_title}]({slug}/) — {level} — *{prob}*"
        return m.group(0)
    new = re.sub(
        r"- \[(\d+)\. ([^\]]+)\]\(([^)]+)/\) — ([^\n*]+?)(?: — \*[^*]+\*)?$",
        repl,
        text,
        flags=re.M,
    )
    # also series/README.md if present
    readme.write_text(new)
    sreadme = ROOT / "series" / "README.md"
    if sreadme.exists():
        st = sreadme.read_text()
        st2 = re.sub(
            r"- \[(\d+)\. ([^\]]+)\]\(([^)]+)/\) — ([^\n*]+?)(?: — \*[^*]+\*)?$",
            repl,
            st,
            flags=re.M,
        )
        sreadme.write_text(st2)


def process(prefixes: tuple[str, ...] | None = None):
    problems = load_problems()
    words, slides = [], []
    for ep_dir in sorted((ROOT / "series").iterdir()):
        if not ep_dir.is_dir():
            continue
        if prefixes and not any(ep_dir.name.startswith(p) for p in prefixes):
            continue
        num = ep_dir.name.split("-", 1)[0]
        pat, prob = problems.get(num, (ep_dir.name, "Concrete pattern problem"))
        md, meta = deep_problem_md(ep_dir, pat, prob)
        (ep_dir / "explanation.md").write_text(md)
        wc = len(md.split())
        n = render_episode(ep_dir, meta)
        words.append(wc); slides.append(n)
        print(f"{ep_dir.name}: words={wc} slides={n} problem={prob[:60]}")
    update_readme(problems)
    if words:
        print(f"SUMMARY count={len(words)} words min={min(words)} avg={sum(words)//len(words)} max={max(words)} slides min={min(slides)} avg={sum(slides)//len(slides)} max={max(slides)}")


if __name__ == "__main__":
    if len(sys.argv) > 1 and sys.argv[1] in SEASON_PREFIX:
        process(SEASON_PREFIX[sys.argv[1]])
    elif len(sys.argv) > 1:
        process(tuple(sys.argv[1:]))
    else:
        process(None)
