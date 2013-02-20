/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.f1.contabilizar;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author victor
 */
public class Main {

    static HashMap clasifCarrera;
    static int idCarrera;

    public static void main(String[] args) {
        idCarrera = Integer.parseInt(args[0]);

        clasifCarrera = DB.getClasificacionCarrera(idCarrera);

        ArrayList listaUsuarios = DB.getUsuarios();
        for (int i = 0; i < listaUsuarios.size(); i++) {
            String usuario = (String) listaUsuarios.get(i);
            try{
                int puntos = calculaPuntosUsuario(usuario);
                DB.guardarPuntos(puntos, idCarrera, usuario);
            }catch(Exception e){
                System.out.println("ERROR. Mirar log errores, se continua con el resto de usuarios");
                System.err.println("Error al calcular los puntos del usuario "+usuario);
                System.err.println("################TRAZA DEL ERROR################");
                e.printStackTrace(System.err);
                System.err.println("######################FIN######################");
            }
        }
    }

    private static int calculaPuntosUsuario(String usuario) {
        System.out.println("Datos de " + usuario);
        HashMap clasifUsuario = DB.getClasificacionUsuario(idCarrera, usuario);
        if(clasifUsuario==null){
            System.out.println("El usuario no ha rellenado la clasificacion (solo debe pasar en la primera carrera y hasta que el usuario la rellene)");
            return 0;
        }
        System.out.println("Clasificacion Usuario: " + clasifUsuario.toString());
        System.out.println("Clasificacion Real:    " + clasifCarrera.toString());
        int puntos = 0;
        puntos += calcularPole(clasifUsuario);
        puntos += calcularTodos(clasifUsuario);
        puntos += calcularPodium(clasifUsuario);
        puntos += calcularAciertos(clasifUsuario); //tambien borra del hashmap los aciertos
        puntos += clacularResto(clasifUsuario);

        System.out.println("Total puntos: " + puntos);
        return puntos;
    }

    private static int calcularPole(HashMap clasifUsuario) {
        String a = (String) clasifUsuario.get(Puestos.POLE);
        String b = (String) clasifCarrera.get(Puestos.POLE);

        clasifUsuario.remove(Puestos.POLE);
        if (a.equalsIgnoreCase(b)) {
            System.out.println("Acierta pole");
            return Puntos.POLE;
        } else {
            System.out.println("Falla pole");
            return 0;
        }
    }

