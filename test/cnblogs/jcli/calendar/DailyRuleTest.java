package cnblogs.jcli.calendar;

import org.junit.Assert;
import org.junit.Test;

import cnblogs.jcli.calendar.utils.DateTimeUtils;

public class DailyRuleTest extends Assert {

    @Test
    public void testDailyRuleCase1() {
        Event calendar = new Event();
        calendar.setStartDate(DateTimeUtils.parseDate("2013-06-21 09:30:00"));
        calendar.setRule("DAILY;UNTIL=20140101T000000Z;INTERVAL=3");

        Rule rule = RuleFactory.createRule(calendar);

        // 在事件最开始之前
        assertTrue(DateTimeUtils.compareTo(rule
                .nextOccurDate(DateTimeUtils.parseDate("2013-05-29")), DateTimeUtils
                .parseDate("2013-6-21 09:30:00")) == 0);
        // 在中间的任何一个周期上
        assertTrue(DateTimeUtils.compareTo(rule.nextOccurDate(DateTimeUtils
                .parseDate("2013-7-1 12:36:00")), DateTimeUtils.parseDate("2013-7-3 09:30:00")) == 0);
        // 超过until日期
        assertEquals(rule.nextOccurDate(DateTimeUtils.parseDate("2013-12-31 12:36:00")), null);
    }

    @Test
    public void testDailyRuleCase2() {
        Event calendar = new Event();
        calendar.setStartDate(DateTimeUtils.parseDate("2013-06-21 09:30:00"));
        calendar.setRule("DAILY;COUNT=3;INTERVAL=3");

        Rule rule = RuleFactory.createRule(calendar);

        assertTrue(DateTimeUtils.compareTo(rule.nextOccurDate(DateTimeUtils
            .parseDate("2013-6-21 9:30:00")), DateTimeUtils.parseDate("2013-6-24 09:30:00")) == 0);
        
        // 在count内
        assertTrue(DateTimeUtils.compareTo(rule.nextOccurDate(DateTimeUtils
                .parseDate("2013-6-26 12:36:00")), DateTimeUtils.parseDate("2013-6-27 09:30:00")) == 0);
        // 在count外
        assertEquals(rule.nextOccurDate(DateTimeUtils.parseDate("2013-6-27 12:36:00")), null);
    }


    @Test
    public void testDailyRuleCase3() {
        Event calendar = new Event();
        calendar.setStartDate(DateTimeUtils.parseDate("2013-06-21 00:00:00"));
        calendar.setRule("DAILY;INTERVAL=3");

        Rule rule = RuleFactory.createRule(calendar);

        assertTrue(rule.includes(DateTimeUtils.parseDate("2013-6-21")));
        assertFalse(rule.includes(DateTimeUtils.parseDate("2013-6-22 12:36:00")));
        assertFalse(rule.includes(DateTimeUtils.parseDate("2013-6-23 12:36:00")));
        assertTrue(rule.includes(DateTimeUtils.parseDate("2013-6-24 12:36:00")));
    }

    @Test
    public void testWeekRuleCase1() {
        Event calendar = new Event();
        calendar.setStartDate(DateTimeUtils.parseDate("2013-06-22 16:11:00"));
        calendar.setRule("WEEKLY;BYDAY=MO,SA;COUNT=2");

        Rule rule = RuleFactory.createRule(calendar);
        assertTrue(DateTimeUtils.compareTo(rule.nextOccurDate(DateTimeUtils
                .parseDate("2013-6-23 16:30:00")), DateTimeUtils.parseDate("2013-6-24 16:11:00")) == 0);
        assertNull(rule.nextOccurDate(DateTimeUtils.parseDate("2013-6-29 16:30:00")));
    }

    @Test
    public void testWeekRuleCase2() {
        Event calendar = new Event();
        calendar.setStartDate(DateTimeUtils.parseDate("2013-06-17 00:00:00"));
        calendar.setRule("WEEKLY;BYDAY=MO,WE,SA;INTERVAL=2");

        Rule rule = RuleFactory.createRule(calendar);
        assertTrue(DateTimeUtils.compareTo(rule.nextOccurDate(DateTimeUtils
                .parseDate("2013-6-17 00:00:00")), DateTimeUtils.parseDate("2013-6-19 00:00:00")) == 0);
        assertTrue(DateTimeUtils.compareTo(rule.nextOccurDate(DateTimeUtils
            .parseDate("2013-6-19 14:00:00")), DateTimeUtils.parseDate("2013-6-22 00:00:00")) == 0);
        assertTrue(DateTimeUtils.compareTo(rule.nextOccurDate(DateTimeUtils
            .parseDate("2013-6-22 14:00:00")), DateTimeUtils.parseDate("2013-7-1 00:00:00")) == 0);
    }
    
