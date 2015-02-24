package com.epam.kiev.onlineshop.dao.mysqldao;

import com.epam.kiev.onlineshop.dao.daointerface.IUserDAO;
import com.epam.kiev.onlineshop.entity.User;
import com.epam.kiev.onlineshop.dao.factory.MySQLFactory;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by Администратор on 12/20/14.
 */
public class MySQLUserDAO implements IUserDAO {

    private static final String SELECT_NUMBERS_USERS = "SELECT * FROM USERS WHERE ? LIKE ?";
    private static final String UPDATE_STATEMENT_USER = "UPDATE USERS SET PASSWORD = ?, PERMISSION = ?, EMAIL = ? WHERE ID = ?";
    private static final String SELECT_STATEMENT_LOGIN = "SELECT * FROM USERS WHERE LOGIN = ?";
//    private final String SELECT_STATEMENT_SORTED_ASC = "SELECT * FROM USERS ORDER BY ? ASC LIMIT ? , ?";
//    private final String SELECT_STATEMENT_SORTED_DESC = "SELECT * FROM USERS ORDER BY ? DESC LIMIT ? , ?";
    private static final String INSERT_STATEMENT_USER = "INSERT INTO USERS (LOGIN,PASSWORD,EMAIL) VALUES(?,?,?)";
    private Connection connection;

    public MySQLUserDAO () {
        connection = MySQLFactory.createConnection();
    }

    @Override
    public boolean insertUser(User user) {
        boolean res = false;
        try {
            PreparedStatement pStatement = connection.prepareStatement(INSERT_STATEMENT_USER,
                                        Statement.RETURN_GENERATED_KEYS);
            pStatement.setString(1, user.getLogin());
            pStatement.setString(2, user.getPassword());
            pStatement.setString(3, user.getEmail());
            pStatement.executeUpdate();
            //Getting generatedKeys
            ResultSet generatedKeys = pStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                user.setId(generatedKeys.getString(1));
            }
            res = true;
        } catch (SQLException e) {
            Logger.getLogger(MySQLUserDAO.class).info(e.getMessage());
        } catch (Exception e) {

        }
        finally {
            MySQLFactory.putConnection(connection);
        }

        return res;
    }

    @Override
    public User findUser(String login) {
        User user = new User();

        try {
            PreparedStatement pStatement = connection.prepareStatement(SELECT_STATEMENT_LOGIN);
            pStatement.setString(1, login);
            pStatement.executeQuery();
            ResultSet rs = pStatement.getResultSet();
            while (rs.next()) {
                user.setId(rs.getString("users.id"));
                user.setLogin(rs.getString("users.login"));
                user.setPassword(rs.getString("users.password"));
                user.setEmail(rs.getString("users.email"));
                user.setPermission(rs.getInt("users.permission"));
            }
        } catch (SQLException e) {
            Logger.getLogger(MySQLUserDAO.class).info(e.getMessage());

        } catch (Exception e) {

        }
        finally {
            MySQLFactory.putConnection(connection);
        }

        return user;
    }

    @Override
    public boolean updateUser(User user) {
        boolean res = false;
        try {
            PreparedStatement pStatement = connection.prepareStatement(UPDATE_STATEMENT_USER);
            pStatement.setString(1, user.getPassword());
            pStatement.setInt(2, user.getPermission());
            pStatement.setString(3, user.getEmail());
            pStatement.setString(4, user.getId());
            pStatement.executeUpdate();
            res = true;
        } catch (SQLException e) {
            Logger.getLogger(MySQLUserDAO.class).info(e.getMessage());
        } catch (Exception e) {

        }
        finally {
            MySQLFactory.putConnection(connection);
        }
        return res;
    }

    @Override
    public boolean updateUserProperty(String prop, String propVal, String byWhat, String byVal) {
        boolean res = false;
        try {
            Statement statement = connection.createStatement();
            String sql = "UPDATE USERS SET " + prop + "=" + propVal + " WHERE " + byWhat + " = " + byVal;
            statement.executeUpdate(sql);
            res = true;
        } catch (SQLException e) {
            Logger.getLogger(MySQLUserDAO.class).info(e.getMessage());
        } catch (Exception e) {

        }
        finally {
            MySQLFactory.putConnection(connection);
        }

        return res;
    }

    @Override
    public boolean deleteUser(User user) {
        return false;
    }

    @Override
    public ArrayList<User> getUsers(String sortBy, int from, int count, String cend, String searchCol, String searchVal) {
        ArrayList<User> users = new ArrayList<User>();
        searchVal = "%" + searchVal + "%"; //search in word
        try {
            String sql = "SELECT * FROM USERS WHERE " + searchCol + " LIKE '" + searchVal + "' ORDER BY " + sortBy + " " + cend + " LIMIT " + from +
                    " , " + count;
            Statement statement = connection.createStatement();
            statement.execute(sql);
            ResultSet rs = statement.getResultSet();
            while (rs.next()) {
                User user = new User();

                user.setId(rs.getString("users.id"));
                user.setLogin(rs.getString("users.login"));
                user.setPassword(rs.getString("users.password"));
                user.setEmail(rs.getString("users.email"));
                user.setPermission(rs.getInt("users.permission"));

                users.add(user);
            }
        } catch (SQLException e) {
            Logger.getLogger(MySQLUserDAO.class).info(e.getMessage());

        } catch (Exception e) {

        }
        finally {
            MySQLFactory.putConnection(connection);
        }

        return users;
    }

    @Override
    public int getNumberOfUsers(String searchCol, String searchVal) {
        int res = 0;
        searchVal = "%" + searchVal + "%"; //search in word
        try {
//            PreparedStatement pStatement = connection.prepareStatement(SELECT_NUMBERS_USERS);
//            pStatement.setString(1, searchCol);
//            pStatement.setString(2, searchVal);
//            pStatement.executeQuery();
//            ResultSet rs = pStatement.getResultSet();
            String sql = "SELECT * FROM USERS WHERE " + searchCol + " LIKE '" + searchVal + "'";
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
