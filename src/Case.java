public abstract class Case {
        public final int lig, col;
        public Case(int l, int c) {
            this.lig = l;
            this.col = c;
        }
        public abstract boolean estTraversable();

        public abstract Case audessus();
        public abstract Case endessous();
        public abstract Case agauche();
        public abstract Case adroite();

}
