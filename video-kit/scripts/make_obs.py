"""Generate the OBS scene collection (obs/BuildWithWaqar_SceneCollection.json) plus masks/frames it uses.
Paths use the placeholder VIDEOKIT_DIR; run obs/repoint_obs.py --kit-dir <abs path> to rewrite them."""
import json, os, uuid
from PIL import Image, ImageDraw, ImageFilter
from brand import *
import gfx
KITDIR = KIT; OBS = os.path.join(KITDIR, "obs"); os.makedirs(OBS, exist_ok=True)
OV = os.path.join(ASSETS, "overlays")
P = lambda rel: "VIDEOKIT_DIR/" + rel
X0, Y0, X1, Y1 = gfx.PIP; PW, PH = X1 - X0 - 4, Y1 - Y0 - 4          # 476 x 316 rounded PiP
CS = 320; CX0, CY0 = X1 - CS, Y1 - CS                                  # circle PiP 320

def masks():
    # rounded mask in camera space (1920x1080): crop 150px each side -> 1620x1080 (3:2), radius scaled
    m = Image.new("RGBA", (1920, 1080), (0, 0, 0, 0)); ImageDraw.Draw(m).rounded_rectangle([150, 0, 1769, 1079], 100, fill=(255, 255, 255, 255))
    m.save(os.path.join(OV, "obs_mask_rounded_3x2.png"))
    m = Image.new("RGBA", (1920, 1080), (0, 0, 0, 0)); ImageDraw.Draw(m).ellipse([420, 0, 1499, 1079], fill=(255, 255, 255, 255))
    m.save(os.path.join(OV, "obs_mask_circle.png"))
    # full facecam brand frame (border + corner logo), transparent centre
    im = Image.new("RGBA", (W, H), (0, 0, 0, 0)); d = ImageDraw.Draw(im)
    d.rounded_rectangle([16, 16, W - 16, H - 16], 30, outline=SKY + (200,), width=5)
    im.alpha_composite(logo(110), (W - 150, 40)); im.save(os.path.join(OV, "fullcam_frame.png"))

def src(name, sid, settings, filters=None, **kw):
    s = {"name": name, "uuid": str(uuid.uuid4()), "id": sid, "versioned_id": sid, "settings": settings, "enabled": True,
         "volume": 1.0, "muted": False, "mixers": 255, "monitoring_type": 0, "flags": 0, "sync": 0, "filters": filters or [],
         "hotkeys": {}, "private_settings": {}}
    s.update(kw); return s

def item(source, iid, x=0, y=0, sx=1.0, sy=1.0, visible=True, crop=(0, 0, 0, 0), bounds=None, locked=False):
    it = {"name": source["name"], "source_uuid": source["uuid"], "visible": visible, "locked": locked, "rot": 0.0,
          "pos": {"x": x, "y": y}, "scale": {"x": sx, "y": sy}, "align": 5, "bounds_type": 0, "bounds_align": 0,
          "bounds": {"x": 0.0, "y": 0.0}, "crop_left": crop[0], "crop_top": crop[1], "crop_right": crop[2], "crop_bottom": crop[3],
          "id": iid, "group_item_backup": False, "scale_filter": "lanczos", "blend_method": "default", "blend_type": "normal", "show_transition": {"duration": 0}, "hide_transition": {"duration": 0}, "private_settings": {}}
    if bounds: it.update({"bounds_type": 2, "bounds": {"x": bounds[0], "y": bounds[1]}})
    return it

def scene(name, items):
    return src(name, "scene", {"id_counter": len(items), "custom_size": False, "items": items})

