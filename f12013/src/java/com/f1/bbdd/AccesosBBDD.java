/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.f1.bbdd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

/**
 * 
 * @author Mato
 */
public class AccesosBBDD {

    /**
     * Devuelve las opciones de menú.
     * 
     * @return ArrayList que contiene en cada posición un HashMap con la información de una opción de menú. Cada HashMap de opción de menú contiene dos registros, el texto de la opción de menú (key: texto) y la url a la que debe ir (key: destino).
     */
    public static ArrayList getMenu() throws SQLException{
        System.out.println("Llamada a BBDD: getMenu()");

        HashMap menuItem = new HashMap();
        Connection conexion = BaseDeDatos.establecerConexion();
        ArrayList opcionesMenu = new ArrayList();

        String query="SELECT * "
                + "FROM menu "
                + "ORDER BY posicion ASC";

        PreparedStatement selectStatement = conexion.prepareStatement(query);
        ResultSet rs = selectStatement.executeQuery ();

        if(rs!=null){
            while(rs.next()){
                menuItem=new HashMap();
                
                String textoRecuperado=rs.getString("texto");
                if(textoRecuperado==null)textoRecuperado="";
                String destinoRecuperado=rs.getString("destino");
                if(destinoRecuperado==null) destinoRecuperado="";
                String requiere_conexion=rs.getString("requiere_conexion");
                if(requiere_conexion==null) requiere_conexion="";
                String perfiles=rs.getString("perfiles");
                if(perfiles==null) perfiles="";

                menuItem.put("texto", textoRecuperado);
                menuItem.put("url", destinoRecuperado);
                menuItem.put("requiere_conexion", requiere_conexion);
                menuItem.put("perfiles", perfiles);

                opcionesMenu.add(menuItem);
            }
        }

        BaseDeDatos.cerrarConexion(conexion);
        
        return opcionesMenu;
    }

    /**
     * Guarda en base de datos todos los datos de un nuevo usuario (el tipo de usuario por defecto va a U y los usuarios por defecto están desactivados).
     * 
     * @param nick
     * @param nombre
     * @param correo
     * @param pass
     * @throws SQLException
     */
    public static void altaUsuarioSQL(String nick, String nombre, String correo, String pass) throws SQLException {
        System.out.println("Llamada a BBDD: altaUsuarioSQL()");

        Connection conexion = BaseDeDatos.establecerConexion();
        String query="";

        query="INSERT INTO usuarios(ident_usuario,pass_usuario,nombre_usuario,correo_usuario,perfil,activado) "
                + "VALUES(?,?,?,?,?,?)";

        PreparedStatement selectStatement = conexion.prepareStatement(query);

        selectStatement.setString(1, nick);
        selectStatement.setString(2, pass);
        selectStatement.setString(3, nombre);
        selectStatement.setString(4, correo);
        selectStatement.setString(5, "U");
        selectStatement.setString(6, "N");

        selectStatement.executeUpdate();

        BaseDeDatos.cerrarConexion(conexion);
    }

    /**
     * Recupera el número de puntos que ha obtenido el ganador de cada carrera disputada.
     * 
     * @return HashMap con un registro por cada carrera disputada que contiene los puntos que obtuvo el ganador de esa carrera (key: número de la carrera).
     * @throws SQLException
     */
    public static HashMap getPuntosMaximosSQL() throws SQLException {
        System.out.println("Llamada a BBDD: getPuntosMaximosSQL()");

        Connection conexion = BaseDeDatos.establecerConexion();
        String query="SELECT ident_carrera, MAX(puntos) AS puntos "
                + "FROM resultados_apuestas "
                + "GROUP BY ident_carrera";

        Statement s = conexion.createStatement();
        ResultSet rs = s.executeQuery (query);

        HashMap puntosMaximos = new HashMap();
        if(rs!=null){
            while(rs.next()){
                puntosMaximos.put(rs.getString("ident_carrera"), rs.getString("puntos"));
            }
        }

        BaseDeDatos.cerrarConexion(conexion);
        
        return puntosMaximos;
    }

    /**
     * Devuelve un HashMap con una lista con los usuarios ordenados según sus puntos y además los puntos totales de cada usuario.
     * 
     * @return HashMap que devuevle un registro con la lista de usuarios ordenador por puntos (key: ListaUsuariosOrdenados) y un registro por cada usuario con los puntos totales del usuario (key: nick del usuario).
     * @throws SQLException
     */
    public static HashMap getPuntosTotalesSQL() throws SQLException {
        System.out.println("Llamada a BBDD: getPuntosTotalesSQL()");

        ArrayList usuariosOrdenados=new ArrayList();
        Connection conexion = BaseDeDatos.establecerConexion();
        String query="SELECT usuario, SUM(puntos) as puntos_totales "
                + "FROM resultados_apuestas "
                + "GROUP BY usuario "
                + "ORDER BY puntos_totales DESC, usuario ASC";

        Statement s = conexion.createStatement();
        ResultSet rs = s.executeQuery (query);

        HashMap resultado = new HashMap();
        if(rs!=null){
            while(rs.next()){
                resultado.put(rs.getString("usuario"), rs.getString("puntos_totales"));
                usuariosOrdenados.add(rs.getString("usuario"));
            }
        }
        resultado.put("ListaUsuariosOrdenados", usuariosOrdenados);

        BaseDeDatos.cerrarConexion(conexion);
        
        return resultado;
    }

