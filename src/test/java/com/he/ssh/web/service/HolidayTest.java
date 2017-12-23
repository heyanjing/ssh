package com.he.ssh.web.service;

import com.he.ssh.base.bean.HolidayBean;
import com.he.ssh.base.bean.Result;
import com.he.ssh.base.core.CalenderConverter;
import com.he.ssh.base.core.Guava;
import com.he.ssh.base.core.HolidayUtil;
import com.he.ssh.base.core.enumm.HolidayEnum;
import com.he.ssh.entity.Holiday;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by heyanjing on 2017/12/22 8:58.
 * 检查一个日期是否为节假日 http://www.easybots.cn/api/holiday.php?d=20130101
 * 检查多个日期是否为节假日 http://www.easybots.cn/api/holiday.php?d=20130101,20130103,20130105,20130201
 * 获取2012年1月份节假日 http://www.easybots.cn/api/holiday.php?m=201201
 * 获取2013年1/2月份节假日 http://www.easybots.cn/api/holiday.php?m=201301,201302
 * <p>
 * 新年，放假1天（1月1日）；
 * 春节，放假3天（农历正月初一、初二、初三）；
 * 清明节，放假1天（阳历清明当日）；
 * 劳动节，放假1天（5月1日）；
 * 端午节，放假1天（农历端午当日）；
 * 中秋节，放假1天（农历中秋当日）；
 * 国庆节，放假3天（10月1日、2日、3日）。
 * <p>
 * ▪ 春节 ( 农历正月初一)	▪ 元宵节 ( 农历正月十五)	▪ 头牙 ( 农历二月初二)
 * ▪ 寒食节 ( 清明节前一天)	▪ 清明节 ( 节气清明)	▪ 端午节 ( 农历五月初五)
 * ▪ 七夕 ( 农历七月初七)	▪ 中元节 ( 农历七月十五)	▪ 中秋节 ( 农历八月十五)
 * ▪ 重阳节 ( 农历九月九)	▪ 冬至 ( 节气冬至)	▪ 腊八节 ( 农历腊月初八)
 * ▪ 尾牙 ( 农历腊月十六)	▪ 祭灶 ( 农历腊月廿四)	▪ 除夕 ( 农历十二月卅日)
 */
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = {"classpath*:spring/spring-base.xml"})
public class HolidayTest {
    private static final Logger log = LoggerFactory.getLogger(HolidayTest.class);

    @Autowired
    HolidayService holidayService;

