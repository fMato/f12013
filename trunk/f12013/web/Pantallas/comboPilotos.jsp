<%@page import="java.util.HashMap"%>
<%@page import="java.util.ArrayList"%>
<%
ArrayList listaPilotos=(ArrayList)request.getAttribute("listaPilotos");
%>
<option value="">--Selecciona un piloto--</option>
<%
for(int i=0; i<listaPilotos.size(); i++){
    HashMap piloto=(HashMap)listaPilotos.get(i);
    String ident_piloto=(String)piloto.get("ident_piloto");
    if(ident_piloto==null)ident_piloto="";
    String nombre_piloto=(String)piloto.get("nombre_piloto");
    if(nombre_piloto==null)nombre_piloto="";
    String equipo=(String)piloto.get("equipo");
    if(equipo==null)equipo="";

    HashMap proximaApuesta=(HashMap)request.getAttribute("proximaApuesta");
    if(proximaApuesta==null)proximaApuesta=new HashMap();

    String indicadorCombo = request.getParameter("indSelect");
    if(indicadorCombo==null)indicadorCombo="";
    String pilotoSeleccionado=(String) proximaApuesta.get(indicadorCombo);
    if(pilotoSeleccionado==null)pilotoSeleccionado="";
    %>
    <option value="<%=ident_piloto%>" <%if(ident_piloto.equals(pilotoSeleccionado)){ %>selected="true"<%}%>><%=nombre_piloto%> (<%=equipo%>)</option>
    <%
    }
%>