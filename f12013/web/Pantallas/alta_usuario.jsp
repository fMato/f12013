<h3>Confirmaci�n de alta de usuario.</h3>
<p>
    <%
    String nombre_usuario=(String)request.getAttribute("nombre_usuario");
    if(nombre_usuario==null)nombre_usuario="";
    String email=(String)request.getAttribute("email");
    if(email==null)email="";
    %>
    Hola <b><%=nombre_usuario%></b>.<BR />
    Tu usuario se ha dado de alta correctamente.<BR />
    Cuando los administradores lo revisen y lo activen te enviar�n un correo de confirmaci�n a la direcci�n <b><%=email%></b>.
</p>