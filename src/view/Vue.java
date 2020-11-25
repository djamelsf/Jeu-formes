/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.Color;
import java.awt.Graphics2D;
import model.Forme;

/**
 *
 * @author mac
 */
public interface Vue {
    void paint(Graphics2D graphics2D);
    Forme getForme();
    Color getCouleur();

    @Override
    public boolean equals(Object obj);
    
}
