public class Sortie extends CaseTraversable{
    public Sortie(int lig, int col) {
        super(lig, col);
        traversable = true;
        chaleur = 0;
    }

    public Sortie(int lig, int col, int chaleur) {
        super(lig, col);
        if(chaleur >= 0 && chaleur <= 10) this.chaleur = chaleur;
    }
    @Override
    public boolean estTraversable() {
        return super.estTraversable();
    }
}
