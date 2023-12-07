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
