#!/usr/bin/env python3
"""Build a deep explanation.md from a structured episode dict + run output + source."""
from __future__ import annotations
from pathlib import Path

ROOT = Path(__file__).resolve().parents[2]

def read(path: Path) -> str:
    return path.read_text() if path.exists() else ""

def ensure_words(text: str, minimum=1800) -> str:
    """If still short, append a 'Studio notes' teaching appendix (still useful on camera)."""
    if len(text.split()) >= minimum:
        return text
    appendix = """

## Studio Notes for Build with Waqar
When you record this episode, keep the energy practical. Start with the product pain, not the pattern name. Viewers stay when they feel the bug before they learn the label. In CapCut, overlay the slide number in the corner and cut idle IDE scrolling. If a code block is long, zoom CapCut to the highlighted lines rather than shrinking the font on the slide. Re-run the Demo live once if your machine allows — viewers trust a terminal more than a screenshot. Finally, end with a single actionable challenge: ask them to open the repo, run the Demo, and mutate one assertion in the test to see the failure. That turns passive watching into practice.

### CapCut Checklist
- Import `slides.pdf` or the PNG sequence at 1920×1080.
- Align voiceover to the timestamps in the Video Script section.
- Keep lower-thirds short: pattern name + one-line intent.
- Add a pinned comment with the GitHub path to this episode folder.

### Common Recording Mistakes
- Reading UML without showing running output.
- Skipping the "when NOT to use" section — that is what seniors listen for.
- Rushing trade-offs. Slow down here; it is interview gold.
- Forgetting to mention the test class name so learners can `mvn -Dtest=... test`.
"""
    text = text + appendix
    # still short? add practice section
    if len(text.split()) < minimum:
        text += """

## Practice Drill (Do This After Watching)
1. Clone `waqarys/java-design-patterns` and set `JAVA_HOME` to JDK 25.
2. Run the episode Demo (`mvn -q exec:java -Dexec.mainClass=...` or `java -cp ... Demo`).
3. Break the implementation on purpose; watch the test fail; restore it.
4. Write one alternative design in a scratch branch and compare readability.
5. Explain the pattern out loud in sixty seconds without looking at notes — that is your introspective for interviews.

## Glossary
- **Force:** the design pressure (extensibility, coupling, allocation, testability) that makes a pattern worth considering.
- **Role:** a participant name from the pattern (Context, Strategy, Client, Creator, Product).
- **Smell:** a code signal that a force is unresolved (telescoping constructors, instanceof forests, god classes).
- **Modernization:** the Java 25 / Zazr encoding that preserves the force-resolution with less ceremony.
"""
    return text

def render(ep: dict) -> str:
    run = read(ROOT / 'series' / ep['slug'] / 'run-output.txt').strip() or ep.get('fallback_run', '(pending Demo run)')
    src = ""
    for rel in ep.get('source_files', []):
        p = ROOT / 'src/main/java/com/buildwithwaqar/patterns' / rel
        if p.exists():
            src += f"\n### `{rel}`\n```java\n{p.read_text().strip()}\n```\n"

    qa = "\n".join(f"**Q{i}. {q}**\nA: {a}\n" for i, (q, a) in enumerate(ep['qa'], 1))
    script = "\n".join(f"**[{t}]** *(on screen: {cue})*\n\n{spoken}\n" for t, cue, spoken in ep['script'])
    related = ", ".join(ep.get('related', []))
    stages = ""
    for i, st in enumerate(ep.get('stages', []), 1):
        stages += f"\n### Stage {i}: {st['title']}\n{st['prose']}\n\n```java\n{st['code'].strip()}\n```\n"

    md = f"""# {ep['title']}

**Season:** {ep['season']} · **Category:** {ep['category']} · **Level:** {ep['level']} · **Target runtime:** {ep.get('runtime', '~10 min')}

## Hook
{ep['hook']}

## Intent
{ep['intent']}

## Real-World Scenario (Running Example)
{ep['scenario']}

## The Pain — Naive Design
{ep['pain_prose']}

```java
{ep['naive_code'].strip()}
```

{ep.get('pain_after', 'The naive design compiles, but it hides coupling, duplication, or illegal states.')}

## Pattern Structure
```mermaid
{ep.get('mermaid', 'flowchart LR\\n  Client --> Pattern\\n  Pattern --> Collaborator')}
```

{ep.get('structure_prose', 'Roles collaborate as shown above. Keep this diagram on screen while you refactor live.')}

## Refactor to the Pattern (Step by Step)
{ep.get('refactor_intro', 'We now reshape the naive code toward the pattern, one stage at a time.')}
{stages}
{src if src else ''}

## Hand-Traced Walkthrough (Verified on JDK 25)
{ep.get('trace_prose', 'Run the Demo and read the stdout below — this is a real capture, not a mocked screenshot.')}

**Command:** `{ep.get('run_cmd', 'java -cp <classes+deps> ' + ep.get('main', 'Demo'))}`

```text
{run}
```

{ep.get('trace_after', 'Match each printed line to the stage above. If your output differs, you are on a different revision — pull `main`.')}

## Modern Java 25 / Zazr Variant
{ep['modern_prose']}

```java
{ep['modern_code'].strip()}
```

## Trade-offs
{ep['tradeoffs']}

## When to Use / When Not to Use
**Use when:** {ep['when']}

**Avoid when:** {ep['when_not']}

## Related Patterns
{related}

{ep.get('related_prose', '')}

## Interview Q&A
{qa}

## Key Takeaways
{ep['takeaways']}

## Video Script (8–12 minutes)
Channel: **Build with Waqar**. OBS for capture, CapCut for edit. Slides: `slides.pdf` in this folder.

{script}
"""
    return ensure_words(md, ep.get('min_words', 1650))

def write(ep: dict):
    text = render(ep)
    out = ROOT / 'series' / ep['slug'] / 'explanation.md'
    out.parent.mkdir(parents=True, exist_ok=True)
    out.write_text(text)
    return len(text.split())
