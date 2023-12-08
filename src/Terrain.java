import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
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

    public void deplacerJoueur(Direction d){
        CaseTraversable caseJ = joueur.getPos();
        int index, lig, col;
        lig = caseJ.getLig();
        col = caseJ.getCol();
        switch(d){
            case nord:
                // 'monter' d'une case revient à monter à la ligne suivante, càd décrémenter l'indice de ligne
                index = Math.max(0, lig - 1);
                joueur.bouge(carte[index][col]);
                break;
            case sud:
                index = Math.min(hauteur - 1, lig + 1);
                joueur.bouge(carte[index][col]);
                break;
            case ouest: // aller à l'ouest par raport à une case signifie aller à gauche, ce qui se traduit par une décrémentation de l'indice colonne
                index = Math.max(0, col - 1);
                joueur.bouge(carte[lig][index]);
                break;
            case est:
                index = Math.min(largeur - 1, col + 1);
                joueur.bouge(carte[lig][index]);
                break;
        }
    }

    public ArrayList<CaseTraversable> getVoisinesTraversables(int lig, int col) {
        /* À compléter */
        return null;
    }
}
