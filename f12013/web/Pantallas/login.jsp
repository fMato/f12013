<script type="text/javascript">
    function pulsaEnter(){
            doSubmitLogin();
        }
    function doSubmitLogin(){
        var hayNick=false;
        var hayPass=false;

        if(document.frmLogin.ident_usu.value=="")
            hayNick=false;
        else
            hayNick=true;

        if(document.frmLogin.pass.value=="")
            hayPass=false;
        else
            hayPass=true;

        if(!hayNick)
            alert("El usuario es obligarotio.");
        else if(!hayPass)
            alert("La contraseña es obligatoria.");
        else{
            document.frmLogin.pass_encp.value=SHA1(document.frmLogin.pass.value);
            document.frmLogin.pass.value="";
            document.frmLogin.submit();
            }
        }
</script>
<%
String actionConex=(String)request.getAttribute("actionConex");
if(actionConex==null)actionConex="mi_apuesta";
%>
<h3>Identificación de usuario.</h3>
<FORM name="frmLogin" method="POST" action="./<%=actionConex%>.html" accept-charset="iso-8859-1">
    <p>
        <span class="labelFormLogin">Usuario:</span> <input type="text" class="campoForm" name="ident_usu" size="15" maxlength="15" value=""/>
        <BR /><span class="labelFormLogin">Contraseña:</span><input type="password" class="campoForm" name="pass" size="30" maxlength="30" value=""/><input type="hidden" class="campoForm" name="pass_encp" size="40" maxlength="40" value=""/>
        <input type="hidden" name="proceso_conexion" value="S"/>
    </p>
</FORM>
<p align="right">
    <A href="#" class="linkStandar" onClick="doSubmitLogin();">Continuar</A>
</p>