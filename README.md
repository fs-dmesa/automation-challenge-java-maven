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
fail. View one by uploading the file at
[https://trace.playwright.dev/](https://trace.playwright.dev/) — no install
needed. If you have Node, `npx playwright show-trace traces/<name>.zip`
works too. Every trace is also attached to the Allure report below, so you
usually don't need this file directly.

## Allure report

```
mvn test allure:report exec:java@view-allure-report
```

This runs the tests, generates an Allure report, and serves it at the URL it
prints (`http://localhost:<port>/`). Allure's report loads its data via XHR,
which browsers block outright under `file://` — opening `index.html`
directly just shows a blank page — so this runs a small embedded HTTP
server instead of requiring `allure serve` or a separate Allure install.
Each test's Playwright trace appears inline as an attachment. Stop the
server with Ctrl+C.

## Layout

- `src/main/java/challenge/pages` — page objects
- `src/main/java/challenge/support` — shared constants
- `src/test/java/challenge` — test classes
