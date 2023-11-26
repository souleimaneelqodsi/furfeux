import java.awt.event.*;

public class Joueur {

    private CaseTraversable c;
    private int resistance;

    public int getResistance() {
        return resistance;
    }

    public CaseTraversable getPos(){
        return c;
    }

    public void baisseResistance() {
        if (resistance > 0) this.resistance--;
    }

    private int cles;

    public Joueur(CaseTraversable c, int r, int k) {
        this.c = c;
        this.resistance = r;
        this.cles = k;
    }

    public void bouge(Case cible) {
        if (cible instanceof CaseTraversable && !(cible instanceof CaseIntraversable))
            c = (CaseTraversable) cible;
        resistance -= ((CaseTraversable) cible).getChaleur();
    }
}
