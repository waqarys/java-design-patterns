# Per-episode concrete problems

| Ep | Pattern | Problem |
|----|---------|---------|
| 00 | Series Intro | Showcase map of problems across seasons |
| 01 | Java 25 Features | Model Payment ADT without illegal states |
| 02 | Zazr & VAVR | Replace null-config lookup with Option |
| 03 | Singleton | One MetricsRegistry across request threads |
| 04 | Factory Method | Create Email/SMS/Push notifiers without client coupling |
| 05 | Abstract Factory | Light/Dark UI widget family stays consistent |
| 06 | Builder | Build immutable HTTP requests with many optionals |
| 07 | Prototype | Clone weekly-meeting doc templates per team |
| 08 | Adapter | Wrap legacy XML SOAP payment gateway as PaymentProcessor |
| 09 | Bridge | Draw shapes with Vector vs Raster renderers independently |
| 10 | Composite | Compute total size of nested project folders |
| 11 | Decorator | Stack compression + encryption on a data store |
| 12 | Facade | One placeOrder call over inventory+payment+shipping |
| 13 | Flyweight | Share glyph styles across a million characters |
| 14 | Proxy | Lazy-load heavy image assets |
| 15 | Chain | Route support tickets billing→tech→general |
| 16 | Command | Undoable editor keystrokes |
| 17 | Interpreter | Evaluate arithmetic AST (2+3)*4 |
| 18 | Iterator | Round-robin assign jobs to workers |
| 19 | Mediator | Chat room without user-to-user coupling |
| 20 | Memento | Save/restore text editor snapshots |
| 21 | Observer | Fan out video.published to audit+UI |
| 22 | State | Order lifecycle NEW→PAID→SHIPPED with illegal guards |
| 23 | Strategy | Swap cart pricing (percent vs flat vs half) |
| 24 | Template Method | Mine CSV/JSON reports with shared pipeline |
| 25 | Visitor | Pretty-print / evaluate expression AST |
| 26 | Repository | Persist users behind collection-like API |
| 27 | DI | Wire Greeter without hard `new` in controllers |
| 28 | Null Object | Safe logger when monitoring disabled |
| 29 | Specification | Filter catalog: cheap AND in-stock |
| 30 | Circuit Breaker | Protect calls to flaky rates API |
| 31 | Retry | Publish event despite transient broker errors |
| 32 | Event Sourcing/CQRS | Bank account ledger + balance read model |
| 33 | Saga | Order: reserve→charge→ship with reverse compensations |
| 34 | Outbox | Atomically save order + outbox row, relay later |
| 35 | Producer-Consumer | Bounded queue between encoder and uploader |
| 36 | Thread Pool | Cap concurrent thumbnail jobs |
| 37 | Read-Write Lock | Cache manifest with many readers |
| 38 | Future/Promise | Async thumbnail then compose URL |
| 39 | Active Object | Serialize ledger mutations on one servant |
| 40 | Virtual Threads | Fan-out 10k blocking HTTP checks |
| 41 | Persistent Collections | Immutable playlist edits sharing structure |
| 42 | Option | Parse positive port without null |
| 43 | Either/Railway | Validate user id pipeline without exceptions |
| 44 | Try | Parse config int with recovery |
| 45 | Validation | Signup form accumulates all field errors |
| 46 | Lazy/IO | Defer expensive report until edge |
| 47 | Functor/Applicative/Monad | Map/flatMap Option via type-class witness |
| 48 | Monoid | Fold invoice line totals / log strings |
| 49 | Foldable/Traverse | Fold scores; traverse validated IDs |
| 50 | Currying/Composition | Build price pipeline from small functions |
| 51 | Memoization | Cache fib/square for hot keys |
| 52 | Smart Constructors | Email that cannot hold invalid strings |
| 53 | ADTs Sealed | Exhaustive Payment describe |
| 54 | Reader | Load+merge config.yaml, secrets.env, flags.json via Env |
| 55 | Writer | Compute totals while accumulating audit log |
| 56 | State | Pure RNG / bank balance simulation |
| 57 | Kleisli | Compose parseInt → enrichUser → authorize |
| 58 | Free | KV-store program interpreted by Map vs logging |
| 59 | Tagless Final | Console greet with Option test interpreter |
| 60 | Cake | Wire UserRepo+Email into UserService app |
| 61 | Type Classes | Explicit Monad dictionary for Option |
| 62 | Lenses | Update nested city in immutable Person/Address |
| 63 | Trampoline | Sum 1..100000 without StackOverflowError |
| 64 | Effect Interpreters | Describe IO report, run at edge |
| 65 | VAVR vs Zazr | Same Option describe in both libraries |
