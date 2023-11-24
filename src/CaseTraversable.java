public class CaseTraversable extends Case {
    boolean traversable;
    int chaleur;

    public CaseTraversable(int lig, int col) {
        super(lig, col);
        traversable = true;
    }

    @Override
    public Case agauche() {
        if(col != 0)
           return this(this.lig,this.col-1);
        else return this;
    }

    @Override
    public Case adroite() {
        return null;
    }

    @Override
    public Case endessous() {
        return null;
    }

    @Override
    public Case audessus() {
        return null;
    }
}