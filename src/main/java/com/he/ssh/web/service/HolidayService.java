package com.he.ssh.web.service;

import com.he.ssh.base.core.Guava;
import com.he.ssh.base.core.enumm.HolidayEnum;
import com.he.ssh.entity.Holiday;
import com.he.ssh.web.dao.HolidayDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * Created by heyanjing on 2017/12/19 10:37.
 */
@Service
public class HolidayService {
    private static final Logger log = LoggerFactory.getLogger(HolidayService.class);
    private HolidayDao holidayDao;

    @Autowired
    public void setHolidayDao(HolidayDao holidayDao) {
        this.holidayDao = holidayDao;
    }

    public void save(Holiday holiday) {
        this.holidayDao.save(holiday);
    }

    public void saveAll(Iterable<Holiday> holiday) {
        this.holidayDao.saveAll(holiday);
    }

    public Holiday getByLocalDateStr(String localDateStr) {
        return this.holidayDao.getByLocalDateStr(localDateStr);
    }

    public List<Holiday> findByLocalDateStrs(String localDateStrs) {
        List<Holiday> list = Guava.newArrayList();
        Arrays.asList(localDateStrs.split(",")).forEach(localDateStr -> {
            list.add(this.getByLocalDateStr(localDateStr));
        });
        return list;
    }

    public List<Holiday> findByMonth(String localDateStr) {
        return this.holidayDao.findByLocalDateStrLikeAndTypeNotAndHolidayTypeIsNotNullOrderByLocalDateAsc(localDateStr + "%", HolidayEnum.txgzr.getValue());
    }
}
