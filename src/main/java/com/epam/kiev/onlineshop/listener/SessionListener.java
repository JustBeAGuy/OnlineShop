package com.epam.kiev.onlineshop.listener;

import com.epam.kiev.onlineshop.command.CommandFactory;
import com.epam.kiev.onlineshop.entity.Good;
import com.epam.kiev.onlineshop.manager.ConfigurationManager;
import org.apache.log4j.Logger;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.ArrayList;

/**
 * Created by Администратор on 1/6/15.
 */
@WebListener("Session create listener")
public class SessionListener implements HttpSessionListener {
    // Setting of Attributes, when session is created
    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
        ConfigurationManager confMan = ConfigurationManager.getInstance();
        HttpSession session = httpSessionEvent.getSession();

        session.setAttribute("body",confMan.getProperty(ConfigurationManager.INDEX));
        session.setAttribute("head",confMan.getProperty(ConfigurationManager.HEAD_MAIN));
        session.setAttribute("headRight",confMan.getProperty(ConfigurationManager.HEAD_LOGIN));
        //Setting blank cart
        session.setAttribute("cart", new ArrayList<Good>());

        //Set nonUser available Commands
        session.setAttribute("commands", CommandFactory.getInstance().noneUserCommands());

        //Items Per Page
        session.setAttribute("itemsPerPage", 20);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {

    }
}
