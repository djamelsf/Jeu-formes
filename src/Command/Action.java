/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Command;

/**
 * Interface qui definit une action du pattern Command. Une action peut être
 * (re)faite ou annulée.
 *
 * @author mac
 */
public interface Action {

    /**
     * methode qui permet d'exécuter l'action
     */
    void operate();

    /**
     * methode qui permet de l'annuler
     */
    void compensate();

}
