<%@page import="java.util.ArrayList"%>
<%@page import="java.util.HashMap"%>

<script type="text/javascript">
    var mensaje="";
    function cerrarApuestas(){
        document.frmDatosResultado.accion.value="cerrar";
        document.frmDatosResultado.submit();
        }
    function guardarResultado(){
        if(validarApuesta())
            document.frmDatosResultado.submit();
        else
            alert(mensaje);
    }
    function pulsaEnter(){
            guardarResultado();
        }
    function validarApuesta(){
        mensaje="";
        var validacionApuestas=new Array(10);
        validacionApuestas[0]=document.frmDatosResultado.primero.options[document.frmDatosResultado.primero.selectedIndex].value;
        validacionApuestas[1]=document.frmDatosResultado.segundo.options[document.frmDatosResultado.segundo.selectedIndex].value;
        validacionApuestas[2]=document.frmDatosResultado.tercero.options[document.frmDatosResultado.tercero.selectedIndex].value;
        validacionApuestas[3]=document.frmDatosResultado.cuarto.options[document.frmDatosResultado.cuarto.selectedIndex].value;
        validacionApuestas[4]=document.frmDatosResultado.quinto.options[document.frmDatosResultado.quinto.selectedIndex].value;
        validacionApuestas[5]=document.frmDatosResultado.sexto.options[document.frmDatosResultado.sexto.selectedIndex].value;
        validacionApuestas[6]=document.frmDatosResultado.septimo.options[document.frmDatosResultado.septimo.selectedIndex].value;
        validacionApuestas[7]=document.frmDatosResultado.octavo.options[document.frmDatosResultado.octavo.selectedIndex].value;
        validacionApuestas[8]=document.frmDatosResultado.noveno.options[document.frmDatosResultado.noveno.selectedIndex].value;
        validacionApuestas[9]=document.frmDatosResultado.decimo.options[document.frmDatosResultado.decimo.selectedIndex].value;

        for(var i=0; i<validacionApuestas.length;i++){
            if(validacionApuestas[i]==""){
                mensaje="Has dejado algún puesto en blanco.";
                return false;
            }

            for(var j=i+1;j<validacionApuestas.length;j++){
                if(validacionApuestas[i]==validacionApuestas[j]){
                    mensaje="Hay pilotos repetidos entre los 10 primeros.";
                    return false;
                }
            }
        }

        if(document.frmDatosResultado.pole.options[document.frmDatosResultado.pole.selectedIndex].value==""){
            mensaje="Te falta por elegir la pole.";
            return false;
        }

        return true;
    }
</script>

<h3>Informar resultado.</h3>

