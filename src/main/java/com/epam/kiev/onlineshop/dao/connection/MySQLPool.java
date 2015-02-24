package com.epam.kiev.onlineshop.dao.connection;

import com.epam.kiev.onlineshop.manager.ConfigurationManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MySQLPool {

    private static Queue<Connection> pool = new ConcurrentLinkedQueue();
    private static final ConfigurationManager conf = ConfigurationManager.getInstance();
    private static final int MAX_CONNECTIONS = Integer.parseInt(conf.getProperty(conf.MAX_POOL_SIZE));
    private static final String DB = conf.getProperty(conf.URL);

    private static AtomicInteger allConnections = new AtomicInteger(0);

    private MySQLPool() {
        connectDriver();
        addConnections(Integer.parseInt(conf.getProperty(conf.MIN_POOL_SIZE)));
    }

    private static class SingletonHolder {
        private static final MySQLPool INSTANCE = new MySQLPool();
    }

    public static final MySQLPool getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private void connectDriver() {
        try {
            Class.forName(conf.getProperty(conf.DRIVER));
        } catch (ClassNotFoundException e) {
            Logger.getLogger(MySQLPool.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    private void addConnections(int numConnections) {
        for(int i = 0; i < numConnections; i++) {
            addConnection();
        }
    }

    private Connection pollConnection() {
        Connection res = null;
        try {
            res = pool.remove();
        } catch (NoSuchElementException e) {
            if(allConnections.get() <= MAX_CONNECTIONS) {
                addConnection();
            } else {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e1) {
                    //    e1.printStackTrace();
                }
            }
        }
        return res;
    }

    private void addConnection() {
            if (allConnections.get() <= MAX_CONNECTIONS) {
                try {
                    pool.add(DriverManager.getConnection(DB));
                    allConnections.incrementAndGet();
                } catch (SQLException e) {
                    Logger.getLogger(MySQLPool.class.getName()).log(Level.SEVERE, null, e);
                }
            }
    }

    public Connection getConnection(){
        Connection res = null;
        do {
            res = pollConnection();
        } while (res == null);
        org.apache.log4j.Logger.getLogger(MySQLPool.class).info("connection is got and available connections : " + pool.size());
        return res;
    }

    public void putConnection(Connection conn){
        pool.add(conn);
        org.apache.log4j.Logger.getLogger(MySQLPool.class).info("connection is putted back and available connections : " + pool.size());
    }
}
