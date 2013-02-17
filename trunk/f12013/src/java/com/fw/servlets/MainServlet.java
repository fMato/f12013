/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fw.servlets;

import com.f1.bbdd.BaseDeDatos;
import com.fw.comunes.DatosPantalla;
import com.fw.comunes.ErrorControlado;
import com.fw.comunes.PantallaWeb;
import com.fw.comunes.Utils;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author fMato
 */
public class MainServlet extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */

    String mensajeError;
    String templatePresentacion="/Pantallas/template_inicio.jsp";
    String peticion;
    
    String perfil_usuario;

    DatosPantalla pantalla;
    boolean requiere_conexion; // Indica si la pantalla solicitada necesita tener un usuario conectado.
    String jspPresentacion;
    String tituloPantalla;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        try {
            mensajeError="";
            peticion="";
            perfil_usuario="";
            requiere_conexion=false; // Indica si la pantalla solicitada necesita tener un usuario conectado.
            jspPresentacion="";
            tituloPantalla="";

            Utils.pintaTrazas(request,this.getClass().getName(),"Inicio del tratamiento de la petición.");
            HttpSession session = request.getSession(true);

            HashMap conexionDB=Utils.estableceDatosConexionDB();
            if(conexionDB==null)conexionDB=new HashMap();

            String url=(String)conexionDB.get("url");
            String user=(String)conexionDB.get("user");
            String password=(String)conexionDB.get("password");
            if(url==null)url="";
            if(user==null)user="";
            if(password==null)password="";

            new BaseDeDatos(url,user,password);

            ArrayList opcionesMenu=com.f1.bbdd.AccesosBBDD.getMenu();
            if(opcionesMenu==null)opcionesMenu=new ArrayList();
            request.setAttribute("opcionesMenu", opcionesMenu);

            Boolean hay_conexion_obj=(Boolean)session.getAttribute("haySession"); // Recupera de sesión el objeto que identifica si hay conectado un usuario.
            if(hay_conexion_obj==null)hay_conexion_obj=Boolean.valueOf(false);
            boolean hay_conexion=hay_conexion_obj.booleanValue(); // Indica si hay un usuario conectado.
            boolean proceso_conexion=false; // Indica si estamos en proceso de conexión.

            String p_proceso_conexion=Utils.getParam(request, "proceso_conexion");
            if("S".equals(p_proceso_conexion))
                proceso_conexion=true;
            else
                proceso_conexion=false;

            if(hay_conexion){ // Dejamos una traza indicando si ya hay una conexión establecida.

                String usuario_conectado=(String)session.getAttribute("usuario");
                perfil_usuario=(String)session.getAttribute("perfil");
                if(perfil_usuario==null)perfil_usuario="";
                Utils.pintaTrazas(request,this.getClass().getName(),"El usuario "+usuario_conectado+" está conectado.");

            }else{
                    Utils.pintaTrazas(request,this.getClass().getName(),"No hay usuario conectado.");
                }

            // Reconocer la peticion solicitada
            peticion=request.getServletPath();
            if(peticion==null) peticion="";
            Utils.pintaTrazas(request,this.getClass().getName(),"Petición a tratar: "+peticion);
            peticion=peticion.replaceFirst("/", "");
            peticion=peticion.replaceFirst(".html", "");

            // Comprobar que es correcta la petición
            if(Utils.compruebaPeticionCorrecta(peticion)){ // Si es correcta recuperar todos los datos de la petición.

                pantalla=Utils.cargaObjetoPantalla(peticion);
                jspPresentacion=pantalla.getJsp();
                tituloPantalla=pantalla.getTitulo();
                if("S".equals(pantalla.getRequiereConexion())){
                    requiere_conexion=true;
                    Utils.pintaTrazas(request, pantalla.getManejadora(), "La pantalla requiere conexión.");
                }else{
                    requiere_conexion=false;
                    Utils.pintaTrazas(request, pantalla.getManejadora(), "La pantalla NO requiere conexión.");
                    }
                
            }else{ // Si no es correcta mostrar error lógico
                Utils.pintaTrazas(request, pantalla.getManejadora(), "ERROR: La página solicitada no existe.");
                throw new ErrorControlado("La página solicitada no existe.");
            }
            
            if(requiere_conexion && !hay_conexion && !proceso_conexion){ // La pantalla requiere conexión, no hay usuario conectado y no estamos en proceso de conexión, mostramos la pantalla de conexión.
                jspPresentacion="login.jsp";
                tituloPantalla="Conexión de usuario";
                request.setAttribute("actionConex", peticion);
            }else if(proceso_conexion){ // Estamos en proceso de conexión.
                
                boolean conexion_correcta=Utils.conexion(request,session); // Realiza la conexión e indica si la conexión se ha realizado correctamente.

                if(conexion_correcta){ // La conexión se ha realizado correctamente. Tratamiento normal de la pantalla.
                    perfil_usuario=(String)session.getAttribute("perfil");
                    if(perfil_usuario==null)perfil_usuario="";
                    tratamientoPeticion(request, response);
                }else{ // La conexión no se ha realizado. Mostramos de nuevo la pantalla de conexión mostrando un mensaje.
                    jspPresentacion="login.jsp";
                    tituloPantalla="Conexión de usuario";
                    request.setAttribute("conexion_fallida", "S"); // Indicador de intento fallido de conexión.
                    request.setAttribute("actionConex", peticion);
                }

            }else{ // Tratamiento normal de la pantalla.
                tratamientoPeticion(request, response);
            }

        }catch(ErrorControlado exc){
            mensajeError="ERROR[C]: "+exc.getMessage();
        }catch(ClassNotFoundException exc){
            mensajeError="ERROR[S]: "+exc.getMessage();
            Utils.pintaTrazas(request,this.getClass().getName(),"ERROR: No se localiza la clase: "+pantalla.getManejadora());
        }catch(InstantiationException exc){
            mensajeError="ERROR[S]: "+exc.getMessage();
            Utils.pintaTrazas(request,this.getClass().getName(),"ERROR: No se puede crear una instancia de la clase: "+pantalla.getManejadora());
        }catch(Exception exc){
            mensajeError="ERROR[S]: "+exc.getMessage();
            Utils.pintaTrazas(request,this.getClass().getName(),"ERROR: "+exc.getMessage());
        }finally {
            if(!"".equals(mensajeError)){ // Se ha producido un error.
                Utils.pintaTrazas(request,this.getClass().getName(),"ERROR: Se ha producido un error durante el tratamiento de la petición.");
                request.setAttribute("ERROR",mensajeError); // Pasamos a la pantalla el indicador de error.
                jspPresentacion="error.jsp";
                tituloPantalla="ERROR";
                }

            request.setAttribute("jspPresentacion", jspPresentacion); // Jsp con el que va a pintar la pantalla.
            request.setAttribute("tituloPantalla", tituloPantalla); // Titulo de la pantalla que se va a pintar.
            RequestDispatcher rd = request.getRequestDispatcher (templatePresentacion);
            rd.include (request, response);

            Utils.pintaTrazas(request,this.getClass().getName(),"Fin del tratamiento de la petición.");

            out.close();
        }
    }

    /**
     * Trata la petición solicitada, comprueba perfiles y lanza la clase java si es necesario.
     *
     * @param request
     * @param response
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws ErrorLogico
     */
    private void tratamientoPeticion(HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException, InstantiationException, IllegalAccessException, ErrorControlado{
            if(!requiere_conexion || Utils.compruebaPerfiles(pantalla.getPerfiles(), perfil_usuario)){
                    if(!"".equals(pantalla.getManejadora())){ // Solamente si la pantalla tiene clase java manejadora.
                        PantallaWeb objetoManejador = (PantallaWeb) Class.forName(pantalla.getManejadora()).newInstance();
                        objetoManejador.processRequest(request, response);
                    }
                }else{
                    Utils.pintaTrazas(request, pantalla.getManejadora(), "ERROR: El perfil del usuario no permite ver la pantalla.");
                    throw new ErrorControlado("Careces de permisos para ver esta pantalla.");
                }
        }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
            processRequest(request, response);
    } 

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
            processRequest(request, response);
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
