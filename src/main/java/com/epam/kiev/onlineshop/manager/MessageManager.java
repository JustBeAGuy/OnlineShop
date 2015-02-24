package com.epam.kiev.onlineshop.manager;

import com.epam.kiev.onlineshop.support.WINDOWS1251Control;

import java.util.Locale;
import java.util.ResourceBundle;

public class MessageManager {
    private static volatile MessageManager instanceDefault;
    private static volatile MessageManager instanceRu;
    private ResourceBundle resource;
    private static final String BUNDLE_NAME = "messages";
    public static final String SERVLET_EXECPTION = "SERVLET_EXCEPTION";
    public static final String IO_EXCEPTION = "IO_EXCEPTION";
    public static final String LOGIN_ERROR = "LOGIN_ERROR";
    public static final String LOGIN_SUCC = "LOGIN_SUCC";
    public static final String ACCESS_DENIED = "ACCESS_DENIED";
    public static final String GOOD_ADDED_CART = "GOOD_ADDED_CART";
    public static final String REMOVED_SUCC = "REMOVED_SUCC";
    public static final String ADDED_SUCC = "ADDED_SUCC";
    public static final String ORDER_SUCC = "ORDER_SUCC";
//  types of message
    public static final String TYPE_ERROR = "TYPE_ERROR";
    public static final String TYPE_SUCC = "TYPE_SUCC";
    //MessageManager with default local
    private MessageManager(){
        Locale defLocale = new Locale("en", "US");
        resource = ResourceBundle.getBundle(BUNDLE_NAME, defLocale);
    }

    private MessageManager(Locale locale){
        resource = ResourceBundle.getBundle(BUNDLE_NAME, locale, new WINDOWS1251Control());
    }

    public static MessageManager getInstance() {
        if (instanceDefault == null){
            synchronized (MessageManager.class){
                if (instanceDefault == null){
                    instanceDefault = new MessageManager();
                }
            }
        }
        return instanceDefault;
    }

    public static MessageManager getInstance(Locale locale) {
        MessageManager res = MessageManager.getInstance();

        if (locale.getLanguage().equals("ru")) {
            if (instanceRu == null){
                synchronized (MessageManager.class){
                    if (instanceRu == null){
                        instanceRu = new MessageManager(locale);
                    }
                }
            }
            res = instanceRu;
        }

        return res;

    }

    public String getProperty(String key) {
        return (String) resource.getObject(key);
    }
}
