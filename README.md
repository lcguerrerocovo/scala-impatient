# Scala for the Impatient — Solutions

[![CI](https://github.com/lcguerrerocovo/scala-impatient/actions/workflows/publish-site.yml/badge.svg)](https://github.com/lcguerrerocovo/scala-impatient/actions)
![Scala](https://img.shields.io/badge/Scala-2.12-red?logo=scala)
![SBT](https://img.shields.io/badge/SBT-1.3.10-blue)

Exercise solutions and annotations for the 2nd edition of [Scala for the Impatient](https://horstmann.com/scala/) by Cay Horstmann.

**[Browse the solutions →](https://lcguerrerocovo.github.io/scala-impatient/)**

## Chapters

| # | Topic | Source | Tests |
|---|-------|--------|-------|
| 1 | The Basics | [source](src/main/scala/impatient_exercises/Chapter01.scala) | — |
| 2 | Control Structures and Functions | [source](src/main/scala/impatient_exercises/Chapter02.scala) | [tests](src/test/scala/impatient_exercises/Chapter02Test.scala) |
| 3 | Working with Arrays | [source](src/main/scala/impatient_exercises/Chapter03.scala) | — |
| 4 | Maps and Tuples | [source](src/main/scala/impatient_exercises/Chapter04.scala) | [tests](src/test/scala/impatient_exercises/Chapter04Test.scala) |
| 5 | Classes | [source](src/main/scala/impatient_exercises/Chapter05.scala) | [tests](src/test/scala/impatient_exercises/Chapter05Test.scala) |
| 6 | Objects | [source](src/main/scala/impatient_exercises/Chapter06.scala) | [tests](src/test/scala/impatient_exercises/Chapter06Test.scala) |
| 7 | Packages and Imports | [source](src/main/scala/impatient_exercises/Chapter07.scala) | [tests](src/test/scala/impatient_exercises/Chapter07Test.scala) |
| 8 | Inheritance | [source](src/main/scala/impatient_exercises/Chapter08.scala) | [tests](src/test/scala/impatient_exercises/Chapter08Test.scala) |
| 9 | Files and Regular Expressions | [source](src/main/scala/impatient_exercises/Chapter09.scala) | [tests](src/test/scala/impatient_exercises/Chapter09Test.scala) |
| 10 | Traits | [source](src/main/scala/impatient_exercises/Chapter10.scala) | [tests](src/test/scala/impatient_exercises/Chapter10Test.scala) |
| 11 | Operators | [source](src/main/scala/impatient_exercises/Chapter11.scala) | [tests](src/test/scala/impatient_exercises/Chapter11Test.scala) |
| 12 | Higher-Order Functions | [source](src/main/scala/impatient_exercises/Chapter12.scala) | [tests](src/test/scala/impatient_exercises/Chapter12Test.scala) |
| 13 | Collections | [source](src/main/scala/impatient_exercises/Chapter13.scala) | [tests](src/test/scala/impatient_exercises/Chapter13Test.scala) |
| 14 | Pattern Matching and Case Classes | [source](src/main/scala/impatient_exercises/Chapter14.scala) | [tests](src/test/scala/impatient_exercises/Chapter14Test.scala) |
| 17 | Futures | [source](src/main/scala/impatient_exercises/Chapter17.scala) | [tests](src/test/scala/impatient_exercises/Chapter17Test.scala) |
| 18 | Type Parameters | [source](src/main/scala/impatient_exercises/Chapter18.scala) | [tests](src/test/scala/impatient_exercises/Chapter18Test.scala) |

## Building

Prerequisites: JDK 8+, SBT

```bash
sbt compile    # compile
sbt test       # run tests
```

To generate the documentation site locally:

```bash
./generate-site.sh
```

## License

[MIT](LICENSE)
