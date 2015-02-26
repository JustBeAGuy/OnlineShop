package com.epam.kiev.onlineshop.servlet;

import com.epam.kiev.onlineshop.command.CommandFactory;
import com.epam.kiev.onlineshop.command.ICommand;
import com.epam.kiev.onlineshop.dao.connection.ConnectionManager;
import com.epam.kiev.onlineshop.manager.ConfigurationManager;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

@WebServlet(asyncSupported = true, value = "/servlet")
@MultipartConfig(fileSizeThreshold=1024*1024*2, // 2MB
        maxFileSize=1024*1024*10,      // 10MB
        maxRequestSize=1024*1024*50)    //50MB
public class MainServlet extends HttpServlet{
    protected void doRequest(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        Logger.getLogger(MainServlet.class).info("Servlet Active");
        String page = null;

            ICommand command = CommandFactory.getInstance().getCommand(req);
            page = command.execute(req, resp);

        getServletContext().getRequestDispatcher(page).forward(req, resp);
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        doRequest(req,resp);
    }
}
