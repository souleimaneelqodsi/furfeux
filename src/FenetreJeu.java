import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.security.Key;

public class FenetreJeu extends JPanel implements KeyListener{

    //TODO: propagation des flammes
    //TODO: rapport (difficultés rencontrées, progression, éléments ratés ou qu'on aurait aimé faire)
    //TODO: barre de PV
    //TODO: affichage du score en temps réel
    //TODO: (menu) recommencer la partie
    //TODO: créer de nouvelles maps

    private JButton pvButton;
    // ce bouton ci-dessus servira à afficher les points de vie (ou résistance) actuels du joueur
    private Terrain terrain;
    final  private int tailleCase = 36;
    final private int hauteur, largeur;
    final private JFrame frame;

    public FenetreJeu(Terrain t) {
        this.hauteur = t.getHauteur();
        this.largeur = t.getLargeur();
        this.terrain = t;

        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(1920, 1080));

        JFrame frame = new JFrame("Furfeux");
        this.frame = frame;
        this.addKeyListener(this);
        this.setFocusable(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(this);
        pvButton = new JButton("PV : " + terrain.getJoueur().getResistance()  + " | Clé(s) : " + terrain.getJoueur().getCles());
        frame.getContentPane().add(pvButton, BorderLayout.SOUTH);
        frame.pack();
        frame.setVisible(true);
        this.requestFocusInWindow();
    }

    void pvJoueur(){
            pvButton.setText("PV : " + terrain.getJoueur().getResistance() + " | Clé(s) : " + terrain.getJoueur().getCles());
            frame.repaint();
    }


    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        int rayonCercleJoueur = tailleCase;
        int tailleCleLargeur = 20;
        int tailleCleHauteur = 10;
        CaseTraversable caseJ = terrain.getJoueur().getPos();
        Case[][] carte = terrain.getCarte();

        // L'usage de boucles de décrémentation sert à ne pas afficher le terrain à l'envers
        for (int i = terrain.getHauteur() - 1; i >= 0; i--) {
            for (int j = terrain.getLargeur() - 1; j >= 0; j--) {
                Case caseActuelle = carte[i][j];
                // Mise à l'échelle des indices de case par rapport aux pixels
                // On essaie de tout centrer en décalant à chaque fois ces derniers de la moitié des dimensions du tableau
                int X = ((largeur / 2) * tailleCase) + (j * tailleCase);
                int Y = ((hauteur / 2) * tailleCase) + (i * tailleCase);

                    // Vérification de la visibilité basée sur la position du joueur
                    // On effectue le calcul de distance sur les indices dans le tableau 2D de Terrain
                    // Ainsi, on considère chaque case comme un carré de taille 1 (car chaque case n'occupe qu'une place dans le tableau 2D)
                    // Donc, diagonale d'une case = √2 * 1 et cercle de cases autour du joueur (rayon) = (x - x')^2 + (y - y')^2 <= N * √2 * 1
                    // où N est le nombre de cases que l'on souhaite donner au joueur comme rayon de visibilité (en l'occurrence 6 semble être adéquat)
                if (Math.pow(caseJ.getCol() - j, 2) + Math.pow(caseJ.getLig() - i, 2) <= 6 * Math.sqrt(2)) {
                    // Traitement des différents types de cases
                    if (caseActuelle instanceof Porte) {
                        // Dessiner une porte
                        if (caseActuelle.estTraversable()) {
                            g.setColor(Color.black);
                            g.drawRect(X, Y, tailleCase, tailleCase);
                        } else {
                            g.setColor(Color.green);
                            g.fillRect(X, Y, tailleCase, tailleCase);
                        }
                    } else if (caseActuelle instanceof Sortie) {
                        // Dessiner une sortie
                        g.setColor(Color.blue);
                        g.fillRect(X, Y, tailleCase, tailleCase);
                    } else if (caseActuelle instanceof Mur) {
                        // Dessiner un mur
                        g.setColor(Color.black);
                        g.fillRect(X, Y, tailleCase, tailleCase);
                    } else if (caseActuelle instanceof Hall) {
                        // Dessiner un hall avec gestion de la chaleur
                        Hall hallActuel = (Hall) caseActuelle;
                        if (hallActuel.getChaleur() > 0) {
                            g.setColor(new Color(255, 255 - (50 + (hallActuel.getChaleur() * 10)), 255 - (50 + (hallActuel.getChaleur() * 10))));
                        } else {
                            g.setColor(Color.white);
                        }
                        g.fillRect(X, Y, tailleCase, tailleCase);

                        // Dessiner une clé si présente
                        if (hallActuel.testCle()) {
                            // centrage de la clé au milieu de la case du Hall
                            int cleX = X + (tailleCase - tailleCleLargeur) / 2;
                            int cleY = Y + (tailleCase - tailleCleHauteur) / 2;
                            g.setColor(Color.gray);
                            g.fillRect(cleX, cleY, tailleCleLargeur, tailleCleHauteur);
                            }
                        }
                    }
                    if (caseActuelle.equals(caseJ)) {
                        // Dessiner le joueur
                        g.setColor(Color.gray);
                        g.fillOval(X, Y, rayonCercleJoueur, rayonCercleJoueur);
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

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();
        switch (keyCode) {
            case KeyEvent.VK_UP: case KeyEvent.VK_Z:
                terrain.deplacerJoueur(Direction.nord);
                break;
            case KeyEvent.VK_DOWN: case KeyEvent.VK_S:
                terrain.deplacerJoueur(Direction.sud);
                break;
            case KeyEvent.VK_LEFT: case KeyEvent.VK_Q:
                terrain.deplacerJoueur(Direction.ouest);
                break;
            case KeyEvent.VK_RIGHT: case KeyEvent.VK_D:
                terrain.deplacerJoueur(Direction.est);
                break;
            default: break;
        }
        frame.repaint();
        pvJoueur();
    }

}

