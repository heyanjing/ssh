package com.he.ssh.base.core.enumm;

/**
 * Created by heyanjing on 2017/12/22 10:38.
 */
public enum HolidayEnum {
    jjr("节假日", 1),
    fdjjr("法定节假日", 2),
    txgzr("调休工作日", 3),
    gzr("工作日",4),
    xxr("休息日", 5),
    yd("元旦", 6),
    cj("春节", 7),
    qm("清明节", 8),
    ld("劳动节", 9),
    dw("端午节",10),
    zq("中秋节", 11),
    gq("国庆节", 12),
    dwzq("端午节中秋节", 13),
    gqzq("国庆节中秋节", 14),
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
