# Reader Monad

**Season:** 7 · Advanced FP · **Category:** Functional · **Level:** Advanced · **Target runtime:** ~10–12 min · **Channel:** Build with Waqar · **Target runtime:** ~10–12 min · **Channel:** Build with Waqar · **Target runtime:** ~10–12 min · **Channel:** Build with Waqar · **Target runtime:** ~10–12 min · **Channel:** Build with Waqar

## Problem
**Load+merge config.yaml, secrets.env, flags.json via Env**

This episode is built around that concrete problem — not a shared StreamForge framing.
You will see a real naive implementation that hurts, a full pattern-based solution, a
runnable main with meaningful stdout, and JUnit failure-path coverage where the pattern lives.


**Reader nuance:** Reader is not a file-I/O API. It is a computation `Reader<Env, A>` that *depends on* an environment. Loading several files together is a great fit because every step needs the same Env (baseDir, charset, FileSystem, logger). Inject Env once at the edge with `.run(env)`.

## Hook
Welcome back to **Build with Waqar**. Today we earn **Reader** by solving one problem well:
*Load+merge config.yaml, secrets.env, flags.json via Env*. By the end you will have walked naive pain → pattern refactor → real JDK 25
stdout → trade-offs, with CapCut slides and a spoken script.

## Intent
Reader exists because this force kept showing up: load+merge config.yaml, secrets.env, flags.json via env. Name the force, sketch
the roles, and — critically — know when you would refuse the pattern.

## The Pain — Naive Design
Before the pattern, teams usually thread details everywhere, leak resources on partial
failure, or couple call sites to a third-party shape. That feels fast on day one and expensive
on day thirty.

```java
package com.buildwithwaqar.patterns.functional.reader.problem;

import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Naive: every helper takes fs/charset/baseDir explicitly — parameter drilling.
 * Easy to pass the wrong charset to one of three files.
 */
public final class NaiveMultiFileLoader {
    private final FileSystem fs;
    private final String baseDir;

    public NaiveMultiFileLoader(FileSystem fs, String baseDir) {
        this.fs = fs;
        this.baseDir = baseDir;
    }

    public ConfigBundle load() {
        var yaml = parseKv(read("config.yaml"));
        var secrets = parseKv(read("secrets.env"));
        var flags = parseKv(read("feature-flags.json"));
        return new ConfigBundle(yaml, secrets, flags);
    }

    private String read(String name) {
        // "accidentally" hard-codes UTF-8 and ignores baseDir semantics
        return fs.readString(name, StandardCharsets.UTF_8)
                .orElseThrow(() -> new IllegalStateException("missing " + baseDir + "/" + name));
    }

    static Map<String, String> parseKv(String raw) {
        Map<String, String> m = new LinkedHashMap<>();
        for (String line : raw.split("\n")) {
            line = line.trim();
            if (line.isEmpty() || line.startsWith("#") || line.startsWith("{") || line.startsWith("}")) continue;
            line = line.replace("\"", "").replace(",", "");
            int eq = line.indexOf('=');
            int colon = line.indexOf(':');
            int sep = eq >= 0 ? eq : colon;
            if (sep > 0) {
                m.put(line.substring(0, sep).trim(), line.substring(sep + 1).trim());
            }
        }
        return m;
    }
}

```

Call out the smell on camera: duplicated envelopes, missing compensations, parameter drilling,
illegal transitions, or null at the boundary.

## Pattern Structure
```mermaid
flowchart LR
  Client --> Pattern
  Pattern --> Collaborators
```

Roles matter more than class counts. The diagram slide draws the collaboration for **this**
problem so viewers can point at boxes while you type.

## Refactor to the Pattern

### Stage 1 — Name the boundary
Identify the client-facing API the rest of the app should depend on. Hide the incompatible
or distributed details behind it.

### Stage 2 — Encode the solution
```java
package com.buildwithwaqar.patterns.functional.reader.problem;

import com.buildwithwaqar.patterns.functional.reader.Reader;

import java.util.Map;

/**
 * Reader-based loader: Env (baseDir, charset, FileSystem, logger) is asked once
 * and threaded through composed reads — no parameter drilling.
 *
 * Nuance: Reader is not "a file reader API". It is a computation that depends on
 * an environment. Reading multiple files together is a great fit because every
 * step needs the same Env.
 */
public final class ReaderConfigLoader {
    private ReaderConfigLoader() {}

    public static Reader<Env, String> readFile(String relative) {
        return Reader.<Env>ask().flatMap(env -> Reader.pure(env.fs()
                .readString(relative, env.charset())
                .map(content -> {
                    env.info("read " + env.baseDir() + "/" + relative + " (" + content.length() + " bytes)");
                    return content;
                })
                .orElseThrow(() -> new IllegalStateException("missing " + env.baseDir() + "/" + relative))));
    }

    public static Reader<Env, ConfigBundle> loadBundle() {
        return readFile("config.yaml").flatMap(yamlRaw ->
                readFile("secrets.env").flatMap(secretsRaw ->
                        readFile("feature-flags.json").map(flagsRaw -> {
                            Map<String, String> yaml = NaiveMultiFileLoader.parseKv(yamlRaw);
                            Map<String, String> secrets = NaiveMultiFileLoader.parseKv(secretsRaw);
                            Map<String, String> flags = NaiveMultiFileLoader.parseKv(flagsRaw);
                            return new ConfigBundle(yaml, secrets, flags);
                        })));
    }
}

```

