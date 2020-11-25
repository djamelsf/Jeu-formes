/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package State;

import Command.Action;
import Command.AjoutFormeAction;
import Test.Main;
import java.awt.Color;
import java.awt.Point;
import java.awt.event.MouseEvent;
import javax.swing.JOptionPane;
import model.Cercle;
import model.ConteneurFormes;
import view.CercleVue;
import view.VueConteneur;

/**
 *
 * @author mac
 */
public class EtatCreerCercle implements EtatForme {

    Cercle cercle;
    CercleVue cercleVue;
    boolean dessinEnCours = false;
    VueConteneur vueConteneur;
    ConteneurFormes conteneurFormes;
    Point depart;
    Point fin;

    public EtatCreerCercle(VueConteneur vueConteneur, ConteneurFormes conteneurFormes) {
        this.vueConteneur = vueConteneur;
        this.conteneurFormes = conteneurFormes;
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
        cercle = new Cercle(p, e.getX() - depart.getX());
        this.cercleVue = new CercleVue(cercle, Color.blue);
        if (dessinEnCours) {
            this.vueConteneur.removeVue(vueConteneur.getVues().size() - 1);
        }
        this.vueConteneur.addVue(cercleVue);
        this.vueConteneur.modeleMisAjour();
        this.dessinEnCours = true;

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (dessinEnCours) {
            this.vueConteneur.removeVue(this.vueConteneur.getVues().size() - 1);
            // add somthing here
            this.fin = e.getPoint();
            dessinEnCours = false;
            cercle.collision(this.conteneurFormes);
            if (!cercle.collision(this.conteneurFormes) && cercle.formeValidee()) {
                Action action = new AjoutFormeAction(cercle, cercleVue, vueConteneur);
                this.vueConteneur.getCommandHandler().handle(action);
                Main.jButton5.setEnabled(!vueConteneur.getCommandHandler().getStackUndo().isEmpty());
                Main.jButton6.setEnabled(!vueConteneur.getCommandHandler().getStackRedo().isEmpty());
            } else {
                JOptionPane.showMessageDialog(vueConteneur, "Dessin impossible !");
            }
            this.vueConteneur.modeleMisAjour();

            //conteneurFormes.add(cercle);
            //MAJ OU PAS?
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        //VIDE
    }

}
