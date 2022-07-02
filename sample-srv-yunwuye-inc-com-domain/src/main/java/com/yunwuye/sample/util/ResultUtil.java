package com.yunwuye.sample.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.CollectionUtils;
import com.yunwuye.sample.enums.CommonResultCode;
import com.yunwuye.sample.result.PageResult;
import com.yunwuye.sample.result.Result;

/**
 *
 * @author Roy
 *
 */
public class ResultUtil {
  /**
   *
   * 创建成功结果
   *
   * @return
   */
  public static Result<Void> createSuccessResult() {
    return createSuccessResult(null);
  }

  /**
   * 创建成功结果
   *
   * @return
   */
  public static <T> Result<T> createSuccessResult(T data) {
    Result<T> result = new Result<T>();
    result.setData(data);
    return result;
  }

  /**
   * 空的分页对象
   * 
   * @return
   */
  public static <T> PageResult<List<T>> createPageSuccess() {
    PageResult<List<T>> result = new PageResult<List<T>>();
    result.setData(new ArrayList<T>());
    result.setSuccess(Boolean.TRUE);
    return result;
  }

  /**
   *
   * @param totalSize
   * @param datas
   * @return
   */
  public static <T> PageResult<List<T>> createPageSuccess(int totalSize, List<T> datas) {
    PageResult<List<T>> result = new PageResult<List<T>>();
    result.setData(datas);
    if (CollectionUtils.isEmpty(datas)) {
      result.setData(new ArrayList<T>());
    }
    result.setTotalSize(totalSize);
    result.setSuccess(Boolean.TRUE);
    return result;
  }

  /**
   *
   * @param totalSize
   * @param datas
   * @return
   */
  public static <T> PageResult<List<T>> createPageSuccess(int totalSize, int pageNo, int pageSize, List<T> datas) {
    PageResult<List<T>> result = new PageResult<List<T>>();
    result.setData(datas);
    if (CollectionUtils.isEmpty(datas)) {
      result.setData(new ArrayList<T>());
    }
    result.setTotalSize(totalSize);
    result.setSuccess(Boolean.TRUE);
    result.setPageNo(pageNo);
    result.setPageSize(pageSize);
    return result;
  }

  public static <T> PageResult<List<T>> createPageQueryFailed(CommonResultCode commonResultcode) {
    PageResult<List<T>> result = new PageResult<List<T>>();
    result.setSuccess(Boolean.FALSE);
    result.setCode(commonResultcode.getCode());
    result.setMessage(commonResultcode.getDesc());
    return result;
  }

  public static <T> PageResult<List<T>> createPageQueryFailed(CommonResultCode commonResultcode, String resultMsg) {
    PageResult<List<T>> result = new PageResult<List<T>>();
    result.setSuccess(false);
    result.setCode(commonResultcode.getCode());
    result.setMessage(resultMsg);
    return result;
  }

  public static <T> PageResult<List<T>> createPageQueryFailed(String resultCode, String resultMsg) {
    PageResult<List<T>> result = new PageResult<List<T>>();
    result.setSuccess(false);
    result.setCode(resultCode);
    result.setMessage(resultMsg);
    return result;
  }

  public static <T> Result<T> createFailResult(CommonResultCode commonResultcode) {
    Result<T> result = new Result<T>();
    result.setSuccess(Boolean.FALSE);
    result.setCode(commonResultcode.getCode());
    result.setMessage(commonResultcode.getDesc());
    return result;
  }

  public static <T> Result<T> createFailResult(CommonResultCode commonResultcode, String desc) {
    Result<T> result = new Result<T>();
    result.setSuccess(false);
    result.setCode(commonResultcode.getCode());
    result.setMessage(desc);
    return result;
  }

  public static <T> Result<T> createFailResult(String resultCode, String desc) {
    Result<T> result = new Result<T>();
    result.setSuccess(Boolean.FALSE);
    result.setCode(resultCode);
    result.setMessage(desc);
    return result;
  }

  /**
   * 计算分页信息
   *
   * @param result
   * @param totalSize
   * @param pageNo
   * @param pageSize
   * @return
   */
  public static Integer getlimitCursorByPageResult(PageResult<?> result, int totalSize, int pageNo, int pageSize) {
    int cursorStart = 0;
    if (totalSize == 0) {
      return cursorStart;
    }
    result.setTotalSize(totalSize);
    if (pageSize == 0) {
      pageSize = result.getPageSize();
    }
    result.setPageSize(pageSize);
    int totalPage = totalSize % pageSize == 0 ? totalSize / pageSize : totalSize / pageSize + 1;
    result.setTotalPage(totalPage);
    if (pageNo > 0 && pageNo <= totalPage) {
      result.setPageNo(pageNo);
      cursorStart = (pageNo - 1) * pageSize;
    }
    return cursorStart;
  }

  public static Result<?> createFailResult(String failMessage) {
    Result<?> result = new Result<>();
    result.setSuccess(false);
    result.setCode(CommonResultCode.BIZ_FAIL.getCode());
    result.setMessage(failMessage);
    return result;
  }
}
