package com.movr.controller;

import com.movr.model.User;
import com.movr.model.UserDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;

public class LoginController {

    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private Label statusLabel;

    private UserDAO userDAO = new UserDAO();

    @FXML
    private void handleLogin() {
        String email = emailField.getText();
        String password = passwordField.getText();

        if (email.isEmpty() || password.isEmpty()) {
            statusLabel.setText("Email dan Password harus diisi.");
            return;
        }

        User user = userDAO.findUserByEmailAndPassword(email, password);

        if (user != null) {
            statusLabel.setText("Login berhasil!");
            openMainApp();
        } else {
            statusLabel.setText("Email atau Password salah.");
        }
    }
    
    private void openMainApp() {
        try {
            Stage stage = (Stage) statusLabel.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("/com/movr/view/UserCrudView.fxml"));
            stage.setScene(new Scene(root));
            stage.setTitle("Manajemen User");
            stage.centerOnScreen();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}