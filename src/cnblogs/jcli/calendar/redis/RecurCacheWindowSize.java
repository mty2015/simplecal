package cnblogs.jcli.calendar.redis;

import java.util.Date;

public class RecurCacheWindowSize {
    
    private Date startDate;
    private Date endDate;
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
    
    
    public boolean inWindow(Date day){
        return day.getTime() >= startDate.getTime() && day.getTime() <= endDate.getTime();
    }
    
}
