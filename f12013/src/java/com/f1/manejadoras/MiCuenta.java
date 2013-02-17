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
import javax.servlet.http.HttpSession;

/**
 *
 * @author Mato
 */
public class MiCuenta implements PantallaWeb{

    public HttpServletRequest processRequest(HttpServletRequest request, HttpServletResponse response) throws ErrorControlado {
        
        try {
            HttpSession session = request.getSession(true);
            String usuario_conectado=(String)session.getAttribute("usuario");
            String accion=Utils.getParam(request, "accion");
            
            if("guardar".equals(accion)){
                String correo=Utils.getParam(request, "correo");
                String pass=Utils.getParam(request, "pass");
                int resultado=com.f1.bbdd.AccesosBBDD.modificarDatosUsuarioSQL(usuario_conectado, correo, pass);
                request.setAttribute("resultadoGuardar", String.valueOf(resultado));
                }

            HashMap datosUsuario = com.f1.bbdd.AccesosBBDD.getDatosUsuarioSQL(usuario_conectado);
            request.setAttribute("datosUsuario", datosUsuario);
        } catch (SQLException ex) {
            throw new ErrorControlado(ex.getMessage());
        }

        return request;
    }

}
