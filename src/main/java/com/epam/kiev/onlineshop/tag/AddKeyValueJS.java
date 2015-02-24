package com.epam.kiev.onlineshop.tag;

import org.apache.log4j.Logger;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

/**
 * Created by Администратор on 1/4/15.
 */
public class AddKeyValueJS extends TagSupport {

    //Spliting URL
    private String[] splitParam(String url) {
        return url.split("/");
    }
    //Setting Parameters
    private void setParam(StringBuilder strBuilder, String[] param) {
        if (param.length > 1) {
            strBuilder.append("'command':'" + param[1] + "'");
        } else strBuilder.append("'command':'index'");
        for (int i = 2; i < param.length; i++) {
            strBuilder.append(",'" + i + "':'" + param[i] + "'");
        }
        //Setting reset param
        strBuilder.append(", 'reset' : 'true' ");
    }

    public int doStartTag() throws JspException {
        HttpSession session = pageContext.getSession();
        String url = (String) session.getAttribute("url");
        StringBuilder strBuilder = new StringBuilder();
        setParam(strBuilder,splitParam(url)); // setting command and param from URL
        Logger.getLogger(AddKeyValueJS.class).info("Command and param are: " + strBuilder + " URL : " + url);
        try {
            JspWriter out = pageContext.getOut();
            out.write(String.valueOf(strBuilder));
        } catch (IOException e) {
            throw new JspException(e.getMessage());
        }
        return SKIP_BODY;
    }
}
