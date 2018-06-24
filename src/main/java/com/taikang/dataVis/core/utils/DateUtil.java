package com.taikang.dataVis.core.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** 
 * ClassName:DateUtil
 * Function: 日期转换工具类
 * Date:     2018年3月12日 下午4:40:57
 * @author   吴勇 
 */
public class DateUtil {


    private static Logger logger = LoggerFactory.getLogger(DateUtil.class);

    /**
     * 默认时间格式
     */
    public static final String DATE_FORMAT_DEFAULT = "yyyy-MM-dd HH:mm:ss";
    /**
     * 时间格式: yyyyMMddHHmmss
     */
    public static final String DATE_FORMAT_YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

    public static final String DATE_FORMAT_SIMPLE = "yyyy-MM-dd";
    
    public static final String DATE_FORMAT_YYYYMM = "yyyy-MM";

    public static final String DATE_FORMAT_YYYY = "yyyy";

    public static final String DATE_FORMAT_SSMMHHDDMMYYYY = "ss mm HH dd MM ? yyyy";

    public static final String DAY = "day";
    public static final String MONTH = "month";
    public static final String YEAR = "year";

    /**
     * 时间差单位_日
     */
    public static final String INTERVAL_UNIT_DAY = "day";
    /**
     * 时间差单位_分钟
     */
    public static final String INTERVAL_UNIT_MINUTE = "minute";

