public class CaseTraversable extends Case{
    boolean traversable;

    public CaseTraversable(int lig, int col) {
        super(lig, col);
        traversable = true;
    }
    @Override
    public boolean estTraversable(){ return traversable;}
}
