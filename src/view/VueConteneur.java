/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import Command.CommandHandler;
import State.EtatCreerCercle;
import State.EtatCreerRectangle;
import State.EtatDeplacementForme;
import State.EtatForme;
import State.EtatSuppressionForme;
import State.EtatTranslationForme;
import Strategy.EasyStrategy;
import Strategy.HardStrategy;
import Strategy.SolutionStrategy;
import Test.Gui;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;
import model.Cercle;
import model.ConteneurFormes;
import model.Forme;
import model.Point;
import model.Rectangle;
import util.ConteneurListener;
import util.EcouteurFormes;

/**
 * *Classe représenant notre zone de dessin, c'est à dire l'ensemble des données 
 * permmettant de représenter un mode du jeu lors du fonctionnement de l'application.
 * @author mac
 */
public class VueConteneur extends JPanel implements ConteneurListener, EcouteurFormes, MouseMotionListener, MouseListener {

    private ArrayList<Vue> vues;
    private ConteneurFormes conteneurFormes;
    private Rectangle rectangle;
    private Cercle cercle;
    /**
     *  represente l'etat de notre model
     */
    public static EtatForme etatForme;

  
    public static int ETAT = 1; // Bouton de création rectangle
    private CommandHandler commandHandler;

    /**
     * represente la largueur de notre Jpanel.
     */
    public static int largeur = 1000;

    /**
     * represente la hauteur de notre Jpanel
     */
    public static int hauteur = 400;

    /**
     * le score de la partie en cours.
     */
    public static double score;

    public VueConteneur() {
        this.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
        this.setPreferredSize(new Dimension(1000, 400));
        this.setMaximumSize(new Dimension(1000, 400));
        this.vues = new ArrayList<>();
        this.conteneurFormes = new ConteneurFormes();
        addMouseMotionListener(this);
        addMouseListener(this);
        this.commandHandler = new CommandHandler();
        Gui.undo.setEnabled(!commandHandler.getStackUndo().isEmpty());
        Gui.redo.setEnabled(!commandHandler.getStackRedo().isEmpty());
        tableMAJ();
        generateRandomFormes();

    }
    /**
     * cette methode permet de génerer une solution du jeu en fonction d'une difficulté choisie et créer un nouvelle partie.
     * @param solutionStrategy la strategie voulu
     */
    public void resoudre(SolutionStrategy solutionStrategy) {
        String solution = "";
        if (conteneurFormes.getFormes().size() < 8) {
            JOptionPane.showMessageDialog(this, "vous devez deposer 4 formes !");
        } else {
            score = calculScore();
            removeUsersFormes();
            generateSolution(solutionStrategy);
            double sIA = calculScore();
            String s = "";
            if (score > sIA) {
                s = "Vous avez gagné ! Votre score :" + score + " % le score de l'IA = " + sIA + " %";
            } else {
                s = "Vous avez perdu, Votre score :" + score + " % le score de l'IA = " + sIA + " %";
            }
            s += ", voulez vous rejouer ?";
            JOptionPane pane = new JOptionPane(s);
            JDialog dialog = pane.createDialog(null, "Résulat");
            dialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
            dialog.setVisible(true);

            commandHandler = new CommandHandler();
            Gui.undo.setEnabled(!commandHandler.getStackUndo().isEmpty());
            Gui.redo.setEnabled(!commandHandler.getStackRedo().isEmpty());

            conteneurFormes.getFormes().removeAll(conteneurFormes.getFormes());
            vues.removeAll(vues);
            generateRandomFormes();
        }
    }
    
    /**
     * cette fonction permet de re-initialiser la tentative du joueur en supprimant les formes qu'il avait générées.
     */
    public void removeUsersFormes() {
        for (int i = 7; i >= 4; i--) {
            for (Vue vue : vues) {
                if (vue.getForme() == conteneurFormes.getFormes().get(i)) {
                    vues.remove(vue);
                    break;
                }
            }
            conteneurFormes.getFormes().remove(i);
        }
    }
    
