public class CaseTraversable extends Case {
    boolean traversable;
    int chaleur;

    public CaseTraversable(int lig, int col) {
        super(lig, col);
        traversable = true;
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

    public boolean estTraversable(){
        return traversable;
    }

    /*@Override
    public Case adjacente(Direction d) {
        Case c;
        switch (d) {

            case nord -> {
                if(lig > 0)
                c = new CaseTraversable(lig - 1, col);
                else c = this;
            }
            case sud -> {
                c = new CaseTraversable(lig + 1, col);
            }
            case est -> {
                c = new CaseTraversable(lig, col + 1);
            }
            case ouest -> {
                if(col > 0)
                c = new CaseTraversable(lig, col - 1);
                else c = this;
            }
            default -> c = this;
        }
        return c;
    }*/
}