package com.epam.kiev.onlineshop.command;

import com.epam.kiev.onlineshop.manager.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Администратор on 1/6/15.
 */
public class LogoutCommand implements ICommand {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        req.getSession().invalidate();
        return ConfigurationManager.getInstance().getProperty(ConfigurationManager.MAIN);
    }
}
