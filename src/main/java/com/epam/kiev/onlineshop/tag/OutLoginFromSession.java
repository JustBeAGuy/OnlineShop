package com.epam.kiev.onlineshop.tag;

import com.epam.kiev.onlineshop.entity.User;
import org.apache.log4j.Logger;

import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

/**
 * Created by Администратор on 1/6/15.
 */
public class OutLoginFromSession extends TagSupport {
    private String attr;

    public void setAttr(String attr) {
        this.attr = attr;
    }

    public int doStartTag() {
        try {
            User user = (User) pageContext.getSession().getAttribute(attr);
            String outStr = user.getLogin();
            if(outStr == null) {
                outStr = "";
            }
            pageContext.getOut().write(outStr);
        } catch (IOException e) {
            Logger.getLogger(OutLoginFromSession.class).error(e.getMessage());
        } catch (NullPointerException e) {
//            Logger.getLogger(OutLoginFromSession.class).error(e.getMessage());
        } catch (ClassCastException e) {
                Logger.getLogger(OutLoginFromSession.class).error(e.getMessage());
        }
        return SKIP_BODY;
    }
}
