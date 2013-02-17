<h3>Error.</h3>
<p>
    Se ha producido un error.
    <%
    String mensajeError = (String) request.getAttribute("ERROR");
    %>
    
</p>
<div class="bloqueError">
    <%=mensajeError%>
</div>
<p>
    Ponte en contacto con los administradores de la página.
</p>