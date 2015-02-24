package com.epam.kiev.onlineshop.dao.daointerface;

import com.epam.kiev.onlineshop.entity.UserDescription;

/**
 * Created by Администратор on 12/20/14.
 */
public interface IUserDescriptionDAO {
    UserDescription findUserDescription(String id);
    boolean insertUserDescription(UserDescription userDescription);
    boolean updateUserDescription(UserDescription userDescription);
    boolean deleteUserDescription(UserDescription userDescription);
}
