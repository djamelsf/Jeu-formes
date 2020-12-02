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

/**
 *
 * @author mac
 */
public class EasyStrategy implements SolutionStrategy {
    
    /**
     * La fonction solution de EasyStrategy qui génère 
     * @param premisse
     * @return 
     */
    @Override
    public ConteneurFormes solution(ConteneurFormes premisse) {
        ConteneurFormes sol = new ConteneurFormes();
        //ConteneurFormes tmp = new ConteneurFormes(premisse.getFormes());
        ConteneurFormes tmp = new ConteneurFormes();
        tmp.getFormes().addAll(premisse.getFormes());
        for (int i = 0; i < 4; i++) {
            int bool = (int) (Math.random() * 2);
            if (bool == 0) {
                Forme r = generateRectangle(tmp, 50, 1000);
                sol.add(r);
                tmp.add(r);
            } else {
                Forme c = generateCercle(tmp, 50, 1000);
                sol.add(c);
                tmp.add(c);
            }
        }
        return sol;
    }

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
