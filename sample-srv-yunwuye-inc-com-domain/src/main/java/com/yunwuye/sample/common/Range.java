package com.yunwuye.sample.common;

import com.yunwuye.sample.common.base.annotation.FieldCNName;
import com.yunwuye.sample.common.base.enums.CompositionTypeEnum;

/**
 * 范围字段定义
 *
 * @author Roy
 * @param <T>
 */
public class Range<T> extends ToString {
    private static final long serialVersionUID = -6293646927313958818L;
    @FieldCNName(value = "起始值",comment = "超始值")
    private T from;
    @FieldCNName(value = "结束值",comment = "结束值")
    private T to;
    @FieldCNName(value = "比较类型 ",comment = "比较类型，默认大于等于左边，小于等右边")
    private CompositionTypeEnum compositionType;

    public Range() {
        super();
        this.compositionType = CompositionTypeEnum.GE_LE;
    }

    /**
     * @return the from
     */
    public T getFrom() {
        return from;
    }

    /**
     * @param from
     *            the from to set
     */
    public void setFrom(T from) {
        this.from = from;
    }

    /**
     * @return the to
     */
    public T getTo() {
        return to;
    }

    /**
     * @param to
     *            the to to set
     */
    public void setTo(T to) {
        this.to = to;
    }

    /**
     * @return the compositionType
     */
    public CompositionTypeEnum getCompositionType() {
        return compositionType;
    }

    /**
     * @param compositionType
     *            the compositionType to set
     */
    public void setCompositionType(CompositionTypeEnum compositionType) {
        this.compositionType = compositionType;
    }
}
