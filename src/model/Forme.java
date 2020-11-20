/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author mac
 */
public interface Forme {
    double calculSurface();
    boolean collision(ConteneurFormes conteneurFormes);
    void deplacement(Point point);
    void translation(Forme forme);
    boolean collisionPoint(java.awt.Point point);
    @Override
    public boolean equals(Object obj);
    Point getPointDepart();

    @Override
    public String toString();
    
    
}
