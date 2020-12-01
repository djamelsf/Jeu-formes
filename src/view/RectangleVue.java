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
 *
 * @author mac
 */
public class RectangleVue implements Vue{

    private Rectangle rectangle;
    private Color couleur;

    public RectangleVue(Rectangle rectangle, Color couleur) {
        this.rectangle = rectangle;
        this.couleur=couleur;
    }

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
