package cnblogs.jcli.calendar.impl;

import java.util.Date;

import org.joda.time.DateTime;

import cnblogs.jcli.calendar.Event;
import cnblogs.jcli.calendar.utils.DateTimeUtils;

public class GregorianYearlyRule extends AbstractYearlyRule {

    public GregorianYearlyRule(Event calendar) {
        super(calendar);
    }


    @Override
    protected Date getNextYearFirst(Date offsetDate) {
        return DateTimeUtils
                .theNextYearFirst(offsetDate);
    }

    @Override
    protected Date getNextYearMonthFirst(Date offsetDate) {
        DateTime offSetDt = new DateTime(offsetDate);
        DateTime startTime = new DateTime(getStartDateTime());
        int year = offSetDt.getYear() + (offSetDt.getYear() - startTime.getYear()) % getInterval();
        int month = getByMonthSet().iterator().next();
        
        Date nextOccurMonthFirst = new DateTime(year, month, 1, getStartTime()/60, getStartTime()%60).toDate();
        
        return nextOccurMonthFirst;
    }

    @Override
    public Date computeTheLastCountOccurDate() {
        int years = (getCount() - 1) * getInterval();
        return DateTimeUtils.theYearEnd(DateTimeUtils.plusYears(getStartDateTime(), years));
    }


}