    @Test
    public void testWeekRuleCase3() {
        Event calendar = new Event();
        calendar.setStartDate(DateTimeUtils.parseDate("2013-06-18 12:36:00"));
        calendar.setRule("WEEKLY;BYDAY=MO,TU,WE,SA;INTERVAL=3");

        Rule rule = RuleFactory.createRule(calendar);
        assertFalse(rule.includes(DateTimeUtils.parseDate("2013-6-17 00:00:00")));
        assertTrue(rule.includes(DateTimeUtils.parseDate("2013-6-18 00:00:00")));
        assertTrue(rule.includes(DateTimeUtils.parseDate("2013-6-19 00:00:00")));
        assertFalse(rule.includes(DateTimeUtils.parseDate("2013-6-20 00:00:00")));
        assertTrue(rule.includes(DateTimeUtils.parseDate("2013-6-22 00:00:00")));
        assertFalse(rule.includes(DateTimeUtils.parseDate("2013-6-26 00:00:00")));
        assertFalse(rule.includes(DateTimeUtils.parseDate("2013-7-3 00:00:00")));
        assertTrue(rule.includes(DateTimeUtils.parseDate("2013-7-10 00:00:00")));
    }

    @Test
    public void testMonthlyRuleCase1() {
        Event calendar = new Event();
        calendar.setStartDate(DateTimeUtils.parseDate("2013-06-19 12:36:00"));
        calendar.setRule("MONTHLY;BYDAY=3WE;");
        Rule rule = RuleFactory.createRule(calendar);
        
        
        assertTrue(DateTimeUtils.compareTo(rule.nextOccurDate(DateTimeUtils
            .parseDate("2013-6-18 17:00:00")), DateTimeUtils.parseDate("2013-6-19 12:36:00")) == 0);
        
        assertTrue(DateTimeUtils.compareTo(rule.nextOccurDate(DateTimeUtils
            .parseDate("2013-6-19 12:36:00")), DateTimeUtils.parseDate("2013-7-17 12:36:00")) == 0);
        
        assertTrue(DateTimeUtils.compareTo(rule.nextOccurDate(DateTimeUtils
            .parseDate("2013-6-19 12:35:00")), DateTimeUtils.parseDate("2013-6-19 12:36:00")) == 0);
        
        assertTrue(DateTimeUtils.compareTo(rule.nextOccurDate(DateTimeUtils
            .parseDate("2013-7-17 12:40:00")), DateTimeUtils.parseDate("2013-8-21 12:36:00")) == 0);
    }
    
    @Test
    public void testMonthlyRuleCase2() {
        Event calendar = new Event();
        calendar.setStartDate(DateTimeUtils.parseDate("2013-06-29 12:36:00"));
        calendar.setRule("MONTHLY;BYDAY=-1SA;");
        Rule rule = RuleFactory.createRule(calendar);
        
        assertTrue(DateTimeUtils.compareTo(rule.nextOccurDate(DateTimeUtils
            .parseDate("2013-6-18 17:00:00")), DateTimeUtils.parseDate("2013-6-29 12:36:00")) == 0);
        
        assertTrue(DateTimeUtils.compareTo(rule.nextOccurDate(DateTimeUtils
            .parseDate("2013-6-29 12:36:00")), DateTimeUtils.parseDate("2013-7-27 12:36:00")) == 0);
        
        assertTrue(DateTimeUtils.compareTo(rule.nextOccurDate(DateTimeUtils
            .parseDate("2013-6-29 12:35:00")), DateTimeUtils.parseDate("2013-6-29 12:36:00")) == 0);
        
    }
    
