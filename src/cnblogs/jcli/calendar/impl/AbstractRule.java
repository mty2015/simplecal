package cnblogs.jcli.calendar.impl;

import java.util.Date;

import org.joda.time.DateTime;

import cnblogs.jcli.calendar.Event;
import cnblogs.jcli.calendar.Rule;

public abstract class AbstractRule implements Rule{

    protected Event event;
    
    public AbstractRule(final Event event){
        this.event = event;
    }
    
    /**
     * 返回事件发生的开始日期, 不包括时间信息
     */
    public Date getStartDate(){
        return new DateTime(event.getStartDate()).withMillisOfDay(0).toDate();
    }
    
    /**
     * 返回事件发生的结束日期, 不包括时间信息
     */
    public Date getEndDate(){
        return new DateTime(event.getEndDate()).withMillisOfDay(0).toDate();
    }
    
    /**
     * 返回事件发生的开始时间, 以一天的分钟为单位时间,如 早上8:30,则返回 510
     */
    public Integer getStartTime(){
        return new DateTime(event.getStartDate()).getMinuteOfDay();
    }
    
    /**
     * 返回事件发生的结束时间, 以一天的分钟为单位时间,如 早上8:30,则返回 510
     */
    public Integer getEndTime(){
        return new DateTime(event.getEndDate()).getMinuteOfDay();
    }
    
    /**
     * 返回事件发生的开始日期和时间
     */
    public Date getStartDateTime(){
        return event.getStartDate();
    }
    
}
