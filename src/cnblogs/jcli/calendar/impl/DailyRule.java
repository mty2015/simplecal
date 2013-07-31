package cnblogs.jcli.calendar.impl;

import cnblogs.jcli.calendar.Event;
import cnblogs.jcli.calendar.redis.CacheKeys;
import cnblogs.jcli.calendar.redis.JRedisClientFactory;
import cnblogs.jcli.calendar.utils.DateTimeUtils;

public class DailyRule extends AbstractRecurRule {


    public DailyRule(Event calendar) {
        super(calendar);
    }

    @Override
    public void loadOneCycleCache() {
        JRedisClientFactory.getJRedisClient().zadd(CacheKeys.getRecurEventKey(event),
            curDay.getTime(), String.valueOf(curDay.getTime()));
        
        curDay = DateTimeUtils.plusDays(curDay, 1);
        times++;
        
    }

}
