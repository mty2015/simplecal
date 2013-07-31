package cnblogs.jcli.calendar.impl;

import java.util.Date;

import org.joda.time.DateTime;

import cnblogs.jcli.calendar.Event;
import cnblogs.jcli.calendar.utils.Lunar;
import cnblogs.jcli.calendar.utils.LunarMap;

public class LunarYearlyRule extends AbstractYearlyRule {

    public LunarYearlyRule(Event calendar) {
        super(calendar);
    }

    @Override
    protected Date getNextIntervalYearFirst(Date theDay, int years) {
        Lunar theDayL = new Lunar(theDay);
        int year = theDayL.getYear() + years;
        int month = getByMonthSet().iterator().next();
        Lunar theNextYearMonthFirstL = new Lunar(year, month, 1, false);
        return new DateTime(LunarMap.getDate(theNextYearMonthFirstL)).withMillisOfDay(getStartTime() * 60 * 1000).toDate();
    }


}