    private static int calcularAciertos(HashMap clasifUsuario) {
        int puntos = 0;

        //Comprobamos si coincide el primer puesto
        String pu = (String) clasifUsuario.get(Puestos.PRIMERO);
        if (pu == null) {
            pu = "";
        }
        String pr = (String) clasifCarrera.get(Puestos.PRIMERO);
        if (pr == null) {
            pr = "";
        }
        if (pu.equalsIgnoreCase(pr)) {
            clasifUsuario.remove(Puestos.PRIMERO);
            puntos += Puntos.PRIMERO;
            System.out.println("Acierta el 1º");
        }

        pu = (String) clasifUsuario.get(Puestos.SEGUNDO);
        if (pu == null) {
            pu = "";
        }
        pr = (String) clasifCarrera.get(Puestos.SEGUNDO);
        if (pr == null) {
            pr = "";
        }
        if (pu.equalsIgnoreCase(pr)) {
            clasifUsuario.remove(Puestos.SEGUNDO);
            puntos += Puntos.SEGUNDO;
            System.out.println("Acierta el 2º");
        }

        pu = (String) clasifUsuario.get(Puestos.TERCERO);
        if (pu == null) {
            pu = "";
        }
        pr = (String) clasifCarrera.get(Puestos.TERCERO);
        if (pr == null) {
            pr = "";
        }
        if (pu.equalsIgnoreCase(pr)) {
            clasifUsuario.remove(Puestos.TERCERO);
            puntos += Puntos.TERCERO;
            System.out.println("Acierta el 3º");
        }

        pu = (String) clasifUsuario.get(Puestos.CUARTO);
        if (pu == null) {
            pu = "";
        }
        pr = (String) clasifCarrera.get(Puestos.CUARTO);
        if (pr == null) {
            pr = "";
        }
        if (pu.equalsIgnoreCase(pr)) {
            clasifUsuario.remove(Puestos.CUARTO);
            puntos += Puntos.CUARTO;
            System.out.println("Acierta el 4º");
        }

        pu = (String) clasifUsuario.get(Puestos.QUINTO);
        if (pu == null) {
            pu = "";
        }
        pr = (String) clasifCarrera.get(Puestos.QUINTO);
        if (pr == null) {
            pr = "";
        }
        if (pu.equalsIgnoreCase(pr)) {
            clasifUsuario.remove(Puestos.QUINTO);
            puntos += Puntos.QUITO;
            System.out.println("Acierta el 5º");
        }

        pu = (String) clasifUsuario.get(Puestos.SEXTO);
        if (pu == null) {
            pu = "";
        }
        pr = (String) clasifCarrera.get(Puestos.SEXTO);
        if (pr == null) {
            pr = "";
        }
        if (pu.equalsIgnoreCase(pr)) {
            clasifUsuario.remove(Puestos.SEXTO);
            puntos += Puntos.SEXTO;
            System.out.println("Acierta el 6º");
        }

        pu = (String) clasifUsuario.get(Puestos.SEPTIMO);
        if (pu == null) {
            pu = "";
        }
        pr = (String) clasifCarrera.get(Puestos.SEPTIMO);
        if (pr == null) {
            pr = "";
        }
        if (pu.equalsIgnoreCase(pr)) {
            clasifUsuario.remove(Puestos.SEPTIMO);
            puntos += Puntos.SEPTIMO;
            System.out.println("Acierta el 7º");
        }

        pu = (String) clasifUsuario.get(Puestos.OCTAVO);
        if (pu == null) {
            pu = "";
        }
        pr = (String) clasifCarrera.get(Puestos.OCTAVO);
        if (pr == null) {
            pr = "";
        }
        if (pu.equalsIgnoreCase(pr)) {
            clasifUsuario.remove(Puestos.OCTAVO);
            puntos += Puntos.OCTAVO;
            System.out.println("Acierta el 8º");
        }

        pu = (String) clasifUsuario.get(Puestos.NOVENO);
        if (pu == null) {
            pu = "";
        }
        pr = (String) clasifCarrera.get(Puestos.NOVENO);
        if (pr == null) {
            pr = "";
        }
        if (pu.equalsIgnoreCase(pr)) {
            clasifUsuario.remove(Puestos.NOVENO);
            puntos += Puntos.NOVENO;
            System.out.println("Acierta el 9º");
        }

        pu = (String) clasifUsuario.get(Puestos.DECIMO);
        if (pu == null) {
            pu = "";
        }
        pr = (String) clasifCarrera.get(Puestos.DECIMO);
        if (pr == null) {
            pr = "";
        }
        if (pu.equalsIgnoreCase(pr)) {
            clasifUsuario.remove(Puestos.DECIMO);
            puntos += Puntos.DECIMO;
            System.out.println("Acierta el 10º");
        }

        return puntos;

    }

    private static int calcularPodium(HashMap clasifUsuario) {
        String p1u = (String) clasifUsuario.get(Puestos.PRIMERO);
        String p1r = (String) clasifCarrera.get(Puestos.PRIMERO);

        String p2u = (String) clasifUsuario.get(Puestos.SEGUNDO);
        String p2r = (String) clasifCarrera.get(Puestos.SEGUNDO);

        String p3u = (String) clasifUsuario.get(Puestos.TERCERO);
        String p3r = (String) clasifCarrera.get(Puestos.TERCERO);

        if (p1u.equalsIgnoreCase(p1r) && p2u.equalsIgnoreCase(p2r) && p3u.equalsIgnoreCase(p3r)) {
            //clasifUsuario.remove(Puestos.PRIMERO);
            //clasifUsuario.remove(Puestos.SEGUNDO);
            //clasifUsuario.remove(Puestos.TERCERO);
            System.out.println("Acierta podium");
            return Puntos.PODIUM;
        } else {
            System.out.println("No acierta podium");
            return 0;
        }
    }

