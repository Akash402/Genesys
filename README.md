# Genesys Cloud Test Suite (POC)

## Overview

A POC test suite combining API and UI testing in a single integrated workflow against the Genesys Cloud platform.

The suite includes:
- **EnvironmentConfigUtility.groovy** — maps environment identifiers to deployment regions and test groups, with inline assertions
- **OutboundPathsTest** — API test that validates the Genesys Cloud Swagger spec, verifying outbound paths, operations, and schema definitions
- **DeveloperCenterTest** — UI test that navigates the Genesys Cloud Developer Center and verifies the same outbound endpoint is visible and documented correctly

The groovy script runs as a standalone script

The API test runs first and passes the verified endpoint to the UI test, forming a single end-to-end workflow. If the API test fails, the UI test is skipped.

---

## Prerequisites

- Java 17
- Maven
- Git
- Docker (for containerised execution)
- Groovy (for local script execution)

---

## How to Run

Ensure Docker is installed and running before executing the commands below. If Docker is not installed, you can download it from https://www.docker.com/products/docker-desktop/

Clone the repository:

```cmd
git clone https://github.com/akash402/Genesys.git
cd Genesys
```

### Mac/Windows

```cmd
docker build -t genesys-tests .
docker run genesys-tests
```

---

## Scope and Limitations
- The Groovy script is a standalone POC demonstrating environment config mapping. At scale, environment configurations would be externalised to a YAML file.
- The API test targets the public Genesys Cloud Swagger spec (`/docs/swagger`) and does not require authentication.
- The UI test runs Chromium in headless mode via Playwright
- A production framework would include multiple TestNG suites (e.g. regression, smoke)
- At scale, all hardcoded values would be externalised to config files and driven by test data.
- At scale, UI tests would follow POM 
- At scale, allure/extent reporting would be integrated for detailed HTML reports