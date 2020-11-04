/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import State.EtatCreerRectangle;
import State.EtatForme;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import javax.swing.JPanel;
import model.Cercle;
import model.ConteneurFormes;
import model.Forme;
import model.Rectangle;
import util.ConteneurListener;
import util.EcouteurFormes;

/**
 *
 * @author mac
 */
public class VueConteneur extends JPanel implements ConteneurListener, EcouteurFormes, MouseMotionListener, MouseListener {

    private ArrayList<Vue> vues;
    private ConteneurFormes conteneurFormes;
    private Rectangle rectangle;
    private Cercle cercle;
    private EtatForme etatForme;

    public VueConteneur() {
        this.vues = new ArrayList<>();
        this.conteneurFormes = new ConteneurFormes();
        addMouseMotionListener(this);
        addMouseListener(this);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D gr = (Graphics2D) g;
        for (Vue vue : vues) {
            vue.paint(gr);
        }
    }

    public void addVue(Vue vue) {
        vues.add(vue);
    }

    public void removeVue(int i) {
        vues.remove(i);
    }

    @Override
    public void formeAjoutee(Forme forme) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void formeRetiree(Forme forme) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void modeleMisAjour() {
        repaint();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        etatForme.mouseDragged(e);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        //
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        //
    }

    @Override
    public void mousePressed(MouseEvent e) {
        etatForme=new EtatCreerRectangle(this, conteneurFormes);
        etatForme.mousePressed(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        etatForme.mouseReleased(e);
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        //
    }

    @Override
    public void mouseExited(MouseEvent e) {
        //
    }

    public ArrayList<Vue> getVues() {
        return vues;
    }

    public void setVues(ArrayList<Vue> vues) {
        this.vues = vues;
    }
        

}
