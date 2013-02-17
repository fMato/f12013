/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.f1.manejadoras;

import com.fw.comunes.ErrorControlado;
import com.fw.comunes.PantallaWeb;
import com.fw.comunes.Utils;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Mato
 */
public class MiApuesta implements PantallaWeb {

    public HttpServletRequest processRequest(HttpServletRequest request, HttpServletResponse response) throws ErrorControlado {
        
        try {
            HttpSession session = request.getSession(true);
            String usuario_conectado=(String)session.getAttribute("usuario");

            ArrayList listaPilotos=new ArrayList();
            listaPilotos=com.f1.bbdd.AccesosBBDD.getDatosPilotosSQL("A");
            if(listaPilotos==null)listaPilotos=new ArrayList();
            request.setAttribute("listaPilotos", listaPilotos);

            HashMap proximaCarrera=com.f1.bbdd.AccesosBBDD.getDatosProximaCarreraSQL();
            if(proximaCarrera==null)proximaCarrera=new HashMap();
            request.setAttribute("proximaCarrera", proximaCarrera);

            String fecha_cierre=(String)proximaCarrera.get("fecha_cierre");
            if(fecha_cierre==null) fecha_cierre="";

            long milisegAhora=0;
            long milisegCierre=0;
            boolean permiteGuardar=false;

            SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            Date dCierre;
            try {
                dCierre=sdf.parse(fecha_cierre);
                milisegAhora=System.currentTimeMillis();
                milisegCierre=dCierre.getTime();

                if(milisegAhora>milisegCierre){
                    request.setAttribute("permiteGuardar", "N");
                    permiteGuardar=false;
                }else{
                    request.setAttribute("permiteGuardar", "S");
                    permiteGuardar=true;
                }
            } catch (ParseException ex) {
                request.setAttribute("permiteGuardar", "N");
                permiteGuardar=false;
            }

            String accion=Utils.getParam(request, "accion");

            if("guardar".equals(accion)){
                String pole=Utils.getParam(request, "pole");
                String primero=Utils.getParam(request, "primero");
                String segundo=Utils.getParam(request, "segundo");
                String tercero=Utils.getParam(request, "tercero");
                String cuarto=Utils.getParam(request, "cuarto");
                String quinto=Utils.getParam(request, "quinto");
                String sexto=Utils.getParam(request, "sexto");
                String septimo=Utils.getParam(request, "septimo");
                String octavo=Utils.getParam(request, "octavo");
                String noveno=Utils.getParam(request, "noveno");
                String decimo=Utils.getParam(request, "decimo");

                if(!permiteGuardar){
                    throw new ErrorControlado("La apuesta no se puede guardar, se ha pasado la hora de cierre, jugarás con la apuesta de la carrera anterior.");
                    }

                if("".equals(pole) || "".equals(primero) || "".equals(segundo) || "".equals(tercero) || "".equals(cuarto) || "".equals(quinto) ||
                        "".equals(sexto) || "".equals(septimo) || "".equals(octavo) || "".equals(noveno) || "".equals(decimo)){
                    throw new ErrorControlado("Has dejado algún puesto en blanco.");
                    }

                if(validarPosiciones(request)){
                    int resultado_guardado=com.f1.bbdd.AccesosBBDD.altaApuestaProxCarreraSQL(usuario_conectado, pole, primero, segundo, tercero, cuarto, quinto, sexto, septimo, octavo, noveno, decimo);
                    }
                else{
                    throw new ErrorControlado("Hay pilotos repetidos entre los 10 primeros.");
                    }
                
                request.setAttribute("guardado", "S");
                }

            

            HashMap proximaApuesta=com.f1.bbdd.AccesosBBDD.getApuestaProximaCarreraSQL(usuario_conectado);
            if(proximaApuesta==null)proximaApuesta=new HashMap();
            request.setAttribute("proximaApuesta", proximaApuesta);
            
        } catch (SQLException ex) {
            throw new ErrorControlado(ex.getMessage());
        }
        
        return request;
    }

    public boolean validarPosiciones(HttpServletRequest request){
        boolean correcto=true;

        ArrayList posiciones = new ArrayList();

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

        if(primero==null) primero="";
        if(segundo==null) segundo="";
        if(tercero==null) tercero="";
        if(cuarto==null) cuarto="";
        if(quinto==null) quinto="";
        if(sexto==null) sexto="";
        if(septimo==null) septimo="";
        if(octavo==null) octavo="";
        if(noveno==null) noveno="";
        if(decimo==null) decimo="";

        posiciones.add(primero);
        posiciones.add(segundo);
        posiciones.add(tercero);
        posiciones.add(cuarto);
        posiciones.add(quinto);
        posiciones.add(sexto);
        posiciones.add(septimo);
        posiciones.add(octavo);
        posiciones.add(noveno);
        posiciones.add(decimo);

        for(int i=0; i<posiciones.size();i++)
            for(int j=i+1; j<posiciones.size(); j++)
                if(posiciones.get(i).equals(posiciones.get(j)))
                    correcto=false;

        return correcto;
    }

}