    /**
     * Devuelve los datos de cada usuario para cada una de las carreras y el total de puntos que lleva en el campeonato
     * 
     * @param puntosTotales
     * @return HashMap cuya key es el nombre de usuario y que a su vez contiene un HashMap por usuario que contiene el nombre real del usuario (key: nombre), el número total de puntos del usuario (key: total) y el número de puntos obtenidos en cada carrera disputada (key: el número de carrera).
     * @throws SQLException
     */
    public static HashMap getPuntosCarrerasSQL(HashMap puntosTotales) throws SQLException {
        System.out.println("Llamada a BBDD: getPuntosCarrerasSQL()");

        Connection conexion = BaseDeDatos.establecerConexion();
        String query="SELECT a.usuario, a.carrera, a.puntos, b.nombre "
                + "FROM resultados_apuestas a, usuarios b "
                + "WHERE a.usuario=b.nick "
                + "ORDER BY a.usuario, a.carrera";

        Statement s = conexion.createStatement();
        ResultSet rs = s.executeQuery (query);

        HashMap resultadosTodosUsuarios = new HashMap();
        HashMap resultadosUnUsuario = new HashMap();
        String usuarioAnt="";
        if(rs!=null){
            while(rs.next()){
                String usuarioActual=rs.getString("usuario");
                String puntos=rs.getString("puntos");
                String carrera=rs.getString("carrera");
                String nombre=rs.getString("nombre");

                if(!usuarioActual.equals(usuarioAnt)){
                    resultadosUnUsuario = new HashMap();
                    resultadosUnUsuario.put("nombre", nombre);
                    resultadosUnUsuario.put("total", (String)puntosTotales.get(usuarioActual));
                    resultadosUnUsuario.put(carrera, puntos);
                    resultadosTodosUsuarios.put(usuarioActual, resultadosUnUsuario);
                }
                else{
                    resultadosUnUsuario.put(carrera, puntos);
                    resultadosTodosUsuarios.put(usuarioActual, resultadosUnUsuario);
                }
                usuarioAnt=usuarioActual;
            }
        }

        BaseDeDatos.cerrarConexion(conexion);
        
        return resultadosTodosUsuarios;
    }

    /**
     * Devuelve los datos de un usuario para una de las carreras
     *
     * @param puntosTotales
     * @return 
     * @throws SQLException
     */
    public static String getPuntosCarrerasSQL(String usuario, String carrera) throws SQLException {
        System.out.println("Llamada a BBDD: getPuntosCarrerasSQL()");

        Connection conexion = BaseDeDatos.establecerConexion();
        String query="SELECT puntos "
                + "FROM resultados_apuestas "
                + "WHERE ident_usuario=? AND ident_carrera=?";

        PreparedStatement selectStatement = conexion.prepareStatement(query);
        selectStatement.setString(1, usuario);
        selectStatement.setString(2, carrera);

        ResultSet rs = selectStatement.executeQuery ();

        String puntos="";

        if(rs!=null){
            while(rs.next()){
                puntos=rs.getString("puntos"); 
            }
        }

        BaseDeDatos.cerrarConexion(conexion);

        return puntos;
    }

    /**
     * Devuelve la fecha de la última actualización de la tabla apuestas por parte de cada usuario.
     * 
     * @return
     * @throws SQLException
     */
    public static HashMap getActividadApuestasSQL() throws SQLException {
        System.out.println("Llamada a BBDD: getActividadApuestasSQL()");

        Connection conexion = BaseDeDatos.establecerConexion();
        ArrayList nombreUsuario = new ArrayList();
        ArrayList actividadUsuario = new ArrayList();
        ArrayList colorCelda = new ArrayList();
        HashMap resultado=new HashMap();

        String query="SELECT a.nombre, b.fecha_modif "
                + "FROM usuarios a LEFT JOIN apuestas b ON (a.nick=b.usuario) "
                + "ORDER BY b.fecha_modif ASC";

        Statement s = conexion.createStatement();
        ResultSet rs = s.executeQuery (query);

        if(rs!=null){
            while(rs.next()){
                nombreUsuario.add(rs.getString("nombre"));
                String registro_fecha="";
                Date fecha=rs.getDate("fecha_modif");
                long milisegFecha=0;
                long milisegAhora=System.currentTimeMillis();
                if(fecha!=null){
                    milisegFecha=fecha.getTime();
                    long milisegDif=milisegAhora-milisegFecha;
                    System.out.println("Milisegundos de diferencia: "+milisegDif);
                    long diasDif=milisegDif/1000/3600/24;
                    registro_fecha="Hace aprox. "+diasDif+" días";
                    if(diasDif<=2)
                         colorCelda.add("#00FF00");
                    else if(diasDif<=4)
                         colorCelda.add("#FFFF00");
                    else if(diasDif<=7)
                         colorCelda.add("#FF9900");
                    else
                         colorCelda.add("#FF0000");
                }else{
                    registro_fecha="No hay apuesta.";
                    colorCelda.add("#FF0000");
                }
                actividadUsuario.add(registro_fecha);
            }
        }
        BaseDeDatos.cerrarConexion(conexion);

        resultado.put("nombreUsuario", nombreUsuario);
        resultado.put("actividadUsuario", actividadUsuario);
        resultado.put("colorCelda", colorCelda);

        return resultado;
    }

