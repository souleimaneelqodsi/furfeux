public class Porte extends CaseTraversable {

    // Porte est une classe fille de CaseTraversable
    // ce qui est pratique car elle hérite d'un attribut booléan
    // 'traversable' qui va permettre de déterminer si la porte est ouverte ou non

   public Porte(int lig, int col) {
       super(lig, col);
   }
   public Porte(int lig, int col, boolean b){
       super(lig, col);
       traversable = b;
   }

    public void ouvrir() {
        traversable = true;
    }
}