    @Test
    public void holiday3() {
        //region Description 2017年的法定节假日
//        一、元旦：1月1日放假，1月2日（星期一）补休。0101/ /0102
//
//        二、春节：1月27日至2月2日放假调休，共7天。1月22日（星期日）、2月4日（星期六）上班。0127-0202 / 0122 0204
//
//        三、清明节：4月2日至4日放假调休，共3天。4月1日（星期六）上班。0402-0404 / 0401
//
//        四、劳动节：5月1日放假，与周末连休。0429-0501
//
//        五、端午节：5月28日至30日放假调休，共3天。5月27日（星期六）上班。 0528-0530 / 0527
//
//        六、中秋节、国庆节：10月1日至8日放假调休，共8天。9月30日（星期六）上班。 1001-1008 / 0930
        //endregion
        String year = "2017";
        List<String> holidays1 = Arrays.asList(CalenderConverter.HOLIDAYS).parallelStream().map(md -> CalenderConverter.ying2yang(year + md)).collect(Collectors.toList());
        List<String> holidays2 = Arrays.asList(HolidayUtil.HOLIDAYS).parallelStream().map(md -> year + md).collect(Collectors.toList());
        holidays1.addAll(holidays2);
        //得到所有法定假日
        List<String> holidays = holidays1.stream().sorted().collect(Collectors.toList());

        holidays.forEach(str -> log.info(str));
//        20170101
//        20170128
//        20170129
//        20170130
//        20170501
//        20170530
//        20171001
//        20171002
//        20171003
//        20171004
// METIP: 2017/12/24 2:24 重要提醒 只需要封装对应的 周末任然工作 和 工作日任然休息

















/*
        List<HolidayBean> legalHoliday2017 = Guava.newArrayList();//法定节日
        legalHoliday2017.add(new HolidayBean(HolidayEnum.SIX, "0101"));
        legalHoliday2017.add(new HolidayBean(HolidayEnum.SEVEN, "0128-0130"));
        legalHoliday2017.add(new HolidayBean(HolidayEnum.EIGHT, "0404"));
        legalHoliday2017.add(new HolidayBean(HolidayEnum.NINE, "0501"));
        legalHoliday2017.add(new HolidayBean(HolidayEnum.TEN, "0530"));
        legalHoliday2017.add(new HolidayBean(HolidayEnum.TWELVE, "1001-1003"));
        legalHoliday2017.add(new HolidayBean(HolidayEnum.ELEVEN, "1004"));

        List<HolidayBean> workStill2017 = Guava.newArrayList();//周末任然工作
        workStill2017.add(new HolidayBean(HolidayEnum.SEVEN, "0122"));
        workStill2017.add(new HolidayBean(HolidayEnum.SEVEN, "0204"));
        workStill2017.add(new HolidayBean(HolidayEnum.EIGHT, "0401"));
        workStill2017.add(new HolidayBean(HolidayEnum.TEN, "0527"));
        workStill2017.add(new HolidayBean(HolidayEnum.TWELVE, "0930"));

        List<HolidayBean> restStill2017 = Guava.newArrayList();//工作日任然休息
        restStill2017.add(new HolidayBean(HolidayEnum.SIX, "0102"));
        restStill2017.add(new HolidayBean(HolidayEnum.SEVEN, "0127"));
        restStill2017.add(new HolidayBean(HolidayEnum.SEVEN, "0131"));
        restStill2017.add(new HolidayBean(HolidayEnum.SEVEN, "0201"));
        restStill2017.add(new HolidayBean(HolidayEnum.SEVEN, "0202"));
        workStill2017.add(new HolidayBean(HolidayEnum.EIGHT, "0403"));
        workStill2017.add(new HolidayBean(HolidayEnum.TEN, "0529"));
        workStill2017.add(new HolidayBean(HolidayEnum.TWELVE, "1005"));
        workStill2017.add(new HolidayBean(HolidayEnum.TWELVE, "1006"));

        Result result = HolidayUtil.genernateAllHolidays(year, legalHoliday2017, workStill2017, restStill2017);
        insert(result);


        //region Description 2018年的法定节假日

//        一、元旦：1月1日放假，与周末连休。0101
//
//        二、春节：2月15日至21日放假调休，共7天。2月11日（星期日）、2月24日（星期六）上班。0215-0221 0211 0224
//
//        三、清明节：4月5日至7日放假调休，共3天。4月8日（星期日）上班。0405-0407 0408
//
//        四、劳动节：4月29日至5月1日放假调休，共3天。4月28日（星期六）上班。 0429-0501 0428
//
//        五、端午节：6月18日放假，与周末连休。 0618
//
//        六、中秋节：9月24日放假，与周末连休。 0924
//
//        七、国庆节：10月1日至7日放假调休，共7天。9月29日（星期六）、9月30日（星期日）上班。 1001-1007 0929-0930
        //endregion
        String year2 = "2018";
        List<HolidayBean> legalHoliday2018 = Guava.newArrayList();//法定节日
        legalHoliday2018.add(new HolidayBean(HolidayEnum.SIX, "0101"));
        legalHoliday2018.add(new HolidayBean(HolidayEnum.SEVEN, "0216-0218"));
        legalHoliday2018.add(new HolidayBean(HolidayEnum.EIGHT, "0405"));
        legalHoliday2018.add(new HolidayBean(HolidayEnum.NINE, "0501"));
        legalHoliday2018.add(new HolidayBean(HolidayEnum.TEN, "0618"));
        legalHoliday2018.add(new HolidayBean(HolidayEnum.ELEVEN, "0924"));
        legalHoliday2018.add(new HolidayBean(HolidayEnum.TWELVE, "1001-1003"));

        List<HolidayBean> workStill2018 = Guava.newArrayList();//周末任然工作
        workStill2018.add(new HolidayBean(HolidayEnum.SEVEN, "0211"));
        workStill2018.add(new HolidayBean(HolidayEnum.SEVEN, "0224"));
        workStill2018.add(new HolidayBean(HolidayEnum.EIGHT, "0408"));
        workStill2018.add(new HolidayBean(HolidayEnum.NINE, "0428"));
        workStill2018.add(new HolidayBean(HolidayEnum.TWELVE, "0929-0930"));

        List<HolidayBean> restStill2018 = Guava.newArrayList();//工作日任然休息
        restStill2018.add(new HolidayBean(HolidayEnum.SEVEN, "0215"));
        restStill2018.add(new HolidayBean(HolidayEnum.SEVEN, "0219-0221"));
        restStill2018.add(new HolidayBean(HolidayEnum.EIGHT, "0406"));
        restStill2018.add(new HolidayBean(HolidayEnum.NINE, "0430"));
        restStill2018.add(new HolidayBean(HolidayEnum.TWELVE, "1004-1005"));

        Result result2 = HolidayUtil.genernateAllHolidays(year2, legalHoliday2018, workStill2018, restStill2018);
        insert(result2);*/
    }