    /**
     * Devuelve los datos del usuario solicitado.
     *
     * @param usuario
     * @return HashMap con los datos de usuario
     * @throws SQLException
     */
    public static HashMap getDatosUsuarioSQL(String usuario) throws SQLException {
        System.out.println("Llamada a BBDD: getDatosUsuarioSQL()");

        Connection conexion = BaseDeDatos.establecerConexion();
        String query="SELECT * "
                + "FROM usuarios "
                + "WHERE ident_usuario=?";

        PreparedStatement selectStatement = conexion.prepareStatement(query);

        selectStatement.setString(1, usuario);

        ResultSet rs = selectStatement.executeQuery ();

        HashMap datosRecuperados=new HashMap();

        if(rs!=null){
            rs.next();
            datosRecuperados.put("ident_usuario", rs.getString("ident_usuario"));
            datosRecuperados.put("pass_usuario", rs.getString("pass_usuario"));
            datosRecuperados.put("nombre_usuario", rs.getString("nombre_usuario"));
            datosRecuperados.put("correo_usuario", rs.getString("correo_usuario"));
            datosRecuperados.put("perfil", rs.getString("perfil"));
            datosRecuperados.put("activado", rs.getString("activado"));
        }

        BaseDeDatos.cerrarConexion(conexion);
        return datosRecuperados;
    }

    /**
     * Actualiza el correo y contraseña del usuario.
     *
     * @param nick
     * @param correo
     * @param pass
     * @return Un entero resultado del update.
     * @throws SQLException
     */
    public static int modificarDatosUsuarioSQL(String usuario, String correo, String pass) throws SQLException {
        System.out.println("Llamada a BBDD: modificarDatosUsuarioSQL()");

        Connection conexion = BaseDeDatos.establecerConexion();
        String query="UPDATE usuarios "
                + "SET correo_usuario=?, pass_usuario=? "
                + "WHERE ident_usuario=?";

        PreparedStatement selectStatement = conexion.prepareStatement(query);

        selectStatement.setString(1, correo);
        selectStatement.setString(2, pass);
        selectStatement.setString(3, usuario);

        int i = selectStatement.executeUpdate();

        BaseDeDatos.cerrarConexion(conexion);

        return i;
    }

    /**
     * Guarda la apuesta de un usuario para la próxima carrera.
     *
     * @param usuario
     * @param pole
     * @param primero
     * @param segundo
     * @param tercero
     * @param cuarto
     * @param quinto
     * @param sexto
     * @param septimo
     * @param octavo
     * @param noveno
     * @param decimo
     * @return Un entero resultado del insert o update.
     * @throws SQLException
     */
    public static int altaApuestaProxCarreraSQL(String usuario, String pole, String primero, String segundo, String tercero, String cuarto, String quinto, String sexto, String septimo, String octavo, String noveno, String decimo) throws SQLException {
        System.out.println("Llamada a BBDD: altaApuestaProxCarreraSQL()");

        int i=0;

        Connection conexion = BaseDeDatos.establecerConexion();

        String query="INSERT INTO ultimas_apuestas (ident_usuario,fecha_modificacion,pole,primero,segundo,tercero,cuarto,quinto,sexto,septimo,octavo,noveno,decimo) "
                + "VALUES (?,NOW(),?,?,?,?,?,?,?,?,?,?,?) "
                + "ON DUPLICATE KEY "
                + "UPDATE ident_usuario=?, fecha_modificacion=NOW(), pole=?, primero=?, segundo=?, tercero=?, cuarto=?, quinto=?, sexto=?, septimo=?, octavo=?, noveno=?, decimo=?";

        PreparedStatement selectStatement = conexion.prepareStatement(query);
        
        selectStatement.setString(1, usuario);
        selectStatement.setString(2, pole);
        selectStatement.setString(3, primero);
        selectStatement.setString(4, segundo);
        selectStatement.setString(5, tercero);
        selectStatement.setString(6, cuarto);
        selectStatement.setString(7, quinto);
        selectStatement.setString(8, sexto);
        selectStatement.setString(9, septimo);
        selectStatement.setString(10, octavo);
        selectStatement.setString(11, noveno);
        selectStatement.setString(12, decimo);
        selectStatement.setString(13, usuario);
        selectStatement.setString(14, pole);
        selectStatement.setString(15, primero);
        selectStatement.setString(16, segundo);
        selectStatement.setString(17, tercero);
        selectStatement.setString(18, cuarto);
        selectStatement.setString(19, quinto);
        selectStatement.setString(20, sexto);
        selectStatement.setString(21, septimo);
        selectStatement.setString(22, octavo);
        selectStatement.setString(23, noveno);
        selectStatement.setString(24, decimo);     
        
        i = selectStatement.executeUpdate();

        BaseDeDatos.cerrarConexion(conexion);

        return i;
    }

