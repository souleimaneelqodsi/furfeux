public class Sortie extends CaseTraversable{
    private int chaleur;
    public Sortie(int lig, int col) {
        super(lig, col);
        traversable = true;
        chaleur = 0;
    }

    public Sortie(int lig, int col, int chaleur) {
        super(lig, col);
        this.chaleur = chaleur;
    }
    @Override
    public boolean estTraversable() {
        return super.estTraversable();
    }
}