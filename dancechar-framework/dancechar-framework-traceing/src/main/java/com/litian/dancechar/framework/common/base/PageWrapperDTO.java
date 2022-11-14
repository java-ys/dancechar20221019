package com.litian.dancechar.framework.common.base;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 包装的分页返回对象
 *
 * @author tojson
 * @date 2021/6/19 13:20
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class PageWrapperDTO<T> extends BasePage {
    private static final long serialVersionUID = 1L;

    /**
     * 总记录数
     */
    private Integer total;

    /**
     * 数据记录列表
     */
    private List<T> list;

    /**
     * 附带信息
     */
    private Object otherObj;

    public PageWrapperDTO() {
        super();
    }

    public PageWrapperDTO(Integer pageNo, Integer pageSize, Integer total, List<T> list) {
        super(pageNo, pageSize);
        this.list = list;
        this.total = total;
    }

    public PageWrapperDTO(IPage<T> iPage) {
        super((int) iPage.getCurrent(), (int) iPage.getSize());
        this.list = iPage.getRecords();
        this.total = (int) iPage.getTotal();
    }

    public void setIPage(IPage<T> iPage) {
        this.list = iPage.getRecords();
        this.total = (int) iPage.getTotal();
        setPageNo((int) iPage.getCurrent());
        setPageSize((int) iPage.getSize());
    }
}
