<%@page import="java.util.HashMap"%>
<%@page import="java.util.ArrayList"%>
<div class="nav">
    <%
    Boolean hay_conexion_obj=(Boolean)session.getAttribute("haySession"); // Recupera de sesión el objeto que identifica si hay conectado un usuario.
    if(hay_conexion_obj==null)hay_conexion_obj=Boolean.valueOf(false);
    boolean hay_conexion=hay_conexion_obj.booleanValue(); // Indica si hay un usuario conectado.
    String perfil_usuario=(String)session.getAttribute("perfil");
    if(perfil_usuario==null)perfil_usuario="";
    ArrayList opcionesMenu=(ArrayList)request.getAttribute("opcionesMenu");
    if(opcionesMenu==null)opcionesMenu=new ArrayList();

    for(int i=0; i<opcionesMenu.size(); i++){
        HashMap menuItem=(HashMap)opcionesMenu.get(i);
        if(menuItem==null)menuItem=new HashMap();

        long antiCache=System.currentTimeMillis();

        String requiere_conexion=(String)menuItem.get("requiere_conexion");
        if(requiere_conexion==null)requiere_conexion="";
        String texto=(String)menuItem.get("texto");
        if(texto==null)texto="";
        String url=(String)menuItem.get("url");
        if(url==null)url="";
        String perfiles=(String)menuItem.get("perfiles");
        if(perfiles==null)perfiles="";

        boolean cumplePerfil=false;
        if(perfiles.indexOf(perfil_usuario)!=-1)
            cumplePerfil=true;

        if("N".equals(requiere_conexion)){%>
            <div><A href="<%=url%>?<%=antiCache%>"><%=texto%></A></div>
            <%}
        else if(hay_conexion && cumplePerfil){%>
            <div><A href="<%=url%>?<%=antiCache%>"><%=texto%></A></div>
            <%}
        }

    if(opcionesMenu.size()==0){%>
        <div>No hay datos.</div>
    <%}
    %>
</div>



