#  ScamShield – Fraud and Scam Detection Assistant

ScamShield is a rule-based command line security assistant created in Java. It looks at messages SMS texts, email content and strange web links to see if there is a chance of fraud using a system that can add rules for risk scoring.

---

##  Project Overview

Digital scams often use ways to make people feel worried offer money or hide the web addresses. ScamShield looks at the information you give it. Checks it against set ways to find out the chance of a problem. It gives a score from 0 to 100 percent. Puts the problem into different levels of danger (SAFE, SUSPICIOUS, HIGH_RISK CRITICAL).

---

##  Key Features

- **Multi-Vector Threat Analysis**: Looks at words, urgent language and strange web addresses.

- **Rule-Based Risk Engine**: A system that can change and add rules using the Strategy Design Pattern.

- **Audit and History Persistence**: Keeps a record of when scans happened the scores, the danger levels and the rules that were used.

- **Asynchronous Background Scanner**: Uses threads to do several scans at the same time with ExecutorService.

- **Zero External Dependencies**: Uses the basic Java library (works with any JDK without other programs).

---

##  Software Architecture

ScamShield follows a 4-tier **Layered Architecture** adhering to SOLID principles:

```text
                             +-------------------+
                             |   ScamShieldCLI   |  <-- UI / Presentation Layer
                             +---------+---------+
                                       |
                                       v
                             +-------------------+
                             |    RiskEngine     |  <-- Service / Business Logic Layer
                             +---------+---------+
                                       |
                   +-------------------+-------------------+
                   |                   |                   |
                   v                   v                   v
           +---------------+   +---------------+   +---------------+
           | KeywordRule   |   |   UrlRule     |   | UrgencyRule   | <-- Strategy Pattern (Rules)
           +---------------+   +---------------+   +---------------+
                                       |
                                       v
                             +-------------------+
                             | FileStorageService|  <-- Persistence / Data Access Layer
                             +-------------------+
```

### Design Patterns Applied:
* **Strategy Pattern**: The `ScamRule` interface defines a pluggable contract for all detection logic. New rules can be plugged in without modifying the engine (**Open/Closed Principle**).
* **Data Transfer Object (DTO)**: `ScanRequest` and `ScanResult` encapsulate immutable data objects.
* **DAO / Storage Abstraction**: `StorageService` interface decouples the file persistence implementation from caller services.

---

##  Project Structure

```text
ScamShield/
 ├── .gitignore
 ├── README.md
 ├── data/
 │    └── scan_history.txt             # Automatically generated persistence log
 └── src/
      ├── Main.java                    # Application Entry Point & Dependency Injection
      └── com/
           └── scamshield/
                ├── model/             # Entities & DTOs
                │    ├── RiskLevel.java
                │    ├── ScanRequest.java
                │    └── ScanResult.java
                ├── rule/              # Detection Strategies
                │    ├── ScamRule.java
                │    ├── KeywordRule.java
                │    ├── UrgencyRule.java
                │    └── UrlRule.java
                ├── engine/            # Scoring Engine
                │    └── RiskEngine.java
                ├── storage/           # Persistence Layer
                │    ├── StorageService.java
                │    └── FileStorageService.java
                ├── service/           # Multithreading / Background Jobs
                │    └── BackgroundScanService.java
                └── ui/                # Command-Line Interface
                     └── ScamShieldCLI.java
```

---

##  Getting Started

### Prerequisites
* **Java Development Kit (JDK 8 or higher)** installed on your machine.
* Verify Java installation:
  ```bash
  javac -version
  java -version
  ```

### Compilation & Execution

1. **Clone or navigate to the project directory**:
   ```bash
   cd ScamShield
   ```

2. **Compile all Java source files**:
   ```bash
   javac -d bin src/com/scamshield/model/*.java src/com/scamshield/rule/*.java src/com/scamshield/engine/*.java src/com/scamshield/storage/*.java src/com/scamshield/service/*.java src/com/scamshield/ui/*.java src/Main.java
   ```

3. **Run the application**:
   ```bash
   java -cp bin Main
   ```

---

##  Sample Test Cases

| Scenario | Sample Input | Expected Risk Level |
| :--- | :--- | :--- |
| **Phishing SMS** | `URGENT: Your account is blocked! Verify PIN at bit.ly/mybank within 2 hours or face legal action` | `HIGH_RISK` (~70%) |
| **Malicious Link** | `Claim your lottery winner prize of 10000 immediately at http://192.168.1.50/login.xyz` | `CRITICAL` (~90%) |
| **Suspicious Email** | `Action required: Please process bank transfer for your pending refund.` | `SUSPICIOUS` (~35%) |
| **Normal Message** | `Hey! Are we still meeting for group study at the library tomorrow at 4 PM?` | `SAFE` (0%) |

---
