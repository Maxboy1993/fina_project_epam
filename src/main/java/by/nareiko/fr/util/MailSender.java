package by.nareiko.fr.util;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.ResourceBundle;

public class MailSender {
    private static final String MAIL_PROPERTIES_PATH = "mail";
    private static final String USER_NAME = "mail.user.name";
    private static final String PASSWORD = "mail.user.password";
    private static final String HOST_KEY = "mail.smtp.host";
    private static final String AUTH_KEY = "mail.smtp.auth";
    private static final String PORT_KEY = "mail.smtp.port";
    private static final String SOCKET_PORT_KEY = "mail.smtp.socketFactory.port";
    private static final String SOCKET_CLASS_KEY = "mail.smtp.socketFactory.class";

    private static final String EMAIL_SUBJECT = "Email address confirmation";
    private static final String EMAIL_TEXT = "<b>Thanks for registration!</b><br><p>Please confirm your email address - click the link below</p>";
    private static final String EMAIL_LINK = "<a href=http://localhost:8080/FilmsRaiting_war_exploded/controller?command=confirm_email&login=";


    private static final Logger LOGGER = LogManager.getLogger();

    private String sendToAddress;

    public MailSender(String sendToAddress) {
        this.sendToAddress = sendToAddress;
    }

    public void send() {
        try {
            Message message = initMessage();
            Transport.send(message);
        } catch (AddressException e) {
            LOGGER.error("incorrect address " + sendToAddress + " : ", e);
        } catch (MessagingException e) {
            LOGGER.error("error generating or sending mail", e);
        }
    }

    private MimeMessage initMessage() throws MessagingException {
        ResourceBundle bundle = ResourceBundle.getBundle(MAIL_PROPERTIES_PATH);
        String login = bundle.getString(USER_NAME);
        String password = bundle.getString(PASSWORD);
        String auth = bundle.getString(AUTH_KEY);
        String host = bundle.getString(HOST_KEY);
        String port = bundle.getString(PORT_KEY);
        String socketPort = bundle.getString(SOCKET_PORT_KEY);
        String socketClass = bundle.getString(SOCKET_CLASS_KEY);
        Properties properties = new Properties();
        properties.setProperty(HOST_KEY, host);
        properties.setProperty(AUTH_KEY, auth);
        properties.setProperty(PORT_KEY, port);
        properties.setProperty(SOCKET_PORT_KEY, socketPort);
        properties.setProperty(SOCKET_CLASS_KEY, socketClass);
        Session session = Session.getDefaultInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(login, password);
            }
        });
        session.setDebug(true);

        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(login));
        message.setSubject(EMAIL_SUBJECT);
        String mailText = emailBodyCreator(sendToAddress);
        message.setContent(mailText, "text/html");
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(sendToAddress));
        return message;
    }

    private String emailBodyCreator(String loginParameter) {
        StringBuilder builder = new StringBuilder();
        builder.append(EMAIL_TEXT);
        builder.append("<br>");
        builder.append(EMAIL_LINK);
        builder.append(loginParameter);
        builder.append(">Confirm your email</a>");
        return builder.toString();
    }
}
