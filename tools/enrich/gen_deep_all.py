#!/usr/bin/env python3
"""Generate deep explanation.md + dense slides for all episodes."""
from __future__ import annotations
import sys
from pathlib import Path
ROOT = Path(__file__).resolve().parents[2]
sys.path.insert(0, str(ROOT / "tools" / "enrich"))
from expand import write as write_ep, render
from deep_slides import render_episode

# (slug, title, season, category, level, main_fqcn, pkg_hint, intent, analogy, when, when_not,
#  scenario, pain, naive, stage1_title, stage1_prose, stage1_code, modern_prose, modern_code,
#  tradeoffs, related, diagram_edges)

def qa_block(name, extra=None):
    base = [
        (f"What problem does {name} solve?", f"It resolves a recurring design force around {name.lower()} without scattering ad-hoc conditionals."),
        (f"When should you avoid {name}?", "When a simpler language feature or a smaller API change removes the force entirely."),
        (f"How do you test code that uses {name}?", "Prefer injecting interfaces or interpreters; keep demos deterministic with fixed clocks and canned inputs."),
        (f"How does Java 25 change {name}?", "Records, sealed types, pattern matching, and virtual threads often shrink the classic encoding."),
        (f"What related pattern is commonly confused with {name}?", "Name the neighbor pattern and the distinguishing force — interviews love this contrast."),
        (f"What is a production pitfall for {name}?", "Ignoring concurrency, idempotency, or observability when the teaching demo is single-threaded."),
    ]
    if extra:
        base.extend(extra)
    return base

def script_for(title):
    return [
        ("0:00", "title slide", f"Hey everyone, welcome back to Build with Waqar. Today we go deep on {title} in Java 25 — with a runnable Demo and CapCut-ready slides."),
        ("0:45", "hook slide", "I will start with a StreamForge product pain, not the pattern name. Feel the bug first."),
        ("1:30", "scenario", "Here is the concrete scenario we will carry through the whole episode."),
        ("2:30", "naive code slide", "Switch to IDE. This is the naive version. Watch what hurts: coupling, duplication, or illegal states."),
        ("4:00", "diagram", "Back to slides — here is the structure diagram. Keep these roles in your head."),
        ("5:00", "refactor stages", "IDE again. We refactor stage by stage until the Demo output matches the walkthrough."),
        ("7:00", "walkthrough output", "Show the real stdout captured on JDK 25. Narrate each line."),
        ("8:30", "modern variant", "Now the Java 25 or Zazr modernization — same force, less ceremony."),
        ("9:30", "trade-offs", "Slow down. When NOT to use this matters more than the happy path."),
        ("10:30", "interview + outro", f"Interview drill, then wrap. Next episode continues the season. I am Waqar — see you there."),
    ]

def src_snippet(hint: str, limit=60) -> str:
    base = ROOT / "src/main/java/com/buildwithwaqar/patterns"
    for p in base.rglob("*.java"):
        if hint.replace("\\", "/") in str(p).replace("\\", "/"):
            if p.name == "Demo.java":
                continue
            lines = p.read_text().splitlines()
            return "\n".join(lines[:limit])
    return "// see repo"

EPISODES = []

def add(**kw):
    EPISODES.append(kw)

# --- Season 0 ---
add(slug="00-series-intro", title="Series Intro: Java 25 Design Patterns", season="0 · Intro", category="Overview", level="Beginner",
    main="(curriculum)", intent="Orient learners to the Build with Waqar design-patterns curriculum on Java 25 + Zazr/VAVR.",
    scenario="You are launching StreamForge, a creator SaaS. You need a shared vocabulary with your team and a recording plan for YouTube. This episode is the syllabus and the filming contract for every later video.",
    pain_prose="The naive way to 'learn patterns' is memorizing names from a listicle without running code. That collapses in interviews and design reviews.",
    naive_code="// 1) Memorize 23 names\n// 2) Screenshot a UML diagram\n// 3) Never run Demo.main\n// 4) Freeze when asked 'when NOT to use this?'",
    stages=[{"title":"Adopt a product metaphor","prose":"StreamForge is our running product: notifications, themed UI, billing, workers, pure domain logic.","code":"// Seasons map to StreamForge subsystems\n// S1 create objects · S2 structure · S3 behavior\n// S4 enterprise · S5 concurrency · S6-7 FP"},
            {"title":"Adopt an episode contract","prose":"Every episode ships explanation, slides, Demo, tests, and captured stdout.","code":"series/NN-slug/\n  explanation.md\n  slides/*.png\n  slides.pdf\n  run-output.txt"}],
    modern_prose="Java 25 + Zazr means we teach classic forces, then modern encodings. No preview flags.",
    modern_code="// JDK 25 + dev.zazr:zazr-core:0.4.0 + io.vavr:vavr:1.1.0\n// mvn test",
    tradeoffs="- Depth over trivia.\n- Teaching-sized distributed patterns.\n- You record voiceover; scripts are prompts.",
    when="Starting the series or onboarding contributors to the repo.", when_not="If you only need one pattern — jump to that episode after skimming Season 0.",
    related=["Factory Method","Strategy","Tagless Final"], source_files=[],
    mermaid="flowchart LR\n  S0[Intro] --> S1[Creational]\n  S1 --> S2[Structural]\n  S2 --> S3[Behavioral]\n  S3 --> S4[Enterprise]\n  S4 --> S5[Concurrency]\n  S5 --> S6[FP Foundations]\n  S6 --> S7[Advanced FP]",
    diagram_edges=[("Intro","Creational"),("Creational","Structural"),("Structural","Behavioral"),("Behavioral","Enterprise"),("Enterprise","Concurrency"),("Concurrency","FP"),("FP","Advanced FP")],
    fallback_run="See season README for the playlist map.")

