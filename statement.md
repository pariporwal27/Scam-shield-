# Problem Statement & Project Scope: ScamShield

## 1. Problem Statement
In the modern digital landscape, communication channels such as SMS, instant messaging, and email are frequently exploited by cybercriminals to execute social engineering attacks, phishing schemes, and financial fraud. Attackers use sophisticated psychological triggers—including artificial urgency, fear of account suspension, and lucrative financial promises—often coupled with obfuscated or raw IP-based hyperlinks to deceive unsuspecting users. Many existing security tools are heavyweight, require constant cloud connectivity, or consume significant system resources. There is a strong need for an accessible, lightweight, rule-based security assistant that evaluates message content and links locally, delivering transparent risk scores and actionable threat breakdowns without compromising privacy.

---

## 2. Project Scope

### In-Scope:
* **Text & URL Ingestion**: Real-time console ingestion of SMS content, email excerpts, or web URLs.
* **Multi-Layered Heuristic Analysis**:
  * Categorized dictionary lookup for financial and identity fraud keywords.
  * Natural language detection of high-pressure urgency and coercion language.
  * Heuristic analysis of web hyperlinks (raw IP hostnames, suspicious TLDs, and URL shorteners).
* **Deterministic Risk Scoring Engine**: A normalized scoring engine ($0–100\%$) calculating weighted threat contributions.
* **Risk Categorization**: Mapping numerical risk scores to discrete threat tiers (`SAFE`, `SUSPICIOUS`, `HIGH_RISK`, `CRITICAL`).
* **Audit Persistence**: Automatic recording of scan history with timestamps and UUIDs in a structured local log file.
* **Asynchronous Multithreading**: Background thread pool execution for batch file scans to maintain non-blocking UI responsiveness.
* **Interactive CLI Interface**: An intuitive, robust menu-driven interface with comprehensive error handling.

### Out-of-Scope (for Current Release):
* Direct cloud-based API calls or external network scraping.
* Heavy deep-learning / neural network training pipelines.
* Relational database systems (RDBMS) (abstracted via storage interfaces for future updates).
* Graphical User Interface (GUI) / Mobile applications.

---

## 3. Target Users
* **Everyday Smartphone & Computer Users**: Individuals seeking quick verification of suspicious messages or SMS alerts before clicking links.
* **Elderly & Vulnerable Internet Users**: Individuals most susceptible to lottery scams, urgent bank alerts, and credential phishing.
* **Small Business Employees**: Staff handling customer support and vendor correspondence who need a rapid, offline validation check for inbound inquiries.
* **Computer Science Students & Security Enthusiasts**: Learners studying cybersecurity principles, heuristic evaluation, and modular object-oriented software architecture.

---

## 4. High-Level Features
1. **Interactive Message & URL Analyzer**: Instant scanning of arbitrary text and hyperlinks with detailed rule-by-rule diagnostic outputs.
2. **Pluggable Strategy Rules Engine**: Extensible detection modules adhering to the Open/Closed Principle.
3. **Structured Audit Log**: Persistent storage with options to view past scan history or clear history records.
4. **Multithreaded Batch Scanner**: Background worker pool processing bulk records concurrently without freezing user interaction.
5. **Zero-Dependency Architecture**: Built entirely on core standard Java (JDK 8+) with zero external runtime libraries.
