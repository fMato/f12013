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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Mato
 */
public class InformarResultado implements PantallaWeb{

    public HttpServletRequest processRequest(HttpServletRequest request, HttpServletResponse response) throws ErrorControlado {

        try {
            String accion = Utils.getParam(request, "accion");

            if("cerrar".equals(accion)){
                System.out.println("cerrar");
                String carrera=Utils.getParam(request,"carrera");
                String[] param={carrera};
                com.f1.cerrarApuestas.Main.main(param);
                }

            if("guardar".equals(accion)){
                String carrera=Utils.getParam(request,"carrera");
                String pole=Utils.getParam(request,"pole");
                String primero=Utils.getParam(request,"primero");
                String segundo=Utils.getParam(request,"segundo");
                String tercero=Utils.getParam(request,"tercero");
                String cuarto=Utils.getParam(request,"cuarto");
                String quinto=Utils.getParam(request,"quinto");
                String sexto=Utils.getParam(request,"sexto");
                String septimo=Utils.getParam(request,"septimo");
                String octavo=Utils.getParam(request,"octavo");
                String noveno=Utils.getParam(request,"noveno");
                String decimo=Utils.getParam(request,"decimo");
                
                com.f1.bbdd.AccesosBBDD.guardarResultadoCarreraSQL(carrera, pole, primero, segundo, tercero, cuarto, quinto, sexto, septimo, octavo, noveno, decimo);

                String[] param={carrera};
                com.f1.contabilizar.Main.main(param);
                }
            
            ArrayList listaPilotos=new ArrayList();
            listaPilotos=com.f1.bbdd.AccesosBBDD.getDatosPilotosSQL("A");
            if(listaPilotos==null)listaPilotos=new ArrayList();
            request.setAttribute("listaPilotos", listaPilotos);

        // Lista de carreras para saber cuantas columnas pintar.
            ArrayList listaCarreras = com.f1.bbdd.AccesosBBDD.getDatosCarrerasSQL();
            if(listaCarreras==null)listaCarreras=new ArrayList();
            request.setAttribute("listaCarreras", listaCarreras);

        // Carrera que aparecerá marcada
            String ultimaCarreraDisputada=com.f1.bbdd.AccesosBBDD.getCarreraAnteriorSQL();
            if(ultimaCarreraDisputada==null)ultimaCarreraDisputada="";
            request.setAttribute("ultimaCarreraDisputada", ultimaCarreraDisputada);
            
        } catch (SQLException ex) {
            throw new ErrorControlado(ex.getMessage());
        }
        
        return request;
    }

}
