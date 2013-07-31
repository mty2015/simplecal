package cnblogs.jcli.calendar.impl;

import java.util.Date;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cnblogs.jcli.calendar.Event;
import cnblogs.jcli.calendar.RuleException;
import cnblogs.jcli.calendar.field.ByDay;
import cnblogs.jcli.calendar.redis.CacheKeys;
import cnblogs.jcli.calendar.redis.JRedisClientFactory;

public abstract class AbstractMonthlyRule extends AbstractRecurRule {

    private Logger log = LoggerFactory.getLogger(AbstractMonthlyRule.class);
    
    protected AbstractMutliCalendarRuleHelper ruleHelper = new GregorianCalenarRuleHelper();

    public AbstractMonthlyRule(Event calendar) {
        super(calendar);
    }
    
    public AbstractMonthlyRule(Event calendar, AbstractMutliCalendarRuleHelper ruleHelper) {
        super(calendar);
        this.ruleHelper = ruleHelper;
    }

    @Override
    public void loadOneCycleCache() {
        Date day = ruleHelper.computeNextOccurMonthDay(this,curDay);
        
        if(day != null){
            JRedisClientFactory.getJRedisClient().zadd(CacheKeys.getRecurEventKey(event),
                day.getTime(), String.valueOf(day.getTime()));
            times++;
        }

        //下一个周期
        curDay = getNextIntervalMonthFirst(curDay, getInterval());
    }
    
    protected abstract Date getNextIntervalMonthFirst(Date theDay, int months);

}
