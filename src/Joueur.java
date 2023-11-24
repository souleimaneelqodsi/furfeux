import java.awt.event.*;

public class Joueur implements KeyListener{

    private CaseTraversable c;
    private int resistance;

    public int getResistance() {
        return resistance;
    }

    public void baisseResistance(){
        if (resistance > 0) this.resistance--;
    }

    private int cles;
    public Joueur(CaseTraversable c, int r, int k) {
        this.c = c;
        this.resistance = r;
        this.cles = k;
    }

    public void bouge(Case cible) {
        if(cible instanceof CaseTraversable && !(cible instanceof CaseIntraversable))
            c = (CaseTraversable) cible;
            resistance -= ((CaseTraversable) cible).getChaleur();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
