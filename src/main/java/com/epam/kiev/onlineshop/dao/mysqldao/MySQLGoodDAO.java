package com.epam.kiev.onlineshop.dao.mysqldao;

import com.epam.kiev.onlineshop.dao.daointerface.IGoodDAO;
import com.epam.kiev.onlineshop.entity.Good;
import com.epam.kiev.onlineshop.dao.factory.MySQLFactory;
import com.epam.kiev.onlineshop.entity.User;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Администратор on 12/20/14.
 */
public class MySQLGoodDAO implements IGoodDAO {

    private static final String UPDATE_STATEMENT_GOOD = "UPDATE GOODS SET CATEGORY = ? , NAME = ? , PRICE = ? , " +
            "DESCRIPTION = ? , AVAILABILITY = ? , IMG = ? WHERE ID = ? " ;
    private static final String SELECT_NUMBERS_GOODS = "SELECT * FROM GOODS";
    private Connection connection;
    private static final String SELECT_STATEMENT_CATEGORY = "SELECT * FROM GOODS WHERE CATEGORY = ?";
    private static final String SELECT_STATEMENT_ID = "SELECT * FROM GOODS WHERE ID = ?";
    private static final String INSERT_STATEMENT_GOOD = "INSERT INTO GOODS (CATEGORY,NAME, PRICE, " +
                                                "DESCRIPTION, AVAILABILITY) VALUES(?,?,?,?,?)";
    private static final String DELETE_GOOD = "DELETE FROM GOODS WHERE ID = ?";

    public MySQLGoodDAO () {
        connection = MySQLFactory.createConnection();
    }

    @Override
    public Good findGood(String id) {

        Good good = null;

        try {
            PreparedStatement pStatement = connection.prepareStatement(SELECT_STATEMENT_ID);
            pStatement.setString(1, id);
            pStatement.executeQuery();
            ResultSet rs = pStatement.getResultSet();

            while(rs.next()) {
                good = new Good();
                good.setId(rs.getString("goods.id"));
                good.setPrice(rs.getString("goods.price"));
                good.setName(rs.getString("goods.name"));
                good.setDescription(rs.getString("goods.description"));
                good.setAvailability(rs.getString("goods.availability"));
                good.setImg(rs.getString("goods.img"));
                good.setCategory(rs.getString("goods.category"));
            }
        } catch (SQLException e) {
            Logger.getLogger(MySQLGoodDAO.class).info(e.getMessage());
        } catch (Exception e) {

        }
        finally {
            MySQLFactory.putConnection(connection);
        }

        return good;
    }

    @Override
    public ArrayList<Good> selectGoodsCategory(String category) {

        ArrayList<Good> goods = new ArrayList();

        try {
            PreparedStatement pStatement = connection.prepareStatement(SELECT_STATEMENT_CATEGORY);
            pStatement.setString(1, category);
            pStatement.executeQuery();
            ResultSet rs = pStatement.getResultSet();

            while(rs.next()) {
                Good good = new Good();

                good.setId(rs.getString("goods.id"));
                good.setPrice(rs.getString("goods.price"));
                good.setName(rs.getString("goods.name"));
                good.setImg(rs.getString("goods.img"));
                good.setCategory(rs.getString("goods.category"));

                goods.add(good);
            }
        } catch (SQLException e) {
            Logger.getLogger(MySQLGoodDAO.class).info(e.getMessage());
        } catch (Exception e) {

        }
        finally {
            MySQLFactory.putConnection(connection);
        }

        return goods;
    }

    @Override
    public Collection<Good> selectGoodsName(String name) {
        return null;
    }

    @Override
    public Collection<Good> selectGoodsPrice(int min, int max) {
        return null;
    }