<FORM name="frmDatosResultado" method="POST" action="informar_resultado.html" accept-charset="iso-8859-1">
    <input type="hidden" name="accion" value="guardar"/>
    <div style="padding:5px;width:90%;">
        <span class="labelMiApuesta">Pole:</span>
        <select class="selectMiApuesta" name="pole">
            <jsp:include page="./comboPilotos.jsp" flush="false"/>
        </select>
    </div>

    <hr style="width:100%;border:solid 1px #ccc;"/>

    <div style="padding:5px;float:left;width:50%;">
        <span class="labelMiApuesta">Primer clasificado:</span>
        <select class="selectMiApuesta" name="primero">
            <jsp:include page="./comboPilotos.jsp" flush="false"/>
        </select><br /><br />
        <span class="labelMiApuesta">Segundo clasificado:</span>
        <select class="selectMiApuesta" name="segundo">
            <jsp:include page="./comboPilotos.jsp" flush="false"/>
        </select><br /><br />
        <span class="labelMiApuesta">Tercer clasificado:</span>
        <select class="selectMiApuesta" name="tercero">
            <jsp:include page="./comboPilotos.jsp" flush="false"/>
        </select><br /><br />
        <span class="labelMiApuesta">Cuarto clasificado:</span>
        <select class="selectMiApuesta" name="cuarto">
            <jsp:include page="./comboPilotos.jsp" flush="false"/>
        </select><br /><br />
        <span class="labelMiApuesta">Quinto clasificado:</span>
        <select class="selectMiApuesta" name="quinto">
            <jsp:include page="./comboPilotos.jsp" flush="false"/>
        </select><br /><br />
        <span class="labelMiApuesta">Sexto clasificado:</span>
        <select class="selectMiApuesta" name="sexto">
            <jsp:include page="./comboPilotos.jsp" flush="false"/>
        </select><br /><br />
        <span class="labelMiApuesta">Séptimo clasificado:</span>
        <select class="selectMiApuesta" name="septimo">
            <jsp:include page="./comboPilotos.jsp" flush="false"/>
        </select><br /><br />
        <span class="labelMiApuesta">Octavo clasificado:</span>
        <select class="selectMiApuesta" name="octavo">
            <jsp:include page="./comboPilotos.jsp" flush="false"/>
        </select><br /><br />
        <span class="labelMiApuesta">Noveno clasificado:</span>
        <select class="selectMiApuesta" name="noveno">
            <jsp:include page="./comboPilotos.jsp" flush="false"/>
        </select><br /><br />
        <span class="labelMiApuesta">Décimo clasificado:</span>
        <select class="selectMiApuesta" name="decimo">
            <jsp:include page="./comboPilotos.jsp" flush="false"/>
        </select>
    </div>
    <div style="border:solid 2px #ccc;padding:5px;float:right;width:45%;">
        <%
        ArrayList listaCarreras=(ArrayList)request.getAttribute("listaCarreras");
        if(listaCarreras==null)listaCarreras=new ArrayList();
        %>
        <b>Resultado del Gran Premio:</b>
        <select name="carrera">
            <%
            for(int i=0; i<listaCarreras.size(); i++){
                HashMap carrera=(HashMap)listaCarreras.get(i);
                if(carrera==null)carrera=new HashMap();
                String ident_carrera=(String)carrera.get("ident_carrera");
                if(ident_carrera==null)ident_carrera="";
                String nombre_carrera=(String)carrera.get("nombre_carrera");
                if(nombre_carrera==null)nombre_carrera="";

                String ultimaCarreraDisputada=(String)request.getAttribute("ultimaCarreraDisputada");
                if(ultimaCarreraDisputada==null)ultimaCarreraDisputada="";
                %>
                <option value="<%=ident_carrera%>" <%if(ultimaCarreraDisputada.equals(ident_carrera)){%>selected="true"<%}%>><%=nombre_carrera%></option>
                <%}
            %>
        </select>
    </div>
    <div style="padding:5px;float:right;width:45%;">
        &#160;
    </div>
    <div style="border:solid 2px #ccc;padding:5px;float:right;width:45%;">
        Se guarda el resultado de la carrera y se contabilizan los puntos.
    </div>
    <div style="padding:5px;float:right;width:45%;">
        &#160;
    </div>

    <!-- Inicio Confirmación de que la apuesta se ha guardado -->
    <%
    String ind_guardado=(String)request.getAttribute("guardado");
    if(ind_guardado==null)ind_guardado="";

    if("S".equals(ind_guardado)){
    %>
        <div id="divConfirmacion" style="border:solid 2px #f00;padding:5px;float:right;width:45%;font-weight:bold;color:#f00;text-align:center;">
            Apuesta guardada con éxito.
        </div>
        <script type="text/javascript">
            function ocultaDivConfirm(){
                document.getElementById("divConfirmacion").style.display="none";
            }
            setTimeout("ocultaDivConfirm()",6000);
        </script>
    <%
        }
    %>
    <!-- Fin Confirmación de que la apuesta se ha guardado -->
</FORM>
<div style="padding:5px;float:right;width:95%;text-align:right;">
        <!--A href="#" class="linkStandar" onClick="cerrarApuestas();">Cerrar apuestas</A><BR /-->
        <A href="#" class="linkStandar" onClick="guardarResultado();">Guardar resultado</A>
</div>