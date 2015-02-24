package com.epam.kiev.onlineshop.tag;

import com.epam.kiev.onlineshop.manager.TextManager;
import org.apache.log4j.Logger;

import javax.servlet.jsp.tagext.JspTag;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.Locale;

/**
 * Created by Администратор on 2/20/15.
 */
public class TextOut extends TagSupport {
    private String text;
    public void setText(String text) {
        this.text = text;
    }

    public int doStartTag() {
        Locale locale;
        TextManager txtManager = null;
        try {
            locale = (Locale) pageContext.getSession().getAttribute("locale");
            txtManager = TextManager.getInstance(locale);
        } catch (NullPointerException e) {
            txtManager = TextManager.getInstance();
        } finally {
            try {
                pageContext.getOut().write(txtManager.getProperty(text));
            } catch (Exception e) {
                Logger.getLogger(TextOut.class).error(e.getMessage());
            }
        }
        return SKIP_BODY;
    }
}
