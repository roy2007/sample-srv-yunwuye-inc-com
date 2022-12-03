package com.yunwuye.sample.configuration;

import java.sql.SQLException;
import javax.sql.DataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
// import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import com.alibaba.druid.pool.DruidDataSource;

// import com.github.pagehelper.PageInterceptor;
/**
 *
 @author Roy
 *
 @date 2020年6月7日-下午7:39:28
 */

@Deprecated
// @Configuration
// @MapperScan(basePackages = ClusterDataSourceConfig.PACKAGE, sqlSessionFactoryRef = "clusterSqlSessionFactory")
public class ClusterDataSourceConfig {
  /**
   * 配置多数据源 关键就在这里 这里配置了不同的mapper对应不同的数据源
   */
  static final String PACKAGE = "com.yunwuye.sample.dao.mapper.cluster";
  static final String MAPPER_LOCATION = "classpath:mapper/cluster/*.xml";

  @Value("${spring.datasource.druid.second.url}")
  private String url;

  @Value("${spring.datasource.druid.second.username}")
  private String username;

  @Value("${spring.datasource.druid.second.password}")
  private String password;

  @Value("${spring.datasource.druid.second.driverClassName}")
  private String driverClass;

  @Value("${spring.datasource.druid.initialSize}")
  private int initialSize;

  @Value("${spring.datasource.druid.minIdle}")
  private int minIdle;

  @Value("${spring.datasource.druid.maxActive}")
  private int maxActive;

  @Value("${spring.datasource.druid.maxWait}")
  private int maxWait;

  @Value("${spring.datasource.druid.timeBetweenEvictionRunsMillis}")
  private int timeBetweenEvictionRunsMillis;

  @Value("${spring.datasource.druid.minEvictableIdleTimeMillis}")
  private int minEvictableIdleTimeMillis;

  @Value("${spring.datasource.druid.validationQuery}")
  private String validationQuery;

  @Value("${spring.datasource.druid.testWhileIdle}")
  private boolean testWhileIdle;

  @Value("${spring.datasource.druid.testOnBorrow}")
  private boolean testOnBorrow;

  @Value("${spring.datasource.druid.testOnReturn}")
  private boolean testOnReturn;

  @Value("${spring.datasource.druid.poolPreparedStatements}")
  private boolean poolPreparedStatements;

  @Value("${spring.datasource.druid.maxPoolPreparedStatementPerConnectionSize}")
  private int maxPoolPreparedStatementPerConnectionSize;

  @Value("${spring.datasource.druid.filters}")
  private String filters;

  @Value("{spring.datasource.druid.connectionProperties}")
  private String connectionProperties;

  @Bean(name = "clusterDataSource")
  public DataSource clusterDataSource() {
    DruidDataSource dataSource = new DruidDataSource();
    dataSource.setUrl(url);
    dataSource.setUsername(username);
    dataSource.setPassword(password);
    dataSource.setDriverClassName(driverClass); // 具体配置 dataSource.setInitialSize(initialSize);
    dataSource.setMinIdle(minIdle);
    dataSource.setMaxActive(maxActive);
    dataSource.setMaxWait(maxWait);
    dataSource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
    dataSource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
    dataSource.setValidationQuery(validationQuery);
    dataSource.setTestWhileIdle(testWhileIdle);
    dataSource.setTestOnBorrow(testOnBorrow);
    dataSource.setTestOnReturn(testOnReturn);
    try {
      dataSource.setFilters("stat,wall,slf4j");
    } catch (SQLException e) {
      e.printStackTrace();
    }
    dataSource.setPoolPreparedStatements(poolPreparedStatements);
    dataSource.setMaxPoolPreparedStatementPerConnectionSize(maxPoolPreparedStatementPerConnectionSize);
    try {
      dataSource.setFilters(filters);
    } catch (SQLException e) {
      e.printStackTrace();
    }
    dataSource.setConnectionProperties(connectionProperties);
    return dataSource;
  }

  @Bean(name = "clusterTransactionManager")
  public DataSourceTransactionManager clusterTransactionManager() {
    return new DataSourceTransactionManager(clusterDataSource());
  }

  @Bean(name = "clusterSqlSessionFactory")
  public SqlSessionFactory clusterSqlSessionFactory(@Qualifier("clusterDataSource") DataSource clusterDataSource)
      throws Exception {
    final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
    sessionFactory.setDataSource(clusterDataSource);
    // 分页插件
    // Interceptor interceptor = new PageInterceptor();
    // Properties properties = new Properties();
    // 数据库
    // properties.setProperty("helperDialect", "mysql");
    // 是否将参数offset作为PageNum使用 //
    // properties.setProperty("offsetAsPageNum", "true");
    // 是否进行count查询
    // properties.setProperty("rowBoundsWithCount","true");
    // 是否分页合理化
    // properties.setProperty("reasonable", "false");
    // interceptor.setProperties(properties);
    // sessionFactory.setPlugins(new Interceptor[] {interceptor});
    sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver()
        .getResources(ClusterDataSourceConfig.MAPPER_LOCATION));
    return sessionFactory.getObject();
  }
}
