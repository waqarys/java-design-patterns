"""Render dark 1080p slides + PDF from an explanation.md using AlgoExpert pipeline."""
from __future__ import annotations
import os, re, sys, shutil
from pathlib import Path
from PIL import Image

ROOT = Path(__file__).resolve().parents[1]
sys.path.insert(0, str(ROOT / "tools" / "pipeline"))
import make_lecture as ml  # noqa

BLOCK = re.compile(r"```(?:java|text|mermaid)?\n(.*?)```", re.S)

def bullets_of(s: str, limit=8):
    items, cur = [], None
    for line in s.splitlines():
        if line.strip().startswith("```"):
            break
        t = re.sub(r"[*`#]", "", line).strip()
        if not t:
            cur = None
            continue
        if re.match(r"^([-*]|\d+\.)\s", line.strip()):
            t = re.sub(r"^([-*]|\d+\.)\s+", "", re.sub(r"[`*]", "", line.strip()))
            items.append(t)
            cur = len(items) - 1
        elif cur is not None:
            items[cur] += " " + t
        else:
            items.append(t)
            cur = len(items) - 1
    out = []
    for i in items:
        i = i.replace("$", r"\$")
        if len(i) > 140:
            i = i[:137] + "..."
        out.append(i)
        if len(out) >= limit:
            break
    return out

def first_code(s: str):
    m = BLOCK.search(s or "")
    return m.group(1).strip() if m else None

def chunks(code: str, n=28):
    lines = code.splitlines()
    out, cur = [], []
    for l in lines:
        cur.append(l)
        if len(cur) >= n and (l.strip() in ("}", "") or l.strip().startswith("//")):
            out.append("\n".join(cur))
            cur = []
    if any(x.strip() for x in cur):
        out.append("\n".join(cur))
    return out[:3]

def parse_sections(md: str):
    title = re.search(r"^# (.+)$", md, re.M).group(1)
    meta_m = re.search(r"^\*\*Season:\*\*.*$", md, re.M)
    meta = meta_m.group(0).replace("**", "") if meta_m else ""
    # Prefer the Problem one-liner as the title subtitle (short, filmable).
    prob = ""
    pm = re.search(r"^## Problem\n\*\*(.+?)\*\*", md, re.M | re.S)
    if pm:
        prob = " ".join(pm.group(1).split())
    # Compact season · category · level (drop Runtime/Channel)
    short_meta = ""
    if meta_m:
        parts = []
        for key in ("Season", "Category", "Level"):
            mm = re.search(rf"\*\*{key}:\*\*\s*([^*·]+)", meta_m.group(0))
            if mm:
                parts.append(mm.group(1).strip())
        short_meta = " · ".join(parts)
    secs = {}
    for m in re.finditer(r"^## (.+?)\n(.*?)(?=^## |\Z)", md, re.M | re.S):
        secs[m.group(1).strip()] = m.group(2).strip()
    return title, meta, secs, prob, short_meta

def _short(s: str, n=70) -> str:
    s = " ".join((s or "").split())
    return s if len(s) <= n else s[: n - 1].rstrip(" ·-,") + "…"

def render_episode(ep_dir: Path):
    md_path = ep_dir / "explanation.md"
    if not md_path.exists():
        return
    md = md_path.read_text()
    title, meta, secs, prob, short_meta = parse_sections(md)
    if prob and short_meta:
        subtitle = _short(prob, 70) + "\n" + short_meta
    elif short_meta:
        subtitle = short_meta
    elif prob:
        subtitle = _short(prob, 70)
    else:
        subtitle = "Build with Waqar · Java 25 Design Patterns"
    S = [dict(kind="title", heading=title, subtitle=subtitle)]
    for name in [
        "Intent", "Problem", "Real-world Analogy", "When to Use", "When Not to Use",
        "Naive Approach", "Pattern Structure", "Classic Implementation",
        "Modern Java 25 / Functional", "Worked Example", "Pitfalls",
        "Interview Q&A", "Key Takeaways",
    ]:
        if name not in secs:
            continue
        code = first_code(secs[name])
        bullets = bullets_of(secs[name])
        if name in ("Classic Implementation", "Modern Java 25 / Functional") and code:
            for i, c in enumerate(chunks(code)):
                S.append(dict(kind="code", heading=name + (f" ({i+1})" if i else ""), code=c, lang="java", font_size=22))
        elif name == "Worked Example" and code:
            pre = "\n".join(bullets[:3])
            S.append(dict(kind="code", heading=name, code=(pre + "\n\n" + code) if pre else code, lang="text", font_size=24))
        elif name == "Pattern Structure" and "```mermaid" in secs[name]:
            # keep mermaid as bullets of nodes if present; also show caption bullets
            S.append(dict(kind="bullets", heading=name, bullets=bullets or ["See mermaid diagram in explanation.md"]))
        elif bullets:
            S.append(dict(kind="bullets", heading=name, bullets=bullets))

    # Video script teaser
    if "Video Script" in secs:
        S.append(dict(kind="bullets", heading="Video Script Highlights", bullets=bullets_of(secs["Video Script"], 6)))

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
    print(f"slides {ep_dir.name}: {len(paths)} png + pdf")

def render_all(filter_prefix: str | None = None):
    series = ROOT / "series"
    for ep in sorted(series.iterdir()):
        if not ep.is_dir():
            continue
        if filter_prefix and not ep.name.startswith(filter_prefix):
            continue
        if (ep / "explanation.md").exists():
            render_episode(ep)

if __name__ == "__main__":
    pref = sys.argv[1] if len(sys.argv) > 1 else None
    render_all(pref)
