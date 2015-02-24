package com.epam.kiev.onlineshop.command;

import com.epam.kiev.onlineshop.manager.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Администратор on 1/2/15.
 */
public class NoCommand implements ICommand {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        ConfigurationManager configurationManager = ConfigurationManager.getInstance();
        req.getSession().setAttribute("body",configurationManager.getProperty(configurationManager.NO_PAGE));
        return configurationManager.getProperty(configurationManager.MAIN);
    }
}
