# Build with Waqar: video workflow (OBS → CapCut in 1–2 hours)

This kit turns the slides you already have (`series/NN-*/slides`, `leetcode/content/*/slides`) plus your own
OBS recording into a finished YouTube episode. Two reference demos were built with it:

| Demo | Length | What it shows |
|---|---|---|
| `demo-design-pattern-saga.mp4` | 3:37 | Design-pattern episode (Saga, ep 33) |
| `demo-algorithm-two-sum.mp4` | 2:53 | LeetCode episode (0001 Two Sum) |

Download them from the `video-kit-v1` GitHub release. Watch one end to end before your first edit; your episodes
should follow the same rhythm.

> **All audio in this kit is synthesised from code** (`scripts/make_audio.py`, numpy only, no samples or loops
> from anywhere). Whooshes, pops, dings, risers, impacts, glitches and the music bed are yours to use with no
> Content-ID or copyright risk. The narration in the demos is edge-tts `en-US-GuyNeural`. It's a stand-in for your voice.

---

## 0. Episode structure (the rhythm viewers expect)

```
[HOOK 8–15 s]  Full facecam, talking head, one punchy question + kinetic caption
[INTRO 6.5 s]  Logo slam (riser → impact), channel name, episode title slot
[01 PROBLEM]   section card → slides (+ lower third on the first slide)
[02 NAIVE]     section card → naive code / why it hurts
[03 PATTERN]   section card → diagram / insight (KEY POINT callout + ding)
[04 CODE]      section card → code slides / IDE recording with facecam PiP
[05 TRADE-OFFS / COMPLEXITY]
[06 RECAP]     section card → recap slide, call to action
[OUTRO 9.5 s]  Subscribe click, bell, "watch next", repo URL, 2 empty end-screen slots
```
Every slide change gets a stinger and a whoosh. Every section start gets a card with a swoosh and pop, the intro gets a riser and impact, and every
key point gets a ding. Music runs under everything, about 20 dB below the voice.

## 1. Kit contents (`video-kit/`)

| Path | Use |
|---|---|
| `assets/intro/intro_generic.mp4` | Intro with an empty title slot. Add your title as a text layer in CapCut from 3.6 s. |
| `out/_work_*/intro.mp4` (built per episode) | Intro with the title already baked in. Made by `build_episode.py`. |
| `assets/outro/outro_generic.mp4` | Outro. Add your "watch next" text, or build a per-episode one. |
| `assets/stingers/stinger_{swipe,glitch,lightleak}.mov` | Transparent (alpha) stingers, 0.7 s. Use for OBS stinger transitions or overlays. |
| `assets/stingers/stinger_*_green.mp4` | Same stingers on chroma green, SFX baked in. Use in CapCut with Chroma key. |
| `assets/stingers/stinger_*_sfx.wav` | Just the matching sound. |
| `assets/cards/card_0N_*.mp4` | Six section cards, 2.2 s each, with sound. |
| `assets/overlays/lower_third_generic.png` | Name bar (transparent PNG). |
| `assets/overlays/facecam_frame_{rounded,circle}.png` | Brand-gradient PiP frame with a soft shadow. Goes **above** the camera. |
| `assets/overlays/facecam_placeholder_*.png` | The demo stand-ins showing where your face goes. |
| `assets/overlays/obs_mask_*.png`, `fullcam_frame.png` | Used by the OBS scene collection. |
| `assets/audio/*.wav` | whoosh, swoosh, pop, click, ding, riser, impact, glitch, shimmer, `music_bed_loop.wav` (45 s, seamless). |
| `obs/BuildWithWaqar_SceneCollection.json` | Importable OBS scenes. See §3. |
| `build_episode.py` | One-command auto-edit. See §2. |
| `thumbnails/` | Two sample 1280×720 thumbnails. |
| `episodes/*.md` | Narration scripts for the two demos. Copy one for a new episode. |

## 2. Fastest path: one command

```bash
python -m venv .venv && .venv/bin/pip install pillow numpy edge-tts      # once (+ ffmpeg on PATH)
python scripts/make_audio.py && python scripts/make_assets.py              # once: regenerates the brand pack
python build_episode.py \
  --slides ../series/34-outbox/slides \
  --script episodes/34-outbox.md \
  --out out/ep34-outbox.mp4 --thumb thumbnails/ep34.png --thumb-slide 9
```
Script format (`episodes/33-saga.md` is a full example):
```
---
title: Saga Pattern
series: Design Patterns · Episode 33
next: Next up · Episode 34 · Transactional Outbox
repo: github.com/waqarys/java-design-patterns
---
# Hook
[face] What happens when the payment goes through, but shipping fails? ...
# Problem
[03] Narration spoken over slide 03 ...  {key: Text for the gold KEY POINT callout}
# Code walkthrough
[face-pip 09] Narration over slide 09 with the facecam PiP placeholder ...
```
The output is an MP4 (1080p30, H.264 CRF 18, AAC, two-pass loudnorm to −14 LUFS) plus an `.srt`, a thumbnail and a
`.timeline.json` that lists every clip start.

