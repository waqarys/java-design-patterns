# Adapter

**Season:** 2 · Structural · **Category:** GoF · **Level:** Beginner · **Target runtime:** ~10–12 min · **Channel:** Build with Waqar · **Target runtime:** ~10–12 min · **Channel:** Build with Waqar · **Target runtime:** ~10–12 min · **Channel:** Build with Waqar · **Target runtime:** ~10–12 min · **Channel:** Build with Waqar · **Target runtime:** ~10–12 min · **Channel:** Build with Waqar

## Problem
**Wrap legacy XML SOAP payment gateway as PaymentProcessor**

This episode is built around that concrete problem — not a shared StreamForge framing.
You will see a real naive implementation that hurts, a full pattern-based solution, a
runnable main with meaningful stdout, and JUnit failure-path coverage where the pattern lives.


**Adapter nuance:** the target interface (`PaymentProcessor`) belongs to *your* domain. The adaptee (`LegacySoapGateway`) stays untouched. The adapter owns XML envelopes, currency checks, and fault scraping so checkout never sees SOAP.

## Hook
Welcome back to **Build with Waqar**. Today we earn **Adapter** by solving one problem well:
*Wrap legacy XML SOAP payment gateway as PaymentProcessor*. By the end you will have walked naive pain → pattern refactor → real JDK 25
stdout → trade-offs, with CapCut slides and a spoken script.

## Intent
Adapter exists because this force kept showing up: wrap legacy xml soap payment gateway as paymentprocessor. Name the force, sketch
the roles, and — critically — know when you would refuse the pattern.

## The Pain — Naive Design
Before the pattern, teams usually thread details everywhere, leak resources on partial
failure, or couple call sites to a third-party shape. That feels fast on day one and expensive
on day thirty.

```java
package com.buildwithwaqar.patterns.gof.structural.adapter.problem;

/**
 * Naive: checkout knows SOAP XML and dollar conversion.
 * Every call site duplicates envelope formatting and response scraping.
 */
public final class NaiveCheckout {
    private final LegacySoapGateway gateway = new LegacySoapGateway();

    public String pay(String customerId, int amountCents) {
        double dollars = amountCents / 100.0;
        String xml = "<Envelope><Customer>" + customerId + "</Customer><Amount>"
                + dollars + "</Amount></Envelope>";
        String ack = gateway.submitXml(xml);
        if (ack.contains("<Fault>")) {
            return "FAILED:" + ack;
        }
        int ts = ack.indexOf("<Txn>") + 5;
        int te = ack.indexOf("</Txn>");
        return "OK:" + ack.substring(ts, te);
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
package com.buildwithwaqar.patterns.gof.structural.adapter.problem;

/** Adapter: translates PaymentProcessor calls into LegacySoapGateway XML. */
public final class SoapPaymentAdapter implements PaymentProcessor {
    private final LegacySoapGateway gateway;

    public SoapPaymentAdapter(LegacySoapGateway gateway) {
        this.gateway = gateway;
    }

    @Override
    public PaymentResult charge(String customerId, int amountCents, String currency) {
        if (!"USD".equalsIgnoreCase(currency)) {
            return new PaymentResult(false, null, "unsupported currency " + currency);
        }
        double dollars = amountCents / 100.0;
        String xml = "<Envelope><Customer>" + customerId
                + "</Customer><Amount>" + dollars + "</Amount><Currency>USD</Currency></Envelope>";
        String ack = gateway.submitXml(xml);
        if (ack.contains("<Fault>")) {
            return new PaymentResult(false, null, ack);
        }
        int ts = ack.indexOf("<Txn>") + 5;
        int te = ack.indexOf("</Txn>");
        return new PaymentResult(true, ack.substring(ts, te), "charged");
    }
}

```

### Stage 3 — Runnable main
```java
package com.buildwithwaqar.patterns.gof.structural.adapter.problem;

/** Problem: checkout must talk PaymentProcessor; legacy SOAP gateway speaks XML. */
public final class ProblemDemo {
    public static void main(String[] args) {
        System.out.println("=== Adapter problem: legacy SOAP payment gateway ===");
        System.out.println("--- naive (controller knows SOAP XML) ---");
        System.out.println(new NaiveCheckout().pay("alice", 1999));
        System.out.println("--- adapter (PaymentProcessor) ---");
        var checkout = new CheckoutService(new SoapPaymentAdapter(new LegacySoapGateway()));
        System.out.println(checkout.checkout("alice", 1999));
        System.out.println(checkout.checkout("alice", 0));
        System.out.println(new SoapPaymentAdapter(new LegacySoapGateway()).charge("a", 100, "INR"));
    }
}

```

## Hand-Traced Walkthrough (Verified on JDK 25)
Narrate each line. Do not invent numbers on camera.

```text
=== Adapter problem: legacy SOAP payment gateway ===
--- naive (controller knows SOAP XML) ---
OK:SOAP-alice-1998
--- adapter (PaymentProcessor) ---
OK:SOAP-alice-1998
FAILED:<Fault>non-positive</Fault>
PaymentResult[ok=false, transactionId=null, message=unsupported currency INR]
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
Contrast Adapter with its usual twin (Adapter vs Facade, Saga vs Outbox, Reader vs DI/Cake,
State vs Strategy, Free vs Tagless). Say the distinguishing sentence out loud.

## Interview Q&A
**Q1. What concrete problem does Adapter solve here?**
A: Wrap legacy XML SOAP payment gateway as PaymentProcessor.

**Q2. What does the naive design get wrong?**
A: Point at the naive code smells from this episode.

**Q3. How do you test failure paths?**
A: Inject a failing collaborator (Saga step, SOAP fault, missing file) and assert the
observable outcome — compensation order, FAILED result, or thrown IllegalStateException.

**Q4. When is Adapter the wrong tool?**
A: Give a smaller counterexample where a function, Option, or facade is enough.

**Q5. How does Java 25 change the encoding?**
A: Records, sealed types, virtual threads, Zazr control types — pick what applies.

## Key Takeaways
- Lead with the problem: Wrap legacy XML SOAP payment gateway as PaymentProcessor.
- Show naive pain before naming Adapter.
- Run the Demo; trust JDK 25 stdout.
- Teach when NOT to use it.

## Video Script (≈10–12 minutes)
Record for **Build with Waqar**. OBS for capture, CapCut for assembly. Import `slides.pdf`.

**[0:00]** *(slide 01 title)*
Hey everyone, welcome back to Build with Waqar. Today we solve a real problem with Adapter:
Wrap legacy XML SOAP payment gateway as PaymentProcessor.

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
Java 25 / Zazr take, then slow down for when NOT to use Adapter.

**[10:30]** *(interview + recap)*
Two interview questions, three takeaways, link the GitHub episode folder. I am Waqar —
see you in the next one.
