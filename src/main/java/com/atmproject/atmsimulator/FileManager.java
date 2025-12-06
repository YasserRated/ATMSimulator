package com.atmproject.atmsimulator;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileManager {

    private static final String FILE_PATH = "/data/accounts.txt";

    public static List<Account> loadAccounts() {
        List<Account> accounts = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(FileManager.class.getResourceAsStream(FILE_PATH)))) {

            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String acc = parts[0];
                String pin = parts[1];
                double balance = Double.parseDouble(parts[2]);
                accounts.add(new Account(acc, pin, balance));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return accounts;
    }

    public static void saveAccounts(List<Account> accounts) {
        try {
            File file = new File("src/main/resources/data/accounts.txt");
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));

            for (Account acc : accounts) {
                writer.write(acc.getAccountNumber() + "," + acc.getPin() + "," + acc.getBalance());
                writer.newLine();
            }

            writer.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Account findAccount(String accountNumber, String pin) {
        for (Account acc : loadAccounts()) {
            if (acc.getAccountNumber().equals(accountNumber) &&
                    acc.getPin().equals(pin)) {
                return acc;
            }
        }
        return null;
    }
}
