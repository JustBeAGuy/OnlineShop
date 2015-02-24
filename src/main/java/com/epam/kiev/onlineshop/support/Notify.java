package com.epam.kiev.onlineshop.support;

import com.epam.kiev.onlineshop.manager.ConfigurationManager;
import com.epam.kiev.onlineshop.manager.MessageManager;

import java.util.Locale;

/**
 * Created by Администратор on 2/5/15.
 */
public class Notify {
    private String type;
    private String message;
    private MessageManager messageManager;


    public Notify() {
        messageManager = MessageManager.getInstance();
    }

    public Notify(String message, String type) {
        messageManager = MessageManager.getInstance();
        setType(type);
        setMessage(message);
    }

    public Notify(String message, String type, Locale locale) {
        messageManager = MessageManager.getInstance(locale);
        setType(type);
        setMessage(message);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = messageManager.getProperty(type);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = messageManager.getProperty(message);
    }
}
