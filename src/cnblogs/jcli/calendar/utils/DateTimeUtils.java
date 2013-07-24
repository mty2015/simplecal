package cnblogs.jcli.calendar.utils;

import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.Period;
import org.joda.time.format.DateTimeFormat;

public class DateTimeUtils {

    public static int periodDays(Date d1, Date d2){
        if(d1 == null || d2 == null){
            throw new IllegalArgumentException("d1, d2 should not be null!");
        }
        Duration duration = new Duration(d1.getTime(), d2.getTime());
        return (int)duration.getStandardDays();
    }
    
    public static int periodWeeks(Date d1, Date d2){
        return periodDays(d1, d2) / 7;
    }
    
    public static int periodMonths(Date d1, Date d2){
        if(d1 == null || d2 == null){
            throw new IllegalArgumentException("d1, d2 should not be null!");
        }
        Period period = new Period(d1.getTime(), d2.getTime());
        return period.getYears() * 12 + period.getMonths();
    }
    
    public static int periodYears(Date d1, Date d2){
        if(d1 == null || d2 == null){
            throw new IllegalArgumentException("d1, d2 should not be null!");
        }
        Period period = new Period(d1.getTime(), d2.getTime());
        return period.getYears();
    }
    
    /**
     * 比较时间的大小. 比较的精确度是分, 为什么精确度是分呢? 因为日历事件关注的时间相关度就是分,到秒有意义不?
     * 
     * @param d1
     * @param d2
     * @return if d1 &gt; d2, return 1, else if d1 == d2 ,return 0 , else , return -1
     */
    public static int compareTo(Date d1, Date d2){
        if(d1 == null || d2 == null){
            throw new IllegalArgumentException("d1, d2 should not be null!");
        }
        DateTime dt1 = new DateTime(d1).withSecondOfMinute(0).withMillisOfSecond(0);
        DateTime dt2 = new DateTime(d2).withSecondOfMinute(0).withMillisOfSecond(0);
        return dt1.compareTo(dt2);
    }
    
    public static Date plusDays(Date d, int days){
        if(d == null){
            throw new IllegalArgumentException("d should not be null!");
        }
        return new DateTime(d).plusDays(days).toDate();
    }
    
    public static Date plusMinutes(Date d, int minutes){
        if(d == null){
            throw new IllegalArgumentException("d should not be null!");
        }
        return new DateTime(d).plusMinutes(minutes).toDate();
    }
    
    public static Date plusWeeks(Date d, int weeks){
        if(d == null){
            throw new IllegalArgumentException("d should not be null!");
        }
        return new DateTime(d).plusWeeks(weeks).toDate();
    }
    
    public static Date plusMonths(Date d, int months){
        if(d == null){
            throw new IllegalArgumentException("d should not be null!");
        }
        return new DateTime(d).plusMonths(months).toDate();
    }
    
    public static Date plusYears(Date d, int years){
        if(d == null){
            throw new IllegalArgumentException("d should not be null!");
        }
        return new DateTime(d).plusYears(years).toDate();
    }
    
    public static Date clearTime(Date d){
        if(d == null){
            throw new IllegalArgumentException("d should not be null!");
        }
        return new DateTime(d).withMillisOfDay(0).toDate();
    }
    
    public static Date theWeekFirst(Date d){
        if(d == null){
            throw new IllegalArgumentException("d should not be null!");
        }
        return new DateTime(d).withDayOfWeek(1).toDate();
    }
    public static Date theNextWeekFirst(Date d){
        if(d == null){
            throw new IllegalArgumentException("d should not be null!");
        }
        return new DateTime(d).plusWeeks(1).withDayOfWeek(1).toDate();
    }
    
    public static Date theMonthFirst(Date d){
        if(d == null){
            throw new IllegalArgumentException("d should not be null!");
        }
        return new DateTime(d).withDayOfMonth(1).toDate();
    }
    
    public static Date theNextMonthFirst(Date d){
        if(d == null){
            throw new IllegalArgumentException("d should not be null!");
        }
        return new DateTime(d).plusMonths(1).withDayOfMonth(1).toDate();
    }
    
    public static Date theMonthEnd(Date d){
        if(d == null){
            throw new IllegalArgumentException("d should not be null!");
        }
        return new DateTime(d).withDayOfMonth(new DateTime(d).dayOfMonth().getMaximumValue()).toDate();
    }
    
    public static Date theNextYearFirst(Date d){
        if(d == null){
            throw new IllegalArgumentException("d should not be null!");
        }
        return new DateTime(d).plusYears(1).withDayOfYear(1).toDate();
    }
    
    public static Date theYearEnd(Date d){
        if(d == null){
            throw new IllegalArgumentException("d should not be null!");
        }
        return new DateTime(d).withDayOfYear(new DateTime(d).dayOfYear().getMaximumValue()).toDate();
    }
    
    public static boolean isSameDay(Date d1, Date d2){
        if(d1 == null || d2 == null){
            throw new IllegalArgumentException("d1, d2 should not be null!");
        }
        DateTime dt1 = new DateTime(d1).withMillisOfDay(0);
        DateTime dt2 = new DateTime(d2).withMillisOfDay(0);
        return dt1.compareTo(dt2) == 0;
    }
    
    /**
     * yyyy-MM-dd HH:mm:ss SSS 格式日期字符串
     * 
     * @param dateStr
     * @return
     */
    public static Date parseDate(String dateStr) {
        try {
            return DateTimeFormat.forPattern("yyyy-MM-dd").parseDateTime(dateStr).toDate();
        } catch (IllegalArgumentException ex) {
            return DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss").parseDateTime(dateStr).toDate();
        }
    }
}
