package com.buildwithwaqar.patterns.functional.reader.problem;

import java.util.Map;

/** Merged view of config.yaml + secrets.env + feature-flags.json (simplified). */
public record ConfigBundle(
        Map<String, String> yaml,
        Map<String, String> secrets,
        Map<String, String> flags
) {
    public String require(String key) {
        if (secrets.containsKey(key)) return secrets.get(key);
        if (yaml.containsKey(key)) return yaml.get(key);
        if (flags.containsKey(key)) return flags.get(key);
        throw new IllegalArgumentException("missing " + key);
    }
}
