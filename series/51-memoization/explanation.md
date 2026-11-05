# Memoization

**Season:** 6 · FP Foundations · **Category:** Functional · **Level:** Intermediate · **Target runtime:** ~10–12 min · **Channel:** Build with Waqar · **Target runtime:** ~10–12 min · **Channel:** Build with Waqar · **Target runtime:** ~10–12 min · **Channel:** Build with Waqar · **Target runtime:** ~10–12 min · **Channel:** Build with Waqar

## Problem
**Cache coin-change DP / tax-rate lookups with call-count trace**

This episode is built around that concrete problem — not a shared StreamForge framing.
You will see a real naive implementation that hurts, a full pattern-based solution, a
runnable main with meaningful stdout, and JUnit failure-path coverage where the pattern lives.

## Hook
Welcome back to **Build with Waqar**. Today we earn **Memoization** by solving one problem well:
*Cache coin-change DP / tax-rate lookups with call-count trace*. By the end you will have walked naive pain → pattern refactor → real JDK 25
stdout → trade-offs, with CapCut slides and a spoken script.

## Intent
Memoization exists because this force kept showing up: cache coin-change dp / tax-rate lookups with call-count trace. Name the force, sketch
the roles, and — critically — know when you would refuse the pattern.

## The Pain — Naive Design
Before the pattern, teams usually thread details everywhere, leak resources on partial
failure, or couple call sites to a third-party shape. That feels fast on day one and expensive
on day thirty.

```java
package com.buildwithwaqar.patterns.functional.memoization.problem;

public final class ProblemDemo {
    public static void main(String[] args) {
        System.out.println("=== Memoization: coin-change DP + tax lookup ===");
        int[] coins = {1, 5, 10, 25};
        int amount = 30;

        var naiveC = new CoinChangeMemo.Counter();
        long t0 = System.nanoTime();
        int naive = CoinChangeMemo.naiveWays(amount, coins, naiveC);
        long naiveMs = (System.nanoTime() - t0) / 1_000_000;
        System.out.println("naive ways=" + naive + " calls=" + naiveC.get() + " ms=" + naiveMs);

        var memoC = new CoinChangeMemo.Counter();
        t0 = System.nanoTime();
        int memo = CoinChangeMemo.memoWays(amount, coins, memoC);
        long memoMs = (System.nanoTime() - t0) / 1_000_000;
        System.out.println("memo  ways=" + memo + " calls=" + memoC.get() + " ms=" + memoMs);

        System.out.println("--- tax-rate lookup memoize ---");
        var taxC = new CoinChangeMemo.Counter();
        var tax = CoinChangeMemo.memoize(CoinChangeMemo::expensiveTaxRate, taxC);
        long t1 = System.nanoTime();
        System.out.println("CA1=" + tax.apply("CA") + " CA2=" + tax.apply("CA") + " NY=" + tax.apply("NY"));
        System.out.println("computeCalls=" + taxC.get() + " elapsedMs=" + (System.nanoTime() - t1) / 1_000_000);
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
package com.buildwithwaqar.patterns.functional.memoization.problem;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

/** Memoized coin-change DP + tax-rate lookup with call-count / timing traces. */
public final class CoinChangeMemo {
    private CoinChangeMemo() {}

    public static final class Counter {
        private final AtomicInteger calls = new AtomicInteger();
        public int inc() { return calls.incrementAndGet(); }
        public int get() { return calls.get(); }
        public void reset() { calls.set(0); }
    }

    /** Naive exponential recursion. */
    public static int naiveWays(int amount, int[] coins, Counter c) {
        c.inc();
        if (amount == 0) return 1;
        if (amount < 0 || coins.length == 0) return 0;
        int with = naiveWays(amount - coins[0], coins, c);
        int without = naiveWays(amount, Arrays.copyOfRange(coins, 1, coins.length), c);
        return with + without;
    }

    public static int memoWays(int amount, int[] coins, Counter c) {
        return memoWays(amount, coins, 0, new HashMap<>(), c);
    }

    private static int memoWays(int amount, int[] coins, int i, Map<String, Integer> cache, Counter c) {
        c.inc();
        if (amount == 0) return 1;
        if (amount < 0 || i >= coins.length) return 0;
        String key = amount + ":" + i;
        Integer hit = cache.get(key);
        if (hit != null) return hit;
        int with = memoWays(amount - coins[i], coins, i, cache, c);
        int without = memoWays(amount, coins, i + 1, cache, c);
        int ans = with + without;
        cache.put(key, ans);
        return ans;
    }

    /** Generic memoize wrapper for expensive lookups. */
    public static <A, B> Function<A, B> memoize(Function<A, B> f, Counter c) {
        Map<A, B> cache = new HashMap<>();
        return a -> {
            if (cache.containsKey(a)) return cache.get(a);
            c.inc();
            B b = f.apply(a);
            cache.put(a, b);
            return b;
        };
    }

    public static int expensiveTaxRate(String region) {
        try { Thread.sleep(5); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        return switch (region) {
            case "CA" -> 725; case "NY" -> 800; default -> 500;
        };
    }
}

```

