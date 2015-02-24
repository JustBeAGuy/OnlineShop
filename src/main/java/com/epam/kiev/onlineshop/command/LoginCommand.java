package com.epam.kiev.onlineshop.command;

import com.epam.kiev.onlineshop.dao.factory.DAOFactory;
import com.epam.kiev.onlineshop.entity.User;
import com.epam.kiev.onlineshop.entity.UserDescription;
import com.epam.kiev.onlineshop.manager.ConfigurationManager;
import com.epam.kiev.onlineshop.manager.MessageManager;
import com.epam.kiev.onlineshop.support.Message;
import com.epam.kiev.onlineshop.support.Notify;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Locale;

/**
 * Created by Администратор on 1/5/15.
 */
public class LoginCommand implements ICommand{

    private static final int ADMIN_PERMISSION = 2;
    private static final int BLOCKED_PERMISSION = 0;

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {

        ConfigurationManager configurationManager = ConfigurationManager.getInstance();
        String res = configurationManager.MAIN;
        String body = configurationManager.LOGIN;

        //loging and set account to Session
        if (req.getSession().getAttribute("account") == null) {

            String login = req.getParameter("login");
            String password = req.getParameter("password");
            User user = null;
            if((login != null) && (login != "")) {
                DAOFactory factory = DAOFactory.getDAOFactory(DAOFactory.MY_SQL);
                user = factory.getUserDAO().findUser(login);

                Logger.getLogger(LoginCommand.class).info("Loginning: login " + user.getLogin() + ", password: " + user.getPassword());

                if((user.getLogin() != null) && (user.getLogin().equals(login)) && (user.getPassword().equals(password))) {
                    req.getSession().setAttribute("account", user); // Setting account to session
                    req.getSession().setAttribute("headRight"
                            , configurationManager.getProperty(ConfigurationManager.HEAD_ACCOUNT)); // Setting HEAD to ACCOUNT
                    //set userDescription From DB
                    req.getSession().setAttribute("userDescription", getUserDescription(user.getId()));
                    HashMap<String, ICommand> commands = CommandFactory.getInstance().userCommands();    // getting User Commands
                    //Checking for Admin
                    if(user.getPermission() == ADMIN_PERMISSION) {
                        commands = CommandFactory.getInstance().adminCommands(); // getting Admin Commands
                    }
                    if(user.getPermission() == BLOCKED_PERMISSION) {
                        commands = CommandFactory.getInstance().blockedUserCommands(); // getting Blocked Commands
                    }
                    req.getSession().setAttribute("commands", commands); // Setting Commands

                    //setting Success-Login notify message
                    Message.notify(req, MessageManager.LOGIN_SUCC, MessageManager.TYPE_SUCC);

                    body = configurationManager.INDEX;
                } else {
                    //setting UnSuccess-Login notify message
                    Message.notify(req, MessageManager.LOGIN_ERROR, MessageManager.TYPE_ERROR);
                }
            }
        } else {
            //setting Success-Login notify message
            Message.notify(req, MessageManager.LOGIN_SUCC, MessageManager.TYPE_SUCC);

            body = configurationManager.INDEX;
        }

        req.getSession().setAttribute("body",configurationManager.getProperty(body));

        return configurationManager.getProperty(res);
    }

    private UserDescription getUserDescription(String userId) {
        DAOFactory factory = DAOFactory.getDAOFactory(DAOFactory.MY_SQL);
        return factory.getUserDescriptionDAO().findUserDescription(userId);
    }
}
