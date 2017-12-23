package com.he.ssh.test;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Calendar;

/**
 * Created by heyanjing on 2017/12/23 14:43.
 */
public class YinYangConvert {
    private static final Logger log = LoggerFactory.getLogger(YinYangConvert.class);

    @Test
    public void test2() throws Exception {
        Calendar today = Calendar.getInstance();
        today.setTime(new java.util.Date());//加载当前日期
        //today.setTime(chineseDateFormat.parse("2006年10月30日"));//加载自定义日期 Lunar
        Lunar lunar = new Lunar(today);
        System.out.print(lunar.cyclical() + "年");//计算输出阴历年份
        System.out.println(lunar.toString());//计算输出阴历日期
        System.out.println(lunar.animalsYear());//计算输出属相 System.out.println(new
//        java.sql.Date(today.getTime().getTime()));//输出阳历日期
        System.out.println("星期" + lunar.getChinaWeekdayString(today.getTime().toString().substring(0, 3)));//计算输出星期几 }
        log.info("{}", lunar);
    }

    @Test
    public void test() throws Exception {
//        System.out.println(CalendarUtil.lunarToSolar("19901204", false));
//        System.out.println(CalendarUtil.lunarToSolar("19841021", true));
//        System.out.println("************");
        System.out.println(CalendarUtil.solarToLunar("19000923"));
//        System.out.println(CalendarUtil.solarToLunar("19000924"));
//        System.out.println(CalendarUtil.solarToLunar("19001022"));
//        System.out.println(CalendarUtil.solarToLunar("19001023"));
//
//        System.out.println(CalendarUtil.solarToLunar("19900630"));
//        System.out.println(CalendarUtil.solarToLunar("19841213"));
//        System.out.println(CalendarUtil.solarToLunar("19910119"));
    }
}
