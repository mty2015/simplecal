package cnblogs.jcli.calendar.impl;

import java.util.Date;

import org.joda.time.DateTime;

import cnblogs.jcli.calendar.Event;
import cnblogs.jcli.calendar.utils.Lunar;
import cnblogs.jcli.calendar.utils.LunarMap;

public class LunarMonthlyRule extends AbstractMonthlyRule {

    public LunarMonthlyRule(Event calendar) {
        super(calendar);
        this.ruleHelper = new LunarCalenarRuleHelper();
    }

    @Override
    protected Date getNextIntervalMonthFirst(Date theDay, int months) {
        Date day = theDay;
        int i = 0;
        while(i++ < months){
            Lunar theDayL = new Lunar(day).nextMonthFirst();
            day =  new DateTime(LunarMap.getDate(theDayL)).toDate();
        }
        return theDay;
    }

}
