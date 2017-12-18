package com.he.ssh.base.jdbc;


public class Sqls {
    public static final Integer PAGE_NUMBER = 1;
    public static final Integer PAGE_SIZE = 20;


    public static String buildPageSql(String sql, int pageNumber, int pageSize) {
        StringBuilder pageSQL = new StringBuilder();
        if (pageNumber < 1) {
            pageNumber = PAGE_NUMBER;
        }
        if (pageSize < 1) {
            pageSize = PAGE_SIZE;
        }
        int offset = pageSize * (pageNumber - 1);
        pageSQL.append(sql);
        pageSQL.append(" limit ").append(offset).append(", ").append(pageSize);   // limit can use one or two '?' to pass paras
        return pageSQL.toString();
    }


}
