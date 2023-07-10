package com.atguigu.gmall.common.util;

import org.apache.commons.lang.time.DateUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    private static final String dateFormat = "yyyy-MM-dd";

    public Long getTimeSubtract(Date date1,Date date2) {
        return (date1.getTime() - date2.getTime()) / 1000;
    }

    public static String formatDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        return sdf.format(date);
    }

    public static int truncatedCompareTo(final Date date1,final Date date2,final int field) {
        return DateUtils.truncatedCompareTo(date1,date2,field);
    }

    public static boolean dateCompare(Date beginDate,Date endDate) {
        // endDate > beginDate
        if (DateUtil.truncatedCompareTo(beginDate,endDate, Calendar.SECOND) == 1) {
            return false;
        }
        // beginDate  <= endDate
        return true;
    }
}
