package com.epam.kiev.onlineshop.dao.daointerface;

import com.epam.kiev.onlineshop.entity.Order;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Администратор on 12/20/14.
 */
public interface IOrderDAO {
    Order findOrder(int id);
    ArrayList<Order> selectOrders(String status, String user, int from, int number);
    ArrayList<Order> getOrders(String sortBy, int from, int count, String cend, String searchCol, String searchVal);
    ArrayList<Order> selectOrders(String status, String user);
    boolean updateOrderStatus(String StatusVal, String id);
    boolean updateOrdersStatus(String StatusVal);
    boolean insertOrder(Order user);
    boolean updateOrder(Order user);
    boolean deleteOrderByGood(int goodId, String status);
    boolean deleteOrders(String status, String user);
    int getNumberOfOrders(String searchCol, String searchVal);
}
