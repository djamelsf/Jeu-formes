/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Command;

import model.Cercle;
import model.Forme;
import model.Point;
import model.Rectangle;
import view.VueConteneur;

/**
 *
 * @author mac
 */
public class TranslationFormeAction implements Action{
    Forme forme;
    VueConteneur vueConteneur;
    Forme nouvelleForme;
    Forme formeInitial;

    public TranslationFormeAction(Forme forme, VueConteneur vueConteneur, Forme nouvelleForme) {
        this.forme = forme;
        this.vueConteneur = vueConteneur;
        this.nouvelleForme = nouvelleForme;
    }
    
    

    @Override
    public void operate() {
        if(forme instanceof Rectangle){
            Rectangle r=(Rectangle)forme;
            this.formeInitial=new Rectangle(r.getPointDepart(), r.getLargeur(), r.getHauteur());
        }
        if(forme instanceof Cercle){
            Cercle c=(Cercle)forme;
            this.formeInitial=new Cercle(c.getPointDepart(), c.getRayon());
        }
        this.forme.translation(nouvelleForme);
    }

    @Override
    public void compensate() {
        this.forme.translation(formeInitial);
    }
    
}
