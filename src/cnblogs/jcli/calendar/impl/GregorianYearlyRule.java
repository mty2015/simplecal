package cnblogs.jcli.calendar.impl;

import java.util.Date;

import cnblogs.jcli.calendar.Event;
import cnblogs.jcli.calendar.utils.DateTimeUtils;

public class GregorianYearlyRule extends AbstractYearlyRule {

    public GregorianYearlyRule(Event calendar) {
        super(calendar);
    }

    @Override
    protected Date getNextIntervalYearFirst(Date theDay, int years) {
        return DateTimeUtils.plusYears(theDay, years);
    }

}
