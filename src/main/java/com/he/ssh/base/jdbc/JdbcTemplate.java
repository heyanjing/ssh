package com.he.ssh.base.jdbc;

import com.he.ssh.base.core.Guava;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

public class JdbcTemplate extends org.springframework.jdbc.core.JdbcTemplate {
    private static final Logger log = LoggerFactory.getLogger(JdbcTemplate.class);

    public static final Integer PAGE_NUMBER = 1;
    public static final Integer PAGE_SIZE = 20;

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    public JdbcTemplate(DataSource dataSource) {
        super(dataSource);
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    private String buildPageSql(String sql, int pageNumber, int pageSize) {
        StringBuilder pageSQL = new StringBuilder();
        if (pageNumber < 1) {
            pageNumber = PAGE_NUMBER;
        }
        if (pageSize < 1) {
            pageSize = PAGE_SIZE;
        }
        int offset = pageSize * (pageNumber - 1);
        pageSQL.append(sql);
        pageSQL.append(" limit ").append(offset).append(", ").append(pageSize);
        log.info(pageSQL.toString());
        return pageSQL.toString();
    }

    //    **********************************************************************************************************************************
//    MEINFO:2017/12/18 16:57 find by entityClass
//    **********************************************************************************************************************************
    public <E> List<E> find(String sql, Class<E> entityClass) {
        return super.query(sql, BeanPropertyRowMapper.newInstance(entityClass));
    }

    public <E> List<E> find(String sql, Class<E> entityClass, Map<String, ?> params) {
        return this.namedParameterJdbcTemplate.query(sql, params, BeanPropertyRowMapper.newInstance(entityClass));
    }

    public <E> List<E> find(String sql, Class<E> entityClass, Object... params) {
        return super.query(sql, BeanPropertyRowMapper.newInstance(entityClass), params);
    }

    //    **********************************************************************************************************************************
//    MEINFO:2017/12/18 16:57 page by entityClass   pageNumber从1开始
//    **********************************************************************************************************************************
    public <E> Page<E> page(String sql, Class<E> entityClass, int pageNumber, int pageSize) {
        return this.page(sql, entityClass, pageNumber, pageSize, Guava.newHashMap());
    }

    public <E> Page<E> page(String sql, Class<E> entityClass, int pageNumber, int pageSize, Map<String, ?> params) {
        int count = this.queryForCount(sql, params);
        List<E> entities = Guava.newArrayList();
        if (count > 0) {
            entities = this.find(this.buildPageSql(sql, pageNumber, pageSize), entityClass, params);
        }
        return new PageImpl<>(entities, PageRequest.of(pageNumber - 1, pageSize), count);
    }

    public <E> Page<E> page(String sql, Class<E> entityClass, int pageNumber, int pageSize, Object... params) {
        int count = this.queryForCount(sql, params);
        List<E> entities = Guava.newArrayList();
        if (count > 0) {
            entities = this.find(this.buildPageSql(sql, pageNumber, pageSize), entityClass, params);
        }
        return new PageImpl<>(entities, PageRequest.of(pageNumber - 1, pageSize), count);
    }


    //    **********************************************************************************************************************************
//    MEINFO:2017/12/18 16:57 find by rowmapper
//    **********************************************************************************************************************************
    public <E> List<E> find(String sql, RowMapper<E> mapper) {
        return super.query(sql, mapper);
    }

    public <E> List<E> find(String sql, RowMapper<E> mapper, Map<String, ?> params) {
        return this.namedParameterJdbcTemplate.query(sql, params, mapper);
    }

    public <E> List<E> find(String sql, RowMapper<E> mapper, Object... params) {
        return super.query(sql, mapper, params);
    }

    //    **********************************************************************************************************************************
//    MEINFO:2017/12/18 16:57 page by rowmapper
//    **********************************************************************************************************************************
    public <E> Page<E> page(String sql, RowMapper<E> mapper, int pageNumber, int pageSize) {
        return this.page(sql, mapper, pageNumber, pageSize, Guava.newHashMap());
    }

    public <E> Page<E> page(String sql, RowMapper<E> mapper, int pageNumber, int pageSize, Map<String, ?> params) {
        int count = this.queryForCount(sql, params);
        List<E> entities = Guava.newArrayList();
        if (count > 0) {
            entities = this.find(this.buildPageSql(sql, pageNumber, pageSize), mapper, params);
        }
        return new PageImpl<>(entities, PageRequest.of(pageNumber - 1, pageSize), count);
    }

    public <E> Page<E> page(String sql, RowMapper<E> mapper, int pageNumber, int pageSize, Object... params) {
        int count = this.queryForCount(sql, params);
        List<E> entities = Guava.newArrayList();
        if (count > 0) {
            entities = this.find(this.buildPageSql(sql, pageNumber, pageSize), mapper, params);
        }
        return new PageImpl<>(entities, PageRequest.of(pageNumber - 1, pageSize), count);
    }

    //    **********************************************************************************************************************************
//    MEINFO:2017/12/18 16:57 query for count
//    **********************************************************************************************************************************
    private Integer queryForCount(String sql, Map<String, ?> params) {
        return this.namedParameterJdbcTemplate.queryForObject(this.buildCountSql(sql), params, Integer.class);
    }

    private Integer queryForCount(String sql, Object... params) {
        return super.queryForObject(this.buildCountSql(sql), params, Integer.class);
    }

    private String buildCountSql(String sql) {
        return "SELECT COUNT(*) " + StringUtils.substring(sql, StringUtils.indexOfIgnoreCase(sql, "from", 0));
    }
}
