/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fw.comunes;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author fMato
 */
public class Utils {

    /**
     * Pinta las trazas con un formato genérico para toda la aplicación.
     * 
     * @param request
     * @param clase
     * @param mensaje
     */
    static public void pintaTrazas(HttpServletRequest request,String clase,String mensaje){
        String ip=request.getLocalAddr();
        String hora="";

        Calendar cal = new GregorianCalendar();
        String diaFechaHoy=Integer.toString(cal.get(Calendar.DATE));
        String mesFechaHoy=Integer.toString(cal.get(Calendar.MONTH)+1);
        String yearFechaHoy=Integer.toString(cal.get(Calendar.YEAR));
        String horaActual=Integer.toString(cal.get(Calendar.HOUR_OF_DAY));
        String minutosActual=Integer.toString(cal.get(Calendar.MINUTE));
        String segundosActual=Integer.toString(cal.get(Calendar.SECOND));
        String milisegundosActual=Integer.toString(cal.get(Calendar.MILLISECOND));

        if(diaFechaHoy.length()==1)diaFechaHoy="0"+diaFechaHoy;
        if(mesFechaHoy.length()==1)mesFechaHoy="0"+mesFechaHoy;

        hora=diaFechaHoy+"/"+mesFechaHoy+"/"+yearFechaHoy+" "+horaActual+":"+minutosActual+":"+segundosActual+":"+milisegundosActual;

        System.out.println("["+clase+"]["+hora+"]["+ip+"] "+mensaje);
        }

    /**
     * Comprueba si el usuario de la solicitud tiene el perfil correcto para acceder a la pantalla.
     * 
     * @param lista_perfiles_pantalla
     * @param perfil_usuario
     * @return
     */
    static public boolean compruebaPerfiles(ArrayList lista_perfiles_pantalla, String perfil_usuario){
        boolean acceso=false;
        for(int i=0; i<lista_perfiles_pantalla.size(); i++){
            if(perfil_usuario.equals((String)lista_perfiles_pantalla.get(i)))
                acceso=true;
            }

        return acceso;
        }

    /**
     * Realiza la conexión de usuario.
     * 
     * @param request
     * @param session 
     * @return
     */
    static public boolean conexion(HttpServletRequest request,HttpSession session) throws SQLException, ErrorControlado{
        boolean conexion_realizada=false;

        String usuario=Utils.getParam(request,"ident_usu"); // Usuario que quiere conectarse.
        String pass=Utils.getParam(request,"pass_encp"); // Password encriptada.

        HashMap datosRecuperados=com.f1.bbdd.AccesosBBDD.getDatosUsuarioSQL(usuario);

        String recup_activado=(String)datosRecuperados.get("activado");
        String recup_pass=(String)datosRecuperados.get("pass_usuario");
        String recup_perfil=(String)datosRecuperados.get("perfil");

        if(!"S".equals(recup_activado)){
            conexion_realizada=false;
            throw new ErrorControlado("El usuario todavía no está activado.");
            }
        else if(!pass.equals(recup_pass)){
            conexion_realizada=false;
            throw new ErrorControlado("Contraseña incorrecta.");
            }
        else{
            session.setAttribute("haySession", Boolean.valueOf(true));
            session.setAttribute("usuario", usuario);
            session.setAttribute("perfil", recup_perfil);
            conexion_realizada=true;
            }

        return conexion_realizada;
        }

    /**
     * Recupera un parámetro de la request valida que no sea null.
     * 
     * @param request
     * @param param
     * @return
     */
    static public String getParam(HttpServletRequest request,String param){
        String parametro="";

        parametro=request.getParameter(param);
        if(parametro==null)parametro="";

        return parametro;
        }

    /**
     * Comprueba que la petición solicitada es correcta.
     *
     * @param peticion
     * @return
     */
    static public boolean compruebaPeticionCorrecta(String peticion){
        boolean correcta=true;

        // Comprobamos que la petición solicitada tiene un fichero asociado.
        String sFichero = "/var/lib/tomcat5.5/config/www/"+peticion+".xml";
        File fichero = new File(sFichero);
        if(!fichero.exists()){
            correcta=false;
        }

        return correcta;
        }

    /**
     * Lee de un fichero los datos necesarios para la conexión a la base de datos.
     */
    static public HashMap estableceDatosConexionDB() throws ParserConfigurationException, SAXException, IOException, ErrorControlado{
        /**
         * El fichero debe tener esta estructura
         *
         * <database>
                <url></url>
                <user></user>
                <password></password>
            </database>
         */
        HashMap conexionDB=new HashMap();
        String sFichero = "/var/lib/tomcat5.5/config/datos/database.xml";
        File fichero = new File(sFichero);
        if(fichero.exists()){
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder;
            builder = factory.newDocumentBuilder();
            Document docConfigDB;
            docConfigDB = builder.parse(fichero);

            NodeList listaURL=docConfigDB.getElementsByTagName("url");
            NodeList listaUser=docConfigDB.getElementsByTagName("user");
            NodeList listaPass=docConfigDB.getElementsByTagName("password");

            if(listaURL.getLength()>1 || listaUser.getLength()>1 || listaPass.getLength()>1 ||
                    listaURL.getLength()==0 || listaUser.getLength()==0 || listaPass.getLength()==0){
                throw new ErrorControlado("Los datos de conexión a la base de datos son erroneos.");
                }

            Node nURL=listaURL.item(0);
            String url=nURL.getTextContent();

            Node nUser=listaUser.item(0);
            String user=nUser.getTextContent();

            Node nPass=listaPass.item(0);
            String password=nPass.getTextContent();

            conexionDB.put("url", url);
            conexionDB.put("user", user);
            conexionDB.put("password", password);

            }
        else{
            throw new ErrorControlado("Falta el fichero de configuración para conectar con la base de datos.");
            }
        
        return conexionDB;
        }

