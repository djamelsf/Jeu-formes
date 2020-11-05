/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package State;

import java.awt.Point;
import java.awt.event.MouseEvent;
import model.ConteneurFormes;
import model.Forme;
import model.Rectangle;
import view.Vue;
import view.VueConteneur;

/**
 *
 * @author mac
 */
public class EtatDeplacementForme implements EtatForme {

    Forme f;
    Vue vue;
    boolean deplacementEnCours = false;
    VueConteneur vueConteneur;
    ConteneurFormes conteneurFormes;
    Point depart;
    Point fin;

    public EtatDeplacementForme(VueConteneur vueConteneur, ConteneurFormes conteneurFormes) {
        this.vueConteneur = vueConteneur;
        this.conteneurFormes = conteneurFormes;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        for (Forme forme : this.conteneurFormes.getFormes()) {
            if (forme.collisionPoint(e.getPoint())) {

                for (Vue vue : vueConteneur.getVues()) {

                    if (vue.getForme().equals(forme)) {
                        this.f = forme;
                        this.vue = vue;
                        this.conteneurFormes.getFormes().remove(f);
                        break;
                    }
                }
                break;
            }
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        model.Point p = new model.Point(e.getPoint().getX(), e.getPoint().getY());
        this.f.deplacement(p);
        this.vueConteneur.modeleMisAjour();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        this.f.collision(conteneurFormes);
        this.conteneurFormes.add(f);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

}
