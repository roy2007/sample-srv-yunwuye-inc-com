package com.yunwuye.sample.configuration;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 *
 * @author Roy
 *
 * @date 2020年9月6日-下午4:11:15
 */
public class MultiRouteDataSource extends AbstractRoutingDataSource {

  @Override
  protected Object determineCurrentLookupKey() {
    // 通过绑定线程的数据源上下文实现多数据源的动态切换
    return DataSourceContext.getRouterKey();
  }
}
