
// classe abstraite modélisant une case quelconque dans la grille du jeu
public abstract class Case {
        public final int lig, col;
        public Case(int l, int c) {
            this.lig = l;
            this.col = c;
        }
        public abstract boolean estTraversable();
        public abstract int getLig();
        public abstract int getCol();
}
