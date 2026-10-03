#!/usr/bin/env python3
"""
Fichier de prototypage pour créer un chemin aléatoire par une procédure de marche aléatoire.

Fonctionnement :
    - On choisit (normalement au hasard) une case de départ et d'arrivée. Attention à vérifier que les deux cases vérifient une distance minimale
    - Tant que nous ne sommes pas arrivés à la case finale
        - On regarde tous les voisins (diagonales et cases du chemin non inclus)
        - Si pas de voisin libre
            - On repart du début
        - Si le chemin est trop long
            - On reprend un état antérieur du chemin
        - Sinon
            - On choisit un voisin aléatoirement
            - On l'ajoute au plateau
            - On recommence à partir de cette nouvelle case

Pourquoi ça ne bouclera jamais à l'infini:
    -> Loi des grands nombres, probabilité nulle de faire le même chemin en permanence

Comme cet algorithme n'est pas fait pour faire du temps réel (on génère le chemin au début du jeu), il sera suffisant, même si il prend une minute
"""

import random

plateau = [[i * (j + 1) for i in range(10)] for j in range(10)]


def dist(a: (int, int), b: (int, int)) -> int:
    return abs(a[0] - b[0]) + abs(a[1] - b[1])


def get_voisins(a: (int, int), chemin):
    return [
        (a[0] + i, a[1] + j)
        for i in range(-1, 2)
        for j in range(-1, 2)
        if i != j
        and abs(i) + abs(j) == 1
        and 0 <= a[0] + i < 10
        and 0 <= a[1] + j < 10
        and (a[0] + i, a[1] + j) not in chemin
    ]


# NOTE: Création de 10 chemins aléatoires
for i in range(10):
    case_debut = (7, 0)
    case_fin = (3, 9)
    case_courante = case_debut
    chemin = [case_debut]

    # Début algorithme
    while case_courante != case_fin:
        voisins = get_voisins(case_courante, chemin)
        if len(voisins) == 0:
            chemin = [case_debut]
            case_courante = case_debut
        elif len(chemin) > 20:
            chemin = chemin[:-12]
            case_courante = chemin[-1]
        else:
            nvl_case = random.choice(get_voisins(case_courante, chemin))
            chemin.append(nvl_case)
            case_courante = nvl_case
    # Fin algorithme
    print(chemin)
