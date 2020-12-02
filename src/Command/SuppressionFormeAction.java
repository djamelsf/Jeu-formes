/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Command;

import model.Forme;
import view.Vue;
import view.VueConteneur;

/**
 * cette classe difinit l'action de suppression d'une forme.
 * elle implemente de l'interface Action(commande) de notre jeu
 * @author mac
 */
public class SuppressionFormeAction implements Action {

    Forme forme;
    Vue vue;
    VueConteneur vueConteneur;
    
    /**
     * 
     * @param forme la forme qu'on veut supprimer.
     * @param vue   la vue qu'on veut supprimer.
     * @param vueConteneur c'est l'instance de notre Jpanel.
     */
    public SuppressionFormeAction(Forme forme, Vue vue, VueConteneur vueConteneur) {
        this.forme = forme;
        this.vue = vue;
        this.vueConteneur = vueConteneur;
    }

    @Override
    public void operate() {
        this.vueConteneur.removeVue(vue);
        this.vueConteneur.getConteneurFormes().remove(forme);
    }

    @Override
    public void compensate() {
        this.vueConteneur.addVue(vue);
        this.vueConteneur.getConteneurFormes().add(forme);
    }

}
