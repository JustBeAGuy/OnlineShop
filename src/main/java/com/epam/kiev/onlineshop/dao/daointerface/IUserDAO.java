package com.epam.kiev.onlineshop.dao.daointerface;

import com.epam.kiev.onlineshop.entity.User;

import java.util.ArrayList;

/**
 * Created by Администратор on 12/20/14.
 */
public interface IUserDAO {
    boolean insertUser(User user);
    User findUser(String login);
    boolean updateUser(User user);
    boolean updateUserProperty(String prop, String propVal, String byWhat, String byVal);
    boolean deleteUser(User user);
    ArrayList<User> getUsers(String sortBy, int from, int count, String cend, String searchCol, String searchVal);
    int getNumberOfUsers(String searchCol, String searchVal);
}
