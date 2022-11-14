package com.litian.dancechar.framework.common.util;

import cn.hutool.core.collection.CollUtil;
import com.github.pagehelper.Page;
import com.litian.dancechar.framework.common.base.BasePage;
import com.litian.dancechar.framework.common.base.PageWrapperDTO;

import java.util.Collections;
import java.util.List;

/**
 * 分页结果处理工具类
 *
 * @author tojson
 * @date 2021/6/19 13:20
 */
public class PageResultUtil {
    private PageResultUtil() {
    }

    /**
     * 设置分页返回对象
     */
    public static <T> void setPageResult(List<T> list, PageWrapperDTO<T> pageWrapperDTO) {
        if (CollUtil.isNotEmpty(list) && list instanceof Page) {
            Page page = (Page) list;
            pageWrapperDTO.setPageNo(page.getPageNum());
            pageWrapperDTO.setTotal((int) page.getTotal());
            pageWrapperDTO.setPageSize(page.getPageSize());
            pageWrapperDTO.setList(list);
        } else {
            if(pageWrapperDTO == null){
                pageWrapperDTO = new PageWrapperDTO<>();
            }
            pageWrapperDTO.setList(Collections.emptyList());
            pageWrapperDTO.setPageNo(BasePage.DEFAULT_PAGE_NO);
            pageWrapperDTO.setTotal(0);
            pageWrapperDTO.setPageSize(BasePage.DEFAULT_SIZE);
        }
    }

    /**
     * 设置分页返回对象(带class)
     */
    public static <T,P> PageWrapperDTO<P> setPageResult(List<T> resultList, Class<P> cLassP){
        PageWrapperDTO<P> pageWrapperDTO = new PageWrapperDTO<>();
        if(CollUtil.isNotEmpty(resultList) && resultList instanceof Page){
            Page page = (Page) resultList;
            pageWrapperDTO.setPageNo(page.getPageNum());
            pageWrapperDTO.setTotal((int) page.getTotal());
            pageWrapperDTO.setPageSize(page.getPageSize());
            pageWrapperDTO.setList(DCBeanUtil.copyList(resultList, cLassP));
        } else{
            pageWrapperDTO.setList(Collections.emptyList());
            pageWrapperDTO.setPageNo(BasePage.DEFAULT_PAGE_NO);
            pageWrapperDTO.setTotal(0);
            pageWrapperDTO.setPageSize(BasePage.DEFAULT_SIZE);
        }
        return pageWrapperDTO;
    }
}