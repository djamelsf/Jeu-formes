/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package State;

import Command.Action;
import Command.TranslationFormeAction;
import Test.Main;
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
public class EtatTranslationForme implements EtatForme {

    Forme f;
    Vue vue;
    VueConteneur vueConteneur;
    ConteneurFormes conteneurFormes;
    Forme currentForme;
    Vue currentVue;
    boolean translationEnCours = false;
    model.Point intialPoint;
    model.Point finalPoint;

    public EtatTranslationForme(VueConteneur vueConteneur, ConteneurFormes conteneurFormes) {
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
                        this.f = forme;
                        this.vue = vue;
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
                currentForme = new Cercle(c.getPointDepart(), e.getX() - c.getPointDepart().getX());
                currentVue = new CercleVue((Cercle) currentForme);
            } else {
                Rectangle r = (Rectangle) f;
                currentForme = new Rectangle(r.getPointDepart(), e.getX() - r.getPointDepart().getX(), e.getY() - r.getPointDepart().getY());
                currentVue = new RectangleVue((Rectangle) currentForme);
            }
            if (translationEnCours) {
                this.vueConteneur.removeVue(vueConteneur.getVues().size() - 1);
            } else {
                this.conteneurFormes.getFormes().remove(f);
                this.vueConteneur.removeVue(this.vue);
            }

            this.vueConteneur.addVue(currentVue);
            this.vueConteneur.modeleMisAjour();
            this.translationEnCours = true;
        }

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        this.finalPoint = new model.Point(e.getPoint().getX(), e.getPoint().getY());
        if (f != null) {
            if (translationEnCours) {
                this.vueConteneur.removeVue(this.vueConteneur.getVues().size() - 1);
                if (this.currentForme.collision(conteneurFormes) || !this.currentForme.formeValidee()) {
                    JOptionPane.showMessageDialog(vueConteneur, "Collision detecté");
                    this.conteneurFormes.add(f);
                    this.vueConteneur.addVue(vue);
                    translationEnCours = false;
                    this.vueConteneur.modeleMisAjour();
                } else {
                    Action action = new TranslationFormeAction(f, vueConteneur, currentForme);
                    this.vueConteneur.getCommandHandler().handle(action);
                    
                    Main.jButton5.setEnabled(!vueConteneur.getCommandHandler().getStackUndo().isEmpty());
                    Main.jButton6.setEnabled(!vueConteneur.getCommandHandler().getStackRedo().isEmpty());
                    
                    
                    this.conteneurFormes.add(f);
                    this.vueConteneur.addVue(vue);

                    translationEnCours = false;
                    this.vueConteneur.modeleMisAjour();
                }
            }
        }

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        //VIDE
    }

}
