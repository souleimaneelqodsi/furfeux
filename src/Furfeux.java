import javax.swing.*;

public class Furfeux {

    Terrain terrain;
    Joueur joueur;

    public Furfeux(String f) {
        this.terrain = new Terrain(f);
        this.joueur = terrain.getJoueur();
    }

    public void tour() {
        // à chaque tour, le joueur perd 10 pts de vie + la chaleur de la case sur laquelle il se trouve
        joueur.baisseResistance(10 + joueur.getPos().getChaleur());
        //TODO: augmentation de la propagation des flammes
    }

    public boolean partieFinie() {
       return (joueur.getResistance() <= 0 || joueur.getPos() instanceof Sortie);
    }

    public static void main(String[] args) {
        int tempo = 1000; // 1 seconde (1000ms) convient
        Furfeux jeu = new Furfeux("manoir.txt");
        FenetreJeu graphic = new FenetreJeu(jeu.terrain);
        Timer timer = new Timer(tempo,
            e -> {
                jeu.tour();
                graphic.repaint();
                if (jeu.partieFinie()) {
                    graphic.ecranFinal(Math.max(0, jeu.joueur.getResistance()));
                    ((Timer)e.getSource()).stop();
                }
            }
        );
        timer.start();
    }
}
