/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import static java.lang.Math.sqrt;
import view.VueConteneur;

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
        return rayon*rayon* Math.PI;
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
                    //System.out.println("CERCLE CERCLE");
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
                    //System.out.println("COLLISION CERCLE RECT!!!!!");
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

        double distX = point.getX() - this.getX();
        double distY = point.getY() - this.getY();
        double distance = Math.sqrt((distX * distX) + (distY * distY));

        if (distance <= this.rayon) {
            return true;
        }
        return false;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj instanceof Cercle) {
            Cercle cercle = (Cercle) obj;
            return (cercle.getRayon() == this.rayon && cercle.getX() == this.getX() && cercle.getY() == this.getY());
        }
        return false;
    }

    @Override
    public Point getPointDepart() {
        return new Point(this.getX(), this.getY());
    }

    @Override
    public String toString() {
        String string = "Cercle rayon=" + this.rayon;
        return string;
    }

    @Override
    public boolean formeValidee() {
        return this.getRayon() > 0 && !lineCircle(0, 0, 0, VueConteneur.hauteur) && !lineCircle(0, 0, VueConteneur.largeur, 0) && !lineCircle(VueConteneur.largeur, 0, VueConteneur.largeur, VueConteneur.hauteur)
                && !lineCircle(0, VueConteneur.hauteur, VueConteneur.largeur, VueConteneur.hauteur);
    }

    boolean pointCircle(double px, double py) {
        double distX = px - this.getX();
        double distY = py - this.getY();
        double distance = sqrt((distX * distX) + (distY * distY));

        if (distance <= this.getRayon()) {
            return true;
        }
        return false;
    }

    boolean lineCircle(double x1, double y1, double x2, double y2) {

        // is either end INSIDE the circle?
        // if so, return true immediately
        boolean inside1 = pointCircle(x1, y1);
        boolean inside2 = pointCircle(x2, y2);

        if (inside1 || inside2) {
            return true;
        }

        // get length of the line
        double distX = x1 - x2;
        double distY = y1 - y2;
        double len = sqrt((distX * distX) + (distY * distY));

        // get dot product of the line and circle
        double dot = (((this.getX() - x1) * (x2 - x1)) + ((this.getY() - y1) * (y2 - y1))) / Math.pow(len, 2);

        // find the closest point on the line
        double closestX = x1 + (dot * (x2 - x1));
        double closestY = y1 + (dot * (y2 - y1));

        // is this point actually on the line segment?
        // if so keep going, but if not, return false
        boolean onSegment = linePoint(x1, y1, x2, y2, closestX, closestY);
        if (!onSegment) {
            return false;
        }

        // get distance to closest point
        distX = closestX - this.getX();
        distY = closestY - this.getY();
        double distance = Math.sqrt((distX * distX) + (distY * distY));

        if (distance <= this.rayon) {
            return true;
        }
        return false;
    }

    boolean linePoint(double x1, double y1, double x2, double y2, double px, double py) {

        // get distance from the point to the two ends of the line
        double d1 = dist(px, py, x1, y1);
        double d2 = dist(px, py, x2, y2);

        // get the length of the line
        double lineLen = dist(x1, y1, x2, y2);

        // since floats are so minutely accurate, add
        // a little buffer zone that will give collision
        double buffer = 0.1;    // higher # = less accurate

   
        if (d1 + d2 >= lineLen - buffer && d1 + d2 <= lineLen + buffer) {
            return true;
        }
        return false;
    }

    public double dist(double x1, double y1, double x2, double y2) {
        return Math.sqrt((y2 - y1) * (y2 - y1) + (x2 - x1) * (x2 - x1));
    }

}