    /**
     * Devuelve los pilotos que en la tabla están con el estado indicado. Si estado="TODOS", devuelve todos los pilotos de la tabla.
     * @param estadoPiloto
     * @return ArrayList con un HashMap por cada piloto que contiene los datos de este, número (key: numero), nombre (key: nombre), equipo (key: equipo) y estado (key: estado).
     * @throws SQLException
     */
    public static ArrayList getDatosPilotosSQL(String estadoPiloto) throws SQLException {
        System.out.println("Llamada a BBDD: getDatosPilotosSQL()");

        Connection conexion = BaseDeDatos.establecerConexion();
        String query="";
        
        if(estadoPiloto.equals("TODOS"))
            query="SELECT * "
                + "FROM pilotos "
                + "ORDER BY posicion_tabla ASC";
        else
            query="SELECT * "
                + "FROM pilotos "
                + "WHERE estado_piloto=? "
                + "ORDER BY posicion_tabla ASC";

        PreparedStatement selectStatement = conexion.prepareStatement(query);

        if(!estadoPiloto.equals("TODOS"))
            selectStatement.setString(1, estadoPiloto);

        ResultSet rs = selectStatement.executeQuery ();

        ArrayList datosRecuperados = new ArrayList();

        if(rs!=null){
            while(rs.next()){
                HashMap registroRecuperado=new HashMap();
                registroRecuperado.put("ident_piloto", rs.getString("ident_piloto"));
                registroRecuperado.put("numero_piloto", rs.getString("numero_piloto"));
                registroRecuperado.put("nombre_piloto", rs.getString("nombre_piloto"));
                registroRecuperado.put("equipo", rs.getString("equipo"));
                registroRecuperado.put("estado_piloto", rs.getString("estado_piloto"));
                registroRecuperado.put("posicion_tabla", rs.getString("posicion_tabla"));
                datosRecuperados.add(registroRecuperado);
            }
        }

        BaseDeDatos.cerrarConexion(conexion);

        return datosRecuperados;
    }

    /**
     * Devuelve los datos de la próxima carrera que se va a celebrar.
     * 
     * @return
     * @throws SQLException
     */
    public static HashMap getDatosProximaCarreraSQL() throws SQLException {
        System.out.println("Llamada a BBDD: getDatosProximaCarreraSQL()");

        Connection conexion = BaseDeDatos.establecerConexion();

        Calendar c = Calendar.getInstance();
        String dia = Integer.toString(c.get(Calendar.DATE));
        String mes = Integer.toString(c.get(Calendar.MONTH)+1);
        String annio = Integer.toString(c.get(Calendar.YEAR));

        String fecha_hoy=annio+"-"+mes+"-"+dia;
        String query="SELECT * "
                + "FROM carreras "
                + "WHERE fecha_carrera >= ?"
                + "limit 1";

        PreparedStatement selectStatement = conexion.prepareStatement(query);

        selectStatement.setString(1, fecha_hoy);

        ResultSet rs = selectStatement.executeQuery();

        HashMap registroRecuperado=new HashMap();

        if(rs!=null && rs.next()){

                String identificador_carrera=rs.getString("ident_carrera");
                if(identificador_carrera==null)identificador_carrera="";
                String nombre = rs.getString("nombre_carrera");
                if(nombre==null)nombre="";
                String fecha_carrera = rs.getString("fecha_carrera");
                if(fecha_carrera==null)fecha_carrera="";
                String fecha_cierre_apuestas = rs.getString("fecha_cierre");
                if(fecha_cierre_apuestas==null)fecha_cierre_apuestas="";

                registroRecuperado.put("ident_carrera", identificador_carrera);
                registroRecuperado.put("nombre_carrera", nombre);
                registroRecuperado.put("fecha_carrera", fecha_carrera);
                registroRecuperado.put("fecha_cierre", fecha_cierre_apuestas);

                String fecha_carrera_formateada=fecha_carrera.substring(8)+" de "+com.fw.comunes.Utils.NombreMeses(fecha_carrera.substring(5,7));
                String fecha_cierre_apuestas_formateada=fecha_cierre_apuestas.substring(8,10)+" de "+com.fw.comunes.Utils.NombreMeses(fecha_cierre_apuestas.substring(5,7))+" a las "+fecha_cierre_apuestas.substring(11,16);

                registroRecuperado.put("fecha_carrera_formateada", fecha_carrera_formateada);
                registroRecuperado.put("fecha_cierre_apuestas_formateada", fecha_cierre_apuestas_formateada);
        }

        BaseDeDatos.cerrarConexion(conexion);

        return registroRecuperado;
    }

    /**
     * Devuelve la apuesta del usuario para la próxima carrera.
     * @param usuario
     * @return
     * @throws SQLException
     */
    public static HashMap getApuestaProximaCarreraSQL(String usuario) throws SQLException {
        System.out.println("Llamada a BBDD: getApuestaProximaCarreraSQL()");

        Connection conexion = BaseDeDatos.establecerConexion();

        String query="SELECT * "
                + "FROM ultimas_apuestas "
                + "WHERE ident_usuario = ?";

        PreparedStatement selectStatement = conexion.prepareStatement(query);

        selectStatement.setString(1, usuario);

        ResultSet rs = selectStatement.executeQuery ();

        HashMap registroRecuperado=new HashMap();

        if(rs!=null){
                while(rs.next()){
                    String fecha_modificacion=rs.getString("fecha_modificacion");
                    String fecha_modificacion_formateada=fecha_modificacion.substring(8,10)+" de "+com.fw.comunes.Utils.NombreMeses(fecha_modificacion.substring(5,7))+" a las "+fecha_modificacion.substring(11,16);

                    registroRecuperado.put("fecha_modificacion", fecha_modificacion);
                    registroRecuperado.put("fecha_modificacion_formateada", fecha_modificacion_formateada);
                    registroRecuperado.put("pole", rs.getString("pole"));
                    registroRecuperado.put("primero", rs.getString("primero"));
                    registroRecuperado.put("segundo", rs.getString("segundo"));
                    registroRecuperado.put("tercero", rs.getString("tercero"));
                    registroRecuperado.put("cuarto", rs.getString("cuarto"));
                    registroRecuperado.put("quinto", rs.getString("quinto"));
                    registroRecuperado.put("sexto", rs.getString("sexto"));
                    registroRecuperado.put("septimo", rs.getString("septimo"));
                    registroRecuperado.put("octavo", rs.getString("octavo"));
                    registroRecuperado.put("noveno", rs.getString("noveno"));
                    registroRecuperado.put("decimo", rs.getString("decimo"));
                }
        }

        BaseDeDatos.cerrarConexion(conexion);
        
        return registroRecuperado;
    }

