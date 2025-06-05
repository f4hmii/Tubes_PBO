package com.movr.controller;

import com.movr.model.User;
import com.movr.model.UserDAO;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.util.List;

public class UserCrudController {

    @FXML private TableView<User> userTable;
    @FXML private TableColumn<User, Integer> idColumn;
    @FXML private TableColumn<User, String> nameColumn;
    @FXML private TableColumn<User, String> emailColumn;
    
    @FXML private TextField nameField;
    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private Label statusLabel;

    private UserDAO userDAO = new UserDAO();

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        
        loadUserData();
    }

    private void loadUserData() {
        List<User> users = userDAO.getAllUsers();
        userTable.setItems(FXCollections.observableArrayList(users));
        statusLabel.setText("Data user berhasil dimuat.");
    }

    @FXML
    private void handleSave() {
        String name = nameField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();

        if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            statusLabel.setText("Nama, Email, dan Password harus diisi!");
            return;
        }

        User user = new User(name, email, password);
        if (userDAO.insertUser(user)) {
            statusLabel.setText("User baru berhasil ditambahkan!");
            loadUserData();
            clearFields();
        } else {
            statusLabel.setText("Gagal menyimpan. Email mungkin sudah ada.");
        }
    }

    @FXML
    private void handleDelete() {
        User selectedUser = userTable.getSelectionModel().getSelectedItem();
        if (selectedUser == null) {
            statusLabel.setText("Pilih user yang akan dihapus dari tabel.");
            return;
        }

        if (userDAO.deleteUser(selectedUser.getId())) {
            statusLabel.setText("User berhasil dihapus.");
            loadUserData();
        } else {
            statusLabel.setText("Gagal menghapus user.");
        }
    }

    private void clearFields() {
        nameField.clear();
        emailField.clear();
        passwordField.clear();
    }
}