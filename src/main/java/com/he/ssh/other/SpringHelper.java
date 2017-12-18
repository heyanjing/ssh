package com.he.ssh.other;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by heyanjing on 2017/12/18 11:20.
 */
@Component
public class SpringHelper implements ApplicationContextAware, ServletContextAware {

    private static ApplicationContext ac;
    private static ServletContext sc;

    @Override
    public void setApplicationContext(ApplicationContext ac) throws BeansException {
        SpringHelper.ac = ac;
    }

    @Override
    public void setServletContext(ServletContext sc) {
        SpringHelper.sc = sc;
    }

    public static ApplicationContext getApplicatoinContext() {
        return ac;
    }

    public static ServletContext getServletContext() {
        return sc;
    }

    public static Object getSpringBean(String name) {
        if (ac == null) {
            throw new IllegalStateException("spring环境尚未启动！");
        }
        return ac.getBean(name);
    }

    public static HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
    }

    public static HttpSession getSession() {
        return getRequest().getSession();
    }

    public static String getContextPath() {
        String ctx = getRequest().getContextPath();
        return ctx.endsWith("/") ? ctx : ctx + "/";
    }


    public static String getAbsoluteUrl() {
        HttpServletRequest request = getRequest();
        return request.getLocalAddr() + ":" + request.getLocalPort() + request.getContextPath();
    }

    public static String getIPAddress() {
        HttpServletRequest request = getRequest();
        String ip = request.getHeader("x-forwarded-for");
        if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

}
