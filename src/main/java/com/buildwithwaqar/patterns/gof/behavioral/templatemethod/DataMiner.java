package com.buildwithwaqar.patterns.gof.behavioral.templatemethod;

import java.util.List;

public abstract class DataMiner {
    /** Template method — fixed algorithm skeleton. */
    public final String mine(String path) {
        var raw = open(path);
        var data = parse(raw);
        var report = analyze(data);
        close();
        return report;
    }

    protected abstract String open(String path);
    protected abstract List<String> parse(String raw);
    protected String analyze(List<String> data) { return "rows=" + data.size(); }
    protected void close() {}
}

final class CsvMiner extends DataMiner {
    protected String open(String path) { return "a,b\nc,d"; }
    protected List<String> parse(String raw) { return List.of(raw.split("\\n")); }
}
