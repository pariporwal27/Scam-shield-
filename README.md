#  ScamShield – Fraud & Scam Detection Assistant

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![Architecture](https://img.shields.io/badge/Architecture-Layered%20%2F%20Modular-blue.svg)](#software-architecture)

**ScamShield** is a lightweight, rule-based command-line security assistant developed in Java. It analyzes messages, SMS texts, email bodies, and suspicious web links to determine the likelihood of fraud using an extensible risk scoring engine.

---

## 📌 Project Overview

Digital scams frequently employ psychological manipulation techniques such as **urgency**, **financial incentives**, and **obfuscated/IP-based URLs**. ScamShield scans input data against structured detection heuristics to compute a normalized risk score ($0–100\%$) and classifies threats into severity tiers (`SAFE`, `SUSPICIOUS`, `HIGH_RISK`, `CRITICAL`).

---

## ⚙️ Key Features

- **Multi-Vector Threat Analysis**: Evaluates keywords, high-pressure urgency language, and suspicious URL patterns.
- **Rule-Based Risk Engine**: Modular scoring aggregator built on the **Strategy Design Pattern**.
- **Audit & History Persistence**: Automatically records scan timestamps, scores, risk levels, and triggered heuristics to local storage.
- **Asynchronous Background Scanner**: Multithreaded batch processing powered by `ExecutorService`.
- **Zero External Dependencies**: Pure core Java standard library (runs on any JDK without third-party frameworks).

---

## 🏗️ Software Architecture

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

## 📁 Project Structure

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

## 🚀 Getting Started

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

## 🧪 Sample Test Cases

| Scenario | Sample Input | Expected Risk Level |
| :--- | :--- | :--- |
| **Phishing SMS** | `URGENT: Your account is blocked! Verify PIN at bit.ly/mybank within 2 hours or face legal action` | `HIGH_RISK` (~70%) |
| **Malicious Link** | `Claim your lottery winner prize of 10000 immediately at http://192.168.1.50/login.xyz` | `CRITICAL` (~90%) |
| **Suspicious Email** | `Action required: Please process bank transfer for your pending refund.` | `SUSPICIOUS` (~35%) |
| **Normal Message** | `Hey! Are we still meeting for group study at the library tomorrow at 4 PM?` | `SAFE` (0%) |

---

## 📄 License
This project is open-source under the [MIT License](LICENSE).
