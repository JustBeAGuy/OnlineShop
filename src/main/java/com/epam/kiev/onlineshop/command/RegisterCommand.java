package com.epam.kiev.onlineshop.command;

import com.epam.kiev.onlineshop.dao.factory.DAOFactory;
import com.epam.kiev.onlineshop.entity.User;
import com.epam.kiev.onlineshop.entity.UserDescription;
import com.epam.kiev.onlineshop.manager.ConfigurationManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Администратор on 1/6/15.
 */
public class RegisterCommand implements ICommand{

    //fullfill User Entity
    private boolean setUser(HttpServletRequest req, User user) {
        Boolean res = false;
        if (req.getParameter("password") != null && (req.getParameter("password") != "")) {
            user.setLogin(req.getParameter("login"));
            user.setPassword(req.getParameter("password"));
            user.setEmail(req.getParameter("email"));

            res = true;
        }
        return res;
    }
    //insert UserDescription
    private boolean insertUserDescription(UserDescription userDescription) {
        DAOFactory factory = DAOFactory.getDAOFactory(DAOFactory.MY_SQL);
        return factory.getUserDescriptionDAO().insertUserDescription(userDescription);
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {

        ConfigurationManager configurationManager = ConfigurationManager.getInstance();
        String res = configurationManager.MAIN;
        String body = configurationManager.REGISTER;

        //registration and login
        if (req.getSession().getAttribute("account") == null) {

            String login = req.getParameter("login");
            String password = req.getParameter("password");
            User user = null;
            if((login != null) && (login != "")) {
                DAOFactory factory = DAOFactory.getDAOFactory(DAOFactory.MY_SQL);
                user = factory.getUserDAO().findUser(login);
                if (user.getLogin() == null) {
                    if(setUser(req,user)) {
                        factory = DAOFactory.getDAOFactory(DAOFactory.MY_SQL);
                        if(factory.getUserDAO().insertUser(user)) {  //Inserting in DB
//                            Logger.getLogger(RegisterCommand.class).info("Loginning: login " + user.getLogin()
//                                    + ", password: " + user.getPassword());
                            //creating, setting and adding userDescription
                            UserDescription userDescription = new UserDescription();
                            userDescription.setId(user.getId());
                            insertUserDescription(userDescription);
                            req.getSession().setAttribute("userDescription", userDescription);

//                            req.getSession().setAttribute("account", user.getLogin()); // Setting account to session
//                            req.getSession().setAttribute("headRight"
//                                    , configurationManager.getProperty(ConfigurationManager.HEAD_ACCOUNT)); // Setting HEAD to ACCOUNT
                            body = configurationManager.INDEX;
                        }
                    }
                }
            }
        } else {
            body = configurationManager.INDEX;
        }

        req.getSession().setAttribute("body",configurationManager.getProperty(body));

        return configurationManager.getProperty(res);
    }
}
