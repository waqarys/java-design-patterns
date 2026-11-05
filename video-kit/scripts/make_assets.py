"""Render the reusable brand pack (generic intro/outro, stingers, section cards, overlays) into assets/."""
import os, json
from brand import ASSETS
import gfx
A = lambda *p: os.path.join(ASSETS, *p)
CARDS = ["Problem", "Naive approach", "Pattern", "Code walkthrough", "Complexity / Trade-offs", "Recap"]
def main():
    v = A("intro", "_intro.mp4"); cues = gfx.intro(v, dur=6.5); gfx.mux(v, cues, A("intro", "intro_generic.mp4"), 6.5, music=True); os.remove(v)
    v = A("outro", "_outro.mp4"); cues = gfx.outro(v, "Next up · your next episode title", "github.com/waqarys/java-design-patterns")
    gfx.mux(v, cues, A("outro", "outro_generic.mp4"), 9.5, music=True); os.remove(v)
    for s in ["swipe", "glitch", "lightleak"]:
        mov = A("stingers", f"stinger_{s}.mov"); cues = gfx.stinger(mov, s)
        gfx.flatten_stinger(mov, A("stingers", f"stinger_{s}_green.mp4"), cues=cues)
        from mixer import mix_cues; mix_cues(cues, 0.9, A("stingers", f"stinger_{s}_sfx.wav"))
    for i, c in enumerate(CARDS, 1):
        slug = c.lower().replace(" / ", "-").replace(" ", "-"); v = A("cards", "_c.mp4"); cues = gfx.section_card(v, c, i)
        gfx.mux(v, cues, A("cards", f"card_{i:02d}_{slug}.mp4"), 2.2); os.remove(v)
    gfx.lower_third(A("overlays", "lower_third_generic.png"), "Waqar Shaikh", "Build with Waqar · Java & Algorithms")
    gfx.facecam_frame(A("overlays", "facecam_frame_rounded.png"), "rounded")
    gfx.facecam_frame(A("overlays", "facecam_frame_circle.png"), "circle")
    gfx.facecam_frame(A("overlays", "facecam_placeholder_rounded.png"), "rounded", placeholder=True)
    gfx.facecam_frame(A("overlays", "facecam_placeholder_circle.png"), "circle", placeholder=True)
    # code-editor scene frame: bigger PiP top-right-safe bottom-right
    print("assets done")
if __name__ == "__main__": main()
