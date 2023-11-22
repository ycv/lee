package org.pvuu.utils.tools;

import static java.util.stream.Collectors.toList;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.jetty.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

public class DateUtils extends Date {
    private static final Logger logger = LoggerFactory.getLogger(DateUtils.class);
    private static final long serialVersionUID = 1L;

    public DateUtils() {
        super();
    }

    /**
     * 计算日期
     *
     * @param dateTemp
     * @return
     */
    public static String getDateByParam(String dateTemp, String periodType, int num) {
        String reStr = dateTemp;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date dt = sdf.parse(dateTemp);
            Calendar rightNow = Calendar.getInstance();
            rightNow.setTime(dt);
            if (Objects.equals("year", periodType)) {
                // 日期减1年
                rightNow.add(Calendar.YEAR, -1);
            }

            if (Objects.equals("month", periodType)) {
                // 日期加num个月
                rightNow.add(Calendar.MONTH, num);
            }

            if (Objects.equals("day", periodType)) {
                // 日期加num个天
                rightNow.add(Calendar.DAY_OF_YEAR, num);
            }
            Date dt1 = rightNow.getTime();
            reStr = sdf.format(dt1);
        } catch (Exception e) {
            reStr = dateTemp;
            e.printStackTrace();
        }
        return reStr;
    }

    /**
     * 去除时间段中的 常规法定节假日、周末
     * 
     * @param workDateList
     * @return
     */
    public static List<String> getWorkDateListOutHoliday(List<String> workDateList) {
        // 常规法定节假日
        List<String> initHolidayList =
            Stream.of("09-10", "09-11", "09-12", "10-01", "10-02", "10-03", "10-04", "10-05", "10-06", "10-07")
                .collect(toList());
        // 添加工作日
        // List<String> dateListTemp = Stream.of("2022-09-08", "2022-09-09").collect(toList());
        List<String> dateListTemp = new ArrayList<>();
        List<String> workDateListTemp = new ArrayList<>();
        if (!CollectionUtils.isEmpty(workDateList)) {
            Map<String, String> dateTemp = getToDay(workDateList.get(0).substring(0, 4));
            for (Map.Entry<String, String> entry : dateTemp.entrySet()) {
                workDateListTemp.add(entry.getKey());
            }
            if (!workDateListTemp.isEmpty() && !CollectionUtils.isEmpty(initHolidayList)
                && !initHolidayList.isEmpty()) {
                for (String tmp : workDateList) {
                    if (workDateListTemp.contains(tmp) && !initHolidayList.contains(tmp.substring(5))) {
                        dateListTemp.add(tmp);
                    }
                }
            }
        }
        return dateListTemp.stream().distinct().collect(Collectors.toList());
    }

    /**
     * 获取两个时间段之间的日期
     *
     * @return
     */
    public static List<String> getWorkDateListByBeginEndDay(String beginDate, String endDay) {
        LocalDate timeStartDay = LocalDate.parse(beginDate);
        LocalDate timeEndDay = LocalDate.parse(endDay);
        List<String> workDateList = Stream.iterate(timeStartDay, localDate -> localDate.plusDays(1))
            .limit(ChronoUnit.DAYS.between(timeStartDay, timeEndDay) + 1).map(LocalDate::toString)
            .collect(Collectors.toList());
        return getWorkDateListOutHoliday(workDateList);
    }

    /**
     * 获取日期
     * 
     * @return
     */
    public static Map<String, String> getToDay(String year) {
        Map<String, String> result = new LinkedHashMap<>();
        try {
            // 如果没有传入需要生成哪年的日历，则会自动生成明年的。如果传入哪年的就生成哪年的日历
            if (StringUtil.isBlank(year)) {
                year = String.valueOf(Calendar.getInstance().get(Calendar.YEAR) + 1);
            }
            // 开始时间
            Calendar startTime = Calendar.getInstance();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            startTime.setTime(simpleDateFormat.parse(year + "-01-01"));
            // 结束时间
            Calendar endTime = Calendar.getInstance();
            endTime.setTime(simpleDateFormat.parse(year + "-12-31"));
            while (startTime.compareTo(endTime) <= 0) {
                String week = dateToWeek(simpleDateFormat.format(startTime.getTime()));
                if (!"星期六".equals(week) && !"星期日".equals(week)) {
                    result.put(simpleDateFormat.format(startTime.getTime()).trim(), week.trim());
                }
                // 时间加一天
                startTime.set(Calendar.DATE, startTime.get(Calendar.DATE) + 1);
            }
        } catch (Exception e) {
            logger.info("DateWeekUtils-->getToDay() 获取时间错误:{}", e.getMessage());
        }

        return result;
    }

    /**
     * 根据日期获取 星期 （2019-05-06 ——> 星期一）
     * 
     * @param datetime
     * @return
     */
    private static String dateToWeek(String datetime) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar cal = Calendar.getInstance();
        Date date;
        try {
            date = format.parse(datetime);
            cal.setTime(date);
        } catch (ParseException e) {
            logger.info("DateWeekUtils-->dateToWeek() 获取周错误:{}", e.getMessage());
        }
        // 一周的第几天
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0) {
            w = 0;
        }

        return weekDays[w];
    }

    /**
     * 得到当前年
     *
     * @return
     */
    public static int getCurrYear() {
        Calendar cal = Calendar.getInstance();
        return cal.get(Calendar.YEAR);
    }

    /**
     * 得到当前月份 注意，这里的月份依然是从0开始的
     *
     * @return
     */
    public static int getCurrMonth() {
        Calendar cal = Calendar.getInstance();
        return cal.get(Calendar.MONTH);
    }

    /**
     * 得到当前日
     *
     * @return
     */
    public static int getCurrDay() {
        Calendar cal = Calendar.getInstance();
        return cal.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 得到当前星期
     *
     * @return
     */
    public static int getCurrWeek() {
        Calendar cal = Calendar.getInstance();
        return cal.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * 得到当前小时
     *
     * @return
     */
    public static int getCurrHour() {
        Calendar cal = Calendar.getInstance();
        return cal.get(Calendar.HOUR);
    }

    /**
     * 得到当前分钟
     *
     * @return
     */
    public static int getCurrMinute() {
        Calendar cal = Calendar.getInstance();
        return cal.get(Calendar.MINUTE);
    }

    /**
     * 得到当前秒
     *
     * @return
     */
    public static int getCurrSecond() {
        Calendar cal = Calendar.getInstance();
        return cal.get(Calendar.SECOND);
    }

    /**
     * Date类型转换到Calendar类型
     *
     * @param date
     * @return
     */
    public static Calendar Date2Calendar(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }

    /**
     * Calendar类型转换到Date类型
     *
     * @param cal
     * @return
     */
    public static Date calendar2Date(Calendar cal) {
        return cal.getTime();
    }

    /**
     * Date类型转换到Timestamp类型
     *
     * @param date
     * @return
     */
    public static Timestamp date2Timestamp(Date date) {
        return new Timestamp(date.getTime());
    }

    /**
     * Calendar类型转换到Timestamp类型
     *
     * @return
     */
    public static Timestamp calendar2Timestamp(Calendar cal) {
        return new Timestamp(cal.getTimeInMillis());
    }

    /**
     * Timestamp类型转换到Calendar类型
     *
     * @param timestamp
     * @return
     */
    public static Calendar timestamp2Calendar(Timestamp timestamp) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(timestamp);
        return cal;
    }

    /**
     * 当前时间的下一天时间
     *
     * @return
     */
    public static Date nextDate() {
        return nextDate(new DateUtils(), 1);
    }

    /**
     * 得到当前时间的毫秒数
     *
     * @return
     */
    public static Long getCurrentTimeMillis() {
        return System.currentTimeMillis();
    }

    /**
     * 获取任意时间后num天的时间
     *
     * @param date java.util.Date
     * @param num
     * @return
     */
    public static Date nextDate(Date date, int num) {
        Calendar cla = Calendar.getInstance();
        cla.setTime(date);
        cla.add(Calendar.DAY_OF_YEAR, num);
        return cla.getTime();
    }

    /**
     * 获取任意时间后num天的时间
     *
     * @param date String; <br>
     *            格式支持�?<br>
     *            yyyy-MM-dd HH:mm:ss <br>
     *            yyyy年MM月dd日HH时mm分ss�?<br>
     *            yyyy/MM/dd HH:mm:ss <br>
     *            默认时间格式
     * @param num int
     * @return java.util.Date
     * @throws ParseException
     */
    public static Date nextDate(String date, int num) throws ParseException {
        if (date == null)
            return null;
        SimpleDateFormat sdf = null;
        if (date.indexOf("-") != -1)
            sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        else if (date.indexOf("-") != -1)
            sdf = new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss");
        else if (date.indexOf("/") != -1)
            sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        else if (date.indexOf("CST") != -1)
            sdf = new SimpleDateFormat();
        else
            System.out.println("no match format:");
        return nextDate(sdf.parse(date), num);
    }

    /**
     * 获取当天时间num天后的时间<br>
     * 如果num小于0则返回当前时间的前num天的时间<br>
     * ，否则返回当天时间后num天的时间
     *
     * @param num int;
     * @return java.util.Date
     */
    public static Date nextDate(int num) {
        return nextDate(new Date(), num);
    }

    /**
     * 取得当前日期是多少周
     *
     * @param date
     * @return
     */
    public static int getWeekOfYear(Date date) {
        Calendar c = Calendar.getInstance();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        /**
         * 设置一年中第一个星期所需的最少天数，例如，如果定义第一个星期包含一年第一个月的第一天，则使用值 1 调用此方法。 如果最少天数必须是一整个星期，则使用值 7 调用此方法。
         **/
        c.setMinimalDaysInFirstWeek(1);
        c.setTime(date);
        return c.get(Calendar.WEEK_OF_YEAR);
    }

    /**
     * 得到某一年周的总数
     *
     * @param year
     * @return
     */
    public static int getMaxWeekNumOfYear(int year) {
        Calendar c = Calendar.getInstance();
        c.set(year, Calendar.DECEMBER, 31, 23, 59, 59);
        return getWeekOfYear(c.getTime());
    }

    /**
     * 得到某年某周的第一天
     *
     * @param year
     * @param week
     * @return
     */
    public static Date getFirstDayOfWeek(int year, int week) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.WEEK_OF_YEAR, week);
        c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);// 设置周一
        c.setFirstDayOfWeek(Calendar.MONDAY);
        return c.getTime();
    }

    /**
     * 得到当周的第一天
     *
     * @param year
     * @param week
     * @return
     */
    public static Date getFirstDayOfWeek(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);// 设置周一
        c.setFirstDayOfWeek(Calendar.MONDAY);
        return c.getTime();
    }

    /**
     * 得到某年某周的最后一天
     *
     * @param year
     * @param week
     * @return
     */
    public static Date getLastDayOfWeek(int year, int week) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.WEEK_OF_YEAR, week);
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek() + 6); // Sunday
        return c.getTime();
    }

    /**
     * 得到当前周的周的最后一天
     *
     * @param year
     * @param week
     * @return
     */
    public static Date getLastDayOfWeek(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek() + 6); // Sunday
        return c.getTime();
    }

    /**
     * 得到某年某月的第一天
     *
     * @param year
     * @param month
     * @return
     */
    public static Date getFirstDayOfMonth(int year, int month) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month - 1);
        c.set(Calendar.DAY_OF_MONTH, c.getActualMinimum(Calendar.DAY_OF_MONTH));
        return c.getTime();
    }

    /**
     * 获得当前月的第一天
     *
     * @param year
     * @param date
     * @return
     */
    public static Date getFirstDayOfMonth(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.DAY_OF_MONTH, c.getActualMinimum(Calendar.DAY_OF_MONTH));
        return c.getTime();
    }

    /**
     * 得到某年某月的最后一天
     *
     * @param year
     * @param month
     * @return
     */
    public static Date getLastDayOfMonth(int year, int month) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month - 1);
        c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
        return c.getTime();
    }

    /**
     * 获得当前月的最后一天
     *
     * @param year
     * @param date
     * @return
     */
    public static Date getLastDayOfMonth(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
        return c.getTime();
    }

    /**
     * 得到某年某季度第一天
     *
     * @param year
     * @param quarter
     * @return
     */
    public static Date getFirstDayOfQuarter(int year, int quarter) {
        int month = 0;
        if (quarter > 4) {
            return null;
        } else {
            month = (quarter - 1) * 3 + 1;
        }
        return getFirstDayOfMonth(year, month);
    }

    /**
     * 得到某年某季度最后一天
     *
     * @param year
     * @param quarter
     * @return
     */
    public static Date getLastDayOfQuarter(int year, int quarter) {
        int month = 0;
        if (quarter > 4) {
            return null;
        } else {
            month = quarter * 3;
        }
        return getLastDayOfMonth(year, month);
    }

    /**
     * 得到某年第一天
     *
     * @param year
     * @return
     */
    public static Date getFirstDayOfYear(int year) {
        return getFirstDayOfQuarter(year, 1);
    }

    /**
     * 得到当年第一天
     *
     * @param year
     * @return
     */
    public static Date getFirstDayOfYear(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int year = c.get(Calendar.YEAR);
        return getFirstDayOfYear(year);
    }

    /**
     * 得到某年最后一天
     *
     * @param year
     * @return
     */
    public static Date getLastDayOfYear(int year) {
        return getLastDayOfQuarter(year, 4);
    }

    /**
     * 得到当年最后一天
     *
     * @param year
     * @return
     */
    public static Date getLastDayOfYear(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int year = c.get(Calendar.YEAR);
        return getLastDayOfYear(year);
    }

    /**
     * 功能：获取本周的开始时间 示例：2013-05-13 00:00:00
     */
    public static Date getWeekStart() {// 当周开始时间
        Calendar currentDate = Calendar.getInstance();
        currentDate.setFirstDayOfWeek(Calendar.MONDAY);
        currentDate.set(Calendar.HOUR_OF_DAY, 0);
        currentDate.set(Calendar.MINUTE, 0);
        currentDate.set(Calendar.SECOND, 0);
        currentDate.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        return (Date)currentDate.getTime();
    }

    /**
     * 功能：获取本周的结束时间 示例：2013-05-19 23:59:59
     */
    public static Date getWeekEnd() {// 当周结束时间
        Calendar currentDate = Calendar.getInstance();
        currentDate.setFirstDayOfWeek(Calendar.MONDAY);
        currentDate.set(Calendar.HOUR_OF_DAY, 23);
        currentDate.set(Calendar.MINUTE, 59);
        currentDate.set(Calendar.SECOND, 59);
        currentDate.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        return (Date)currentDate.getTime();
    }

    /**
     * 得到指定或者当前时间前n天的Calendar
     *
     * @param day
     * @return
     */
    public static Calendar getBeforeNDays(int n) {
        Calendar cal = null;
        // 偏移量，给定n天的毫秒数
        long offset = n * 24 * 60 * 60 * 1000;
        if (cal != null) {
            cal.setTimeInMillis(cal.getTimeInMillis() - offset);
        } else {
            cal = Calendar.getInstance();
            cal.setTimeInMillis(cal.getTimeInMillis() - offset);
        }
        return cal;
    }

    /**
     * 得到指定或者当前时间后n天的Calendar
     *
     * @param n
     * @return
     */
    public static Calendar getAfterNDays(int n) {
        Calendar cal = null;
        // 偏移量，给定n天的毫秒数
        long offset = n * 24 * 60 * 60 * 1000;
        if (cal != null) {
            cal.setTimeInMillis(cal.getTimeInMillis() + offset);
        } else {
            cal = Calendar.getInstance();
            cal.setTimeInMillis(cal.getTimeInMillis() + offset);
        }
        return cal;
    }

    /**
     * 获取当前时间的后一天
     *
     * @return
     */
    public static Calendar getTomorrowDay() {
        long offset = 1 * 24 * 60 * 60 * 1000;
        Calendar cal = null;
        if (cal != null) {
            cal.setTimeInMillis(cal.getTimeInMillis() + offset);
        } else {
            cal = Calendar.getInstance();
            cal.setTimeInMillis(cal.getTimeInMillis() + offset);
        }
        return cal;
    }

    /**
     * 获取当前时间的上一天
     *
     * @return
     */
    public static Calendar getYesterDay() {
        long offset = 1 * 24 * 60 * 60 * 1000;
        Calendar cal = null;
        if (cal != null) {
            cal.setTimeInMillis(cal.getTimeInMillis() - offset);
        } else {
            cal = Calendar.getInstance();
            cal.setTimeInMillis(cal.getTimeInMillis() - offset);
        }
        return cal;

    }
}
