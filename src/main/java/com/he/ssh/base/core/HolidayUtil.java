package com.he.ssh.base.core;

import com.he.ssh.base.bean.HolidayBean;
import com.he.ssh.base.core.enumm.HolidayEnum;
import com.he.ssh.entity.Holiday;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by heyanjing on 2017/12/22 17:06.
 * 新年，放假1天（1月1日）；
 * 春节，放假3天（农历正月初一、初二、初三）；
 * 清明节，放假1天（阳历清明当日）；
 * 劳动节，放假1天（5月1日）；
 * 端午节，放假1天（农历端午当日）；
 * 中秋节，放假1天（农历中秋当日）；
 * 国庆节，放假3天（10月1日、2日、3日）。
 */
public class HolidayUtil {
    private static final Logger log = LoggerFactory.getLogger(HolidayUtil.class);
    public static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
    /**
     * 阳历对应的法定假日
     */
    public final static String[] HOLIDAYSYANG = {"0101", "0501", "1001", "1002", "1003"};
    /**
     * 农历对应的法定假日
     */
    public final static String[] HOLIDAYSYYING = {"0101", "0102", "0103", "0505", "0815"};


    /**
     * @param start 开始日期
     * @param end   结束日期
     * @return 返回从开始到结束日期中的所有日期，包含开始、结束日期
     */
    public static List<LocalDate> getAllDays(LocalDate start, LocalDate end) {
        log.info("开始日期为{}", start.format(dateTimeFormatter));
        log.info("结束日期为{}", end.format(dateTimeFormatter));
        List<LocalDate> localDateList = Guava.newArrayList();
        LocalDate temp = start.minusDays(1);
        int over = 0;
        while (true) {
            int oldValue = temp.getMonth().getValue();
            temp = temp.plusDays(1);
            over++;
            int newValue = temp.getMonth().getValue();
            if (newValue > oldValue) {
                log.warn("{}月份日期如上", oldValue);
            }

            log.info("添加日期{}", temp.format(dateTimeFormatter));
            localDateList.add(temp);
            if (temp.equals(end)) {
                log.warn("{}月份日期如上", newValue);
                break;
            }
            if (over > 365) {
                break;
            }
        }
        return localDateList;
    }

