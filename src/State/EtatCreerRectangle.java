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
import view.RectangleVue;
import view.VueConteneur;

/**
 *
 * @author mac
 */
public class EtatCreerRectangle implements EtatForme {

    Rectangle rectangle;
    boolean dessinEnCours = false;
    VueConteneur vueConteneur;
    ConteneurFormes conteneurFormes;
    Point depart;
    Point fin;

    public EtatCreerRectangle(VueConteneur vueConteneur, ConteneurFormes conteneurFormes) {
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
        rectangle = new Rectangle(p, e.getX() - depart.getX(), e.getY() - depart.getY());
        RectangleVue rectangleVue = new RectangleVue(rectangle);
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
            this.fin = e.getPoint();
            dessinEnCours = false;
            rectangle.collision(this.conteneurFormes);
            conteneurFormes.add(rectangle);
        }
    }

}