add(slug="01-java25-features", title="Java 25 Features for Patterns", season="0 · Intro", category="Language", level="Beginner",
    main="com.buildwithwaqar.patterns.functional.adt.Demo", intent="Show how records, sealed types, pattern matching, and virtual threads change pattern encodings.",
    scenario="StreamForge models payments as CARD/CASH/WIRE. The old mutable Payment class allows illegal field combinations. We rebuild it as a sealed ADT and preview virtual threads for workers.",
    pain_prose="Optional fields on one class compile even when they contradict the type tag.",
    naive_code="class Payment {\n  enum Type { CARD, CASH, WIRE }\n  Type type; int amountCents; String last4; String iban;\n}",
    stages=[{"title":"Sealed products","prose":"Each variant carries only its data.","code":src_snippet("functional/adt/Payment.java")},
            {"title":"Exhaustive switch","prose":"Compiler forces new variants to be handled.","code":"return switch (p) {\n  case CardPayment(var c, var last4) -> \"card ****\" + last4;\n  case CashPayment(var c) -> \"cash \" + c;\n  case WirePayment(var c, var iban) -> \"wire \" + iban;\n};"}],
    modern_prose="Virtual threads become the default concurrency tool in Season 5; sealed+switch replaces many Visitors.",
    modern_code="try (var pool = Executors.newVirtualThreadPerTaskExecutor()) {\n  pool.submit(this::transcode);\n}",
    tradeoffs="- Sealed is a closed world.\n- Virtual threads help blocking I/O, not magic CPU speedups.",
    when="Any new pattern implementation on JDK 25.", when_not="Maintaining a Java 11 library without a toolchain upgrade.",
    related=["Visitor","ADT","Strategy"], source_files=["functional/adt/Payment.java"],
    mermaid="classDiagram\n  Payment <|-- CardPayment\n  Payment <|-- CashPayment\n  Payment <|-- WirePayment",
    diagram_edges=[("Payment","CardPayment"),("Payment","CashPayment"),("Payment","WirePayment")],
    fallback_run="(from ADT Demo)")

add(slug="02-zazr-and-vavr", title="Zazr and VAVR Tour", season="0 · Intro", category="FP Libraries", level="Beginner",
    main="com.buildwithwaqar.patterns.functional.vavrcompare.Demo", intent="Introduce Zazr as the Java 25 FP library and VAVR for comparison.",
    scenario="StreamForge validation and optional config values must stop using null. We compare VAVR Option with Zazr Option pattern matching.",
    pain_prose="Null checks and Optional sprawl make railway-style code ugly.",
    naive_code="String name = map.get(\"name\");\nif (name != null) return \"hi \" + name;\nreturn \"hi stranger\";",
    stages=[{"title":"Zazr Option","prose":"Sealed Some/None — exhaustive switch.","code":src_snippet("functional/option/ZazrOptionDemo.java")},
            {"title":"Side-by-side","prose":"VAVR map/getOrElse vs Zazr switch.","code":src_snippet("functional/vavrcompare/VavrVsZazr.java")}],
    modern_prose="Prefer Zazr for new Java 25 code; keep VAVR when maintaining older modules.",
    modern_code="switch (opt) {\n  case Option.Some(var n) -> \"got \" + n;\n  case Option.None() -> \"missing\";\n}",
    tradeoffs="- Two libraries in one repo need clear package boundaries.\n- Zazr rejects null inside Option.",
    when="Writing null-hostile functional Java on JDK 25.", when_not="A tiny script where Optional is enough.",
    related=["Option","Validation","Either"], source_files=["functional/vavrcompare/VavrVsZazr.java"],
    mermaid="flowchart LR\n  VAVR --> Compare\n  Zazr --> Compare\n  Compare --> Choose",
    diagram_edges=[("VAVR Option","Describe"),("Zazr Option","Describe")],
    fallback_run="(from VavrVsZazr Demo)")

print("season0 stubs in list", len(EPISODES))