    /**
     * @param year                 年份
     * @param legalHolidayPlanMap  节假日日期字符串对应的枚举
     * @param legalHolidayMap      法定节假日的map
     * @param holidays             所有的法定节假日日期字符串
     * @param legalHolidayPlanList 所有的节假日日期字符串
     * @param workStillList        所有的节假日时间安排周末上班对应的日期字符串
     * @return 返回该年度每一天的实体
     */
    public static List<Holiday> getAllHoliday(String year, Map<String, HolidayEnum> legalHolidayPlanMap, Map<String, HolidayEnum> legalHolidayMap, List<String> holidays, List<String> legalHolidayPlanList, List<String> workStillList) {
        List<Holiday> holidayList = Guava.newArrayList();
        String startDate = year + "0101";
        String endDate = year + "1231";
        LocalDate start = LocalDate.parse(startDate, dateTimeFormatter);
        LocalDate end = LocalDate.parse(endDate, dateTimeFormatter);
        if (start.isBefore(end)) {
            LocalDateTime now = LocalDateTime.now();
            List<LocalDate> allDays = HolidayUtil.getAllDays(start, end);
            int totalDays = allDays.size();
            List<String> finalHolidays = holidays;
            legalHolidayPlanList.forEach(str -> {
                if (str.startsWith((Integer.parseInt(year) - 1) + "")) {
                    allDays.add(LocalDate.parse(str, dateTimeFormatter));
                }
            });
            allDays.forEach(nowDate -> {
                DayOfWeek dayOfWeek = nowDate.getDayOfWeek();
                String nowStr = nowDate.format(dateTimeFormatter);
                Holiday holiday = new Holiday(nowStr, nowDate, totalDays);
                holiday.setCreateDateTime(now);
                holiday.setUpdateDateTime(now);

                if (legalHolidayPlanList.contains(nowStr)) {
                    log.error("{}是法定节假日期间", nowDate.format(dateTimeFormatter));
                    HolidayEnum type = null;
                    if (finalHolidays.contains(nowStr)) {
                        type = legalHolidayMap.get(nowStr);
                    } else {
                        type = HolidayEnum.jjr;
                    }
                    holiday.setType(type.getValue());
                    holiday.setTypeStr(type.getText());

                    HolidayEnum holidayEnum = legalHolidayPlanMap.get(nowStr);
                    holiday.setHolidayType(holidayEnum.getValue());
                    holiday.setHolidayTypeStr(holidayEnum.getText());
                } else {
                    if (dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY) {
                        if (workStillList.contains(nowStr)) {
                            log.error("{}是调休工作日", nowDate.format(dateTimeFormatter));
                            holiday.setType(HolidayEnum.txgzr.getValue());
                            holiday.setTypeStr(HolidayEnum.txgzr.getText());
                            HolidayEnum holidayEnum = legalHolidayPlanMap.get(nowStr);
                            holiday.setHolidayType(holidayEnum.getValue());
                            holiday.setHolidayTypeStr(holidayEnum.getText());
                        } else {
                            log.error("{}是休息日", nowDate.format(dateTimeFormatter));
                            holiday.setType(HolidayEnum.xxr.getValue());
                            holiday.setTypeStr(HolidayEnum.xxr.getText());
                        }
                    } else {
                        log.error("{}是工作日", nowDate.format(dateTimeFormatter));
                        holiday.setType(HolidayEnum.gzr.getValue());
                        holiday.setTypeStr(HolidayEnum.gzr.getText());
                    }
                }
                holidayList.add(holiday);
            });
        } else {
            log.warn("开始日期应该小于结束日期");
        }
        return holidayList;
    }

    /**
     * @param year                年份
     * @param workStill           所有的节假日时间安排周末上班bean
     * @param legalHolidayPlanMap 节假日日期字符串对应的枚举
     * @return 所有的节假日时间安排周末上班对应的日期字符串
     */
    public static List<String> getWorkStill(String year, List<HolidayBean> workStill, Map<String, HolidayEnum> legalHolidayPlanMap) {
        List<String> workStillList2017 = Guava.newArrayList();
        for (int i = 0; i < workStill.size(); i++) {
            HolidayBean holidayBean = workStill.get(i);
            HolidayEnum holidayType = holidayBean.getHolidayType();
            String dayRange = holidayBean.getDayRange();
            String localDateStr = year + dayRange;
            workStillList2017.add(localDateStr);
            legalHolidayPlanMap.put(localDateStr, holidayType);
        }
        return workStillList2017;
    }

