package com.he.ssh.web.controller;

import com.he.ssh.entity.Holiday;
import com.he.ssh.web.service.HolidayService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by heyanjing on 2017/12/19 11:17.
 */
@Controller
@RequestMapping("/holiday")
public class HolidayController {
    private static final Logger log = LoggerFactory.getLogger(HolidayController.class);

    private HolidayService holidayService;

    @Autowired
    public void setHolidayService(HolidayService holidayService) {
        this.holidayService = holidayService;
    }


    /**
     * @http  /holiday/getByLocalDateStr
     */
    @RequestMapping("/getByLocalDateStr")
    @ResponseBody
    public Holiday getByLocalDateStr(@RequestParam("d") String d) {
        return this.holidayService.getByLocalDateStr(d);
    }
    /**
     * @http  /holiday/findByLocalDateStrs
     */
    @RequestMapping("/findByLocalDateStrs")
    @ResponseBody
    public List<Holiday> findByLocalDateStrs(@RequestParam("d") String d) {
        return this.holidayService.findByLocalDateStrs(d);
    }
    /**
     * @http  /holiday/findByMonth
     */
    @RequestMapping("/findByMonth")
    @ResponseBody
    public List<Holiday> findByMonth(@RequestParam("m") String m) {
        return this.holidayService.findByMonth(m);
    }


}
