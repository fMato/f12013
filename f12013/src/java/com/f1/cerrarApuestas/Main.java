/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.f1.cerrarApuestas;

import com.f1.bbdd.BaseDeDatos;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author Mato
 */
public class Main {

    static int idCarrera;

    public static void main(String[] args) {
        idCarrera = Integer.parseInt(args[0]);

        Connection conexion = null;
        try{
            conexion = BaseDeDatos.establecerConexion();
            String query="INSERT INTO apuestas(ident_usuario, ident_carrera, pole, primero, segundo, tercero, cuarto, quinto, sexto, septimo, octavo, noveno, decimo) SELECT ident_usuario, "+idCarrera+", pole, primero, segundo, tercero, cuarto, quinto, sexto, septimo, octavo, noveno, decimo FROM ultimas_apuestas";
            System.out.println(query);
            PreparedStatement selectStatement = conexion.prepareStatement(query);
            selectStatement.executeUpdate();
            selectStatement.close();
            conexion.close();
        }catch (SQLException ex) {
            ex.printStackTrace();
            }
    }

}
