package com.revo.PluginShop.domain.port;

import com.revo.PluginShop.domain.exception.EmailSendingException;

public interface MailSenderPort {

    void sendEmail(String to, String title, String message) throws EmailSendingException;
}