**Two ways to use it:**
1. **Faceless or quick episode:** upload the output as is.
2. **Your voice and face (recommended):** use the output as a **timing and animation guide**. Record your own voice and camera
   in OBS (§3), then in CapCut lay your recording over it. Mute the TTS track, keep the SFX and music, and replace the
   facecam placeholders with your camera. Building with `--voice-db -60` gives you a version with no TTS
   (the SFX and music only, but still timed to your script).

## 3. OBS Studio setup (once, ~20 min)

### Import the scenes
1. Copy `video-kit/` to your PC. Run `python obs/repoint_obs.py --kit-dir "C:/path/to/video-kit"`.
   Add `--camera mac` on macOS so the camera, mic and display source types are swapped.
   This writes `BuildWithWaqar_SceneCollection.local.json` with absolute paths.
2. OBS → **Scene Collection → Import**, pick the `.local.json` file, then switch to the "Build with Waqar" collection.
3. If a file shows as missing, use OBS's **Missing Files** dialog ("Search Directory") and point it at `video-kit/assets`.
4. Open each camera source (`Facecam (PiP rounded)`, `Facecam (PiP circle)`, `Facecam (full)`) and pick your webcam.
   All three can use the same device. Pick your display or IDE window in the capture sources.

### Scenes
| # | Scene | Layout |
|---|---|---|
| 1 | Starting Soon / Intro | Looping intro + low music bed (+ hidden "Starting soon" text) |
| 2 | Slides + Facecam PiP | Slides full screen. The camera is cropped to 3:2 and masked to a rounded rectangle at the bottom right (1382,702, 476×316) under the gradient frame PNG. The lower third is hidden, so toggle it on. |
| 3 | Code Editor + Facecam | IDE full screen, 320 px circle camera at the bottom right with the circle frame |
| 4 | Full Facecam (talking head / hook) | Camera full frame, brand border + lower third. Use it for the hook and the outro talk. |
| 5 | Outro | Outro video |

The PiP sits in the **bottom-right 500×340 px**. Keep that corner of your slides and IDE empty: the slide footer page number
is fine, but don't put code there. In your IDE, use a 20–22 pt font and soft-wrap.

