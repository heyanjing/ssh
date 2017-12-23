package com.he.ssh.base.core;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

/**
 * 阳历转阴历
 * 阴历转阳历
 * Created by heyanjing on 2017/12/24 0:01.
 * ▪ 春节 ( 农历正月初一)	▪ 元宵节 ( 农历正月十五)	▪ 头牙 ( 农历二月初二)
 * ▪ 寒食节 ( 清明节前一天)	▪ 清明节 ( 节气清明)	▪ 端午节 ( 农历五月初五)
 * ▪ 七夕 ( 农历七月初七)	▪ 中元节 ( 农历七月十五)	▪ 中秋节 ( 农历八月十五)
 * ▪ 重阳节 ( 农历九月九)	▪ 冬至 ( 节气冬至)	▪ 腊八节 ( 农历腊月初八)
 * ▪ 尾牙 ( 农历腊月十六)	▪ 祭灶 ( 农历腊月廿四)	▪ 除夕 ( 农历十二月卅日)
 */
public class CalenderConverter {
    private static final Logger log = LoggerFactory.getLogger(CalenderConverter.class);
    /**
     * 农历对应的法定假日
     */
    public final static String[] HOLIDAYS = {"0101", "0102", "0103", "0505", "0815"};
    /**
     * 计算阴历日期参照1900年到2049年
     * 然后解释一下表中的数据，拿第一个0x04bd8来说吧，他是5个16进制数，共20bit。最后四位，
     * 即8，代表该年闰月的月份，为0则没有闰月。前四位，即0，在该年有闰月才有意义，为0表示闰月29天，
     * 为1表示闰月30天。中间12位，即4bd代表该年12个月每个月的天数，0表示29天，1表示30天。
     * 然后就是根据阳历的1900年1月31日是阴历的1900年的正月初一，然后查表得出阴历和阳历的关系。
     */
    public final static int[] LUNAR_INFO = {
            0x04bd8, 0x04ae0, 0x0a570, 0x054d5, 0x0d260, 0x0d950, 0x16554, 0x056a0, 0x09ad0, 0x055d2,//1909
            0x04ae0, 0x0a5b6, 0x0a4d0, 0x0d250, 0x1d255, 0x0b540, 0x0d6a0, 0x0ada2, 0x095b0, 0x14977,//1919
            0x04970, 0x0a4b0, 0x0b4b5, 0x06a50, 0x06d40, 0x1ab54, 0x02b60, 0x09570, 0x052f2, 0x04970,
            0x06566, 0x0d4a0, 0x0ea50, 0x06e95, 0x05ad0, 0x02b60, 0x186e3, 0x092e0, 0x1c8d7, 0x0c950,
            0x0d4a0, 0x1d8a6, 0x0b550, 0x056a0, 0x1a5b4, 0x025d0, 0x092d0, 0x0d2b2, 0x0a950, 0x0b557,
            0x06ca0, 0x0b550, 0x15355, 0x04da0, 0x0a5d0, 0x14573, 0x052d0, 0x0a9a8, 0x0e950, 0x06aa0,
            0x0aea6, 0x0ab50, 0x04b60, 0x0aae4, 0x0a570, 0x05260, 0x0f263, 0x0d950, 0x05b57, 0x056a0,
            0x096d0, 0x04dd5, 0x04ad0, 0x0a4d0, 0x0d4d4, 0x0d250, 0x0d558, 0x0b540, 0x0b5a0, 0x195a6,
            0x095b0, 0x049b0, 0x0a974, 0x0a4b0, 0x0b27a, 0x06a50, 0x06d40, 0x0af46, 0x0ab60, 0x09570,
            0x04af5, 0x04970, 0x064b0, 0x074a3, 0x0ea50, 0x06b58, 0x055c0, 0x0ab60, 0x096d5, 0x092e0,//1999
            0x0c960, 0x0d954, 0x0d4a0, 0x0da50, 0x07552, 0x056a0, 0x0abb7, 0x025d0, 0x092d0, 0x0cab5,//2009
            0x0a950, 0x0b4a0, 0x0baa4, 0x0ad50, 0x055d9, 0x04ba0, 0x0a5b0, 0x15176, 0x052b0, 0x0a930,
            0x07954, 0x06aa0, 0x0ad50, 0x05b52, 0x04b60, 0x0a6e6, 0x0a4e0, 0x0d260, 0x0ea65, 0x0d530,
            0x05aa0, 0x076a3, 0x096d0, 0x04bd7, 0x04ad0, 0x0a4d0, 0x1d0b6, 0x0d250, 0x0d520, 0x0dd45,
            0x0b5a0, 0x056d0, 0x055b2, 0x049b0, 0x0a577, 0x0a4b0, 0x0aa50, 0x1b255, 0x06d20, 0x0ada0
    };
    /**
     * 阳历日期计算起点
     */
    public final static String START_DATE = "19000130";

