package com.epam.kiev.onlineshop.support;

import com.epam.kiev.onlineshop.manager.MessageManager;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

/**
 * Created by Администратор on 2/8/15.
 */
public class Message {
    public static void notify(HttpServletRequest req, String message, String type) {
        req.getSession().setAttribute("notify",
                new Notify(message, type, (Locale) req.getSession().getAttribute("locale")));
    }
}