    /**
     * Carga un objeto de tipo DatosPantalla con los datos de la pantalla correspondiente a la petición solicitada.
     * @param peticion 
     * @return
     */
    static public DatosPantalla cargaObjetoPantalla(String peticion) throws ParserConfigurationException, SAXException, IOException, ErrorControlado{
        /**
         * El fichero debe tener esta estructura
         * 
            <pantalla>
                <titulo></titulo>
                <java></java>
                <jsp></jsp>
                <conexion></conexion>
                <perfiles>
                    <perfil></perfil>
                    <perfil></perfil>
                </perfiles>
            </pantalla>
         */


        DatosPantalla pantalla = new DatosPantalla();

        String sFichero = "/var/lib/tomcat5.5/config/www/"+peticion+".xml";
        File fichero = new File(sFichero);
        if(fichero.exists()){
            
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder;
            builder = factory.newDocumentBuilder();
            Document docConfigPeticion;
            docConfigPeticion = builder.parse(fichero);

            NodeList listaTitulos=docConfigPeticion.getElementsByTagName("titulo");
            if(listaTitulos.getLength()>1){
                throw new ErrorControlado("Fichero asociado a la petición erroneo. Node <titulo> repetido");
                }
            NodeList listaClasesJava=docConfigPeticion.getElementsByTagName("java");
            if(listaClasesJava.getLength()>1){
                throw new ErrorControlado("Fichero asociado a la petición erroneo. Node <java> repetido");
                }
            NodeList listaJsp=docConfigPeticion.getElementsByTagName("jsp");
            if(listaJsp.getLength()>1){
                throw new ErrorControlado("Fichero asociado a la petición erroneo. Node <jsp> repetido");
                }
            NodeList listaConexion=docConfigPeticion.getElementsByTagName("conexion");
            if(listaConexion.getLength()>1){
                throw new ErrorControlado("Fichero asociado a la petición erroneo. Node <conexion> repetido");
                }
            NodeList listaPerfiles=docConfigPeticion.getElementsByTagName("perfiles");
            if(listaPerfiles.getLength()>1){
                throw new ErrorControlado("Fichero asociado a la petición erroneo. Node <perfiles> repetido");
                }

            Node nTituloPantalla=listaTitulos.item(0);
            String tituloPantalla=nTituloPantalla.getTextContent();
            pantalla.setTitulo(tituloPantalla);

            Node nClaseJava=listaClasesJava.item(0);
            String claseJava=nClaseJava.getTextContent();
            pantalla.setManejadora(claseJava);

            Node nJsp=listaJsp.item(0);
            String jsp=nJsp.getTextContent();
            pantalla.setJsp(jsp);
            
            Node nConexion=listaConexion.item(0);
            String conexion=nConexion.getTextContent();
            pantalla.setRequiereConexion(conexion);

            ArrayList perfiles=new ArrayList();
            NodeList listaPerfil=docConfigPeticion.getElementsByTagName("perfil");
            for(int i=0; i<listaPerfil.getLength(); i++){
                Node nPerfil=listaPerfil.item(i);
                String nombreNodo=nPerfil.getNodeName();
                if("perfil".equals(nombreNodo)){
                    String valorNodo=nPerfil.getTextContent();
                    if(valorNodo==null)valorNodo="";
                    perfiles.add(valorNodo);
                    }
                }
            pantalla.setPerfiles(perfiles);
        }

        return pantalla;
        }

    public static String NombreMeses(String mes){
        String nombreMes="";

        if(mes.equals("1") || mes.equals("01")){
		nombreMes="Enero";
	}else if(mes.equals("2") || mes.equals("02")){
		nombreMes="Febrero";
	}else if(mes.equals("3") || mes.equals("03")){
		nombreMes="Marzo";
	}else if(mes.equals("4") || mes.equals("04")){
		nombreMes="Abril";
	}else if(mes.equals("5") || mes.equals("05")){
		nombreMes="Mayo";
	}else if(mes.equals("6") || mes.equals("06")){
		nombreMes="Junio";
	}else if(mes.equals("7") || mes.equals("07")){
		nombreMes="Julio";
	}else if(mes.equals("8") || mes.equals("08")){
		nombreMes="Agosto";
	}else if(mes.equals("9") || mes.equals("09")){
		nombreMes="Septiembre";
	}else if(mes.equals("10")){
		nombreMes="Octubre";
	}else if(mes.equals("11")){
		nombreMes="Noviembre";
	}else if(mes.equals("12")){
		nombreMes="Diciembre";
	}

        return nombreMes;
    }

}
