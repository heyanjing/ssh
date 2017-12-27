package com.he.ssh.entity;

import com.he.ssh.base.entity.BaseEntityWithStringId;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import java.time.LocalDate;

/**
 * Created by heyanjing on 2017/12/19 9:43.
 */
@Entity
@DynamicUpdate
@DynamicInsert
public class Holiday extends BaseEntityWithStringId {

    private String localDateStr;//日期字符串
    private LocalDate localDate;//日期
    private Integer type;//日期类型
    private String typeStr;//日期类型字符串
    private Integer totalDay;//当年的总天数
    private Integer holidayType;//节假日类型
    private String holidayTypeStr;//节假日类型字符串

    public String getHolidayTypeStr() {
        return holidayTypeStr;
    }

    public void setHolidayTypeStr(String holidayTypeStr) {
        this.holidayTypeStr = holidayTypeStr;
    }

    public Integer getHolidayType() {
        return holidayType;
    }

    public void setHolidayType(Integer holidayType) {
        this.holidayType = holidayType;
    }

    public Holiday(String localDateStr, LocalDate localDate, Integer type, String typeStr, Integer totalDay, Integer holidayType) {

        this.localDateStr = localDateStr;
        this.localDate = localDate;
        this.type = type;
        this.typeStr = typeStr;
        this.totalDay = totalDay;
        this.holidayType = holidayType;
    }

    public Holiday(String localDateStr, LocalDate localDate, Integer totalDay) {
        this.localDateStr = localDateStr;
        this.localDate = localDate;
        this.totalDay = totalDay;
    }

    public String getLocalDateStr() {
        return localDateStr;
    }

    public void setLocalDateStr(String localDateStr) {
        this.localDateStr = localDateStr;
    }

    public String getTypeStr() {
        return typeStr;
    }

    public void setTypeStr(String typeStr) {
        this.typeStr = typeStr;
    }

    public Integer getTotalDay() {
        return totalDay;
    }

    public void setTotalDay(Integer totalDay) {
        this.totalDay = totalDay;
    }

    public LocalDate getLocalDate() {
        return localDate;
    }

    public void setLocalDate(LocalDate localDate) {
        this.localDate = localDate;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Holiday(String localDateStr, LocalDate localDate, Integer type, String typeStr, Integer totalDay) {
        this.localDateStr = localDateStr;
        this.localDate = localDate;
        this.type = type;
        this.typeStr = typeStr;
        this.totalDay = totalDay;
    }

    public Holiday() {

    }
}
