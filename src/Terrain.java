import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.Scanner;

public class Terrain {
    private int hauteur, largeur;
    protected Case[][] carte;
    private Joueur joueur;

    public int getHauteur() {
        return hauteur;
    }

    public int getLargeur() {
        return largeur;
    }

    public Case[][] getCarte() {
        return carte;
    }

    public Terrain(String file) {
        try {
            Scanner sc = new Scanner(new FileInputStream(file));
            this.hauteur = sc.nextInt();
            this.largeur = sc.nextInt();
            sc.nextLine();
            int resistanceJoueur = sc.nextInt();
            int cles = sc.nextInt();
            sc.nextLine();
            this.carte = new Case[hauteur][largeur];
            for (int l=0; l<hauteur; l++) {
                String line = sc.nextLine();
                for (int c=0; c<largeur; c++) {
                    Case cc;
                    Character ch = line.charAt(c);
                    switch (ch) {
                        case '#': cc = new Mur(l, c); break;
                        case ' ': cc = new Hall(l, c); break;
                        case '+': cc = new Hall(l, c, true); break;
                        case '1': case '2': case '3': case '4':
                            cc = new Hall(l, c, (int)ch-(int)'0'); break;
                        case 'O': cc = new Sortie(l, c, 0); break;
                        case '@': cc = new Porte(l, c, false); break;
                        case '.': cc = new Porte(l, c, true); break;
                        case 'H':
                            if (this.joueur != null) throw new IllegalArgumentException("carte avec deux joueurs");
                            cc = new Hall(l, c);
                            this.joueur = new Joueur((CaseTraversable) cc, resistanceJoueur, cles);
                            break;
                        default:  cc = null; break;
                    }
                    carte[l][c] = cc;
                }
            }
            sc.close();
        }
        catch (IOException e) { e.printStackTrace(); System.exit(1); }
    }

    public Joueur getJoueur() { return this.joueur; }

    // Cette méthode, étant donnés les indices lig et col d'une case ainsi qu'une direction
    // renvoie la case (si elle est traversable) se trouvant au-dessus (d = nord), en-dessous (d = sud), à gauche (d = ouest) ou à droite (est) de
    // cette case dans la carte du terrain
    public CaseTraversable adjacenteTraversable(int lig, int col, Direction d) {
        // on vérifie d'abord que les indices entrés sont valides
        if (!(lig >= hauteur || lig < 0) && !(col >= largeur || col < 0)) {
            // la variable suivante va contenir l'index lig ou colonne qui permettra
            // de récupérer la case voulue dans la carte
            int index;
            Case c = carte[lig][col]; // on récupère la case référentielle
            switch (d) {
                case nord:
                    // l'usage de max permet d'éviter de rentrer dans des indices négatifs
                    index = Math.max(0, lig - 1);
                    // avant de stocker la case trouvée dans la variable de retour, on vérifie que c'est bien une case traversable
                    if (carte[index][col] instanceof CaseTraversable)
                        c = carte[index][col];
                    break;
                case sud:
                    // l'usage de min permet d'éviter de déborder des limites du terrain
                    index = Math.min(hauteur - 1, lig + 1);
                    if (carte[index][col] instanceof CaseTraversable)
                        c = carte[index][col];
                    break;
                case ouest:
                    // pareil que pour nord
                    index = Math.max(0, col - 1);
                    if (carte[lig][index] instanceof CaseTraversable)
                        c = carte[lig][index];
                    break;
                case est:
                    // pareil que pour sud
                    index = Math.min(largeur - 1, col + 1);
                    if (carte[lig][index] instanceof CaseTraversable)
                        c = carte[lig][index];
                    break;
                // une direction ne peut pas diverger des cas déroulés ci-dessus, mais il
                // est toujours possible que le pointeur null soit entré en paramètre par exemple
                default:
                    c = null;
                    break;
            }
            // revérification supplémentaire que la case que l'on s'apprête à renvoyer est bien traversable
            // en effet, c est initialisé comme une Case au départ, et si on trouve que la case adjacente (par rapport
            // à la direction entrée en paramètre), n'est pas une case traversable, c demeurera une Case, qui n'est pas nécessairement
            // une case traversable
            if(c instanceof CaseTraversable)
                return (CaseTraversable) c;
            // ainsi, le cas échéant, on renvoie simplement le pointeur null (des vérifications seront faites dans les méthodes appelantes pour éviter des bugs)
            else
                return null;
        }
        // si les indices entrés en paramètre sont invalides, on renvoie également null
        return null;
    }

    public void deplacerJoueur(Direction d){
        CaseTraversable caseJ = joueur.getPos();
        int lig, col;
        lig = caseJ.getLig();
        col = caseJ.getCol();
        if(adjacenteTraversable(lig, col, d) != null){
            joueur.bouge(adjacenteTraversable(lig, col, d));
        }
    }

