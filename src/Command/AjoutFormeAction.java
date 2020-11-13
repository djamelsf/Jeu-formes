/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Command;

import model.Forme;
import view.VueConteneur;

/**
 *
 * @author mac
 */
public class AjoutFormeAction implements Action{
    Forme forme;
    VueConteneur vueConteneur;

    public AjoutFormeAction(Forme forme, VueConteneur vueConteneur) {
        this.forme = forme;
        this.vueConteneur = vueConteneur;
    }
    
    

    @Override
    public void operate() {
        this.vueConteneur.getConteneurFormes().add(forme);
    }

    @Override
    public void compensate() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
