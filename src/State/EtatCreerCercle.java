/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package State;

import Command.Action;
import Command.AjoutFormeAction;
import java.awt.Point;
import java.awt.event.MouseEvent;
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
        this.depart = e.getPoint();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        model.Point p = new model.Point(depart.getX(), depart.getY());
        cercle = new Cercle(p, e.getX() - depart.getX());
        this.cercleVue = new CercleVue(cercle);
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
            this.vueConteneur.removeVue(this.vueConteneur.getVues().size()-1);
            // add somthing here
            this.fin = e.getPoint();
            dessinEnCours = false;
            cercle.collision(this.conteneurFormes);
            Action action=new AjoutFormeAction(cercle, cercleVue, vueConteneur);
            this.vueConteneur.getCommandHandler().handle(action);
            //conteneurFormes.add(cercle);
            //MAJ OU PAS?
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        //VIDE
    }

}
