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
    model.Point intialPoint;
    model.Point finalPoint;

    public EtatDeplacementForme(VueConteneur vueConteneur, ConteneurFormes conteneurFormes) {
        this.vueConteneur = vueConteneur;
        this.conteneurFormes = conteneurFormes;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        this.f=null;
        System.out.println("pressed");
        for (Forme forme : this.conteneurFormes.getFormes()) {
            System.out.println("presse2");
            if (forme.collisionPoint(e.getPoint())) {
                System.out.println( "xxxxxxx");
                for (Vue vue : vueConteneur.getVues()) {

                    if (vue.getForme().equals(forme)) {
                        this.f = forme;
                        this.vue = vue;
                        this.conteneurFormes.getFormes().remove(f);
                        //
                        this.intialPoint=f.getPointDepart();
                        System.out.println("1 :"+this.intialPoint.getX()+":->"+intialPoint.getY());
                        //
                        break;
                    }
                }
                break;
            }
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        
        if (this.f != null) {
            finalPoint = new model.Point(e.getPoint().getX(), e.getPoint().getY());
            this.f.deplacement(finalPoint);
            this.vueConteneur.modeleMisAjour();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (this.f != null) {
            System.out.println("PPPPPPPPPPP");
            //this.f.collision(conteneurFormes);
            //
            if(this.f.collision(conteneurFormes)){
                JOptionPane.showMessageDialog(vueConteneur,"Collision detecté");
                //this.vueConteneur.removeVue(vue);
                this.f.deplacement(intialPoint);
                System.out.println("2 :"+this.f.getPointDepart().getX()+":->"+f.getPointDepart().getY());
                this.conteneurFormes.add(f);
                this.vueConteneur.modeleMisAjour();
            }else{
            Action action=new DeplacementFormeAction(f, intialPoint, finalPoint, vueConteneur);
            this.vueConteneur.getCommandHandler().handle(action);
                //this.conteneurFormes.add(f);
            }
            //
            //this.conteneurFormes.add(f);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

}
