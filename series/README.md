# Series Index — Build with Waqar

YouTube curriculum for **Build with Waqar** (OBS + CapCut).

Each episode folder contains:

- `explanation.md` — ~1.7–1.9k words: scenario, naive pain, staged refactor, verified Demo stdout, modern Java 25/Zazr, trade-offs, 7 interview Q&As, ~10–12 min spoken script

- `slides/*.png` + `slides.pdf` — 15–17 dark 1080p slides (diagram + code stages + walkthrough output)

- `run-output.txt` — real stdout from `Demo.main` on Temurin JDK 25


Runnable Java lives under `src/main/java/...` with JUnit 5 tests (`mvn test`).


## Seasons


### Season 0 · Intro

- [00-series-intro](00-series-intro/) · **933 words** · **15 slides** — **Series Intro: Java 25 Design Patterns** · ~9 min · **1792 words** · **15 slides**
- [01-java25-features](01-java25-features/) · **1053 words** · **15 slides** — **Java 25 Features for Patterns** · ~9 min · **1785 words** · **15 slides**
- [02-zazr-and-vavr](02-zazr-and-vavr/) · **1051 words** · **18 slides** — **Zazr and VAVR Tour** · ~10 min · **1846 words** · **15 slides**

### Season 1 · Creational (GoF)

- [03-singleton](03-singleton/) · **1130 words** · **15 slides** — **Singleton** · ~10 min · **1802 words** · **16 slides**
- [04-factory-method](04-factory-method/) · **1131 words** · **15 slides** — **Factory Method** · ~10 min · **1802 words** · **15 slides**
- [05-abstract-factory](05-abstract-factory/) · **1195 words** · **15 slides** — **Abstract Factory** · ~10 min · **1900 words** · **17 slides**
- [06-builder](06-builder/) · **1177 words** · **15 slides** — **Builder** · ~10 min · **1842 words** · **17 slides**
- [07-prototype](07-prototype/) · **1152 words** · **15 slides** — **Prototype** · ~10 min · **1911 words** · **16 slides**

### Season 2 · Structural (GoF)

- [08-adapter](08-adapter/) · **1266 words** · **16 slides** — **Adapter** · ~9 min · **1780 words** · **15 slides**
- [09-bridge](09-bridge/) · **1187 words** · **15 slides** — **Bridge** · ~10 min · **1850 words** · **16 slides**
- [10-composite](10-composite/) · **1141 words** · **15 slides** — **Composite** · ~10 min · **1800 words** · **16 slides**
- [11-decorator](11-decorator/) · **1224 words** · **15 slides** — **Decorator** · ~10 min · **1894 words** · **17 slides**
- [12-facade](12-facade/) · **1108 words** · **15 slides** — **Facade** · ~10 min · **1835 words** · **15 slides**
- [13-flyweight](13-flyweight/) · **1170 words** · **15 slides** — **Flyweight** · ~10 min · **1846 words** · **16 slides**
- [14-proxy](14-proxy/) · **1133 words** · **15 slides** — **Proxy** · ~10 min · **1809 words** · **16 slides**

### Season 3 · Behavioral (GoF)

- [15-chain-of-responsibility](15-chain-of-responsibility/) · **1136 words** · **15 slides** — **Chain of Responsibility** · ~10 min · **1892 words** · **16 slides**
- [16-command](16-command/) · **1148 words** · **15 slides** — **Command** · ~10 min · **1842 words** · **16 slides**
- [17-interpreter](17-interpreter/) · **1267 words** · **18 slides** — **Interpreter** · ~9 min · **1793 words** · **16 slides**
- [18-iterator](18-iterator/) · **1123 words** · **15 slides** — **Iterator** · ~10 min · **1810 words** · **16 slides**
- [19-mediator](19-mediator/) · **1062 words** · **15 slides** — **Mediator** · ~10 min · **1894 words** · **16 slides**
- [20-memento](20-memento/) · **1135 words** · **15 slides** — **Memento** · ~10 min · **1825 words** · **16 slides**
- [21-observer](21-observer/) · **1110 words** · **15 slides** — **Observer** · ~9 min · **1795 words** · **15 slides**
- [22-state](22-state/) · **1100 words** · **15 slides** — **State** · ~10 min · **1874 words** · **16 slides**
- [23-strategy](23-strategy/) · **1187 words** · **15 slides** — **Strategy** · ~10 min · **1816 words** · **16 slides**
- [24-template-method](24-template-method/) · **1162 words** · **15 slides** — **Template Method** · ~10 min · **1822 words** · **15 slides**
- [25-visitor](25-visitor/) · **1206 words** · **15 slides** — **Visitor** · ~10 min · **1865 words** · **16 slides**

### Season 4 · Enterprise

- [26-repository](26-repository/) · **1061 words** · **15 slides** — **Repository** · ~10 min · **1800 words** · **15 slides**
- [27-dependency-injection](27-dependency-injection/) · **1159 words** · **15 slides** — **Dependency Injection** · ~9 min · **1708 words** · **15 slides**
- [28-null-object](28-null-object/) · **1175 words** · **15 slides** — **Null Object** · ~10 min · **1848 words** · **16 slides**
- [29-specification](29-specification/) · **1127 words** · **15 slides** — **Specification** · ~10 min · **1842 words** · **16 slides**
- [30-circuit-breaker](30-circuit-breaker/) · **1180 words** · **16 slides** — **Circuit Breaker** · ~9 min · **1729 words** · **17 slides**
- [31-retry](31-retry/) · **1076 words** · **15 slides** — **Retry** · ~10 min · **1829 words** · **16 slides**
- [32-event-sourcing-cqrs](32-event-sourcing-cqrs/) · **1147 words** · **15 slides** — **Event Sourcing & CQRS** · ~9 min · **1723 words** · **17 slides**
- [33-saga](33-saga/) · **1635 words** · **18 slides** — **Saga** · ~10 min · **1837 words** · **16 slides**
- [34-outbox](34-outbox/) · **1159 words** · **15 slides** — **Transactional Outbox** · ~10 min · **1854 words** · **16 slides**

