import javax.swing.*;
import java.util.ArrayList;
import java.util.Random;

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
        // à chaque tour, on propage la chaleur à travers les cases :
        for(Case[] c : terrain.getCarte()){
            for(Case cc : c){
                if(cc instanceof Hall){
                    ArrayList<CaseTraversable> voisines = terrain.getVoisinesTraversables(cc.getLig(), cc.getCol());
                    int chaleur = ((CaseTraversable) cc).getChaleur();
                    for(CaseTraversable v : voisines){
                        chaleur += v.getChaleur();
                    }
                    Random rnd = new Random();
                    // nous avons décidé de baisser l'intervalle de génération du nombre aléatoire donné dans le sujet,
                    // pour rendre le jeu un peu plus "challengeant" avec une propagation de flammes plus rapide
                    int aleat = rnd.nextInt(180);
                    // pareil, ci-dessous, nous avons décidé de légèrement dévier des consignes du sujet en considérant aussi l'égalité
                    // et pas seulement l'infériorité avec le total des chaleurs, pour que les cases aient tendance à chauffer plus fréquemment
                    // accélérant ainsi la propagation de chaleur
                    if(aleat <= chaleur) ((CaseTraversable) cc).chauffe();
                    else if(aleat > 170)  ((CaseTraversable) cc).refroidit();
                }
            }
        }
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
