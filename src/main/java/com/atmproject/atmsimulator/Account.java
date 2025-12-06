package com.atmproject.atmsimulator;

import java.util.ArrayList;
import java.util.List;

public class Account {
    private String accountNumber;
    private String pin;
    private double balance;
    private List<Transaction> history;

    public Account(String accountNumber, String pin, double balance) {
        this.accountNumber = accountNumber;
        this.pin = pin;
        this.balance = balance;
        this.history = new ArrayList<>();
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getPin() {
        return pin;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        balance += amount;
        history.add(new Transaction(Transaction.Type.DEPOSIT, amount));
    }

    public boolean withdraw(double amount) {
        if (amount <= balance) {
            balance -= amount;
            history.add(new Transaction(Transaction.Type.WITHDRAW, amount));
            return true;
        }
        return false;
    }

    public List<Transaction> getHistory() {
        return history;
    }
}
