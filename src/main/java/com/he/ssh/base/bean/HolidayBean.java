package com.he.ssh.base.bean;

import com.he.ssh.base.core.enumm.HolidayEnum;

/**
 * Created by heyanjing on 2017/12/22 13:20.
 */
public class HolidayBean extends BaseBean {

    private HolidayEnum holidayType;//节假日类型
    private String dayRange;//节日范围

    public HolidayEnum getHolidayType() {
        return holidayType;
    }

    public void setHolidayType(HolidayEnum holidayType) {
        this.holidayType = holidayType;
    }

    public String getDayRange() {
        return dayRange;
    }

    public void setDayRange(String dayRange) {
        this.dayRange = dayRange;
    }

    public HolidayBean(HolidayEnum holidayType, String dayRange) {

        this.holidayType = holidayType;
        this.dayRange = dayRange;
    }

    public HolidayBean() {

    }
}
