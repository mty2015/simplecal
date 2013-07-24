package cnblogs.jcli.calendar.utils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class LunarMap {

    private static final Map<Lunar, Date> data = new HashMap<Lunar, Date>();

    static {
        Date start = DateTimeUtils.parseDate("1950-01-01");
        Date end = DateTimeUtils.parseDate("2049-01-01");
        for (; DateTimeUtils.compareTo(start, end) < 0; start = DateTimeUtils.plusDays(start, 1)) {
            data.put(new Lunar(start), start);
        }
    }
    
    public static Date getDate(Lunar lunar){
        return data.get(lunar);
    }

}
