import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class FenetreJeu extends JPanel{
    private Terrain terrain;
    private int tailleCase = 36;
    private int hauteur, largeur;
    private JFrame frame;

    public FenetreJeu(Terrain t) {
        this.hauteur = t.getHauteur();
        this.largeur = t.getLargeur();
        this.terrain = t;

        setBackground(Color.LIGHT_GRAY);
        setPreferredSize(new Dimension(9 * tailleCase, 9 * tailleCase));

        JFrame frame = new JFrame("Furfeux");
        this.frame = frame;
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(this);
        frame.pack();
        frame.setVisible(true);
    }
    /*public void keyTyped(KeyEvent e) {

    }
    public void keyPressed(KeyEvent e) {

    }
    public void keyReleased(KeyEvent e) {

    }*/

    @Override
    public void paintComponent(Graphics g) {
        int taille = tailleCase/9;
        super.paintComponent(g);
        int rayonCercleJoueur = 8;
        //int tailleCle = 20;
        CaseTraversable caseJ = terrain.getJoueur().getPos();
        int posJX = caseJ.getLig();
        int posJY = caseJ.getCol();
        Case[][] carte = terrain.getCarte();
        /*for(int i = carte.length - 1; i >=0; i--){
            for(int j = carte[i].length - 1; j >=0; j--){*/
        for(int i = 0; i < carte.length;i++){
            for(int j = 0; j < carte[i].length;j++){
                Case caseActuelle = carte[i][j];
                if(caseActuelle.equals(caseJ)) {
                    g.setColor(Color.gray);
                    g.fillOval(j+9, i+9, rayonCercleJoueur, rayonCercleJoueur);}
                else {//if (Math.pow((posJX - j), 2) + Math.pow((posJY - i), 2) <= 10) {
                    if(caseActuelle instanceof Porte) {
                        g.setColor(Color.green);
                        g.fillRect(j+9, i+9, taille, taille);
                    }
                    else if(caseActuelle instanceof Sortie) {
                        g.setColor(Color.green);
                        g.fillRect(j+9, i+9, taille, taille);
                    }

                    else if(caseActuelle instanceof Mur){
                        g.setColor(Color.black);
                        g.fillRect(j+9, i+9, taille, taille);
                    }
                    else if(caseActuelle instanceof Hall){
                        if(((Hall) caseActuelle).getChaleur() > 0)
                        g.setColor(new Color(255, 255-100+(((Hall) caseActuelle).getChaleur()*10), 255-100+(((Hall) caseActuelle).getChaleur()*10)));
                        else g.setColor(Color.white);
                        g.fillRect(j+9, i+9, taille, taille);
                    }
                }
            }
        }
    }

    public void ecranFinal(int n) {
        frame.remove(this);
        JLabel label = new JLabel("Score " + n);
        label.setFont(new Font("Verdana", 1, 20));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setSize(this.getSize());
        frame.getContentPane().add(label);
        frame.repaint();
    }

    public static void main(String args[]){
        Terrain t = new Terrain("manoir.txt");
        FenetreJeu f = new FenetreJeu(t);
        f.repaint();
    }

}

