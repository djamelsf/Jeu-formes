/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import model.Forme;

/**
 *
 * @author mac
 */
public interface ConteneurListener {
    void formeAjoutee(Forme forme);
    void formeRetiree(Forme forme);
}
