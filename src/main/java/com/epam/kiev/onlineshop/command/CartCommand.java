package com.epam.kiev.onlineshop.command;

import com.epam.kiev.onlineshop.dao.factory.DAOFactory;
import com.epam.kiev.onlineshop.entity.Good;
import com.epam.kiev.onlineshop.entity.Order;
import com.epam.kiev.onlineshop.entity.User;
import com.epam.kiev.onlineshop.manager.ConfigurationManager;
import com.epam.kiev.onlineshop.manager.MessageManager;
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
 * Created by Администратор on 1/12/15.
 */
public class CartCommand implements ICommand {
    private static final int FIRST_PAGE = 1;
    private int itemsPerPage;
    private static final String STATUS_CART = "0";
    private static final String STATUS_UNPAID = "1";
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        ConfigurationManager configurationManager = ConfigurationManager.getInstance();
        itemsPerPage =  (Integer) req.getSession().getAttribute("itemsPerPage");

        //getting User
        User user = (User) req.getSession().getAttribute("account");

        //Set Status of cart orders to unPaid
        if(req.getParameter("cart_pay") != null) {
            updateOrdersStatus(STATUS_UNPAID);
            Message.notify(req, MessageManager.ORDER_SUCC, MessageManager.TYPE_SUCC);
        }

        //Remove Good if income parameter
        if(req.getParameter("cart_good_remove") != null) {
            removeGoodFromTable(req.getParameter("cart_good_remove"), STATUS_CART);
            //Notify Remove Successful
            Message.notify(req, MessageManager.REMOVED_SUCC, MessageManager.TYPE_SUCC);
        }

        //Cart Clear
        if(req.getParameter("cart_clear") != null) {
            cartClear(STATUS_CART,user.getId());
            //Notify Remove Successful
            Message.notify(req, MessageManager.REMOVED_SUCC, MessageManager.TYPE_SUCC);
        }

        //Get and set goods from account cart
        req.getSession().setAttribute("cart", getCartGoods(STATUS_CART, user.getId()));
        //Calculate and set amountToPay
        req.getSession().setAttribute("amountToPay", amountToPay(STATUS_CART, user.getId()));

        req.getSession().setAttribute("body",configurationManager.getProperty(configurationManager.CART)); // Go to CART Page
        return configurationManager.getProperty(configurationManager.MAIN);
    }

    private void cartClear(String status, String userId) {
        DAOFactory factory = DAOFactory.getDAOFactory(DAOFactory.MY_SQL);
        factory.getOrderDAO().deleteOrders(status, userId);
    }

    private int amountToPay(String status, String user) {
        int res = 0;
        DAOFactory factory = DAOFactory.getDAOFactory(DAOFactory.MY_SQL);
        ArrayList<Order> orders = factory.getOrderDAO().selectOrders(status, user);
        for(Order order : orders) {
            Good good = getGoodInCart(order.getGood());
            if(good != null) {
                res += Integer.parseInt(good.getPrice());
            }
        }
        return res;
    }

    private ArrayList<Good> getCartGoods(String status, String user) {
        ArrayList<Good> goods = new ArrayList<Good>();
        DAOFactory factory = DAOFactory.getDAOFactory(DAOFactory.MY_SQL);
        ArrayList<Order> orders = factory.getOrderDAO().selectOrders(status, user);
        for(Order order: orders) {
            Good good = getGoodInCart(order.getGood());
            if(good != null) {
                ImageOperations.addPathToGoodImg(good);
                goods.add(good);
            }
        }
        return goods;
    }

    private Good getGoodInCart(String goodId) {
        DAOFactory factory = DAOFactory.getDAOFactory(DAOFactory.MY_SQL);
        return factory.getGoodDAO().findGood(goodId);
    }

    private void removeGoodFromTable(String goodId, String status) {
        DAOFactory factory = DAOFactory.getDAOFactory(DAOFactory.MY_SQL);
        factory.getOrderDAO().deleteOrderByGood(Integer.parseInt(goodId), status);
    }

    private void updateOrdersStatus(String status) {
        DAOFactory factory = DAOFactory.getDAOFactory(DAOFactory.MY_SQL);
        factory.getOrderDAO().updateOrdersStatus(status);
    }
}
