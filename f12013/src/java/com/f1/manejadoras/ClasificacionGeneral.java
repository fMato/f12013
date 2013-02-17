/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.f1.manejadoras;

import com.fw.comunes.ErrorControlado;
import com.fw.comunes.PantallaWeb;
import com.fw.comunes.Utils;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Mato
 */
public class ClasificacionGeneral implements PantallaWeb {

    public HttpServletRequest processRequest(HttpServletRequest request, HttpServletResponse response) throws ErrorControlado {
        try {

        // Lista de carreras para saber cuantas columnas pintar.
            ArrayList listaCarreras = com.f1.bbdd.AccesosBBDD.getDatosCarrerasSQL();
            if(listaCarreras==null)listaCarreras=new ArrayList();
            request.setAttribute("listaCarreras", listaCarreras);

       // HashMap que indica los puntos máximos de cada carrera.

            HashMap puntosMaximosDeCarreras=com.f1.bbdd.AccesosBBDD.getPuntosMaximosSQL();
            request.setAttribute("puntosMaximosDeCarreras", puntosMaximosDeCarreras);

        // Datos para pintar la tabla.
            HashMap datosClasificacion=com.f1.bbdd.AccesosBBDD.getClasificacionGeneralSQL();
            if(datosClasificacion==null)datosClasificacion=new HashMap();

        // Si se ha ordenado la tabla por una carrera concreto cambiamos la lista de usuarios que marca en que orden se pinta la tabla.
            String ordenar=Utils.getParam(request, "ordenar");
            if(!"".equals(ordenar)){
                ArrayList listaUsuarios=com.f1.bbdd.AccesosBBDD.getUsuariosOrdenadosPorCarreraSQL(ordenar);
                if(listaUsuarios.size()>0)
                    datosClasificacion.put("listaUsuarios", listaUsuarios);
                }
            
            request.setAttribute("datosClasificacion", datosClasificacion);

        } catch (SQLException ex) {
            throw new ErrorControlado(ex.getMessage());
        }
        return request;
    }

}
