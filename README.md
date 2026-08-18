# Automation Challenge — Java / Maven

Scaffold for the FastSpring SDET technical interview. This repo sets up the
environment only — the actual challenge is given during your interview session.

## Prerequisites

- JDK 17+
- Maven 3.8+

## Setup

```
mvn compile exec:java@install-playwright-browsers   # once per machine
mvn test                                             # runs the smoke test
```

The smoke test opens the target storefront (Test Mode — no login or account
required) and checks that a product title renders. It should pass before your
interview starts; if it doesn't, that's an environment problem worth chasing
down ahead of time rather than during the session.

## Layout

- `src/main/java/challenge/pages` — page objects
- `src/main/java/challenge/support` — shared constants
- `src/test/java/challenge` — test classes
