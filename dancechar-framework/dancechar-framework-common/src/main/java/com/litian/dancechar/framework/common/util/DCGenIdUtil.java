package com.litian.dancechar.framework.common.util;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.lang.generator.SnowflakeGenerator;
import cn.hutool.core.math.MathUtil;
import com.google.common.collect.Lists;

/**
 * 生成唯一的Id操作工具类
 *
 * @author tojson
 * @date 2021/6/19 11:16
 */
public class DCGenIdUtil {

    public static long genIdBySnowflake(){
        SnowflakeGenerator snowflake = new SnowflakeGenerator();
        return snowflake.next();
    }

    public static String genIdByUUID(){
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    public static void main(String[] args) {
        System.out.println(genIdBySnowflake());
        System.out.println(genIdByUUID());
        double a = Convert.toDouble(3000);
        double b = Convert.toDouble(300);
        System.out.println((a-b)/a);

        int m  = 3210;
        int n = 50;
        int num = m/n;
        int sub = m- num*n;
        System.out.println(num + " " +sub);
    }
}
