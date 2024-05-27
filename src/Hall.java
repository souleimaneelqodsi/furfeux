
// classe modélisant une case de couloir/hall
public class Hall extends CaseTraversable {
    private boolean contientCle;

    public Hall(int lig, int col) {
        super(lig, col);
        // pas de clé, à moins qu'on ne le renseigne explicitement
        this.contientCle = false;
    }
    public Hall(int lig, int col, int chaleur) {
        super(lig, col);
        // chaleur comprise entre 0 et 10 :
        if(chaleur >= 0 && chaleur <= 10) this.chaleur = chaleur;
        this.contientCle = false;
    }

    public Hall(int lig, int col, boolean contientCle) {
        super(lig, col);
        this.contientCle = contientCle;
    }

    // getter de contientCle
    public boolean testCle() {
        return contientCle;
    }

    public void setContientCle(boolean contientCle) {
        this.contientCle = contientCle;
    }
}
