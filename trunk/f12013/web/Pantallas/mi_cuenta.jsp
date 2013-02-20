<%@page import="java.util.HashMap"%>
<h3>Mi cuenta</h3>
<%
HashMap datosUsuario=(HashMap)request.getAttribute("datosUsuario");
if(datosUsuario==null)datosUsuario=new HashMap();
%>
<script type="text/javascript">
    var ncrpt = "<%=(String)datosUsuario.get("pass_usuario")%>";
    var mail = "<%=(String)datosUsuario.get("correo_usuario")%>";

    function pulsaEnter(){
            modificarDatos();
        }

    function cambiarPass(){
        document.getElementById("bloque2").style.display="none";
        document.getElementById("bloque3").style.display="";
        }

    function modificarDatos(){
        var hayModif=false;
        var modPass=false;

        var mensaje1="Vas a modificar los siguientes datos:\n";

        if(document.getElementById("email").value==""){
            alert("El correo es obligatorio.");
            return false;
        }else if(document.getElementById("email").value!=mail){
            mensaje1+="Tu correo.\n";
            hayModif=true;
        }
        if(document.getElementById("nuevaPass1").value!="" || document.getElementById("nuevaPass2").value!="" || document.getElementById("nuevaPass3").value!=""){
            hayModif=true;
            if(document.getElementById("nuevaPass1").value==""){
                alert("Debes escribir tu contraseña actual.");
                return false;
            }else if(document.getElementById("nuevaPass2").value==""){
                alert("Debes escribir tu nueva contraseña.");
                return false;
            }else if(document.getElementById("nuevaPass3").value==""){
                alert("Debes repetir tu nueva contraseña.");
                return false;
            }else if(SHA1(document.getElementById("nuevaPass1").value)!=ncrpt){
                alert("La contraseña actual que has puesto no coincide con la que tenemos guardada.");
                return false;
            }else if(document.getElementById("nuevaPass2").value!=document.getElementById("nuevaPass3").value){
                alert("Las contraseñas nuevas no coinciden.");
                return false;
            }else{
                mensaje1+="Tu contraseña.\n";
                modPass=true;
            }
        }

        if(hayModif){
           if(confirm(mensaje1)){
                document.frmDatos.correo.value=document.getElementById("email").value;
           if(modPass)
                document.frmDatos.pass.value=SHA1(document.getElementById("nuevaPass2").value);
           else{
                document.frmDatos.pass.value=ncrpt;
           }
           document.frmDatos.submit();
        }
    }else{
        alert("No has modificado nada...");
    }
}
</script>
<form name="frmDatos" method="post" action="./mi_cuenta.html" accept-charset="iso-8859-1">
    <input type="hidden" name="accion" value="guardar"/>
    <input type="hidden" name="correo" value=""/>
    <input type="hidden" name="pass" value=""/>
</form>
<p>
    Sólo se pueden modificar la cuenta de correo y la contraseña. Si quieres modificar otro dato ponte en contacto con los administradores de lá página.
</p>
<!-- Inicio Confirmación de que los datos se ha guardado -->
<%
String ind_guardado=(String)request.getAttribute("resultadoGuardar");
if(ind_guardado==null)ind_guardado="";

if("1".equals(ind_guardado)){
%>
    <center>
        <div id="divConfirmacion" style="border:solid 2px #f00;padding:5px;width:45%;font-weight:bold;color:#f00;text-align:center;">
            Datos modificados con éxito.
        </div>
    </center>
    <script type="text/javascript">
        function ocultaDivConfirm(){
            document.getElementById("divConfirmacion").style.display="none";
        }
        setTimeout("ocultaDivConfirm()",6000);
    </script>
<%
    }
%>
<!-- Fin Confirmación de que los datos se ha guardado -->

<div style="padding:5px;float:left;width:100%;" id="bloque1">
    <span class="labelMiCuenta">Nick:</span> <%=datosUsuario.get("ident_usuario")%><BR /><BR />
    <span class="labelMiCuenta">Nombre:</span> <%=datosUsuario.get("nombre_usuario")%><BR /><BR />
    <span class="labelMiCuenta">Mail:</span> <input type="text" class="campoForm" name="email" id="email" size="75" maxlength="75" value="<%=datosUsuario.get("correo_usuario")%>"/><BR />
</div>
<div style="padding:5px;float:left;width:100%;" id="bloque2">
    <span class="labelMiCuenta2">Contraseña:</span> <a class="linkStandar" href="#" onClick="cambiarPass();">Quiero cambiarla.</a>
</div>
<div style="padding:5px;float:left;width:100%;display:none;" id="bloque3">
    <span class="labelMiCuenta2">Contraseña actual:</span> <input size="30" maxlength="30" type="password" name="nuevaPass1" id="nuevaPass1" value=""/><BR /><BR />
    <span class="labelMiCuenta2">Nueva contraseña:</span> <input size="30" maxlength="30" type="password" name="nuevaPass2" id="nuevaPass2" value=""/><BR /><BR />
    <span class="labelMiCuenta2">Repítela:</span> <input size="30" maxlength="30" type="password" name="nuevaPass3" id="nuevaPass3" value=""/>
</div>
<div style="padding:5px;float:left;width:100%;text-align:right;" id="bloque4">
    <a href="#" class="linkStandar" onClick="modificarDatos();">Cambiar datos</a>
</div>