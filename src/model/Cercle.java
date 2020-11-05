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
public class Cercle extends Point implements Forme {

    private double rayon;

    public Cercle(Point point, double rayon) {
        super(point.getX(), point.getY());
        this.rayon = rayon;
    }

    @Override
    public double calculSurface() {
        return Math.sqrt(rayon) * Math.PI;
    }

    public double getRayon() {
        return rayon;
    }

    public void setRayon(double rayon) {
        this.rayon = rayon;
    }

    @Override
    public boolean collision(ConteneurFormes conteneurFormes) {
        for (Forme forme : conteneurFormes.getFormes()) {
            if (forme instanceof Cercle) {
                Cercle cercle = (Cercle) forme;
                double distX = this.getX() - cercle.getX();
                double distY = this.getY() - cercle.getY();
                double distance = Math.sqrt((distX * distX) + (distY * distY));
                if (distance <= this.getRayon() + cercle.getRayon()) {
                    System.out.println("CERCLE CERCLE");
                    return true;
                }
            }
            if (forme instanceof Rectangle) {
                Rectangle r = (Rectangle) forme;
                double testX = this.getX();
                double testY = this.getY();

                if (this.getX() < r.getX()) {
                    testX = r.getX();
                } else if (this.getX() > r.getX() + r.getLargeur()) {
                    testX = r.getX() + r.getLargeur();
                }
                if (this.getY() < r.getY()) {
                    testY = r.getY();
                } else if (this.getY() > r.getY() + r.getHauteur()) {
                    testY = r.getY() + r.getHauteur();
                }

                double distX = this.getX() - testX;
                double distY = this.getY() - testY;
                double distance = Math.sqrt((distX * distX) + (distY * distY));

                if (distance <= this.getRayon()) {
                    System.out.println("COLLISION CERCLE RECT!!!!!");
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void deplacement(Point point) {
        this.setX(point.getX());
        this.setY(point.getY());
    }

    @Override
    public void translation(Forme forme) {
        if (forme instanceof Cercle) {
            Cercle cercle = (Cercle) forme;
            this.rayon = cercle.getRayon();
        }
    }

    @Override
    public boolean collisionPoint(java.awt.Point point) {
        // get distance between the point and circle's center
        // using the Pythagorean Theorem
        double distX = point.getX() - this.getX();
        double distY = point.getY() - this.getY();
        double distance = Math.sqrt((distX * distX) + (distY * distY));

        // if the distance is less than the circle's
        // radius the point is inside!
        if (distance <= this.rayon) {
            return true;
        }
        return false;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj==null) return false;
        if(obj instanceof Cercle){
            Cercle cercle=(Cercle)obj;
            return (cercle.getRayon()==this.rayon && cercle.getX()==this.getX() && cercle.getY()==this.getY());
        }
        return false;
    }

    
    
    

}
