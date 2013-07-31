package cnblogs.jcli.calendar.impl;

import java.util.Date;

import org.joda.time.DateTime;

import cnblogs.jcli.calendar.Event;
import cnblogs.jcli.calendar.field.ByDay;
import cnblogs.jcli.calendar.redis.CacheKeys;
import cnblogs.jcli.calendar.redis.JRedisClientFactory;
import cnblogs.jcli.calendar.utils.DateTimeUtils;

public class WeeklyRule extends AbstractRecurRule {

    public WeeklyRule(Event event) {
        super(event);
    }

    @Override
    public void loadOneCycleCache() {
        for (ByDay byDay : getByDaySet()) {
            Date day = new DateTime(curDay).withDayOfWeek(byDay.getWeekDay().getIndex()).toDate();
            
            //如果发生时间虽然在本周内,但是weekIndex已过期
            if(DateTimeUtils.compareTo(day, curDay) <= 0){
                continue;
            }
            
            JRedisClientFactory.getJRedisClient().zadd(CacheKeys.getRecurEventKey(event),
                    day.getTime(), String.valueOf(day.getTime()));
            times++;
        }
        
        //下一个周期
        curDay = DateTimeUtils.theWeekFirst(DateTimeUtils.plusWeeks(curDay, getInterval()));
    }


}
