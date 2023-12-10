import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class FenetreJeu extends JPanel implements KeyListener{

    //TODO: rapport : amélioration
    //TODO: rajouter commentaires méthodes
    //TODO: barre de PV
    //TODO: affichage du score en temps réel
    //TODO: (menu) recommencer la partie et augmenter la difficulté (moins de PV et propagation plus violente du feu)
    //TODO: rajouter une bande sonore

    private JButton pvButton;
    // ce bouton ci-dessus servira à afficher les points de vie (ou résistance) actuels du joueur
    private Terrain terrain;
    final  private int tailleCase = 36;
    final private int hauteur, largeur;
    final private JFrame frame;
    final private Image imageBrique; // image d'un mur
    final private Image imagePorteFermee; // image de porte fermée
    final private Image imagePorteOuverte; // image d'une porte ouverte
    final private Image imageHall; // image de Hall
    final private Image imageCle; // image d'une clé
    final private Image imageSortie; // image d'une sortie
    final private Image imageJoueur; // image d'un joueur
    final private ArrayList<Image> imagesHallChaleur;

    public FenetreJeu(Terrain t) {

        this.hauteur = t.getHauteur();
        this.largeur = t.getLargeur();
        this.terrain = t;

        setBackground(Color.black);
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

        MediaTracker tracker = new MediaTracker(this); //priorise l'affichage des images au paintComponent
        imageBrique = new ImageIcon("brique.png").getImage();
        imagePorteFermee = new ImageIcon("porte_fermee.png").getImage();
        imagePorteOuverte = new ImageIcon("porte_ouverte.png").getImage();
        imageHall = new ImageIcon("hall.jpg").getImage();
        imageCle = new ImageIcon("cle.png").getImage();
        imageJoueur = new ImageIcon("perso.png").getImage();
        imageSortie = new ImageIcon("sortie.gif").getImage();
        imagesHallChaleur = new ArrayList<>();

        // on charge les différentes images dans un tableau d'images représentant chacune
        // un niveau de chaleur différent. On stocke les images aux indices de sorte que les indices plus hauts
        //contiennent les images de plus forte chaleur

        for(int i = 1; i <= 10; i++){
            Image newImg = new ImageIcon(i + ".jpg").getImage();
            imagesHallChaleur.add(newImg);
            tracker.addImage(newImg, i + 6);
        }

        tracker.addImage(imageBrique, 0);
        tracker.addImage(imagePorteFermee, 1);
        tracker.addImage(imageHall, 2);
        tracker.addImage(imageCle, 3);
        tracker.addImage(imageJoueur, 4);
        tracker.addImage(imageSortie, 5);
        tracker.addImage(imagePorteOuverte, 6);

        try {
            tracker.waitForAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

       if (tracker.isErrorAny()) {
            System.err.println("Erreur de chargement d'une ou plusieurs images.");
        }
    }

    /*void pvJoueur(){
            pvButton.setText("PV : " + terrain.getJoueur().getResistance() + " | Clé(s) : " + terrain.getJoueur().getCles());
            frame.repaint();
    }*/

    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);

        int tailleCle = 10;
        CaseTraversable caseJ = terrain.getJoueur().getPos();
        Case[][] carte = terrain.getCarte();

        // L'usage de boucles de décrémentation sert à ne pas afficher le terrain à l'envers
        for (int lig = terrain.getHauteur() - 1; lig >= 0; lig--) {
            for (int col = terrain.getLargeur() - 1; col >= 0; col--) {
                Case caseActuelle = carte[lig][col];

                // Mise à l'échelle des indices de case par rapport aux pixels
                // On essaie de tout centrer en décalant à chaque fois ces derniers de la moitié des dimensions du tableau

                int X = ((largeur / 3) * tailleCase) + (col * tailleCase);
                int Y = ((hauteur / 3) * tailleCase) + (lig * tailleCase);

                    /*
                    Vérification de la visibilité basée sur la position du joueur
                    On effectue le calcul de distance sur les indices dans le tableau 2D de Terrain
                    Ainsi, on considère chaque case comme un carré de taille 1 (car chaque case n'occupe qu'une place dans le tableau 2D)
                    Donc, diagonale d'une case = √2 * 1 et cercle de cases autour du joueur (rayon) = (x - x')^2 + (y - y')^2 <= N * √2 * 1
                    où N est le nombre de cases que l'on souhaite donner au joueur comme rayon de visibilité (en l'occurrence 6 semble être adéquat)
                    */

                if (Math.pow(caseJ.getCol() - col, 2) + Math.pow(caseJ.getLig() - lig, 2) <= 6 * Math.sqrt(2)) {
                    // Traitement des différents types de cases
                    if (caseActuelle instanceof Porte) {
                        // Dessiner une porte
                        if (caseActuelle.estTraversable()) {
                            g.drawImage(imagePorteOuverte, X, Y, tailleCase, tailleCase, this);
                        } else {
                            g.drawImage(imagePorteFermee, X, Y, tailleCase, tailleCase, this);
                        }
                    } else if (caseActuelle instanceof Sortie) {
                        // Dessiner une sortie
                        g.drawImage(imageSortie, X, Y, tailleCase, tailleCase, this);
                    } else if (caseActuelle instanceof Mur) {
                        // Dessiner un mur
                        g.drawImage(imageBrique, X, Y, tailleCase, tailleCase, this);
                    } else if (caseActuelle instanceof Hall) {
                        Hall hallActuel = (Hall) caseActuelle;
                            int cleX = X + (tailleCase - tailleCle) / 2;
                            int cleY = Y + (tailleCase - tailleCle) / 2;
                            for(int i = 1; i <= 10 ; i++){
                                if (hallActuel.getChaleur() == i) {
                                    g.drawImage(imagesHallChaleur.get(i-1), X, Y, tailleCase, tailleCase, this);
                                }
                            }
                            if(hallActuel.getChaleur() == 0) g.drawImage(imageHall, X, Y, tailleCase, tailleCase, this);
                            if(hallActuel.testCle()) g.drawImage(imageCle, cleX, cleY, tailleCle, tailleCle, this);
                        }
                    }
                    if (caseActuelle.equals(caseJ)) {
                        // Dessiner le joueur
                        g.drawImage(imageJoueur, X, Y, tailleCase, tailleCase, this);
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
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {}

    @Override
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
        //pvJoueur();
    }

}

