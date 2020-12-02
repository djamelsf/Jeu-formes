/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import static java.lang.Math.sqrt;
import view.VueConteneur;

/**
 * Classe permettant de représenter un cercle.
 * elle a deux attributs: son centre de type point et son rayon de type double et on peux y acceder avec un getter et un setter.
 * @author mac
 */
public class Cercle extends Point implements Forme {

    private double rayon;
    
    /**
     *c'est le bloque qui permet de construire notre classe.
     * @param point le centre du cercle de type Point.
     * @param rayon son rayon de type double
     */
    public Cercle(Point point, double rayon) {
        super(point.getX(), point.getY());
        this.rayon = rayon;
    }
    
    /**
     * permet de calculer la surface d'un cercle.
     */
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
    
    /**
     * cette fonction permet de detecter la collision entre les formes crées dans la zone de dessin.
     * @param conteneurFormes c'est le conteneur des formes présentes dans le model.
     * @return vrai ou faux
     */
    @Override
    public boolean collision(ConteneurFormes conteneurFormes) {
        for (Forme forme : conteneurFormes.getFormes()) {
            if (forme instanceof Cercle) {
                Cercle cercle = (Cercle) forme;
                double distX = this.getX() - cercle.getX();
                double distY = this.getY() - cercle.getY();
                double distance = Math.sqrt((distX * distX) + (distY * distY));
                if (distance <= this.getRayon() + cercle.getRayon()) {
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
                    return true;
                }
            }
        }
        return false;
    }
    
    /**
     * cette methode permet de déplacer un cercle. 
     * @param point le nouveau centre.
     */
    @Override
    public void deplacement(Point point) {
        this.setX(point.getX());
        this.setY(point.getY());
    }
    
    /**
     * methode qui permet de modifier le rayon du cercle et par conséquent la redimentionner.
     * @param forme une copie avec les nouvelles dimensions.
     */
    @Override
    public void translation(Forme forme) {
        if (forme instanceof Cercle) {
            Cercle cercle = (Cercle) forme;
            this.rayon = cercle.getRayon();
        }
    }
    
    /**
     * cette fonction permet de detecter la collision avec un point pour tester si le clic de la souris a été fait dans une forme.
     * @param point la position du click.
     * @return elle retourne vrai si le clic a été fait dedant et false dans le cas contraire.
     */
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
    
    /**
     * une fonction qui renvoie la position de depart de la forme.
     * @return retourne un point.
     */
    @Override
    public Point getPointDepart() {
        return new Point(this.getX(), this.getY());
    }
    
    /**
     * fonction qui renvoie le type et le rayon de la forme.
     * @return retourne une chaine de caractère(string).
     */
    @Override
    public String toString() {
        String string = "Cercle rayon=" + this.rayon;
        return string;
    }
    
    /**
     * fonction permet de verifier si un cercle ne dépasse pas la bordure de la zone de dessin.
     * @return vrai s'il est valide et inversement.
     */
    @Override
    public boolean formeValidee() {
        return this.getRayon() > 0 && !lineCircle(0, 0, 0, VueConteneur.hauteur) && !lineCircle(0, 0, VueConteneur.largeur, 0) && !lineCircle(VueConteneur.largeur, 0, VueConteneur.largeur, VueConteneur.hauteur)
                && !lineCircle(0, VueConteneur.hauteur, VueConteneur.largeur, VueConteneur.hauteur);
    }
    
    /**
     * fonction de tester si les cordonnées d'un point appartient a ce cercle.
     * @param px l'abscisse du point testé.
     * @param py l'ordonné du point testé.
     * @return retourne un booleen qui represente le résultat du test.
     */
    boolean pointCircle(double px, double py) {
        double distX = px - this.getX();
        double distY = py - this.getY();
        double distance = sqrt((distX * distX) + (distY * distY));

        if (distance <= this.getRayon()) {
            return true;
        }
        return false;
    }
    
    
    /**
    * fonction de tester la collision entre le cercle et une ligne.
    * @param x1  l'abscisse de la premiere fin de ligne.
    * @param y1 l'ordonné de la premiere fin de ligne.
    * @param x2 l'abscisse de la deuxieme fin de ligne.
    * @param y2 l'ordonné de la deuxieme fin de ligne.
    * @return variable boolean est retournée contenant le resultat du test.
    */
    boolean lineCircle(double x1, double y1, double x2, double y2) {
        boolean inside1 = pointCircle(x1, y1);
        boolean inside2 = pointCircle(x2, y2);

        if (inside1 || inside2) {
            return true;
        }
        double distX = x1 - x2;
        double distY = y1 - y2;
        double len = sqrt((distX * distX) + (distY * distY));
        double dot = (((this.getX() - x1) * (x2 - x1)) + ((this.getY() - y1) * (y2 - y1))) / Math.pow(len, 2);

        double closestX = x1 + (dot * (x2 - x1));
        double closestY = y1 + (dot * (y2 - y1));

        boolean onSegment = linePoint(x1, y1, x2, y2, closestX, closestY);
        if (!onSegment) {
            return false;
        }
        distX = closestX - this.getX();
        distY = closestY - this.getY();
        double distance = Math.sqrt((distX * distX) + (distY * distY));

        if (distance <= this.rayon) {
            return true;
        }
        return false;
    }
    
    /**
     * fonction de tester la collision entre un point donné et une ligne.
    * @param x1  l'abscisse de la premiere fin de ligne.
    * @param y1 l'ordonné de la premiere fin de ligne.
    * @param x2 l'abscisse de la deuxieme fin de ligne.
    * @param y2 l'ordonné de la deuxieme fin de ligne.
    * @param px l'abscisse du point testé.
    * @param py l'ordonné du point testé.
    * @return retourve vrai s'il s'agit d'une collision sinon non.
    */
    boolean linePoint(double x1, double y1, double x2, double y2, double px, double py) {

        double d1 = dist(px, py, x1, y1);
        double d2 = dist(px, py, x2, y2);

        double lineLen = dist(x1, y1, x2, y2);

        double buffer = 0.1;   

   
        if (d1 + d2 >= lineLen - buffer && d1 + d2 <= lineLen + buffer) {
            return true;
        }
        return false;
    }
    
    /**
     * fonction qui permet de calculer la distance entre deux points.
     * @param x1 l'abscisse du premier point.
     * @param y1 l'ordonné du premier point.
     * @param x2 l'abscisse du deuxième point.
     * @param y2 l'ordonné du deuxième point.
     * @return rétourne le résultat de calcul sous la forme d'un double.
     */
    public double dist(double x1, double y1, double x2, double y2) {
        return Math.sqrt((y2 - y1) * (y2 - y1) + (x2 - x1) * (x2 - x1));
    }

}
