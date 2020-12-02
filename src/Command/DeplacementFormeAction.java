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
 * cette classe difinit l'action de deplacement d'une forme.
 * elle implemente de l'interface Action(commande) de notre jeu
 * @author mac
 */
public class DeplacementFormeAction implements Action {

    Forme forme;
    VueConteneur vueConteneur;
    Point initialPoint;
    Point finalPoint;
    
    /**
     * 
     * @param forme c'est l'instance de forme qu'on veut deplacer
     * @param initialPoint  c'est le point de depart avant l'action de deplacement
     * @param finalPoint C'est la position final de relachement du clic. 
     * @param vueConteneur l'instance de Jpanel.
     */
    public DeplacementFormeAction(Forme forme, Point initialPoint, Point finalPoint, VueConteneur vueConteneur) {
        this.forme = forme;
        this.initialPoint = initialPoint;
        this.finalPoint = finalPoint;
        this.vueConteneur = vueConteneur;
    }

    @Override
    public void operate() {
        this.forme.deplacement(finalPoint);
        
    }

    @Override
    public void compensate() {
        this.forme.deplacement(initialPoint);
    }

}
