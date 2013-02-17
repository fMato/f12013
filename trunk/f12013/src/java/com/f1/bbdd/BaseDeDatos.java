package com.f1.bbdd;

import java.sql.*;

/**
 *
 * @author Mato
 */
public class BaseDeDatos{
        private static String url="";
        private static String usuario="";
        private static String password="";
        
        public BaseDeDatos(String urlIN,String usuarioIN,String passwordIN){
            url=urlIN;
            usuario=usuarioIN;
            password=passwordIN;
        }

        public static Connection establecerConexion() throws SQLException{
            System.out.println("Se establece conexión con la base de datos.");
         // Se registra el Driver de MySQL
            DriverManager.registerDriver(new org.gjt.mm.mysql.Driver());
        // Establecemos la conexión con la base de datos.
           Connection conexion = DriverManager.getConnection (url,usuario, password);

           return conexion;
        }

        public static void cerrarConexion(Connection conexion) throws SQLException{
            System.out.println("Se cierra la conexión con la base de datos.");

            // Cerramos la conexion a la base de datos.
            conexion.close();
        }

}