    /**
     * Devuelve el la relación numero-nombre de los pilotos.
     * @return HashMap con el numero de piloto como key y el nombre del piloto como valor.
     * @throws SQLException
     */
    public static HashMap getListaNombrePilotosSQL() throws SQLException {
        System.out.println("Llamada a BBDD: getListaNombrePilotosSQL()");

        Connection conexion = BaseDeDatos.establecerConexion();
        String query="SELECT ident_piloto, nombre_piloto FROM pilotos";

        Statement s = conexion.createStatement();
        ResultSet rs = s.executeQuery (query);

        HashMap listaPilotos = new HashMap();

        if(rs!=null){
            while(rs.next()){
                listaPilotos.put(rs.getString("ident_piloto"), rs.getString("nombre_piloto"));
            }
        }
        BaseDeDatos.cerrarConexion(conexion);

        return listaPilotos;
    }

    /**
     * Devuelve el nombre del usuario a partir del nick
     * @param usuario
     * @return String con el nombre de usuario.
     * @throws SQLException
     */
    public static String getNombreUsuarioSQL(String usuario) throws SQLException {
        System.out.println("Llamada a BBDD: getNombreUsuarioSQL()");

        Connection conexion = BaseDeDatos.establecerConexion();
        String query="SELECT nombre_usuario FROM usuarios WHERE ident_usuario=?";

        PreparedStatement selectStatement = conexion.prepareStatement(query);

        selectStatement.setString(1, usuario);

        ResultSet rs = selectStatement.executeQuery ();

        String nombreUsuario = "";

        if(rs!=null){
            while(rs.next()){
                nombreUsuario=rs.getString("nombre_usuario");
            }
        }
        BaseDeDatos.cerrarConexion(conexion);

        return nombreUsuario;
    }

    /**
     * Devuelve el nombre de la carrera indicada.
     * @param carrera
     * @return
     * @throws SQLException
     */
    public static String getNombreCarreraSQL(String carrera) throws SQLException {
        System.out.println("Llamada a BBDD: getNombreCarreraSQL()");

        Connection conexion = BaseDeDatos.establecerConexion();
        String query="SELECT nombre_carrera FROM carreras WHERE ident_carrera=?";

        PreparedStatement selectStatement = conexion.prepareStatement(query);

        selectStatement.setString(1, carrera);

        ResultSet rs = selectStatement.executeQuery ();

        String nombreCarrera = "";

        if(rs!=null){
            while(rs.next()){
                nombreCarrera=rs.getString("nombre_carrera");
            }
        }
        BaseDeDatos.cerrarConexion(conexion);

        return nombreCarrera;
    }

    /**
     * Recupera el resultado de una carrera
     * @param carrera
     * @return
     * @throws SQLException
     */
    public static HashMap getResultadoCarreraSQL(String carrera) throws SQLException {
        System.out.println("Llamada a BBDD: getResultadoCarreraSQL()");

        Connection conexion = BaseDeDatos.establecerConexion();
        String query="SELECT * FROM resultados_carreras WHERE ident_carrera=?";

        PreparedStatement selectStatement = conexion.prepareStatement(query);

        selectStatement.setString(1, carrera);

        ResultSet rs = selectStatement.executeQuery ();

        HashMap resultadoCarrera = new HashMap();

        if(rs!=null){
            while(rs.next()){
                resultadoCarrera.put("pole", rs.getString("pole"));
                resultadoCarrera.put("primero", rs.getString("primero"));
                resultadoCarrera.put("segundo", rs.getString("segundo"));
                resultadoCarrera.put("tercero", rs.getString("tercero"));
                resultadoCarrera.put("cuarto", rs.getString("cuarto"));
                resultadoCarrera.put("quinto", rs.getString("quinto"));
                resultadoCarrera.put("sexto", rs.getString("sexto"));
                resultadoCarrera.put("septimo", rs.getString("septimo"));
                resultadoCarrera.put("octavo", rs.getString("octavo"));
                resultadoCarrera.put("noveno", rs.getString("noveno"));
                resultadoCarrera.put("decimo", rs.getString("decimo"));
            }
        }
        
        BaseDeDatos.cerrarConexion(conexion);

        return resultadoCarrera;
    }