### Stage 3 — Runnable main
```java
package com.buildwithwaqar.patterns.functional.reader.problem;

/** Problem: load config.yaml + secrets.env + flags.json with Env injected once. */
public final class ProblemDemo {
    public static void main(String[] args) {
        System.out.println("=== Reader problem: multi-file config merge ===");
        var fs = new FileSystem.InMemory()
                .put("config.yaml", "app=demo\nport=8080\n")
                .put("secrets.env", "DB_PASS=s3cret\n")
                .put("feature-flags.json", "beta=true\n");

        System.out.println("--- naive (paths + charset threaded everywhere) ---");
        System.out.println(new NaiveMultiFileLoader(fs, "/app").load());

        System.out.println("--- reader (Env asked once at the edge) ---");
        var env = Env.inMemory(fs);
        ConfigBundle bundle = ReaderConfigLoader.loadBundle().run(env);
        System.out.println(bundle);
        System.out.println("log=" + env.log());
    }
}

```

## Hand-Traced Walkthrough (Verified on JDK 25)
Narrate each line. Do not invent numbers on camera.

```text
=== Reader problem: multi-file config merge ===
--- naive (paths + charset threaded everywhere) ---
ConfigBundle[yaml={app=demo, port=8080}, secrets={DB_PASS=s3cret}, flags={beta=true}]
--- reader (Env asked once at the edge) ---
ConfigBundle[yaml={app=demo, port=8080}, secrets={DB_PASS=s3cret}, flags={beta=true}]
log=[read /app/config/config.yaml (19 bytes), read /app/config/secrets.env (15 bytes), read /app/config/feature-flags.json (10 bytes)]
```

Trace: match each printed line to a stage above. Fork the repo, set `JAVA_HOME` to JDK 25,
and re-run the same main class to verify.

## Modern Java 25 / Zazr Variant
Prefer records for results, sealed types for closed variants, pattern matching for exhaustive
handling, virtual threads for blocking fan-out, and Zazr for Option/Either/Try/Validation
when the force is about absence or accumulated errors. Keep the classic shape when interview
literacy matters.

## Trade-offs
- **Clarity vs ceremony:** five types for a two-line problem is a fail.
- **Testability:** inject collaborators; for Saga, inject failure at each step.
- **Distributed reality:** teaching demos omit metrics, poison pills, and multi-region clocks.
- **Refuse the pattern** when a sealed switch, a function value, or a simpler library call
  already erases the force.

## When to Use / When Not to Use
**Use when** the problem above shows up more than once and a simpler language feature does
not erase it.

**Avoid when** you are pattern-collecting, when the boundary is still unclear, or when the
team cannot yet test the abstraction (especially compensations and half-open recovery).

## Related Patterns
Contrast Reader with its usual twin (Adapter vs Facade, Saga vs Outbox, Reader vs DI/Cake,
State vs Strategy, Free vs Tagless). Say the distinguishing sentence out loud.

## Interview Q&A
**Q1. What concrete problem does Reader solve here?**
A: Load+merge config.yaml, secrets.env, flags.json via Env.

**Q2. What does the naive design get wrong?**
A: Point at the naive code smells from this episode.

**Q3. How do you test failure paths?**
A: Inject a failing collaborator (Saga step, SOAP fault, missing file) and assert the
observable outcome — compensation order, FAILED result, or thrown IllegalStateException.

**Q4. When is Reader the wrong tool?**
A: Give a smaller counterexample where a function, Option, or facade is enough.

**Q5. How does Java 25 change the encoding?**
A: Records, sealed types, virtual threads, Zazr control types — pick what applies.

## Key Takeaways
- Lead with the problem: Load+merge config.yaml, secrets.env, flags.json via Env.
- Show naive pain before naming Reader.
- Run the Demo; trust JDK 25 stdout.
- Teach when NOT to use it.

## Video Script (≈10–12 minutes)
Record for **Build with Waqar**. OBS for capture, CapCut for assembly. Import `slides.pdf`.

**[0:00]** *(slide 01 title)*
Hey everyone, welcome back to Build with Waqar. Today we solve a real problem with Reader:
Load+merge config.yaml, secrets.env, flags.json via Env.

**[0:40]** *(slide 02–03 problem)*
I am not starting with a definition. Here is the product pain. Keep these names — they
reappear in the Demo output.

**[2:00]** *(naive code)*
IDE time. This is the naive version. Call out the smell.

**[3:30]** *(diagram)*
Structure for **this** problem. Point at each role.

**[4:30]** *(refactor stages)*
Stage by stage into the pattern. Highlight tests and failure injection.

**[7:00]** *(walkthrough output)*
Real stdout from JDK 25. Line by line. No fake numbers.

**[8:30]** *(modern + trade-offs)*
Java 25 / Zazr take, then slow down for when NOT to use Reader.

**[10:30]** *(interview + recap)*
Two interview questions, three takeaways, link the GitHub episode folder. I am Waqar —
see you in the next one.
