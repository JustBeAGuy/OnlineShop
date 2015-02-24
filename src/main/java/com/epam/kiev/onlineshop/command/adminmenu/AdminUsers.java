package com.epam.kiev.onlineshop.command.adminmenu;

import com.epam.kiev.onlineshop.dao.factory.DAOFactory;
import com.epam.kiev.onlineshop.entity.User;
import com.epam.kiev.onlineshop.manager.ConfigurationManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

/**
 * Created by Администратор on 1/15/15.
 */
public class AdminUsers implements IAdminMenu {
    private static final int FIRST_PAGE = 1;
    private int itemsPerPage;
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) {
        ConfigurationManager configurationManager = ConfigurationManager.getInstance();

        String sortBy = "login"; // sorting by
        String cend = "ASC";
        String searchCol = "id"; // searching col
        String searchVal = "";
        itemsPerPage =  (Integer) req.getSession().getAttribute("itemsPerPage");

        // Updates
        if (req.getParameter("admin_users_permission_id") != null) {
            updatePermission(req.getParameter("admin_users_permission"),
                        req.getParameter("admin_users_permission_id")); // Update Permissions
        }

        //reset Attributes when refresh page
        if(req.getParameter("reset") != null) {
            resetAttributes(req, sortBy, cend, searchCol, searchVal);
        }

        //Searching
        if(req.getParameter("admin_users_searchCol") != null) {
            req.getSession().setAttribute("admin_users_searchCol", req.getParameter("admin_users_searchCol"));
            req.getSession().setAttribute("admin_users_searchVal", req.getParameter("admin_users_searchVal"));
            //Set number of pages
            req.getSession().setAttribute("admin_users_number_pages", getNumberOfPages(
                    req.getParameter("admin_users_searchCol"),
                    req.getParameter("admin_users_searchVal")));
            //Set current page
            req.getSession().setAttribute("admin_users_curr_page", FIRST_PAGE);
        }
        searchCol = (String) req.getSession().getAttribute("admin_users_searchCol");
        searchVal = (String) req.getSession().getAttribute("admin_users_searchVal");

        //check for income param and set it
        getSortingParam(req);
        sortBy = (String) req.getSession().getAttribute("admin_users_sorting_sortby");
        cend = (String) req.getSession().getAttribute("admin_users_sorting_cend");

        //Setting current page
        if (req.getParameter("admin_users_curr_page") != null) {
            try {
                int currPage = Integer.parseInt(req.getParameter("admin_users_curr_page"));
                req.getSession().setAttribute("admin_users_curr_page", currPage);
            } catch (ClassCastException e) {
                Logger.getLogger(AdminUsers.class).info(e.getMessage());
            }
        }
        int page = (Integer) req.getSession().getAttribute("admin_users_curr_page");
        // The first User number in the page
        int getFirstUserInPage = (page-1) * itemsPerPage;


//        Logger.getLogger(AdminUsers.class).info("getFirstUserInPage: " + getFirstUserInPage +" CurrPage:" + req.getSession().getAttribute("admin_users_curr_page"));
//        Logger.getLogger(AdminUsers.class).info("SortBy: " + sortBy +" ascend:" + cend);

        req.getSession().setAttribute("admin_content" ,
                configurationManager.getProperty(configurationManager.ADMIN_USERS));
        req.getSession().setAttribute("admin_users" ,
                getUsers(sortBy, getFirstUserInPage, itemsPerPage, cend, searchCol, searchVal)); // Setting users for viewing
    }
    //Removing attributes
    private void resetAttributes(HttpServletRequest req, String sortBy, String cend, String searchCol, String searchVal) {
        //Set Initial sorting , if we havent needed attribute
        req.getSession().setAttribute("admin_users_sorting_sortby", sortBy);
        req.getSession().setAttribute("admin_users_sorting_cend", cend);
        //Set number of pages
        req.getSession().setAttribute("admin_users_number_pages", getNumberOfPages(searchCol, searchVal));
        //Set current page
        req.getSession().setAttribute("admin_users_curr_page", FIRST_PAGE);
        //set without search
        req.getSession().setAttribute("admin_users_searchCol", searchCol);
        req.getSession().setAttribute("admin_users_searchVal", searchVal);

    }

    private int getNumberOfPages(String searchCol, String searchVal) {
        int res = 0;
        DAOFactory factory = DAOFactory.getDAOFactory(DAOFactory.MY_SQL);
        int numOfUsers = factory.getUserDAO().getNumberOfUsers(searchCol, searchVal);
        res = numOfUsers / itemsPerPage;
        if((numOfUsers % itemsPerPage) != 0) {
            res++;
        };
        return res;
    }

    private ArrayList<User> getUsers(String sortBy, int from, int number, String cend, String searchCol, String searchVal) {
        DAOFactory factory = DAOFactory.getDAOFactory(DAOFactory.MY_SQL);
        return factory.getUserDAO().getUsers(sortBy, from, number, cend, searchCol, searchVal);
    }

    private boolean getSortingParam(HttpServletRequest req) {
        Boolean res = false;
        //Getting parameters to SORTING
        if(req.getParameter("admin_users_sorting") != null) {
//            Logger.getLogger(AdminUsers.class).info("Sort PARAM: FU!!!");
            String[] sortParam = req.getParameter("admin_users_sorting").split("_");
//            Logger.getLogger(AdminUsers.class).info("Sort PARAM: " + sortParam);
            String cend = "ASC";
            if(sortParam[0].equals("de")) {
                cend = "DESC";
            }
            req.getSession().setAttribute("admin_users_sorting_cend", cend);
            req.getSession().setAttribute("admin_users_sorting_sortby", sortParam[1]);
            res = true;
        }
        return res;
    }
     private boolean updatePermission(String propVal, String byVal) {
        DAOFactory factory = DAOFactory.getDAOFactory(DAOFactory.MY_SQL);
        return factory.getUserDAO().updateUserProperty("permission", propVal, "id", byVal);
    }
}
