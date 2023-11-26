import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class FenetreJeu extends JPanel implements KeyListener{
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
    public void keyTyped(KeyEvent e) {

    }
    public void keyPressed(KeyEvent e) {

    }
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void paintComponent(Graphics g) {
        int rayonCercleJoueur = 25;
        int tailleCle = 20;
        CaseTraversable caseJ = terrain.getJoueur().getPos();
        int posJX = caseJ.getLig();
        int posJY = caseJ.getCol();
        Case[][] carte = terrain.getCarte();
        for(int i = 0; i < carte.length; i++){
            for(int j = 0; j < carte[i].length; j++){
                Case caseActuelle = carte[i][j];
                if(caseActuelle.equals(caseJ)) g.drawOval(i, j, rayonCercleJoueur, rayonCercleJoueur);
                else if (Math.pow((posJX - i), 2) + Math.pow((posJY - j), 2) <= 10) {
                    if(caseActuelle instanceof Porte) {
                        g.setColor(Color.green);
                        g.drawRect(i, j, tailleCase, tailleCase);
                    }
                    else if(caseActuelle instanceof Sortie) {}
                    else if(caseActuelle instanceof Mur){
                        g.setColor(Color.black);
                        g.drawRect(i, j, tailleCase, tailleCase);
                    }
                    else if(caseActuelle instanceof Hall){
                        g.setColor(new Color(255-((Hall) caseActuelle).getChaleur()*10, 255, 255));
                        g.drawRect(i, j, tailleCase, tailleCase);
                    }
                }
            }
        }
        super.paintComponent(g);
        /* À compléter */    }

    public void ecranFinal(int n) {
        frame.remove(this);
        JLabel label = new JLabel("Score " + n);
        label.setFont(new Font("Verdana", 1, 20));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setSize(this.getSize());
        frame.getContentPane().add(label);
        frame.repaint();
    }
}

