package com.he.ssh.base.core;

import com.he.ssh.base.bean.HolidayBean;
import com.he.ssh.base.bean.Result;
import com.he.ssh.base.core.enumm.HolidayEnum;
import com.he.ssh.entity.Holiday;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

/**
 * Created by heyanjing on 2017/12/22 17:06.
 */
public class HolidayUtil {
    private static final Logger log = LoggerFactory.getLogger(HolidayUtil.class);
    public static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");

    /**
     * @param year         年份
     * @param legalHoliday 节假日类型和对应的节假日范围的对象集合
     * @param workStill    节假日类型和对应的周末任然工作的对象集合
     * @param restStill    节假日类型和对应的工作日任然休息的对象集合
     * @return
     */
    public static Result genernateAllHolidays(String year, List<HolidayBean> legalHoliday, List<HolidayBean> workStill, List<HolidayBean> restStill) {
        Map<String, HolidayEnum> map = Guava.newHashMap();

        List<String> legalHolidayDateRangeList = Guava.newArrayList();
        List<HolidayEnum> legalHolidayHolidayEnumList = Guava.newArrayList();
        legalHoliday.forEach(bean -> {
            legalHolidayDateRangeList.add(bean.getDayRange());
            legalHolidayHolidayEnumList.add(bean.getHolidayType());
        });
        List<String> workStillDateRangeList = Guava.newArrayList();
        List<HolidayEnum> workStillHolidayEnumList = Guava.newArrayList();
        workStill.forEach(bean -> {
            workStillDateRangeList.add(bean.getDayRange());
            workStillHolidayEnumList.add(bean.getHolidayType());
        });
        List<String> restStillDateRangeList = Guava.newArrayList();
        List<HolidayEnum> restStillHolidayEnumList = Guava.newArrayList();
        restStill.forEach(bean -> {
            restStillDateRangeList.add(bean.getDayRange());
            restStillHolidayEnumList.add(bean.getHolidayType());
        });


        Result legalHolidayResult = getdays(year, legalHolidayDateRangeList, legalHolidayHolidayEnumList, map);
        Result workStillResult = getdays(year, workStillDateRangeList, workStillHolidayEnumList, map);
        Result restStillResult = getdays(year, restStillDateRangeList, restStillHolidayEnumList, map);
        if (!legalHolidayResult.isSuccess()) {
            return legalHolidayResult;
        }
        if (!workStillResult.isSuccess()) {
            return workStillResult;
        }
        if (!restStillResult.isSuccess()) {
            return restStillResult;
        }
        List<String> legalHolidayStrList = (List<String>) legalHolidayResult.getData();
        List<String> workStillStrList = (List<String>) workStillResult.getData();
        List<String> restStillStrList = (List<String>) restStillResult.getData();

        return getAllHolidays(year, map, legalHolidayStrList, workStillStrList, restStillStrList);
    }

