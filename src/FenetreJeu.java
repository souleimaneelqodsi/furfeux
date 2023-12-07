import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.security.Key;

public class FenetreJeu extends JPanel implements KeyListener{
    private Terrain terrain;
    final  private int tailleCase = 36;
    final private int hauteur, largeur;
    final private JFrame frame;

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

        this.addKeyListener(this);
        this.setFocusable(true);
    }




    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        int rayonCercleJoueur = tailleCase;
        //int tailleCleLargeur = 20 ;
        //int tailleCleHauteur = 10 ;
        CaseTraversable caseJ = terrain.getJoueur().getPos();
        int posJX = caseJ.getCol() * tailleCase + largeur/2 * (tailleCase - 10);
        int posJY = caseJ.getLig() * tailleCase + hauteur/2 * (tailleCase - 10) ;
        Case[][] carte = terrain.getCarte();
        for (int i = terrain.getHauteur() - 1; i >= 0 ; i--) {
            for (int j = terrain.getLargeur() - 1; j >= 0; j--) {
                Case caseActuelle = carte[i][j];
                int X = largeur/2 * (tailleCase - 10) + j * tailleCase;
                int Y = hauteur/2 * (tailleCase - 10) + i * tailleCase;
                if (caseActuelle.equals(caseJ)) {
                    g.setColor(Color.gray);
                    g.fillOval(X, Y, rayonCercleJoueur, rayonCercleJoueur);
                } else {
                    /*double calcX = (double) (posJX) - (double) (X);
                    double calcY = (double) (posJY) - (double) (Y);
                    double calcX2 = Math.pow(calcX, 2.0);
                    double calcY2 = Math.pow(calcY, 2.0);
                    double diagonale = Math.sqrt(2.0) * (double) (tailleCase);
                    double nbreCases = 20.0;
                    boolean distEquation = calcX2 + calcY2 <= diagonale * nbreCases;*/
                    // Math.abs(posJX - j) + Math.abs(posJY - i);
                    //int distManhattan = Math.abs(caseJ.getCol() - j) + Math.abs(caseJ.getLig() - i);
                    if(Math.pow(caseJ.getCol() - j, 2) + Math.pow(caseJ.getLig() - i, 2) <= 10* Math.sqrt(2))  {
                    //if(Math.pow(caseJ.getCol() - j, 2) + Math.pow(caseJ.getLig() - i, 2) <= 10)
                        if (caseActuelle instanceof Porte) {
                            g.setColor(Color.green);
                            g.fillRect(X, Y, tailleCase, tailleCase);
                        } else if (caseActuelle instanceof Sortie) {
                            g.setColor(Color.blue);
                            g.fillRect(X, Y, tailleCase, tailleCase);
                        } else if (caseActuelle instanceof Mur) {
                            g.setColor(Color.black);
                            g.fillRect(X, Y, tailleCase, tailleCase);
                        } else if (caseActuelle instanceof Hall) {
                            if (((Hall) caseActuelle).getChaleur() > 0)
                                g.setColor(new Color(255, 255 - 100 + (((Hall) caseActuelle).getChaleur() * 10), 255 - 100 + (((Hall) caseActuelle).getChaleur() * 10)));
                            else if (((Hall) caseActuelle).testCle() ) {
                                g.setColor(Color.gray);
                                g.fillRect(X + (tailleCase / 2), Y + (tailleCase / 2), 20, 10);
                            } else g.setColor(Color.white);
                            g.fillRect(X, Y, tailleCase, tailleCase);
                        }
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

    // test:
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Terrain t = new Terrain("manoir.txt");
            FenetreJeu f = new FenetreJeu(t);
            f.repaint();
        });

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();
        switch (keyCode) {
            case KeyEvent.VK_UP:
                //deplacerJoueur(-1, 0);
                terrain.getJoueur().bouge(terrain.getJoueur().getPos().adjacente(Direction.nord));
                break;
            case KeyEvent.VK_DOWN:
                deplacerJoueur(1, 0);
                break;
            case KeyEvent.VK_LEFT:
                deplacerJoueur(0, -1);
                break;
            case KeyEvent.VK_RIGHT:
                deplacerJoueur(0, 1);
                break;
        }
        frame.repaint(); // Refresh
    }
    private void deplacerJoueur(int lig, int col) {
        CaseTraversable caseActuelle = terrain.getJoueur().getPos();

        // nouvelles coordonnées
        int newLig = caseActuelle.getLig() + lig;
        int newCol = caseActuelle.getCol() + col;

        // check limites du terrain
        if (newLig >= 0 && newLig < hauteur && newCol >= 0 && newCol < largeur) {
            Case nouvelleCase = terrain.getCarte()[newLig][newCol];

            // Vérification si la nouvelle case est traversable
            if (nouvelleCase.estTraversable()) {
                // Déplacement du joueur vers la nouvelle case
                terrain.getJoueur().bouge(nouvelleCase);
            }
        }
    }
}

