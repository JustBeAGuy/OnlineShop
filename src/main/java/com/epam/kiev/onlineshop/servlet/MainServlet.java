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
//        ConnectionManager conM = ConnectionManager.getInstance();
//        Connection connection = conM.getConnection();
//        try {
//            PreparedStatement pStatement = connection.prepareStatement("SELECT * FROM USERS WHERE LOGIN = ? AND PASSWORD = ?");
//            pStatement.setString(1, "Dream");
//            pStatement.setString(2, "Team");
//            pStatement.execute();
//            ResultSet rs = pStatement.getResultSet();
//            rs.next();
//            req.setAttribute("login",rs.getString("users.login"));
//            req.setAttribute("password",rs.getString("users.password"));
//        } catch (SQLException e) {
//
//        } catch (Exception e) {
//
//        }
//        finally {
//            conM.putConnection(connection);
//        }
        //req.setAttribute("name",req.getRequestURI());
        Logger.getLogger(MainServlet.class).info("Servlet Active");
        String page = null;
//        try {
            ICommand command = CommandFactory.getInstance().getCommand(req);
            page = command.execute(req, resp);
//        } catch (ServletException e) {
//            e.printStackTrace();
//            request.setAttribute("messageError", MessageManager.getInstance().getProperty(MessageManager.SERVLET_EXECPTION));
//
//        } catch (IOException e) {
//            e.printStackTrace();
//            request.setAttribute("messageError", MessageManager.getInstance().getProperty(MessageManager.IO_EXCEPTION));
//
//        }
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