    /**
     * 日期格式化样式
     */
    public final static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");

    /**
     * 最小年份
     */
    public final static int MIN_YEAR = 1900;

    /**
     * 最大年份
     */
    public final static int MAX_YEAR = 2049;
    /**
     * 农历月份的表达
     */
    final static String CHINESENUMBER[] = {"正", "二", "三", "四", "五", "六", "七", "八", "九", "十", "十一", "腊"};
    final static String CHINESENUMBER1[] = {"一", "二", "三", "四", "五", "六", "七", "八", "九", "十", "十一", "十二"};
    /**
     * 生肖
     */
    final static String[] ANIMALS = new String[]{"鼠", "牛", "虎", "兔", "龙", "蛇", "马", "羊", "猴", "鸡", "狗", "猪"};

    /**
     * 天干地支
     */
    final static String[] GAN = new String[]{"甲", "乙", "丙", "丁", "戊", "己", "庚", "辛", "壬", "癸"};
    final static String[] ZHI = new String[]{"子", "丑", "寅", "卯", "辰", "巳", "午", "未", "申", "酉", "戌", "亥"};

    /**
     * date与1900年1月30日相差的天数
     */
    public static long getDifferenceDays(LocalDate date) {
        LocalDate startDate = LocalDate.parse(START_DATE, dateTimeFormatter);
        return ChronoUnit.DAYS.between(startDate, date);
    }

    /**
     * 计算阴历 {@code year}年闰哪个月 1-12 , 没闰传回 0
     *
     * @param year 阴历年
     * @return (int)月份
     * @author liu 2015-1-5
     */
    public static int getLeapMonth(int year) {
        return (int) (LUNAR_INFO[year - 1900] & 0xf);
    }

    /**
     * 计算阴历年闰月多少天
     *
     * @param year 阴历年
     * @return (int)天数
     */
    public static int getLeapMonthDays(int year) {
        if (getLeapMonth(year) != 0) {
            if ((LUNAR_INFO[year - 1900] & 0xf0000) != 0) {
                return 30;
            } else {
                return 29;
            }
        } else {
            return 0;
        }
    }

    /**
     * 计算阴历年的总天数
     *
     * @param year 阴历年
     * @return (int)总天数
     */
    public static int getYearDays(int year) {
        int sum = 29 * 12;
        for (int i = 0x8000; i >= 0x8; i >>= 1) {
            if ((LUNAR_INFO[year - 1900] & 0xfff0 & i) != 0) {
                sum++;
            }
        }
        return sum + getLeapMonthDays(year);
    }

    /**
     * 计算阴历{@code lunarYeay}年{@code month}月的天数
     *
     * @param lunarYeay 阴历年
     * @param month     阴历月
     * @return (int)该月天数
     * @throws Exception
     * @author liu 2015-1-5
     */
    public static int getMonthDays(int lunarYeay, int month) {
        if ((month > 31) || (month < 0)) {
//            throw (new Exception("月份有错！"));
        }
        // 0X0FFFF[0000 {1111 1111 1111} 1111]中间12位代表12个月，1为大月，0为小月
        int bit = 1 << (16 - month);
        if (((LUNAR_INFO[lunarYeay - 1900] & 0x0FFFF) & bit) == 0) {
            return 29;
        } else {
            return 30;
        }
    }

