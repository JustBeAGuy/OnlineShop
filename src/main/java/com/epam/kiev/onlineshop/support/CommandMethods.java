package com.epam.kiev.onlineshop.support;

import org.apache.log4j.Logger;
import sun.util.calendar.CalendarDate;
import sun.util.calendar.Gregorian;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

/**
 * Created by Администратор on 2/9/15.
 */
public class CommandMethods {
    public static String getCurrentTime() {
        Gregorian calendar = Gregorian.getGregorianCalendar();
        CalendarDate calendarDate = calendar.getCalendarDate();
        String year = String.valueOf(calendarDate.getYear());
        String month = String.valueOf(calendarDate.getMonth());
        String dayOfMonth = String.valueOf(calendarDate.getDayOfMonth());
        String hours = String.valueOf(calendarDate.getHours());
        String minutes = String.valueOf(calendarDate.getMinutes());
        String seconds = String.valueOf(calendarDate.getSeconds());

        return year+"-"+month+"-"+dayOfMonth+" "+hours+":"+minutes+":"+seconds;
    }

    public static String paramDec(HttpServletRequest req, String paramName) {
        String res = null;
        try {
            res = new String(req.getParameter(paramName).getBytes("ISO8859_1"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
        } catch (NullPointerException e) {
        }
        return res;
    }
}
