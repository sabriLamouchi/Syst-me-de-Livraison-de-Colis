
Système de Livraison de Colis
Ce projet JavaFX simule un système de gestion et de livraison de colis. Les fonctionnalités principales incluent l'enregistrement des colis, la livraison avec visualisation en temps réel (couleur ou barre de progression), et la synchronisation avec des threads.

Classes du Projet
1. Classe Colis
Rôle : Représente un colis dans le système.
Propriétés principales :
id : Identifiant unique du colis.
etat : État du colis (En attente, En transit, Livré).
destination : Destination du colis.
description : Description facultative du colis.
progression : Progrès de la livraison (entre 0.0 et 1.0).
Méthodes principales :
mettreAJourEtat(String nouvelEtat) : Met à jour l'état du colis.
mettreAJourProgression(double valeur) : Met à jour la progression de la livraison.
java
Copier le code
public class Colis {
    private final int id;
    private String etat;
    private final String destination;
    private String description;
    private double progression;

    public synchronized void mettreAJourEtat(String nouvelEtat) {
        this.etat = nouvelEtat;
    }

    public synchronized void mettreAJourProgression(double valeur) {
        this.progression = valeur;
    }
}
2. Classe GestionnaireColis
Rôle : Gère une liste synchronisée de colis.
Propriétés principales :
colisList : Liste des colis enregistrés.
semaphore : Assure une synchronisation lors de l'accès à la liste.
Méthodes principales :
enregistrerColis(Colis colis) : Enregistre un nouveau colis dans la liste.
getColisList() : Renvoie la liste des colis.
java
Copier le code
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class GestionnaireColis {
    private final List<Colis> colisList = new ArrayList<>();
    private final Semaphore semaphore = new Semaphore(1);

    public synchronized void enregistrerColis(Colis colis) {
        try {
            semaphore.acquire();
            colisList.add(colis);
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
3. Classe ThreadLivraison
Rôle : Simule la livraison d'un colis avec un délai et une mise à jour progressive de son état.
Propriétés principales :
colis : Colis en cours de livraison.
progressBar : Barre de progression associée au colis.
Méthodes principales :
run() :
Met l'état du colis à En transit.
Simule la livraison en utilisant un délai aléatoire (entre 3000 et 6000 ms).
Met à jour la progression de la livraison (barre de progression et état final).
java
Copier le code
import javafx.application.Platform;
import javafx.scene.control.ProgressBar;

public class ThreadLivraison extends Thread {
    private final Colis colis;
    private final ProgressBar progressBar;

    public ThreadLivraison(Colis colis, ProgressBar progressBar) {
        this.colis = colis;
        this.progressBar = progressBar;
    }

    @Override
    public void run() {
        try {
            colis.mettreAJourEtat("En transit");
            for (int i = 0; i <= 100; i++) {
                double progress = i / 100.0;
                colis.mettreAJourProgression(progress);
                Platform.runLater(() -> progressBar.setProgress(progress));
                Thread.sleep(50);
            }
            colis.mettreAJourEtat("Livré");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
4. Classe RandomUtils
Rôle : Fournit une fonction utilitaire pour générer des délais aléatoires.
Méthodes principales :
getRandomDelay() : Retourne un délai aléatoire entre 3000 et 6000 ms.
java
Copier le code
import java.util.Random;

public class RandomUtils {
    public static int getRandomDelay() {
        Random random = new Random();
        return 3000 + random.nextInt(3000);
    }
}
5. Classe HelloApplication
Rôle : Interface principale JavaFX pour l'interaction utilisateur.
Fonctionnalités principales :
Bouton "Enregistrer un colis" :
Affiche une boîte de dialogue pour entrer la destination et une description facultative.
Ajoute le colis à la liste et l'affiche dans l'interface.
Bouton "Livrer un colis" :
Vérifie les colis en attente.
Simule la livraison pour chaque colis en affichant une barre de progression ou en colorant la ligne :
Bleu : En transit.
Vert : Livré.
Fonctionnalités
Enregistrement des colis :

Saisir la destination et une description facultative.
Ajouter le colis à une liste avec un état initial En attente.
Livraison des colis :

Les colis passent à En transit, puis à Livré.
Les mises à jour d'état sont visibles dans l'interface :
Couleur ou barre de progression.
Synchronisation des threads :

Plusieurs threads peuvent enregistrer ou livrer des colis sans conflit.
Interface utilisateur intuitive :

Utilise JavaFX pour afficher les colis, leur état, et leur progression.
Structure du Code
scss
Copier le code
src/
├── Colis.java           // Classe représentant un colis
├── GestionnaireColis.java // Gestion de la liste des colis
├── ThreadLivraison.java  // Thread simulant la livraison
├── RandomUtils.java      // Génération de délais aléatoires
├── HelloApplication.java // Interface graphique principale
Exécution
Compilation :
Compilez tous les fichiers .java en utilisant votre IDE ou la ligne de commande :

bash
Copier le code
javac *.java
Exécution :
Lancez l'application avec :

bash
Copier le code
java HelloApplication
Exemples d'Utilisation
Enregistrer un colis :

Cliquez sur "Enregistrer un colis" et entrez une destination (et une description facultative).
Livrer un colis :

Cliquez sur "Livrer un colis" pour simuler la livraison.
Observation :

Couleur : Bleu pour En transit, Vert pour Livré.
Barre de progression (si activée) pour chaque colis en livraison.
