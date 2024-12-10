package com.api.systemedelivraisondecolis;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class GestionnaireColis {
    private final List<Colis> colisList = new ArrayList<>();
    private final Semaphore semaphore = new Semaphore(1);

    public void enregistrerColis(Colis colis) {
        try {
            semaphore.acquire();
            colisList.add(colis);
            System.out.println("Colis " + colis.getId() + " enregistré pour " + colis.getDestination());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            semaphore.release();
        }
    }

    public List<Colis> getColisList() {
        return colisList;
    }
}
