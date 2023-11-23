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
}
