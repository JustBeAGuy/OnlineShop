package com.epam.kiev.onlineshop.dao.factory;

import com.epam.kiev.onlineshop.dao.connection.ConnectionManager;
import com.epam.kiev.onlineshop.dao.daointerface.IGoodDAO;
import com.epam.kiev.onlineshop.dao.daointerface.IOrderDAO;
import com.epam.kiev.onlineshop.dao.daointerface.IUserDAO;
import com.epam.kiev.onlineshop.dao.daointerface.IUserDescriptionDAO;
import com.epam.kiev.onlineshop.dao.mysqldao.MySQLGoodDAO;
import com.epam.kiev.onlineshop.dao.mysqldao.MySQLOrderDAO;
import com.epam.kiev.onlineshop.dao.mysqldao.MySQLUserDAO;
import com.epam.kiev.onlineshop.dao.mysqldao.MySQLUserDescriptionDAO;

import java.sql.Connection;

/**
 * Created by Администратор on 12/20/14.
 */
public class MySQLFactory extends DAOFactory {

    public static Connection createConnection() {
        return ConnectionManager.getInstance().getConnection();
    }

    public static void putConnection(Connection con) {
        ConnectionManager conM = ConnectionManager.getInstance();
        conM.putConnection(con);
    }

    @Override
    public IUserDAO getUserDAO() {
        return new MySQLUserDAO();
    }

    @Override
    public IOrderDAO getOrderDAO() {
        return new MySQLOrderDAO();
    }

    @Override
    public IGoodDAO getGoodDAO() {
        return new MySQLGoodDAO();
    }

    @Override
    public IUserDescriptionDAO getUserDescriptionDAO() {
        return new MySQLUserDescriptionDAO();
    }
}