Optional transition: Scene Transitions → **+ Stinger** → `assets/stingers/stinger_swipe.mov`, transition point
≈ 330 ms. Then scene changes in OBS already get the branded wipe (the sound comes from the `.mov`'s matching WAV in CapCut).

### Recording settings (Settings → Output, Advanced mode)
- **Video:** base and output 1920×1080. **60 fps** for code and screen work (smooth scrolling), 30 fps is fine for slides only.
- **Recording format:** **MKV** (it survives crashes), then **File → Remux Recordings** to MP4 before CapCut.
- **Encoder:** NVENC/AMF/QuickSync/Apple VT hardware. **Rate control CQP, CQ 18–20** (or x264 CRF 18 at the "veryfast" preset).
- **Audio:** 48 kHz, 192 kbps AAC. Put the mic on its own track (Track 2) so you can process it separately in CapCut.
- **Mic filters** (already in the collection, in this order): Noise Suppression (RNNoise) → Noise Gate (−36/−46 dB) →
  Compressor (4:1, −20 dB, 6 ms/60 ms, +4 dB) → Limiter (−2 dB). Talk at normal volume and aim for peaks around −10 dBFS.

### Hotkey suggestions (Settings → Hotkeys)
| Key | Action |
|---|---|
| F1–F5 | Switch to scene 1–5 |
| F6 | Show/Hide **Lower third** in scenes 2/3 |
| F9 | Start/Stop recording |
| F10 | Pause recording |
| F11 | Add chapter marker (OBS 30.2+ with Hybrid MP4; skip if not available) |
| Ctrl+M | Mute/Unmute mic |

## 4. Camera tips (facecam is part of the brand)

- **Framing:** eyes on the **top third line** (the full-facecam placeholder shows the grid). Leave a hand's width of headroom.
  Frame from mid-chest up. For the PiP, frame tighter (shoulders up), because the circle crop cuts the sides.
- **Eye-line:** put the lens at eye height, or slightly above. Stack books if you need to. Read your script from a window placed **right under the
  camera** so your eyes stay near the lens. Look into the lens for the hook and the CTA.
- **Lighting:** one soft key light at 45° in front of you, a little above eye level, plus a weaker fill or a bounce on the other side.
  Add a coloured rim or background light in brand **purple/pink** (#5B21B6 / #FF90E8) to tie the room to the graphics. Avoid a window
  behind you. Lock the exposure and white balance in the camera properties so they don't drift.
- **Background:** use a real depth gap of about 1–2 m behind you. Software blur is optional: the OBS core has no blur filter, so use your camera
  app's or vendor's blur, a background-blur plugin, or CapCut's background tools later. Keep it subtle: heavy blur
  "halos" around your hair look cheap.
- **Wardrobe:** solid colours. Avoid fine stripes (they moiré) and pure white.
- **Audio > video:** a ₹2–5k USB or lavalier mic beats any webcam mic. Record 10 s of room silence at the start (it helps noise reduction).

## 5. CapCut (desktop) assembly, ~60–90 min

CapCut changes its menus between versions, so the steps below name the **feature** rather than exact menu paths.
If something has moved, search for it in CapCut's panels or settings.

### 5.1 Project skeleton, made once and reused (10 min, one time)
Create a project called `BWW – TEMPLATE` with this track layout (top to bottom):

```
V5  Text / KEY POINT callouts / lower third (PNG)
V4  Stingers (green MP4s, chroma keyed) ─ centred on each cut
V3  Facecam overlay + facecam_frame PNG (or the PiP from OBS already baked in)
V2  Section cards
V1  Main: hook clip │ intro │ OBS recording (cut) │ outro
A1  Your voice (from OBS track 2)
A2  SFX (whoosh/pop/ding/riser/impact WAVs)
A3  Music bed (loop, −20 dB under voice; use the ducking/auto-volume feature if your CapCut version has it)
```
Put in the intro at 0, the outro at the end, the six cards on V2 as placeholders, one stinger on V4, and the music on A3.
**Reuse:** CapCut doesn't reliably offer "save as template" on every desktop build, so **duplicate this draft** from the
projects/home screen (copy or duplicate the project) for each new episode. Then only V1, A1 and the text change.
Also keep the kit folder in CapCut's media library or favourites so the assets are one drag away.

### 5.2 Per episode (realistic times)
| Step | Time |
|---|---|
| Write and trim the script in `episodes/NN.md` (hook + section lines, 600–1200 words) | 15–20 min |
| Record in OBS: hook in Full Facecam, then slides with PiP, then the IDE (3 takes max per section) | 25–35 min |
| *(optional)* `build_episode.py` → timing guide, thumbnail, SRT | 5 min, unattended |
| CapCut: duplicate the template, import the remuxed MP4s | 3 min |
| Rough cut: remove mistakes and silences (use CapCut's silence/filler-word removal if present) | 10–15 min |
| Place the section cards (V2) at each topic change, with a stinger (V4) across every cut, then SFX (A2) | 10 min |
| Zoom emphasis on 3–6 key moments (below) + KEY POINT callouts | 10 min |
| Auto captions → fix the jargon (Java, HashMap, Saga, O(n)) → style | 5–10 min |
| Audio pass: voice ~−14 LUFS, music ducked, SFX not louder than the voice | 5 min |
| Export + thumbnail + upload with chapters | 10 min |
| **Total** | **≈ 1.5–2 h** (≈1 h once the template is muscle memory) |

### 5.3 Where each piece goes
- **Hook (0:00–0:15):** Full Facecam clip. Add punchy captions (2–3 words per line) and a riser leading into the intro.
- **Intro:** `intro_generic.mp4` at the hook's end. Add your title as a text layer at 3.6 s, in Montserrat ExtraBold white, with the series line
  in sky blue (#38BDF8) above it. Or use the per-episode intro from `build_episode.py`, which has the title baked in.
- **Section cards:** at each topic change. The swoosh and pop are already baked in.
- **Stingers:** put the green MP4 on V4 so its **middle frame (0.35 s) sits on the cut**. Apply **Chroma key** (pick the
  green), then raise the strength until the edges are clean. Use swipe for slide→slide, glitch into section cards and code, and light-leak for big moments
  (into the intro and outro). If you use the `.mov`, it already has transparency, but CapCut's alpha support for MOV varies, so test it once.
- **SFX:** whoosh on swipes, glitch on glitch stingers, **ding** on every KEY POINT, pop when text or callouts appear, riser into
  reveals, impact on the intro logo or a big "aha".
- **Lower third:** the PNG on V5 for 5–6 s, near the start of the first content section. Animate it with a slide-in from the left and an
  out animation.
- **Facecam PiP** (if you didn't bake it in OBS): put the camera clip on V3, scale and position it into the bottom-right box. Use a mask
  (rounded rectangle or circle), then put `facecam_frame_*.png` above it. **Background removal:** CapCut has an automatic
  background-removal / cutout feature for people. That gives you a "floating head" PiP or a cut-out for thumbnails.
  It works best with good light and a plain background. **Auto-reframe** is useful for turning the 16:9 episode into
  9:16 Shorts: it keeps your face centred.
- **Outro:** last 9.5 s. Leave the two right-hand boxes empty, because YouTube Studio's end screen puts 2 videos there
  (the elements must sit inside the last 5–20 s).

### 5.4 Zoom-in emphasis technique
The classic tech-YouTube "punch-in": on the main clip, add a **keyframe** just before the moment, then another 6–8 frames later
with scale **115–130%** and the position moved toward the line of code you're talking about. Hold it, then keyframe back to 100%
over about 10 frames. Add a soft whoosh or pop on the punch-in. For a hard "smash zoom", skip the ramp (cut straight to 130%). Don't use more than
one zoom every 20–30 s. Combine it with a KEY POINT callout for the single most important line in each section.

### 5.5 Auto captions
Use CapCut's **auto captions** (speech-to-text) on your voice track and choose English. Then fix the technical terms by hand.
Style: Inter or Montserrat Bold, 60–70 px, white with a dark box, lower centre, above the PiP's top edge. Or skip burned-in captions and upload
the `.srt` from this kit (or CapCut's caption export) to YouTube as closed captions. That's better for SEO and lets viewers
switch them off.

### 5.6 Export settings
- 1080p (or **4K upscale** for better YouTube bitrate if your machine copes), **30 fps** for slide episodes, **60 fps** if you
  recorded the IDE at 60.
- H.264 (or HEVC if your upload speed is low), bitrate "Recommended/High" (≈ 16–20 Mbps for 1080p30), AAC 320 kbps.
- If CapCut exposes audio normalisation, target about −14 LUFS. Otherwise keep voice peaks around −6 to −3 dBFS.

## 6. Thumbnail recipe (1280×720)
See `thumbnails/thumb-saga.png` and `thumbnails/thumb-two-sum.png` (from `gfx.thumbnail`).
1. Dark brand gradient background, with a tilted screenshot of the most visual slide (code or diagram) on the right.
2. **2–3 huge words** (Montserrat Black, 180+ px, white with a dark stroke): `SAGA`, `TWO SUM`.
3. One gold pill with the promise or transformation: `Rollback across services`, `O(n²) → O(n)`.
4. A red tag top-left (`JAVA 25`) and the WS logo top-right.
5. **Add your face** (CapCut background removal or any cutout tool) bottom-left/centre, overlapping the screenshot, with
   an expressive look (surprise, "aha"). Faces lift CTR more than anything else here.
6. Check it at 160×90 px: if you can't read it, cut words.

## 7. Hook checklist (first 30 s)
- [ ] The first sentence states a **pain or stake** ("You get charged but nothing ships"), not "Hi guys".
- [ ] Say the **payoff** in 1 line: what they'll be able to do or answer by the end.
- [ ] Show something visual in the first 5 s (the failing output, the O(n²) → O(n) diff).
- [ ] Name drop: "asked at Amazon/Google…" **only if true**. "The most common warm-up question" is fine.
- [ ] Keep it under 15 s before the intro. The intro itself is under 7 s.
- [ ] Use an open loop: "…and there's one mistake in the naive version almost everyone ships. Watch for it."
- [ ] Cut every pause over 0.3 s in the hook.

## 8. Per-episode checklist
- [ ] Script `episodes/NN-slug.md`: hook, 6 sections, 1 KEY POINT per section max, CTA in the recap.
- [ ] Slides checked: titles not clipped, the bottom-right PiP area is clear.
- [ ] OBS: correct scene collection, mic filters on, recording to MKV, then remux.
- [ ] Hook recorded in Full Facecam, with eyes on the lens.
- [ ] CapCut: template duplicated. Intro, cards, stingers and SFX are on every cut, the lower third is in the first 30 s, and the outro is last.
- [ ] Captions corrected, music ducked, loudness about −14 LUFS.
- [ ] Thumbnail made from the recipe. Title ≤ 60 characters. Chapters in the description (copy the section timestamps from `.timeline.json`).
- [ ] Description: repo link to the episode folder, the next-episode link, and an `.srt` uploaded.
- [ ] End screen: 2 videos + subscribe, in the outro slots.

## 9. Regenerating or customising
- Colours and fonts: `scripts/brand.py` (sampled from the slides: bg #0F172A, text #E2E8F0, accent #38BDF8, muted
  #94A3B8; and the logo: purple #5B21B6, pink #FF90E8).
- Audio: `scripts/make_audio.py` (change the bpm or chords in `music_bed()`).
- Graphics: `scripts/gfx.py`. OBS: `scripts/make_obs.py`. Then rerun `make_assets.py` and `make_obs.py`.
