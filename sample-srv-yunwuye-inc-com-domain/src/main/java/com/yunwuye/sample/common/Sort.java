package com.yunwuye.sample.common;

import com.yunwuye.sample.annotation.FieldCNName;

public class Sort extends ToString {

    private static final long serialVersionUID = -217152719207119599L;

    /**排序字段名**/
    @FieldCNName(value = "排序字段",comment = "排序字段")
    private String fieldName;
    /**排序值**/
    @FieldCNName(value = "排序值",comment = "排序值")
    private String orderBy;
    /**
     * @return the fieldName
     */
    public String getFieldName() {
        return fieldName;
    }
    /**
     * @param fieldName the fieldName to set
     */
    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }
    /**
     * @return the orderBy
     */
    public String getOrderBy() {
        return orderBy;
    }
    /**
     * @param orderBy the orderBy to set
     */
    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

}
