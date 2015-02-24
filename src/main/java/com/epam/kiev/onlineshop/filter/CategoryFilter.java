package com.epam.kiev.onlineshop.filter;

import com.epam.kiev.onlineshop.manager.ConfigurationManager;
import com.epam.kiev.onlineshop.servlet.MainServlet;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;

/**
 * Created by Администратор on 12/27/14.
 */
@WebFilter(filterName = "category",
        urlPatterns = {"/*"}
        ,servletNames = {"MainServlet"})
public class CategoryFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Logger.getLogger(CategoryFilter.class).info("Filter category is initialized");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        //Set Character Encoding Filter
        servletRequest.setCharacterEncoding("UTF-8");

        ServletContext context = servletRequest.getServletContext();
        String url = ((HttpServletRequest)servletRequest).getRequestURI();
        if(checkForImgPath(url)) {
            filterChain.doFilter(servletRequest,servletResponse);
        } else {
            if(servletRequest.getParameter("command") == null){
                ((HttpServletRequest) servletRequest).getSession().setAttribute("url", url);
                Logger.getLogger(CategoryFilter.class).info("URL ADDED");
                // forward to parsing URL
                context.getRequestDispatcher(ConfigurationManager.getInstance().
                        getProperty(ConfigurationManager.POST_TO_URL)).
                        forward(servletRequest, servletResponse);
            } else {
                //refresh notify message
                ((HttpServletRequest) servletRequest).getSession().setAttribute("notify", null);
                //Setting Locale
                setLocale(((HttpServletRequest) servletRequest));
//                setEncodingToParam(((HttpServletRequest) servletRequest));
                context.getRequestDispatcher("/servlet").
                            forward(servletRequest, servletResponse);
            }
        }
    }


    private void setLocale(HttpServletRequest servletRequest) {
        if (servletRequest.getSession().getAttribute("locale") == null) {
            servletRequest.getSession().setAttribute("locale", servletRequest.getLocale());
        }
    }

    private boolean checkForImgPath(String url) {
        boolean res = false;

        if(url.contains(".jpg")) {
            res = true;
        }

        if(url.contains(".ico")) {
            res = true;
        }

        return res;
    }

    @Override
    public void destroy() {

    }
}
