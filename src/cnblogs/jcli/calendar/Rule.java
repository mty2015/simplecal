package cnblogs.jcli.calendar;

import java.util.Date;

public interface Rule {
    
    /**
     * 检测某一日期是否在在本次日历事件的提醒日期序列中.
     * 
     * @param theDate 待检测的日期
     * @return 
     */
    boolean includes(Date theDate);

    /**
     * 以某一时刻为基线, 计算当前日历事件的下次发生时间.
     * 
     * @param offsetDate 计算下次发生时间的基线时间
     * @return 如果返回null则表示基于该时间已结结束
     */
    Date nextOccurDate(Date offsetDate);

    /**
     * 日历事件不再发生的结束时间
     * 
     * @return 如果返回<code>null</code>则表示永不结束
     */
    Date getRecurEndDate();
}
