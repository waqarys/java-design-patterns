#!/usr/bin/env python3
"""Compile (if needed) and run a Demo main; return stdout."""
import os, subprocess, textwrap
from pathlib import Path
ROOT = Path(__file__).resolve().parents[2]
JAVA_HOME = os.environ.get("JAVA_HOME", "/home/box/tools/jdk-25.0.5+8")
MVN = "/home/box/tools/apache-maven-3.9.9/bin/mvn"
env = {**os.environ, "JAVA_HOME": JAVA_HOME, "PATH": f"{JAVA_HOME}/bin:{os.environ.get('PATH','')}"}

def ensure_built():
    r = subprocess.run([MVN, "-q", "-DskipTests", "test-compile"], cwd=ROOT, env=env, capture_output=True, text=True)
    if r.returncode != 0:
        raise RuntimeError(r.stdout + r.stderr)

def run_main(fqcn: str) -> str:
    cp = f"{ROOT}/target/classes:{ROOT}/target/test-classes"
    # resolve deps from maven
    deps = subprocess.check_output(
        [MVN, "-q", "-DincludeScope=runtime", "dependency:build-classpath", "-Dmdep.outputFile=/dev/stdout"],
        cwd=ROOT, env=env, text=True).strip().splitlines()[-1]
    full = f"{ROOT}/target/classes:{deps}"
    r = subprocess.run([f"{JAVA_HOME}/bin/java", "-cp", full, fqcn], cwd=ROOT, env=env, capture_output=True, text=True, timeout=60)
    if r.returncode != 0:
        raise RuntimeError(f"{fqcn} failed:\n{r.stdout}\n{r.stderr}")
    return r.stdout.strip()

if __name__ == "__main__":
    import sys
    ensure_built()
    print(run_main(sys.argv[1]))
