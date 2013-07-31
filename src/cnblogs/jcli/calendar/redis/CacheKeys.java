package cnblogs.jcli.calendar.redis;

import cnblogs.jcli.calendar.Event;

public class CacheKeys {

    public static String getRecurEventKey(Event event){
        return "recure_event_list" + event.getId();
    }
    
}