    public ArrayList<CaseTraversable> getVoisinesTraversables(int lig, int col) {
        // l'usage ici d'un HashSet va permettre d'ajouter NordOuest, NordEst, SudOuest, SudEst
        // en passant par nord (nord.adjacente(est) ou nord.adjacente(ouest) par exemple), sud, ouest et est
        // sans ajouter de doublon au tableau final

        Set<CaseTraversable> acc = new HashSet<>();

        // on vérifie que les indices entrés en paramètre sont corrects (sait-on jamais), si ce n'est pas le cas on renvoie la liste logiquement vide à ce stade
        if (lig < 0 || lig >= hauteur || col < 0 || col >= largeur) return new ArrayList<>(acc);
        // normalement le test suivant ne devrait pas être effectué car sémantiquement, la case dont on cherche les voisines
        // traversables peut parfaitement être une porte ou une sortie
        // mais on effectue tout de même le test, car cette méthode servira en fait dans notre code à faire monter la chaleur de la case
        // à l'indice [lig][col], or, cela n'a pas de sens pour des portes et des sorties
        if (carte[lig][col] instanceof CaseTraversable && !(carte[lig][col] instanceof Porte) && !(carte[lig][col] instanceof Sortie)) {

            CaseTraversable nordOuest;
            CaseTraversable sudOuest;
            CaseTraversable nordEst;
            CaseTraversable sudEst;

            int ligN, colN, ligS, colS;

            // récupération de la case "du dessus"
            CaseTraversable nord = adjacenteTraversable(lig, col, Direction.nord);
            if (nord != null) {
                acc.add(nord); // la vérification de null provient de l'implémentation de adjacenteTraversable()
                // la partie de code ci-dessous va permettre de récupérer les cases des "coins" sur l'axe des diagonales du carré formé par la case centrale et ses huit voisines
                // en fait, pour récupérer par exemple le nordOuest, on va récupérer la case au nord de la case ouest (pareil pour sud ouest et pour est avec nordest et sudest)
                ligN = nord.getLig();
                colN = nord.getCol();
                nordOuest = adjacenteTraversable(ligN, colN, Direction.ouest);
                if(nordOuest != null) acc.add(nordOuest);
                nordEst = adjacenteTraversable(ligN, colN, Direction.est);
                if(nordEst != null) acc.add(nordEst);
            }
            // récupération de la case "du dessous"
            CaseTraversable sud = adjacenteTraversable(lig, col, Direction.sud);
            if(sud != null) {
                acc.add(sud);
                ligS = sud.getLig();
                colS = sud.getCol();
                sudOuest = adjacenteTraversable(ligS, colS, Direction.ouest);
                if(sudOuest != null) acc.add(sudOuest);
                sudEst = adjacenteTraversable(ligS, colS, Direction.est);
                if(sudEst != null) acc.add(sudEst);
            }
            // récupération de la case "à gauche"
            CaseTraversable ouest = adjacenteTraversable(lig, col, Direction.ouest);
            if (ouest != null) {
                acc.add(ouest);
                int ligO = ouest.getLig();
                int colO = ouest.getCol();
                // donc, ici ci-dessous, il est possible que nordOuest ait déjà été ajouté dans le bloc de nord
                // mais l'usage du HashSet nous évite d'avoir à nous soucier de cela
                nordOuest = adjacenteTraversable(ligO, colO, Direction.nord);
                if (nordOuest != null) acc.add(nordOuest);
                // pareil pour sudOuest dans le bloc sud...
                sudOuest = adjacenteTraversable(ligO, colO, Direction.sud);
                if(sudOuest != null) acc.add(sudOuest);
            }
            // récupération de la case "à droite"
            CaseTraversable est = adjacenteTraversable(lig, col, Direction.est);
            if (est != null) {
                acc.add(est);
                int ligE = est.getLig();
                int colE = est.getCol();
                // même commentaire concernant nordEst et sudEst, ils ont peut-être déjà été ajoutés dans le bloc de nord et sud, mais ce n'est pas grave
                nordEst = adjacenteTraversable(ligE, colE, Direction.nord);
                if (nordEst != null) acc.add(nordEst);
                sudEst = adjacenteTraversable(ligE, colE, Direction.sud);
                if (sudEst != null) acc.add(sudEst);
            }
        }
        // la structure de code initiale fournie dans le sujet définit getVoisinesTraversables comme renvoyant un ArrayList. De plus, notre implémentation de tour()
        // dans Furfeux.java considère les voisines comme un ArrayList, alors nous avons décidé de conserver cela en convertissant le HashSet :
        return new ArrayList<>(acc);
    }
}

