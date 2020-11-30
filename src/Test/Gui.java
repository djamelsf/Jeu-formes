/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Test;

import java.awt.Color;
import java.awt.HeadlessException;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import view.VueConteneur;

/**
 *
 * @author mac
 */
public class Gui extends JFrame {

    public Gui() throws HeadlessException {
        this.setSize(1800,800);
        this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
        VueConteneur vueConteneur = new VueConteneur();
        vueConteneur.setSize(500, 500);
        vueConteneur.setBackground(Color.black);
        this.add(vueConteneur);

        this.setTitle("SurfaMax !");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        new Gui();
    }

}
