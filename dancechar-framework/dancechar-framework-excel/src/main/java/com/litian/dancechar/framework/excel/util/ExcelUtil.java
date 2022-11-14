package com.litian.dancechar.framework.excel.util;

import com.alibaba.excel.EasyExcel;
import com.litian.dancechar.framework.excel.test.DemoData;

import java.util.ArrayList;
import java.util.List;

public class ExcelUtil {

    /**
     * 循环设置要添加的数据，最终封装到list集合中
     */
    private static List<DemoData> getData(){
        List<DemoData> demoData = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            DemoData data = new DemoData();
            data.setSno(i);
            data.setName("Eric" + i);
            demoData.add(data);
        }
        return demoData;
    }

    public static void main(String[] args) {
        //实现excel写的操作
        //1、设置写入文件夹地址和excel文件名称
        String fileName = "/Users/guohg/project/dancechar/write.xlsx";
        //2、调用EasyExcel里面方法实现写的操作
        //write两个参数：参数1：文件路径名称   参数2：参数实体类class
        EasyExcel.write(fileName, DemoData.class).sheet("学生列表")
                .doWrite(getData());
    }
}
