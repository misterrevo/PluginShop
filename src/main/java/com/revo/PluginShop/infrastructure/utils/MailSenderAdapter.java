package com.revo.PluginShop.infrastructure.utils;

import com.revo.PluginShop.domain.exception.EmailSendingException;
import com.revo.PluginShop.domain.port.MailSenderPort;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;

@Component
class MailSenderAdapter implements MailSenderPort {

    private static final String FROM = "Plugins.Revo-Dev.pl";
    private static final String HOST_PROPERTIES = "mail.smtp.host";
    private static final String PORT_PROPERTIES = "mail.smtp.port";
    private static final String TYPE = "text/html; charset=utf-8";
    
    @Value("${mail.smtp.host}")
    private String host;
    @Value("${mail.smtp.port}")
    private String port;
    
    @Override
    public void sendEmail(String to, String title, String message) throws EmailSendingException {
        try {
            Transport.send(getMimeMessage(to, title, message));
        } catch (Exception exception) {
            throw new EmailSendingException(to);
        }
    }

    private MimeMessage getMimeMessage(String to, String title, String message) throws Exception {
        var mimeMessage = new MimeMessage(getSession());
        mimeMessage.setFrom(new InternetAddress(FROM));
        mimeMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
        mimeMessage.setSubject(title);
        mimeMessage.setContent(getMultipart(message));
        return mimeMessage;
    }

    private Session getSession(){
        return Session.getInstance(getProperties());
    }

    private Properties getProperties(){
        var properties = new Properties();
        properties.put(HOST_PROPERTIES, host);
        properties.put(PORT_PROPERTIES, port);
        return properties;
    }

    private Multipart getMultipart(String message) throws Exception {
        var multipart = new MimeMultipart();
        multipart.addBodyPart(getMimeBodyPart(message));
        return multipart;
    }

    private MimeBodyPart getMimeBodyPart(String message) throws Exception{
        var mimeBodyPart = new MimeBodyPart();
        mimeBodyPart.setContent(message, TYPE);
        return mimeBodyPart;
    }
}
