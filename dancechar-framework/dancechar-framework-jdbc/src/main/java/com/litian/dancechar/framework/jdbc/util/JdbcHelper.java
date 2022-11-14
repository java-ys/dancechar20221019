package com.litian.dancechar.framework.jdbc.util;

import org.springframework.jdbc.core.JdbcTemplate;

import javax.annotation.Resource;

/**
 *
 * jdbc操作工具类
 *
 * @author tojson
 * @date 2022/10/14 16:20
 */
public class JdbcHelper {

    @Resource
    private JdbcTemplate jdbcTemplate;

    private JdbcTemplate getJdbcTemplate(){
        return this.jdbcTemplate;
    }

    public void query(String sql){
    }


    public static void main(String[] args) {

    }
}