    /**
     * Devuelve los datos de un usario para una carrera ya disputada.
     * 
     * @param usuario
     * @param carrera
     * @return
     * @throws SQLException
     */
    public static HashMap getDatosCarreraAnteriorSQL(String usuario, String carrera) throws SQLException {
        System.out.println("Llamada a BBDD: getDatosCarreraAnteriorSQL()");

        Connection conexion = BaseDeDatos.establecerConexion();
        String query="SELECT * FROM apuestas WHERE ident_usuario=? AND ident_carrera=?";

        PreparedStatement selectStatement = conexion.prepareStatement(query);

        selectStatement.setString(1, usuario);
        selectStatement.setString(2, carrera);

        ResultSet rs = selectStatement.executeQuery ();

        HashMap apuestaCarrera = new HashMap();

        if(rs!=null){
            while(rs.next()){
                apuestaCarrera.put("pole", rs.getString("pole"));
                apuestaCarrera.put("primero", rs.getString("primero"));
                apuestaCarrera.put("segundo", rs.getString("segundo"));
                apuestaCarrera.put("tercero", rs.getString("tercero"));
                apuestaCarrera.put("cuarto", rs.getString("cuarto"));
                apuestaCarrera.put("quinto", rs.getString("quinto"));
                apuestaCarrera.put("sexto", rs.getString("sexto"));
                apuestaCarrera.put("septimo", rs.getString("septimo"));
                apuestaCarrera.put("octavo", rs.getString("octavo"));
                apuestaCarrera.put("noveno", rs.getString("noveno"));
                apuestaCarrera.put("decimo", rs.getString("decimo"));
            }
        }
        
        BaseDeDatos.cerrarConexion(conexion);

        return apuestaCarrera;
    }

    /**
     * Devuelve los datos de todos los usuarios de la aplicación.
     * 
     * @return
     * @throws SQLException
     */
    public static ArrayList getUsuariosSQL() throws SQLException {
        System.out.println("Llamada a BBDD: getUsuariosSQL()");

        ArrayList resultado = new ArrayList();
        Connection conexion = BaseDeDatos.establecerConexion();
        String query="SELECT * FROM usuarios ORDER BY nombre_usuario ASC";

        Statement s = conexion.createStatement();
        ResultSet rs = s.executeQuery (query);

        if(rs!=null){
            while(rs.next()){
                String nombre_usuario=(String)rs.getString("nombre_usuario");
                String ident_usuario=(String)rs.getString("ident_usuario");
                String correo_usuario=(String)rs.getString("correo_usuario");
                String perfil=(String)rs.getString("perfil");
                String activado=(String)rs.getString("activado");

                HashMap usuario = new HashMap();
                usuario.put("nombre_usuario", nombre_usuario);
                usuario.put("ident_usuario", ident_usuario);
                usuario.put("correo_usuario", correo_usuario);
                usuario.put("perfil", perfil);
                usuario.put("activado", activado);

                resultado.add(usuario);
            }
        }
        
        BaseDeDatos.cerrarConexion(conexion);

        return resultado;
    }

    /**
     * Devuelve los datos de todos los usuarios activos de la aplicación.
     *
     * @return
     * @throws SQLException
     */
    public static ArrayList getUsuariosActivosSQL() throws SQLException {
        System.out.println("Llamada a BBDD: getUsuariosSQL()");

        ArrayList resultado = new ArrayList();
        Connection conexion = BaseDeDatos.establecerConexion();
        String query="SELECT * FROM usuarios WHERE activado='S' ORDER BY nombre_usuario ASC";

        Statement s = conexion.createStatement();
        ResultSet rs = s.executeQuery (query);

        if(rs!=null){
            while(rs.next()){
                String nombre_usuario=(String)rs.getString("nombre_usuario");
                String ident_usuario=(String)rs.getString("ident_usuario");
                String correo_usuario=(String)rs.getString("correo_usuario");
                String perfil=(String)rs.getString("perfil");
                String activado=(String)rs.getString("activado");

                HashMap usuario = new HashMap();
                usuario.put("nombre_usuario", nombre_usuario);
                usuario.put("ident_usuario", ident_usuario);
                usuario.put("correo_usuario", correo_usuario);
                usuario.put("perfil", perfil);
                usuario.put("activado", activado);

                resultado.add(usuario);
            }
        }

        BaseDeDatos.cerrarConexion(conexion);

        return resultado;
    }

    /**
     * Devuelve todas las apuestas anteriores de todos los usuarios.
     *
     * @return
     * @throws SQLException
     */
    public static HashMap getApuestasAnterioresSQL() throws SQLException {
        System.out.println("Llamada a BBDD: getApuestasAnterioresSQL()");

        Connection conexion = BaseDeDatos.establecerConexion();
        String query="SELECT b.ident_usuario, b.nombre_usuario, a.ident_carrera "
                + "FROM apuestas a right join usuarios b "
                + "ON a.ident_usuario=b.ident_usuario "
                + "WHERE b.activado='S'"
                + "ORDER BY b.nombre_usuario, a.ident_carrera";

        Statement s = conexion.createStatement();
        ResultSet rs = s.executeQuery (query);

        HashMap porUsuarios = new HashMap();
        HashMap unUsuario = new HashMap();
        ArrayList listaUsuarios = new ArrayList();

        if(rs!=null){
            String usuarioAnterior="";
            while(rs.next()){
                String usuarioActual=rs.getString("ident_usuario");
                String nombre=rs.getString("nombre_usuario");
                String carrera=rs.getString("ident_carrera");

                if(!usuarioAnterior.equals(usuarioActual)){
                    unUsuario = new HashMap();
                    unUsuario.put("nombre_usuario", nombre);
                    unUsuario.put(carrera,"S");
                    porUsuarios.put(usuarioActual, unUsuario);
                    listaUsuarios.add(usuarioActual);
                }else{
                    unUsuario.put(carrera,"S");
                    porUsuarios.put(usuarioActual, unUsuario);
                }
                usuarioAnterior=usuarioActual;
            }
        }
        
        BaseDeDatos.cerrarConexion(conexion);

        porUsuarios.put("listaUsuarios", listaUsuarios);

        return porUsuarios;
    }

