public class Porte extends Case {
   private boolean ouverte;
   public Porte(int lig, int col) {
       super(lig, col);
       ouverte = false;
   }

   public Porte(int lig, int col, boolean ouverte) {
       super(lig, col);
       this.ouverte = ouverte;
   }
    @Override
    public boolean estTraversable() {
        return ouverte;
    }

    public void ouvrir() {
        ouverte = true;
    }

    public void fermer() {
       ouverte = false;
    }

    @Override
    public Case audessus() {
        if(col != 0)
            return (new CaseTraversable(this.lig, this.col-1));
        else return this;
    }

    @Override
    public Case agauche() {
        return new CaseTraversable(this.lig, this.col + 1);
    }

    @Override
    public Case endessous() {
        return new CaseTraversable(this.lig + 1, this.col);

    }

    @Override
    public Case adroite() {
        if (lig > 0) return new CaseTraversable(this.lig - 1, this.col);
        else return this;
    }
}
