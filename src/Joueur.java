import java.awt.event.*;

public class Joueur {
    private CaseTraversable c;
    private int resistance;
    private int cles;

    public Joueur(CaseTraversable c, int r, int k) {
        this.c = c;
        this.resistance = r;
        this.cles = k;
    }

    public int getResistance() {
        return resistance;
    }

    public void baisseResistance(int degats){
        resistance = Math.max(0, resistance - degats);
    }

    public CaseTraversable getPos() {
        return c;
    }

    public int getCles(){
        return cles;
    }

    public void bouge(Case cible) {
        if (cible instanceof CaseTraversable && !(cible instanceof CaseIntraversable)) {
            // la ligne suivante permet d'éviter d'avoir à "recaster" à chaque fois cible à chaque appel
            CaseTraversable cibleT = (CaseTraversable) cible;
            // la ligne suivante s'assure que la résistance n'est jamais négative
            resistance = Math.max(0, resistance - cibleT.getChaleur());
            // on explore les cas particuliers du déplacement :
            if (cibleT instanceof Hall && ((Hall) cibleT).testCle()) {
                ((Hall) cibleT).setContientCle(false);
                cles += 1;
            }
            if (cibleT instanceof Porte && (!cibleT.estTraversable())) {
                if (cles >= 1) {
                    ((Porte) cibleT).ouvrir();
                    cles--;
                }
                else return; // stoppe la fonction bouge car le joueur se trouve en face d'une porte fermée et il n'a pas de clés
            }

            c = cibleT; // et ensuite on déplace le joueur

        }
    }
}
