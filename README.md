# Clone de Bloons TD 6 (Java)

*Projet universitaire réalisé en groupe (4 étudiants).*

## Présentation
Développement d'un clone du célèbre jeu de "Tower Defense" Bloons TD 6. Le joueur doit placer des défenses stratégiques pour empêcher des vagues d'ennemis d'atteindre la fin du parcours. 

## Mon rôle dans ce projet
- Implémentation du comportement des ennemis (ballons) et gestion de leurs interactions et effets sur le plateau de jeu.
- Correction de bugs et amélioration du code sur l'ensemble du projet.
- Mise en place des tests unitaires automatisés.

## Technologies & Outils
- **Langage :** Java (Programmation Orientée Objet)
- **Prototypage :** Python (pour la génération aléatoire des chemins)
- **Tests :** JUnit
- **Compilation :** Makefile
- **Gestion de projet :** réunions hebdomadaires

## Comment lancer le jeu ?
Toutes les commandes sont à exécuter depuis un terminal, à la racine du dossier du projet.

**1. Jouer au jeu**
*   Compiler le projet : `make game`
*   Lancer le jeu : `java -jar game.jar`

**2. Tests & Documentation (Pour les développeurs)**
*   Compiler les tests unitaires : `make tests`
*   Exécuter les tests : `make runtests`
*   Générer la documentation (Javadoc) : `make docs`

---
*Note : Si vous souhaitez voir l'évolution hebdomadaire du projet et notre organisation de groupe, vous pouvez consulter le fichier [JOURNAL_DE_BORD.md](lien-vers-ton-fichier).*