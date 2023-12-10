public class Sortie extends CaseTraversable{
    public Sortie(int lig, int col, int chaleur) {
        super(lig, col);
        if(chaleur >= 0 && chaleur <= 10) this.chaleur = chaleur;
    }
}
