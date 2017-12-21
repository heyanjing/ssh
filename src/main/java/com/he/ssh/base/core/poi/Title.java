package com.he.ssh.base.core.poi;

/**
 * Created by heyanjing on 2017/12/1 16:20.
 */
public class Title {
    private String title;//标题
    private String prop;//对应的属性名称

    @Override
    public String toString() {
        return "Title{" +
                "title='" + title + '\'' +
                ", prop='" + prop + '\'' +
                '}';
    }

    public Title(String title, String prop) {
        this.title = title;
        this.prop = prop;
    }

    public Title() {

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getProp() {
        return prop;
    }

    public void setProp(String prop) {
        this.prop = prop;
    }
}
