public class CaseTraversable extends Case {
    boolean traversable;
    int chaleur;

    public CaseTraversable(int lig, int col) {
        super(lig, col);
        traversable = true;
    }

    public int getChaleur() {
        return chaleur;
    }

    @Override
    public Case agauche() {
        if(col != 0)
           return (new CaseTraversable(this.lig, this.col-1));
        else return this;
    }

    @Override
    public Case adroite() {
        return new CaseTraversable(this.lig, this.col + 1);
    }

    @Override
    public Case endessous() {
        return new CaseTraversable(this.lig+1, this.col);
    }

    @Override
    public Case audessus() {
        if (lig > 0) return new CaseTraversable(this.lig - 1, this.col);
        else return this;
    }

    public boolean estTraversable(){return true;}
}