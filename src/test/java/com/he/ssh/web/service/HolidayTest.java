package com.he.ssh.web.service;

import com.he.ssh.base.bean.HolidayBean;
import com.he.ssh.base.core.YingYangConverter;
import com.he.ssh.base.core.Guava;
import com.he.ssh.base.core.HolidayUtil;
import com.he.ssh.base.core.enumm.HolidayEnum;
import com.he.ssh.entity.Holiday;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
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
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:spring/spring-base.xml"})
public class HolidayTest {
    public static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
    private static final Logger log = LoggerFactory.getLogger(HolidayTest.class);

    @Autowired
    HolidayService holidayService;

    @Test
    public void holiday3() {
        //region Description 2017年的法定节假日
//        一、元旦：1月1日放假，1月2日（星期一）补休。0101/ /0102
//        二、春节：1月27日至2月2日放假调休，共7天。1月22日（星期日）、2月4日（星期六）上班。0127-0202 / 0122 0204
//        三、清明节：4月2日至4日放假调休，共3天。4月1日（星期六）上班。0402-0404 / 0401
//        四、劳动节：5月1日放假，与周末连休。0429-0501
//        五、端午节：5月28日至30日放假调休，共3天。5月27日（星期六）上班。 0528-0530 / 0527
//        六、中秋节、国庆节：10月1日至8日放假调休，共8天。9月30日（星期六）上班。 1001-1008 / 0930
        //年份
        String year = "2017";
        //清明
        HolidayBean qmBean = new HolidayBean(HolidayEnum.qm, "0404");
        //节假日安排
        List<HolidayBean> legalHolidayPlan = Guava.newArrayList();
        legalHolidayPlan.add(new HolidayBean(HolidayEnum.yd, "1231-0102"));//元旦
        legalHolidayPlan.add(new HolidayBean(HolidayEnum.cj, "0127-0202"));//春节
        legalHolidayPlan.add(new HolidayBean(HolidayEnum.qm, "0402-0404"));//清明节
        legalHolidayPlan.add(new HolidayBean(HolidayEnum.ld, "0429-0501"));//劳动节
        legalHolidayPlan.add(new HolidayBean(HolidayEnum.dw, "0528-0530"));//端午节
        legalHolidayPlan.add(new HolidayBean(HolidayEnum.gqzq, "1001-1008"));//国庆节中秋节
        //节假日安排正常上班的时间
        List<HolidayBean> workStill = Guava.newArrayList();
        workStill.add(new HolidayBean(HolidayEnum.cj, "0122"));//春节
        workStill.add(new HolidayBean(HolidayEnum.cj, "0204"));//春节
        workStill.add(new HolidayBean(HolidayEnum.qm, "0401"));//清明
        workStill.add(new HolidayBean(HolidayEnum.dw, "0527"));//端午
        workStill.add(new HolidayBean(HolidayEnum.gqzq, "0930"));//国庆节中秋节
        //endregion
        //region Description 2018年的法定节假日
//        一、元旦：1月1日放假，与周末连休。0101
//        二、春节：2月15日至21日放假调休，共7天。2月11日（星期日）、2月24日（星期六）上班。0215-0221 0211 0224
//        三、清明节：4月5日至7日放假调休，共3天。4月8日（星期日）上班。0405-0407 0408
//        四、劳动节：4月29日至5月1日放假调休，共3天。4月28日（星期六）上班。 0429-0501 0428
//        五、端午节：6月18日放假，与周末连休。 0618
//        六、中秋节：9月24日放假，与周末连休。 0924
//        七、国庆节：10月1日至7日放假调休，共7天。9月29日（星期六）、9月30日（星期日）上班。 1001-1007 0929-0930
        //年份
        year = "2018";
        //清明
        qmBean = new HolidayBean(HolidayEnum.qm, "0405");
        //节假日安排
        legalHolidayPlan = Guava.newArrayList();
        legalHolidayPlan.add(new HolidayBean(HolidayEnum.yd, "1230-0101"));//元旦
        legalHolidayPlan.add(new HolidayBean(HolidayEnum.cj, "0215-0221"));//春节
        legalHolidayPlan.add(new HolidayBean(HolidayEnum.qm, "0405-0407"));//清明节
        legalHolidayPlan.add(new HolidayBean(HolidayEnum.ld, "0429-0501"));//劳动节
        legalHolidayPlan.add(new HolidayBean(HolidayEnum.dw, "0616-0618"));//端午节
        legalHolidayPlan.add(new HolidayBean(HolidayEnum.zq, "0922-0924"));//中秋节
        legalHolidayPlan.add(new HolidayBean(HolidayEnum.gq, "1001-1007"));//国庆节
        //节假日安排正常上班的时间
        workStill = Guava.newArrayList();
        workStill.add(new HolidayBean(HolidayEnum.cj, "0211"));//春节
        workStill.add(new HolidayBean(HolidayEnum.cj, "0224"));//春节
        workStill.add(new HolidayBean(HolidayEnum.qm, "0408"));//清明
        workStill.add(new HolidayBean(HolidayEnum.ld, "0428"));//劳动节
        workStill.add(new HolidayBean(HolidayEnum.gq, "0929"));//国庆节
        workStill.add(new HolidayBean(HolidayEnum.gq, "0930"));//国庆节
        //endregion


        Map<String, HolidayEnum> legalHolidayPlanMap = Guava.newHashMap();//节假日安排 阳历日期字符串对应的节假日枚举
        Map<String, HolidayEnum> legalHolidayMap = Guava.newHashMap();//法定节假日 阳历日期字符串对应的节假日枚举
        String qmStr = HolidayUtil.getQM(year, qmBean, legalHolidayMap);//清明
        List<String> holidaysYing = HolidayUtil.getYING(year, legalHolidayMap);//农历法定假日对应的阳历日期
//        holidaysYing.forEach(str -> log.info(str));
//        log.info("以上是农历");
        List<String> holidaysYang = HolidayUtil.getYANG(year, legalHolidayMap);//阳历法定假日对应的日期
//        holidaysYang.forEach(str -> log.info(str));
//        log.info("以上是农历");
        List<String> holidays = HolidayUtil.getAllHolidays(qmStr, holidaysYing, holidaysYang);//得到所有法定假日
//        holidays.forEach(str -> log.info(str));
        List<String> legalHolidayPlanList2017 = HolidayUtil.getAllPlanHolidays(year, legalHolidayPlan, legalHolidayPlanMap);//所有的节假日时间安排对应的日期字符串
        List<String> workStillList2017 = HolidayUtil.getWorkStill(year, workStill, legalHolidayPlanMap);//所有的节假日时间安排周末上班对应的日期字符串
        List<Holiday> allHoliday = HolidayUtil.getAllHoliday(year, legalHolidayPlanMap, legalHolidayMap, holidays, legalHolidayPlanList2017, workStillList2017);//返回该年度每一天的实体
        this.holidayService.saveAll(allHoliday);


    }


}
