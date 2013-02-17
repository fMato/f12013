/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fw.comunes;

import java.util.ArrayList;

/**
 *
 * @author fMato
 */
public class DatosPantalla {
    
    private String jspPresentacion="";
    private String tituloPantalla="";
    private String requiereConexion="";
    private ArrayList perfiles=new ArrayList();
    private String claseManejadora="";

    public DatosPantalla(){
            // Vacío.
        }

    public void setJsp(String jspPresentacionIn){
        jspPresentacion=jspPresentacionIn;
    }

    public String getJsp(){
        return jspPresentacion;
    }

    public void setTitulo(String tituloPantallaIn){
        tituloPantalla=tituloPantallaIn;
    }

    public String getTitulo(){
        return tituloPantalla;
    }

    public void setRequiereConexion(String requiereConexionIn){
        requiereConexion=requiereConexionIn;
    }

    public String getRequiereConexion(){
        return requiereConexion;
    }

    public void setPerfiles(ArrayList perfilesIn){
        perfiles=perfilesIn;
    }

    public ArrayList getPerfiles(){
        return perfiles;
    }

    public void setManejadora(String claseManejadoraIn){
        claseManejadora=claseManejadoraIn;
    }

    public String getManejadora(){
        return claseManejadora;
    }
}
