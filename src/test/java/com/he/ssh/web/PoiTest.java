package com.he.ssh.web;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by heyanjing on 2017/12/20 8:31.
 */
public class PoiTest {
    private static final Logger log = LoggerFactory.getLogger(PoiTest.class);

    @Test
    public void poi() {
        try {
            FileInputStream fileInputStream = new FileInputStream("D:\\Temp\\卡片列表 - 副本.xls");
            HSSFWorkbook wb = new HSSFWorkbook(new POIFSFileSystem(fileInputStream));
            log.info("{}", this.getRealRowNumber(wb.getSheetAt(0)));




            wb.write(new File("D:\\Temp\\卡片列表x.xls"));

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private int getRealRowNumber(Sheet sheet) {
        int lastRowNum = sheet.getLastRowNum();
        log.info("{}", sheet.getLastRowNum());
        for (int i = 0; i < lastRowNum; i++) {
            Row row1 = sheet.getRow(i);
            if (row1 == null) {
                sheet.shiftRows(i + 1, lastRowNum, -1);
                lastRowNum = sheet.getLastRowNum();
                i=i-1;
            } else {
                Boolean isRemove = true;//默认移除该行
                for (Cell cell : row1) {
                    if (cell.getCellTypeEnum() != CellType.BLANK) {
                        isRemove = false;
                        break;
                    }
                }
                if (isRemove) {
                    if (i == sheet.getLastRowNum()) {
                        sheet.removeRow(row1);
                    } else {
                        sheet.shiftRows(i + 1, lastRowNum, -1);
                        lastRowNum = sheet.getLastRowNum();
                        i=i-1;
                    }
                }
            }
        }
        log.info("{}", sheet.getLastRowNum());
        return sheet.getLastRowNum();
    }
}