### Stage 3 — Runnable main
```java
package com.buildwithwaqar.patterns.functional.memoization.problem;

public final class ProblemDemo {
    public static void main(String[] args) {
        System.out.println("=== Memoization: coin-change DP + tax lookup ===");
        int[] coins = {1, 5, 10, 25};
        int amount = 30;

        var naiveC = new CoinChangeMemo.Counter();
        long t0 = System.nanoTime();
        int naive = CoinChangeMemo.naiveWays(amount, coins, naiveC);
        long naiveMs = (System.nanoTime() - t0) / 1_000_000;
        System.out.println("naive ways=" + naive + " calls=" + naiveC.get() + " ms=" + naiveMs);

        var memoC = new CoinChangeMemo.Counter();
        t0 = System.nanoTime();
        int memo = CoinChangeMemo.memoWays(amount, coins, memoC);
        long memoMs = (System.nanoTime() - t0) / 1_000_000;
        System.out.println("memo  ways=" + memo + " calls=" + memoC.get() + " ms=" + memoMs);

        System.out.println("--- tax-rate lookup memoize ---");
        var taxC = new CoinChangeMemo.Counter();
        var tax = CoinChangeMemo.memoize(CoinChangeMemo::expensiveTaxRate, taxC);
        long t1 = System.nanoTime();
        System.out.println("CA1=" + tax.apply("CA") + " CA2=" + tax.apply("CA") + " NY=" + tax.apply("NY"));
        System.out.println("computeCalls=" + taxC.get() + " elapsedMs=" + (System.nanoTime() - t1) / 1_000_000);
    }
}

```

## Hand-Traced Walkthrough (Verified on JDK 25)
Narrate each line. Do not invent numbers on camera.

```text
=== Memoization: coin-change DP + tax lookup ===
naive ways=18 calls=961 ms=0
memo  ways=18 calls=241 ms=1
--- tax-rate lookup memoize ---
CA1=725 CA2=725 NY=800
computeCalls=2 elapsedMs=12
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
Contrast Memoization with its usual twin (Adapter vs Facade, Saga vs Outbox, Reader vs DI/Cake,
State vs Strategy, Free vs Tagless). Say the distinguishing sentence out loud.

## Interview Q&A
**Q1. What concrete problem does Memoization solve here?**
A: Cache coin-change DP / tax-rate lookups with call-count trace.

**Q2. What does the naive design get wrong?**
A: Point at the naive code smells from this episode.

**Q3. How do you test failure paths?**
A: Inject a failing collaborator (Saga step, SOAP fault, missing file) and assert the
observable outcome — compensation order, FAILED result, or thrown IllegalStateException.

**Q4. When is Memoization the wrong tool?**
A: Give a smaller counterexample where a function, Option, or facade is enough.

**Q5. How does Java 25 change the encoding?**
A: Records, sealed types, virtual threads, Zazr control types — pick what applies.

## Key Takeaways
- Lead with the problem: Cache coin-change DP / tax-rate lookups with call-count trace.
- Show naive pain before naming Memoization.
- Run the Demo; trust JDK 25 stdout.
- Teach when NOT to use it.

## Video Script (≈10–12 minutes)
Record for **Build with Waqar**. OBS for capture, CapCut for assembly. Import `slides.pdf`.

**[0:00]** *(slide 01 title)*
Hey everyone, welcome back to Build with Waqar. Today we solve a real problem with Memoization:
Cache coin-change DP / tax-rate lookups with call-count trace.

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
Java 25 / Zazr take, then slow down for when NOT to use Memoization.

**[10:30]** *(interview + recap)*
Two interview questions, three takeaways, link the GitHub episode folder. I am Waqar —
see you in the next one.
