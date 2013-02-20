<%@page import="java.util.HashMap"%>
<%@page import="java.util.ArrayList"%>
<h3>Adminsitración de usuarios.</h3>
<script type="text/javascript">
    function pulsaEnter(){

        }
    function administrar(accion,usuario,nombre){
        var mensaje="";

        if(accion=="activar"){
            mensaje="Vas a activar el usuario de "+nombre+".";
            }
        else if(accion=="borrar"){
            mensaje="Vas a borrar el usuario de "+nombre+". Esta acción no se puede deshacer.";
            }
        else if(accion=="adminNO"){
            mensaje=nombre+" dejará de ser administrador.";
            }
        else if(accion=="adminSI"){
            mensaje=nombre+" empezará a ser administrador.";
            }
        else if(accion=="desactivar"){
            mensaje="Vas a desactivar el usuario de "+nombre+".";
            }

        if(confirm(mensaje)){
            document.frmAdministrar.accion.value=accion;
            document.frmAdministrar.usuario.value=usuario;
            document.frmAdministrar.submit();
            }
        }
</script>

<FORM name="frmAdministrar" method="POST" action="./admin_usuarios.html" accept-charset="iso-8859-1">
    <input type="hidden" name="accion" value=""/>
    <input type="hidden" name="usuario" value=""/>
</FORM>

<%
ArrayList listaUsuariosDesactivados=(ArrayList)request.getAttribute("listaUsuariosDesactivados");
if(listaUsuariosDesactivados==null)listaUsuariosDesactivados=new ArrayList();
%>
<h4>Usuarios pendientes de activación (<%=listaUsuariosDesactivados.size()%>)</h4>

<%
for(int i=0; i<listaUsuariosDesactivados.size(); i++){
    HashMap usuario=(HashMap)listaUsuariosDesactivados.get(i);
    if(usuario==null)usuario=new HashMap();
    String nombre_usuario=(String)usuario.get("nombre_usuario");
        if(nombre_usuario==null)nombre_usuario="";
    String correo_usuario=(String)usuario.get("correo_usuario");
        if(correo_usuario==null)correo_usuario="";
    String ident_usuario=(String)usuario.get("ident_usuario");
        if(ident_usuario==null)ident_usuario="";
    %>
    <p>&#160;&#160;&#160;&#160;&#160;<%=nombre_usuario%> (<%=correo_usuario%>) <A href="#" class="linkStandar" onClick="administrar('activar','<%=ident_usuario%>','<%=nombre_usuario%>');">Activar</A> | <A href="#" class="linkStandar" onClick="administrar('borrar','<%=ident_usuario%>','<%=nombre_usuario%>');">Borrar</A></p>
    <%}
if(listaUsuariosDesactivados.size()==0){%>
    <p>&#160;&#160;&#160;&#160;&#160;No hay usuarios pendientes de activar.</p>
    <%}
    %>

<%
ArrayList listaUsuariosAdmin=(ArrayList)request.getAttribute("listaUsuariosAdmin");
if(listaUsuariosAdmin==null)listaUsuariosAdmin=new ArrayList();
%>
<h4>Administradores (<%=listaUsuariosAdmin.size()%>)</h4>

<%
for(int i=0; i<listaUsuariosAdmin.size(); i++){
    HashMap usuario=(HashMap)listaUsuariosAdmin.get(i);
    if(usuario==null)usuario=new HashMap();
    String nombre_usuario=(String)usuario.get("nombre_usuario");
        if(nombre_usuario==null)nombre_usuario="";
    String correo_usuario=(String)usuario.get("correo_usuario");
        if(correo_usuario==null)correo_usuario="";
    String ident_usuario=(String)usuario.get("ident_usuario");
        if(ident_usuario==null)ident_usuario="";
    %>
    <p>
        &#160;&#160;&#160;&#160;&#160;<%=nombre_usuario%> (<%=correo_usuario%>)
        <% if(listaUsuariosAdmin.size()>1){%>
            <A href="#" class="linkStandar" onClick="administrar('adminNO','<%=ident_usuario%>','<%=nombre_usuario%>');">Eliminar como admin</A>
        <%}%>
    </p>
    <%}
    if(listaUsuariosAdmin.size()==0){%>
        <p>&#160;&#160;&#160;&#160;&#160;No hay administradores.</p>
    <%}
%>

<%
ArrayList listaUsuariosActivos=(ArrayList)request.getAttribute("listaUsuariosActivos");
if(listaUsuariosActivos==null)listaUsuariosActivos=new ArrayList();
%>

<h4>Usuarios activos (<%=listaUsuariosActivos.size()%>)</h4>

<%
for(int i=0; i<listaUsuariosActivos.size(); i++){
    HashMap usuario=(HashMap)listaUsuariosActivos.get(i);
    if(usuario==null)usuario=new HashMap();
    String nombre_usuario=(String)usuario.get("nombre_usuario");
        if(nombre_usuario==null)nombre_usuario="";
    String correo_usuario=(String)usuario.get("correo_usuario");
        if(correo_usuario==null)correo_usuario="";
    String ident_usuario=(String)usuario.get("ident_usuario");
        if(ident_usuario==null)ident_usuario="";
    String perfil=(String)usuario.get("perfil");
        if(perfil==null)perfil="";
    %>
    <p>&#160;&#160;&#160;&#160;&#160;<%=nombre_usuario%> (<%=correo_usuario%>) 
        <% if(!"A".equals(perfil)){%><A href="#" class="linkStandar" onClick="administrar('desactivar','<%=ident_usuario%>','<%=nombre_usuario%>');">Desactivar</A> | <A href="#" class="linkStandar" onClick="administrar('adminSI','<%=ident_usuario%>','<%=nombre_usuario%>');">Hacer admin</A><%}%>
    </p>
    <%}
    if(listaUsuariosActivos.size()==0){%>
        <p>&#160;&#160;&#160;&#160;&#160;No hay usuarios activos.</p>
    <%}
%>
<BR />
