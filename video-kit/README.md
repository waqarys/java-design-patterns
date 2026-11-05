# Build with Waqar: video kit

A reusable kit for making polished YouTube episodes from the slides in this repo (and the LeetCode slides in
`waqarys/AlgoExpert@leetcode`) with **OBS Studio + CapCut**. It has an animated intro and outro, branded stingers with
matching SFX, section cards, a lower third, facecam frames, a procedurally generated music bed, an OBS scene collection,
and a one-command auto-editor.

- **Start here:** [VIDEO-WORKFLOW.md](VIDEO-WORKFLOW.md), the full OBS → CapCut workflow, camera tips, and the hook and episode checklists.
- **Demo videos + asset zip:** GitHub release [`video-kit-v1`](https://github.com/waqarys/java-design-patterns/releases/tag/video-kit-v1).
- **Preview:** [demos/contact-sheet.png](demos/contact-sheet.png).

```bash
pip install pillow numpy edge-tts   # + ffmpeg
python build_episode.py --slides ../series/33-saga/slides --script episodes/33-saga.md \
       --out out/saga.mp4 --thumb thumbnails/saga.png --thumb-slide 9
```
All audio is synthesised in `scripts/make_audio.py`, so it has no copyright or Content-ID issues.
