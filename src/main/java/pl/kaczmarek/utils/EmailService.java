package pl.kaczmarek.utils;


import org.springframework.stereotype.Service;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@Service
public class EmailService {


    private static String SENDER = "inteligentneparkingi";  // GMail user name (just the part before "@gmail.com")
    private static String PASSWORD = "Parking123"; // GMail password
    private static String RECIPIENT = "kaczmarek.jacek10@gmail.com";


    public void sendRegisterEmail(String to, String name) {
        String[] receiver = { to };
        Properties props = System.getProperties();
        String host = "smtp.gmail.com";
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.user", SENDER);
        props.put("mail.smtp.password", PASSWORD);
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");

        Session session = Session.getDefaultInstance(props);
        MimeMessage message = new MimeMessage(session);

        try {
            message.setFrom(new InternetAddress(SENDER));
            InternetAddress[] toAddress = new InternetAddress[receiver.length];

            // To get the array of addresses
            for( int i = 0; i < receiver.length; i++ ) {
                toAddress[i] = new InternetAddress(receiver[i]);
            }

            for( int i = 0; i < toAddress.length; i++) {
                message.addRecipient(Message.RecipientType.TO, toAddress[i]);
            }

            String subject = "Rejerstracja parkingu " + name;
            String body = "Witamy w inteligentnych parkingach. Nazwa twojego parkingu to:"
                + name + "Życzymy udanego korzystania z systemu!" + "Pozdrawiamy, zespół parkingi";

            message.setSubject(subject);
            message.setText(body);
            Transport transport = session.getTransport("smtp");
            transport.connect(host, SENDER, PASSWORD);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        }
        catch (AddressException ae) {
            ae.printStackTrace();
        }
        catch (MessagingException me) {
            me.printStackTrace();
        }
    }
}

