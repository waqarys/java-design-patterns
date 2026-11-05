# Java 25 Design Patterns

Idiomatic **Java 25** implementations of classic GoF, enterprise, concurrency, and **functional** design patterns — with **[Zazr](https://zazr.dev)** (`dev.zazr:zazr-core:0.4.0`) and **[VAVR](https://www.vavr.io)** (`io.vavr:vavr:1.1.0`).

Built as YouTube series content for **[Build with Waqar](https://www.youtube.com/@BuildWithWaqar)** (OBS + CapCut). Each episode under `series/` ships:

- `explanation.md` — intent, analogy, UML/mermaid, naive vs pattern, Java 25/FP refactor, pitfalls, interview Q&A, full video script
- `slides/*.png` + `slides.pdf` — dark 1080p slides ready to drop into CapCut
- Working Java under `src/` with JUnit 5 tests

## Requirements

- JDK **25** (Temurin 25.0.5+ tested)
- Maven 3.9+

```bash
export JAVA_HOME=/path/to/jdk-25
mvn test
```

## Layout

```
src/main/java/com/buildwithwaqar/patterns/
  gof/creational|structural|behavioral/   # all 23 GoF patterns
  enterprise/                             # Repository, DI, Circuit Breaker, Saga, ...
  concurrency/                            # Producer-Consumer, Virtual Threads, ...
  functional/                             # Zazr/VAVR + Tagless Final, Cake, Free, ...
series/                                   # YouTube episodes (beginner → advanced)
tools/                                    # slide renderer
```

## Zazr vs VAVR

Zazr is a **Vavr fork for Java 25+** (sealed interfaces, records, pattern matching). This repo uses Zazr as the primary FP library and keeps VAVR for side-by-side comparison (`functional/vavrcompare`).

## License

MIT © Waqar Shaikh

## YouTube series

See [`series/README.md`](series/README.md) for the full playlist (66 episodes across 8 seasons).

# Series Index — Build with Waqar

Suggested playlist order (beginner → advanced).


## Season 0 · Intro

- [00. Series Intro: Java 25 Design Patterns](00-series-intro/) — Beginner — *Showcase map of problems across seasons*
- [01. Java 25 Features for Patterns](01-java25-features/) — Beginner — *Model Payment ADT without illegal states*
- [02. Zazr and VAVR Tour](02-zazr-and-vavr/) — Beginner — *Replace null-config lookup with Option*

## Season 1 · Creational

- [03. Singleton](03-singleton/) — Beginner — *One MetricsRegistry across request threads*
- [04. Factory Method](04-factory-method/) — Beginner — *Create Email/SMS/Push notifiers without client coupling*
- [05. Abstract Factory](05-abstract-factory/) — Intermediate — *Light/Dark UI widget family stays consistent*
- [06. Builder](06-builder/) — Beginner — *Build immutable HTTP requests with many optionals*
- [07. Prototype](07-prototype/) — Beginner — *Clone weekly-meeting doc templates per team*

## Season 2 · Structural

- [08. Adapter](08-adapter/) — Beginner — *Wrap legacy XML SOAP payment gateway as PaymentProcessor*
- [09. Bridge](09-bridge/) — Beginner — *Draw shapes with Vector vs Raster renderers independently*
- [10. Composite](10-composite/) — Beginner — *Compute total size of nested project folders*
- [11. Decorator](11-decorator/) — Beginner — *Stack compression + encryption on a data store*
- [12. Facade](12-facade/) — Beginner — *One placeOrder call over inventory+payment+shipping*
- [13. Flyweight](13-flyweight/) — Intermediate — *Share glyph styles across a million characters*
- [14. Proxy](14-proxy/) — Beginner — *Lazy-load heavy image assets*

## Season 3 · Behavioral

- [15. Chain of Responsibility](15-chain-of-responsibility/) — Intermediate — *Route support tickets billing→tech→general*
- [16. Command](16-command/) — Intermediate — *Undoable editor keystrokes*
- [17. Interpreter](17-interpreter/) — Intermediate — *Evaluate arithmetic AST (2+3)*4*
- [18. Iterator](18-iterator/) — Intermediate — *Round-robin assign jobs to workers*
- [19. Mediator](19-mediator/) — Intermediate — *Chat room without user-to-user coupling*
- [20. Memento](20-memento/) — Intermediate — *Save/restore text editor snapshots*
- [21. Observer](21-observer/) — Intermediate — *Fan out video.published to audit+UI*
- [22. State](22-state/) — Intermediate — *Order lifecycle NEW→PAID→SHIPPED with illegal guards*
- [23. Strategy](23-strategy/) — Intermediate — *Swap cart pricing (percent vs flat vs half)*
- [24. Template Method](24-template-method/) — Intermediate — *Mine CSV/JSON reports with shared pipeline*
- [25. Visitor](25-visitor/) — Intermediate — *Pretty-print / evaluate expression AST*

## Season 4 · Enterprise

- [26. Repository](26-repository/) — Intermediate — *Persist users behind collection-like API*
- [27. Dependency Injection](27-dependency-injection/) — Intermediate — *Wire Greeter without hard `new` in controllers*
- [28. Null Object](28-null-object/) — Intermediate — *Safe logger when monitoring disabled*
- [29. Specification](29-specification/) — Intermediate — *Filter catalog: cheap AND in-stock*
- [30. Circuit Breaker](30-circuit-breaker/) — Intermediate — *Protect calls to flaky rates API*
- [31. Retry](31-retry/) — Intermediate — *Publish event despite transient broker errors*
- [32. Event Sourcing & CQRS](32-event-sourcing-cqrs/) — Intermediate — *Bank account ledger + balance read model*
- [33. Saga](33-saga/) — Intermediate — *Order booking: orchestration + event-bus choreography with compensations*
- [34. Transactional Outbox](34-outbox/) — Intermediate — *Atomically save order + outbox row, relay later*

## Season 5 · Concurrency

- [35. Producer-Consumer](35-producer-consumer/) — Intermediate — *Bounded queue between encoder and uploader*
- [36. Thread Pool](36-thread-pool/) — Intermediate — *Cap concurrent thumbnail jobs*
- [37. Read-Write Lock](37-read-write-lock/) — Intermediate — *Cache manifest with many readers*
- [38. Future & Promise](38-future-promise/) — Intermediate — *Async thumbnail then compose URL*
- [39. Active Object](39-active-object/) — Intermediate — *Serialize ledger mutations on one servant*
- [40. Virtual Threads](40-virtual-threads/) — Intermediate — *Fan-out 10k blocking HTTP checks*

## Season 6 · FP Foundations

- [41. Immutability & Persistent Collections](41-immutability-collections/) — Intermediate — *Immutable playlist edits sharing structure*
- [42. Option / Maybe](42-option/) — Intermediate — *Parse positive port without null*
- [43. Either & Railway-Oriented Programming](43-either-railway/) — Intermediate — *Validate user id pipeline without exceptions*
- [44. Try](44-try/) — Intermediate — *Parse config int with recovery*
- [45. Validation](45-validation/) — Intermediate — *Signup form accumulates all field errors*
- [46. Lazy & IO](46-lazy-io/) — Intermediate — *Defer expensive report until edge*
- [47. Functor, Applicative, Monad](47-functor-applicative-monad/) — Intermediate — *Combine async user+order fetches into a dashboard view*
- [48. Monoid & Semigroup](48-monoid-semigroup/) — Intermediate — *Fold invoice line totals / log strings*
- [49. Foldable & Traverse](49-foldable-traverse/) — Intermediate — *Fold scores; traverse validated IDs*
- [50. Currying, Partial Application & Composition](50-currying-composition/) — Intermediate — *Build price pipeline from small functions*
- [51. Memoization](51-memoization/) — Intermediate — *Cache coin-change DP / tax-rate lookups with call-count trace*
- [52. Smart Constructors](52-smart-constructors/) — Intermediate — *Money, Email, IBAN value objects with validated creation*
- [53. Algebraic Data Types with Sealed Records](53-adts-sealed/) — Intermediate — *Exhaustive Payment describe*

## Season 7 · Advanced FP

- [54. Reader Monad](54-reader/) — Advanced — *Load+merge config.yaml, secrets.env, flags.json via Env*
- [55. Writer Monad](55-writer/) — Advanced — *Compute totals while accumulating audit log*
- [56. State Monad](56-state/) — Advanced — *Pure RNG / bank balance simulation*
- [57. Kleisli Composition](57-kleisli/) — Advanced — *Compose parseInt → enrichUser → authorize*
- [58. Free Monad](58-free-monad/) — Advanced — *Bank-transfer DSL with Map interpreter + logging optimizer*
- [59. Tagless Final](59-tagless-final/) — Advanced — *User registration against UserRepo/Email/Logger algebras; IO + pure test interpreters*
- [60. Cake Pattern](60-cake-pattern/) — Advanced — *Multi-layer repo→service→controller app; test cake swaps fakes*
- [61. Type Classes via Dictionaries](61-type-classes/) — Advanced — *JSON encoder + Show/Eq for domain records and composites*
- [62. Lenses & Optics](62-lenses-optics/) — Advanced — *Update nested city in immutable Person/Address*
- [63. Trampolining & Stack Safety](63-trampolining/) — Advanced — *Sum 1..100000 without StackOverflowError*
- [64. Effect Systems & Interpreters](64-effect-interpreters/) — Advanced — *Read file → call service → write result; prod vs test interpreters*
- [65. VAVR vs Zazr Deep Dive](65-vavr-vs-zazr/) — Advanced — *Same Option describe in both libraries*

