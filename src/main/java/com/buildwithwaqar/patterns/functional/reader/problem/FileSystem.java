package com.buildwithwaqar.patterns.functional.reader.problem;

import java.nio.charset.Charset;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

/** Abstraction so tests use an in-memory FS while prod uses disk. */
public interface FileSystem {
    Optional<String> readString(String relativePath, Charset charset);

    final class InMemory implements FileSystem {
        private final Map<String, String> files = new LinkedHashMap<>();

        public InMemory put(String path, String content) {
            files.put(path, content);
            return this;
        }

        @Override
        public Optional<String> readString(String relativePath, Charset charset) {
            return Optional.ofNullable(files.get(relativePath));
        }
    }
}