    /**
     * c'est une fonction qui permet de calculer le score du joueur.
     * @return retourne le score sous la forme d'un double 
     */
    public double calculScore() {
        double sc;
        double sum = 0;
        for (int i = 4; i < conteneurFormes.getFormes().size(); i++) {
            sum += conteneurFormes.getFormes().get(i).calculSurface();
        }
        sc = sum / (largeur * hauteur) * 100;
        return sc;
    }
    
    /**
     * cette methode permet de dessier un graphe donné.
     * @param g le graphe a dessiner
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D gr = (Graphics2D) g;
        for (Vue vue : vues) {
            vue.paint(gr);
        }
    }
    /**
     * cette methode remplit notre model de Jtable pour pouvoir afficher les informations des formes générées en temps réel.
     */
    public void tableMAJ() {
        int i = 0;
        DefaultTableModel modelTable = new DefaultTableModel();
        String[] ligne0 = {"forme", "surface"};
        modelTable.setColumnIdentifiers(ligne0);
        for (Forme forme : conteneurFormes.getFormes()) {
            if (forme instanceof Rectangle) {
                String[] ligne = {"Rectangle " + i, String.valueOf(forme.calculSurface())};
                modelTable.addRow(ligne);
            }
            if (forme instanceof Cercle) {
                String[] ligne = {"Cercle " + i, String.valueOf(forme.calculSurface())};
                modelTable.addRow(ligne);
            }
            i++;
        }
        Gui.tableau.setModel(modelTable);
    }

      /**
     * permet d'appler la fonction de génération des 4 formes aléatoires et de mettre a jour le modele.
     */
    public void generateRandomFormes() {
        this.conteneurFormes = generatePremisse();
        for (Forme forme : this.conteneurFormes.getFormes()) {
            if (forme instanceof Rectangle) {
                Rectangle rectangle = (Rectangle) forme;
                vues.add(new RectangleVue(rectangle, Color.red));
            }
            if (forme instanceof Cercle) {
                Cercle cercle = (Cercle) forme;
                vues.add(new CercleVue(cercle, Color.red));
            }
        }
        modeleMisAjour();
    }

     /**
     * c'est la procédure qui permet de générer une solution en fonction d'un niveau de difficulté.
     * @param solutionStrategy represente la classe Stratégie de notre solution(niveau).
     */
    public void generateSolution(SolutionStrategy solutionStrategy) {
        ConteneurFormes sol = solutionStrategy.solution(conteneurFormes);
        conteneurFormes.getFormes().addAll(sol.getFormes());
        for (Forme forme : sol.getFormes()) {
            if (forme instanceof Rectangle) {
                Rectangle rectangle = (Rectangle) forme;
                vues.add(new RectangleVue(rectangle, Color.green));
            }
            if (forme instanceof Cercle) {
                Cercle cercle = (Cercle) forme;
                vues.add(new CercleVue(cercle, Color.green));
            }
        }
        modeleMisAjour();
    }

    /**
     *
     * @return savoir si l'utilisateur a dèja deposé 4 formes
     */
    public boolean quatreFormesDeposee() {
        return this.conteneurFormes.getFormes().size() == 8;
    }
   
    /**
     * c'est le bloque responsable de changer l'etat du Jpanel lors du fonctionnement de notre application.
     * @param i entier qui désigne l'etat actuel.
     */
    public void changeEtat(int i) {
        tableMAJ();
        switch (i) {
            case 0:
                etatForme = new EtatCreerRectangle(this, conteneurFormes);
                break;
            case 1:
                etatForme = new EtatCreerCercle(this, conteneurFormes);
                break;
            case 2:
                etatForme = new EtatDeplacementForme(this, conteneurFormes);
                break;
            case 3:
                etatForme = new EtatSuppressionForme(this, conteneurFormes);
                break;
            case 4:
                etatForme = new EtatTranslationForme(this, conteneurFormes);
                break;
            default:
        }
    }
    
