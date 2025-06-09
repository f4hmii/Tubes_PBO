package com.movr.controller;

import com.movr.model.Product;
import com.movr.model.ProductDAO;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class ProductController {

    @FXML private TableView<Product> productTable;
    @FXML private TableColumn<Product, Integer> idColumn;
    @FXML private TableColumn<Product, String> nameColumn;
    @FXML private TableColumn<Product, String> descriptionColumn;
    @FXML private TableColumn<Product, Double> priceColumn;
    @FXML private TableColumn<Product, Integer> stockColumn;

    @FXML private TextField nameField;
    @FXML private TextField descriptionField;
    @FXML private TextField priceField;
    @FXML private TextField stockField;
    @FXML private Label statusLabel;
    
    private ProductDAO productDAO = new ProductDAO();

    @FXML
    public void initialize() {
        // Setup kolom tabel
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        stockColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));

        // Listener untuk mengisi form saat item di tabel dipilih
        productTable.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> showProductDetails(newValue));
        
        loadProductData();
    }

    private void loadProductData() {
        productTable.setItems(FXCollections.observableArrayList(productDAO.getAllProducts()));
    }
    
    private void showProductDetails(Product product) {
        if (product != null) {
            nameField.setText(product.getName());
            descriptionField.setText(product.getDescription());
            priceField.setText(Double.toString(product.getPrice()));
            stockField.setText(Integer.toString(product.getStock()));
        } else {
            clearFields();
        }
    }

    @FXML
    private void handleSave() {
        try {
            Product product = new Product(0, nameField.getText(), descriptionField.getText(),
                Double.parseDouble(priceField.getText()), Integer.parseInt(stockField.getText()));
            
            if (productDAO.insertProduct(product)) {
                statusLabel.setText("Produk baru berhasil ditambahkan!");
                loadProductData();
                clearFields();
            } else {
                statusLabel.setText("Gagal menambahkan produk.");
            }
        } catch (NumberFormatException e) {
            statusLabel.setText("Error: Harga dan Stok harus berupa angka.");
        }
    }

    @FXML
    private void handleUpdate() {
        Product selectedProduct = productTable.getSelectionModel().getSelectedItem();
        if (selectedProduct == null) {
            statusLabel.setText("Pilih produk yang akan diupdate.");
            return;
        }
        try {
            selectedProduct.setName(nameField.getText());
            selectedProduct.setDescription(descriptionField.getText());
            selectedProduct.setPrice(Double.parseDouble(priceField.getText()));
            selectedProduct.setStock(Integer.parseInt(stockField.getText()));
            
            if (productDAO.updateProduct(selectedProduct)) {
                statusLabel.setText("Produk berhasil diupdate!");
                loadProductData();
                clearFields();
            } else {
                statusLabel.setText("Gagal mengupdate produk.");
            }
        } catch (NumberFormatException e) {
            statusLabel.setText("Error: Harga dan Stok harus berupa angka.");
        }
    }

    @FXML
    private void handleDelete() {
        Product selectedProduct = productTable.getSelectionModel().getSelectedItem();
        if (selectedProduct == null) {
            statusLabel.setText("Pilih produk yang akan dihapus.");
            return;
        }
        
        if (productDAO.deleteProduct(selectedProduct.getId())) {
            statusLabel.setText("Produk berhasil dihapus.");
            loadProductData();
            clearFields();
        } else {
            statusLabel.setText("Gagal menghapus produk.");
        }
    }

    private void clearFields() {
        nameField.clear();
        descriptionField.clear();
        priceField.clear();
        stockField.clear();
        productTable.getSelectionModel().clearSelection();
    }
}