public class CaseTraversable extends Case {
    boolean traversable;
    int chaleur;

    public CaseTraversable(int lig, int col) {
        super(lig, col);
        traversable = true;
        chaleur = 0;
    }

    public int getChaleur() {
        return chaleur;
    }

    @Override
    public int getLig() {
        return lig;
    }

    @Override
    public int getCol() {
        return col;
    }

    @Override
    public boolean estTraversable() {
        return traversable;
    }

    //chauffe la case de 1 unité de chaleur
    public void chauffe(){
        // pour ne pas déborder de 10 :
        chaleur = Math.min(10, chaleur+1);
    }
    // refroidit la case de 1 unité de chaleur
    public void refroidit(){
        // pour ne pas descendre en-dessous de 0 :
        chaleur = Math.max(0, chaleur-1);
    }

}