    @Test
    public void testMonthlyRuleCase3() {
        Event calendar = new Event();
        calendar.setStartDate(DateTimeUtils.parseDate("2013-06-29 00:00:00"));
        calendar.setRule("MONTHLY;BYDAY=-1SA;");
        Rule rule = RuleFactory.createRule(calendar);
        
        assertTrue(DateTimeUtils.compareTo(rule.nextOccurDate(DateTimeUtils
            .parseDate("2013-6-18 17:00:00")), DateTimeUtils.parseDate("2013-6-29 00:00:00")) == 0);
        
        assertTrue(DateTimeUtils.compareTo(rule.nextOccurDate(DateTimeUtils
            .parseDate("2013-6-29 00:00:00")), DateTimeUtils.parseDate("2013-7-27 00:00:00")) == 0);
        
        assertTrue(DateTimeUtils.compareTo(rule.nextOccurDate(DateTimeUtils
            .parseDate("2013-6-28 23:59:00")), DateTimeUtils.parseDate("2013-6-29 00:00:00")) == 0);
        
    }
    
    @Test
    public void testMonthlyRuleCase4() {
        Event calendar = new Event();
        calendar.setStartDate(DateTimeUtils.parseDate("2013-06-29 00:00:00"));
        calendar.setRule("MONTHLY;BYMONTHDAY=29;");
        Rule rule = RuleFactory.createRule(calendar);
        
        assertTrue(DateTimeUtils.compareTo(rule.nextOccurDate(DateTimeUtils
            .parseDate("2013-6-18 17:00:00")), DateTimeUtils.parseDate("2013-6-29 00:00:00")) == 0);
        
        assertTrue(DateTimeUtils.compareTo(rule.nextOccurDate(DateTimeUtils
            .parseDate("2013-6-29 00:00:00")), DateTimeUtils.parseDate("2013-7-29 00:00:00")) == 0);
        
        assertTrue(DateTimeUtils.compareTo(rule.nextOccurDate(DateTimeUtils
            .parseDate("2013-6-28 23:59:00")), DateTimeUtils.parseDate("2013-6-29 00:00:00")) == 0);
        
    }
    
    @Test
    public void testMonthlyRuleCase5() {
        Event calendar = new Event();
        calendar.setStartDate(DateTimeUtils.parseDate("2013-08-31 00:00:00"));
        calendar.setRule("MONTHLY;BYMONTHDAY=31;");
        Rule rule = RuleFactory.createRule(calendar);
        
        assertTrue(DateTimeUtils.compareTo(rule.nextOccurDate(DateTimeUtils
            .parseDate("2013-8-18 17:00:00")), DateTimeUtils.parseDate("2013-8-31 00:00:00")) == 0);
        
        assertTrue(DateTimeUtils.compareTo(rule.nextOccurDate(DateTimeUtils
            .parseDate("2013-8-31 00:00:00")), DateTimeUtils.parseDate("2013-10-31 00:00:00")) == 0);
        
        assertTrue(DateTimeUtils.compareTo(rule.nextOccurDate(DateTimeUtils
            .parseDate("2013-8-28 23:59:00")), DateTimeUtils.parseDate("2013-8-31 00:00:00")) == 0);
        
    }
    
    @Test
    public void testMonthlyRuleCase6() {
        Event calendar = new Event();
        calendar.setStartDate(DateTimeUtils.parseDate("2013-02-28 00:00:00"));
        calendar.setRule("MONTHLY;BYMONTHDAY=-1");
        Rule rule = RuleFactory.createRule(calendar);
        
        assertTrue(DateTimeUtils.compareTo(rule.nextOccurDate(DateTimeUtils
            .parseDate("2013-2-28 17:00:00")), DateTimeUtils.parseDate("2013-3-31 00:00:00")) == 0);
        
    }
    
    @Test
    public void testYearlyRuleCase1() {
        Event calendar = new Event();
        calendar.setStartDate(DateTimeUtils.parseDate("2013-06-16 00:00:00"));
        calendar.setRule("YEARLY;BYMONTH=6;BYDAY=3SU");
        Rule rule = RuleFactory.createRule(calendar);
        
        assertTrue(DateTimeUtils.compareTo(rule.nextOccurDate(DateTimeUtils
            .parseDate("2013-8-18 17:00:00")), DateTimeUtils.parseDate("2014-6-15 00:00:00")) == 0);
        
    }
    