    /**
     * Devuelve los datos de todas las carreras dadas de alta.
     * 
     * @return
     * @throws SQLException
     */
    public static ArrayList getDatosCarrerasSQL() throws SQLException {
        System.out.println("Llamada a BBDD: getDatosCarrerasSQL()");

        Connection conexion = BaseDeDatos.establecerConexion();
        String query="SELECT * FROM carreras ORDER BY fecha_carrera ASC";

        Statement s = conexion.createStatement();
        ResultSet rs = s.executeQuery (query);

        ArrayList datosRecuperados = new ArrayList();

        if(rs!=null){
            while(rs.next()){
                HashMap carrera=new HashMap();
                carrera.put("ident_carrera", rs.getString("ident_carrera"));
                carrera.put("nombre_carrera", rs.getString("nombre_carrera"));
                carrera.put("fecha_carrera", rs.getString("fecha_carrera"));
                carrera.put("fecha_cierre", rs.getString("fecha_cierre"));
                carrera.put("ind_bandera", rs.getString("ind_bandera"));
                datosRecuperados.add(carrera);
            }
        }

        BaseDeDatos.cerrarConexion(conexion);
        
        return datosRecuperados;
    }

    /**
     * Devuelve los datos de la última carrera disputada.
     * 
     * @return
     * @throws SQLException
     */
    public static String getCarreraAnteriorSQL() throws SQLException {
        System.out.println("Llamada a BBDD: getCarreraAnteriorSQL()");

        Connection conexion = BaseDeDatos.establecerConexion();

        Calendar c = Calendar.getInstance();
        String dia = Integer.toString(c.get(Calendar.DATE));
        String mes = Integer.toString(c.get(Calendar.MONTH)+1);
        String annio = Integer.toString(c.get(Calendar.YEAR));

        String fecha_hoy=annio+"-"+mes+"-"+dia;
        String query="SELECT * "
                + "FROM carreras "
                + "WHERE fecha_carrera = ("
                + "SELECT max(fecha_carrera)"
                + "FROM carreras "
                + "WHERE fecha_carrera <= ?)";

        PreparedStatement selectStatement = conexion.prepareStatement(query);

        selectStatement.setString(1, fecha_hoy);

        ResultSet rs = selectStatement.executeQuery ();
        
        String ultimaCarreraDisputada="";

        if(rs!=null){
                while(rs.next()){
                    ultimaCarreraDisputada=rs.getString("ident_carrera");
                    if(ultimaCarreraDisputada==null)ultimaCarreraDisputada="";
                }
        }

        BaseDeDatos.cerrarConexion(conexion);
        
        return ultimaCarreraDisputada;
    }

     public static int guardarResultadoCarreraSQL(String carrera, String pole, String primero, String segundo, String tercero, String cuarto, String quinto, String sexto, String septimo, String octavo, String noveno, String decimo) throws SQLException {
        System.out.println("Llamada a BBDD: guardarResultadoCarreraSQL()");

        Connection conexion = BaseDeDatos.establecerConexion();

        String query="INSERT INTO resultados_carreras (ident_carrera,pole,primero,segundo,tercero,cuarto,quinto,sexto,septimo,octavo,noveno,decimo) "
                + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?) "
                + "ON DUPLICATE KEY "
                + "UPDATE ident_carrera=?, pole=?, primero=?, segundo=?, tercero=?, cuarto=?, quinto=?, sexto=?, septimo=?, octavo=?, noveno=?, decimo=?";

        PreparedStatement selectStatement = conexion.prepareStatement(query);

        selectStatement.setString(1, carrera);
        selectStatement.setString(2, pole);
        selectStatement.setString(3, primero);
        selectStatement.setString(4, segundo);
        selectStatement.setString(5, tercero);
        selectStatement.setString(6, cuarto);
        selectStatement.setString(7, quinto);
        selectStatement.setString(8, sexto);
        selectStatement.setString(9, septimo);
        selectStatement.setString(10, octavo);
        selectStatement.setString(11, noveno);
        selectStatement.setString(12, decimo);
        selectStatement.setString(13, carrera);
        selectStatement.setString(14, pole);
        selectStatement.setString(15, primero);
        selectStatement.setString(16, segundo);
        selectStatement.setString(17, tercero);
        selectStatement.setString(18, cuarto);
        selectStatement.setString(19, quinto);
        selectStatement.setString(20, sexto);
        selectStatement.setString(21, septimo);
        selectStatement.setString(22, octavo);
        selectStatement.setString(23, noveno);
        selectStatement.setString(24, decimo);

        int i = selectStatement.executeUpdate();

