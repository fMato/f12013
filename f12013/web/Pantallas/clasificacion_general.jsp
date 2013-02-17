<%@page import="java.util.HashMap"%>
<%@page import="java.util.ArrayList"%>
<h3>Clasificación general.</h3>

<table class="tablaClasif" width="99%" cellspacing="0">
    <tr>
        <th>
            &#160;
        </th>
        <th>
            Nombre
        </th>
        <%
        ArrayList listaCarreras = (ArrayList)request.getAttribute("listaCarreras");
        if(listaCarreras==null)listaCarreras=new ArrayList();

        HashMap puntosMaximosDeCarreras=(HashMap)request.getAttribute("puntosMaximosDeCarreras");
        if(puntosMaximosDeCarreras==null)puntosMaximosDeCarreras=new HashMap();

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
                <A href="./clasificacion_general.html?ordenar=<%=ident_carrera%>" class="linkStandar" title="Ordenar por <%=nombre_carrera%>.">
                    <img border="0" src="./Imagenes/banderas/<%=ind_bandera%>.png" alt="Ordenar por <%=nombre_carrera%>." title="Ordenar por <%=nombre_carrera%>."/>
                </A>
            </th>
        <%}%>
        <th>
            <A href="./clasificacion_general.html" class="linkStandar" title="Ordenar por puntos totales.">Total</A>
        </th>
    </tr>
    <%
    String usuario_conexion=(String)session.getAttribute("usuario");
    if(usuario_conexion==null)usuario_conexion="";
    HashMap datosClasificacion=(HashMap)request.getAttribute("datosClasificacion");
    if(datosClasificacion==null)datosClasificacion=new HashMap();

    ArrayList listaUsuarios=(ArrayList)datosClasificacion.get("listaUsuarios");
    if(listaUsuarios==null)listaUsuarios=new ArrayList();

    if(listaUsuarios.size()==0){%>
        <tr>
            <td colspan="<%=listaCarreras.size()+3%>">
                No se ha disputado ninguna carrera.
            </td>
        </tr>
        <%}
    int posicion=1;
    String puntos_totales_pos_anterior="";
    for(int i=0; i<listaUsuarios.size(); i++){
        String usuario=(String)listaUsuarios.get(i);
        if(usuario==null)usuario="";
        
        HashMap datosUsuario = new HashMap();
        datosUsuario=(HashMap)datosClasificacion.get(usuario);

        String nombre_usuario=(String)datosUsuario.get("nombre_usuario");
        if(nombre_usuario==null)nombre_usuario="";
        String puntos_totales=(String)datosUsuario.get("puntos_totales");
        if(puntos_totales==null)puntos_totales="";

        if(!puntos_totales.equals(puntos_totales_pos_anterior)){
            posicion=i+1;
            }
        puntos_totales_pos_anterior=puntos_totales;

        boolean marcarFila=false;
        if(usuario.equals(usuario_conexion))
            marcarFila=true;
    %>
        <tr <%if(marcarFila){%>class="filaUsuario"<%}%>>
            <td><%=posicion%>º</td>
            <td style="text-align:left;">
                <%=nombre_usuario%>
            </td>
            <%
            for(int j=0; j<listaCarreras.size(); j++){
                HashMap carrera=(HashMap)listaCarreras.get(j);
                String ident_carrera=(String)carrera.get("ident_carrera");
                if(ident_carrera==null)ident_carrera="";

                String puntos_maximos_esta_carrera=(String)puntosMaximosDeCarreras.get(ident_carrera);
                if(puntos_maximos_esta_carrera==null)puntos_maximos_esta_carrera="";

                HashMap puntosCarreras=(HashMap)datosUsuario.get("carreras");
                String puntos_carrera=(String)puntosCarreras.get(ident_carrera);
                if(puntos_carrera==null || "".equals(puntos_carrera))puntos_carrera="-";

                boolean esMaximo=false;
                if(puntos_maximos_esta_carrera.equals(puntos_carrera))
                    esMaximo=true;
            %>
                <td <%if(esMaximo){%>class="celdaMaximo"<%}%>>
                    <%=puntos_carrera%>
                </td>
            <%}%>
            <td>
                <%=puntos_totales%>
            </td>
        </tr>
    <%}%>
</table>