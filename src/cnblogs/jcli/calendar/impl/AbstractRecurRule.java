package cnblogs.jcli.calendar.impl;

import java.util.Date;
import java.util.Set;

import org.joda.time.DateTime;

import cnblogs.jcli.calendar.Event;
import cnblogs.jcli.calendar.RuleFactory;
import cnblogs.jcli.calendar.field.ByDay;
import cnblogs.jcli.calendar.utils.DateTimeUtils;

public abstract class AbstractRecurRule extends AbstractRule{
    

    private Date until;
    private Integer count;
    private Integer interval;
    private Set<ByDay> byDaySet;
    private Set<Integer> byMonthDaySet;
    private Set<Integer> byYearDaySet;
    private Set<Integer> byWeekNoSet;
    private Set<Integer> byMonthSet;
    
    public AbstractRecurRule(final Event calendar){
        super(calendar);
        parse();
    }

    //解析rule字符串规则
    private void parse() {
        String ruleStr = calendar.getRule();
        //until
        until = RuleFactory.extractUntilDate(ruleStr);
        //count
        count = RuleFactory.extractCount(ruleStr);
        //interval
        interval = RuleFactory.extractInterval(ruleStr);
        //byDaySet
        byDaySet = RuleFactory.extractByDaySet(ruleStr);
        //byMonthDaySet
        byMonthDaySet = RuleFactory.extractByMonthDaySet(ruleStr);
        //byYearDaySet
        byYearDaySet = RuleFactory.extractByYearDaySet(ruleStr);
        //byWeekNoset
        byWeekNoSet = RuleFactory.extractByWeekNoSet(ruleStr);
        //byMonthSet
        byMonthSet = RuleFactory.extractByMonthSet(ruleStr);
    }
    
    @Override
    public boolean includes(Date theDate) {
        if(theDate == null){
            return false;
        }
        
        //如果小时和分相同, 则偏移一分钟, 避免临界值的时候计算到下一个周期内
        Date compareDate = DateTimeUtils.clearTime(theDate);
        if(new DateTime(compareDate).getMinuteOfDay() == getStartTime()){
            compareDate = DateTimeUtils.plusMinutes(compareDate, -1);
        }
        
        Date nextOccurDate = nextOccurDate(compareDate);
        return DateTimeUtils.isSameDay(theDate, nextOccurDate);
    }
    
    @Override
    public Date nextOccurDate(Date offsetDate) {
        //已过期时间
        if(isExpiredDate(offsetDate)){
            return null;
        }
        
        Date startDateTime = getStartDateTime();
        
        //早于开始时间,则直接返回事件的开始时间
        if(DateTimeUtils.compareTo(offsetDate, startDateTime) < 0){
            return startDateTime;
        }
        
        //下个周期内事件发生时间
        Date nextOccurDate = computeNextOccurDate(offsetDate);
        
        //再次检查计算出的时间是否已过期
        if(isExpiredDate(nextOccurDate)){
            nextOccurDate = null;
        }
        return nextOccurDate;
    }
    
    protected abstract Date computeNextOccurDate(Date offsetDate);
    
    @Override
    public Date getRecurEndDate() {
        Date recurEndDate = null;
        if(getUntil() != null){
            recurEndDate = getUntil();
        }else if(getCount() != null){
            recurEndDate = computeTheLastCountOccurDate();
        }
        return recurEndDate;
    }
    
    /**
     * 计算最出一次重复发生的时间.
     */
    protected abstract Date computeTheLastCountOccurDate();
    
    
    /**
     * 
     * @param date
     * @return
     */
    public boolean isExpiredDate(Date date){
        if(date == null){
            return true;
        }
        if(getRecurEndDate() == null){//永不结束事件情况
            return false;
        }
        return DateTimeUtils.compareTo(date, getRecurEndDate()) > 0;
    }
    
    public Event getCalendar() {
        return calendar;
    }

    public Date getUntil() {
        return until;
    }

    public Integer getCount() {
        return count;
    }

    public Integer getInterval() {
        if(interval == null){
            return 1;
        }
        return interval;
    }

    public Set<ByDay> getByDaySet() {
        return byDaySet;
    }

    public Set<Integer> getByMonthDaySet() {
        return byMonthDaySet;
    }

    public Set<Integer> getByYearDaySet() {
        return byYearDaySet;
    }

    public Set<Integer> getByWeekNoSet() {
        return byWeekNoSet;
    }

    public Set<Integer> getByMonthSet() {
        return byMonthSet;
    }
    
    public static void main(String[] args){
    }
}