        return i;
    }

    public static ArrayList getUsuariosOrdenadosPorCarreraSQL(String carrera) throws SQLException {
        System.out.println("Llamada a BBDD: getOrdenadosPorCarreraSQL()");

        ArrayList usuariosOrdenados=new ArrayList();
        Connection conexion = BaseDeDatos.establecerConexion();
        String query="SELECT ident_usuario "
                + "FROM resultados_apuestas "
                + "WHERE ident_carrera=? "
                + "ORDER BY puntos DESC";

        PreparedStatement selectStatement = conexion.prepareStatement(query);

        selectStatement.setString(1, carrera);

        ResultSet rs = selectStatement.executeQuery ();

        if(rs!=null){
            while(rs.next()){
                usuariosOrdenados.add(rs.getString("ident_usuario"));
            }
        }

        BaseDeDatos.cerrarConexion(conexion);

        return usuariosOrdenados;
    }
    
    public static HashMap getClasificacionGeneralSQL() throws SQLException {
        System.out.println("Llamada a BBDD: getClasificacionGeneralSQL()");

        Connection conexion = BaseDeDatos.establecerConexion();
        String query="SELECT usuarios.ident_usuario as iu, usuarios.nombre_usuario as nu, sum(resultados_apuestas.puntos) as pt "
                + "FROM usuarios LEFT JOIN resultados_apuestas "
                + "ON usuarios.ident_usuario=resultados_apuestas.ident_usuario "
                + "WHERE usuarios.activado='S'"
                + "GROUP by iu "
                + "ORDER by pt DESC, nu ASC";

        Statement s = conexion.createStatement();
        ResultSet rs = s.executeQuery (query);

        HashMap datosClasificacion = new HashMap();
        HashMap puntosUsuarios = getPuntosCarrerasPorUsuariosSQL();
        ArrayList listaUsuarios = new ArrayList();

        if(rs!=null){
            while(rs.next()){
                String ident_usuario=rs.getString("iu");
                String nombre_usuario=rs.getString("nu");
                String puntos_totales=rs.getString("pt");
                listaUsuarios.add(ident_usuario);

                HashMap usuario = new HashMap();
                usuario.put("ident_usuario", ident_usuario);
                usuario.put("nombre_usuario", nombre_usuario);
                usuario.put("puntos_totales", puntos_totales);
                HashMap puntosCarreras=(HashMap)puntosUsuarios.get(ident_usuario);
                if(puntosCarreras==null)puntosCarreras=new HashMap();
                usuario.put("carreras", puntosCarreras);

                datosClasificacion.put(ident_usuario, usuario);
            }
        }

        BaseDeDatos.cerrarConexion(conexion);

        datosClasificacion.put("listaUsuarios", listaUsuarios);

        return datosClasificacion;
    }

    public static HashMap getPuntosCarrerasPorUsuariosSQL() throws SQLException {
        System.out.println("Llamada a BBDD: getPuntosCarrerasPorUsuariosSQL()");

        Connection conexion = BaseDeDatos.establecerConexion();
        String query="SELECT ident_usuario, ident_carrera, puntos "
                + "FROM resultados_apuestas "
                + "ORDER BY ident_usuario, ident_carrera";

        Statement s = conexion.createStatement();
        ResultSet rs = s.executeQuery (query);

        HashMap resultadosTodosUsuarios = new HashMap();
        HashMap resultadosUnUsuario = new HashMap();
        String usuarioAnt="";
        if(rs!=null){
            while(rs.next()){
                String usuarioActual=rs.getString("ident_usuario");
                String puntos=rs.getString("puntos");
                String carrera=rs.getString("ident_carrera");

                if(!usuarioActual.equals(usuarioAnt)){
                    resultadosUnUsuario = new HashMap();
                    resultadosUnUsuario.put(carrera, puntos);
                    resultadosTodosUsuarios.put(usuarioActual, resultadosUnUsuario);
                }
                else{
                    resultadosUnUsuario.put(carrera, puntos);
                    resultadosTodosUsuarios.put(usuarioActual, resultadosUnUsuario);
                }
                usuarioAnt=usuarioActual;
            }
        }
        BaseDeDatos.cerrarConexion(conexion);

        return resultadosTodosUsuarios;
    }

    public static int cambiarPerfilUsuarioSQL(String usuario, String perfil) throws SQLException {
        System.out.println("Llamada a BBDD: cambiarPerfilUsuarioSQL()");

        Connection conexion = BaseDeDatos.establecerConexion();
        String query="UPDATE usuarios "
                + "SET perfil=? "
                + "WHERE ident_usuario=?";

        PreparedStatement selectStatement = conexion.prepareStatement(query);

        selectStatement.setString(1, perfil);
        selectStatement.setString(2, usuario);

        int i = selectStatement.executeUpdate();

        BaseDeDatos.cerrarConexion(conexion);

        return i;
    }

    public static int cambiarEstadoUsuarioSQL(String usuario, String estado) throws SQLException {
        System.out.println("Llamada a BBDD: cambiarEstadoUsuarioSQL()");

        Connection conexion = BaseDeDatos.establecerConexion();
        String query="UPDATE usuarios "
                + "SET activado=? "
                + "WHERE ident_usuario=?";

        PreparedStatement selectStatement = conexion.prepareStatement(query);

        selectStatement.setString(1, estado);
        selectStatement.setString(2, usuario);

        int i = selectStatement.executeUpdate();

        BaseDeDatos.cerrarConexion(conexion);

        return i;
    }

    public static int borrarUsuarioSQL(String usuario) throws SQLException {
        System.out.println("Llamada a BBDD: borrarUsuarioSQL()");

        Connection conexion = BaseDeDatos.establecerConexion();
        String query="DELETE FROM usuarios "
                + "WHERE ident_usuario=?";

        PreparedStatement selectStatement = conexion.prepareStatement(query);

        selectStatement.setString(1, usuario);

        int i = selectStatement.executeUpdate();

        BaseDeDatos.cerrarConexion(conexion);

        return i;
    }

}