    @Test
    public void holiday2() {
        //region Description 2017年的法定节假日
//        一、元旦：1月1日放假，1月2日（星期一）补休。0101/ /0102
//
//        二、春节：1月27日至2月2日放假调休，共7天。1月22日（星期日）、2月4日（星期六）上班。0127-0202 / 0122 0204
//
//        三、清明节：4月2日至4日放假调休，共3天。4月1日（星期六）上班。0402-0404 / 0401
//
//        四、劳动节：5月1日放假，与周末连休。0429-0501
//
//        五、端午节：5月28日至30日放假调休，共3天。5月27日（星期六）上班。 0528-0530 / 0527
//
//        六、中秋节、国庆节：10月1日至8日放假调休，共8天。9月30日（星期六）上班。 1001-1008 / 0930
        //endregion
        String year = "2017";
        List<HolidayBean> legalHoliday2017 = Guava.newArrayList();//法定节日
        legalHoliday2017.add(new HolidayBean(HolidayEnum.SIX, "0101"));
        legalHoliday2017.add(new HolidayBean(HolidayEnum.SEVEN, "0128-0130"));
        legalHoliday2017.add(new HolidayBean(HolidayEnum.EIGHT, "0404"));
        legalHoliday2017.add(new HolidayBean(HolidayEnum.NINE, "0501"));
        legalHoliday2017.add(new HolidayBean(HolidayEnum.TEN, "0530"));
        legalHoliday2017.add(new HolidayBean(HolidayEnum.TWELVE, "1001-1003"));
        legalHoliday2017.add(new HolidayBean(HolidayEnum.ELEVEN, "1004"));

        List<HolidayBean> workStill2017 = Guava.newArrayList();//周末任然工作
        workStill2017.add(new HolidayBean(HolidayEnum.SEVEN, "0122"));
        workStill2017.add(new HolidayBean(HolidayEnum.SEVEN, "0204"));
        workStill2017.add(new HolidayBean(HolidayEnum.EIGHT, "0401"));
        workStill2017.add(new HolidayBean(HolidayEnum.TEN, "0527"));
        workStill2017.add(new HolidayBean(HolidayEnum.TWELVE, "0930"));

        List<HolidayBean> restStill2017 = Guava.newArrayList();//工作日任然休息
        restStill2017.add(new HolidayBean(HolidayEnum.SIX, "0102"));
        restStill2017.add(new HolidayBean(HolidayEnum.SEVEN, "0127"));
        restStill2017.add(new HolidayBean(HolidayEnum.SEVEN, "0131"));
        restStill2017.add(new HolidayBean(HolidayEnum.SEVEN, "0201"));
        restStill2017.add(new HolidayBean(HolidayEnum.SEVEN, "0202"));
        workStill2017.add(new HolidayBean(HolidayEnum.EIGHT, "0403"));
        workStill2017.add(new HolidayBean(HolidayEnum.TEN, "0529"));
        workStill2017.add(new HolidayBean(HolidayEnum.TWELVE, "1005"));
        workStill2017.add(new HolidayBean(HolidayEnum.TWELVE, "1006"));

        Result result = HolidayUtil.genernateAllHolidays(year, legalHoliday2017, workStill2017, restStill2017);
        insert(result);


        //region Description 2018年的法定节假日

//        一、元旦：1月1日放假，与周末连休。0101
//
//        二、春节：2月15日至21日放假调休，共7天。2月11日（星期日）、2月24日（星期六）上班。0215-0221 0211 0224
//
//        三、清明节：4月5日至7日放假调休，共3天。4月8日（星期日）上班。0405-0407 0408
//
//        四、劳动节：4月29日至5月1日放假调休，共3天。4月28日（星期六）上班。 0429-0501 0428
//
//        五、端午节：6月18日放假，与周末连休。 0618
//
//        六、中秋节：9月24日放假，与周末连休。 0924
//
//        七、国庆节：10月1日至7日放假调休，共7天。9月29日（星期六）、9月30日（星期日）上班。 1001-1007 0929-0930
        //endregion
        String year2 = "2018";
        List<HolidayBean> legalHoliday2018 = Guava.newArrayList();//法定节日
        legalHoliday2018.add(new HolidayBean(HolidayEnum.SIX, "0101"));
        legalHoliday2018.add(new HolidayBean(HolidayEnum.SEVEN, "0216-0218"));
        legalHoliday2018.add(new HolidayBean(HolidayEnum.EIGHT, "0405"));
        legalHoliday2018.add(new HolidayBean(HolidayEnum.NINE, "0501"));
        legalHoliday2018.add(new HolidayBean(HolidayEnum.TEN, "0618"));
        legalHoliday2018.add(new HolidayBean(HolidayEnum.ELEVEN, "0924"));
        legalHoliday2018.add(new HolidayBean(HolidayEnum.TWELVE, "1001-1003"));

        List<HolidayBean> workStill2018 = Guava.newArrayList();//周末任然工作
        workStill2018.add(new HolidayBean(HolidayEnum.SEVEN, "0211"));
        workStill2018.add(new HolidayBean(HolidayEnum.SEVEN, "0224"));
        workStill2018.add(new HolidayBean(HolidayEnum.EIGHT, "0408"));
        workStill2018.add(new HolidayBean(HolidayEnum.NINE, "0428"));
        workStill2018.add(new HolidayBean(HolidayEnum.TWELVE, "0929-0930"));

        List<HolidayBean> restStill2018 = Guava.newArrayList();//工作日任然休息
        restStill2018.add(new HolidayBean(HolidayEnum.SEVEN, "0215"));
        restStill2018.add(new HolidayBean(HolidayEnum.SEVEN, "0219-0221"));
        restStill2018.add(new HolidayBean(HolidayEnum.EIGHT, "0406"));
        restStill2018.add(new HolidayBean(HolidayEnum.NINE, "0430"));
        restStill2018.add(new HolidayBean(HolidayEnum.TWELVE, "1004-1005"));

        Result result2 = HolidayUtil.genernateAllHolidays(year2, legalHoliday2018, workStill2018, restStill2018);
        insert(result2);
    }

