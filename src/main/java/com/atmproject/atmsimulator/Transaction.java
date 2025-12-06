package com.atmproject.atmsimulator;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Transaction {
    public enum Type { DEPOSIT, WITHDRAW, CHECK_BALANCE, LOGIN }

    private final Type type;
    private final double amount; // 0 for non-amount actions
    private final LocalDateTime time;

    public Transaction(Type type, double amount) {
        this.type = type;
        this.amount = amount;
        this.time = LocalDateTime.now();
    }

    public Transaction(Type type) { // for LOGIN or CHECK_BALANCE
        this(type, 0);
    }

    @Override
    public String toString() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String t = time.format(fmt);
        switch (type) {
            case DEPOSIT:
                return t + "  —  Deposited $" + String.format("%.2f", amount);
            case WITHDRAW:
                return t + "  —  Withdrew $" + String.format("%.2f", amount);
            case CHECK_BALANCE:
                return t + "  —  Checked balance";
            case LOGIN:
                return t + "  —  Logged in";
            default:
                return t + "  —  Action";
        }
    }
}
