package com.movr.controller;

import com.movr.controller.action.HapusProduct;
import com.movr.controller.action.ProductAction;
import com.movr.controller.action.TambahProduct;
import com.movr.controller.action.UpdateProduct;
import com.movr.model.Product;
import com.movr.model.ProductDAO;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class ProductController {

    // Semua @FXML dan deklarasi variabel tetap sama
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
        // Initialize tetap sama
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        stockColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productTable.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> showProductDetails(newValue));
        
        // Memuat data pertama kali saat aplikasi dibuka
        loadProductData();
    }
    
    // --- DI SINI BAGIAN YANG DILENGKAPI ---

    /**
     * Mengambil data terbaru dari database dan menampilkannya ke tabel.
     */
    private void loadProductData() {
        productTable.setItems(FXCollections.observableArrayList(productDAO.getAllProducts()));
    }
    
    /**
     * Mengisi form input berdasarkan baris yang dipilih di tabel.
     * @param product produk yang dipilih.
     */
    private void showProductDetails(Product product) {
        if (product != null) {
            // Jika ada produk yang dipilih, isi form
            nameField.setText(product.getName());
            descriptionField.setText(product.getDescription());
            priceField.setText(Double.toString(product.getPrice()));
            stockField.setText(Integer.toString(product.getStock()));
        } else {
            // Jika pilihan dibatalkan, kosongkan form
            clearFields();
        }
    }

    /**
     * Mengosongkan semua isian form.
     */
    private void clearFields() {
        nameField.clear();
        descriptionField.clear();
        priceField.clear();
        stockField.clear();
        productTable.getSelectionModel().clearSelection();
    }

    // --- Method handler untuk tombol-tombol (tidak berubah) ---

    @FXML
    private void handleSave() {
        try {
            Product product = new Product(0, nameField.getText(), descriptionField.getText(),
                Double.parseDouble(priceField.getText()), Integer.parseInt(stockField.getText()));
            
            ProductAction tambahAction = new TambahProduct(productDAO, product);
            
            if (tambahAction.execute()) {
                statusLabel.setText("Produk baru berhasil ditambahkan!");
                loadProductData(); // Refresh tabel
                clearFields();     // Kosongkan form
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
            
            ProductAction updateAction = new UpdateProduct(productDAO, selectedProduct);
            
            if (updateAction.execute()) {
                statusLabel.setText("Produk berhasil diupdate!");
                loadProductData(); // Refresh tabel
                clearFields();     // Kosongkan form
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
        
        ProductAction hapusAction = new HapusProduct(productDAO, selectedProduct.getId());
        
        if (hapusAction.execute()) {
            statusLabel.setText("Produk berhasil dihapus.");
            loadProductData(); // Refresh tabel
            clearFields();     // Kosongkan form
        } else {
            statusLabel.setText("Gagal menghapus produk.");
        }
    }
}