<%@page import="java.util.HashMap"%>
<%@page import="java.util.ArrayList"%>
<h3>Todas las apuestas.</h3>

<table class="tablaClasif" width="99%" cellspacing="0">
    <tr>
        <th>
            Nombre
        </th>
        <%
        ArrayList listaCarreras = (ArrayList)request.getAttribute("listaCarreras");
        if(listaCarreras==null)listaCarreras=new ArrayList();

        for(int i=0; i<listaCarreras.size(); i++){
        %>
            <th>
                <%
                HashMap carrera=(HashMap)listaCarreras.get(i);
                String ident_carrera=(String)carrera.get("ident_carrera");
                if(ident_carrera==null)ident_carrera="";
                String nombre_carrera=(String)carrera.get("nombre_carrera");
                if(nombre_carrera==null)nombre_carrera="";
                String ind_bandera=(String)carrera.get("ind_bandera");
                if(ind_bandera==null)ind_bandera="";
                %>
                <img border="0" src="./Imagenes/banderas/<%=ind_bandera%>.png" alt="<%=nombre_carrera%>." title="<%=nombre_carrera%>."/>
            </th>
        <%}%>
    </tr>
    <%
    String usuario_conexion=(String)session.getAttribute("usuario");
    if(usuario_conexion==null)usuario_conexion="";
    HashMap apuestasAnteriores=(HashMap)request.getAttribute("apuestasAnteriores");
    if(apuestasAnteriores==null)apuestasAnteriores=new HashMap();

    ArrayList listaUsuarios=(ArrayList)apuestasAnteriores.get("listaUsuarios");
    if(listaUsuarios==null)listaUsuarios=new ArrayList();
    
    if(listaUsuarios.size()==0){%>
        <tr>
            <td colspan="<%=listaCarreras.size()+1%>">
                No se ha disputado ninguna carrera.
            </td>
        </tr>
        <%}
    for(int i=0; i<listaUsuarios.size(); i++){
        String usuario=(String)listaUsuarios.get(i);
        if(usuario==null)usuario="";
        
        HashMap datosUsuario = new HashMap();
        datosUsuario=(HashMap)apuestasAnteriores.get(usuario);

        String nombre_usuario=(String)datosUsuario.get("nombre_usuario");
        if(nombre_usuario==null)nombre_usuario="";

        boolean marcarFila=false;
        if(usuario.equals(usuario_conexion))
            marcarFila=true;
    %>
        <tr <%if(marcarFila){%>class="filaUsuario"<%}%>>
            <td style="text-align:left;">
                <%=nombre_usuario%>
            </td>
            <%
            for(int j=0; j<listaCarreras.size(); j++){
                HashMap carrera=(HashMap)listaCarreras.get(j);
                String ident_carrera=(String)carrera.get("ident_carrera");
                if(ident_carrera==null)ident_carrera="";
                String nombre_carrera=(String)carrera.get("nombre_carrera");
                if(nombre_carrera==null)nombre_carrera="";

                String hay_datos_carrera=(String)datosUsuario.get(ident_carrera);
                if(hay_datos_carrera==null || "".equals(hay_datos_carrera))hay_datos_carrera="N";
            %>
                <td>
                    <%if("S".equals(hay_datos_carrera)){%>
                        <A href="./apuesta_anterior.html?carrera=<%=ident_carrera%>&jugador=<%=usuario%>" class="linkStandar" title="Apuesta de <%=nombre_usuario%> para el <%=nombre_carrera%>.">
                            <img border="0" src="./Imagenes/otras/lupa2.png" alt="Apuesta de <%=nombre_usuario%> para el <%=nombre_carrera%>." title="Apuesta de <%=nombre_usuario%> para el <%=nombre_carrera%>."/>
                        </A>
                    <%}else{%>
                    -
                    <%}%>
                </td>
            <%}%>
        </tr>
    <%}%>
</table>