def main():
    masks()
    mask = lambda p: {"name": "PiP shape mask", "id": "mask_filter_v2", "versioned_id": "mask_filter_v2", "enabled": True,
                      "settings": {"type": "mask_alpha_filter.effect", "image_path": P(p)}}
    cam_pip = src("Facecam (PiP rounded)", "dshow_input", {"video_device_id": "", "resolution": "1920x1080", "res_type": 1},
                  [mask("assets/overlays/obs_mask_rounded_3x2.png")])
    cam_circle = src("Facecam (PiP circle)", "dshow_input", {"video_device_id": "", "resolution": "1920x1080", "res_type": 1},
                     [mask("assets/overlays/obs_mask_circle.png")])
    cam_full = src("Facecam (full)", "dshow_input", {"video_device_id": "", "resolution": "1920x1080", "res_type": 1})
    slides = src("Slides (window/display capture)", "monitor_capture", {"monitor": 0, "capture_cursor": True})
    ide = src("Code editor (window capture)", "window_capture", {"window": "", "method": 2, "cursor": True})
    img = lambda n, rel: src(n, "image_source", {"file": P(rel), "unload": False})
    frame_r = img("Facecam frame (rounded)", "assets/overlays/facecam_frame_rounded.png")
    frame_c = img("Facecam frame (circle)", "assets/overlays/facecam_frame_circle.png")
    frame_f = img("Full facecam frame", "assets/overlays/fullcam_frame.png")
    lower = img("Lower third", "assets/overlays/lower_third_generic.png")
    media = lambda n, rel, loop: src(n, "ffmpeg_source", {"local_file": P(rel), "is_local_file": True, "looping": loop, "restart_on_activate": True, "close_when_inactive": True})
    intro = media("Intro video", "assets/intro/intro_generic.mp4", True)
    outro = media("Outro video", "assets/outro/outro_generic.mp4", False)
    bed = media("Music bed (low)", "assets/audio/music_bed_loop.wav", True); bed["volume"] = 0.1
    starting = src("Starting soon text", "text_ft2_source_v2", {"text": "Starting soon…", "font": {"face": "Montserrat", "size": 72, "flags": 1, "style": "Bold"}, "color1": 4293980400, "color2": 4293980400})
    # sizes: rounded PiP: crop camera 150px L/R (1620x1080) scaled to 476 wide
    s_r = PW / 1620; s_c = CS / 1080
    scenes = [
        scene("1 · Starting Soon / Intro", [item(intro, 1), item(bed, 2), item(starting, 3, 120, 940, visible=False)]),
        scene("2 · Slides + Facecam PiP", [item(slides, 1, bounds=(1920, 1080)), item(cam_pip, 2, X0 + 2, Y0 + 2, s_r, s_r, crop=(150, 0, 150, 0)),
                                         item(frame_r, 3, locked=True), item(lower, 4, visible=False)]),
        scene("3 · Code Editor + Facecam", [item(ide, 1, bounds=(1920, 1080)), item(cam_circle, 2, CX0, CY0, s_c, s_c, crop=(420, 0, 420, 0)),
                                           item(frame_c, 3, locked=True), item(lower, 4, visible=False)]),
        scene("4 · Full Facecam (talking head / hook)", [item(cam_full, 1, bounds=(1920, 1080)), item(frame_f, 2, locked=True), item(lower, 3, visible=True)]),
        scene("5 · Outro", [item(outro, 1)]),
    ]
    inputs = [cam_pip, cam_circle, cam_full, slides, ide, frame_r, frame_c, frame_f, lower, intro, outro, bed, starting]
    mic_filters = [
        {"name": "1 Noise Suppression (RNNoise)", "id": "noise_suppress_filter_v2", "versioned_id": "noise_suppress_filter_v2", "enabled": True, "settings": {"method": "rnnoise"}},
        {"name": "2 Noise Gate", "id": "noise_gate_filter", "versioned_id": "noise_gate_filter", "enabled": True, "settings": {"open_threshold": -36.0, "close_threshold": -46.0}},
        {"name": "3 Compressor", "id": "compressor_filter", "versioned_id": "compressor_filter", "enabled": True, "settings": {"ratio": 4.0, "threshold": -20.0, "attack_time": 6, "release_time": 60, "output_gain": 4.0}},
        {"name": "4 Limiter", "id": "limiter_filter", "versioned_id": "limiter_filter", "enabled": True, "settings": {"threshold": -2.0, "release_time": 60}},
    ]
    mic = src("Mic/Aux", "wasapi_input_capture", {"device_id": "default"}, mic_filters)
    coll = {"name": "Build with Waqar", "current_scene": scenes[1]["name"], "current_program_scene": scenes[1]["name"],
            "scene_order": [{"name": s["name"]} for s in scenes], "sources": scenes + inputs, "groups": [],
            "AuxAudioDevice1": mic, "transitions": [], "current_transition": "Fade", "transition_duration": 300,
            "quick_transitions": [], "preview_locked": False, "scaling_enabled": False, "modules": {}}
    out = os.path.join(OBS, "BuildWithWaqar_SceneCollection.json"); json.dump(coll, open(out, "w"), indent=2)
    print(out)

if __name__ == "__main__": main()
