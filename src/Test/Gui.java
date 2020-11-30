/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Test;

import Command.CommandHandler;
import java.awt.Color;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import javax.swing.BoxLayout;
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

    public static JTable tableau;
    public JScrollPane scrollPane;

    public VueConteneur vueConteneur;

    public Gui() {
        //this.setSize(1800, 800);
        this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));

        barre = new JMenuBar();
        setJMenuBar(barre);
        generateButtons();

        this.tableau = new JTable(8, 2);
        scrollPane = new JScrollPane(tableau,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        
        
        vueConteneur = new VueConteneur();
        vueConteneur.setBackground(Color.black);
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
        this.creationRectagle = new JButton("Rectangle");
        this.barre.add(creationRectagle);
        this.creationRectagle.addActionListener(this::EventCreationRectagle);

        this.creationCercle = new JButton("Cercle");
        this.barre.add(creationCercle);
        this.creationCercle.addActionListener(this::EventCreationCercle);

        this.translation = new JButton("translation");
        this.barre.add(translation);
        this.translation.addActionListener(this::EventTranslation);

        this.deplacement = new JButton("deplacement");
        this.barre.add(deplacement);
        this.deplacement.addActionListener(this::EventDeplacement);

        this.suppression = new JButton("suppression");
        this.barre.add(suppression);
        this.suppression.addActionListener(this::EventSuppression);

        this.undo = new JButton("undo");
        this.barre.add(undo);
        this.undo.addActionListener(this::EventUndo);

        this.redo = new JButton("redo");
        this.barre.add(redo);
        this.redo.addActionListener(this::EventRedo);

    }

    public void EventCreationRectagle(ActionEvent event) {
        vueConteneur.ETAT = 0;
        vueConteneur.changeEtat(0);
    }

    public void EventCreationCercle(ActionEvent event) {
        vueConteneur.ETAT = 1;
        vueConteneur.changeEtat(1);
    }

    public void EventTranslation(ActionEvent event) {
        vueConteneur.ETAT = 4;
        vueConteneur.changeEtat(4);
    }

    public void EventDeplacement(ActionEvent event) {
        vueConteneur.ETAT = 2;
        vueConteneur.changeEtat(2);
    }

    public void EventSuppression(ActionEvent event) {
        vueConteneur.ETAT = 3;
        vueConteneur.changeEtat(3);
    }

    public void EventUndo(ActionEvent event) {
        CommandHandler commandHandler = this.vueConteneur.getCommandHandler();
        commandHandler.undo();
        this.vueConteneur.modeleMisAjour();
        this.undo.setEnabled(!commandHandler.getStackUndo().isEmpty());
        this.redo.setEnabled(!commandHandler.getStackRedo().isEmpty());
    }

    public void EventRedo(ActionEvent event) {
        CommandHandler commandHandler = this.vueConteneur.getCommandHandler();
        commandHandler.redo();
        this.vueConteneur.modeleMisAjour();
        this.undo.setEnabled(!commandHandler.getStackUndo().isEmpty());
        this.redo.setEnabled(!commandHandler.getStackRedo().isEmpty());
    }

}
