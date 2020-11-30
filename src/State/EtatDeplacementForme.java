/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package State;

import Command.Action;
import Command.DeplacementFormeAction;
import Test.Gui;
import java.awt.Color;
import java.awt.Point;
import java.awt.event.MouseEvent;
import javax.swing.JOptionPane;
import model.Cercle;
import model.ConteneurFormes;
import model.Forme;
import model.Rectangle;
import view.CercleVue;
import view.RectangleVue;
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
    model.Point intialPoint;
    model.Point finalPoint;
    Forme currentForme;
    Vue currentVue;

    public EtatDeplacementForme(VueConteneur vueConteneur, ConteneurFormes conteneurFormes) {
        this.vueConteneur = vueConteneur;
        this.conteneurFormes = conteneurFormes;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        this.f = null;
        for (Forme forme : this.conteneurFormes.getFormes()) {
            if (forme.collisionPoint(e.getPoint())) {
                for (Vue vue : vueConteneur.getVues()) {
                    if (vue.getForme().equals(forme)) {
                        if (vue.getCouleur() != Color.red) {
                            this.f = forme;
                            this.vue = vue;
                            this.intialPoint = f.getPointDepart();
                        }

                        break;
                    }
                }
                break;
            }
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (f != null) {
            model.Point point = new model.Point(e.getPoint().getX(), e.getPoint().getY());
            if (f instanceof Cercle) {
                Cercle c = (Cercle) f;
                currentForme = new Cercle(point, c.getRayon());
                currentVue = new CercleVue((Cercle) currentForme, Color.blue);
            } else {
                Rectangle r = (Rectangle) f;
                currentForme = new Rectangle(point, r.getLargeur(), r.getHauteur());
                currentVue = new RectangleVue((Rectangle) currentForme, Color.blue);
            }
            if (deplacementEnCours) {
                this.vueConteneur.removeVue(vueConteneur.getVues().size() - 1);
            } else {
                this.conteneurFormes.getFormes().remove(f);
                this.vueConteneur.removeVue(this.vue);
            }
            this.vueConteneur.addVue(currentVue);
            this.vueConteneur.modeleMisAjour();
            this.deplacementEnCours = true;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        this.finalPoint = new model.Point(e.getPoint().getX(), e.getPoint().getY());
        if (this.f != null) {
            if (deplacementEnCours) {
                this.vueConteneur.removeVue(this.vueConteneur.getVues().size() - 1);

                if (this.currentForme.collision(conteneurFormes) || (!this.currentForme.formeValidee())) {
                    JOptionPane.showMessageDialog(vueConteneur, "Collision detecté");
                    System.out.println("2 :" + this.f.getPointDepart().getX() + ":->" + f.getPointDepart().getY());

                    this.f.deplacement(intialPoint);
                    this.conteneurFormes.add(f);
                    this.vueConteneur.addVue(vue);

                    deplacementEnCours = false;
                    this.vueConteneur.modeleMisAjour();
                } else {
                    Action action = new DeplacementFormeAction(f, intialPoint, finalPoint, vueConteneur);
                    this.vueConteneur.getCommandHandler().handle(action);

                    Gui.undo.setEnabled(!vueConteneur.getCommandHandler().getStackUndo().isEmpty());
                    Gui.redo.setEnabled(!vueConteneur.getCommandHandler().getStackRedo().isEmpty());

                    //this.f.deplacement(finalPoint);
                    this.conteneurFormes.add(f);
                    this.vueConteneur.addVue(vue);

                    deplacementEnCours = false;
                    this.vueConteneur.modeleMisAjour();
                }
                //
                //this.conteneurFormes.add(f);
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

}
