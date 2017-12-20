package com.he.ssh;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.servlet.ServletContext;
import java.text.DateFormat;
import java.util.Date;

/**
 * Created by heyanjing on 2017/12/18 10:45.
 * <p>
 * 注入的context、this.servletContext=servletContext;和获取的webApplicationContext.getServletContext()是同一个对象
 * ContextLoader.getCurrentWebApplicationContext().getServletContext() 该方法需要spring容器初始化完成才能使用
 */
@Controller
public class Init implements ApplicationContextAware, ServletContextAware {
    private static final Logger log = LoggerFactory.getLogger(Init.class);
    @Autowired
    private ServletContext context;

    private ServletContext servletContext;
    private ApplicationContext applicationContext;

    @PostConstruct
    public void init() {
        log.warn("{}", context);
        String ctx = context.getContextPath();
        String staticc = ctx + "/static";
        String lib = staticc + "/lib";
        context.setAttribute("CTX", ctx);
        context.setAttribute("STATIC", staticc);
        context.setAttribute("IMG", staticc + "/img");
        context.setAttribute("JS", staticc + "/js");
        context.setAttribute("CSS", staticc + "/css");
        context.setAttribute("LIB", lib);
        context.setAttribute("JQUERY", lib + "/jquery");
        context.setAttribute("BOOTSTRAP", lib + "/bootstrap");
        context.setAttribute("WEBUPLOADER", lib + "/webuploader");

        context.setAttribute("V", DateFormat.getDateTimeInstance().format(new Date()));
    }

    @PreDestroy
    public void close() {
        context.removeAttribute("CTX");
        context.removeAttribute("STATIC");
        context.removeAttribute("IMG");
        context.removeAttribute("JS");
        context.removeAttribute("CSS");
        context.removeAttribute("LIB");
        context.removeAttribute("JQUERY");
        context.removeAttribute("BOOTSTRAP");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        WebApplicationContext webApplicationContext = (WebApplicationContext) applicationContext;
        ServletContext servletContext = webApplicationContext.getServletContext();
        log.warn("{}", servletContext);
        this.applicationContext = applicationContext;
    }

    @Override
    public void setServletContext(ServletContext servletContext) {
        log.warn("{}", servletContext);
        this.servletContext = servletContext;
    }
}
