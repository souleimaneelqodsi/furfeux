public class CaseIntraversable extends CaseTraversable {

    public CaseIntraversable(int lig, int col) {
        super(lig, col);
        // par définition, intraversable
        this.traversable = false;
        // une case traversable ne peut pas chauffer
        this.chaleur = 0;
    }
}
