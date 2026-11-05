# Facade

**Season:** 2 · Structural · **Category:** GoF · **Level:** Beginner

## Intent
Provide a simple interface to a noisy subsystem.

## Problem
Without this pattern, code tends toward: New things up with conditionals and duplicated construction.

## Real-world Analogy
Hotel concierge books taxi, dinner, tickets.

## When to Use
- See Intent.
- The design force named in Intent shows up more than once.

## When Not to Use
- When a simpler redesign removes the need.
- A simpler language feature (record, sealed switch, function value) already solves it.

## Naive Approach
```text
New things up with conditionals and duplicated construction.
```

## Pattern Structure
```mermaid
flowchart LR
  Client --> Pattern
  Pattern --> Collaborators
```
Client -> Pattern -> Collaborators

## Classic Implementation
Primary code lives under `src/main/java/com/buildwithwaqar/patterns/gof/structural/facade/`.

```java
package com.buildwithwaqar.patterns.gof.structural.facade;

final class Inventory { boolean reserve(String sku, int qty) { return qty > 0 && !sku.isBlank(); } }
final class PaymentGateway { boolean charge(String card, int cents) { return card != null && cents > 0; } }
final class Shipping { String ship(String sku) { return "TRACK-" + sku; } }

/** Simple interface over a noisy subsystem. */
public final class OrderFacade {
    private final Inventory inventory = new Inventory();
    private final PaymentGateway payments = new PaymentGateway();
    private final Shipping shipping = new Shipping();

    public String placeOrder(String sku, int qty, String card, int cents) {
        if (!inventory.reserve(sku, qty)) return "REJECTED:inventory";
        if (!payments.charge(card, cents)) return "REJECTED:payment";
        return "OK:" + shipping.ship(sku);
    }
}
```

## Modern Java 25 / Functional
Prefer sealed interfaces + records; virtual proxy uses synchronized lazy init.

## Worked Example
```text
See the JUnit test beside the implementation.
Run: mvn -Dtest=*Test test
Focus package matching: gof/structural/facade/
```

## Pitfalls
- Keep adapters thin.
- Decorators should preserve the interface.
- Facades must not become god objects.

## Interview Q&A
**Q: When is Facade the wrong choice?**
A: When you can change the adaptee/subject API or collapse the hierarchy.

**Q: How would you test this?**
A: Prefer injecting interfaces or interpreters; keep demos deterministic (fixed clocks, canned inputs).

## Key Takeaways
- Provide a simple interface to a noisy subsystem.
- Prefer Java 25 sealed types / records / virtual threads where they simplify the classic shape.
- Ship working tests with every episode.

## Video Script
**[0:00] Hook** — Open with the analogy: Hotel concierge books taxi, dinner, tickets.
**[0:40] Intent** — State the design force in one sentence: Provide a simple interface to a noisy subsystem.
**[1:30] Naive pain** — Show the messy approach: New things up with conditionals and duplicated construction.
**[3:00] Pattern** — Walk the code under `gof/structural/facade/`; draw the mermaid structure on-screen.
**[6:00] Modern take** — Prefer sealed interfaces + records; virtual proxy uses synchronized lazy init.
**[8:00] Pitfalls & interview** — Cover: When is Facade the wrong choice?
**[9:30] Outro** — Tease the next episode; point viewers to the GitHub repo and slides PDF.
