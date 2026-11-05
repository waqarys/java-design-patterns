from pathlib import Path
ROOT = Path(__file__).resolve().parents[1]

def write(rel: str, content: str):
    p = ROOT / rel
    p.parent.mkdir(parents=True, exist_ok=True)
    text = content[1:] if content.startswith("\n") else content
    p.write_text(text)
    print("wrote", rel)