    private static int calcularTodos(HashMap clasifUsuario) {
        String p1u = (String) clasifUsuario.get(Puestos.PRIMERO);
        String p1r = (String) clasifCarrera.get(Puestos.PRIMERO);

        String p2u = (String) clasifUsuario.get(Puestos.SEGUNDO);
        String p2r = (String) clasifCarrera.get(Puestos.SEGUNDO);

        String p3u = (String) clasifUsuario.get(Puestos.TERCERO);
        String p3r = (String) clasifCarrera.get(Puestos.TERCERO);

        String p4u = (String) clasifUsuario.get(Puestos.CUARTO);
        String p4r = (String) clasifCarrera.get(Puestos.CUARTO);

        String p5u = (String) clasifUsuario.get(Puestos.QUINTO);
        String p5r = (String) clasifCarrera.get(Puestos.QUINTO);

        String p6u = (String) clasifUsuario.get(Puestos.SEXTO);
        String p6r = (String) clasifCarrera.get(Puestos.SEXTO);

        String p7u = (String) clasifUsuario.get(Puestos.SEPTIMO);
        String p7r = (String) clasifCarrera.get(Puestos.SEPTIMO);

        String p8u = (String) clasifUsuario.get(Puestos.OCTAVO);
        String p8r = (String) clasifCarrera.get(Puestos.OCTAVO);

        String p9u = (String) clasifUsuario.get(Puestos.NOVENO);
        String p9r = (String) clasifCarrera.get(Puestos.NOVENO);

        String p10u = (String) clasifUsuario.get(Puestos.DECIMO);
        String p10r = (String) clasifCarrera.get(Puestos.DECIMO);
        if (p1u.equalsIgnoreCase(p1r)
                && p2u.equalsIgnoreCase(p2r)
                && p3u.equalsIgnoreCase(p3r)
                && p4u.equalsIgnoreCase(p4r)
                && p5u.equalsIgnoreCase(p5r)
                && p6u.equalsIgnoreCase(p6r)
                && p7u.equalsIgnoreCase(p7r)
                && p8u.equalsIgnoreCase(p8r)
                && p9u.equalsIgnoreCase(p9r)
                && p10u.equalsIgnoreCase(p10r)) {
            System.out.println("Acierta la clasificacion completa");
            return Puntos.TODOS;
        } else {
            System.out.println("No acierta la clasificacion completa");
            return 0;
        }
    }

    private static int clacularResto(HashMap clasifUsuario) {
        ArrayList listaClasifUsuario = new ArrayList(clasifUsuario.values());
        System.out.println("Resto de pilotos: " + listaClasifUsuario.toString());
        HashMap clasifCarreraSinPole = new HashMap(clasifCarrera);
        clasifCarreraSinPole.remove(Puestos.POLE);
        ArrayList listaClasifReal = new ArrayList(clasifCarreraSinPole.values());
        int puntos = 0;
        for (int i = 0; i < listaClasifUsuario.size(); i++) {
            String piloto = (String) listaClasifUsuario.get(i);
            if (tienePiloto(piloto, listaClasifReal)) {
                puntos += Puntos.DENTRO;
            }
        }
        return puntos;
    }

    private static boolean tienePiloto(String piloto, ArrayList listaClasifReal) {
        for (int i = 0; i < listaClasifReal.size(); i++) {
            if (((String) listaClasifReal.get(i)).equalsIgnoreCase(piloto)) {
                System.out.println("Tiene a: " + piloto);
                return true;
            }
        }
        return false;
    }
}