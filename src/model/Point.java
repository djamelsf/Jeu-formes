/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 * classe qui représente un point dans notre model
 * elle contient deux doubles qui representent réspectivement l'abscisse et l'ordonnée du point.
 * @author mac
 */
public class Point {
    private double x;
    private double y;
    
    /**
     * constructeur de notre classe
     * @param x abscisse du nouveau point
     * @param y ordonnée du nouveau point
     */
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj==null) return false;
        if(obj instanceof Point){
            Point point=(Point)obj;
            return (point.getX()==this.x && point.getY()==this.y);
        }
        return false;
    }
    
    
    
}
