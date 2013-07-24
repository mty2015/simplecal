package cnblogs.jcli.calendar;

import java.util.Date;

public class Event {
    /**
     * 日历类型: 1,公历 2,农历
     */
    private int calendarType;
    
    /**
     * 重复事件规则表达式
     */
    private String rule;
    
    /**
     * 事件发生的开始时间
     */
    private Date startDate;
    
    /**
     * 事件发生的结束时间
     */
    private Date endDate;
    
    public int getCalendarType() {
        return calendarType;
    }

    public void setCalendarType(int calendarType) {
        this.calendarType = calendarType;
    }

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

}
