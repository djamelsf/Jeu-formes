/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import model.Cercle;
import model.Forme;

/**
 * Classe permettant de représenter la vue graphique associée à un cercle.
 * @author mac
 */
public class CercleVue implements Vue{
    private Cercle cercle;
    private Color couleur;
    
    /**
     *
     * @param cercle   l'instance cercle de la forme géométrique  qu'on est entrant de gérer.
     * @param couleur  c'est la couleur choisie pour le rectangle par le model en fonction de celui qui l'a crée.
     */
    public CercleVue(Cercle cercle,Color couleur) {
        this.cercle = cercle;
        this.couleur=couleur;
    }
    
     /**
     * cette fonction permet de dessiner le graphe en question.
     * @param graphics2D le composant a dessiner.
     */
    @Override
    public void paint(Graphics2D graphics2D) {
        Shape shape= new Ellipse2D.Double(cercle.getX()-cercle.getRayon(), cercle.getY()-cercle.getRayon(), cercle.getRayon()*2, cercle.getRayon()*2);
        graphics2D.setColor(this.couleur);
        graphics2D.fill(shape);
    }

    @Override
    public Forme getForme() {
        return this.cercle;
    }
    
    /**
     * c'est une methode qui permet de comparer deux cercles.
     * @param obj l'instance de la forme en question
     * @return true si l'objet est un rectangle et false dans le cas contraire.
     */
    @Override
    public boolean equals(Object obj) {
        if(obj==null) return false;
        if(obj instanceof CercleVue){
            CercleVue cercleVue=(CercleVue)obj;
            return cercleVue.getForme().equals(this.cercle);
        }
        return false;
    }

    public Cercle getCercle() {
        return cercle;
    }

    @Override
    public Color getCouleur() {
        return couleur;
    }
    
    
    
}
