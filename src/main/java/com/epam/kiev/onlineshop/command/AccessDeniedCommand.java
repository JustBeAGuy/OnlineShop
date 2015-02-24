package com.epam.kiev.onlineshop.command;

import com.epam.kiev.onlineshop.manager.ConfigurationManager;
import com.epam.kiev.onlineshop.manager.MessageManager;
import com.epam.kiev.onlineshop.support.Message;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Администратор on 1/14/15.
 */
public class AccessDeniedCommand implements ICommand {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        ConfigurationManager configurationManager = ConfigurationManager.getInstance();

        Message.notify(req, MessageManager.ACCESS_DENIED, MessageManager.TYPE_ERROR);
        String body = configurationManager.getProperty(configurationManager.ACCESS_DENIED); // ACCESS DENIED Page
        req.getSession().setAttribute("body",body);
        return configurationManager.getProperty(configurationManager.MAIN);
    }
}
