/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;

/**
 * classe premet de gérer le conteneur des formes de notre model.
 * @author mac
 */
public class ConteneurFormes {

    private ArrayList<Forme> formes;
    
    /**
     * constructeur du conteneur qui permet d'initialiser la liste des formes
     */
    public ConteneurFormes() {
        this.formes = new ArrayList<>();
    }
    
    public ConteneurFormes(ArrayList<Forme> formes){
        this.formes=formes;
    }

    public void add(Forme forme) {
        this.formes.add(forme);
    }

    public void remove(Forme forme) {
        this.formes.remove(forme);
    }

    public ArrayList<Forme> getFormes() {
        return formes;
    }

    public void setFormes(ArrayList<Forme> formes) {
        this.formes = formes;
    }
    

}
