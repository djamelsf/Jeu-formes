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
 * cette classe definit l'action d'ajout d'une forme. elle implemente de
 * l'interface Action(commande) de notre jeu
 *
 * @author mac
 */
public class AjoutFormeAction implements Action {

    Forme forme;
    Vue vue;
    VueConteneur vueConteneur;

    /**
     *
     * @param forme la forme qu'on veut ajouter.
     * @param vue la vue qu'on veut ajouter.
     * @param vueConteneur l'instance de Jpanel.
     */
    public AjoutFormeAction(Forme forme, Vue vue, VueConteneur vueConteneur) {
        this.forme = forme;
        this.vue = vue;
        this.vueConteneur = vueConteneur;
    }

    /**
     * ajouter la vue a notre zone de dessin et faire appel au conteneur de
     * forme pour lui ajouter la forme.
     */
    @Override
    public void operate() {
        this.vueConteneur.addVue(vue);
        this.vueConteneur.getConteneurFormes().add(forme);
        //this.vueConteneur.modeleMisAjour();
    }

    /**
     * retirer la vue a notre zone de dessin et faire appel au conteneur de
     * forme pour lui retirer la forme.
     */
    @Override
    public void compensate() {
        this.vueConteneur.removeVue(vue);
        this.vueConteneur.getConteneurFormes().remove(forme);
    }

}
