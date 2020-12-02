/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Strategy;

import model.ConteneurFormes;

/**
 *
 * @author mac
 */
public interface SolutionStrategy {
    /**
     * Une fonction qui permet de generer une solution a notre jeu 
     * @param premisse la liste des 4 formes initiales
     * @return une liste solution qui contient 4 formes
     */
    ConteneurFormes solution(ConteneurFormes premisse);
    
}
