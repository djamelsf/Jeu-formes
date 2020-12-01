/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Test;

import Command.CommandHandler;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import view.VueConteneur;

/**
 *
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

    public static JTable tableau;
    public JScrollPane scrollPane;

    public VueConteneur vueConteneur;

    public Gui() {
        //this.setSize(1800, 800);
        this.setLayout(new BoxLayout(this.getContentPane(), 1));

        barre = new JMenuBar();
        barre.setLayout(new GridLayout(1, 8));
        setJMenuBar(barre);
        generateButtons();

        this.tableau = new JTable(8, 2);
        scrollPane = new JScrollPane(tableau);
        //        JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
        //        JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setPreferredSize(new Dimension(VueConteneur.largeur, 200));
        scrollPane.setMaximumSize(new Dimension(VueConteneur.largeur, 200));
        vueConteneur = new VueConteneur();
        vueConteneur.setBackground(Color.WHITE);
        this.add(vueConteneur, java.awt.BorderLayout.CENTER);

        this.add(scrollPane);

        this.setTitle("SurfaMax !");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        new Gui();
    }

    public void generateButtons() {
        this.creationRectagle = new JButton("Rectangle",new ImageIcon("icons/frame.png"));
        this.barre.add(creationRectagle);
        this.creationRectagle.addActionListener(this::eventCreationRectagle);

        this.creationCercle = new JButton("Cercle", new ImageIcon("icons/circle.png"));
        this.barre.add(creationCercle);
        this.creationCercle.addActionListener(this::eventCreationCercle);

        this.translation = new JButton("Translation", new ImageIcon("icons/double-arrow.png"));
        this.barre.add(translation);
        this.translation.addActionListener(this::eventTranslation);

        this.deplacement = new JButton("Deplacement",new ImageIcon("icons/arrows.png"));
        this.barre.add(deplacement);
        this.deplacement.addActionListener(this::eventDeplacement);

        this.suppression = new JButton("Suppression",new ImageIcon("icons/trash-can.png"));
        this.barre.add(suppression);
        this.suppression.addActionListener(this::eventSuppression);

        this.undo = new JButton("undo",new ImageIcon("icons/undo.png"));
        this.barre.add(undo);
        this.undo.addActionListener(this::eventUndo);

        this.redo = new JButton("redo",new ImageIcon("icons/redo.png"));
        this.barre.add(redo);
        this.redo.addActionListener(this::eventRedo);

        this.resoudre = new JButton("Resoudre");
        this.barre.add(resoudre);
        this.resoudre.addActionListener(this::eventResoudre);

    }

    public void eventCreationRectagle(ActionEvent event) {
        vueConteneur.ETAT = 0;
        vueConteneur.changeEtat(0);
    }

    public void eventCreationCercle(ActionEvent event) {
        vueConteneur.ETAT = 1;
        vueConteneur.changeEtat(1);
    }

    public void eventTranslation(ActionEvent event) {
        vueConteneur.ETAT = 4;
        vueConteneur.changeEtat(4);
    }

    public void eventDeplacement(ActionEvent event) {
        vueConteneur.ETAT = 2;
        vueConteneur.changeEtat(2);
    }

    public void eventSuppression(ActionEvent event) {
        vueConteneur.ETAT = 3;
        vueConteneur.changeEtat(3);
    }

    public void eventUndo(ActionEvent event) {
        CommandHandler commandHandler = this.vueConteneur.getCommandHandler();
        commandHandler.undo();
        this.vueConteneur.modeleMisAjour();
        this.undo.setEnabled(!commandHandler.getStackUndo().isEmpty());
        this.redo.setEnabled(!commandHandler.getStackRedo().isEmpty());
    }

    public void eventRedo(ActionEvent event) {
        CommandHandler commandHandler = this.vueConteneur.getCommandHandler();
        commandHandler.redo();
        this.vueConteneur.modeleMisAjour();
        this.undo.setEnabled(!commandHandler.getStackUndo().isEmpty());
        this.redo.setEnabled(!commandHandler.getStackRedo().isEmpty());
    }

    public void eventResoudre(ActionEvent event) {
        this.vueConteneur.resoudre();
    }

}
