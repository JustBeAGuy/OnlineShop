package com.epam.kiev.onlineshop.dao.factory;

import com.epam.kiev.onlineshop.dao.connection.ConnectionManager;
import com.epam.kiev.onlineshop.dao.daointerface.IGoodDAO;
import com.epam.kiev.onlineshop.dao.daointerface.IOrderDAO;
import com.epam.kiev.onlineshop.dao.daointerface.IUserDAO;
import com.epam.kiev.onlineshop.dao.daointerface.IUserDescriptionDAO;

import java.sql.Connection;

/**
 * Created by Администратор on 12/20/14.
 */
public abstract class DAOFactory {

    public static final int MY_SQL = 1;

    public abstract IUserDAO getUserDAO();
    public abstract IOrderDAO getOrderDAO();
    public abstract IGoodDAO getGoodDAO();
    public abstract IUserDescriptionDAO getUserDescriptionDAO();

    public static DAOFactory getDAOFactory(
            int whichFactory) {

        switch (whichFactory) {
            case MY_SQL:
                return new MySQLFactory();
            default           :
                return null;
        }
    }

}
