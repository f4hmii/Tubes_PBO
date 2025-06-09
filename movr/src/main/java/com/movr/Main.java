package com.movr;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
   @Override
public void start(Stage primaryStage) throws Exception {
    Parent root = FXMLLoader.load(getClass().getResource("/com/movr/view/ProductView.fxml"));
    primaryStage.setTitle("Manajemen Produk");

    Scene scene = new Scene(root, 800, 600);

    // **TAMBAHKAN BARIS INI UNTUK MEMUAT CSS**
    scene.getStylesheets().add(getClass().getResource("/com/movr/view/style.css").toExternalForm());

    primaryStage.setScene(scene);
    primaryStage.show();
}

    public static void main(String[] args) {
        launch(args);
    }
}