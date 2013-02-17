/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fw.comunes;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author fMato
 */
public interface PantallaWeb {
    HttpServletRequest processRequest (HttpServletRequest request, HttpServletResponse response) throws ErrorControlado;
}
