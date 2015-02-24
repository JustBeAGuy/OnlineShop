package com.epam.kiev.onlineshop.command.adminmenu;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Администратор on 1/15/15.
 */
public interface IAdminMenu {
    void execute(HttpServletRequest req, HttpServletResponse resp);
}
