/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.f1.manejadoras;

import com.fw.comunes.ErrorControlado;
import com.fw.comunes.PantallaWeb;
import com.fw.comunes.Utils;
import java.sql.SQLException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Mato
 */
public class AltaUsuario implements PantallaWeb{

    public HttpServletRequest processRequest(HttpServletRequest request, HttpServletResponse response) throws ErrorControlado {

        String nombre_apell=Utils.getParam(request, "nombre_apell");
        String nick=Utils.getParam(request, "ident_usu");
        String correo=Utils.getParam(request, "email");
        String pass=Utils.getParam(request, "pass_encr");
        String normativa=Utils.getParam(request, "normativa");

        if("".equals(nombre_apell)){
            throw new ErrorControlado("El nombre es obligatorio.");
            }
        else if("".equals(nick)){
            throw new ErrorControlado("El nick es obligatorio.");
            }
        else if("".equals(correo)){
            throw new ErrorControlado("El correo es obligatorio.");
            }
        else if("".equals(pass)){
            throw new ErrorControlado("La contraseña es obligatoria.");
            }
        else if(!"S".equals(normativa)){
            throw new ErrorControlado("Es obligatorio aceptar la normativa.");
            }

        try {
            com.f1.bbdd.AccesosBBDD.altaUsuarioSQL(nick, nombre_apell, correo, pass);
            request.setAttribute("nombre_usuario", nombre_apell);
            request.setAttribute("email", correo);
        } catch (SQLException ex) {
            throw new ErrorControlado(ex.getMessage());
        }
        
        return request;
    }

}
