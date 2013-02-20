<%-- 
    Document   : template_inicio2
    Created on : 19-feb-2013, 20:09:03
    Author     : Mato
--%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
response.setDateHeader("Expires",-1);
response.setHeader("Pragma","no-cache");
if(request.getProtocol().equals("HTTP/1.1"))
response.setHeader("Cache-Control","no-cache");

String titulo = (String) request.getAttribute("tituloPantalla");
String pantallaContenido = (String) request.getAttribute("jspPresentacion");

%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><%=titulo%></title>
        <link rel="stylesheet" type="text/css" href="./css/general.css" />
        <script type="text/javascript" src="./javascript/comues.js"></script>
    </head>
    <body>
        <table border="0" align="center" width="901px;">
            <tr>
                <td id="td_cabecera" colspan="2">
                    <img src="./Imagenes/cabeceraf1.jpg" alt="Formula Uno Sopra 2013"/>
                </td>
            </tr>
            <tr>
                <td class="td_menu" valign="top" width="150px">
                    <jsp:include page='menu.jsp' flush="false"/>
                </td>
                <td class="td_contenido" valign="top" width="750px" height="350px;">
                    <!-- Include del jsp con el contenido -->
                    <%try{%>
                    <jsp:include page='<%=pantallaContenido%>' flush="false"/>
                    <%}catch (Exception spe){
                    System.out.println("Error al pintar el jsp");
                    String error_msj=spe.getMessage();
                    System.out.println(error_msj);
                    %>
                    <jsp:include page='./error_jsp.jsp' flush="false"/>
                   <% }%>
                </td>
            </tr>
            <tr>
                <td class="td_pie" colspan="2">
                    Para cualquier problema ponte en contacto con los <A href="mailto:f1Sopra@gmail.com">administradores</A> de la página.
                </td>
            </tr>
        </table>
    </body>
</html>
