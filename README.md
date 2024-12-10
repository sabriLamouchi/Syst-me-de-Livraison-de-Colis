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
2. Classe GestionnaireColis
Rôle : Gère une liste synchronisée de colis.
Propriétés principales :
colisList : Liste des colis enregistrés.
semaphore : Assure une synchronisation lors de l'accès à la liste.
Méthodes principales :
enregistrerColis(Colis colis) : Enregistre un nouveau colis dans la liste.
getColisList() : Renvoie la liste des colis.
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
4. Classe RandomUtils
Rôle : Fournit une fonction utilitaire pour générer des délais aléatoires.
Méthodes principales :
getRandomDelay() : Retourne un délai aléatoire entre 3000 et 6000 ms.
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
