/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fw.comunes;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author Mato
 */
public class EnviaMail {

    public EnviaMail() {
        try {
            // Propiedades de la conexión
            Properties props = new Properties();
            props.setProperty("mail.smtp.host", "smtp.gmail.com");
            props.setProperty("mail.smtp.starttls.enable", "true");
            props.setProperty("mail.smtp.port", "587");
            props.setProperty("mail.smtp.user", "f1Sopra@gmail.com");
            props.setProperty("mail.smtp.auth", "true");
System.out.println("1");
            // Preparamos la sesion
            Session session = Session.getDefaultInstance(props);

            // Construimos el mensaje
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress("f1Sopra@gmail.com"));
            message.addRecipient(
                Message.RecipientType.TO,
                new InternetAddress("francisco.mato@gmail.com"));
            message.setSubject("Hola");
            message.setText("Mensajito con Java Mail <b>prueba</b>",
                    "ISO-8859-1",
                    "html");

            // Lo enviamos.
            Transport t = session.getTransport("smtp");
            t.connect("f1sopra@gmail.com", "Traicion");
            t.sendMessage(message, message.getAllRecipients());
System.out.println("2");
            // Cierre.
            t.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
