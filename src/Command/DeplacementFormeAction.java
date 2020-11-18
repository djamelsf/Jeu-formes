/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Command;

import model.Forme;
import model.Point;
import view.Vue;
import view.VueConteneur;

/**
 *
 * @author mac
 */
public class DeplacementFormeAction implements Action {

    Forme forme;
    VueConteneur vueConteneur;
    Point initialPoint;
    Point finalPoint;

    public DeplacementFormeAction(Forme forme,Point initialPoint,Point finalPoint, VueConteneur vueConteneur) {
        this.forme = forme;
        this.initialPoint=initialPoint;
        this.finalPoint=finalPoint;
        this.vueConteneur = vueConteneur;
    }

    @Override
    public void operate() {
        this.vueConteneur.getConteneurFormes().add(forme);
    }

    @Override
    public void compensate() {
        this.forme.deplacement(initialPoint);
        //this.vueConteneur.getConteneurFormes().add(forme);
    }

}
