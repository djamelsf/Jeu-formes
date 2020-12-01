/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package State;

import Command.Action;
import Command.AjoutFormeAction;
import Test.Gui;
import java.awt.Color;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import model.ConteneurFormes;
import model.Forme;
import model.Rectangle;
import view.RectangleVue;
import view.VueConteneur;

/**
 *
 * @author mac
 */
public class EtatCreerRectangle implements EtatForme {

    Rectangle rectangle;
    RectangleVue rectangleVue;
    boolean dessinEnCours = false;
    VueConteneur vueConteneur;
    ConteneurFormes conteneurFormes;
    Point depart;
    Point fin;

    public EtatCreerRectangle(VueConteneur vueConteneur, ConteneurFormes conteneurFormes) {
        this.vueConteneur = vueConteneur;
        this.conteneurFormes = conteneurFormes;
        vueConteneur.setCursor(Toolkit.getDefaultToolkit().createCustomCursor(
                new ImageIcon("icons/pencil-cursor.png").getImage(),
                new Point(0, 0), "custom cursor"));
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (!this.vueConteneur.quatreFormesDeposee()) {
            this.depart = e.getPoint();
        } else {
            JOptionPane.showMessageDialog(vueConteneur, "4 formes dèja deposées !");
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        model.Point p = new model.Point(depart.getX(), depart.getY());
        rectangle = new Rectangle(p, e.getX() - depart.getX(), e.getY() - depart.getY());
        this.rectangleVue = new RectangleVue(rectangle,Color.blue);
        if (dessinEnCours) {
            this.vueConteneur.removeVue(vueConteneur.getVues().size() - 1);
        }
        this.vueConteneur.addVue(rectangleVue);
        this.vueConteneur.modeleMisAjour();
        this.dessinEnCours = true;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (dessinEnCours) {
            this.vueConteneur.removeVue(this.vueConteneur.getVues().size() - 1);
            this.fin = e.getPoint();
            dessinEnCours = false;
            if (!rectangle.collision(this.conteneurFormes) && rectangle.formeValidee()) {
                Action action = new AjoutFormeAction(rectangle, rectangleVue, vueConteneur);
                this.vueConteneur.getCommandHandler().handle(action);
                Gui.undo.setEnabled(!vueConteneur.getCommandHandler().getStackUndo().isEmpty());
                Gui.redo.setEnabled(!vueConteneur.getCommandHandler().getStackRedo().isEmpty());
            }else{
                JOptionPane.showMessageDialog(vueConteneur, "Dessin impossible !");
            }
            this.vueConteneur.modeleMisAjour();

            //conteneurFormes.add(rectangle);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        //VIDE
    }

}
