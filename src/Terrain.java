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

    public CaseTraversable adjacenteTraversable(int lig, int col, Direction d) {
        if (!(lig >= hauteur || lig < 0) && !(col >= largeur || col < 0)) {
            int index;
            Case c = carte[lig][col];
            switch (d) {
                case nord:
                    index = Math.max(0, lig - 1);
                    if (carte[index][col] instanceof CaseTraversable)
                        c = carte[index][col];
                    break;
                case sud:
                    index = Math.min(hauteur - 1, lig + 1);
                    if (carte[index][col] instanceof CaseTraversable)
                        c = carte[index][col];
                    break;
                case ouest:
                    index = Math.max(0, col - 1);
                    if (carte[lig][index] instanceof CaseTraversable)
                        c = carte[lig][index];
                    break;
                case est:
                    index = Math.min(largeur - 1, col + 1);
                    if (carte[lig][index] instanceof CaseTraversable)
                        c = carte[lig][index];
                    break;
                default:
                    c = null;
                    break;
            }
            if(c instanceof CaseTraversable)
                return (CaseTraversable) c;
            else
                return null;
        }
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
        ArrayList<CaseTraversable> acc = new ArrayList<>();
        if (!(lig >= hauteur || lig < 0) && !(col >= largeur || col < 0)) {
            if (carte[lig][col] instanceof CaseTraversable && !(carte[lig][col] instanceof Porte)) {
                CaseTraversable nord = adjacenteTraversable(lig, col, Direction.nord);
                if (nord != null) acc.add(nord);
                CaseTraversable sud = adjacenteTraversable(lig, col, Direction.sud);
                if(sud != null) acc.add(sud);
                CaseTraversable ouest = adjacenteTraversable(lig, col, Direction.ouest);
                if (ouest != null) {
                    acc.add(ouest);
                    int ligO = ouest.getLig();
                    int colO = ouest.getCol();
                    CaseTraversable nordOuest = adjacenteTraversable(ligO, colO, Direction.nord);
                    if(nordOuest != null) acc.add(nordOuest);
                    CaseTraversable sudOuest = adjacenteTraversable(ligO, colO, Direction.sud);
                    if(sudOuest != null) acc.add(sudOuest);
                }
                else{
                    if(nord != null){
                        int ligN = nord.getLig();
                        int colN = nord.getCol();
                        CaseTraversable nordOuest = adjacenteTraversable(ligN, colN, Direction.ouest);
                        if(nordOuest != null) acc.add(nordOuest);
                    }
                    if(sud != null){
                        int ligS = sud.getLig();
                        int colS = sud.getCol();
                        CaseTraversable sudOuest = adjacenteTraversable(ligS, colS, Direction.ouest);
                        if(sudOuest != null) acc.add(sudOuest);
                    }
                }
                CaseTraversable est = adjacenteTraversable(lig, col, Direction.est);
                if (est != null) {
                    acc.add(est);
                    int ligE = est.getLig();
                    int colE = est.getCol();
                    CaseTraversable nordEst = adjacenteTraversable(ligE, colE, Direction.nord);
                    if(nordEst != null) acc.add(nordEst);
                    CaseTraversable sudEst = adjacenteTraversable(ligE, colE, Direction.sud);
                    if(sudEst != null) acc.add(sudEst);
                }
                else{
                    if(nord != null){
                        int ligN = nord.getLig();
                        int colN = nord.getCol();
                        CaseTraversable nordEst = adjacenteTraversable(ligN, colN, Direction.est);
                        if(nordEst != null) acc.add(nordEst);
                    }
                    if(sud != null){
                        int ligS = sud.getLig();
                        int colS = sud.getCol();
                        CaseTraversable sudEst = adjacenteTraversable(ligS, colS, Direction.est);
                        if(sudEst != null) acc.add(sudEst);
                    }
                }
            }
        }
        return acc;
    }
}

