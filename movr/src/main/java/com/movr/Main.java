package com.movr;

import com.movr.model.Product;
import com.movr.model.ProductDAO;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Main extends Application {

    // --- Komponen UI ---
    private TableView<Product> productTable = new TableView<>();
    private TextField nameField = new TextField();
    private TextField descriptionField = new TextField();
    private TextField priceField = new TextField();
    private TextField stockField = new TextField();
    private Label statusLabel = new Label("Status");

    // --- DAO untuk akses database ---
    private ProductDAO productDAO = new ProductDAO();

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Manajemen Produk");

        // --- Konfigurasi Tabel ---
        TableColumn<Product, Integer> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Product, String> nameColumn = new TableColumn<>("Nama Produk");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameColumn.setPrefWidth(150);

        TableColumn<Product, String> descriptionColumn = new TableColumn<>("Deskripsi");
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        descriptionColumn.setPrefWidth(250);

        TableColumn<Product, Double> priceColumn = new TableColumn<>("Harga");
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        priceColumn.setPrefWidth(100);

        TableColumn<Product, Integer> stockColumn = new TableColumn<>("Stok");
        stockColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        stockColumn.setPrefWidth(75);

        productTable.getColumns().addAll(idColumn, nameColumn, descriptionColumn, priceColumn, stockColumn);
        productTable.getSelectionModel().selectedItemProperty().addListener(
            (obs, oldSelection, newSelection) -> showProductDetails(newSelection));

        // --- Form Input ---
        GridPane formGrid = new GridPane();
        formGrid.setHgap(10);
        formGrid.setVgap(10);
        formGrid.addRow(0, new Label("Nama:"), nameField, new Label("Harga:"), priceField);
        formGrid.addRow(1, new Label("Deskripsi:"), descriptionField, new Label("Stok:"), stockField);

        // --- Tombol-tombol Aksi ---
        Button saveButton = new Button("Tambah Produk");
        saveButton.setOnAction(e -> handleSave());

        Button updateButton = new Button("Update Produk");
        updateButton.setOnAction(e -> handleUpdate());

        Button deleteButton = new Button("Hapus Produk");
        deleteButton.setOnAction(e -> handleDelete());

        HBox buttonBox = new HBox(10, saveButton, updateButton, deleteButton);
        buttonBox.setAlignment(Pos.CENTER);

        // --- Layout Bawah (Form + Tombol + Status) ---
        VBox bottomLayout = new VBox(15, formGrid, buttonBox, statusLabel);
        bottomLayout.setPadding(new Insets(15));
        statusLabel.setAlignment(Pos.CENTER);
        VBox.setVgrow(statusLabel, Priority.ALWAYS);


        // --- Layout Utama ---
        BorderPane mainLayout = new BorderPane();
        Label title = new Label("Manajemen Data Produk");
        title.setFont(new Font("System Bold", 24));
        BorderPane.setAlignment(title, Pos.CENTER);
        mainLayout.setTop(title);
        mainLayout.setCenter(productTable);
        mainLayout.setBottom(bottomLayout);
        BorderPane.setMargin(productTable, new Insets(10));
        BorderPane.setMargin(title, new Insets(10));


        // --- Muat data awal ---
        loadProductData();

        // --- Tampilkan Scene ---
        Scene scene = new Scene(mainLayout, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // --- Logika dari Controller yang digabung ke sini ---

    private void loadProductData() {
        productTable.setItems(FXCollections.observableArrayList(productDAO.getAllProducts()));
        statusLabel.setText("Data dimuat.");
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


    public static void main(String[] args) {
        launch(args);
    }
}