    /**
     * @param year            年份
     * @param dateRangeList   日期范围的字符串集合,不包含年份 0101-0121
     * @param holidayEnumList 期范围的字符串集合 对应的 节假日类型
     * @param map             存放具体某一天对应的节假日类型
     * @return 返回日期范围对应的完整的日期
     */
    private static Result getdays(String year, List<String> dateRangeList, List<HolidayEnum> holidayEnumList, Map<String, HolidayEnum> map) {
        Result result = Result.success();
        List list = Guava.newArrayList();
        for (int i = 0; i < dateRangeList.size(); i++) {
            String day = dateRangeList.get(i);
            HolidayEnum dayType = holidayEnumList.get(i);
            if (day.contains("-")) {
                String[] rangeDay = day.split("-");
                String startDay = year + rangeDay[0];
                String endDay = year + rangeDay[1];
                LocalDate start = LocalDate.parse(startDay, dateTimeFormatter);
                LocalDate end = LocalDate.parse(endDay, dateTimeFormatter);
                if (start.isBefore(end)) {
                    LocalDate temp = start.minusDays(1);
                    int over = 0;
                    while (true) {
                        temp = temp.plusDays(1);
                        String localDateStr = temp.format(dateTimeFormatter);
                        list.add(localDateStr);
                        map.put(localDateStr, dayType);
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
                    return Result.failure("日期范围开始日期应该小于结束日期");
                }
            } else {
                String localDateStr = year + day;
                list.add(localDateStr);
                map.put(localDateStr, dayType);
            }
        }
        result.setData(list);
        return result;
    }

    /**
     * @param year                年份的字符串
     * @param map                 存放存放日期与节假日枚举对应关系
     * @param legalHolidayStrList 所有法定假日日期
     * @param workStillStrList    所有周末任然上班日期
     * @param restStillStrList    所有工作日任然休息的日期
     * @return
     */
    public static Result getAllHolidays(String year, Map<String, HolidayEnum> map, List<String> legalHolidayStrList, List<String> workStillStrList, List<String> restStillStrList) {
        Result result = Result.success();
        String startDate = year + "0101";
        String endDate = year + "1231";
        LocalDate start = LocalDate.parse(startDate, dateTimeFormatter);
        LocalDate end = LocalDate.parse(endDate, dateTimeFormatter);
        if (start.isBefore(end)) {
            LocalDateTime now = LocalDateTime.now();
            List<LocalDate> allDays = getAllDays(start, end);
            int totalDays = allDays.size();
            List<Holiday> holidays = Guava.newArrayList();
            allDays.forEach(nowDate -> {
                DayOfWeek dayOfWeek = nowDate.getDayOfWeek();
                String nowStr = nowDate.format(dateTimeFormatter);
                Holiday holiday = new Holiday(nowStr, nowDate, totalDays);
                holiday.setCreateDateTime(now);
                holiday.setUpdateDateTime(now);

                if (legalHolidayStrList.contains(nowStr)) {
                    log.error("{}是法定节假日", nowDate.format(dateTimeFormatter));
                    holiday.setType(HolidayEnum.ONE.getValue());
                    holiday.setTypeStr(HolidayEnum.ONE.getText());
                    HolidayEnum holidayEnum = map.get(nowStr);
                    holiday.setHolidayType(holidayEnum.getValue());
                    holiday.setHolidayTypeStr(holidayEnum.getText());
                } else {
                    if (dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY) {
                        if (workStillStrList.contains(nowStr)) {
                            log.error("{}是调休工作日", nowDate.format(dateTimeFormatter));
                            holiday.setType(HolidayEnum.FOUR.getValue());
                            holiday.setTypeStr(HolidayEnum.FOUR.getText());
                            HolidayEnum holidayEnum = map.get(nowStr);
                            holiday.setHolidayType(holidayEnum.getValue());
                            holiday.setHolidayTypeStr(holidayEnum.getText());
                        } else {
                            log.error("{}是休息日", nowDate.format(dateTimeFormatter));
                            holiday.setType(HolidayEnum.TWO.getValue());
                            holiday.setTypeStr(HolidayEnum.TWO.getText());
                        }
                    } else {
                        if (restStillStrList.contains(nowStr)) {
                            log.error("{}是调休休息日", nowDate.format(dateTimeFormatter));
                            holiday.setType(HolidayEnum.FIVE.getValue());
                            holiday.setTypeStr(HolidayEnum.FIVE.getText());
                            HolidayEnum holidayEnum = map.get(nowStr);
                            holiday.setHolidayType(holidayEnum.getValue());
                            holiday.setHolidayTypeStr(holidayEnum.getText());
                        } else {
                            log.error("{}是工作日", nowDate.format(dateTimeFormatter));
                            holiday.setType(HolidayEnum.THREE.getValue());
                            holiday.setTypeStr(HolidayEnum.THREE.getText());
                        }
                    }
                }
                holidays.add(holiday);
            });
            result.setData(holidays);
        } else {
            log.warn("开始日期应该小于结束日期");
            result = Result.failure();
            result.setMsg("开始日期应该小于结束日期");
        }
        return result;
    }

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
}
