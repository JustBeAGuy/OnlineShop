package com.epam.kiev.onlineshop.dao.connection;

import java.sql.Connection;
/**
 * Created by Администратор on 12/19/14.
 */
public class ConnectionManager {
    private static final MySQLPool pool = MySQLPool.getInstance();

    private ConnectionManager(){
    }

    private static class SingletonHolder {
        private static final ConnectionManager INSTANCE = new ConnectionManager();
    }

    public static final ConnectionManager getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public Connection getConnection(){
        return pool.getConnection();
    }



    public void putConnection(Connection connetion){
        pool.putConnection(connetion);
    }

}
