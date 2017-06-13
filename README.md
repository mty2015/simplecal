simplecal
=========

日历计算工具类，可以满足类似Google日历，QQ日历的一些功能。

参考 [RFC2445协议](https://tools.ietf.org/html/rfc2445#section-4.3.10) 设计：

  * freq : 事件重复频率，有效值：DAILY(按天)，WEEKLY(按周)，MONTHLY(按月)，YEARLY(按年)
  * UNTIL: 重复结束日期 格式：20130102T170000Z(2013-1-2 下午5点结束)
  * COUNT: 重复多少次后结束，该字段与UNTIL两者只有出现一次
  * INTERVAL: 事件重复的间隔，如按天重复时，INTERVAL=2,则表明每2天重复一次，默认值 为1
  * BYDAY: 表示一周的某一天，有效值：MO(周一),TU(周二),WE(周三),TH(周四),FR(周五),SA(周六),SU(周日) ， 示     例： BYDAY=MO,TH,SU 表示重复日期包括周一，周四，周日. 每个值前面可以用 ”+”, “-” 修饰,表示第几个和倒数第几个日子,如 BYDAY = 2MO 表示第2个星期一发生; BYDAY=MO,-1SU 表示每个星期一和最后一个星期日发生
  * BYMONTHDAY: 表示一月的第几天发生,有效值是 [1 ~ 31] 和 [-31 ~ -1] ,如: BYMONTHDAY=2,18 表示一月的第2天,第18天发生; BYMONTHDAY=-1 表示一月的最后一天
  * BYYEARDAY: 表示一年的第几天发生,有效值是 [1 ~ 366] 和 [-366 ~ -1], 如 BYYEARDAY=125 表示一年的第125年发生; BYYEARDAY=-1 表示一年的最后一天发生
  * BYWEEKNO: 表示一年的第几周发生, 有效值是 [1 ~ 53] 和 [-53 ~ -1], 如 BYWEEKNO=2,23 表示一年的第2周,第23周发生
  * BYMONTH: 表示一年中的第几个月发生, 有效值是 [1 ~ 12]
  
  
  详细设计文档，参考 [日历设计之重复事件规则设计](http://www.cnblogs.com/jcli/p/calendar_recur_rule.html)。
