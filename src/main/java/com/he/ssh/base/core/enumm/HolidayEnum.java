package com.he.ssh.base.core.enumm;

/**
 * Created by heyanjing on 2017/12/22 10:38.
 */
public enum HolidayEnum {
    ONE("法定节假日", 1),
    TWO("休息日", 2),
    THREE("工作日", 3),
    FOUR("调休工作日", 4),
    FIVE("调休休息日", 5),
    SIX("元旦", 6),
    SEVEN("春节", 7),
    EIGHT("清明节", 8),
    NINE("劳动节", 9),
    TEN("端午节",10),
    ELEVEN("中秋节",11),
    TWELVE("国庆节",12),
    THIRTEEN("端午节中秋节",13),
    FOURTEEN("中秋节国庆节",14),

    OTHER("其他", 9999);


    private String text;
    private Integer value;


    HolidayEnum(String text, Integer value) {
        this.text = text;
        this.value = value;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
