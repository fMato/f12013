<%@page import="java.util.HashMap"%>
<h3>Apuesta anterior.</h3>
<script type="text/javascript">
    function pulsaEnter(){

        }
</script>
<%
String nombre_usuario=(String)request.getAttribute("nombre_usuario");
if(nombre_usuario==null)nombre_usuario="";
String nombre_carrera=(String)request.getAttribute("nombre_carrera");
if(nombre_carrera==null)nombre_carrera="";
HashMap carreraAnterior=(HashMap)request.getAttribute("carreraAnterior");
if(carreraAnterior==null)carreraAnterior=new HashMap();
HashMap nombresPilotos=(HashMap)request.getAttribute("nombresPilotos");
if(nombresPilotos==null)nombresPilotos=new HashMap();
HashMap resultadoCarrera=(HashMap)request.getAttribute("resultadoCarrera");
if(resultadoCarrera==null)resultadoCarrera=new HashMap();
String puntos_carrera=(String)request.getAttribute("puntos_carrera");
if(puntos_carrera==null)puntos_carrera="";
String carrera_disputada=(String)request.getAttribute("carrera_disputada");
if(carrera_disputada==null)carrera_disputada="";
%>
<p>
    <b>Jugador:</b> <%=nombre_usuario%>
</p>
<p>
    <b>Carrera:</b> <%=nombre_carrera%>
</p>
<p>
    <b>Puntos:</b> <%=puntos_carrera%>
