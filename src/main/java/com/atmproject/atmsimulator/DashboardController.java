package com.atmproject.atmsimulator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;

import java.util.List;
import java.util.Optional;

public class DashboardController {

    @FXML
    private Label balanceLabel;

    @FXML
    private ListView<String> historyList;

    private Account account;
    private final ObservableList<Transaction> transactions = FXCollections.observableArrayList();

    private List<Account> allAccounts;

    @FXML
    public void initialize() {
        // listen for transaction updates
        transactions.addListener((javafx.collections.ListChangeListener.Change<? extends Transaction> c) -> {
            refreshHistoryView();
        });
    }


    public void setAccount(Account account) {
        this.account = account;
        balanceLabel.setText(String.format("Balance: $%.2f", account.getBalance()));


        allAccounts = FileManager.loadAccounts();


        addTransaction(new Transaction(Transaction.Type.LOGIN));
    }

    private void addTransaction(Transaction t) {
        transactions.add(t);
        account.getHistory().add(t);
        FileManager.saveAccounts(allAccounts);
    }

    private void refreshHistoryView() {
        ObservableList<String> items = FXCollections.observableArrayList();
        for (Transaction t : account.getHistory()) {
            items.add(t.toString());
        }
        historyList.setItems(items);
        if (!items.isEmpty()) {
            historyList.scrollTo(items.size() - 1);
        }
    }

    @FXML
    private void handleDeposit() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Deposit");
        dialog.setHeaderText("Enter amount to deposit");
        Optional<String> result = dialog.showAndWait();

        result.ifPresent(amount -> {
            try {
                double value = Double.parseDouble(amount);
                if (value <= 0) {
                    alert("Invalid amount", "Amount must be positive");
                    return;
                }
                account.deposit(value);
                updateBalance();
                addTransaction(new Transaction(Transaction.Type.DEPOSIT, value));
            } catch (Exception e) {
                alert("Error", "Please enter a valid number");
            }
        });
    }

    @FXML
    private void handleWithdraw() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Withdraw");
        dialog.setHeaderText("Enter amount to withdraw");
        Optional<String> result = dialog.showAndWait();

        result.ifPresent(amount -> {
            try {
                double value = Double.parseDouble(amount);
                if (value <= 0) {
                    alert("Invalid amount", "Amount must be positive");
                    return;
                }
                if (!account.withdraw(value)) {
                    alert("Error", "Insufficient funds");
                    return;
                }
                updateBalance();
                addTransaction(new Transaction(Transaction.Type.WITHDRAW, value));
            } catch (Exception e) {
                alert("Error", "Please enter a valid number");
            }
        });
    }

    @FXML
    private void handleCheckBalance() {
        addTransaction(new Transaction(Transaction.Type.CHECK_BALANCE));
        alert("Balance", "Your balance is $" + String.format("%.2f", account.getBalance()));
    }

    @FXML
    private void handleLogout() {
        Stage stage = (Stage) balanceLabel.getScene().getWindow();
        stage.close();
    }

    private void updateBalance() {
        balanceLabel.setText(String.format("Balance: $%.2f", account.getBalance()));
    }

    private void alert(String title, String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.show();
    }
}