    /**
     * @param year                年份
     * @param legalHolidayPlan    所有的节假日时间安排bean
     * @param legalHolidayPlanMap 节假日日期字符串对应的枚举
     * @return 所有的节假日时间安排对应的日期字符串
     */
    public static List<String> getAllPlanHolidays(String year, List<HolidayBean> legalHolidayPlan, Map<String, HolidayEnum> legalHolidayPlanMap) {
        List<String> legalHolidayPlanList2017 = Guava.newArrayList();//计划放假的所有日期字符串
        for (int i = 0; i < legalHolidayPlan.size(); i++) {
            HolidayBean holidayBean = legalHolidayPlan.get(i);
            HolidayEnum holidayType = holidayBean.getHolidayType();
            String dayRange = holidayBean.getDayRange();
            if (dayRange.contains("-")) {
                String[] rangeDay = dayRange.split("-");
                String startStr = rangeDay[0];
                String startDay = null;
                if (startStr.startsWith("12")) {
                    startDay = (Integer.parseInt(year) - 1) + rangeDay[0];
                } else {
                    startDay = year + rangeDay[0];
                }
                String endDay = year + rangeDay[1];
                LocalDate start = LocalDate.parse(startDay, dateTimeFormatter);
                LocalDate end = LocalDate.parse(endDay, dateTimeFormatter);
                if (start.isBefore(end)) {
                    LocalDate temp = start.minusDays(1);
                    int over = 0;
                    while (true) {
                        temp = temp.plusDays(1);
                        String localDateStr = temp.format(dateTimeFormatter);
                        legalHolidayPlanList2017.add(localDateStr);
                        legalHolidayPlanMap.put(localDateStr, holidayType);
                        over++;
                        if (temp.equals(end)) {
                            break;
                        }
                        if (over > 365) {
                            break;
                        }
                    }
                } else {
                    log.warn("日期范围开始日期应该小于结束日期");
                }
            } else {
                String localDateStr = year + dayRange;
                legalHolidayPlanList2017.add(localDateStr);
                legalHolidayPlanMap.put(localDateStr, holidayType);
            }
        }
        return legalHolidayPlanList2017;
    }

    /**
     * @param qmStr        清明节日期字符串
     * @param holidaysYing 农历法定假日对应的阳历日期
     * @param holidaysYang 阳历法定假日对应的日期
     * @return 所有的法定节假日
     */
    public static List<String> getAllHolidays(String qmStr, List<String> holidaysYing, List<String> holidaysYang) {
        List<String> holidays = Guava.newArrayList();
        holidays.addAll(holidaysYing);
        holidays.addAll(holidaysYang);
        holidays.add(qmStr);

        holidays = holidays.stream().sorted().collect(Collectors.toList());
        return holidays;
    }

    /**
     * @param year            年份
     * @param legalHolidayMap 法定节假日的map
     * @return 阳历法定假日对应的日期
     */
    public static List<String> getYANG(String year, Map<String, HolidayEnum> legalHolidayMap) {
        List<String> holidaysYang = Arrays.asList(HolidayUtil.HOLIDAYSYANG).stream().map(md -> year + md).collect(Collectors.toList());
        for (int i = 0; i < holidaysYang.size(); i++) {
            String str = holidaysYang.get(i);
            if (i == 0) {
                legalHolidayMap.put(str, HolidayEnum.yd);
            }
            if (i == 1) {
                legalHolidayMap.put(str, HolidayEnum.ld);
            }
            if (i >= 2) {
                legalHolidayMap.put(str, HolidayEnum.gq);
            }
        }
        return holidaysYang;
    }

    /**
     * @param year            年份
     * @param legalHolidayMap 法定节假日的map
     * @return 农历法定假日对应的阳历日期
     */
    public static List<String> getYING(String year, Map<String, HolidayEnum> legalHolidayMap) {
        List<String> holidaysYing = Arrays.asList(HolidayUtil.HOLIDAYSYYING).stream().map(md -> YingYangConverter.ying2yang(year + md)).collect(Collectors.toList());
        for (int i = 0; i < holidaysYing.size(); i++) {
            String str = holidaysYing.get(i);
            if (i <= 2) {
                legalHolidayMap.put(str, HolidayEnum.cj);
            }
            if (i == 3) {
                legalHolidayMap.put(str, HolidayEnum.dw);
            }
            if (i == 4) {
                legalHolidayMap.put(str, HolidayEnum.zq);
            }
        }
        return holidaysYing;
    }

    /**
     * @param year            年份
     * @param qmBean          清明对应的bean
     * @param legalHolidayMap 法定节假日的map
     * @return 清明节对应的日期字符串  20170404
     */
    public static String getQM(String year, HolidayBean qmBean, Map<String, HolidayEnum> legalHolidayMap) {
        String qmStr = year + qmBean.getDayRange();
        legalHolidayMap.put(qmStr, qmBean.getHolidayType());
        return qmStr;
    }
}
