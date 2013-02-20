/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.f1.contabilizar;

import com.f1.bbdd.BaseDeDatos;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author victor
 */
public class DB {

    public static ArrayList getUsuarios() {
        ArrayList lista = null;
        Connection conexion = null;
        ResultSet rs = null;
        try {
            conexion = BaseDeDatos.establecerConexion();
            String selectCustomerStr;
            selectCustomerStr = "SELECT ident_usuario FROM usuarios";
            PreparedStatement selectStatement = conexion.prepareStatement(selectCustomerStr);
            rs = selectStatement.executeQuery();
            lista = new ArrayList();
            while (rs.next()) {
                lista.add(rs.getString(1));
            }
            rs.close();
            conexion.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return lista;

    }

    public static HashMap getClasificacionCarrera(int idCarrera) {
        HashMap lista = null;
        Connection conexion = null;
        ResultSet rs = null;
        try {
            conexion = BaseDeDatos.establecerConexion();
            String selectCustomerStr;
            selectCustomerStr = "SELECT * FROM resultados_carreras WHERE ident_carrera=" + idCarrera;
            PreparedStatement selectStatement = conexion.prepareStatement(selectCustomerStr);
            rs = selectStatement.executeQuery();
            if (rs.next()) {
                lista = new HashMap();
                lista.put(Puestos.POLE, (String) rs.getString("pole"));
                lista.put(Puestos.PRIMERO, (String) rs.getString("primero"));
                lista.put(Puestos.SEGUNDO, (String) rs.getString("segundo"));
                lista.put(Puestos.TERCERO, (String) rs.getString("tercero"));
                lista.put(Puestos.CUARTO, (String) rs.getString("cuarto"));
                lista.put(Puestos.QUINTO, (String) rs.getString("quinto"));
                lista.put(Puestos.SEXTO, (String) rs.getString("sexto"));
                lista.put(Puestos.SEPTIMO, (String) rs.getString("septimo"));
                lista.put(Puestos.OCTAVO, (String) rs.getString("octavo"));
                lista.put(Puestos.NOVENO, (String) rs.getString("noveno"));
                lista.put(Puestos.DECIMO, (String) rs.getString("decimo"));
            }
            rs.close();
            conexion.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return lista;
    }

    public static HashMap getClasificacionUsuario(int idCarrera, String usuario) {
        HashMap lista=null;
        Connection conexion = null;
        ResultSet rs = null;
        try {
            conexion = BaseDeDatos.establecerConexion();
            String selectCustomerStr;
            selectCustomerStr = "SELECT * FROM apuestas WHERE ident_usuario=? and ident_carrera=?";
            PreparedStatement selectStatement = conexion.prepareStatement(selectCustomerStr);
            selectStatement.setString(1, usuario);
            selectStatement.setString(2, "" + idCarrera);
            rs = selectStatement.executeQuery();
            if (rs.next()) {
                lista = new HashMap();
                lista.put(Puestos.POLE, (String) rs.getString("pole"));
                lista.put(Puestos.PRIMERO, (String) rs.getString("primero"));
                lista.put(Puestos.SEGUNDO, (String) rs.getString("segundo"));
                lista.put(Puestos.TERCERO, (String) rs.getString("tercero"));
                lista.put(Puestos.CUARTO, (String) rs.getString("cuarto"));
                lista.put(Puestos.QUINTO, (String) rs.getString("quinto"));
                lista.put(Puestos.SEXTO, (String) rs.getString("sexto"));
                lista.put(Puestos.SEPTIMO, (String) rs.getString("septimo"));
                lista.put(Puestos.OCTAVO, (String) rs.getString("octavo"));
                lista.put(Puestos.NOVENO, (String) rs.getString("noveno"));
                lista.put(Puestos.DECIMO, (String) rs.getString("decimo"));
            }
            rs.close();
            conexion.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return lista;
    }

    static void guardarPuntos(int puntos, int idCarrera, String usuario) {
        String SQL = "insert into resultados_apuestas(ident_usuario, ident_carrera, puntos)"
                + " values(?,?,?)";
        Connection con = null;
        PreparedStatement insert = null;
        try {
            con = BaseDeDatos.establecerConexion();
            insert = con.prepareStatement(SQL);
            insert.setString(1, usuario);
            insert.setString(2, "" + idCarrera);
            insert.setString(3, "" + puntos);
            insert.executeUpdate();
            insert.close();
            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}