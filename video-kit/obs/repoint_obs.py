#!/usr/bin/env python3
"""Rewrite VIDEOKIT_DIR placeholders in the OBS scene collection to an absolute path on your PC.
   python repoint_obs.py --kit-dir "C:/Users/waqar/java-design-patterns/video-kit"
   Optional: --camera mac|linux  (swap the Windows 'dshow_input' camera/'wasapi' mic ids)."""
import argparse, json, os
ap = argparse.ArgumentParser(); ap.add_argument("--kit-dir", required=True); ap.add_argument("--camera", choices=["windows", "mac", "linux"], default="windows")
a = ap.parse_args(); here = os.path.dirname(os.path.abspath(__file__))
txt = open(os.path.join(here, "BuildWithWaqar_SceneCollection.json"), encoding="utf-8").read()
txt = txt.replace("VIDEOKIT_DIR", a.kit_dir.replace("\\", "/").rstrip("/"))
ids = {"mac": ("av_capture_input_v2", "coreaudio_input_capture", "screen_capture"), "linux": ("v4l2_input", "pulse_input_capture", "xshm_input")}
if a.camera in ids:
    cam, mic, scr = ids[a.camera]
    txt = txt.replace('"dshow_input"', f'"{cam}"').replace('"wasapi_input_capture"', f'"{mic}"').replace('"monitor_capture"', f'"{scr}"')
out = os.path.join(here, "BuildWithWaqar_SceneCollection.local.json"); open(out, "w", encoding="utf-8").write(txt); print("Import this in OBS:", out)
