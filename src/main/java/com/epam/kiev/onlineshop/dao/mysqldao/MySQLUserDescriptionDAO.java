package com.epam.kiev.onlineshop.dao.mysqldao;

import com.epam.kiev.onlineshop.dao.daointerface.IUserDescriptionDAO;
import com.epam.kiev.onlineshop.entity.UserDescription;
import com.epam.kiev.onlineshop.dao.factory.MySQLFactory;
import org.apache.log4j.Logger;

import java.sql.*;

/**
 * Created by Администратор on 12/20/14.
 */
public class MySQLUserDescriptionDAO implements IUserDescriptionDAO {

    private static final String INSERT_STATEMENT_USER_DESCRIPTION = "INSERT INTO USERDESCRIPTIONS (ID,NAME,SURNAME," +
                                                         " PHONE, CITY, STREET, HOUSE, ZIP) VALUES(?,?,?,?,?,?,?,?)";
    private static final String SELECT_STATEMENT_FIND = "SELECT * FROM USERDESCRIPTIONS WHERE ID = ?";
    private static final String UPDATE_STATEMENT_USER_DESCRIPTION = "UPDATE USERDESCRIPTIONS SET NAME = ? , SURNAME = ? , " +
            "PHONE = ? , CITY = ? , STREET = ?, HOUSE = ?, ZIP = ? WHERE ID = ? ";
    private Connection connection;

    public MySQLUserDescriptionDAO () {
        connection = MySQLFactory.createConnection();
    }

    @Override
    public UserDescription findUserDescription(String id) {
        UserDescription userDescription = new UserDescription();

        try {
            PreparedStatement pStatement = connection.prepareStatement(SELECT_STATEMENT_FIND);
            pStatement.setString(1, id);
            pStatement.executeQuery();
            ResultSet rs = pStatement.getResultSet();
            while (rs.next()) {
                userDescription.setId(rs.getString("userdescriptions.id"));
                userDescription.setName(rs.getString("userdescriptions.name"));
                userDescription.setSurname(rs.getString("userdescriptions.surname"));
                userDescription.setPhone(rs.getString("userdescriptions.phone"));
                userDescription.setCity(rs.getString("userdescriptions.city"));
                userDescription.setStreet(rs.getString("userdescriptions.street"));
                userDescription.setHouse(rs.getString("userdescriptions.house"));
                userDescription.setZip(rs.getString("userdescriptions.zip"));
            }
        } catch (SQLException e) {
            Logger.getLogger(MySQLUserDescriptionDAO.class).info(e.getMessage());

        } catch (Exception e) {

        }
        finally {
            MySQLFactory.putConnection(connection);
        }

        return userDescription;
    }

    @Override
    public boolean insertUserDescription(UserDescription userDescription) {
        boolean res = false;
        try {
            PreparedStatement pStatement = connection.prepareStatement(INSERT_STATEMENT_USER_DESCRIPTION);
            pStatement.setString(1, userDescription.getId());
            pStatement.setString(2, userDescription.getName());
            pStatement.setString(3, userDescription.getSurname());
            pStatement.setString(4, userDescription.getPhone());
            pStatement.setString(5, userDescription.getCity());
            pStatement.setString(6, userDescription.getStreet());
            pStatement.setString(7, userDescription.getHouse());
            pStatement.setString(8, userDescription.getZip());
            pStatement.executeUpdate();
            res = true;
        } catch (SQLException e) {
            Logger.getLogger(MySQLUserDescriptionDAO.class).info(e.getMessage());
        } catch (Exception e) {
        }
        finally {
            MySQLFactory.putConnection(connection);
        }
        return res;
    }

    @Override
    public boolean updateUserDescription(UserDescription userDescription) {
        boolean res = false;
        try {
            PreparedStatement pStatement = connection.prepareStatement(UPDATE_STATEMENT_USER_DESCRIPTION);
            pStatement.setString(1, userDescription.getName());
            pStatement.setString(2, userDescription.getSurname());
            pStatement.setString(3, userDescription.getPhone());
            pStatement.setString(4, userDescription.getCity());
            pStatement.setString(5, userDescription.getStreet());
            pStatement.setString(6, userDescription.getHouse());
            pStatement.setInt(7, Integer.parseInt(userDescription.getZip()));
            pStatement.setString(8, userDescription.getId());
            pStatement.executeUpdate();
            res = true;
        } catch (SQLException e) {
            Logger.getLogger(MySQLUserDescriptionDAO.class).info(e.getMessage());

        } catch (Exception e) {

        }
        finally {
            MySQLFactory.putConnection(connection);
        }
        return res;
    }

    @Override
    public boolean deleteUserDescription(UserDescription userDescription) {
        return false;
    }
}
