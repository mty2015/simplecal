package cnblogs.jcli.calendar;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import cnblogs.jcli.calendar.field.ByDay;
import cnblogs.jcli.calendar.field.FrequencyEnum;
import cnblogs.jcli.calendar.field.WeekDayEnum;
import cnblogs.jcli.calendar.impl.DailyRule;
import cnblogs.jcli.calendar.impl.GregorianMonthlyRule;
import cnblogs.jcli.calendar.impl.GregorianYearlyRule;
import cnblogs.jcli.calendar.impl.LunarMonthlyRule;
import cnblogs.jcli.calendar.impl.LunarYearlyRule;
import cnblogs.jcli.calendar.impl.OnceTimeRule;
import cnblogs.jcli.calendar.impl.WeeklyRule;

public class RuleFactory {

    public static Rule createRule(Event calendar){
        Rule rule = new OnceTimeRule(calendar);
        
        if(calendar == null || StringUtils.isBlank(calendar.getRule())){
            return rule;
        }
        
        FrequencyEnum freq = extractFreq(calendar.getRule());
        int calendarType = calendar.getCalendarType();
        if(FrequencyEnum.DAILY == freq){
            rule = new DailyRule(calendar);
        }else if(FrequencyEnum.WEEKLY == freq){
            rule = new WeeklyRule(calendar);
        }else if(FrequencyEnum.MONTHLY == freq){
            if(calendarType == 0){
                rule = new GregorianMonthlyRule(calendar);
            }else{
                rule = new LunarMonthlyRule(calendar);
            }
        }else if(FrequencyEnum.YEARLY == freq){
            if(calendarType == 0){
                rule = new GregorianYearlyRule(calendar);
            }else{
                rule = new LunarYearlyRule(calendar);
            }
        }
        
        return rule;
    }
    
    public static FrequencyEnum extractFreq(String ruleStr){
        FrequencyEnum result = null;
        
        int reqEndPos = ruleStr.indexOf(";");
        if(reqEndPos == -1){
            throw new IllegalArgumentException("rule has syntax error : " + ruleStr);
        }
        
        //check the feq string is defined 
        String freqStr = ruleStr.substring(0, reqEndPos).toUpperCase();
        try{
            result = FrequencyEnum.valueOf(freqStr);
        }catch(Exception ex){
            throw new IllegalArgumentException("the freq string is not defined: " + freqStr);
        }
        
        return result;
    }
    
    public static Date extractUntilDate(String ruleStr){
        Pattern p = Pattern.compile("until=(\\w+)", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(ruleStr);
        String dateStr = null;
        if(m.find()){
            dateStr = m.group(1);
        }
        if(dateStr == null){
            return null;
        }
        DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyyMMdd'T'HHmmss'Z'");
        return fmt.parseDateTime(dateStr).toDate();
    }
    
    public static Integer extractCount(String ruleStr){
        return extractCommonDigit(ruleStr, "count");
    }

    public static Integer extractInterval(String ruleStr) {
        return extractCommonDigit(ruleStr, "interval");
    }
    
    private static Integer extractCommonDigit(String ruleStr, String fieldName) {
        Pattern p = Pattern.compile(fieldName + "=(\\d+)", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(ruleStr);
        String str = null;
        if(m.find()){
            str = m.group(1);
        }
        if(str == null){
            return null;
        }
        return Integer.valueOf(str);
    }
    
    public static Set<ByDay> extractByDaySet(String ruleStr){
        Set<ByDay> result = new HashSet<ByDay>(); 
        Pattern p = Pattern.compile("byday=([-\\w,]+)", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(ruleStr);
        String byDayStr = null;
        if(m.find()){
            byDayStr = m.group(1);
        }
        if(byDayStr == null){
            return result;
        }
        String[] byDayArray = byDayStr.split(",");
        for(String oneByDayStr : byDayArray){
            int len = oneByDayStr.length();
            Integer qualifier = len > 2 ? Integer.valueOf(oneByDayStr.substring(0,len - 2)) : null;
            WeekDayEnum weekDay = WeekDayEnum.valueOf(oneByDayStr.substring(len -2, len).toUpperCase());
            result.add(new ByDay(qualifier, weekDay));
        }
        return result;
    }
    
    public static Set<Integer> extractByMonthDaySet(String ruleStr){
        return extractCommonDigitSet(ruleStr, "bymonthday");
    }
    
    public static Set<Integer> extractByYearDaySet(String ruleStr){
        return extractCommonDigitSet(ruleStr, "byyearday");
    }
    
    public static Set<Integer> extractByWeekNoSet(String ruleStr){
        return extractCommonDigitSet(ruleStr, "byweekno");
    }
    
    public static Set<Integer> extractByMonthSet(String ruleStr){
        return extractCommonDigitSet(ruleStr, "bymonth");
    }
    
    private static Set<Integer> extractCommonDigitSet(String ruleStr,String fieldName){
        Set<Integer> result = new HashSet<Integer>();
        Pattern p = Pattern.compile(fieldName + "=([-\\d,]+)", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(ruleStr);
        String digitJoinStr = null;
        if(m.find()){
            digitJoinStr = m.group(1);
        }
        if(digitJoinStr == null){
            return result;
        }
        String[] digitArray = digitJoinStr.split(",");
        for(String digit : digitArray){
            result.add(Integer.valueOf(digit));
        }
        return result;
    }
    
    /**
     * HHMM -&gt \d+(MM);
     * @param hourMinute HHMM 格式数字
     * @return HHMM 转换后以<b>分</b>为单位的数量
     */
    public static int toMinutes(int hourMinute){
        int minutesOfDay = (hourMinute / 100) * 60;
        minutesOfDay += (hourMinute % 100);
        return minutesOfDay;
    }

}
