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
