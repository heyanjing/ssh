package com.he.ssh;

import com.he.ssh.base.core.poi.Excel;
import com.he.ssh.base.core.poi.Title;
import com.he.ssh.web.bean.Bean;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by heyanjing on 2017/12/16 14:36.
 */
public class PoiTest {
    private static final Logger log = LoggerFactory.getLogger(PoiTest.class);

    @Test
    public void export() throws Exception {
        List<Title> titleList = new ArrayList<>();
        titleList.add(new Title("姓名", "id"));
        titleList.add(new Title("年龄", "age"));
        titleList.add(new Title("生日", "birthday"));

        List<Bean> dataList = new ArrayList<>();
        dataList.add(new Bean("name1", 1, new Date()));
        dataList.add(new Bean("name2", 2, new Date()));
        dataList.add(new Bean("name3", 3, new Date()));

        List<Map<String, Object>> dataList2 = new ArrayList<>();
        HashMap<String, Object> map1 = new HashMap<>();
        map1.put("id", "name1");
        map1.put("age", 1);
        map1.put("birthday", new Date());
        dataList2.add(map1);
        HashMap<String, Object> map2 = new HashMap<>();
        map2.put("id", "name2");
        map2.put("age", 2);
        map2.put("birthday", new Date());
        dataList2.add(map2);
        HashMap<String, Object> map3 = new HashMap<>();
        map3.put("id", "name3");
        map3.put("age", 3);
        map3.put("birthday", new Date());
        dataList2.add(map3);

        log.info("导出对象的集合数据");
        Workbook workbook1 = Excel.getExprotXSSFWorkbook("你妹1", dataList, titleList);
        workbook1.write(new FileOutputStream("D:\\Temp/export1.xlsx"));
        log.info("导出map的集合数据");
        Workbook workbook2 = Excel.getExprotXSSFWorkbook("你妹2", dataList2, titleList);
        workbook2.write(new FileOutputStream("D:\\Temp/export2.xlsx"));


    }
}