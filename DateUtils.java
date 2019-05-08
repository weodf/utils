

import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * 时间日期工具类
 *
 * @author weodf
 */
public class DateUtil {
    private static final SimpleDateFormat yyyyMMdd_HHmmss = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static String[] parsePatterns = new String[]{"yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy.MM.dd"};

    public static Date parseDate(Object str) {
        if (str == null) {
            return null;
        } else {
            try {
                return DateUtils.parseDate(str.toString(), parsePatterns);
            } catch (ParseException var2) {
                return null;
            }
        }
    }

    public static String getYearNow(String time_zone) {
        TimeZone.setDefault(TimeZone.getTimeZone(time_zone));
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        String year = c.get(Calendar.YEAR) + "";
        return year;
    }

    public static String getYearByDate(Date date, String time_zone) {
        TimeZone.setDefault(TimeZone.getTimeZone(time_zone));
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        String year = c.get(Calendar.YEAR) + "";
        return year;
    }

    public static int getMonthNow(String time_zone) {
        TimeZone.setDefault(TimeZone.getTimeZone(time_zone));
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        int month = c.get(Calendar.MONTH) + 1;
        return month;
    }

    public static int getMonthByDate(Date date, String time_zone) {
        TimeZone.setDefault(TimeZone.getTimeZone(time_zone));
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int month = c.get(Calendar.MONTH) + 1;
        return month;
    }

    public static int getWeekOfYearByDate(Date date, String time_zone) {
        TimeZone.setDefault(TimeZone.getTimeZone(time_zone));
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.setFirstDayOfWeek(Calendar.SUNDAY);
        c.setMinimalDaysInFirstWeek(1);
        return c.get(Calendar.WEEK_OF_YEAR);
    }

    public static String getYearWeekStrOfYearByDate(Date date, String time_zone) {
        TimeZone.setDefault(TimeZone.getTimeZone(time_zone));
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.setFirstDayOfWeek(Calendar.SUNDAY);
        c.setMinimalDaysInFirstWeek(1);

        int week = c.get(Calendar.WEEK_OF_YEAR);
        int yearNow = c.get(Calendar.YEAR);

        c.add(Calendar.DAY_OF_MONTH, -7);
        int year_2 = c.get(Calendar.YEAR);
        if (week < c.get(Calendar.WEEK_OF_YEAR)) {
            yearNow = year_2 + 1;
        }

        if (week < 10) {
            return yearNow + "0" + week;
        } else {
            return yearNow + "" + week;
        }

    }

    public static int getWeekOfYearNow(String time_zone) {
        TimeZone.setDefault(TimeZone.getTimeZone(time_zone));
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.setFirstDayOfWeek(Calendar.SUNDAY);
        c.setMinimalDaysInFirstWeek(1);
        return c.get(Calendar.WEEK_OF_YEAR);
    }

    public static int getHourNow(String time_zone) {
        TimeZone.setDefault(TimeZone.getTimeZone(time_zone));
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        return c.get(Calendar.HOUR_OF_DAY);
    }