    @Test
    public void testYearlyRuleCase2() {
        Event calendar = new Event();
        calendar.setStartDate(DateTimeUtils.parseDate("2013-06-16 00:00:00"));
        calendar.setRule("YEARLY;BYMONTH=6;BYMONTHDAY=26");
        Rule rule = RuleFactory.createRule(calendar);
        
        assertTrue(DateTimeUtils.compareTo(rule.nextOccurDate(DateTimeUtils
            .parseDate("2013-8-18 17:00:00")), DateTimeUtils.parseDate("2014-6-26 00:00:00")) == 0);
        
    }
    
    
    
    @Test
    public void testLunarMonthlyRuleCase1() {
        Event calendar = new Event();
        calendar.setCalendarType(1);
        calendar.setStartDate(DateTimeUtils.parseDate("2013-06-19 12:36:00"));
        calendar.setRule("MONTHLY;BYDAY=2WE;");
        Rule rule = RuleFactory.createRule(calendar);
        
        
        assertTrue(DateTimeUtils.compareTo(rule.nextOccurDate(DateTimeUtils
            .parseDate("2013-6-18 17:00:00")), DateTimeUtils.parseDate("2013-6-19 12:36:00")) == 0);
        
        assertTrue(DateTimeUtils.compareTo(rule.nextOccurDate(DateTimeUtils
            .parseDate("2013-6-19 12:36:00")), DateTimeUtils.parseDate("2013-7-17 12:36:00")) == 0);
        
        assertTrue(DateTimeUtils.compareTo(rule.nextOccurDate(DateTimeUtils
            .parseDate("2013-6-19 12:35:00")), DateTimeUtils.parseDate("2013-6-19 12:36:00")) == 0);
        
        assertTrue(DateTimeUtils.compareTo(rule.nextOccurDate(DateTimeUtils
            .parseDate("2013-7-17 12:40:00")), DateTimeUtils.parseDate("2013-8-14 12:36:00")) == 0);
    }
    
    @Test
    public void testLunarMonthlyRuleCase2() {
        Event calendar = new Event();
        calendar.setCalendarType(1);
        calendar.setStartDate(DateTimeUtils.parseDate("2013-07-6 12:36:00"));
        calendar.setRule("MONTHLY;BYDAY=-1SA;");
        Rule rule = RuleFactory.createRule(calendar);
        
        assertTrue(DateTimeUtils.compareTo(rule.nextOccurDate(DateTimeUtils
            .parseDate("2013-6-18 17:00:00")), DateTimeUtils.parseDate("2013-7-6 12:36:00")) == 0);
        
        assertTrue(DateTimeUtils.compareTo(rule.nextOccurDate(DateTimeUtils
            .parseDate("2013-7-6 12:36:00")), DateTimeUtils.parseDate("2013-8-3 12:36:00")) == 0);
        
        
    }
    
    @Test
    public void testLunarMonthlyRuleCase3() {
        Event calendar = new Event();
        calendar.setCalendarType(1);
        calendar.setStartDate(DateTimeUtils.parseDate("2013-07-6 00:00:00"));
        calendar.setRule("MONTHLY;BYDAY=-1SA;");
        Rule rule = RuleFactory.createRule(calendar);
        
        assertTrue(DateTimeUtils.compareTo(rule.nextOccurDate(DateTimeUtils
            .parseDate("2013-6-18 17:00:00")), DateTimeUtils.parseDate("2013-7-6 00:00:00")) == 0);
        
        assertTrue(DateTimeUtils.compareTo(rule.nextOccurDate(DateTimeUtils
            .parseDate("2013-7-6 00:00:00")), DateTimeUtils.parseDate("2013-8-3 00:00:00")) == 0);
        
    }
    
    @Test
    public void testLunarMonthlyRuleCase4() {
        Event calendar = new Event();
        calendar.setCalendarType(1);
        calendar.setStartDate(DateTimeUtils.parseDate("2013-07-6 00:00:00"));
        calendar.setRule("MONTHLY;BYMONTHDAY=29;");
        Rule rule = RuleFactory.createRule(calendar);
        
        assertTrue(DateTimeUtils.compareTo(rule.nextOccurDate(DateTimeUtils
            .parseDate("2013-6-18 17:00:00")), DateTimeUtils.parseDate("2013-7-6 00:00:00")) == 0);
        
        assertTrue(DateTimeUtils.compareTo(rule.nextOccurDate(DateTimeUtils
            .parseDate("2013-7-6 00:00:00")), DateTimeUtils.parseDate("2013-8-5 00:00:00")) == 0);
        
    }
    
