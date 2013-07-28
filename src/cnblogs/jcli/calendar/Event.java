package cnblogs.jcli.calendar;

import java.util.Date;

/**
 * 日历事件/活动.
 * 
 *
 * @author tony.li.fly@gmail.com
 */
public class Event {
	/**
	 * 事件标题
	 */
	private String title;
	
	/**
	 * 事件描述
	 */
	private String description;
	
	/**
	 * 事件发生地点
	 */
	private String location;
	
	 /**
     * 事件发生的开始时间
     */
    private Date startDate;
    
    /**
     * 事件发生的结束时间
     */
    private Date endDate;
	
    /**
     * 日历类型: 1,公历 2,农历
     */
    private int calendarType;
    
    /**
     * 重复事件规则表达式
     */
    private String rule;
    
    
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

    
}
