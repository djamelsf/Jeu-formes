/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import view.VueConteneur;

/**
 * Classe permettant de représenter un rectangle, elle a trois attributs: sa largeur, sa longueur et son point de départ.
 * @author mac
 */
public class Rectangle extends Point implements Forme {

    private double largeur;
    private double hauteur;
    
    /**
     * constructeur de notre classe qui permet d'instancier un rectancgle et qui prend en paramètre
     * @param point son point de départ.
     * @param largeur largeur de rectangle
     * @param hauteur hauteur de rectangle
     */
    public Rectangle(Point point, double largeur, double hauteur) {
        super(point.getX(), point.getY());
        this.hauteur = hauteur;
        this.largeur = largeur;
    }
    
    /**
     * permet de calculer la surface d'un rectangle.
     * @return retourne la surface
     */
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
    
    /**
     * teste la collision de cette forme avec les autres formes du conteneur.
     * @param conteneurFormes le conteneur des formes de dessin
     * @return vrai s'il ya collision sinon faux.
     */
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
                    return true;
                }

            }
            if (forme instanceof Rectangle) {
                Rectangle rectangle = (Rectangle) forme;
                if (this.getX() + this.getLargeur() >= rectangle.getX()
                        && this.getX() <= rectangle.getX() + rectangle.getLargeur()
                        && this.getY() + this.getHauteur() >= rectangle.getY()
                        && this.getY() <= rectangle.getY() + rectangle.getHauteur()) {
                    return true;
                }

            }
        }
        return false;
    }
    
    /**
     * methode de deplacement d'un rectangle
     * @param point le nouveau centre
     */
    @Override
    public void deplacement(Point point) {
        this.setX(point.getX());
        this.setY(point.getY());
    }
    
    /**
     * permet de redimensionner un rectangle
     * @param forme la forme qu'on va déplacer.
     */
    @Override
    public void translation(Forme forme) {
        if (forme instanceof Rectangle) {
            Rectangle rectangle = (Rectangle) forme;
            this.hauteur = rectangle.getHauteur();
            this.largeur = rectangle.getLargeur();
        }
    }
    
    /**
     * fonction qui permet de tester la collision de ce rectangle avec un point.
     * @param point le poit a tester pour la collision
     * @return vrai s'ils se touchent sionon faux.
     */
    @Override
    public boolean collisionPoint(java.awt.Point point) {

        if (point.getX() >= this.getX()
                && point.getX() <= this.getX() + this.largeur
                && point.getY() >= this.getY()
                && point.getY() <= this.getY() + this.hauteur) {
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

    @Override
    public String toString() {
        String str = "Rectangle largeur=" + this.largeur + " hauteur=" + this.hauteur;
        return str;
    }
    
    /**
     * permet de verifier si cette forme est valide c'est a dire ne depasse pas la zone de dessin et ne touche aucune autre forme.
     * @return vrai si les formes se touche ou une une fois qu'on dpassé le plan de dessin.
     */
    @Override
    public boolean formeValidee() {
        return (this.getHauteur() > 0 && this.getLargeur() > 0) && !lineRect(0, 0, 0, VueConteneur.hauteur) && !lineRect(0, 0, VueConteneur.largeur, 0) && !lineRect(VueConteneur.largeur, 0, VueConteneur.largeur, VueConteneur.hauteur)
                && !lineRect(0, VueConteneur.hauteur, VueConteneur.largeur, VueConteneur.hauteur);
    }
    
    /**
    * fonction qui verifie si le rectangle touche une ligne(bordure de Jpanel) 
    * @param x1  l'abscisse de la premiere fin de ligne.
    * @param y1 l'ordonné de la premiere fin de ligne.
    * @param x2 l'abscisse de la deuxieme fin de ligne.
    * @param y2 l'ordonné de la deuxieme fin de ligne.
    * @return variable boolean est retournée contenant le resultat du test.
    */
    boolean lineRect(double x1, double y1, double x2, double y2) {

        boolean left = lineLine(x1, y1, x2, y2, this.getX(), this.getY(), this.getX(), this.getY() + this.hauteur);
        boolean right = lineLine(x1, y1, x2, y2, this.getX() + this.largeur, this.getY(), this.getX() + this.largeur, this.getY() + this.hauteur);
        boolean top = lineLine(x1, y1, x2, y2, this.getX(), this.getY(), this.getX() + this.largeur, this.getY());
        boolean bottom = lineLine(x1, y1, x2, y2, this.getX(), this.getY() + this.hauteur, this.getX() + this.largeur, this.getY() + this.hauteur);

        if (left || right || top || bottom) {
            return true;
        }
        return false;
    }
    
     /**
     * cette fonction permet de tester la collision de  deux lignes.
     * @param x1 c'est l'abscisse du premier extremité de la première ligne
     * @param y1 c'est l'ordonnée du premier extremité de la première ligne
     * @param x2 c'est l'abscisse du deuxième extremité de la première ligne
     * @param y2 c'est l'ordonnée du deuxième extremité de la première ligne
     * @param x3 c'est l'abscisse du premier extremité de la deuxième ligne
     * @param y3 c'est l'ordonnée du premier extremité de la deuxième ligne
     * @param x4 c'est l'abscisse du premier extremité de la deuxième ligne
     * @param y4 c'est l'ordonnée du deuxième extremité de la deuxième ligne
     * @return vrai si collision détectée sinon faux.
     */
    boolean lineLine(double x1, double y1, double x2, double y2, double x3, double y3, double x4, double y4) {

        double uA = ((x4 - x3) * (y1 - y3) - (y4 - y3) * (x1 - x3)) / ((y4 - y3) * (x2 - x1) - (x4 - x3) * (y2 - y1));
        double uB = ((x2 - x1) * (y1 - y3) - (y2 - y1) * (x1 - x3)) / ((y4 - y3) * (x2 - x1) - (x4 - x3) * (y2 - y1));

        if (uA >= 0 && uA <= 1 && uB >= 0 && uB <= 1) {

            return true;
        }
        return false;
    }

}
