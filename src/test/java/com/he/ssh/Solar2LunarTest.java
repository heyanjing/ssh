package com.he.ssh;

import com.he.ssh.base.core.YingYangConverter;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Created by heyanjing on 2017/12/23 15:17.
 */
public class Solar2LunarTest {
    private static final Logger log = LoggerFactory.getLogger(Solar2LunarTest.class);

    @Test
    public void x() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        LocalDate date = LocalDate.parse("20171222", dateTimeFormatter);
        LocalDate plusDays = date.plusDays(104);
        String format = plusDays.format(dateTimeFormatter);
        log.info(format);
    }

    @Test
    public void test() {
        YingYangConverter.yang2ying("20180216");

        //region Description
       /*

       LocalDate endDate = LocalDate.parse("20180216", dateTimeFormatter);

        long offset = Solar2LunarTest.getDifferenceDays(endDate);//
        int year = endDate.getYear();


        for (int i = MIN_YEAR; i <= MAX_YEAR; i++) {
            int temp = getYearDays(i);  //计算阴历年的总天数
            if (offset < temp) {
                break;
            }
            offset -= temp;
        }
        int leapMonth = getLeapMonth(year);//计算该年闰哪个月
        boolean isleapMonth = leapMonth > 0 ? true : false, actionleapMonth = false;//处理过闰年没

        int month = 0;
        for (int i = 1; i <= 12; i++) {
            int temp;
            if (isleapMonth && !actionleapMonth) {//闰月
                temp = getLeapMonthDays(year);
                actionleapMonth = true;
                i--;
            } else {
                temp = getMonthDays(year, i);
            }
            if (offset < temp) {//当前这个月跳出循环
                month = i;
                break;
            }
            offset -= temp;
        }

        log.info("农历{}年{}月{}   闰{}月", year, CHINESENUMBER[month-1], getChinaDay((int) offset), leapMonth);
        log.warn("{}{}年", getCyclicalm(year), getAnimals(year));
*/
        //endregion

    }
}
