package cnblogs.jcli.calendar.impl;

import java.util.Date;

import cnblogs.jcli.calendar.Event;
import cnblogs.jcli.calendar.utils.DateTimeUtils;

public class GregorianMonthlyRule extends AbstractMonthlyRule {

    public GregorianMonthlyRule(Event calendar) {
        super(calendar);
    }

    @Override
    protected Date getNextOccurMonthFirst(Date offsetDate) {
        Date startDateTime = getStartDateTime();
        Date theMonthFirst = DateTimeUtils.theMonthFirst(startDateTime);
        int periodMonths =
                DateTimeUtils.periodMonths(DateTimeUtils.clearTime(theMonthFirst), offsetDate);
        int nextMonthIndex =
                periodMonths % getInterval() == 0 ? periodMonths : periodMonths + getInterval()
                        - periodMonths % getInterval();
        Date nextOccurMonthFirst = DateTimeUtils.plusMonths(theMonthFirst, nextMonthIndex);
        return nextOccurMonthFirst;
    }

    @Override
    protected Date getNextMonthFirst(Date offsetDate) {
        return DateTimeUtils
                .theNextMonthFirst(offsetDate);
    }
    
    @Override
    public Date computeTheLastCountOccurDate() {
        int months = (getCount() - 1) * getInterval();
        return DateTimeUtils.theMonthEnd(DateTimeUtils.plusMonths(getStartDateTime(), months));
    }

}
