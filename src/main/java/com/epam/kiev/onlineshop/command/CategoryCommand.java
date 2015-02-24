package com.epam.kiev.onlineshop.command;

import com.epam.kiev.onlineshop.dao.factory.DAOFactory;
import com.epam.kiev.onlineshop.entity.Good;
import com.epam.kiev.onlineshop.entity.Order;
import com.epam.kiev.onlineshop.entity.User;
import com.epam.kiev.onlineshop.manager.ConfigurationManager;
import com.epam.kiev.onlineshop.manager.MessageManager;
import com.epam.kiev.onlineshop.support.CommandMethods;
import com.epam.kiev.onlineshop.support.ImageOperations;
import com.epam.kiev.onlineshop.support.Message;
import com.epam.kiev.onlineshop.support.Notify;
import org.apache.log4j.Logger;
import sun.util.calendar.CalendarDate;
import sun.util.calendar.Gregorian;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by Администратор on 1/2/15.
 */
public class CategoryCommand implements ICommand {
    private static final String FIRST_PARAM = "2";  //"2"// name of the First parameter after command means category
    private static final String SECOND_PARAM = "3"; //"3"// name of the Second parameter after command, means concrete product
    private static final String GOOD_BUY = "good_buy"; // Means Add to Cart
    private static final String GOOD_BUY_NUMBER = "good_buy_number"; //number of goods
    private int itemsPerPage = 2;
    private static final int FIRST_PAGE = 1;
    private static final String STATUS_CART = "0"; // Orders that not paid
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        ConfigurationManager configurationManager = ConfigurationManager.getInstance();
        String body = configurationManager.INDEX;

        //START Catalog only
        if((req.getParameter(FIRST_PARAM) != null) && (req.getParameter(SECOND_PARAM) == null)) {

            String sortBy = "id"; // sorting by
            String cend = "ASC";
            String searchCol = "id"; // searching col
            String searchVal = "";
            itemsPerPage =  (Integer) req.getSession().getAttribute("itemsPerPage");

            //reset Attributes when refresh page
            if(req.getParameter("reset") != null) {
                resetSearchAttributes(req, sortBy, cend, searchCol, searchVal);
            }

            //Searching
            if(req.getParameter("category_searchCol") != null) {
                req.getSession().setAttribute("category_searchCol", req.getParameter("category_searchCol"));
                req.getSession().setAttribute("category_searchVal", req.getParameter("category_searchVal"));
                //Set number of pages
                req.getSession().setAttribute("category_number_pages", getNumberOfPages(
                        req.getParameter("category_searchCol"),
                        req.getParameter("category_searchVal")));
                //Set current page
                req.getSession().setAttribute("category_curr_page", FIRST_PAGE);
            }
            searchCol = (String) req.getSession().getAttribute("category_searchCol");
            searchVal = (String) req.getSession().getAttribute("category_searchVal");

            //check for income param and set it
            getSortingParam(req);
            sortBy = (String) req.getSession().getAttribute("category_sorting_sortby");
            cend = (String) req.getSession().getAttribute("category_sorting_cend");

            //Setting current page
            if (req.getParameter("category_curr_page") != null) {
                try {
                    int currPage = Integer.parseInt(req.getParameter("category_curr_page"));
                    req.getSession().setAttribute("category_curr_page", currPage);
                } catch (ClassCastException e) {
                    Logger.getLogger(CategoryCommand.class).info(e.getMessage());
                }
            }
            int page = (Integer) req.getSession().getAttribute("category_curr_page");
            // The first Good number in the page
            int getFirstGoodInPage = (page-1) * itemsPerPage;

            body = configurationManager.CATEGORY;
            ArrayList<Good> goods = getCatalog(req.getParameter(FIRST_PARAM), sortBy, getFirstGoodInPage, itemsPerPage, cend, searchCol, searchVal);
            for(Good good : goods) {
                ImageOperations.addPathToGoodImg(good); //Setting Real Path to IMG
            }
            req.getSession().setAttribute("category", goods);
        }
        //END Catalog only

        //START Good only
        if(req.getParameter(SECOND_PARAM) != null) {
            body = configurationManager.GOOD;
            Good good = getGood(req.getParameter(SECOND_PARAM));
            ImageOperations.addPathToGoodImg(good); //Setting Real Path to IMG
            req.getSession().setAttribute("good", good);
        }
        //END Good only

