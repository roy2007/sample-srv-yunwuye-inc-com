package com.yunwuye.sample.result;

import java.util.List;


/**
 *
 * @author Roy
 *
 * @date 2021年3月21日-上午11:54:26
 */
class ListResult<T> extends Result<List<T>> {

    /**
     * ListResult.java -long
     */
    private static final long serialVersionUID = 2781791845414601031L;

    public static <D> ListResult<D> with(List<D> list) {
        ListResult<D> listResult = new ListResult<>();
        listResult.setData(list);
        return listResult;
    }
}
