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
import model.Rectangle;

/**
 *
 * @author mac
 */
public class RectangleVue implements Vue{

    private Rectangle rectangle;

    public RectangleVue(Rectangle rectangle) {
        this.rectangle = rectangle;
    }

    @Override
    public void paint(Graphics2D graphics2D) {
        Shape shape = new Rectangle2D.Double(rectangle.getX(), rectangle.getY(), rectangle.getLargeur(), rectangle.getHauteur());
        graphics2D.setColor(Color.BLUE);
        graphics2D.draw(shape);
    }

}