    @Test
    public void testLunarMonthlyRuleCase5() {
        Event calendar = new Event();
        calendar.setCalendarType(1);
        calendar.setStartDate(DateTimeUtils.parseDate("2014-09-24 00:00:00"));
        calendar.setRule("MONTHLY;BYMONTHDAY=1;");
        Rule rule = RuleFactory.createRule(calendar);
        
        assertTrue(DateTimeUtils.compareTo(rule.nextOccurDate(DateTimeUtils
            .parseDate("2014-9-20 00:00:00")), DateTimeUtils.parseDate("2014-9-24 00:00:00")) == 0);
        
        System.out.println(rule.nextOccurDate(DateTimeUtils
            .parseDate("2014-9-24 00:00:00")));
        assertTrue(DateTimeUtils.compareTo(rule.nextOccurDate(DateTimeUtils
            .parseDate("2014-9-24 00:00:00")), DateTimeUtils.parseDate("2014-10-24 00:00:00")) == 0);
        
        assertTrue(DateTimeUtils.compareTo(rule.nextOccurDate(DateTimeUtils
            .parseDate("2014-10-24 00:00:00")), DateTimeUtils.parseDate("2014-11-22 00:00:00")) == 0);
        
        assertTrue(DateTimeUtils.compareTo(rule.nextOccurDate(DateTimeUtils
            .parseDate("2014-11-22 18:00:00")), DateTimeUtils.parseDate("2014-12-22 00:00:00")) == 0);
        
    }
    
    @Test
    public void testLunarMonthlyRuleCase6() {
        Event calendar = new Event();
        calendar.setCalendarType(1);
        calendar.setStartDate(DateTimeUtils.parseDate("2014-09-25 00:00:00"));
        calendar.setRule("MONTHLY;BYMONTHDAY=2;");
        Rule rule = RuleFactory.createRule(calendar);
        
        assertTrue(DateTimeUtils.compareTo(rule.nextOccurDate(DateTimeUtils
            .parseDate("2014-9-20 00:00:00")), DateTimeUtils.parseDate("2014-9-25 00:00:00")) == 0);
        
        assertTrue(DateTimeUtils.compareTo(rule.nextOccurDate(DateTimeUtils
            .parseDate("2014-9-25 00:00:00")), DateTimeUtils.parseDate("2014-10-25 00:00:00")) == 0);
        
        assertTrue(DateTimeUtils.compareTo(rule.nextOccurDate(DateTimeUtils
            .parseDate("2014-10-25 00:00:00")), DateTimeUtils.parseDate("2014-11-23 00:00:00")) == 0);
        
        assertTrue(DateTimeUtils.compareTo(rule.nextOccurDate(DateTimeUtils
            .parseDate("2014-11-23 18:00:00")), DateTimeUtils.parseDate("2014-12-23 00:00:00")) == 0);
        
    }
    
    @Test
    public void testLunarYearlyRuleCase1() {
        Event calendar = new Event();
        calendar.setCalendarType(1);
        calendar.setStartDate(DateTimeUtils.parseDate("2013-06-16 00:00:00"));
        calendar.setRule("YEARLY;BYMONTH=5;BYDAY=2SU");
        Rule rule = RuleFactory.createRule(calendar);
        
        assertTrue(DateTimeUtils.compareTo(rule.nextOccurDate(DateTimeUtils
            .parseDate("2013-8-18 17:00:00")), DateTimeUtils.parseDate("2014-6-8 00:00:00")) == 0);
        
    }
    
    @Test
    public void testLunarYearlyRuleCase2() {
        Event calendar = new Event();
        calendar.setCalendarType(1);
        calendar.setStartDate(DateTimeUtils.parseDate("2013-06-16 00:00:00"));
        calendar.setRule("YEARLY;BYMONTH=5;BYMONTHDAY=9");
        Rule rule = RuleFactory.createRule(calendar);
        
        assertTrue(DateTimeUtils.compareTo(rule.nextOccurDate(DateTimeUtils
            .parseDate("2013-8-18 17:00:00")), DateTimeUtils.parseDate("2014-6-6 00:00:00")) == 0);
        
        assertTrue(DateTimeUtils.compareTo(rule.nextOccurDate(DateTimeUtils
            .parseDate("2014-6-6 00:00:00")), DateTimeUtils.parseDate("2015-6-24 00:00:00")) == 0);
        
    }
}