    @Override
    public void insertGood(Good good) {
        try {
            PreparedStatement pStatement = connection.prepareStatement(INSERT_STATEMENT_GOOD,
                    Statement.RETURN_GENERATED_KEYS);
            pStatement.setString(1, good.getCategory());
            pStatement.setString(2, good.getName());
            pStatement.setString(3, good.getPrice());
            pStatement.setString(4, good.getDescription());
            pStatement.setString(5, good.getAvailability());
            pStatement.executeUpdate();
            //Getting generatedKeys
            ResultSet generatedKeys = pStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                good.setId(String.valueOf(generatedKeys.getLong(1)));
            }
        } catch (SQLException e) {
            Logger.getLogger(MySQLGoodDAO.class).info(e.getMessage());
            good = null;
        } catch (Exception e) {
            good = null;
        }
        finally {
            MySQLFactory.putConnection(connection);
        }
    }

    @Override
    public ArrayList<Good> getGoods(String sortBy, int from, int count, String cend, String searchCol, String searchVal) {
        ArrayList<Good> goods = new ArrayList<Good>();
        searchVal = "%" + searchVal + "%"; //search in word
        try {
            String sql = "SELECT * FROM GOODS WHERE " + searchCol + " LIKE '" + searchVal + "' ORDER BY " + sortBy + " " + cend + " LIMIT " + from +
                    " , " + count;
            Statement statement = connection.createStatement();
            statement.execute(sql);
            ResultSet rs = statement.getResultSet();
            while (rs.next()) {
                Good good = new Good();

                good.setId(rs.getString("goods.id"));
                good.setCategory(rs.getString("goods.category"));
                good.setName(rs.getString("goods.name"));
                good.setPrice(rs.getString("goods.price"));
                good.setDescription(rs.getString("goods.description"));
                good.setAvailability(rs.getString("goods.availability"));
                good.setImg(rs.getString("goods.img"));

                goods.add(good);
            }
        } catch (SQLException e) {
            Logger.getLogger(MySQLGoodDAO.class).info(e.getMessage());

        } catch (Exception e) {

        }
        finally {
            MySQLFactory.putConnection(connection);
        }

        return goods;
    }

    @Override
    public ArrayList<Good> getCategory(String category, String sortBy, int from, int count, String cend, String searchCol, String searchVal) {
        ArrayList<Good> goods = new ArrayList<Good>();
        searchVal = "%" + searchVal + "%"; //search in word
        try {
            String sql = "SELECT * FROM GOODS WHERE CATEGORY = '" + category + "' AND " + searchCol + " LIKE '" + searchVal + "' ORDER BY " + sortBy + " " + cend + " LIMIT " + from +
                    " , " + count;
            Statement statement = connection.createStatement();
            statement.execute(sql);
            ResultSet rs = statement.getResultSet();
            while (rs.next()) {
                Good good = new Good();

                good.setId(rs.getString("goods.id"));
                good.setCategory(rs.getString("goods.category"));
                good.setName(rs.getString("goods.name"));
                good.setPrice(rs.getString("goods.price"));
                good.setDescription(rs.getString("goods.description"));
                good.setAvailability(rs.getString("goods.availability"));
                good.setImg(rs.getString("goods.img"));

                goods.add(good);
            }
        } catch (SQLException e) {
            Logger.getLogger(MySQLGoodDAO.class).info(e.getMessage());

        } catch (Exception e) {

        }
        finally {
            MySQLFactory.putConnection(connection);
        }

        return goods;
    }

    @Override
    public boolean updateGood(Good good) {
        boolean res = false;
        try {
            PreparedStatement pStatement = connection.prepareStatement(UPDATE_STATEMENT_GOOD);
            pStatement.setString(1, good.getCategory());
            pStatement.setString(2, good.getName());
            pStatement.setString(3, good.getPrice());
            pStatement.setString(4, good.getDescription());
            pStatement.setString(5, good.getAvailability());
            pStatement.setString(6, good.getImg());
            pStatement.setString(7, good.getId());
            pStatement.executeUpdate();
            res = true;
        } catch (SQLException e) {
            Logger.getLogger(MySQLGoodDAO.class).info(e.getMessage());

        } catch (Exception e) {

        }
        finally {
            MySQLFactory.putConnection(connection);
        }
        return res;
    }

    @Override
    public boolean deleteGood(int goodId) {
        boolean res = false;
        try {
            PreparedStatement pStatement = connection.prepareStatement(DELETE_GOOD);
            pStatement.setInt(1, goodId);
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
    public int getNumberOfGoods(String searchCol, String searchVal) {
        int res = 0;
        searchVal = "%" + searchVal + "%"; //search in word
        try {
            String sql = "SELECT * FROM GOODS WHERE " + searchCol + " LIKE '" + searchVal + "'";
            Statement statement = connection.createStatement();
            statement.execute(sql);
            ResultSet rs = statement.getResultSet();
            while (rs.next()) {
                res++;
            }
        } catch (SQLException e) {
            Logger.getLogger(MySQLGoodDAO.class).info(e.getMessage());

        } catch (Exception e) {

        }
        finally {
            MySQLFactory.putConnection(connection);
        }

        return res;
    }
}
