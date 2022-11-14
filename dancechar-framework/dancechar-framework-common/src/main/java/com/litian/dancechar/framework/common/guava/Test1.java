package com.litian.dancechar.framework.common.guava;

import com.google.common.base.Splitter;

import java.util.List;

public class Test1 {

    public static void main(String[] args) {
        // 将字符串转为list
        List<String> result = Splitter.on("｜").splitToList("张三｜李四");
        System.out.println(result.size() == 2);


    }
}
