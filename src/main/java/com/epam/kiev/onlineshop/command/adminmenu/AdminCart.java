package com.epam.kiev.onlineshop.command.adminmenu;

import com.epam.kiev.onlineshop.dao.factory.DAOFactory;
import com.epam.kiev.onlineshop.entity.Order;
import com.epam.kiev.onlineshop.entity.User;
import com.epam.kiev.onlineshop.manager.ConfigurationManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

/**
 * Created by Администратор on 2/3/15.
 */
public class AdminCart implements IAdminMenu{
    private static final int FIRST_PAGE = 1;
    private int itemsPerPage;
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) {
        ConfigurationManager configurationManager = ConfigurationManager.getInstance();
        String sortBy = "timechange"; // sorting by
        String cend = "DESC";
        String searchCol = "id"; // searching col
        String searchVal = "";
        itemsPerPage =  (Integer) req.getSession().getAttribute("itemsPerPage");


        // Updates
        if (req.getParameter("admin_cart_status_id") != null) {
            updateStatus(req.getParameter("admin_cart_status"),
                    req.getParameter("admin_cart_status_id")); // Update order status
        }

        //reset Attributes when refresh page
        if(req.getParameter("reset") != null) {
            resetAttributes(req, sortBy, cend, searchCol, searchVal);
        }

        //Searching
        if(req.getParameter("admin_cart_searchCol") != null) {
            req.getSession().setAttribute("admin_cart_searchCol", req.getParameter("admin_cart_searchCol"));
            req.getSession().setAttribute("admin_cart_searchVal", req.getParameter("admin_cart_searchVal"));
            //Set number of pages
            req.getSession().setAttribute("admin_cart_number_pages", getNumberOfPages(
                    req.getParameter("admin_cart_searchCol"),
                    req.getParameter("admin_cart_searchVal")));
            //Set current page
            req.getSession().setAttribute("admin_cart_curr_page", FIRST_PAGE);
        }
        searchCol = (String) req.getSession().getAttribute("admin_cart_searchCol");
        searchVal = (String) req.getSession().getAttribute("admin_cart_searchVal");

        //check for income param and set it
        getSortingParam(req);
        sortBy = (String) req.getSession().getAttribute("admin_cart_sorting_sortby");
        cend = (String) req.getSession().getAttribute("admin_cart_sorting_cend");

        //Setting current page
        if (req.getParameter("admin_cart_curr_page") != null) {
            try {
                int currPage = Integer.parseInt(req.getParameter("admin_cart_curr_page"));
                req.getSession().setAttribute("admin_cart_curr_page", currPage);
            } catch (ClassCastException e) {
                Logger.getLogger(AdminCart.class).info(e.getMessage());
            }
        }
        int page = (Integer) req.getSession().getAttribute("admin_cart_curr_page");
        // The first User number in the page
        int getFirstUserInPage = (page-1) * itemsPerPage;

        req.getSession().setAttribute("admin_content" ,
                configurationManager.getProperty(configurationManager.ADMIN_CART));
        req.getSession().setAttribute("admin_cart" ,
                getCart(sortBy, getFirstUserInPage, itemsPerPage, cend, searchCol, searchVal)); // Setting users for viewing
    }

    private boolean getSortingParam(HttpServletRequest req) {
        Boolean res = false;
        //Getting parameters to SORTING
        if(req.getParameter("admin_cart_sorting") != null) {
            String[] sortParam = req.getParameter("admin_cart_sorting").split("_");
            String cend = "ASC";
            if(sortParam[0].equals("de")) {
                cend = "DESC";
            }
            req.getSession().setAttribute("admin_cart_sorting_cend", cend);
            req.getSession().setAttribute("admin_cart_sorting_sortby", sortParam[1]);
            res = true;
        }
        return res;
    }

    private boolean updateStatus(String status, String id) {
        DAOFactory factory = DAOFactory.getDAOFactory(DAOFactory.MY_SQL);
        return factory.getOrderDAO().updateOrderStatus(status, id);
    }

    private ArrayList<Order> getCart(String sortBy, int from, int number, String cend, String searchCol, String searchVal) {
        DAOFactory factory = DAOFactory.getDAOFactory(DAOFactory.MY_SQL);
        return factory.getOrderDAO().getOrders(sortBy, from, number, cend, searchCol, searchVal);
    }

    //Removing attributes
    private void resetAttributes(HttpServletRequest req, String sortBy, String cend, String searchCol, String searchVal) {
        //Set Initial sorting , if we havent needed attribute
        req.getSession().setAttribute("admin_cart_sorting_sortby", sortBy);
        req.getSession().setAttribute("admin_cart_sorting_cend", cend);
        //Set number of pages
        req.getSession().setAttribute("admin_cart_number_pages", getNumberOfPages(searchCol, searchVal));
        //Set current page
        req.getSession().setAttribute("admin_cart_curr_page", FIRST_PAGE);
        //set without search
        req.getSession().setAttribute("admin_cart_searchCol", searchCol);
        req.getSession().setAttribute("admin_cart_searchVal", searchVal);
    }

    private int getNumberOfPages(String searchCol, String searchVal) {
        int res = 0;
        DAOFactory factory = DAOFactory.getDAOFactory(DAOFactory.MY_SQL);
        int numOfOrders = factory.getOrderDAO().getNumberOfOrders(searchCol, searchVal);
        res = numOfOrders / itemsPerPage;
        if((numOfOrders % itemsPerPage) != 0) {
            res++;
        };
        return res;
    }
}
