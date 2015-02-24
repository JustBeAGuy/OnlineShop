package com.epam.kiev.onlineshop.tag;


import com.epam.kiev.onlineshop.manager.ConfigurationManager;
import org.apache.log4j.Logger;

import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

/**
 * Created by Администратор on 1/6/15.
 */
public class Include extends TagSupport {
    private String page;

    public void setPage(String page) {
        this.page = page;
    }

    public int doStartTag() {
        try {
            String attr = (String) pageContext.getSession().getAttribute(page);
            if(attr == null) {
                attr = ConfigurationManager.getInstance().getProperty(ConfigurationManager.BLANK);
            }
            String outStr = "<jsp:include page =" + attr + "/>";
            pageContext.getOut().write(outStr);
        } catch (IOException e) {
            Logger.getLogger(Include.class).error(e.getMessage());
        }
        return SKIP_BODY;
    }
}
