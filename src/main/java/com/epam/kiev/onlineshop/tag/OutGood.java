package com.epam.kiev.onlineshop.tag;

import com.epam.kiev.onlineshop.dao.factory.DAOFactory;
import com.epam.kiev.onlineshop.entity.Good;
import com.epam.kiev.onlineshop.manager.ConfigurationManager;
import org.apache.log4j.Logger;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Администратор on 1/9/15.
 */
public class OutGood extends TagSupport {

    private Good getGood(String goodStr) {
        DAOFactory factory = DAOFactory.getDAOFactory(DAOFactory.MY_SQL);
        return factory.getGoodDAO().findGood(goodStr);
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
            String goodStr = (String) pageContext.getSession().getAttribute("goodId");
            Good good = getGood(goodStr);

            if(good !=null) {
                createGoodDiv(good, outStr);
            }

            pageContext.getOut().write(String.valueOf(outStr));
        } catch (IOException e) {
            Logger.getLogger(OutGood.class).error(e.getMessage());
        }
        return SKIP_BODY;
    }
}
