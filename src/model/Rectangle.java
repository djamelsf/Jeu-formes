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
public class Rectangle extends Point implements Forme {

    private double largeur;
    private double hauteur;

    public Rectangle(Point point, double largeur, double hauteur) {
        super(point.getX(), point.getY());
        this.hauteur = hauteur;
        this.largeur = largeur;
    }

    @Override
    public double calculSurface() {
        return this.largeur * this.hauteur;
    }

    public double getLargeur() {
        return largeur;
    }

    public void setLargeur(double largeur) {
        this.largeur = largeur;
    }

    public double getHauteur() {
        return hauteur;
    }

    public void setHauteur(double hauteur) {
        this.hauteur = hauteur;
    }

    @Override
    public boolean collision(ConteneurFormes conteneurFormes) {
        for (Forme forme : conteneurFormes.getFormes()) {
            if (forme instanceof Cercle) {
                Cercle c = (Cercle) forme;
                double testX = c.getX();
                double testY = c.getY();

                if (c.getX() < this.getX()) {
                    testX = this.getX();
                } else if (c.getX() > this.getX() + this.getLargeur()) {
                    testX = this.getX() + this.getLargeur();
                }
                if (c.getY() < this.getY()) {
                    testY = this.getY();
                } else if (c.getY() > this.getY() + this.getHauteur()) {
                    testY = this.getY() + this.getHauteur();
                }

                double distX = c.getX() - testX;
                double distY = c.getY() - testY;
                double distance = Math.sqrt((distX * distX) + (distY * distY));

                if (distance <= c.getRayon()) {
                    System.out.println("COLLISION CERCLE !!!!!");
                    return true;
                }

            }
            if (forme instanceof Rectangle) {
                Rectangle rectangle = (Rectangle) forme;
                if (this.getX() + this.getLargeur() >= rectangle.getX()
                        && this.getX() <= rectangle.getX() + rectangle.getLargeur()
                        && this.getY() + this.getHauteur() >= rectangle.getY()
                        && this.getY() <= rectangle.getY() + rectangle.getHauteur()) {
                    System.out.println("COLLISION !!!!!");
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
        if (forme instanceof Rectangle) {
            Rectangle rectangle = (Rectangle) forme;
            this.hauteur = rectangle.getHauteur();
            this.largeur = rectangle.getLargeur();
        }
    }

    @Override
    public boolean collisionPoint(java.awt.Point point) {
        // is the point inside the rectangle's bounds?
        if (point.getX() >= this.getX()
                && // right of the left edge AND
                point.getX() <= this.getX() + this.largeur
                && // left of the right edge AND
                point.getY() >= this.getY()
                && // below the top AND
                point.getY() <= this.getY() + this.hauteur) {   // above the bottom
            return true;
        }
        return false;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj instanceof Rectangle) {
            Rectangle rectangle = (Rectangle) obj;
            return (this.getX() == rectangle.getX() && this.getY() == rectangle.getY() && this.largeur == rectangle.largeur
                    && this.hauteur == rectangle.hauteur);
        }
        return false;
    }

    @Override
    public Point getPointDepart() {
        return new Point(this.getX(), this.getY());
    }

}
