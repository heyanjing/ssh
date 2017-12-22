package com.he.ssh.web.service;

import com.he.ssh.entity.Holiday;
import com.he.ssh.web.dao.HolidayDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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


}
