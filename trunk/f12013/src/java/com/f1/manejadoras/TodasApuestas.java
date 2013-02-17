/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.f1.manejadoras;

import com.fw.comunes.ErrorControlado;
import com.fw.comunes.PantallaWeb;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Mato
 */
public class TodasApuestas implements PantallaWeb{

    public HttpServletRequest processRequest(HttpServletRequest request, HttpServletResponse response) throws ErrorControlado {

        try{
        // Lista de carreras para saber cuantas columnas pintar.
            ArrayList listaCarreras = com.f1.bbdd.AccesosBBDD.getDatosCarrerasSQL();
            if(listaCarreras==null)listaCarreras=new ArrayList();
            request.setAttribute("listaCarreras", listaCarreras);
            
            HashMap apuestasAnteriores = com.f1.bbdd.AccesosBBDD.getApuestasAnterioresSQL();
            if(apuestasAnteriores==null)apuestasAnteriores=new HashMap();
            request.setAttribute("apuestasAnteriores", apuestasAnteriores);

        } catch (SQLException ex) {
            throw new ErrorControlado(ex.getMessage());
        }

        return request;
    }

}
