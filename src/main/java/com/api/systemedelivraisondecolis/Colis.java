package com.api.systemedelivraisondecolis;

public class Colis {
    private final int id;
    private String etat; // "En attente", "En transit", "Livré"
    private final String destination;
    private String description;
    private double progression;

    public Colis(int id, String destination) {
        this.id = id;
        this.destination = destination;
        this.etat = "En attente";
        this.description = "Aucune description";
        this.progression = 0.0;
    }

    public synchronized void mettreAJourEtat(String nouvelEtat) {
        this.etat = nouvelEtat;
    }

    public int getId() {
        return id;
    }

    public synchronized String getEtat() {
        return etat;
    }

    public String getDestination() {
        return destination;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public String getDescription() {return description;}

    public synchronized void mettreAJourProgression(double valeur) {
        this.progression = valeur;
    }
    public synchronized double getProgression() {
        return progression;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public void setProgression(double progression) {
        this.progression = progression;
    }
}
