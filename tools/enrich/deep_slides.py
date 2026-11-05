#!/usr/bin/env python3
"""Dense 12-20 slide decks with diagrams, code stages, and run output."""
from __future__ import annotations
import os, re, sys, shutil
from pathlib import Path
from PIL import Image

ROOT = Path(__file__).resolve().parents[2]
sys.path.insert(0, str(ROOT / "tools" / "pipeline"))
import make_lecture as ml

def chunk_code(code: str, n=22):
    lines = [l for l in code.splitlines()]
    out, cur = [], []
    for l in lines:
        cur.append(l)
        if len(cur) >= n and (l.strip() in ("}", "") or l.strip().startswith("//")):
            out.append("\n".join(cur)); cur = []
    if any(x.strip() for x in cur):
        out.append("\n".join(cur))
    return out[:4] or ["// (see explanation.md)"]

def render_episode(ep_dir: Path, meta: dict):
    title = meta["title"]
    subtitle = meta.get("subtitle", "Build with Waqar · Java 25 Design Patterns")
    S = []
    S.append(dict(kind="title", heading=title, subtitle=subtitle))
    S.append(dict(kind="bullets", heading="Today's Hook", bullets=meta["hook_bullets"]))
    S.append(dict(kind="bullets", heading="The Problem", bullets=meta["problem_bullets"]))
    if meta.get("diagram_edges"):
        S.append(dict(kind="diagram", heading="Structure", edges=meta["diagram_edges"],
                      highlight=meta.get("diagram_hl", []), caption=meta.get("diagram_caption", title)))
    S.append(dict(kind="bullets", heading="Real-World Scenario", bullets=meta["scenario_bullets"]))
    for i, code in enumerate(chunk_code(meta.get("naive_code", "// naive"))):
        S.append(dict(kind="code", heading="Naive Code" + (f" ({i+1})" if i else ""), code=code, lang="java", font_size=22))
    S.append(dict(kind="bullets", heading="Why Naive Hurts", bullets=meta["pain_bullets"]))
    for stage in meta.get("refactor_stages", []):
        for i, code in enumerate(chunk_code(stage["code"])):
            S.append(dict(kind="code", heading=stage["heading"] + (f" ({i+1})" if i else ""), code=code, lang="java", font_size=22))
    run = meta.get("run_output", "").strip() or "(run Demo.main)"
    S.append(dict(kind="code", heading="Walkthrough Output", code=run, lang="text", font_size=24))
    S.append(dict(kind="bullets", heading="Step-by-Step Trace", bullets=meta["trace_bullets"]))
    for i, code in enumerate(chunk_code(meta.get("modern_code", meta.get("naive_code", "")))):
        S.append(dict(kind="code", heading="Modern Java 25 / Zazr" + (f" ({i+1})" if i else ""), code=code, lang="java", font_size=22))
    S.append(dict(kind="bullets", heading="Trade-offs", bullets=meta["tradeoff_bullets"]))
    S.append(dict(kind="bullets", heading="Related Patterns", bullets=meta["related_bullets"]))
    S.append(dict(kind="bullets", heading="Recap", bullets=meta["recap_bullets"]))
    # ensure 12-20
    while len(S) < 12:
        S.append(dict(kind="bullets", heading="Key Reminder", bullets=meta["recap_bullets"][:4]))
    S = S[:20]

    d = ep_dir / "slides"
    shutil.rmtree(d, ignore_errors=True)
    d.mkdir(parents=True)
    paths = []
    for i, s in enumerate(S, 1):
        p = d / f"{i:02d}.png"
        ml.render(s, str(p), i, len(S), title)
        paths.append(p)
    ims = [Image.open(p).convert("RGB") for p in paths]
    ims[0].save(ep_dir / "slides.pdf", save_all=True, append_images=ims[1:], resolution=150)
    return len(paths)

if __name__ == "__main__":
    print("deep_slides module ready")