    /**
     * 获取生肖
     *
     * @param year
     * @return
     */
    public static String getAnimals(int year) {
        return ANIMALS[(year - 4) % 12];
    }

    /**
     * 获取天干地支
     *
     * @param year
     * @return
     */
    public static String getCyclicalm(int year) {
        int num = year - 1900 + 36;
        return (GAN[num % 10] + ZHI[num % 12]);
    }

    /**
     * 农历的称呼
     *
     * @param day
     * @return
     */
    public static String getChinaDay(int day) {
        String result = day == 10 ? "初十" : day == 20 ? "二十" : day == 30 ? "三十" : "";
        if (StringUtils.isNotEmpty(result)) {
            return result;
        }
        int prefixes = (int) ((day) / 10);
        result = prefixes == 0 ? "初" : prefixes == 1 ? "十" : prefixes == 2 ? "廿" : prefixes == 3 ? "三" : "";
        int suffix = (int) (day % 10);
        result += suffix == 1 ? "一" : suffix == 2 ? "二" : suffix == 3 ? "三" : suffix == 4 ? "四" : suffix == 5 ? "五" : suffix == 6 ? "六" : suffix == 7 ? "七" : suffix == 8 ? "八" : "九";
        return result;
    }

    /**
     * 阳历转阴历
     *
     * @param date
     * @return
     */
    public static String yang2ying(LocalDate date) {
        long offset = getDifferenceDays(date);//
        int year = date.getYear();


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
        log.info("农历{}年{}月{}   闰{}月", year, CHINESENUMBER[month - 1], getChinaDay((int) offset), leapMonth);
        log.info("{}{}年", getCyclicalm(year), getAnimals(year));
        String monthStr = month + "".length() > 1 ? month + "" : "0" + month;
        String dayStr = offset + "".length() > 0 ? offset + "" : "0" + offset;
        log.info(year + monthStr + dayStr);
        return year + monthStr + dayStr;
    }

    public static String yang2ying(String date) {
        return yang2ying(LocalDate.parse(date, dateTimeFormatter));
    }

    /**
     * 阴历转阳历
     *
     * @param date        20171107
     * @param isLeapMonth 当前日期是否是闰月日期
     * @return
     */
    public static String ying2yang(String date, boolean isLeapMonth) {
        LocalDate localDate = LocalDate.parse(date, dateTimeFormatter);//这样转换只是为了得到输入日期的年月日
        int lunarYear = localDate.getYear();
        int lunarMonth = localDate.getMonth().getValue();
        int lunarDay = localDate.getDayOfMonth();

        int offset = 0;
        for (int i = MIN_YEAR; i < lunarYear; i++) {
            int yearDaysCount = getYearDays(i); // 求阴历某年天数
            offset += yearDaysCount;
        }
        int leapMonth = getLeapMonth(lunarYear);

        for (int i = 1; i < lunarMonth; i++) {
            offset += getMonthDays(lunarYear, i);
        }
        if (leapMonth != 0) {
            if (lunarMonth > leapMonth) {//大于闰月
                offset += getLeapMonthDays(lunarYear);
            } else if (lunarMonth == leapMonth && isLeapMonth) {//日期是润月日期
                offset += getMonthDays(lunarYear, lunarMonth);
            }
        }
        offset += lunarDay; // 加上当月的天数
        LocalDate startDate = LocalDate.parse(START_DATE, dateTimeFormatter);
        localDate = startDate.plusDays(offset);
        return localDate.format(dateTimeFormatter);
    }

    public static String ying2yang(String date) {
        return ying2yang(date, false);
    }

}
