package cnblogs.jcli.calendar;

import java.util.Date;
import java.util.Set;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;

import cnblogs.jcli.calendar.field.ByDay;
import cnblogs.jcli.calendar.field.FrequencyEnum;
import cnblogs.jcli.calendar.field.WeekDayEnum;


public class RuleFactoryTest extends Assert{

    @Test
    public void testExtractFreq(){
        assertTrue(RuleFactory.extractFreq("DAILY;UNTIL=20140101T000000Z") == FrequencyEnum.DAILY);
    }
    
    @Test
    public void testExtractUntilDate(){
        Date date = RuleFactory.extractUntilDate("DAILY;UNTIL=20140101T000000Z");
        DateTime dt = new DateTime(date);
        assertTrue(dt.getYear()==2014 && dt.getMonthOfYear() == 1 && dt.getDayOfMonth() == 1);
    }
    
    @Test
    public void testExtractCount(){
        Integer count = RuleFactory.extractCount("DAILY;UNTIL=20140101T000000Z;COUNT=34");
        assertTrue(count.equals(Integer.valueOf(34)));
    }
    
    @Test
    public void testExtractByDaySet(){
        Set<ByDay> byDaySet = RuleFactory.extractByDaySet("YEARLY;BYDAY=1MO,-2TH;COUNT=34");
        assertTrue(byDaySet.size() == 2);
        for(ByDay byDay : byDaySet){
            assertTrue((byDay.getQualifier() == 1 && byDay.getWeekDay() == WeekDayEnum.MO)
                || (byDay.getQualifier() == -2 && byDay.getWeekDay() == WeekDayEnum.TH));
        }
    }
    
    @Test
    public void testExtractByMonthSet(){
        Set<Integer> byMonthSet = RuleFactory.extractByMonthSet("YEARLY;BYDAY=1MO,-2TH;COUNT=34;BYMONTH=12,-1;");
        assertTrue(byMonthSet.size() == 2);
        for(Integer byMonth : byMonthSet){
            assertTrue(byMonth == 12 || byMonth == -1);
        }
    }
}
