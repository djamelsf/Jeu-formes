/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package State;

import model.Forme;
import java.awt.event.MouseEvent;

/**
 * Interface représentant les divers ecouteurs afin de connaitre les différentes actions a réaliser.
 * @author mac
 */
public interface EtatForme {

    void mousePressed(MouseEvent e);
    void mouseDragged(MouseEvent e);
    void mouseReleased(MouseEvent e);
    void mouseClicked(MouseEvent e);
    

}
