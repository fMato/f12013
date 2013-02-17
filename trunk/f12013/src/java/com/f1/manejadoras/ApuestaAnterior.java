/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.f1.manejadoras;

import com.fw.comunes.ErrorControlado;
import com.fw.comunes.PantallaWeb;
import com.fw.comunes.Utils;
import java.sql.SQLException;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Mato
 */
public class ApuestaAnterior implements PantallaWeb{

    public HttpServletRequest processRequest(HttpServletRequest request, HttpServletResponse response) throws ErrorControlado {
        
        try {
            String usuario=Utils.getParam(request, "jugador");
            String carrera=Utils.getParam(request, "carrera");
            
            HashMap carreraAnterior=(HashMap)com.f1.bbdd.AccesosBBDD.getDatosCarreraAnteriorSQL(usuario, carrera);
            if(carreraAnterior==null)carreraAnterior=new HashMap();
            request.setAttribute("carreraAnterior", carreraAnterior);

            HashMap resultadoCarrera=(HashMap)com.f1.bbdd.AccesosBBDD.getResultadoCarreraSQL(carrera);
            if(resultadoCarrera==null)resultadoCarrera=new HashMap();
            request.setAttribute("resultadoCarrera", resultadoCarrera);

            String nombre_usuario=(String)com.f1.bbdd.AccesosBBDD.getNombreUsuarioSQL(usuario);
            if(nombre_usuario==null)nombre_usuario="";
            request.setAttribute("nombre_usuario", nombre_usuario);

            String nombre_carrera=(String)com.f1.bbdd.AccesosBBDD.getNombreCarreraSQL(carrera);
            if(nombre_carrera==null)nombre_carrera="";
            request.setAttribute("nombre_carrera", nombre_carrera);

            HashMap nombresPilotos=(HashMap)com.f1.bbdd.AccesosBBDD.getListaNombrePilotosSQL();
            if(nombresPilotos==null)nombresPilotos=new HashMap();
            request.setAttribute("nombresPilotos", nombresPilotos);

            String puntos_carrera=(String) com.f1.bbdd.AccesosBBDD.getPuntosCarrerasSQL(usuario, carrera);
            if(puntos_carrera==null)puntos_carrera="";
            
            if("".equals(puntos_carrera)){
                request.setAttribute("carrera_disputada", "N");
                puntos_carrera="<i>Carrera no disputada</i>";
                }
            else{
                request.setAttribute("carrera_disputada", "S");
                }
            
            request.setAttribute("puntos_carrera", puntos_carrera);
            
        } catch (SQLException ex) {
            throw new ErrorControlado(ex.getMessage());
        }

        return request;
    }

}
