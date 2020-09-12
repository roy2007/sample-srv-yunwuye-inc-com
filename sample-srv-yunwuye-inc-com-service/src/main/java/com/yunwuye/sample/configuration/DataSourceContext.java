package com.yunwuye.sample.configuration;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Roy
 *
 * @date 2020年9月6日-下午4:09:19
 */
public class DataSourceContext {
  private static final Logger log = LogManager.getLogger(DataSourceContext.class);

  // 线程级别的私有变量
  private static final ThreadLocal<String> contextHolder = new ThreadLocal<String>();

  // 存储已经注册的数据源的key
  public static List<String> dataSourceIds = new ArrayList<>();

  public static void setRouterKey(String routerKey) {
    log.debug("切换至{}数据源", routerKey);
    contextHolder.set(routerKey);
  }

  public static String getRouterKey() {
    return contextHolder.get();
  }

  /**
   * 设置数据源之前一定要先移除
   * 
   * @author Lynch
   */
  public static void clearRouterKey() {
    contextHolder.remove();
  }

  /**
   * 判断指定DataSrouce当前是否存在
   *
   * @param dataSourceId
   */
  public static boolean containsDataSource(String dataSourceId) {
    return dataSourceIds.contains(dataSourceId);
  }

}