    /**
     * generatePremisse permet de générer les 4 formes aléatoires. Elle fait appel a chaque fois a la methode de généeration d'une forme aléatoire.
     * @return un conteneur forme qui contient les 4 formes aléatoires crées.
     */
    public static ConteneurFormes generatePremisse() {
        ConteneurFormes premisse = new ConteneurFormes();
        for (int i = 0; i < 4; i++) {
            int bool = (int) (Math.random() * 2);
            if (bool == 0) {
                premisse.add(generateRectangle(premisse, 50, 300));
            } else {
                premisse.add(generateCercle(premisse, 50, 300));
            }

        }
        return premisse;
    }
    
    /**
     * qui permet de générer un rectangle aléatoire dans un intervalle donné
     * @param premisse c'est le conteneur des formes
     * @param min taille minimale de la largeur ou hauteur.
     * @param max taille maximale de la largeur ou la hauteur.
     * @return retourne un rectangle
     */
    public static Forme generateRectangle(ConteneurFormes premisse, double min, double max) {
        Rectangle rectangle;
        do {
            Random r = new Random();
            double largeur = min + (max - min) * r.nextDouble();
            double hauteur = min + (max - min) * r.nextDouble();
            double x = VueConteneur.largeur * r.nextDouble();
            double y = VueConteneur.hauteur * r.nextDouble();
            rectangle = new Rectangle(new Point(x, y), largeur, hauteur);
        } while (rectangle.collision(premisse) || !rectangle.formeValidee());
        return rectangle;
    }

    public static Forme generateCercle(ConteneurFormes premisse, double min, double max) {
        Cercle cercle;
        do {
            Random r = new Random();
            double rayon = min + (max - min) * r.nextDouble();
            double x = VueConteneur.largeur * r.nextDouble();
            double y = VueConteneur.hauteur * r.nextDouble();
            cercle = new Cercle(new Point(x, y), rayon);
        } while (cercle.collision(premisse) || !cercle.formeValidee());
        return cercle;
    }

    public ConteneurFormes getConteneurFormes() {
        return conteneurFormes;
    }
    
    /**
     * une procedure qui permet d'ajouter un conteneur des formes et d'ecraser le dernier.  
     * @param conteneurFormes le conteneur des formes qu'on veut ajouter. 
     */
    public void setConteneurFormes(ConteneurFormes conteneurFormes) {
        this.conteneurFormes = conteneurFormes;
    }

    public void addVue(Vue vue) {
        vues.add(vue);
    }

    public void removeVue(int i) {
        vues.remove(i);
    }

    public void removeVue(Vue vue) {
        vues.remove(vue);
    }

    @Override
    public void formeAjoutee(Forme forme) {
        this.conteneurFormes.add(forme);
    }

    @Override
    public void formeRetiree(Forme forme) {
        this.conteneurFormes.remove(forme);
    }
    
    /**
     * cette methode permet de mettre a jour notre model.
     * c'est a dire d'appeler les methodes qui permettent de redessiner l'ensemble des composants et de mettre a jour la table qui regroupe les informations sur les formes.
     */
    @Override
    public void modeleMisAjour() {
        repaint();
        tableMAJ();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        etatForme.mouseDragged(e);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        //
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        //etatForme = new EtatSuppressionForme(this, conteneurFormes);
        etatForme.mouseClicked(e);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        etatForme.mousePressed(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        etatForme.mouseReleased(e);
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        //
    }

    @Override
    public void mouseExited(MouseEvent e) {
        //
    }

    public ArrayList<Vue> getVues() {
        return vues;
    }

    public void setVues(ArrayList<Vue> vues) {
        this.vues = vues;
    }

    public CommandHandler getCommandHandler() {
        return commandHandler;
    }

}
