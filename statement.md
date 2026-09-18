# Problem Statement & Project Scope: ScamShield

## 1. Problem Statement
With the advent of morden era, social engineering attacks, phishing scams and cyberfraudsters have often made use of the existing communication channels, including SMS, instant messaging and emails. These attacks take advantage of various psychological factors, which include creating artificial urgency, fear of getting their account suspended and lucrative offers with money involved, accompanied by hidden or raw IP links in order to dupe unsuspecting users. The existing cybersecurity measures are either cumbersome, always connected to the cloud or heavy on system resource consumption. Therefore, there exists an urgent need for an easy-to-use security assistant which performs local evaluation of messages and links and provides risk score.

---

## 2. Project Scope

### In Scope:
* **Text/URL Ingestion**: In-console text ingest of SMS message contents, email snippets, and/or web URLs.
* **Heuristic Analysis Across Multiple Layers**:
  * Dictionary search of categorized fraud keywords in financial and identity fraud schemes.
  * Detection of natural languages in high-stress urgency and coercive language schemes.
  * Heuristic analysis of website hyperlinks (IP addresses of hosts, risky TLDs, and URL shorteners).
* **Normalized Deterministic Threat Scoring Engine**: Normalized risk score engine ($0-100\%$) that assigns threat contributions with weights.
* **Risk Categories**: Mapping numeric risk scores into distinct threat categories (`SAFE`, `SUSPICIOUS`, `HIGH_RISK`, `CRITICAL`).
* **Audit Logging Persistency**: Automated logging of the history of each scan with timestamping and UUID in local log files.
* **Asynchronous Multithreading**: Asynchronous thread pool execution for batch file scans and maintaining non-blocking UIs.
* **Interactive Command Line Interface**: User-friendly menu-driven command line with full error handling.

### Out-of-scope (for current release):
* Cloud-based API requests or web scraping from external network.
* Deep learning / neural networks and heavy training pipeline for machine learning.
* Relational databases systems (RDBMS) (abstracted through storage interfaces).

---

## 3. Target Users
* **Regular Smartphone and Computer Users**: Users who want to quickly validate any questionable messages or text message notifications without clicking on any links.
* **Senior and Vulnerable Internet Users**: Users who are highly prone to lottery scams, urgent banking notifications, and credential phishing attempts.
* **Employees of Small Businesses**: Workers responsible for dealing with customer service and vendors' communication needs a quick way to validate their incoming requests.
***Computer Science Students and Security Afficionados**: Students learning cybersecurity concepts, heuristic analysis, and object-oriented programming principles.

---
## 4. High-Level Features
1. **Message & URL Interactive Analyzer**: Quick analysis of any input data including hyperlinks with comprehensive outputs per each rule.
2. **Extensible Strategy Rules Engine**: Detecting engines built according to the Open/Closed Principle.
3. **Audit Log with Persistence**: Persistent audit log storage which could be accessed to review past scans or cleared completely.
4. **Batch Multithreading Scanner**: Multithreaded scanner which works in the background analyzing bulk data without freezing the GUI.
5. **Pure Java Architecture**: Application developed using only core standard Java libraries (JDK 8+).
