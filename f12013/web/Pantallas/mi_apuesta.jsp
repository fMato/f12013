<%@page import="java.util.HashMap"%>

<script type="text/javascript">
    var mensaje="";
    function lanzarAltaApuesta(){
        if(validarApuesta())
            document.frmDatosProxApuesta.submit();
        else
            alert(mensaje);
    }
    function validarApuesta(){
        mensaje="";
        var validacionApuestas=new Array(10);
        validacionApuestas[0]=document.frmDatosProxApuesta.primero.options[document.frmDatosProxApuesta.primero.selectedIndex].value;
        validacionApuestas[1]=document.frmDatosProxApuesta.segundo.options[document.frmDatosProxApuesta.segundo.selectedIndex].value;
        validacionApuestas[2]=document.frmDatosProxApuesta.tercero.options[document.frmDatosProxApuesta.tercero.selectedIndex].value;
        validacionApuestas[3]=document.frmDatosProxApuesta.cuarto.options[document.frmDatosProxApuesta.cuarto.selectedIndex].value;
        validacionApuestas[4]=document.frmDatosProxApuesta.quinto.options[document.frmDatosProxApuesta.quinto.selectedIndex].value;
        validacionApuestas[5]=document.frmDatosProxApuesta.sexto.options[document.frmDatosProxApuesta.sexto.selectedIndex].value;
        validacionApuestas[6]=document.frmDatosProxApuesta.septimo.options[document.frmDatosProxApuesta.septimo.selectedIndex].value;
        validacionApuestas[7]=document.frmDatosProxApuesta.octavo.options[document.frmDatosProxApuesta.octavo.selectedIndex].value;
        validacionApuestas[8]=document.frmDatosProxApuesta.noveno.options[document.frmDatosProxApuesta.noveno.selectedIndex].value;
        validacionApuestas[9]=document.frmDatosProxApuesta.decimo.options[document.frmDatosProxApuesta.decimo.selectedIndex].value;

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

        if(document.frmDatosProxApuesta.pole.options[document.frmDatosProxApuesta.pole.selectedIndex].value==""){
            mensaje="Te falta por elegir la pole.";
            return false;
        }

        return true;
    }
</script>

<h3>Mi apuesta.</h3>

<FORM name="frmDatosProxApuesta" method="POST" action="./mi_apuesta.html">
    <input type="hidden" name="accion" value="guardar"/>
    <div style="padding:5px;width:90%;">
        <span class="labelMiApuesta">Pole:</span>
        <select class="selectMiApuesta" name="pole">
            <jsp:include page="./comboPilotos.jsp?indSelect=pole" flush="false"/>
        </select>
    </div>

    <hr style="width:100%;border:solid 1px #ccc;"/>

    <div style="padding:5px;float:left;width:50%;">
        <span class="labelMiApuesta">Primer clasificado:</span>
        <select class="selectMiApuesta" name="primero">
            <jsp:include page="./comboPilotos.jsp?indSelect=primero" flush="false"/>
        </select><br /><br />
        <span class="labelMiApuesta">Segundo clasificado:</span>
        <select class="selectMiApuesta" name="segundo">
            <jsp:include page="./comboPilotos.jsp?indSelect=segundo" flush="false"/>
        </select><br /><br />
        <span class="labelMiApuesta">Tercer clasificado:</span>
        <select class="selectMiApuesta" name="tercero">
            <jsp:include page="./comboPilotos.jsp?indSelect=tercero" flush="false"/>
        </select><br /><br />
        <span class="labelMiApuesta">Cuarto clasificado:</span>
        <select class="selectMiApuesta" name="cuarto">
            <jsp:include page="./comboPilotos.jsp?indSelect=cuarto" flush="false"/>
        </select><br /><br />
        <span class="labelMiApuesta">Quinto clasificado:</span>
        <select class="selectMiApuesta" name="quinto">
            <jsp:include page="./comboPilotos.jsp?indSelect=quinto" flush="false"/>
        </select><br /><br />
        <span class="labelMiApuesta">Sexto clasificado:</span>
        <select class="selectMiApuesta" name="sexto">
            <jsp:include page="./comboPilotos.jsp?indSelect=sexto" flush="false"/>
        </select><br /><br />
        <span class="labelMiApuesta">Séptimo clasificado:</span>
        <select class="selectMiApuesta" name="septimo">
            <jsp:include page="./comboPilotos.jsp?indSelect=septimo" flush="false"/>
        </select><br /><br />
        <span class="labelMiApuesta">Octavo clasificado:</span>
        <select class="selectMiApuesta" name="octavo">
            <jsp:include page="./comboPilotos.jsp?indSelect=octavo" flush="false"/>
        </select><br /><br />
        <span class="labelMiApuesta">Noveno clasificado:</span>
        <select class="selectMiApuesta" name="noveno">
            <jsp:include page="./comboPilotos.jsp?indSelect=noveno" flush="false"/>
        </select><br /><br />
        <span class="labelMiApuesta">Décimo clasificado:</span>
        <select class="selectMiApuesta" name="decimo">
            <jsp:include page="./comboPilotos.jsp?indSelect=decimo" flush="false"/>
        </select>
    </div>
</FORM>
<div style="border:solid 2px #ccc;padding:5px;float:right;width:45%;">
    <%
    HashMap proximaApuesta=(HashMap)request.getAttribute("proximaApuesta");
    if(proximaApuesta==null)proximaApuesta=new HashMap();

    String fecha_modificacion=(String) proximaApuesta.get("fecha_modificacion_formateada");
    if(fecha_modificacion==null)fecha_modificacion="";

    HashMap proximaCarrera=(HashMap)request.getAttribute("proximaCarrera");
    if(proximaCarrera==null)proximaCarrera=new HashMap();

    String fecha_proxima_carrera=(String) proximaCarrera.get("fecha_carrera_formateada");
    if(fecha_proxima_carrera==null)fecha_proxima_carrera="";
    String nombre_carrera=(String) proximaCarrera.get("nombre_carrera");
    if(nombre_carrera==null)nombre_carrera="";
    String fecha_cierre=(String) proximaCarrera.get("fecha_cierre_apuestas_formateada");
    if(fecha_cierre==null)fecha_cierre="";
    %>
    <span class="labelMiApuestaDatos">Próxima carrera:</span> <%=nombre_carrera%><BR />
    <span class="labelMiApuestaDatos">&#160;</span> <%=fecha_proxima_carrera%><BR />
    <span class="labelMiApuestaDatos">Cierre de apuestas:</span> <%=fecha_cierre%><BR />
    <span class="labelMiApuestaDatos">Última fecha de guardado:</span> <%=fecha_modificacion%>
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

<div style="padding:5px;float:right;width:95%;text-align:right;">
    <%
    String permiteGuardar=(String)request.getAttribute("permiteGuardar");
    if(permiteGuardar==null)permiteGuardar="";

    if("S".equals(permiteGuardar)){
    %>
        <A href="#" class="linkStandar" onClick="lanzarAltaApuesta();">Actualizar apuesta</A>
    <%}else{%>
        <i>Se ha superado la hora de cierre.</i>
    <%}%>
</div>