package com.epam.kiev.onlineshop.command;

import com.epam.kiev.onlineshop.command.adminmenu.AdminCart;
import com.epam.kiev.onlineshop.command.adminmenu.AdminCatalog;
import com.epam.kiev.onlineshop.command.adminmenu.AdminUsers;
import com.epam.kiev.onlineshop.command.adminmenu.IAdminMenu;
import com.epam.kiev.onlineshop.dao.factory.DAOFactory;
import com.epam.kiev.onlineshop.entity.User;
import com.epam.kiev.onlineshop.manager.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Администратор on 1/14/15.
 */
public class AccountAdminCommand implements ICommand {

    private HashMap<String, IAdminMenu> adminMenu;

    public AccountAdminCommand() {
        initAdminMenu();
    }

    private void initAdminMenu() {
        adminMenu = new HashMap<String, IAdminMenu>();

        adminMenu.put("admin_users", new AdminUsers());
        adminMenu.put("admin_catalog", new AdminCatalog());
        adminMenu.put("admin_cart", new AdminCart());
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {

        ConfigurationManager configurationManager = ConfigurationManager.getInstance();

        String body = configurationManager.getProperty(configurationManager.ADMIN); // Go to ADMIN Page
        req.getSession().setAttribute("admin_content" ,
                configurationManager.getProperty(configurationManager.BLANK));
        req.getSession().setAttribute("body",body);

        IAdminMenu admMenuCommand = adminMenu.get(req.getParameter("admin_content"));
        if(admMenuCommand != null) {
            admMenuCommand.execute(req,resp);      // execute needed element from adminMenu
        }

        return configurationManager.getProperty(configurationManager.MAIN);
    }

}
