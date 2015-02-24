package com.epam.kiev.onlineshop.dao.mysqldao;

import com.epam.kiev.onlineshop.dao.daointerface.IOrderDAO;
import com.epam.kiev.onlineshop.entity.Order;
import com.epam.kiev.onlineshop.dao.factory.MySQLFactory;
import com.epam.kiev.onlineshop.entity.User;
import com.epam.kiev.onlineshop.support.CommandMethods;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Администратор on 12/20/14.
 */
public class MySQLOrderDAO implements IOrderDAO {

    private static final String INSERT_ORDERS = "INSERT INTO ORDERS (USERID, GOOD, STATUS, TIMECHANGE) " +
            "VALUES(?,?,?,?)" ;
    private static final String SELECT_NUMBERS_ORDERS = "SELECT * FROM ORDERS";
    private static final String DELETE_ORDER_BY_GOODID = "DELETE FROM ORDERS WHERE GOOD = ? AND STATUS = ? ORDER BY TIMECHANGE DESC LIMIT 1";
    private static final String DELETE_ORDERS = "DELETE FROM ORDERS WHERE USERID = ? AND STATUS = ?";
    private static final String UPDATE_STATUS = "UPDATE ORDERS SET STATUS = ? , TIMECHANGE = ? WHERE ID = ?";
    private static final String UPDATE_ORDERS_STATUS = "UPDATE ORDERS SET STATUS = ? , TIMECHANGE = ?";
    private Connection connection;

    public MySQLOrderDAO () {
        connection = MySQLFactory.createConnection();
    }

    @Override
    public Order findOrder(int id) {
        return null;
    }

    @Override
    public ArrayList<Order> selectOrders(String status, String user, int from, int number) {
        ArrayList<Order> orders = new ArrayList<Order>();
        try {
            //NEED TO SET STATUS
            String sql = "SELECT * FROM ORDERS WHERE USERID= '" +user+ "' AND STATUS = '" + status + "' ORDER BY timeChange LIMIT " + from +
                    " , " + number;
            Statement statement = connection.createStatement();
            statement.execute(sql);
            ResultSet rs = statement.getResultSet();
            while (rs.next()) {
                    Order order = new Order();
                    Logger.getLogger(MySQLOrderDAO.class).info("good added");
                    order.setId(rs.getString("orders.id"));
                    order.setUser(rs.getString("orders.userId"));
                    order.setGood(rs.getString("orders.good"));
                    order.setStatus(rs.getString("orders.status"));
                    order.setTime(rs.getString("orders.timeChange"));

                    orders.add(order);
            }
        } catch (SQLException e) {
            Logger.getLogger(MySQLOrderDAO.class).info(e.getMessage() + " what a fuck?");

        } catch (Exception e) {

        }
        finally {
            MySQLFactory.putConnection(connection);
        }

        return orders;
    }

    @Override
    public ArrayList<Order> getOrders(String sortBy, int from, int count, String cend, String searchCol, String searchVal) {
        ArrayList<Order> orders = new ArrayList<Order>();
        searchVal = "%" + searchVal + "%"; //search in word
        try {
            String sql = "SELECT * FROM ORDERS WHERE " + searchCol + " LIKE '" + searchVal + "' ORDER BY " + sortBy + " " + cend + " LIMIT " + from +
                    " , " + count;
            Statement statement = connection.createStatement();
            statement.execute(sql);
            ResultSet rs = statement.getResultSet();
            while (rs.next()) {
                Order order = new Order();

                order.setId(rs.getString("orders.id"));
                order.setUser(rs.getString("orders.userid"));
                order.setGood(rs.getString("orders.good"));
                order.setStatus(rs.getString("orders.status"));
                order.setTime(rs.getString("orders.timechange"));

                orders.add(order);
            }
        } catch (SQLException e) {
            Logger.getLogger(MySQLOrderDAO.class).info(e.getMessage());

        } catch (Exception e) {

        }
        finally {
            MySQLFactory.putConnection(connection);
        }

        return orders;
    }

    @Override
    public ArrayList<Order> selectOrders(String status, String user) {
        ArrayList<Order> orders = new ArrayList<Order>();
        try {
            String sql = "SELECT * FROM ORDERS WHERE USERID = '" +user+ "' AND STATUS = '" + status + "' ORDER BY timeChange DESC";
            Statement statement = connection.createStatement();
            statement.execute(sql);
            ResultSet rs = statement.getResultSet();
            while (rs.next()) {
                Order order = new Order();

                order.setId(rs.getString("orders.id"));
                order.setUser(rs.getString("orders.userId"));
                order.setGood(rs.getString("orders.good"));
                order.setStatus(rs.getString("orders.status"));
                order.setTime(rs.getString("orders.timeChange"));

                orders.add(order);
            }
        } catch (SQLException e) {
            Logger.getLogger(MySQLOrderDAO.class).info(e.getMessage() + " what a fuck?");

        } catch (Exception e) {

        }
        finally {
            MySQLFactory.putConnection(connection);
        }

        return orders;
    }

