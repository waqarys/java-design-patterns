package com.buildwithwaqar.patterns.functional.reader.problem;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/** Shared environment threaded by the Reader monad. */
public final class Env {
    private final String baseDir;
    private final Charset charset;
    private final FileSystem fs;
    private final List<String> log = new ArrayList<>();

    public Env(String baseDir, Charset charset, FileSystem fs) {
        this.baseDir = baseDir;
        this.charset = charset;
        this.fs = fs;
    }

    public static Env inMemory(FileSystem.InMemory fs) {
        return new Env("/app/config", StandardCharsets.UTF_8, fs);
    }

    public String baseDir() { return baseDir; }
    public Charset charset() { return charset; }
    public FileSystem fs() { return fs; }
    public void info(String msg) { log.add(msg); }
    public List<String> log() { return List.copyOf(log); }
}
