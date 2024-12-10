package com.api.systemedelivraisondecolis;

import javafx.application.Platform;
import javafx.scene.control.ProgressBar;

import java.util.Random;

public class ThreadLivraison extends Thread {
    private final Colis colis;

    public ThreadLivraison(Colis colis) {
        this.colis = colis;
    }

    @Override
    public void run() {
        try {
            colis.mettreAJourEtat("En transit");
            System.out.println("Colis " + colis.getId() + colis.getEtat());
            Thread.sleep(3000 + new Random().nextInt(3000)); // Simule le temps de livraison
            colis.mettreAJourEtat("Livré");
            System.out.println("Colis " + colis.getId() + " livré à " + colis.getDestination());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

