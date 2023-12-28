package com.example.uap;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Cashier extends Application {

    private ComboBox<String> menuComboBox;
    private TextField namaField, noMejaField, noHpField;
    private TextArea displayArea;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Coffee Shop Kasir");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(5);
        grid.setHgap(5);

        Label menuLabel = new Label("Menu:");
        GridPane.setConstraints(menuLabel, 0, 0);
        menuComboBox = new ComboBox<>();
        menuComboBox.getItems().addAll("Espresso", "Latte", "Cappuccino", "Americano", "Mocha");
        menuComboBox.setPromptText("Pilih Menu");
        GridPane.setConstraints(menuComboBox, 1, 0);

        Label namaLabel = new Label("Nama Pelanggan:");
        GridPane.setConstraints(namaLabel, 0, 1);
        namaField = new TextField();
        GridPane.setConstraints(namaField, 1, 1);

        Label noMejaLabel = new Label("No. Meja:");
        GridPane.setConstraints(noMejaLabel, 0, 2);
        noMejaField = new TextField();
        GridPane.setConstraints(noMejaField, 1, 2);

        Label noHpLabel = new Label("No. HP:");
        GridPane.setConstraints(noHpLabel, 0, 3);
        noHpField = new TextField();
        GridPane.setConstraints(noHpField, 1, 3);

        Button tambahButton = new Button("Tambah");
        GridPane.setConstraints(tambahButton, 1, 4);
        tambahButton.setOnAction(e -> tambahData());

        displayArea = new TextArea();
        displayArea.setEditable(false);
        GridPane.setConstraints(displayArea, 2, 0, 1, 5);

        grid.getChildren().addAll(menuLabel, menuComboBox, namaLabel, namaField, noMejaLabel, noMejaField,
                noHpLabel, noHpField, tambahButton, displayArea);

        Scene scene = new Scene(grid, 600, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void tambahData() {
        String menu = menuComboBox.getValue();
        String namaPelanggan = namaField.getText();
        String noMeja = noMejaField.getText();
        String noHp = noHpField.getText();

        if (menu == null || namaPelanggan.isEmpty() || noMeja.isEmpty() || noHp.isEmpty()) {
            displayArea.appendText("Semua field harus diisi!\n");
            return;
        }


       
        double harga = hitungHarga(menu);

        String dataTransaksi = "Menu: " + menu + "\n" +
                "Total Harga: Rp" + harga + "\n" +
                "Nama Pelanggan: " + namaPelanggan + "\n" +
                "No. Meja: " + noMeja + "\n" +
                "No. HP: " + noHp + "\n\n";

        displayArea.appendText(dataTransaksi);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("data_transaksi.txt", true))) {
            writer.write(dataTransaksi);
            writer.newLine();
            writer.flush();
        } catch (IOException e) {
            displayArea.appendText("Gagal menyimpan data ke dalam file!\n");
        }

        menuComboBox.setValue(null);
        namaField.clear();
        noMejaField.clear();
        noHpField.clear();
    }


    private double hitungHarga(String menu) {
        switch (menu) {
            case "Espresso":
                return 25000;
            case "Latte":
                return 25000;
            case "Cappuccino":
                return 35000;
            case "Americano":
                return 27500;
            case "Mocha":
                return 24000;
            default:
                return 0.00;
        }
    }
}