        //START Add To Cart
        if(req.getParameter(GOOD_BUY) != null) {
            if(req.getParameter(GOOD_BUY_NUMBER) != null) {
                ArrayList<Good> cart = new ArrayList<Good>();
                Good good = getGood(req.getParameter(GOOD_BUY));
                //getting User and if not Loged in, throw Message
                User user = (User) req.getSession().getAttribute("account");
                if(user != null) {
                    try {
                        int numberOfGoods = Integer.parseInt(req.getParameter(GOOD_BUY_NUMBER));
                        for (int i = 1; i <= numberOfGoods; i++) {
                            cart.add(good);
                        }
                        addGoodsToAccount(user, cart);
                        //setting Notify, that Good Added To Cart
                        Message.notify(req, MessageManager.GOOD_ADDED_CART, MessageManager.TYPE_SUCC);

                    } catch (ClassCastException e) {
                        Logger.getLogger(CategoryCommand.class).info(e);
                    }
                } else {
                    //setting Notify, that user must LogIn
                    Message.notify(req, MessageManager.ACCESS_DENIED, MessageManager.TYPE_ERROR);

                }
            }
        }
        //END Add To Cart

        req.getSession().setAttribute("body",configurationManager.getProperty(body));
        return configurationManager.getProperty(configurationManager.MAIN);
    }

    private Good getGood(String goodId) {
        DAOFactory factory = DAOFactory.getDAOFactory(DAOFactory.MY_SQL);
        return factory.getGoodDAO().findGood(goodId);
    }

    private ArrayList<Good> getCatalog(String category, String sortBy, int from, int number, String cend, String searchCol, String searchVal) {
        DAOFactory factory = DAOFactory.getDAOFactory(DAOFactory.MY_SQL);
        return factory.getGoodDAO().getCategory(category, sortBy, from, number, cend, searchCol, searchVal);
    }

    private boolean getSortingParam(HttpServletRequest req) {
        Boolean res = false;
        //Getting parameters to SORTING
        if(req.getParameter("category_sorting") != null) {
            String[] sortParam = req.getParameter("category_sorting").split("_");
            String cend = "ASC";
            if(sortParam[0].equals("de")) {
                cend = "DESC";
            }
            req.getSession().setAttribute("category_sorting_cend", cend);
            req.getSession().setAttribute("category_sorting_sortby", sortParam[1]);
            res = true;
        }
        return res;
    }

    private int getNumberOfPages(String searchCol, String searchVal) {
        int res = 0;
        DAOFactory factory = DAOFactory.getDAOFactory(DAOFactory.MY_SQL);
        int numOfGoods = factory.getGoodDAO().getNumberOfGoods(searchCol, searchVal);
        res = numOfGoods / itemsPerPage;
        if((numOfGoods % itemsPerPage) != 0) {
            res++;
        };
        return res;
    }

    //Removing attributes
    private void resetSearchAttributes(HttpServletRequest req, String sortBy, String cend, String searchCol, String searchVal) {
        //Set Initial sorting , if we havent needed attribute
        req.getSession().setAttribute("category_sorting_sortby", sortBy);
        req.getSession().setAttribute("category_sorting_cend", cend);
        //Set number of pages
        req.getSession().setAttribute("category_number_pages", getNumberOfPages(searchCol, searchVal));
        //Set current page
        req.getSession().setAttribute("category_curr_page", FIRST_PAGE);
        //set without search
        req.getSession().setAttribute("category_searchCol", searchCol);
        req.getSession().setAttribute("category_searchVal", searchVal);
    }

    //Adding goods to account cart DB
    private void addGoodsToAccount(User user, ArrayList<Good> cart) {
        for(Good good: cart) {
            Order order = new Order();

            order.setUser(user.getId());
            order.setGood(good.getId());
            order.setStatus(STATUS_CART); // setting, that good in a cart
            //setting Local Time , need to change
            order.setTime(CommandMethods.getCurrentTime());

            insertOrder(order);
        }
    }

    //Inserting Order Into Table
    private void insertOrder(Order order) {
        DAOFactory factory = DAOFactory.getDAOFactory(DAOFactory.MY_SQL);
        factory.getOrderDAO().insertOrder(order);
    }


}
