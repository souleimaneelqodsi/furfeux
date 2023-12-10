# **Rapport du projet Furfeux - L2 Info IPO 2023 :**


### Initialisation du projet : 

Lors du premier TP concernant le projet, nous avons établi la structure du code en modélisant, à la manère vue en cours, la hiérarchie entre les différentes classes et avons initialisé à ces dernières leurs attributs et méthodes respectives. Cette hiérarchie a été mise à jour plusieurs fois durant le reste de la semaine. -> cette étape du code n'a pas posé de problème mais a demandé un effort de modélisation et de conception.

<!-- #region -->


### Deuxième et début de troisième semaine :

Nous avons ajusté l'affichage de la fenêre graphique, cette partie a été longue car l'élaboration de la gestion de l'affichage était complexe et contenait beaucoup d'étapes (déplacement du joueur dans les limites du terrain par exemple).
<!-- #endregion -->

### Troisième semaine:

nous avons implémenté la partie logique du lancement du jeu : tours de jeux, propagation de la chaleur dans les cases, partie finie...
à ce stade la liste des charges du sujet est respectée, nous avons implémenter des décors sous format d'image apportant de la texture.


## **Difficultés rencontrées :**


### Dimensionnement et placement des cases dans FenetreJeu.java :

au départ, nous n'avons pas réussi à dimensionner les cases correctement car nous considérions uniquement les coordonnées dans le tableau 2D ``carte`` de Terrain, alors que celui-ci n'est pas à l'échelle de la fenêtre graphique et de ses pixels. De plus, notre terrain était dessiné à l'envers car nous parcourions le tableau carte du terrain dans l'ordre croissant, oubliant que l'axe des ordonnées dans la fenêtre graphique va "de bas en haut". Aussi, nous commencions à dessiner le terrain à partir du coin supérieur gauche de la fenêtre (pixel d'origine [0,0]), ce qui n'affichait pas le terrain de manière centrée. Nous avons donc défini deux variables X et Y, qui prendraient à chaque fois l'indice (i, j) de la case dans le parcours, le mettrait à l'échelle des pixels en multipliant par tailleCase,
et en ajoutant terrain.largeur/2 à X et terrain.hauteur/2 à Y, afin de centrer le terrain en s'assurant que
les pixels de dessin sont toujours décalés.




### Placement de la classe Porte dans la hiérarchie des classes : 

L'appel du constructeur de Terrain à Porte nous a un peu confus et nous avons pensé que Porte devait étendre la classe Case.
Finalement, nous avons changé et nous avons précisé que Porte était une classe fille de CaseTraversable, ce qui a été pratique car l'attribut traversable sert pour déterminer si la porte est ouverte ou non et un getter
(estTraversable) est déjà implémenté.



## Conclusions du Projet :
Le projet a englobé la totalité des notions abordées par le cours d'IPO. Ce projet peut encore beaucoup évoluer avec un investissement en temps supplémentaire. les fonctionnalités des bibliothèques swing et awt permettent l'ajout de beaucoup plus de fonctinnoalités, rendant le programme encore plus ergonomique.  
