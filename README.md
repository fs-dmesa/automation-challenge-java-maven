# Automation Challenge — Java / Maven

Scaffold for the FastSpring SDET technical interview. This repo sets up the
environment only — the actual challenge is given during your interview session.

## Prerequisites

- JDK 17+
- Maven 3.8+

## Setup

```
mvn compile exec:java@install-playwright-browsers   # once per machine
mvn test
```

The smoke test should pass before your interview starts — if it doesn't,
let us know ahead of time rather than during the session.

## Headed vs headless

Tests run headless by default. To watch the browser instead:

```
mvn test -Dheadless=false
```

## Report

```
mvn allure:report exec:java@view-allure-report
```

Run this after `mvn test`. Opens at the `http://localhost:<port>/` URL it
prints. Shows the full test result, with each test's Playwright trace
attached — pass or fail. Download it from the report and open it at
[https://trace.playwright.dev/](https://trace.playwright.dev/) for the full
trace viewer. Stop the server with Ctrl+C.

## Layout

- `src/main/java/challenge/pages` — page objects
- `src/main/java/challenge/support` — shared constants
- `src/test/java/challenge` — test classes
