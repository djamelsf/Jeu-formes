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
 *
 * @author mac
 */
public class AjoutFormeAction implements Action {

    Forme forme;
    Vue vue;
    VueConteneur vueConteneur;

    public AjoutFormeAction(Forme forme,Vue vue, VueConteneur vueConteneur) {
        this.forme = forme;
        this.vue=vue;
        this.vueConteneur = vueConteneur;
    }

    @Override
    public void operate() {
        this.vueConteneur.addVue(vue);
        this.vueConteneur.getConteneurFormes().add(forme);
        //this.vueConteneur.modeleMisAjour();
    }

    @Override
    public void compensate() {
        this.vueConteneur.removeVue(vue);
        this.vueConteneur.getConteneurFormes().remove(forme);
    }

}
