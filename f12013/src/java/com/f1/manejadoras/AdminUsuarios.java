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
public class AdminUsuarios implements PantallaWeb{

    public HttpServletRequest processRequest(HttpServletRequest request, HttpServletResponse response) throws ErrorControlado {

        try {
            String accion=Utils.getParam(request, "accion");
            String usuario_modificacion=Utils.getParam(request, "usuario");
            
            if("activar".equals(accion)){
                int i=com.f1.bbdd.AccesosBBDD.cambiarEstadoUsuarioSQL(usuario_modificacion, "S");
                }
            else if("desactivar".equals(accion)){
                int i=com.f1.bbdd.AccesosBBDD.cambiarEstadoUsuarioSQL(usuario_modificacion, "N");
                }
            else if("borrar".equals(accion)){
                int i=com.f1.bbdd.AccesosBBDD.borrarUsuarioSQL(usuario_modificacion);
                }
            else if("adminSI".equals(accion)){
                int i=com.f1.bbdd.AccesosBBDD.cambiarPerfilUsuarioSQL(usuario_modificacion, "A");
                }
            else if("adminNO".equals(accion)){
                int i=com.f1.bbdd.AccesosBBDD.cambiarPerfilUsuarioSQL(usuario_modificacion, "U");
                }

            ArrayList listaUsuarios=com.f1.bbdd.AccesosBBDD.getUsuariosSQL();
            if(listaUsuarios==null)listaUsuarios=new ArrayList();

            ArrayList listaUsuariosDesactivados = new ArrayList();
            ArrayList listaUsuariosAdmin = new ArrayList();
            ArrayList listaUsuariosActivos = new ArrayList();

            for(int i=0; i<listaUsuarios.size(); i++){
                HashMap usuario = new HashMap();
                usuario=(HashMap)listaUsuarios.get(i);
                if(usuario==null)usuario = new HashMap();

                String activado=(String)usuario.get("activado");
                if(activado==null)activado="";
                String perfil=(String)usuario.get("perfil");
                if(perfil==null)perfil="";

                if(!"S".equals(activado)) // Desactivados.
                    listaUsuariosDesactivados.add(usuario);
                else // Activos
                    listaUsuariosActivos.add(usuario);

                if("A".equals(perfil)) // Administradores
                    listaUsuariosAdmin.add(usuario);
                }

            request.setAttribute("listaUsuariosDesactivados", listaUsuariosDesactivados);
            request.setAttribute("listaUsuariosAdmin", listaUsuariosAdmin);
            request.setAttribute("listaUsuariosActivos", listaUsuariosActivos);
            
        } catch (SQLException ex) {
            throw new ErrorControlado(ex.getMessage());
        }

        return request;
    }

    

}