    /**
     * 获取当前日期是周几
     *
     * @return 当前日期是周几
     */
    public static String getWeekStrByDate(Date date, String time_zone) {
        String[] weekDays = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
        TimeZone.setDefault(TimeZone.getTimeZone(time_zone));
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0) {
            w = 0;
        }
        return weekDays[w];
    }
   
   /*
   public static Date stringToDate(String time)
   {
		String startTimeStr = new String(time);
		try {
			date = sdf.parse(startTimeStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	*/

    /**
     * 根据时区和Date 获取时间(yyyy-MM-dd HH:mm:ss)字符串
     *
     * @param date
     * @param time_zone 时区
     * @return
     */
    public static String getDayTimeStrByDate(Date date, String time_zone) {
        SimpleDateFormat time_format_new = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        time_format_new.setTimeZone(TimeZone.getTimeZone(time_zone));
        String dayStr = time_format_new.format(date);
        return dayStr;
    }

    /**
     * 根据时区和Date 获取日期(yyyy-MM-dd)字符串
     *
     * @param date
     * @param time_zone
     * @return
     */
    public static String getDayStrByDate(Date date, String time_zone) {
        SimpleDateFormat day_format = new SimpleDateFormat("yyyy-MM-dd");
        day_format.setTimeZone(TimeZone.getTimeZone(time_zone));
        String dayStr = day_format.format(date);
        return dayStr;
    }

    /**
     * 根据时区和Date 获取日期(yyyy-MM-dd)字符串
     *
     * @param date
     * @return
     */
    public static String getDayStrByDateWithoutTimeZone(Date date) {
        SimpleDateFormat day_format = new SimpleDateFormat("yyyy-MM-dd");
        String dayStr = day_format.format(date);
        return dayStr;
    }

    /**
     * 根据时区和Date 获取时间(HH:mm:ss)字符串
     *
     * @param date
     * @param time_zone 时区
     * @return
     */
    public static String getTimeStrByDate(Date date, String time_zone) {
        SimpleDateFormat time_format_new = new SimpleDateFormat("HH:mm:ss");
        time_format_new.setTimeZone(TimeZone.getTimeZone(time_zone));
        String dayStr = time_format_new.format(date);
        return dayStr;
    }

    /**
     * get first date of given month and year
     *
     * @param year
     * @param month
     * @return
     */
    public static String getFirstDayOfMonth(int year, int month) {
        String monthStr = month < 10 ? "0" + month : String.valueOf(month);
        return year + "-" + monthStr + "-" + "01";
    }

    /**
     * get the last date of given month and year
     *
     * @param year
     * @param month
     * @return
     */
    public static String getLastDayOfMonth(int year, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.DATE, 1);
        calendar.add(Calendar.MONTH, 1);
        calendar.add(Calendar.DAY_OF_YEAR, -1);
        return calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * get Calendar of given year
     *
     * @param year
     * @return
     */
    private static Calendar getCalendarFormYear(int year) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        cal.set(Calendar.YEAR, year);
        cal.setMinimalDaysInFirstWeek(1);
        return cal;
    }

    /**
     * get start date of given week no of a year
     *
     * @param year
     * @param weekNo
     * @return
     */
    public static String getStartDayOfWeekNo(int year, int weekNo) {
        Calendar cal = getCalendarFormYear(year);
        cal.set(Calendar.WEEK_OF_YEAR, weekNo);
        return cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH) + 1) + "-" + cal.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * get the end day of given week no of a year.
     *
     * @param year
     * @param weekNo
     * @return
     */
    public static String getEndDayOfWeekNo(int year, int weekNo) {
        Calendar cal = getCalendarFormYear(year);
        cal.set(Calendar.WEEK_OF_YEAR, weekNo);
        cal.add(Calendar.DAY_OF_WEEK, 6);
        return cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH) + 1) + "-" + cal.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获取两个日期之间的所有日期
     */
    public static List<String> getDaysStrBetweenTwoDate(Long timeFirst, Long timeSecond, String time_zone) {
        List<String> daysList = new ArrayList<>();

        String dayFirst = getDayStrByDate(new Date(timeFirst), time_zone);
        String daySecond = getDayStrByDate(new Date(timeSecond), time_zone);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setTimeZone(TimeZone.getTimeZone(time_zone));
        try {
            Date dateOne = dateFormat.parse(dayFirst);
            Date dateTwo = dateFormat.parse(daySecond);

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(dateOne);

            while (calendar.getTime().before(dateTwo)) {
                // System.out.println(dateFormat.format(calendar.getTime()));
                daysList.add(dateFormat.format(calendar.getTime()));
                calendar.add(Calendar.DAY_OF_MONTH, 1);
            }
            daysList.add(dateFormat.format(calendar.getTime()));
            // System.out.println(dateFormat.format(calendar.getTime()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return daysList;
    }

    /**
     * 根据日期-时间字符串获取该时间点的毫秒值
     *
     * @param day_time_str yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static Long getTimeMillisFromDayTimeStr(String day_time_str) {
        Long timeMilllis = 0L;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date = sdf.parse(day_time_str);
            timeMilllis = date.getTime();
        } catch (ParseException e) {
            return 0L;
        }
        return timeMilllis;
    }

    public static Date getDayFormatDate(Date originalDate) {
        SimpleDateFormat day_format = new SimpleDateFormat("yyyy-MM-dd");
        String day_strString = day_format.format(originalDate);
        Date newDate = null;
        try {
            newDate = day_format.parse(day_strString);

        } catch (ParseException e) {
            newDate = null;
            e.printStackTrace();
        }
        return newDate;
    }

    /**
     * 查询星期
     */
    public static int getDayOfWeek(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;

        dayOfWeek = dayOfWeek == 0 ? 7 : dayOfWeek;

        return dayOfWeek;
    }

    /**
     * 查询小时，24小时制
     */
    public static int getHourOfDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);

        return hourOfDay;
    }

    /**
     * 修改日期中的小时
     */
    public static Date modifyHour(Date date, int hourNum) {
        String dateStr = yyyyMMdd_HHmmss.format(date);

        String hourStr = hourNum < 10 ? "0" + hourNum : "" + hourNum;

        String newDateStr = dateStr.substring(0, 11) + hourStr + dateStr.substring(13);

        try {
            return yyyyMMdd_HHmmss.parse(newDateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 计算两个时间差
     *
     * @param endDate
     * @param nowDate
     * @return
     */
    public static String getDatePoor(Date endDate, Date nowDate) {
        long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 * 60;
        long nm = 1000 * 60;
        long ns = 1000;
        // 获得两个时间的毫秒时间差异
        long diff = endDate.getTime() - nowDate.getTime();
        // 计算差多少天
        long day = diff / nd;
        // 计算差多少小时
        long hour = diff % nd / nh;
        // 计算差多少分钟
        long min = diff % nd % nh / nm;
        // 计算差多少秒//输出结果
        long sec = diff % nd % nh % nm / ns;

        StringBuffer strBuffer = new StringBuffer();
        if (day > 0) {
            strBuffer.append(day).append("天");
        }
        if (hour > 0) {
            strBuffer.append(hour).append("时");
        }
        if (min > 0) {
            strBuffer.append(min).append("分");
        }
        if (sec > 0) {
            strBuffer.append(sec).append("秒");
        }
        return strBuffer.toString();
    }

    //获取当前周的第一天
    public static Date getFirstDayOfWeek(Date date) {
        Calendar cal = Calendar.getInstance();
        try {
            cal.setTime(date);
            cal.set(Calendar.DAY_OF_WEEK, 1);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return cal.getTime();
    }

    //获取当前周的最后一天
    public static Date getLastDayOfWeek(Date date) {
        Calendar cal = Calendar.getInstance();

        try {
            cal.setTime(date);
            cal.set(Calendar.DAY_OF_WEEK, 1);
            cal.set(Calendar.DATE, cal.get(Calendar.DATE) + 6);
        } catch(Exception e) {
            e.printStackTrace();
        }

        return cal.getTime();
    }

    //获取当前月第一天
    public static String getCurrentMonthFirstDay() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cale = Calendar.getInstance();
        cale = Calendar.getInstance();
        cale.add(Calendar.MONTH, 0);
        cale.set(Calendar.DAY_OF_MONTH, 1);
        return format.format(cale.getTime());
    }

    //获取当前月最后一天
    public static String getCurrentMonthLastDay() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cale = Calendar.getInstance();
        cale = Calendar.getInstance();
        cale.add(Calendar.MONTH, 1);
        cale.set(Calendar.DAY_OF_MONTH, 0);
        return format.format(cale.getTime());
    }

}
