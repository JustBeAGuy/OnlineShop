package com.epam.kiev.onlineshop.tag;

import com.epam.kiev.onlineshop.manager.ConfigurationManager;
import org.apache.log4j.Logger;

import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

/**
 * Created by Администратор on 1/6/15.
 */
public class OutConfig extends TagSupport {
    private String config;

    public void setConfig(String config) {
        this.config = config;
    }

    public int doStartTag() {
        try {
//            String outStr = ConfigurationManager.getInstance().getProperty(config);
//            if(outStr == null) {
//                outStr = "";
//            }
            pageContext.getOut().write("/" + config);
        } catch (IOException e) {
            Logger.getLogger(Include.class).error(e.getMessage());
        }
        return SKIP_BODY;
    }
}
