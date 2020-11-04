/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package State;

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
        CercleVue cercleVue = new CercleVue(cercle);
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
            this.fin = e.getPoint();
            dessinEnCours = false;
            cercle.collision(this.conteneurFormes);
            conteneurFormes.add(cercle);
        }
    }

}
