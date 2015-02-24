package com.epam.kiev.onlineshop.command;

import com.epam.kiev.onlineshop.manager.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Locale;

/**
 * Created by Администратор on 2/6/15.
 */
public class LanguageCommand implements ICommand{
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        ConfigurationManager configurationManager = ConfigurationManager.getInstance();
        if(req.getParameter("lang") != null) {
            Locale locale = new Locale(req.getParameter("lang"));
            req.getSession().setAttribute("locale", locale);
        }
        return configurationManager.getProperty(configurationManager.MAIN);
    }
}