### Season 5 · Concurrency

- [35-producer-consumer](35-producer-consumer/) · **1140 words** · **15 slides** — **Producer-Consumer** · ~10 min · **1854 words** · **16 slides**
- [36-thread-pool](36-thread-pool/) · **1124 words** · **15 slides** — **Thread Pool** · ~10 min · **1862 words** · **16 slides**
- [37-read-write-lock](37-read-write-lock/) · **1132 words** · **15 slides** — **Read-Write Lock** · ~10 min · **1833 words** · **16 slides**
- [38-future-promise](38-future-promise/) · **1021 words** · **15 slides** — **Future & Promise** · ~9 min · **1792 words** · **15 slides**
- [39-active-object](39-active-object/) · **1123 words** · **15 slides** — **Active Object** · ~10 min · **1827 words** · **16 slides**
- [40-virtual-threads](40-virtual-threads/) · **1096 words** · **15 slides** — **Virtual Threads** · ~9 min · **1789 words** · **15 slides**

### Season 6 · FP Foundations

- [41-immutability-collections](41-immutability-collections/) · **1110 words** · **15 slides** — **Immutability & Persistent Collections** · ~10 min · **1844 words** · **16 slides**
- [42-option](42-option/) · **1030 words** · **15 slides** — **Option / Maybe** · ~10 min · **1861 words** · **16 slides**
- [43-either-railway](43-either-railway/) · **1200 words** · **15 slides** — **Either & Railway-Oriented Programming** · ~10 min · **1875 words** · **16 slides**
- [44-try](44-try/) · **1137 words** · **15 slides** — **Try** · ~9 min · **1748 words** · **15 slides**
- [45-validation](45-validation/) · **1166 words** · **16 slides** — **Validation** · ~10 min · **1817 words** · **16 slides**
- [46-lazy-io](46-lazy-io/) · **1145 words** · **15 slides** — **Lazy & IO** · ~10 min · **1800 words** · **15 slides**
- [47-functor-applicative-monad](47-functor-applicative-monad/) · **1363 words** · **19 slides** — **Functor, Applicative, Monad** · ~9 min · **1753 words** · **15 slides**
- [48-monoid-semigroup](48-monoid-semigroup/) · **1148 words** · **15 slides** — **Monoid & Semigroup** · ~10 min · **1818 words** · **15 slides**
- [49-foldable-traverse](49-foldable-traverse/) · **1033 words** · **15 slides** — **Foldable & Traverse** · ~10 min · **1879 words** · **16 slides**
- [50-currying-composition](50-currying-composition/) · **1115 words** · **15 slides** — **Currying, Partial Application & Composition** · ~9 min · **1762 words** · **15 slides**
- [51-memoization](51-memoization/) · **1516 words** · **19 slides** — **Memoization** · ~10 min · **1829 words** · **15 slides**
- [52-smart-constructors](52-smart-constructors/) · **1376 words** · **18 slides** — **Smart Constructors** · ~9 min · **1720 words** · **15 slides**
- [53-adts-sealed](53-adts-sealed/) · **1129 words** · **15 slides** — **Algebraic Data Types with Sealed Records** · ~9 min · **1788 words** · **15 slides**

### Season 7 · Advanced FP

- [54-reader](54-reader/) · **1401 words** · **18 slides** — **Reader Monad** · ~10 min · **1837 words** · **15 slides**
- [55-writer](55-writer/) · **1133 words** · **15 slides** — **Writer Monad** · ~10 min · **1841 words** · **15 slides**
- [56-state](56-state/) · **1158 words** · **15 slides** — **State Monad** · ~10 min · **1890 words** · **16 slides**
- [57-kleisli](57-kleisli/) · **1077 words** · **15 slides** — **Kleisli Composition** · ~10 min · **1840 words** · **15 slides**
- [58-free-monad](58-free-monad/) · **1556 words** · **20 slides** — **Free Monad** · ~10 min · **1842 words** · **17 slides**
- [59-tagless-final](59-tagless-final/) · **1571 words** · **20 slides** — **Tagless Final** · ~10 min · **1895 words** · **16 slides**
- [60-cake-pattern](60-cake-pattern/) · **1429 words** · **19 slides** — **Cake Pattern** · ~10 min · **1900 words** · **17 slides**
- [61-type-classes](61-type-classes/) · **1411 words** · **17 slides** — **Type Classes via Dictionaries** · ~10 min · **1802 words** · **15 slides**
- [62-lenses-optics](62-lenses-optics/) · **1142 words** · **15 slides** — **Lenses & Optics** · ~9 min · **1729 words** · **15 slides**
- [63-trampolining](63-trampolining/) · **1133 words** · **15 slides** — **Trampolining & Stack Safety** · ~9 min · **1741 words** · **15 slides**
- [64-effect-interpreters](64-effect-interpreters/) · **1460 words** · **19 slides** — **Effect Systems & Interpreters** · ~9 min · **1743 words** · **15 slides**
- [65-vavr-vs-zazr](65-vavr-vs-zazr/) · **1199 words** · **18 slides** — **VAVR vs Zazr Deep Dive** · ~10 min · **1853 words** · **15 slides**

## Totals
- Episodes: 66
- Words: min 1708, avg 1822, max 1911
- Slides/episode: min 15, avg 15, max 17

