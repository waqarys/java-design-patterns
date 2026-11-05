# Factory Method

**Season:** 1 · Creational · **Category:** GoF · **Level:** Beginner

## Intent
Defer instantiation to subclasses (or a switch on a sealed channel).

## Problem
Without this pattern, code tends toward: New things up with conditionals and duplicated construction.

## Real-world Analogy
A restaurant kitchen: the waiter asks for 'dessert'; the pastry chef decides which cake.

## When to Use
- Clients must not depend on concrete product classes.
- The design force named in Intent shows up more than once.

## When Not to Use
- When a simple constructor or enum switch already suffices.
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
Primary code lives under `src/main/java/com/buildwithwaqar/patterns/gof/creational/factorymethod/`.

```java
package com.buildwithwaqar.patterns.gof.creational.factorymethod;

/** Creator hierarchy — subclasses decide which product to instantiate. */
public abstract class Notifier {
    protected abstract Notification create();

    public final String notify(String to, String body) {
        return create().deliver(to, body);
    }
}

final class EmailNotifier extends Notifier {
    @Override protected Notification create() { return new EmailNotification(); }
}

final class SmsNotifier extends Notifier {
    @Override protected Notification create() { return new SmsNotification(); }
}

final class PushNotifier extends Notifier {
    @Override protected Notification create() { return new PushNotification(); }
}
```

## Modern Java 25 / Functional
NotificationChannel enum with switch replaces Notifier subclasses.

## Worked Example
```text
See the JUnit test beside the implementation.
Run: mvn -Dtest=*Test test
Focus package matching: gof/creational/factorymethod/
```

## Pitfalls
- Don't explode the hierarchy for one-off products.
- Modern Java: sealed enum + switch.

## Interview Q&A
**Q: Factory Method vs Abstract Factory?**
A: Factory Method: one product. Abstract Factory: families of related products.

**Q: How would you test this?**
A: Prefer injecting interfaces or interpreters; keep demos deterministic (fixed clocks, canned inputs).

## Key Takeaways
- Defer instantiation to subclasses (or a switch on a sealed channel).
- Prefer Java 25 sealed types / records / virtual threads where they simplify the classic shape.
- Ship working tests with every episode.

## Video Script
**[0:00] Hook** — Open with the analogy: A restaurant kitchen: the waiter asks for 'dessert'; the pastry chef decides which cake.
**[0:40] Intent** — State the design force in one sentence: Defer instantiation to subclasses (or a switch on a sealed channel).
**[1:30] Naive pain** — Show the messy approach: New things up with conditionals and duplicated construction.
**[3:00] Pattern** — Walk the code under `gof/creational/factorymethod/`; draw the mermaid structure on-screen.
**[6:00] Modern take** — NotificationChannel enum with switch replaces Notifier subclasses.
**[8:00] Pitfalls & interview** — Cover: Factory Method vs Abstract Factory?
**[9:30] Outro** — Tease the next episode; point viewers to the GitHub repo and slides PDF.
