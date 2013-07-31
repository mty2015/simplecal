package cnblogs.jcli.calendar.impl;

import java.util.Date;

import cnblogs.jcli.calendar.Event;
import cnblogs.jcli.calendar.utils.DateTimeUtils;

public class GregorianMonthlyRule extends AbstractMonthlyRule {

    public GregorianMonthlyRule(Event calendar) {
        super(calendar);
    }

    @Override
    protected Date getNextIntervalMonthFirst(Date theDay, int months) {
        return DateTimeUtils.theMonthFirst(DateTimeUtils.plusMonths(theDay, months));
    }


}
