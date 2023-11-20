public class CaseTraversable extends Case{
    private boolean traversable;

    public CaseTraversable(int l, int c, boolean traversable) {
        super(l, c);
        this.traversable = traversable;
    }

    public CaseTraversable(int lig, int col) {
        super(lig, col);
        traversable = true;
    }
    @Override
    public boolean estTraversable(){ return traversable;}
}
