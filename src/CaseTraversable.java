public class CaseTraversable extends Case {
    boolean traversable;
    int chaleur;

    public CaseTraversable(int lig, int col) {
        super(lig, col);
        traversable = true;
        chaleur = 0;
    }

    public int getLig() {
        return lig;
    }

    public int getCol() {
        return col;
    }

    public int getChaleur() {
        return chaleur;
    }

    public boolean estTraversable() {
        return traversable;
    }
}