public class Hall extends CaseTraversable {
    private boolean contientCle;

    public Hall(int lig, int col) {
        super(lig, col);
        this.chaleur = 0;
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
        this.chaleur = 0;
    }

    public boolean testCle() {
        return contientCle;
    }

    public void setContientCle(boolean contientCle) {
        this.contientCle = contientCle;
    }
}
