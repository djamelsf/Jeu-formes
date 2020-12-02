/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import model.Forme;
import model.Rectangle;

/**
 * Classe permettant de représenter la vue graphique associée à un rectangle.
 * @author mac
 */
public class RectangleVue implements Vue{

    private Rectangle rectangle;
    private Color couleur;
    
    /**
     *
     * @param rectangle l'instance de la forme géométrique rectangle qu'on est entrant de gérer.
     * @param couleur c'est la couleur choisie pour le rectangle par le model en fonction de celui qui l'a crée.
     */
    public RectangleVue(Rectangle rectangle, Color couleur) {
        this.rectangle = rectangle;
        this.couleur=couleur;
    }
    
    /**
     * cette fonction permet de dessiner le graphe en question.
     * @param graphics2D le composent 2d a dessiner.
     */
    @Override
    public void paint(Graphics2D graphics2D) {
        Shape shape = new Rectangle2D.Double(rectangle.getX(), rectangle.getY(), rectangle.getLargeur(), rectangle.getHauteur());
        graphics2D.setColor(this.couleur);
        graphics2D.fill(shape);
    }

    @Override
    public Forme getForme() {
        return this.rectangle;
    }
    
    /**
     * c'est une methode qui permet de de comparer deux rectangles.
     * @param obj l'instance de la forme en question.
     * @return true si l'objet est un rectangle et false dans le cas contraire.
     */
    @Override
    public boolean equals(Object obj) {
        if(obj==null) return false;
        if(obj instanceof RectangleVue){
            RectangleVue rectangleVue=(RectangleVue)obj;
            return rectangleVue.getForme().equals(this.rectangle);
        }
        return false;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }
    
    public void setRectangle(Rectangle rectangle) {
        this.rectangle = rectangle;
    }

    @Override
    public Color getCouleur() {
        return couleur;
    }

    public void setCouleur(Color couleur) {
        this.couleur = couleur;
    }
    
    

}
