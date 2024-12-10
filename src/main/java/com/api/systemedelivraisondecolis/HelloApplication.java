package com.api.systemedelivraisondecolis;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class HelloApplication extends Application {
    private final GestionnaireColis gestionnaireColis = new GestionnaireColis();
    private int compteurIdColis = 1;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        VBox root = new VBox(10);
        ListView<Colis> listeColisView = new ListView<>();
        Button boutonEnregistrer = new Button("Enregistrer un colis");
        Button boutonLivrer = new Button("Livrer un colis");

        // Set a custom cell factory for row coloring
        listeColisView.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(Colis colis, boolean empty) {
                super.updateItem(colis, empty);
                if (empty || colis == null) {
                    setText(null);
                    setStyle(""); // Reset style
                } else {
                    setText("Colis " + colis.getId() + ": " + colis.getEtat() + " (Destination: " + colis.getDestination() + ")");
                    // Set background color based on `etat`
                    if ("En transit".equals(colis.getEtat())) {
                        setStyle("-fx-background-color: lightblue;");
                    } else if ("Livré".equals(colis.getEtat())) {
                        setStyle("-fx-background-color: lightgreen;");
                    } else {
                        setStyle(""); // Default style
                    }
                }
            }
        });

        boutonEnregistrer.setOnAction(e -> {
            TextInputDialog dialogDestination = new TextInputDialog();
            dialogDestination.setTitle("Enregistrer un colis");
            dialogDestination.setHeaderText("Entrez les informations du colis");
            dialogDestination.setContentText("Destination :");

            String destination = dialogDestination.showAndWait().orElse("");
            if (destination.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Enregistrement annulé");
                alert.setHeaderText(null);
                alert.setContentText("La destination est obligatoire pour enregistrer un colis.");
                alert.showAndWait();
                return;
            }

            Colis colis = new Colis(compteurIdColis++, destination);
            gestionnaireColis.enregistrerColis(colis);
            listeColisView.getItems().add(colis); // Add `Colis` object directly
        });

        boutonLivrer.setOnAction(e -> {
            boolean hasPending = gestionnaireColis.getColisList().stream()
                    .anyMatch(colis -> "En attente".equals(colis.getEtat()));

            if (!hasPending) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Aucune livraison disponible");
                alert.setHeaderText(null);
                alert.setContentText("Il n'y a aucun colis à livrer pour le moment.");
                alert.showAndWait();
                return;
            }

            for (Colis colis : gestionnaireColis.getColisList()) {
                if ("En attente".equals(colis.getEtat())) {
                    colis.mettreAJourEtat("En transit");

                    // Update the ListView to reflect changes in the `etat`
                    listeColisView.refresh();

                    // Start delivery in a thread
                    ThreadLivraison threadLivraison = new ThreadLivraison(colis);
                    threadLivraison.start();

                    // Update `etat` to "Livré" after the thread completes
                    new Thread(() -> {
                        try {
                            threadLivraison.join(); // Wait for the delivery thread
                            colis.mettreAJourEtat("Livré");
                            Platform.runLater(listeColisView::refresh); // Refresh the ListView
                        } catch (InterruptedException ex) {
                            Thread.currentThread().interrupt();
                        }
                    }).start();
                }
            }
        });

        root.getChildren().addAll(listeColisView, boutonEnregistrer, boutonLivrer);
        Scene scene = new Scene(root, 500, 500);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Système de Livraison");
        primaryStage.show();
    }


}
