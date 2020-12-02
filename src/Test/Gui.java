/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Test;

import Command.CommandHandler;
import Strategy.EasyStrategy;
import Strategy.HardStrategy;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import view.VueConteneur;

/**
 * cette classe reprensente le programme principale(JFrame) de notre application
 * @author mac
 */
public class Gui extends JFrame {

    JMenuBar barre;
    public static JButton creationRectagle;
    public static JButton creationCercle;
    public static JButton translation;
    public static JButton deplacement;
    public static JButton suppression;
    public static JButton undo;
    public static JButton redo;
    public static JButton resoudre;
    public static JButton enregistrer;

    public static JComboBox strategie;
    public static JTable tableau;
    public JScrollPane scrollPane;

    public VueConteneur vueConteneur;

    public Gui() {
        this.setLayout(new BoxLayout(this.getContentPane(), 1));

        barre = new JMenuBar();
        barre.setLayout(new GridLayout(1, 10));
        setJMenuBar(barre);
        generateButtons();

        this.tableau = new JTable(8, 2);
        scrollPane = new JScrollPane(tableau);
        scrollPane.setPreferredSize(new Dimension(VueConteneur.largeur, 200));
        scrollPane.setMaximumSize(new Dimension(VueConteneur.largeur, 200));
        vueConteneur = new VueConteneur();
        vueConteneur.setBackground(Color.WHITE);
        this.add(vueConteneur, java.awt.BorderLayout.CENTER);

        this.add(scrollPane);

        this.setTitle("SurfaMax");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        new Gui();
    }
    
    /**
     * cette fonction permet de générer les bouttons du JFrame.
     */
    public void generateButtons() {
        this.creationRectagle = new JButton("Rectangle", new ImageIcon("icons/frame.png"));
        this.barre.add(creationRectagle);
        this.creationRectagle.addActionListener(this::eventCreationRectagle);

        this.creationCercle = new JButton("Cercle", new ImageIcon("icons/circle.png"));
        this.barre.add(creationCercle);
        this.creationCercle.addActionListener(this::eventCreationCercle);

        this.translation = new JButton("Translation", new ImageIcon("icons/double-arrow.png"));
        this.barre.add(translation);
        this.translation.addActionListener(this::eventTranslation);

        this.deplacement = new JButton("Déplacement", new ImageIcon("icons/arrows.png"));
        this.barre.add(deplacement);
        this.deplacement.addActionListener(this::eventDeplacement);

        this.suppression = new JButton("Suppression", new ImageIcon("icons/trash-can.png"));
        this.barre.add(suppression);
        this.suppression.addActionListener(this::eventSuppression);

        this.undo = new JButton("Undo", new ImageIcon("icons/undo.png"));
        this.barre.add(undo);
        this.undo.addActionListener(this::eventUndo);

        this.redo = new JButton("Redo", new ImageIcon("icons/redo.png"));
        this.barre.add(redo);
        this.redo.addActionListener(this::eventRedo);

        this.enregistrer = new JButton("Enregistrer PNG", new ImageIcon("icons/png.png"));
        this.barre.add(enregistrer);
        this.enregistrer.addActionListener(this::eventEnregistrer);

        this.resoudre = new JButton("Solution", new ImageIcon("icons/intelligence-artificielle.png"));
        this.barre.add(resoudre);
        this.resoudre.addActionListener(this::eventResoudre);

        Object[] elements = new Object[]{"Solution aléatoire", "Solution difficile"};
        this.strategie = new JComboBox(elements);
        this.barre.add(this.strategie);

    }
     /**
     * cette methode permet de changer le mode de dessin actuel en mode création rectangle.
     * @param event l'evenement de la souris.
     */
    public void eventCreationRectagle(ActionEvent event) {
        vueConteneur.ETAT = 0;
        vueConteneur.changeEtat(0);
    }
    
    /**
     * cette methode permet de changer le mode de dessin actuel en mode création cercle.
     * @param event l'evenement de la souris.
     */
    public void eventCreationCercle(ActionEvent event) {
        vueConteneur.ETAT = 1;
        vueConteneur.changeEtat(1);
    }
    
    /**
     * cette methode permet de changer le mode de dessin actuel en mode redimensionnement forme.
     * @param event l'evenement de la souris.
     */
    public void eventTranslation(ActionEvent event) {
        vueConteneur.ETAT = 4;
        vueConteneur.changeEtat(4);
    }
    
    /**
     * cette methode permet de changer le mode de dessin actuel en mode deplacement forme.
     * @param event l'evenement de la souris.
     */
    public void eventDeplacement(ActionEvent event) {
        vueConteneur.ETAT = 2;
        vueConteneur.changeEtat(2);
    }
    
    /**
     * cette methode permet de changer le mode de dessin actuel en mode suppression forme.
     * @param event l'evenement de la souris.
     */
    public void eventSuppression(ActionEvent event) {
        vueConteneur.ETAT = 3;
        vueConteneur.changeEtat(3);
    }
    
    /**
     * cette methode permet d'annuler la dernière action réalisée.
     * @param event l'evenement de la souris.
     */
    public void eventUndo(ActionEvent event) {
        CommandHandler commandHandler = this.vueConteneur.getCommandHandler();
        commandHandler.undo();
        this.vueConteneur.modeleMisAjour();
        this.undo.setEnabled(!commandHandler.getStackUndo().isEmpty());
        this.redo.setEnabled(!commandHandler.getStackRedo().isEmpty());
    }
    
     /**
     * cette methode permet de rétablir la dernière action réalisée.
     * @param event l'evenement de la souris.
     */
    public void eventRedo(ActionEvent event) {
        CommandHandler commandHandler = this.vueConteneur.getCommandHandler();
        commandHandler.redo();
        this.vueConteneur.modeleMisAjour();
        this.undo.setEnabled(!commandHandler.getStackUndo().isEmpty());
        this.redo.setEnabled(!commandHandler.getStackRedo().isEmpty());
    }
    /**
     * une fonction qui permet de resoudre selon la strategie difficile ou facile. 
     * @param event l'evenement
     */
    public void eventResoudre(ActionEvent event) {
        if (strategie.getSelectedItem() == "Solution difficile") {
            this.vueConteneur.resoudre(new HardStrategy());
        } else {
            this.vueConteneur.resoudre(new EasyStrategy());
        }

    }
    /**
     * uen fonction qui permet d'enregister le dessin courrant en format png dans le repertoire courrant.
     * @param event l'evenement
     */
    public void eventEnregistrer(ActionEvent event) {
        BufferedImage bi = new BufferedImage(vueConteneur.getSize().width, vueConteneur.getSize().height, BufferedImage.TYPE_INT_ARGB);
        Graphics g = bi.createGraphics();
        vueConteneur.paint(g);  
        g.dispose();
        try {
            ImageIO.write(bi, "png", new File("dessin.png"));
        } catch (Exception e) {
        }
    }

}
