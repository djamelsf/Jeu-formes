/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Strategy;

import java.util.Random;
import model.Cercle;
import model.ConteneurFormes;
import model.Forme;
import model.Point;
import model.Rectangle;
import view.VueConteneur;
import static view.VueConteneur.hauteur;
import static view.VueConteneur.largeur;

/**
 * cette classe permet de gènerer 4 formes aleatoires mais en se bassent sur le score du joueur, elle essaye d'avoir un score plus grand
 * @author mac
 */
public class HardStrategy implements SolutionStrategy {
    /**
     * La fonction solution de HardStrategy qui génère 4 formes aleatoires sans faire collision avec la premisse
     * et essaye de faire un score superieure a celui du joueur, si l'essaie ecoule plus d'une seconde alors elle vise le score du joueur - 5
     * @param premisse une liste qui contient les 4 formes initiales 
     * @return elle retourne une solution en une liste de 4 formes 
     */
    @Override
    public ConteneurFormes solution(ConteneurFormes premisse) {
        ConteneurFormes sol;
        double scoreMax = VueConteneur.score;
        long lStartTime = System.nanoTime();
        long lEndTime;
        long output;
        do {
            sol = generateUneSolution(premisse);
            lEndTime = System.nanoTime();
            output = (lEndTime - lStartTime) / 1000000000;
            if (output >= 1) {
                scoreMax -= 5;
                lStartTime = System.nanoTime();
            }
        } while (calculScore(sol) < scoreMax);
        return sol;
    }
    /**
     * Une fonction qui calcule le score de 4 formes (somme des surfaces des formes) / surface du JPanel
     * @param conteneurFormes une liste de 4 formes
     * @return le score
     */
    public double calculScore(ConteneurFormes conteneurFormes) {
        double sc;
        double sum = 0;
        for (int i = 0; i < conteneurFormes.getFormes().size(); i++) {
            sum += conteneurFormes.getFormes().get(i).calculSurface();
        }
        sc = sum / (largeur * hauteur) * 100;
        return sc;
    }
    /**
     * Cette fonction genere 4 formes aleatoires sans faire collision avec la premisse
     * @param premisse 4 fomres initiales
     * @return une solution (4 formes)
     */
    public ConteneurFormes generateUneSolution(ConteneurFormes premisse) {
        ConteneurFormes sol = new ConteneurFormes();
        ConteneurFormes tmp = new ConteneurFormes();
        tmp.getFormes().addAll(premisse.getFormes());
        for (int i = 0; i < 4; i++) {
            int bool = (int) (Math.random() * 2);
            if (bool == 0) {
                Forme r = generateRectangle(tmp, 50, 1700);
                sol.add(r);
                tmp.add(r);
            } else {
                Forme c = generateCercle(tmp, 50, 1700);
                sol.add(c);
                tmp.add(c);
            }
        }
        return sol;
    }
    
    /**
     * Une fonction génère un rectangle aleatoire 
     * @param premisse la liste des formes precedentes 
     * @param min largeur/hauteur minimale
     * @param max largeur/hauteur maximale
     * @return Une forme (Rectangle)
     */
    public static Forme generateRectangle(ConteneurFormes premisse, double min, double max) {
        Rectangle rectangle;
        do {
            Random r = new Random();
            double largeur = min + (max - min) * r.nextDouble();
            double hauteur = min + (max - min) * r.nextDouble();
            double x = VueConteneur.largeur * r.nextDouble();
            double y = VueConteneur.hauteur * r.nextDouble();
            rectangle = new Rectangle(new Point(x, y), largeur, hauteur);
        } while (rectangle.collision(premisse) || !rectangle.formeValidee());
        return rectangle;
    }
    
    /**
     * Une fonction génère un Cercle aleatoire 
     * @param premisse la liste des formes precedentes 
     * @param min rayon minimal
     * @param max rayon maximal
     * @return Une forme (Cercle)
     */
    public static Forme generateCercle(ConteneurFormes premisse, double min, double max) {
        Cercle cercle;
        do {
            Random r = new Random();
            double rayon = min + (max - min) * r.nextDouble();
            double x = VueConteneur.largeur * r.nextDouble();
            double y = VueConteneur.hauteur * r.nextDouble();
            cercle = new Cercle(new Point(x, y), rayon);
        } while (cercle.collision(premisse) || !cercle.formeValidee());
        return cercle;
    }

}
