package cnblogs.jcli.calendar.impl;

import java.util.Date;

import org.joda.time.DateTime;

import cnblogs.jcli.calendar.utils.Lunar;
import cnblogs.jcli.calendar.utils.LunarMap;

public class LunarCalenarRuleHelper extends AbstractMutliCalendarRuleHelper {

    @Override
    protected int getMaxDayOfMonth(Date theDay) {
        Lunar lunarDay = new Lunar(theDay);
        return Lunar.monthDays(lunarDay.getYear(), lunarDay.getMonth());
    }

    @Override
    protected int getMonthFirstDayOfWeek(Date theDay) {
        Lunar theDayL = new Lunar(theDay);
        Lunar theMonthFirstDayL =
                new Lunar(theDayL.getYear(), theDayL.getMonth(), 1, theDayL.isLeap());
        return new DateTime(LunarMap.getDate(theMonthFirstDayL)).getDayOfWeek();
    }

    @Override
    protected int getMonthEndDayOfWeek(Date theDay) {
        Lunar theDayL = new Lunar(theDay);
        Lunar theMonthEndDayL =
                new Lunar(theDayL.getYear(), theDayL.getMonth(), Lunar.monthDays(theDayL.getYear(),
                        theDayL.getMonth()), theDayL.isLeap());
        return new DateTime(LunarMap.getDate(theMonthEndDayL)).getDayOfWeek();
    }

}
