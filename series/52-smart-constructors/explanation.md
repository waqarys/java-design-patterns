# Smart Constructors

**Season:** 6 · FP Foundations · **Category:** Functional · **Level:** Intermediate · **Target runtime:** ~10–12 min · **Channel:** Build with Waqar · **Target runtime:** ~10–12 min · **Channel:** Build with Waqar · **Target runtime:** ~10–12 min · **Channel:** Build with Waqar

## Problem
**Money, Email, IBAN value objects with validated creation**

This episode is built around that concrete problem — not a shared StreamForge framing.
You will see a real naive implementation that hurts, a full pattern-based solution, a
runnable main with meaningful stdout, and JUnit failure-path coverage where the pattern lives.

## Hook
Welcome back to **Build with Waqar**. Today we earn **Smart Constructors** by solving one problem well:
*Money, Email, IBAN value objects with validated creation*. By the end you will have walked naive pain → pattern refactor → real JDK 25
stdout → trade-offs, with CapCut slides and a spoken script.

## Intent
Smart Constructors exists because this force kept showing up: money, email, iban value objects with validated creation. Name the force, sketch
the roles, and — critically — know when you would refuse the pattern.

## The Pain — Naive Design
Before the pattern, teams usually thread details everywhere, leak resources on partial
failure, or couple call sites to a third-party shape. That feels fast on day one and expensive
on day thirty.

```java
package com.buildwithwaqar.patterns.functional.smartctor.problem;

/** Naive: public constructors / raw strings — invalid money/email/IBAN compile fine. */
public final class NaiveValues {
    public record RawMoney(String currency, int cents) {}
    public record RawEmail(String value) {}
    public record RawIban(String value) {}
    public static RawMoney money(String c, int cents) { return new RawMoney(c, cents); } // accepts -1
    public static RawEmail email(String v) { return new RawEmail(v); } // accepts "not-an-email"
    public static RawIban iban(String v) { return new RawIban(v); }
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
package com.buildwithwaqar.patterns.functional.smartctor.problem;

import java.util.Optional;

/** Smart constructors: Money, Email, IBAN — invalid values cannot be constructed. */
public final class ValueObjects {
    private ValueObjects() {}

    public static final class Money {
        private final String currency;
        private final int cents;
        private Money(String currency, int cents) {
            this.currency = currency;
            this.cents = cents;
        }
        public String currency() { return currency; }
        public int cents() { return cents; }
        public static Optional<Money> of(String currency, int cents) {
            if (currency == null || currency.length() != 3) return Optional.empty();
            if (cents < 0) return Optional.empty();
            return Optional.of(new Money(currency.toUpperCase(), cents));
        }
        public String toString() { return currency + ":" + cents; }
    }

    public static final class Email {
        private final String value;
        private Email(String value) { this.value = value; }
        public String value() { return value; }
        public static Optional<Email> of(String raw) {
            if (raw == null) return Optional.empty();
            String s = raw.trim();
            int at = s.indexOf('@');
            if (at <= 0 || at != s.lastIndexOf('@') || at == s.length() - 1) return Optional.empty();
            if (s.contains(" ")) return Optional.empty();
            return Optional.of(new Email(s.toLowerCase()));
        }
        public String toString() { return value; }
    }

    public static final class Iban {
        private final String value;
        private Iban(String value) { this.value = value; }
        public String value() { return value; }
        public static Optional<Iban> of(String raw) {
            if (raw == null) return Optional.empty();
            String s = raw.replace(" ", "").toUpperCase();
            if (s.length() < 15 || s.length() > 34) return Optional.empty();
            if (!s.matches("[A-Z]{2}[0-9]{2}[A-Z0-9]+")) return Optional.empty();
            return Optional.of(new Iban(s));
        }
        public String toString() { return value; }
    }
}

```

### Stage 3 — Runnable main
```java
package com.buildwithwaqar.patterns.functional.smartctor.problem;

public final class ProblemDemo {
    public static void main(String[] args) {
        System.out.println("=== Smart Constructors: Money / Email / IBAN ===");
        System.out.println("--- naive allows illegal states ---");
        System.out.println(NaiveValues.money("US", -5));
        System.out.println(NaiveValues.email("not-an-email"));
        System.out.println(NaiveValues.iban("xx"));

        System.out.println("--- smart constructors ---");
        System.out.println("money=" + ValueObjects.Money.of("USD", 1999));
        System.out.println("badMoney=" + ValueObjects.Money.of("US", -1));
        System.out.println("email=" + ValueObjects.Email.of("A@B.C"));
        System.out.println("badEmail=" + ValueObjects.Email.of("nope"));
        System.out.println("iban=" + ValueObjects.Iban.of("DE89 3704 0044 0532 0130 00"));
        System.out.println("badIban=" + ValueObjects.Iban.of("12"));
    }
}

```

## Hand-Traced Walkthrough (Verified on JDK 25)
Narrate each line. Do not invent numbers on camera.

```text
=== Smart Constructors: Money / Email / IBAN ===
--- naive allows illegal states ---
RawMoney[currency=US, cents=-5]
RawEmail[value=not-an-email]
RawIban[value=xx]
--- smart constructors ---
money=Optional[USD:1999]
badMoney=Optional.empty
email=Optional[a@b.c]
badEmail=Optional.empty
iban=Optional[DE89370400440532013000]
badIban=Optional.empty
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
Contrast Smart Constructors with its usual twin (Adapter vs Facade, Saga vs Outbox, Reader vs DI/Cake,
State vs Strategy, Free vs Tagless). Say the distinguishing sentence out loud.

## Interview Q&A
**Q1. What concrete problem does Smart Constructors solve here?**
A: Money, Email, IBAN value objects with validated creation.

**Q2. What does the naive design get wrong?**
A: Point at the naive code smells from this episode.

**Q3. How do you test failure paths?**
A: Inject a failing collaborator (Saga step, SOAP fault, missing file) and assert the
observable outcome — compensation order, FAILED result, or thrown IllegalStateException.

**Q4. When is Smart Constructors the wrong tool?**
A: Give a smaller counterexample where a function, Option, or facade is enough.

**Q5. How does Java 25 change the encoding?**
A: Records, sealed types, virtual threads, Zazr control types — pick what applies.

## Key Takeaways
- Lead with the problem: Money, Email, IBAN value objects with validated creation.
- Show naive pain before naming Smart Constructors.
- Run the Demo; trust JDK 25 stdout.
- Teach when NOT to use it.

## Video Script (≈10–12 minutes)
Record for **Build with Waqar**. OBS for capture, CapCut for assembly. Import `slides.pdf`.

**[0:00]** *(slide 01 title)*
Hey everyone, welcome back to Build with Waqar. Today we solve a real problem with Smart Constructors:
Money, Email, IBAN value objects with validated creation.

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
Java 25 / Zazr take, then slow down for when NOT to use Smart Constructors.

**[10:30]** *(interview + recap)*
Two interview questions, three takeaways, link the GitHub episode folder. I am Waqar —
see you in the next one.
