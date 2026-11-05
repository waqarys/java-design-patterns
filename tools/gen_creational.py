#!/usr/bin/env python3
"""Generate GoF creational patterns: Singleton, Factory Method, Abstract Factory, Builder, Prototype."""
from pathlib import Path
from write_file import write, ROOT

PKG = "com.buildwithwaqar.patterns.gof.creational"
BASE = "src/main/java/" + PKG.replace(".", "/")
TBASE = "src/test/java/" + PKG.replace(".", "/")

def ep(num, slug, title, season, category, level, body_md, java_files, test_files):
    for rel, content in java_files.items():
        write(f"{BASE}/{rel}", content)
    for rel, content in test_files.items():
        write(f"{TBASE}/{rel}", content)
    write(f"series/{num:02d}-{slug}/explanation.md", f"""# {title}

**Season:** {season} · **Category:** {category} · **Level:** {level}

{body_md}
""")

# ---------- SINGTON ----------
ep(3, "singleton", "Singleton Pattern", "1 · Creational", "GoF Creational", "Beginner", """
## Intent
Ensure a class has exactly one instance and provide a global access point to it. In Java 25 we use an enum (Joshua Bloch) or a holder-class lazy singleton; avoid double-checked locking unless you need it for a reason.

## Problem
Configuration, logging, caches, and connection brokers often must be shared. Creating more than one instance wastes memory, duplicates state, and breaks identity equality assumptions.

## Real-world Analogy
A country has one official passport office. Citizens always go to that same office; opening a second "official" office would create conflicting records.

## When to Use
- Exactly one shared resource must coordinate access (logger, metrics registry, feature flags).
- Lazy, thread-safe initialization is required.
- Identity equality (`==`) of the instance matters.

## When Not to Use
- The object holds request-scoped or user-scoped state (use DI instead).
- You need multiple test doubles easily — singletons fight unit tests.
- Hidden global state makes reasoning about call order hard.

## Naive Approach
```java
public class Config {
  public static Config INSTANCE = new Config(); // eager, mutable, hard to test
}
```
Problems: eager cost, no lazy init, field is publicly assignable, subclasses can appear.

## Pattern Structure
```mermaid
classDiagram
  class Singleton {
    -instance
    +getInstance() Singleton
    +business() 
  }
  Client --> Singleton : getInstance()
```

## Classic Implementation
Enum singleton (preferred) and holder-class lazy singleton.

```java
package com.buildwithwaqar.patterns.gof.creational.singleton;

/** Preferred: enum singleton — serialization-safe, reflection-safe. */
public enum AppConfig {
    INSTANCE;

    private String env = "prod";

    public String env() { return env; }
    public void setEnv(String env) { this.env = env; }
    public String greeting() { return "hello from " + env; }
}
```

```java
package com.buildwithwaqar.patterns.gof.creational.singleton;

/** Lazy holder idiom — class loading provides thread-safe lazy init. */
public final class MetricsRegistry {
    private MetricsRegistry() {}

    private static final class Holder {
        static final MetricsRegistry INSTANCE = new MetricsRegistry();
    }

    public static MetricsRegistry getInstance() {
        return Holder.INSTANCE;
    }

    private long hits;

    public synchronized void hit() { hits++; }
    public synchronized long hits() { return hits; }
}
```

## Modern Java 25 / Functional
Prefer dependency injection over singletons. When you truly need one instance, inject an interface and bind it as a singleton in your container. For pure FP, pass an environment (Reader) instead of reaching for globals.

```java
public interface Clock { java.time.Instant now(); }

public record SystemClock() implements Clock {
    public java.time.Instant now() { return java.time.Instant.now(); }
}
// Wire once at the edge; pass Clock into pure functions.
```

## Worked Example
```text
AppConfig.INSTANCE.setEnv("staging");
AppConfig.INSTANCE.greeting()  ->  "hello from staging"
MetricsRegistry.getInstance().hit(); hit();
hits()  ->  2
same instance: getInstance() == getInstance()  ->  true
```

## Pitfalls
- Double-checked locking is easy to get wrong; prefer enum or holder.
- Mutable singleton state is shared mutable global state — synchronize or make it immutable.
- Singletons hide dependencies; tests become order-dependent.
- `clone()`, reflection, and deserialization can break non-enum singletons.

## Interview Q&A
**Q: Why is the enum singleton preferred?**
A: The JVM guarantees one instance, blocks reflective construction, and handles serialization.

**Q: Enum vs holder class?**
A: Enum if you can live with enum semantics; holder if you need a class hierarchy or lazy init with a non-enum type.

**Q: How do you test code that uses a singleton?**
A: Prefer injecting an interface. If stuck, reset state carefully in `@BeforeEach` or use a package-visible test seam.

## Key Takeaways
- One instance, global access — but prefer DI at application edges.
- Enum singleton is the gold standard in Java.
- Holder class for lazy non-enum singletons.
- Treat mutable singleton state as a concurrency hazard.

## Video Script
**[0:00] Hook** — "Ever opened two 'official' configs in one JVM and wondered why flags disagree? That's a missing Singleton."
**[0:30] Intent** — Define the pattern; show the passport-office analogy.
**[1:30] Naive** — Public static mutable field; list the bugs.
**[2:30] Enum** — Live-code `AppConfig`; call `INSTANCE` from two threads.
**[4:00] Holder** — Show lazy class-loading; contrast with DCL.
**[5:30] Modern** — Replace with injected `Clock`; mention Reader for FP.
**[7:00] Pitfalls & interview** — Serialization, testing, when NOT to use.
**[8:00] Outro** — Tease Factory Method next.
""", {
"singleton/AppConfig.java": """
package com.buildwithwaqar.patterns.gof.creational.singleton;

/** Preferred: enum singleton — serialization-safe, reflection-safe. */
public enum AppConfig {
    INSTANCE;

    private String env = "prod";

    public String env() {
        return env;
    }

    public void setEnv(String env) {
        this.env = env;
    }

    public String greeting() {
        return "hello from " + env;
    }
}
""",
"singleton/MetricsRegistry.java": """
package com.buildwithwaqar.patterns.gof.creational.singleton;

/** Lazy holder idiom — class loading provides thread-safe lazy init. */
public final class MetricsRegistry {
    private MetricsRegistry() {}

    private static final class Holder {
        static final MetricsRegistry INSTANCE = new MetricsRegistry();
    }

    public static MetricsRegistry getInstance() {
        return Holder.INSTANCE;
    }

    private long hits;

    public synchronized void hit() {
        hits++;
    }

    public synchronized long hits() {
        return hits;
    }

    /** Test seam — reset between tests. */
    synchronized void reset() {
        hits = 0;
    }
}
""",
"singleton/Clock.java": """
package com.buildwithwaqar.patterns.gof.creational.singleton;

import java.time.Instant;

/** Prefer injecting a Clock over a time singleton. */
public interface Clock {
    Instant now();

    record SystemClock() implements Clock {
        @Override
        public Instant now() {
            return Instant.now();
        }
    }

    record FixedClock(Instant instant) implements Clock {
        @Override
        public Instant now() {
            return instant;
        }
    }
}
""",
}, {
"singleton/SingletonTest.java": """
package com.buildwithwaqar.patterns.gof.creational.singleton;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.*;

class SingletonTest {

    @BeforeEach
    void reset() {
        AppConfig.INSTANCE.setEnv("prod");
        MetricsRegistry.getInstance().reset();
    }

    @Test
    void enumSingletonIsUniqueAndMutable() {
        assertSame(AppConfig.INSTANCE, AppConfig.INSTANCE);
        AppConfig.INSTANCE.setEnv("staging");
        assertEquals("hello from staging", AppConfig.INSTANCE.greeting());
    }

    @Test
    void holderIsLazyAndUniqueAcrossThreads() throws Exception {
        var a = new AtomicReference<MetricsRegistry>();
        var b = new AtomicReference<MetricsRegistry>();
        var start = new CountDownLatch(1);
        try (var pool = Executors.newVirtualThreadPerTaskExecutor()) {
            pool.submit(() -> { start.await(); a.set(MetricsRegistry.getInstance()); return null; });
            pool.submit(() -> { start.await(); b.set(MetricsRegistry.getInstance()); return null; });
            start.countDown();
            Thread.sleep(50);
        }
        assertSame(a.get(), b.get());
        a.get().hit();
        a.get().hit();
        assertEquals(2, b.get().hits());
    }

    @Test
    void injectedClockIsTestable() {
        Clock clock = new Clock.FixedClock(Instant.parse("2026-01-01T00:00:00Z"));
        assertEquals(Instant.parse("2026-01-01T00:00:00Z"), clock.now());
    }
}
""",
})

print("singleton done")