    public void insert(Result result) {
        if (result.isSuccess()) {
            List<Holiday> holidays = (List<Holiday>) result.getData();
            if (!holidays.isEmpty()) {
                this.holidayService.saveAll(holidays);
            }
        } else {
            log.info("{}", result);
        }
    }

   /* public static Result genernateAllHolidays(String year, List<HolidayBean> legalHoliday, List<HolidayBean> workStill, List<HolidayBean> restStill) {
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

    public static List<String> getRangeStrList(List<HolidayBean> legalHoliday) {
        return legalHoliday.parallelStream().map(bean -> bean.getDayRange()).sorted().collect(Collectors.toList());
    }

    @Test
    public void holiday() {
        String s = LocalDate.now().getYear() + "0101";
        String e = LocalDate.now().getYear() + "1231";
        String startDate = "20180101";
        String endDate = "20181231";
        insertHolidays(startDate, endDate);


    }

    public void insertHolidays(String startDate, String endDate) {
        LocalDate start = LocalDate.parse(startDate, dateTimeFormatter);
        LocalDate end = LocalDate.parse(endDate, dateTimeFormatter);
        if (start.isBefore(end)) {
            List<Holiday> holidays = getHolidays(start, end);
            this.holidayService.saveAll(holidays);
        } else {
            log.warn("开始日期应该小于结束日期");
        }
    }

    *//**
     * @param start 开始日期
     * @param end   结束日期
     * @return 返回从开始到结束日期中的所有日期封装成的holiday对象，包含开始、结束日期
     *//*
    public static List<Holiday> getHolidays(LocalDate start, LocalDate end) {
        List<Holiday> holidays = Guava.newArrayList();
        List<String> legalHoliday = getLegalHoliday2018();
        List<String> workStill = getWorkStill2018();
        List<String> restStill = getRestStill2018();
        List<LocalDate> allDays = getAllDays(start, end);
        int totalDays = allDays.size();
        allDays.forEach(now -> {
            log.info(now.format(dateTimeFormatter));
            DayOfWeek dayOfWeek = now.getDayOfWeek();
            String nowStr = now.format(dateTimeFormatter);
            Holiday holiday = new Holiday(nowStr, now, totalDays);
            holiday.setCreateDateTime(LocalDateTime.now());
            holiday.setUpdateDateTime(LocalDateTime.now());
            if (legalHoliday.contains(nowStr)) {
                log.error("{}是法定节假日", now.format(dateTimeFormatter));
                holiday.setType(HolidayEnum.ONE.getValue());
                holiday.setTypeStr(HolidayEnum.ONE.getText());
            } else {
                if (dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY) {
                    if (workStill.contains(nowStr)) {
                        log.error("{}是调休工作日", now.format(dateTimeFormatter));
                        holiday.setType(HolidayEnum.TWO.getValue());
                        holiday.setTypeStr(HolidayEnum.TWO.getText());
                    } else {
                        log.error("{}是周末", now.format(dateTimeFormatter));
                        holiday.setType(HolidayEnum.THREE.getValue());
                        holiday.setTypeStr(HolidayEnum.THREE.getText());
                    }
                } else {
                    if (restStill.contains(nowStr)) {
                        log.error("{}是调休休息日", now.format(dateTimeFormatter));
                        holiday.setType(HolidayEnum.FIVE.getValue());
                        holiday.setTypeStr(HolidayEnum.FIVE.getText());
                    } else {
                        log.error("{}是工作日", now.format(dateTimeFormatter));
                        holiday.setType(HolidayEnum.FOUR.getValue());
                        holiday.setTypeStr(HolidayEnum.FOUR.getText());
                    }
                }
            }
            holidays.add(holiday);
        });
        return holidays;
    }

    *//**
     * @param start 开始日期
     * @param end   结束日期
     * @return 返回从开始到结束日期中的所有日期，包含开始、结束日期
     *//*
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

    *//**
     * 获取法定节假日
     * 一、元旦：1月1日放假，1月2日（星期一）补休。0101
     * 二、春节：1月27日至2月2日放假调休，共7天。1月22日（星期日）、2月4日（星期六）上班。0127-0202 0122 0204
     * 三、清明节：4月2日至4日放假调休，共3天。4月1日（星期六）上班。0402-0404 0401
     * 四、劳动节：5月1日放假，与周末连休。0501
     * 五、端午节：5月28日至30日放假调休，共3天。5月27日（星期六）上班。0528-0530 0527
     * 六、中秋节、国庆节：10月1日至8日放假调休，共8天。9月30日（星期六）上班。1001-1008 0930
     *//*
    public static List<String> getLegalHoliday2017() {
        List<String> list = Guava.newArrayList();

        list.add("20170101");//元旦
//        list.add("20170102");

        list.add("20170127");//春节
        list.add("20170128");
        list.add("20170129");
        list.add("20170130");
        list.add("20170131");
        list.add("20170201");
        list.add("20170202");

        list.add("20170402");//清明
        list.add("20170403");
        list.add("20170404");

        list.add("20170429");//劳动节
        list.add("20170430");
        list.add("20170501");

        list.add("20170528");//端午节
        list.add("20170529");
        list.add("20170530");

        list.add("20171001");//中秋节、国庆节
        list.add("20171002");
        list.add("20171003");
        list.add("20171004");
        list.add("20171005");
        list.add("20171006");
        list.add("20171007");
        list.add("20171008");
        return list;
    }

    *//**
     * 获取 周末 放假调休的日期
     * 一、元旦：1月1日放假，1月2日（星期一）补休。
     * 二、春节：1月27日至2月2日放假调休，共7天。1月22日（星期日）、2月4日（星期六）上班。
     * 三、清明节：4月2日至4日放假调休，共3天。4月1日（星期六）上班。
     * 四、劳动节：5月1日放假，与周末连休。
     * 五、端午节：5月28日至30日放假调休，共3天。5月27日（星期六）上班。
     * 六、中秋节、国庆节：10月1日至8日放假调休，共8天。9月30日（星期六）上班。
     *//*
    public static List<String> getWorkStill2017() {
        List<String> list = Guava.newArrayList();

        list.add("20170122");//春节
        list.add("20170204");

        list.add("20170401");//清明节

        list.add("20170527");//端午节

        list.add("20170930");//中秋节、国庆节

        return list;
    }

    *//**
     * 获取 工作日 放假调休的日期
     * 一、元旦：1月1日放假，1月2日（星期一）补休。
     * 二、春节：1月27日至2月2日放假调休，共7天。1月22日（星期日）、2月4日（星期六）上班。
     * 三、清明节：4月2日至4日放假调休，共3天。4月1日（星期六）上班。
     * 四、劳动节：5月1日放假，与周末连休。
     * 五、端午节：5月28日至30日放假调休，共3天。5月27日（星期六）上班。
     * 六、中秋节、国庆节：10月1日至8日放假调休，共8天。9月30日（星期六）上班。
     *//*
    public static List<String> getRestStill2017() {
        List<String> list = Guava.newArrayList();
        list.add("20170102");
        return list;
    }

    *//**
     * 一、元旦：1月1日放假，与周末连休。 0101
     * 二、春节：2月15日至21日放假调休，共7天。2月11日（星期日）、2月24日（星期六）上班。0215-0221   0211-0224
     * 三、清明节：4月5日至7日放假调休，共3天。4月8日（星期日）上班。0404-0407   0408
     * 四、劳动节：4月29日至5月1日放假调休，共3天。4月28日（星期六）上班。0429-0501   0428
     * 五、端午节：6月18日放假，与周末连休。0618
     * 六、中秋节：9月24日放假，与周末连休。0924
     * 七、国庆节：10月1日至7日放假调休，共7天。9月29日（星期六）、9月30日（星期日）1001-1007 0929-0930
     *
     * @param year            年份
     * @param dateRangeList   日期范围的字符串集合,不包含年份 0101-0121
     * @param holidayEnumList 期范围的字符串集合 对应的 节假日类型
     * @param map             存放具体某一天对应的节假日类型
     * @return 返回日期范围对应的完整的日期
     *//*
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

    private static List<String> getdays(String year, List<String> days) {
        List<String> list = Guava.newArrayList();
        days.forEach(day -> {
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
                        list.add(temp.format(dateTimeFormatter));
                        over++;
                        if (temp.equals(end)) {
                            break;
                        }
                        if (over > 365) {
                            break;
                        }
                    }
                } else {
                    log.warn("开始日期应该小于结束日期");
                }
            } else {
                list.add(year + day);
            }

        });
        return list;
    }

    public static List<String> getLegalHoliday2018() {
        String year = "2018";
        List<String> days = Guava.newArrayList();
        days.add("0101");//元旦
        days.add("0215-0221");//春节
        days.add("0404-0407");//清明节
        days.add("0429-0501");//劳动节
        days.add("0618");//端午节
        days.add("0924");//中秋节
        days.add("1001-1007");//国庆节
        return getdays(year, days);
    }

    public static List<String> getWorkStill2018() {
        String year = "2018";
        List<String> days = Guava.newArrayList();
        days.add("0211-0224");//春节
        days.add("0408");//清明节
        days.add("0428");//劳动节
        days.add("0929-0930");//国庆节
        return getdays(year, days);
    }

    public static List<String> getRestStill2018() {
        String year = "2018";
        List<String> days = Guava.newArrayList();
        return getdays(year, days);
    }*/
}
