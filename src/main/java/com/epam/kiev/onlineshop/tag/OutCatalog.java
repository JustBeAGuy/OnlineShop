package com.epam.kiev.onlineshop.tag;

import com.epam.kiev.onlineshop.dao.factory.DAOFactory;
import com.epam.kiev.onlineshop.entity.Good;
import com.epam.kiev.onlineshop.manager.ConfigurationManager;
import org.apache.log4j.Logger;

import javax.servlet.jsp.tagext.JspTag;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Администратор on 1/7/15.
 */
public class OutCatalog extends TagSupport {

    private ArrayList<Good> getGoods(String category) {
        DAOFactory factory = DAOFactory.getDAOFactory(DAOFactory.MY_SQL);
        return factory.getGoodDAO().selectGoodsCategory(category);
    }

    private void createGoodDiv(Good good, StringBuilder str) {
        str.append("<div class='good'>" +
                    "<div class='good_img'>" +
                        "<img src='" +
                            ConfigurationManager.getInstance().getProperty(ConfigurationManager.IMG_GOOD) +
                            good.getImg() +
                        "' >" +
                    "</div>" +
                    "<div class='good_name'>" +
                    good.getName() +
                    "</div>" +
                    "<div class='good_price'>" +
                    good.getPrice() +
                    "</div>" +
                    "<div class='good_buy'>" +
                    "</div>" +
                    good.getId() +
                    "</div>"
                   );
    }

    @Override
    public int doStartTag() {
        try {
            StringBuilder outStr = new StringBuilder();
            String category = (String) pageContext.getSession().getAttribute("category");
            ArrayList<Good> goods = getGoods(category);

            for(Good good : goods) {
                createGoodDiv(good, outStr);
            }

            pageContext.getOut().write(String.valueOf(outStr));
        } catch (IOException e) {
            Logger.getLogger(OutCatalog.class).error(e.getMessage());
        }
        return SKIP_BODY;
    }
}
