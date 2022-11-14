package com.litian.dancechar.framework.encrypt.example;

import cn.hutool.json.JSONUtil;
import com.google.common.collect.Lists;
import com.litian.dancechar.framework.encrypt.util.EncryptUtil;

import java.util.List;

/**
 * 加解密工具类示例
 *
 * @author tojson
 * @date 2022/08/28 23:10
 */
public class Example {
    public static void main(String[] args) throws Exception {
        // 单个对象处理
        TestVO testVO = new TestVO();
        testVO.setId("1");
        testVO.setMobile("13674486666");
        testVO.setAddress("深圳市南山区科技园");
        testVO.setEmail("tojson@126.com");
        testVO.setIdCard("21263363198708130616");
        testVO.setBankCard("252626277227");
        System.out.println(JSONUtil.toJsonPrettyStr(EncryptUtil.handleEncryptObject(testVO)));

        // 列表处理
        List<TestVO> testList = Lists.newArrayList();
        TestVO testVO2 = new TestVO();
        testVO2.setId("2");
        testVO2.setMobile("13674486667");
        testVO2.setAddress("深圳市南山区科技园2");
        testVO2.setEmail("tojson2@126.com");
        testVO2.setIdCard("212633631987081306162");
        testVO2.setBankCard("2526262772272");
        testList.add(testVO2);
        TestVO testVO3 = new TestVO();
        testVO3.setId("3");
        testVO3.setMobile("13674486666");
        testVO3.setAddress("深圳市南山区科技园3");
        testVO3.setEmail("tojson3@126.com");
        testVO3.setIdCard("212633631987081306163");
        testVO3.setBankCard("2526262772273");
        testList.add(testVO3);
        System.out.println(JSONUtil.toJsonPrettyStr(EncryptUtil.handleEncryptList(testList)));
    }
}
