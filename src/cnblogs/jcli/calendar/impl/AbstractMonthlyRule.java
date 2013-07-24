package cnblogs.jcli.calendar.impl;

import java.util.Date;

import cnblogs.jcli.calendar.Event;
import cnblogs.jcli.calendar.utils.DateTimeUtils;
import cnblogs.jcli.calendar.utils.RuleConst;

public abstract class AbstractMonthlyRule extends AbstractRecurRule {

    protected AbstractMutliCalendarRuleHelper ruleHelper = new GregorianCalenarRuleHelper();

    public AbstractMonthlyRule(Event calendar) {
        super(calendar);
    }

    public AbstractMonthlyRule(Event calendar, AbstractMutliCalendarRuleHelper ruleHelper) {
        super(calendar);
        this.ruleHelper = ruleHelper;
    }

    @Override
    public Date computeNextOccurDate(Date offsetDate) {
        return computeNextOccurDateHelper(offsetDate, 1);
    }

    private Date computeNextOccurDateHelper(Date offsetDate, int retry) {
        System.out.println(retry);
        if (retry > RuleConst.MAX_RETRY) {
            return null;
        }
        Date nextOccurMonthFirst = getNextOccurMonthFirst(offsetDate);

        Date nextOccurDate = ruleHelper.computeNextOccurMonthDay(this, nextOccurMonthFirst);

        boolean isRetry =
                (nextOccurDate == null) || (DateTimeUtils.compareTo(nextOccurDate, offsetDate) < 0)
                        || (DateTimeUtils.compareTo(nextOccurDate, offsetDate) == 0 && retry == 1);

        if (isRetry) {
            nextOccurDate =
                    computeNextOccurDateHelper(DateTimeUtils
                            .clearTime(getNextMonthFirst(offsetDate)), ++retry);
        } 

        return nextOccurDate;
    }

    /**
     * 计算当前时间的下一个月的第一天
     * 
     * @param theDay 基准时间
     * @return 返回基准时间的下一个月的第一天 , 精度度到天
     */
    protected abstract Date getNextMonthFirst(Date theDay);

    /**
     * 下次发生的时间
     * 
     * @param theDay
     * @return 返回重复事件下次发生的时间,注意精确度到分.
     */
    protected abstract Date getNextOccurMonthFirst(Date theDay);
}
