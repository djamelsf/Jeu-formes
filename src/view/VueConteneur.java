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
import Test.Gui;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
import model.Cercle;
import model.ConteneurFormes;
import model.Forme;
import model.Point;
import model.Rectangle;
import util.ConteneurListener;
import util.EcouteurFormes;

/**
 *
 * @author mac
 */
public class VueConteneur extends JPanel implements ConteneurListener, EcouteurFormes, MouseMotionListener, MouseListener {

    private ArrayList<Vue> vues;
    private ConteneurFormes conteneurFormes;
    private Rectangle rectangle;
    private Cercle cercle;
    public static EtatForme etatForme;
    public static int ETAT = 1; // Bouton de création rectangle
    private CommandHandler commandHandler;
    public static int largeur = 1000;
    public static int hauteur = 400;
    
    public static double score;

    public VueConteneur() {
        this.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
        this.setPreferredSize(new Dimension(1000, 400));
        this.setMaximumSize(new Dimension(1000, 400));
        //this.setSize(500, 500);
        this.vues = new ArrayList<>();
        this.conteneurFormes = new ConteneurFormes();
        addMouseMotionListener(this);
        addMouseListener(this);
        this.commandHandler = new CommandHandler();
        Gui.undo.setEnabled(!commandHandler.getStackUndo().isEmpty());
        Gui.undo.setEnabled(!commandHandler.getStackRedo().isEmpty());
        tableMAJ();
        generateRandomFormes();
    }

    public void resoudre() {
        String solution="";
        if (conteneurFormes.getFormes().size() < 8) {
            JOptionPane.showMessageDialog(this, "vous devez deposer 4 formes !");
        } else {
            double sum=0;
            for (int i = 4; i < conteneurFormes.getFormes().size(); i++) {
                sum+=conteneurFormes.getFormes().get(i).calculSurface();
            }
            score=sum/(largeur*hauteur);
            JOptionPane.showMessageDialog(this, "Votre score :"+score);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D gr = (Graphics2D) g;
        for (Vue vue : vues) {
            vue.paint(gr);
        }
    }

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

    public void generateRandomFormes() {
        Rectangle rectangle;
        Cercle cercle;

        for (int j = 0; j < 4; j++) {
            int f = (int) (Math.random() * 2);
            if (f == 0) {
                do {
                    double x = Math.random() * 300;
                    double y = Math.random() * 300;
                    double rayon = 50 + (Math.random() * 256);
                    cercle = new Cercle(new Point(x, y), rayon);
                } while (cercle.collision(conteneurFormes) || !cercle.formeValidee());
                conteneurFormes.add(cercle);
                vues.add(new CercleVue(cercle, Color.red));
            } else {
                do {
                    double x = Math.random() * 300;
                    double y = Math.random() * 300;
                    double largeur = 50 + (Math.random() * 256);
                    double hauteur = 50 + (Math.random() * 256);
                    rectangle = new Rectangle(new Point(x, y), largeur, hauteur);
                } while (rectangle.collision(conteneurFormes) || !rectangle.formeValidee());
                conteneurFormes.add(rectangle);
                vues.add(new RectangleVue(rectangle, Color.red));
            }
        }
        modeleMisAjour();
    }

    public boolean quatreFormesDeposee() {
        return this.conteneurFormes.getFormes().size() == 8;
    }

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

    public ConteneurFormes getConteneurFormes() {
        return conteneurFormes;
    }

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
