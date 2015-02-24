package com.epam.kiev.onlineshop.listener;

import com.epam.kiev.onlineshop.manager.ConfigurationManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.File;
import java.util.logging.Level;

/**
 * Created by Администратор on 12/27/14.
 */
@WebListener("app context listener")
public class ContextListener implements ServletContextListener {
    private static final ConfigurationManager conf = ConfigurationManager.getInstance();
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext context = servletContextEvent.getServletContext();
        String realPath = context.getRealPath("");
        String fullPath = realPath + File.separator + conf.getProperty(conf.LOG4J);
        System.setProperty("rootPath", realPath);
        PropertyConfigurator.configure(fullPath); // getting Log4j properties
        Logger.getLogger(ContextListener.class).info("LogProperties is uploaded");
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
