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

## Test report

`mvn test` alone only writes plain XML/TXT to `target/surefire-reports/`.
For a browsable HTML report:

```
mvn test surefire-report:report-only
open target/site/surefire-report.html
```

## Traces

Every test writes a Playwright trace to `traces/<testName>.zip`, pass or
fail. View one by dragging it into
[trace.playwright.dev](https://trace.playwright.dev) (no install needed), or
with `npx playwright show-trace traces/<name>.zip` if you have Node.

## Layout

- `src/main/java/challenge/pages` — page objects
- `src/main/java/challenge/support` — shared constants
- `src/test/java/challenge` — test classes
