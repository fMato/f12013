<script type="text/javascript">
    function doSubmitRegistro(){
        var hayNombre=false;
        var hayNick=false;
        var hayEmail=false;
        var coincidenPass=false;
        var hayPass=false;
        var aceptaCondiciones=false;

        if(document.frmRegistro.nombre_apell.value=="")
            hayNombre=false;
        else
            hayNombre=true;

        if(document.frmRegistro.ident_usu.value=="")
            hayNick=false;
        else
            hayNick=true;

        if(document.frmRegistro.email.value=="")
            hayEmail=false;
        else
            hayEmail=true;

        if(document.frmRegistro.pass.value=="")
            hayPass=false;
        else
            hayPass=true;

        if(document.frmRegistro.pass.value!=document.frmRegistro.repass.value)
            coincidenPass=false;
        else
            coincidenPass=true;

        if(document.frmRegistro.normativa.checked)
            aceptaCondiciones=true;
        else
            aceptaCondiciones=false;

        var msjError="";
        if(!hayNombre)
            msjError+="\nEl nombre es obligatorio.";
        if(!hayNick)
            msjError+="\nEl nick es obligatorio.";
        if(!hayEmail)
            msjError+="\nEl mail es obligatorio.";
        if(!hayPass)
            msjError+="\nLa contraseña es obligatoria.";
        if(!coincidenPass)
            msjError+="\nLas dos contraseñas introducidas no coinciden.";
        if(!aceptaCondiciones)
            msjError+="\nEs obligatorio aceptar la normativa.";

        if(msjError!="")
            alert(msjError);
        else{
            document.frmRegistro.pass_encr.value=SHA1(document.frmRegistro.pass.value);
            document.frmRegistro.pass.value="";
            document.frmRegistro.submit();
            }
        }

    function pulsaEnter(){
            doSubmitRegistro();
        }
</script>
<h3>Alta de usuraio</h3>
<p>
    Todos los campos son obligatorios.<BR />
    La cuenta será activada manualmente una vez verificada por los administradores de la página.
</p>
<FORM name="frmRegistro" method="POST" action="./alta_usuario.html" accept-charset="iso-8859-1">
    <p>
        <span class="labelFormRegistro">Nombre y apellidos:</span> <input type="text" class="campoForm" name="nombre_apell" size="75" maxlength="75" value=""/>
        <BR /><span class="labelFormRegistro">Usuario:</span> <input type="text" class="campoForm" name="ident_usu" size="15" maxlength="15" value=""/> <i>(con este usuario accederás a la web)</i>
        <BR /><span class="labelFormRegistro">Email:</span> <input type="text" class="campoForm" name="email" size="75" maxlength="75" value=""/>
        <BR /><span class="labelFormRegistro">Contraseña:</span> <input type="password" class="campoForm" name="pass" size="30" maxlength="30" value=""/><input type="hidden" name="pass_encr" size="40" maxlength="40" value=""/>
        <BR /><span class="labelFormRegistro">Repite la contraseña:</span> <input type="password" class="campoForm" name="repass" size="30" maxlength="30" value=""/>
        <BR /><input type="checkbox" name="normativa" value="S"/> He leido y acepto la <A class="linkStandar" href="./normativa.html">normativa</A> para participar en el juego.

    </p>
</FORM>
<p align="right">
    <A href="#" class="linkStandar" onclick="doSubmitRegistro();">Continuar</A>
</p>
