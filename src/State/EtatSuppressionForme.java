/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package State;

import Command.Action;
import Command.AjoutFormeAction;
import Command.SuppressionFormeAction;
import Test.Main;
import java.awt.event.MouseEvent;
import model.ConteneurFormes;
import model.Forme;
import view.Vue;
import view.VueConteneur;

/**
 *
 * @author mac
 */
public class EtatSuppressionForme implements EtatForme {

    VueConteneur vueConteneur;
    ConteneurFormes conteneurFormes;

    public EtatSuppressionForme(VueConteneur vueConteneur, ConteneurFormes conteneurFormes) {
        this.vueConteneur = vueConteneur;
        this.conteneurFormes = conteneurFormes;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        //VIDE
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        //VIDE
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        //VIDE
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        for (Forme forme : this.conteneurFormes.getFormes()) {
            if (forme.collisionPoint(e.getPoint())) {
                for (Vue vue : vueConteneur.getVues()) {
                    if (vue.getForme().equals(forme)) {
                        //this.vueConteneur.removeVue(vue);
                        //this.conteneurFormes.remove(forme);
                        Action action=new SuppressionFormeAction(forme, vue, vueConteneur);
                        this.vueConteneur.getCommandHandler().handle(action);
                        
                        Main.jButton5.setEnabled(!vueConteneur.getCommandHandler().getStackUndo().isEmpty());
                        Main.jButton6.setEnabled(!vueConteneur.getCommandHandler().getStackRedo().isEmpty());
                        
                        this.vueConteneur.modeleMisAjour();
                        break;
                    }
                }
                break;
            }

        }
    }

}
