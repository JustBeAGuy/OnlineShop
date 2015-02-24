package com.epam.kiev.onlineshop.support;

import com.epam.kiev.onlineshop.entity.Good;
import com.epam.kiev.onlineshop.manager.ConfigurationManager;

/**
 * Created by Администратор on 2/8/15.
 */
public class ImageOperations {
    public static void addPathToGoodImg(Good good) {
        ConfigurationManager configurationManager = ConfigurationManager.getInstance();
        String pathToImgs = configurationManager.getProperty(ConfigurationManager.IMG_GOOD);
        String imgPath = good.getImg();
        good.setImg(pathToImgs + imgPath);
    }
}
