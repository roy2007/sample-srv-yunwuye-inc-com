package com.yunwuye.sample.common.base.result;

/**
 *
 * @author Roy
 *
 */
public class PageResult<T> extends Result<T>{

    private static final long serialVersionUID = 3478936368608723163L;
    /**
     * 总条数
     */
    private Integer           totalSize;
    /**
     * 当前页号
     */
    private Integer           pageNo;
    /**
     * 每页数量
     */
    private Integer           pageSize;
    /**
     * 总页数
     */
    private Integer           totalPage;
    /***
     * 偏移位置（跳过行）
     */
    private Integer           offset;

    public PageResult () {
        super ();
    }

    /**
     * 
     * @param totalSize 总条数
     * @param pageNo 当前页
     * @param pageSize 每页数量
     */
    public PageResult (Integer totalSize, Integer pageNo, Integer pageSize) {
        this.totalSize = totalSize;
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        this.totalPage = this.getTotalPage ();
    }

    public PageResult<T> with (Integer totalSize, Integer pageSize, Integer offset, T data) {
        this.totalSize = totalSize;
        this.pageSize = pageSize;
        this.offset = offset;
        this.pageNo = this.getPageNo ();
        this.setData (data);
        return this;
    }

    /**
     * 
     * @param totalSize 总条数
     * @param pageNo 当前页
     * @param pageSize 每页数量
     */
    public PageResult (Integer totalSize, Integer pageNo, Integer pageSize, T data) {
        super (data);
        this.totalSize = totalSize;
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        this.totalPage = this.getTotalPage ();
    }

    /**
     * @return the totalSize
     */
    public Integer getTotalSize () {
        return totalSize;
    }

    /**
     * @param totalSize
     *            the totalSize to set
     */
    public void setTotalSize (Integer totalSize) {
        this.totalSize = totalSize;
    }

    /**
     * @return the pageNo
     */
    public Integer getPageNo () {
        if (this.offset == null || offset == 0) {
            if (this.pageNo == null || this.pageNo < 1) {
                this.pageNo = 1;
            }
        } else if (this.offset != null && this.pageSize != null) {
            this.pageNo = this.offset / this.pageSize;
            if (this.offset % this.pageSize > 0) {
                this.pageNo = this.pageNo + 1;
            }
            // 增加当前页算
            this.pageNo = this.pageNo + 1;
        }
        return this.pageNo;
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
        if (this.pageSize != null && this.pageSize <= 0) {
            this.pageSize = 10;
        }
        return this.pageSize;
    }

    /**
     * @param pageSize
     *            the pageSize to set
     */
    public void setPageSize (Integer pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * @return the totalPage
     */
    public Integer getTotalPage () {
        if (this.totalPage != null) {
            return totalPage;
        } else if (this.pageSize != null && this.totalSize != null) {
            this.totalPage = this.totalSize / this.pageSize;
            if (this.totalSize % this.pageSize > 0) {
                this.totalPage = this.totalPage + 1;
            }
            return this.totalPage;
        } else {
            return 0;
        }
    }

    /**
     * @param totalPage
     *            the totalPage to set
     */
    public void setTotalPage (Integer totalPage) {
        this.totalPage = totalPage;
    }

    /**
     * @return the offset
     */
    public Integer getOffset () {
        return offset;
    }

    /**
     * @param offset the offset to set
     */
    public void setOffset (Integer offset) {
        this.offset = offset;
    }
}
