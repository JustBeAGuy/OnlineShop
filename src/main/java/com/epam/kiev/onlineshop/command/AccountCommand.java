package com.epam.kiev.onlineshop.command;

import com.epam.kiev.onlineshop.dao.factory.DAOFactory;
import com.epam.kiev.onlineshop.entity.Good;
import com.epam.kiev.onlineshop.entity.User;
import com.epam.kiev.onlineshop.entity.UserDescription;
import com.epam.kiev.onlineshop.manager.ConfigurationManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

/**
 * Created by Администратор on 1/12/15.
 */
public class AccountCommand implements ICommand {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        ConfigurationManager configurationManager = ConfigurationManager.getInstance();
        String body = configurationManager.getProperty(configurationManager.ACCOUNT); // Go to ACCOUNT Page
        req.getSession().setAttribute("body",body);
        if(req.getParameter("account_user_update") != null) {
            UserDescription userDescription = (UserDescription) req.getSession().getAttribute("userDescription");
            updateUserDescription(userDescription, req);
            if(req.getParameter("account_user_password") != null) {
                setNewPassword(req);
            }
        }
        return configurationManager.getProperty(configurationManager.MAIN);
    }

    private void setNewPassword(HttpServletRequest req) {
        User user = (User) req.getSession().getAttribute("account");
        if(user.getPassword().equals(req.getParameter("account_user_password"))) {
            user.setPassword(req.getParameter("account_user_newpassword"));
            updateUser(user);
        }
    }

    private boolean updateUser(User user) {
        DAOFactory factory = DAOFactory.getDAOFactory(DAOFactory.MY_SQL);
        return factory.getUserDAO().updateUser(user);
    }

    private void updateUserDescription(UserDescription userDescription, HttpServletRequest req) {
        try {
            userDescription.setName(req.getParameter("account_user_name"));
            userDescription.setSurname(req.getParameter("account_user_surname"));
            userDescription.setPhone(req.getParameter("account_user_phone"));
            userDescription.setCity(req.getParameter("account_user_city"));
            userDescription.setStreet(req.getParameter("account_user_street"));
            userDescription.setHouse(req.getParameter("account_user_house"));
            userDescription.setZip(req.getParameter("account_user_zip"));
        } catch (NullPointerException e) {
            Logger.getLogger(AccountCommand.class).info(e);
        }

        DAOFactory factory = DAOFactory.getDAOFactory(DAOFactory.MY_SQL);
        factory.getUserDescriptionDAO().updateUserDescription(userDescription);
    }
}
