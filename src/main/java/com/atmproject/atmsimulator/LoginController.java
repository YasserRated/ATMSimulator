package com.atmproject.atmsimulator;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {

    @FXML
    private TextField accountNumberField;

    @FXML
    private PasswordField pinField;

    @FXML
    private void handleLogin() {
        String accountNumber = accountNumberField.getText();
        String pin = pinField.getText();

        Account account = FileManager.findAccount(accountNumber, pin);

        if (account != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/atmproject/atmsimulator/dashboard.fxml"));
                Scene scene = new Scene(loader.load());

                DashboardController controller = loader.getController();
                controller.setAccount(account);

                Stage stage = new Stage();
                stage.setTitle("ATM Dashboard");
                stage.setScene(scene);
                stage.show();

                Stage loginStage = (Stage) accountNumberField.getScene().getWindow();
                loginStage.close();

            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Login Failed");
            alert.setHeaderText(null);
            alert.setContentText("Invalid account number or PIN!");
            alert.showAndWait();
        }
    }
}
