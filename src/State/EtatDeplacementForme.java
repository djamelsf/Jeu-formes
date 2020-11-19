/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package State;

import Command.Action;
import Command.DeplacementFormeAction;
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
                        this.f = forme;
                        this.vue = vue;
                        this.conteneurFormes.getFormes().remove(f);
                        this.intialPoint = f.getPointDepart();
                        System.out.println("1 :" + this.intialPoint.getX() + ":->" + intialPoint.getY());
                        break;
                    }
                }
                break;
            }
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        model.Point point=new model.Point(e.getX(), 3);
        if (f instanceof Cercle) {
            Cercle c = (Cercle) f;
            currentForme = new Cercle(c.getPointDepart(), c.getRayon());
            currentVue=new CercleVue((Cercle) currentForme);   
        }else{
            Rectangle r=(Rectangle)f;
            currentForme=new Rectangle(r.getPointDepart(), r.getLargeur(), r.getHauteur());
            currentVue=new RectangleVue((Rectangle) currentForme);
        }
        if (deplacementEnCours) {
            this.vueConteneur.removeVue(vueConteneur.getVues().size() - 1);
        }
        System.out.println("x");
        this.vueConteneur.addVue(currentVue);
        this.vueConteneur.modeleMisAjour();
        this.deplacementEnCours = true;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (this.f != null) {
            System.out.println("PPPPPPPPPPP");
            //this.f.collision(conteneurFormes);
            //
            if (this.f.collision(conteneurFormes)) {
                JOptionPane.showMessageDialog(vueConteneur, "Collision detecté");
                //this.vueConteneur.removeVue(vue);
                this.f.deplacement(intialPoint);
                System.out.println("2 :" + this.f.getPointDepart().getX() + ":->" + f.getPointDepart().getY());
                this.conteneurFormes.add(f);
                this.vueConteneur.modeleMisAjour();
            } else {
                //Action action=new DeplacementFormeAction(f, intialPoint, finalPoint, vueConteneur);
                //this.vueConteneur.getCommandHandler().handle(action);
                this.conteneurFormes.add(f);
            }
            //
            //this.conteneurFormes.add(f);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

}
