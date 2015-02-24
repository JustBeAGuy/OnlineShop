package com.epam.kiev.onlineshop.manager;

import com.epam.kiev.onlineshop.support.WINDOWS1251Control;
import org.apache.log4j.Logger;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Created by Администратор on 2/20/15.
 */
public class TextManager {
    private static volatile TextManager instanceDefault;
    private static volatile TextManager instanceRu;
    private ResourceBundle resource;
    private static final String BUNDLE_NAME = "textManager";

    //TextManager with default local
    private TextManager(){
        Locale defLocale = new Locale("en", "US");
        resource = ResourceBundle.getBundle(BUNDLE_NAME, defLocale);
    }

    private TextManager(Locale locale){
        resource = ResourceBundle.getBundle(BUNDLE_NAME, locale, new WINDOWS1251Control());
    }

    public static TextManager getInstance() {
        if (instanceDefault == null){
            synchronized (TextManager.class){
                if (instanceDefault == null){
                    instanceDefault = new TextManager();
                }
            }
        }
        return instanceDefault;
    }

    public static TextManager getInstance(Locale locale) {
        TextManager res = TextManager.getInstance();

        if (locale.getLanguage().equals("ru")) {
            if (instanceRu == null){
                synchronized (TextManager.class){
                    if (instanceRu == null){
                        instanceRu = new TextManager(locale);
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