</p>
<table class="tablaApuestaAnterior" cellspacing="0">
    <tr>
        <th>
            &#160;
        </th>
        <th>
            Tu apuesta
        </th>
        <th>
            Resultado de la carrera
        </th>
        <th>
            &#160;
        </th>
    </tr>
    <tr>
        <td style="text-align:left;font-weight:bold;">
            Pole
        </td>
        <td>
            <%=nombresPilotos.get((String)carreraAnterior.get("pole"))%>
        </td>
        <td>
            <%
            String pole_carrera=(String)nombresPilotos.get((String)resultadoCarrera.get("pole"));
            if(pole_carrera==null)pole_carrera="<i>Carrera no disputada</i>";
            %>
            <%=pole_carrera%>
        </td>
        <td>
            <%
            if("N".equals(carrera_disputada)){%>
                &#160;
                <%}
            else if(pole_carrera.equals(nombresPilotos.get((String)carreraAnterior.get("pole")))){%>
                <img border="0" src="./Imagenes/otras/check.png" alt="Posición acertada." title="Posición acertada."/>
                <%}
            else{%>
                <img border="0" src="./Imagenes/otras/remove.png" alt="Posición fallada." title="Posición fallada."/>
                <%}
            %>
        </td>
    </tr>
    <tr>
        <td style="text-align:left;font-weight:bold;">
            Primer clasificado
        </td>
        <td>
            <%=nombresPilotos.get((String)carreraAnterior.get("primero"))%>
        </td>
        <td>
            <%
            String primero_carrera=(String)nombresPilotos.get((String)resultadoCarrera.get("primero"));
            if(primero_carrera==null)primero_carrera="<i>Carrera no disputada</i>";
            %>
            <%=primero_carrera%>
        </td>
        <td>
            <%
            if("N".equals(carrera_disputada)){%>
                &#160;
                <%}
            else if(primero_carrera.equals(nombresPilotos.get((String)carreraAnterior.get("primero")))){%>
                <img border="0" src="./Imagenes/otras/check.png" alt="Posición acertada." title="Posición acertada."/>
                <%}
            else{%>
                <img border="0" src="./Imagenes/otras/remove.png" alt="Posición fallada." title="Posición fallada."/>
                <%}
            %>
        </td>
    </tr>
    <tr>
        <td style="text-align:left;font-weight:bold;">
            Segundo clasificado
        </td>
        <td>
            <%=nombresPilotos.get((String)carreraAnterior.get("segundo"))%>
        </td>
        <td>
            <%
            String segundo_carrera=(String)nombresPilotos.get((String)resultadoCarrera.get("segundo"));
            if(segundo_carrera==null)segundo_carrera="<i>Carrera no disputada</i>";
            %>
            <%=segundo_carrera%>
        </td>
        <td>
            <%
            if("N".equals(carrera_disputada)){%>
                &#160;
                <%}
            else if(segundo_carrera.equals(nombresPilotos.get((String)carreraAnterior.get("segundo")))){%>
                <img border="0" src="./Imagenes/otras/check.png" alt="Posición acertada." title="Posición acertada."/>
                <%}
            else{%>
                <img border="0" src="./Imagenes/otras/remove.png" alt="Posición fallada." title="Posición fallada."/>
                <%}
            %>
        </td>
    </tr>
    <tr>
        <td style="text-align:left;font-weight:bold;">
            Tercer clasificado
        </td>
        <td>
            <%=nombresPilotos.get((String)carreraAnterior.get("tercero"))%>
        </td>
        <td>
            <%
            String tercero_carrera=(String)nombresPilotos.get((String)resultadoCarrera.get("tercero"));
            if(tercero_carrera==null)tercero_carrera="<i>Carrera no disputada</i>";
            %>
            <%=tercero_carrera%>
        </td>
        <td>
            <%
            if("N".equals(carrera_disputada)){%>
                &#160;
                <%}
            else if(tercero_carrera.equals(nombresPilotos.get((String)carreraAnterior.get("tercero")))){%>
                <img border="0" src="./Imagenes/otras/check.png" alt="Posición acertada." title="Posición acertada."/>
                <%}
            else{%>
                <img border="0" src="./Imagenes/otras/remove.png" alt="Posición fallada." title="Posición fallada."/>
                <%}
            %>
        </td>
    </tr>
    <tr>
        <td style="text-align:left;font-weight:bold;">
            Cuarto clasificado
        </td>
        <td>
            <%=nombresPilotos.get((String)carreraAnterior.get("cuarto"))%>
        </td>
        <td>
            <%
            String cuarto_carrera=(String)nombresPilotos.get((String)resultadoCarrera.get("cuarto"));
            if(cuarto_carrera==null)cuarto_carrera="<i>Carrera no disputada</i>";
            %>
            <%=cuarto_carrera%>
        </td>
        <td>
            <%
            if("N".equals(carrera_disputada)){%>
                &#160;
                <%}
            else if(cuarto_carrera.equals(nombresPilotos.get((String)carreraAnterior.get("cuarto")))){%>
                <img border="0" src="./Imagenes/otras/check.png" alt="Posición acertada." title="Posición acertada."/>
                <%}
            else{%>
                <img border="0" src="./Imagenes/otras/remove.png" alt="Posición fallada." title="Posición fallada."/>
                <%}
            %>
        </td>
    </tr>
    <tr>
        <td style="text-align:left;font-weight:bold;">
            Quinto clasificado
        </td>
        <td>
            <%=nombresPilotos.get((String)carreraAnterior.get("quinto"))%>
        </td>
        <td>
            <%
            String quinto_carrera=(String)nombresPilotos.get((String)resultadoCarrera.get("quinto"));
            if(quinto_carrera==null)quinto_carrera="<i>Carrera no disputada</i>";
            %>
            <%=quinto_carrera%>
        </td>
        <td>
            <%
            if("N".equals(carrera_disputada)){%>
                &#160;
                <%}
            else if(quinto_carrera.equals(nombresPilotos.get((String)carreraAnterior.get("quinto")))){%>
                <img border="0" src="./Imagenes/otras/check.png" alt="Posición acertada." title="Posición acertada."/>
                <%}
            else{%>
                <img border="0" src="./Imagenes/otras/remove.png" alt="Posición fallada." title="Posición fallada."/>
                <%}
            %>
        </td>
    </tr>
    <tr>
        <td style="text-align:left;font-weight:bold;">
            Sexto clasificado
        </td>
        <td>
            <%=nombresPilotos.get((String)carreraAnterior.get("sexto"))%>
        </td>
        <td>
            <%
            String sexto_carrera=(String)nombresPilotos.get((String)resultadoCarrera.get("sexto"));
            if(sexto_carrera==null)sexto_carrera="<i>Carrera no disputada</i>";
            %>
            <%=sexto_carrera%>
        </td>
        <td>
            <%
            if("N".equals(carrera_disputada)){%>
                &#160;
                <%}
            else if(sexto_carrera.equals(nombresPilotos.get((String)carreraAnterior.get("sexto")))){%>
                <img border="0" src="./Imagenes/otras/check.png" alt="Posición acertada." title="Posición acertada."/>
                <%}
            else{%>
                <img border="0" src="./Imagenes/otras/remove.png" alt="Posición fallada." title="Posición fallada."/>
                <%}
            %>
        </td>
    </tr>
    <tr>
        <td style="text-align:left;font-weight:bold;">
            Séptimo clasificado
        </td>
        <td>
            <%=nombresPilotos.get((String)carreraAnterior.get("septimo"))%>
        </td>
        <td>
            <%
            String septimo_carrera=(String)nombresPilotos.get((String)resultadoCarrera.get("septimo"));
            if(septimo_carrera==null)septimo_carrera="<i>Carrera no disputada</i>";
            %>
            <%=septimo_carrera%>
        </td>
        <td>
            <%
            if("N".equals(carrera_disputada)){%>
                &#160;
                <%}
            else if(septimo_carrera.equals(nombresPilotos.get((String)carreraAnterior.get("septimo")))){%>
                <img border="0" src="./Imagenes/otras/check.png" alt="Posición acertada." title="Posición acertada."/>
                <%}
            else{%>
                <img border="0" src="./Imagenes/otras/remove.png" alt="Posición fallada." title="Posición fallada."/>
                <%}
            %>
        </td>
    </tr>
    <tr>
        <td style="text-align:left;font-weight:bold;">
            Octavo clasificado
        </td>
        <td>
            <%=nombresPilotos.get((String)carreraAnterior.get("octavo"))%>
        </td>
        <td>
            <%
            String octavo_carrera=(String)nombresPilotos.get((String)resultadoCarrera.get("octavo"));
            if(octavo_carrera==null)octavo_carrera="<i>Carrera no disputada</i>";
            %>
            <%=octavo_carrera%>
        </td>
        <td>
            <%
            if("N".equals(carrera_disputada)){%>
                &#160;
                <%}
            else if(octavo_carrera.equals(nombresPilotos.get((String)carreraAnterior.get("octavo")))){%>
                <img border="0" src="./Imagenes/otras/check.png" alt="Posición acertada." title="Posición acertada."/>
                <%}
            else{%>
                <img border="0" src="./Imagenes/otras/remove.png" alt="Posición fallada." title="Posición fallada."/>
                <%}
            %>
        </td>
    </tr>
    <tr>
        <td style="text-align:left;font-weight:bold;">
            Noveno clasificado
        </td>
        <td>
            <%=nombresPilotos.get((String)carreraAnterior.get("noveno"))%>
        </td>
        <td>
            <%
            String noveno_carrera=(String)nombresPilotos.get((String)resultadoCarrera.get("noveno"));
            if(noveno_carrera==null)noveno_carrera="<i>Carrera no disputada</i>";
            %>
            <%=noveno_carrera%>
        </td>
        <td>
            <%
            if("N".equals(carrera_disputada)){%>
                &#160;
                <%}
            else if(noveno_carrera.equals(nombresPilotos.get((String)carreraAnterior.get("noveno")))){%>
                <img border="0" src="./Imagenes/otras/check.png" alt="Posición acertada." title="Posición acertada."/>
                <%}
            else{%>
                <img border="0" src="./Imagenes/otras/remove.png" alt="Posición fallada." title="Posición fallada."/>
                <%}
            %>
        </td>
    </tr>
    <tr>
        <td style="text-align:left;font-weight:bold;">
            Décimo clasificado
        </td>
        <td>
            <%=nombresPilotos.get((String)carreraAnterior.get("decimo"))%>
        </td>
        <td>
            <%
            String decimo_carrera=(String)nombresPilotos.get((String)resultadoCarrera.get("decimo"));
            if(decimo_carrera==null)decimo_carrera="<i>Carrera no disputada</i>";
            %>
            <%=decimo_carrera%>
        </td>
        <td>
            <%
            if("N".equals(carrera_disputada)){%>
                &#160;
                <%}
            else if(decimo_carrera.equals(nombresPilotos.get((String)carreraAnterior.get("decimo")))){%>
                <img border="0" src="./Imagenes/otras/check.png" alt="Posición acertada." title="Posición acertada."/>
                <%}
            else{%>
                <img border="0" src="./Imagenes/otras/remove.png" alt="Posición fallada." title="Posición fallada."/>
                <%}
            %>
        </td>
    </tr>
</table><BR />