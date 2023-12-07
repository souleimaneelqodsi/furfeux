public class CaseTraversable extends Case {
    boolean traversable;
    int chaleur;

    public CaseTraversable(int lig, int col) {
        super(lig, col);
        traversable = true;
        /* On initialise pas la chaleur ?
        chaleur = 0; */
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

    @Override
    public Case adjacente(Direction d) {
        Case c;
        switch (d) {
            case nord :
                if(lig > 0)
                    c = new CaseTraversable(lig - 1, col);
                else c = this;
                break;
            case sud :
                c = new CaseTraversable(lig + 1, col);
                break;
            case est :
                c = new CaseTraversable(lig, col + 1);
                break;
            case ouest :
                if(col > 0)
                    c = new CaseTraversable(lig, col - 1);
                else c = this;
                break;

            default : c = this; break;
        }
        return c;
    }

}