    @Override
    public boolean updateOrderStatus(String StatusVal, String id) {
        boolean res = false;
        String currentTime = CommandMethods.getCurrentTime();
        try {
            PreparedStatement pStatement = connection.prepareStatement(UPDATE_STATUS);
            pStatement.setString(1, StatusVal);
            pStatement.setString(2, currentTime);
            pStatement.setString(3, id);
            pStatement.executeUpdate();
            res = true;
        } catch (SQLException e) {
            Logger.getLogger(MySQLOrderDAO.class).info(e.getMessage());
        } catch (Exception e) {

        }
        finally {
            MySQLFactory.putConnection(connection);
        }

        return res;
    }

    @Override
    public boolean updateOrdersStatus(String StatusVal) {
        boolean res = false;
        String currentTime = CommandMethods.getCurrentTime();
        try {
            PreparedStatement pStatement = connection.prepareStatement(UPDATE_ORDERS_STATUS);
            pStatement.setString(1, StatusVal);
            pStatement.setString(2, currentTime);
            pStatement.executeUpdate();
            res = true;
        } catch (SQLException e) {
            Logger.getLogger(MySQLOrderDAO.class).info(e.getMessage());
        } catch (Exception e) {

        }
        finally {
            MySQLFactory.putConnection(connection);
        }

        return res;
    }

    @Override
    public boolean insertOrder(Order order) {
        boolean res = false;
        try {
            PreparedStatement pStatement = connection.prepareStatement(INSERT_ORDERS);
            pStatement.setString(1, order.getUser());
            pStatement.setString(2, order.getGood());
            pStatement.setString(3, order.getStatus());
            pStatement.setString(4, order.getTime());
            pStatement.executeUpdate();
            res = true;
        } catch (SQLException e) {
            Logger.getLogger(MySQLOrderDAO.class).info(e.getMessage());
        } catch (Exception e) {
            Logger.getLogger(MySQLOrderDAO.class).info(e.getMessage());
        }
        finally {
            MySQLFactory.putConnection(connection);
        }
        return res;
    }

    @Override
    public boolean updateOrder(Order user) {
        return false;
    }

    @Override
    public boolean deleteOrderByGood(int goodId, String status) {
        boolean res = false;
        try {
            PreparedStatement pStatement = connection.prepareStatement(DELETE_ORDER_BY_GOODID);
            pStatement.setInt(1, goodId);
            pStatement.setString(2, status);
            pStatement.executeUpdate();
            res = true;
        } catch (SQLException e) {
            Logger.getLogger(MySQLOrderDAO.class).info(e.getMessage());
        } catch (Exception e) {
            Logger.getLogger(MySQLOrderDAO.class).info(e.getMessage());
        }
        finally {
            MySQLFactory.putConnection(connection);
        }
        return res;
    }

    @Override
    public boolean deleteOrders(String status, String user) {
        boolean res = false;
        try {
            Statement statement = connection.createStatement();
            String sql = "DELETE FROM ORDERS WHERE USERID = '" + user + "'  AND STATUS = '" + status + "'";
            statement.executeUpdate(sql);
            res = true;
        } catch (SQLException e) {
            Logger.getLogger(MySQLOrderDAO.class).info(e.getMessage());
        } catch (Exception e) {
            Logger.getLogger(MySQLOrderDAO.class).info(e.getMessage());
        }
        finally {
            MySQLFactory.putConnection(connection);
        }
        return res;
    }

    @Override
    public int getNumberOfOrders(String searchCol, String searchVal) {
        int res = 0;
        searchVal = "%" + searchVal + "%"; //search in word
        try {
//            PreparedStatement pStatement = connection.prepareStatement(SELECT_NUMBERS_ORDERS);
//            pStatement.executeQuery();
//            ResultSet rs = pStatement.getResultSet();
            String sql = "SELECT * FROM ORDERS WHERE " + searchCol + " LIKE '" + searchVal + "'";
            Statement statement = connection.createStatement();
            statement.execute(sql);
            ResultSet rs = statement.getResultSet();
            while (rs.next()) {
                res++;
            }
        } catch (SQLException e) {
            Logger.getLogger(MySQLUserDAO.class).info(e.getMessage());

        } catch (Exception e) {

        }
        finally {
            MySQLFactory.putConnection(connection);
        }

        return res;
    }
}
