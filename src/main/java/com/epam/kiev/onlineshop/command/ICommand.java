package com.epam.kiev.onlineshop.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Администратор on 1/2/15.
 */
public interface ICommand {
    String execute(HttpServletRequest req, HttpServletResponse resp);
}
