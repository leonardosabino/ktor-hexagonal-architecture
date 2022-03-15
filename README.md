# ktor-hexagonal-template

This service is a template for hexagonal architecture in Ktor.

# Components

## Hexagonal

The center part of the hexagonal architecture is implemented by the domain and usecase packages.

## Driver actors

The driver actors/primary actors of the hexagonal architecture are implemented by the transportLayer packages.

## Driven actors

The driven actors/secondary actors of the hexagonal architecture are implemented by the datasource packages.

## Architecture Test

In order to the architecture be respected, an architecture test was implemented and can be found in ArchitectureTest class. 
If necessary, this test should be changed carefully in order to maintain the architecture.

# Running locally

## Tools

In order to build and run this application, you need to install:

- Java 15
- Maven 3.8.3+

### IntelliJ Plugins
These can be installed through the IntelliJ Plugins Marketplace (Preferences -> Plugins -> Marketplace):

- [Ktlint](#Ktlint)
- Kotest

## Building jar
Run the following command on the root folder:

```shell
mvn clean package
```

If you want to skip the tests, you can use:

```shell
mvn clean package -Dmaven.test.skip
```

## Running in IntelliJ

You can import the project and run the main function in hexagonal.template.ApplicationKt.

### Running tests

You can run all tests by clicking with the right button on the folder src/test/kotlin and then on Run 'All tests' or 
you can run a single test or all tests of a file by opening it and using the left vertical column between the line numbers and the code section. 

## Ktlint

This project uses [Ktlint](https://ktlint.github.io/) to check code style rules and **THE BUILD WILL FAIL ON STYLE VIOLATIONS!!**

1. To check for style violations, run `mvn antrun:run@ktlint`
2. To format code you can use: `mvn antrun:run@ktlint-format`

**Tips:**
* In case you see `"Wildcard import (cannot be auto-corrected)"`, it means Ktlint can't organize imports and you need to do it manually.
