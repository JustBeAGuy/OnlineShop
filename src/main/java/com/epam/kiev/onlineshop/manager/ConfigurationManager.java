/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epam.kiev.onlineshop.manager;

import java.util.ResourceBundle;

/**
 *
 * @author MAXIM
 */
public class ConfigurationManager {

    private static volatile ConfigurationManager instance;
    private static ResourceBundle resource;
    private static final String BUNDLE_NAME = "config";
    public static final String DRIVER = "DRIVER";
    public static final String URL = "URL";
    public static final String MIN_POOL_SIZE = "MIN_POOL_SIZE";
    public static final String MAX_POOL_SIZE = "MAX_POOL_SIZE";
    public static final String MAIN = "MAIN";
    public static final String LOG4J = "LOG4J";
    public static final String NO_PAGE = "NO_PAGE";
    public static final String LOGIN = "LOGIN";
    public static final String REGISTER = "REGISTER";
    public static final String CART = "CART";
    public static final String INDEX = "INDEX";
    public static final String BLANK = "BLANK";
    public static final String POST_TO_URL = "POST_TO_URL";
    public static final String CATEGORY = "CATEGORY";
    public static final String ADMIN = "ADMIN";
    public static final String ADMIN_USERS = "ADMIN_USERS";
    public static final String ADMIN_CATALOG = "ADMIN_CATALOG";
    public static final String ADMIN_CART = "ADMIN_CART";
    public static final String ACCOUNT = "ACCOUNT";
    public static final String ACCESS_DENIED = "ACCESS_DENIED";
    //HEAD
    public static final String HEAD_MAIN = "HEAD_MAIN";
    public static final String HEAD_ACCOUNT = "HEAD_ACCOUNT";
    public static final String HEAD_LOGIN = "HEAD_LOGIN";
    //END HEAD
    public static final String IMG_GOOD = "IMG_GOOD";
    public static final String GOOD = "GOOD";

    private ConfigurationManager() {
        resource = ResourceBundle.getBundle(BUNDLE_NAME);
    }

    public static ConfigurationManager getInstance() {
        if (instance == null){
            synchronized (ConfigurationManager.class){
                if (instance == null){
                    instance = new ConfigurationManager();
                }
            }
        }
        return instance;
    }

    public String getProperty(String key) {
        return (String) resource.getObject(key);
    }
}
