package com.yunwuye.sample.common.base.param;

import java.util.List;
import com.yunwuye.sample.common.Sort;
import com.yunwuye.sample.common.ToString;
import com.yunwuye.sample.common.base.annotation.FieldCNName;

/**
 * @author Roy
 *
 */
public class PageParam<T> extends ToString{

    public PageParam () {
        super ();
    }

    public PageParam (Integer pageNo, Integer pageSize) {
        this.pageNo = pageNo;
        this.pageSize = pageSize;
    }

    private static final long serialVersionUID = -6515216635089719838L;
    /**
     * 当前页号
     */
    @FieldCNName (value = "当前页码", comment = "当前页码 默认第1页")
    private Integer           pageNo;
    /**
     * 每页数量
     */
    @FieldCNName (value = "每页显示条数", comment = "每页显示条数, 默认10条")
    private Integer           pageSize;
    /**
     * 排序类型
     */
    @FieldCNName (value = "排序类型", comment = "排序类型")
    private List<Sort>        sort;
    /**
     * 搜索条件,实体类的属性作为搜索条件
     */
    @FieldCNName (value = "搜索条件", comment = "搜索条件，实体类的属性作为搜索条件")
    private T                 params;

    public T getParams () {
        return params;
    }

    public void setParams (T params) {
        this.params = params;
    }

    /**
     * @return the pageNo
     */
    public Integer getPageNo () {
        if (pageNo < 1) {
            pageNo = 1;
        }
        return pageNo;
    }

    /**
     * @param pageNo
     *            the pageNo to set
     */
    public void setPageNo (Integer pageNo) {
        this.pageNo = pageNo;
    }

    /**
     * @return the pageSize
     */
    public Integer getPageSize () {
        if (pageSize <= 0) {
            pageSize = 10;
        }
        return pageSize;
    }

    /**
     * @param pageSize
     *            the pageSize to set
     */
    public void setPageSize (Integer pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * @return the sort
     */
    public List<Sort> getSort () {
        return sort;
    }

    /**
     * @param sort
     *            the sort to set
     */
    public void setSort (List<Sort> sort) {
        this.sort = sort;
    }

    public Integer getOffset () {
        return (this.pageNo - 1) * this.pageSize;
    }

    public Integer getLimit () {
        return this.pageSize;
    }
}
