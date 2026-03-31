package banking.core;

import banking.exceptions.InsufficientFundsException;
import banking.utils.FileManager;

public class BankAccount {
    private String accountHolder;
    private double balance;

    public BankAccount(String accountHolder, double initialBalance) {
        this.accountHolder = accountHolder;
        this.balance = initialBalance;
    }

    public synchronized void deposit(double amount) {
        balance += amount;
        FileManager.logTransaction(accountHolder, "DEPOSIT", amount);
    }

    public synchronized void withdraw(double amount) throws InsufficientFundsException {
        if (amount > balance) {
            throw new InsufficientFundsException("Insufficient Funds");
        }
        balance -= amount;
        FileManager.logTransaction(accountHolder, "WITHDRAW", amount);
    }

    public double getBalance() { return balance; }
}