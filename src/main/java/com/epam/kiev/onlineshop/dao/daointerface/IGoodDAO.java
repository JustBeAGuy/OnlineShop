package com.epam.kiev.onlineshop.dao.daointerface;

import com.epam.kiev.onlineshop.entity.Good;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Администратор on 12/20/14.
 */
public interface IGoodDAO {
    Good findGood(String id);
    ArrayList<Good> selectGoodsCategory(String category);
    Collection<Good> selectGoodsName(String name);
    Collection<Good> selectGoodsPrice(int min, int max);
    void insertGood(Good good);
    ArrayList<Good> getGoods(String sortBy, int from, int count, String cend, String searchCol, String searchVal);
    ArrayList<Good> getCategory(String category, String sortBy, int from, int count, String cend, String searchCol, String searchVal);
    boolean updateGood(Good good);
    boolean deleteGood(int goodId);
    int getNumberOfGoods(String searchCol, String searchVal);
}
