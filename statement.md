# Problem Statement & Scope

## Problem Statement
Traditional banking software architecture is often obscured by complex enterprise frameworks, making it difficult for students and junior developers to understand the core underlying mechanisms of financial transactions. There is a need for a lightweight, transparent simulation that demonstrates how critical banking operations—such as concurrent transaction handling, data persistence, and error management—are implemented using core Object-Oriented Programming (OOP) principles without reliance on external heavy libraries.

## Scope of the Project
The "Automated Banking & ATM Simulation" is a standalone web-based application designed to simulate the essential functions of a retail bank. 
* **Boundaries:** The system operates in a local environment (localhost) and simulates a single user session interacting with a central banking server.
* **Functionality:** It covers the lifecycle of funds (Deposit/Withdraw), real-time balance updates, and permanent record-keeping via file storage.
* **Technical Scope:** The project specifically targets the implementation of Java Multithreading (Synchronization), Custom Exception Handling, and File I/O operations.

## Target Users
1.  **Bank Customers:** Users wishing to perform basic banking transactions (withdrawals/deposits) via a web interface.
2.  **Bank Auditors:** Administrators who need to review the `BankStatement.txt` log file to verify transaction history and timestamps.

## High-Level Features
1.  **Web-Based Interface:** A user-friendly HTML/CSS dashboard to view balances and perform actions.
2.  **Secure Transaction Processing:** Backend logic that prevents data inconsistency during simultaneous access using Synchronization.
3.  **Audit Trail:** Automatic generation of a persistent text file logging every transaction with a timestamp.
4.  **Error Management:** Robust handling of invalid scenarios (e.g., overdrafts) using custom Exceptions.