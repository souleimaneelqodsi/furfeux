public class Hall extends CaseTraversable {
    private int chaleur;

    public Hall(int lig, int col, int chaleur) {
        super(lig, col);
        this.chaleur = chaleur;
    }

    public Hall(int lig, int col, boolean traversable) {
        super(lig, col);
        this.traversable = traversable;
        this.chaleur = 0;
    }

    public Hall(int lig, int col) {
        super(lig, col);
        this.chaleur = 0;
    }

    @Override
    public boolean estTraversable() {
        return super.estTraversable();
    }
}