    public static SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT_SIMPLE);

    public static SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_YYYY);

    /**
     *
     *
     * @author 孟涛
     * @description 根据参数获取年份
     * @date 2018/5/18 9:02
     * @return java.lang.String -1 去年   |    0 当前年
     */
    public static String getCurrentYear(Integer index) {
        Calendar c = Calendar.getInstance();
        //过去一年
        c.setTime(new Date());
        c.add(Calendar.YEAR, index);
        Date y = c.getTime();
        String year = sdf.format(y);
        return year;
    }

    public static String getCurrentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_DEFAULT);
        return sdf.format(new Date());
    }

   
    public static String getCurrentDate(String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date());
    }
    
    public static String getCurrentZonedDateTime() {
        return zonedDateTimeToString(ZonedDateTime.now(), DATE_FORMAT_DEFAULT);
    }

    public static Date getSimpleDate(Date src) {
        try {
            return simpleDateFormat.parse(simpleDateFormat.format(src));
        } catch (ParseException e) {
            logger.error("simpleDateFormat parse exception", e);
            throw new RuntimeException(e);
        }
    }

    public static ZonedDateTime getDBDate() {
        return ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    }
    
    /**
     * @return
     * @author xff
     */
    public static Date yestoday() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, -1);
        return c.getTime();
    }

    /**
     * @return
     * @author xff
     */
    public static Date yestodaySimpleDate() {
        return getSimpleDate(yestoday());
    }

    public static String addMonth(String date, int i) {
        try {
            GregorianCalendar gCal = new GregorianCalendar(
                Integer.parseInt(date.substring(0, 4)),
                Integer.parseInt(date.substring(5, 7)) - 1,
                Integer.parseInt(date.substring(8, 10)));
            gCal.add(GregorianCalendar.MONTH, i);
            return simpleDateFormat.format(gCal.getTime());
        } catch (Exception e) {
            logger.debug("DateUtil.addMonth():" + e.toString());
            return getCurrentDate();
        }
    }

    /**
     * 将指定日期按默认格式进行格式代化成字符串后输出如：yyyy-MM-dd
     *
     * @param aDate
     * @return
     * @date Mar 11, 2012
     */
    public static final String getDate(Date aDate) {
        SimpleDateFormat df;
        String returnValue = "";
        if (aDate != null) {
            df = new SimpleDateFormat(DATE_FORMAT_SIMPLE);
            returnValue = df.format(aDate);
        }
        return (returnValue);
    }

    /**
     * 将日期字符串按指定格式转换成日期类型
     *
     * @param format  指定的日期格式，如:yyyy-MM-dd
     * @param strDate 待转换的日期字符串
     * @return
     * @throws ParseException
     * @date Mar 11, 2012
     */
    public static final Date convertStringToDate(String format, String strDate) {
        SimpleDateFormat df;
        Date date;
        df = new SimpleDateFormat(format);
        try {
            date = df.parse(strDate);
        } catch (ParseException e) {
            logger.error("ParseException: " + e);
            throw new RuntimeException(e);
        }
        return (date);
    }

    /**
     * @param year
     * @param month
     * @return
     * @author xff
     * 获取月初日期
     */
    public static Date getMonthStartDate(int year, int month) {
        Calendar c = Calendar.getInstance();
        c.set(year, month - 1, 1, 0, 0, 0);
        return c.getTime();
    }

    /**
     * @param year
     * @param month
     * @return
     * @author xff
     * 获取月末日期
     */
    public static Date getMonthEndDate(int year, int month) {
        Calendar c = Calendar.getInstance();
        c.set(year, month, 0, 23, 59, 59);
        return c.getTime();
    }

    /**
     * 将Date转换为ZonedDateTime
     *
     * @param dateTime
     * @return
     */
    public static ZonedDateTime convertDateToZonedDateTime(Date dateTime) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateTime);

        return ZonedDateTime.of(calendar.get(Calendar.YEAR)
            , calendar.get(Calendar.MONTH) + 1
            , calendar.get(Calendar.DATE)
            , calendar.get(Calendar.HOUR_OF_DAY)
            , calendar.get(Calendar.MINUTE)
            , calendar.get(Calendar.SECOND)
            , Calendar.MILLISECOND
            , ZoneId.systemDefault());
    }

    /**
     * @param dateTimeString
     * @return
     * @author 邓哲航
     * @version v2.3.0
     */
    public static ZonedDateTime convertStringToZonedDateTime(String dateTimeString) {
        Date date = convertStringToDate(DATE_FORMAT_DEFAULT, dateTimeString);
        return convertDateToZonedDateTime(date);
    }

    /**
     * 日期转换类型转换
     *
     * @param time
     * @param format
     * @return
     * @author 邓哲航
     * @version v2.3.0
     */
    public static String dateToString(Date time, String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.format(time);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 计算时间差
     *
     * @param dateTimeFrom
     * @param dateTimeTo
     * @return
     * @author 邓哲航
     * @version v2.3.0
     */
    public static int getInterval(Date dateTimeFrom, Date dateTimeTo, String intervalUnit) {
        long millisecond = dateTimeTo.getTime() - dateTimeFrom.getTime();
        int returnValue;
        switch (intervalUnit) {
            case INTERVAL_UNIT_DAY:
                returnValue = (int) (millisecond / 24L / 60L / 60L / 1000L);
                break;
            case INTERVAL_UNIT_MINUTE:
                returnValue = (int) (millisecond / 60L / 1000L);
                break;
            default:
                returnValue = getInterval(dateTimeFrom, dateTimeTo, INTERVAL_UNIT_DAY);
                break;
        }
        return returnValue;
    }

    /**
     * 日期加减
     *
     * @return
     */
    public static String currentTimeAdd(int x, String timeUnit) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_DEFAULT);
        Calendar ca = Calendar.getInstance();

        switch (timeUnit) {
            case DAY:
                ca.add(Calendar.DATE, x);
                break;
            case MONTH:
                ca.add(Calendar.MONTH, x);
                break;
            case YEAR:
                ca.add(Calendar.YEAR, x);
                break;
            default:
                ca.add(Calendar.DATE, x);
                break;
        }
        String dateTime = sdf.format(ca.getTime());

        return dateTime;
    }

    public static String zonedDateTimeToString(ZonedDateTime zonedDateTime, String format) {

        return zonedDateTime.format(DateTimeFormatter.ofPattern(format));
    }

    public static String addDay(String specifiedDate, int d) {
        Calendar c = Calendar.getInstance();
        Date date = null;
        try {
            date = new SimpleDateFormat(DATE_FORMAT_DEFAULT).parse(specifiedDate);
        } catch (ParseException e) {
            try {
                date = new SimpleDateFormat(DATE_FORMAT_SIMPLE).parse(specifiedDate);
            } catch (ParseException e1) {
                logger.error("[{}] addDay 失败", specifiedDate, e1);
                return specifiedDate;
            }
        }
        c.setTime(date);
        int day = c.get(Calendar.DATE);
        c.set(Calendar.DATE, day + d);

        String dayAfter = new SimpleDateFormat(DATE_FORMAT_DEFAULT).format(c.getTime());
        return dayAfter;
    }

    public static String add(String specifiedDate, String timeUnit, int i) {
        String thatDate;
        switch (timeUnit) {
            case DAY:
                thatDate = addDay(specifiedDate, i);
                break;
            case MONTH:
                thatDate = addMonth(specifiedDate, i);
                break;
            case YEAR:
                thatDate = addMonth(specifiedDate, i * 12);
                break;
            default:
                thatDate = addDay(specifiedDate, i);
                break;
        }

        return thatDate;
    }

    public static Date zonedDateTimeToDate(ZonedDateTime zonedDateTime){
        return Date.from(zonedDateTime.toInstant());
    }

    public static String getYerAndMothDate() {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_YYYYMM);
        return sdf.format(new Date());
    }
}
  