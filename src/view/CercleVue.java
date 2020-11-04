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
 *
 * @author mac
 */
public class CercleVue implements Vue{
    private Cercle cercle;

    public CercleVue(Cercle cercle) {
        this.cercle = cercle;
    }
    
    @Override
    public void paint(Graphics2D graphics2D) {
        Shape shape= new Ellipse2D.Double(cercle.getX()-cercle.getRayon(), cercle.getY()-cercle.getRayon(), cercle.getRayon()*2, cercle.getRayon()*2);
        graphics2D.setColor(Color.red);
        graphics2D.draw(shape);
    }

    @Override
    public Forme getForme() {
        return this.cercle;
    }
    
}
