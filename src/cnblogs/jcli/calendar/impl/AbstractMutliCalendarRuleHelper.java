package cnblogs.jcli.calendar.impl;

import java.util.Date;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cnblogs.jcli.calendar.RuleException;
import cnblogs.jcli.calendar.field.ByDay;

public abstract class AbstractMutliCalendarRuleHelper{

    private Logger log = LoggerFactory.getLogger(AbstractMutliCalendarRuleHelper.class);
    
    
    protected Date computeNextOccurMonthDay(AbstractRecurRule rule, Date nextOccurMonthFirst) {
        Date nextOccurDate = null;
        if (!rule.getByDaySet().isEmpty()) {// byDay
            ByDay byDay = rule.getByDaySet().iterator().next();
            try {
                nextOccurDate = computeDayByDay(nextOccurMonthFirst, byDay);
            } catch (RuleException ex) {
                if (log.isDebugEnabled()) {
                    log.debug(ex.getMessage());
                }
            }
        } else {// byMonthDay
            try {
                nextOccurDate =
                        computeDayByMonthDay(nextOccurMonthFirst, rule.getByMonthDaySet().iterator().next());
            } catch (RuleException ex) {
                if (log.isDebugEnabled()) {
                    log.debug(ex.getMessage());
                }
            }
        }
        return nextOccurDate;
    }
    
    private Date computeDayByMonthDay(Date theDay, Integer byMonthDay) {
        int days;
        int maxDays = getMaxDayOfMonth(theDay);
        if(byMonthDay > 0){
            days = byMonthDay - 1;
        }else{
            days = maxDays + byMonthDay;
        }
        validateDayInMonth(days, maxDays);
        return new DateTime(theDay).plusDays(days).toDate();
    }

    protected abstract int getMaxDayOfMonth(Date theDay);

    private Date computeDayByDay(Date theDay,ByDay byDay) {
        int days;
        int monthFirstDayOfWeek = getMonthFirstDayOfWeek(theDay);
        int monthEndDayOfWeek = getMonthEndDayOfWeek(theDay);
        Integer qualifier = byDay.getQualifier();
        int dayOfWeek = byDay.getWeekDay().getIndex();
        int maxDays = getMaxDayOfMonth(theDay);
        if(qualifier > 0){
            if(dayOfWeek >= monthFirstDayOfWeek){
                days = (qualifier - 1) * 7 + (dayOfWeek - monthFirstDayOfWeek);
            }else{
                days = (7 + dayOfWeek - monthFirstDayOfWeek) + (qualifier - 1) * 7;
            }
        }else{
            int reverseDays = 0;
            if(dayOfWeek <= monthEndDayOfWeek){
                reverseDays = (-qualifier - 1) * 7 + (monthEndDayOfWeek - dayOfWeek);
            }else{
                reverseDays = (7 - (dayOfWeek - monthEndDayOfWeek)) + (-qualifier - 1) * 7;
            }
            days = maxDays - reverseDays - 1;
        }
        
        validateDayInMonth(days, maxDays);
        return new DateTime(theDay).plusDays(days).toDate();
    }

    protected abstract int getMonthFirstDayOfWeek(Date theDay) ;
    
    protected abstract int getMonthEndDayOfWeek(Date theDay) ;

    private void validateDayInMonth(int days, int maxDays) {
        //超过这个月的最大天数
        if(days < 0 || days > maxDays - 1){
            throw new RuleException(days + " days is out of the max value of the month: " + maxDays);
        }
    }
    
}
