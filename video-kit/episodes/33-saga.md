---
title: Saga Pattern
subtitle: Distributed transactions with rollback
series: Design Patterns · Episode 33
lower_name: Waqar Shaikh
lower_topic: Saga Pattern · Java 25
next: Next up · Episode 34 · Transactional Outbox
repo: github.com/waqarys/java-design-patterns
thumb_big: SAGA
thumb_small: Rollback across services
thumb_tag: JAVA 25
---
# Hook
[face] What happens when the payment goes through, but shipping fails? Your customer is charged, the stock is locked, and nothing ships. Today we fix that with the Saga pattern.

# Problem
[03] Here is the product problem. To book an order we call three services: reserve inventory, charge the payment, and create the shipment. Each one has its own database, so there is no single transaction that covers all three.
[05] The inputs are simple: an order id, a SKU, a quantity, the amount in cents, and an address. On success, the order is committed and shipped. On failure, we must refund the money and release the stock, in reverse order. {key: On failure, undo completed steps in reverse order}

# Naive approach
[06] This is the naive version most of us write first. Reserve, then charge, then ship, with an early return on every failure. Look at the comments. If the charge fails, inventory stays reserved. If shipping fails, the customer has already paid.
[07] So the naive flow leaks. There is no compensation registry, no way to inject a failure in a test, and no idempotency key, which means a retry can double charge the customer in production.

# Pattern
[04] Enter the Saga. An orchestrator calls each step in order, and every successful step registers a compensating action. Inventory reserve is undone by release. Charge is undone by refund. There is also a choreography twin, where services react to events on a bus instead of a central coordinator. {key: Every step registers its own compensation}

# Code walkthrough
[08] Here is the orchestrated saga. Three small interfaces, inventory, payments and shipping, each with a do and an undo method. A step result record tells us whether a call succeeded.
[face-pip 09] Inside book, every step runs once, keyed by order id and step name. That is our idempotency guard. After a step succeeds, we add its compensation to the front of the list, so when something throws, the compensations replay in reverse order automatically.
[12] And this is real output from JDK 25. In the happy path we see reserve, charge, ship, committed. When the carrier is down, the saga aborts at ship, refunds the payment, releases the stock, and ends in rolled back. No leaked money, no locked inventory. {key: abort → refund → release → rolled_back}

# Complexity / Trade-offs
[16] Now the trade-offs. Use a saga when a business transaction really spans services. Do not reach for it when one database transaction or a simple function would do. And remember, compensations are not a magic undo. They are new business actions, so they must be idempotent and well tested.

# Recap
[18] Quick recap. Start from the problem, feel the naive pain, then name the pattern. Register a compensation for every step, run them in reverse on failure, and inject failures in your tests. All the code and slides are on GitHub, linked below. I am Waqar, and I will see you in the next one.
