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
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import com.alibaba.druid.pool.DruidDataSource;
// import com.github.pagehelper.PageInterceptor;
/**
 *
 @author Roy
 @date 2020年6月7日-下午7:50:53
 */

// @Configuration
// @MapperScan(basePackages = { "com.yunwuye.sample.dao.mapper.master", "com.yunwuye.sample.dal.mapper" },
// sqlSessionFactoryRef = "masterSqlSessionFactory")
public class MasterDataSourceConfig {

  /** 配置多数据源 关键就在这里 这里配置了不同的mapper对应不同的数据源 */
  static final String PACKAGE = "com.yunwuye.sample.dao.mapper.master";
  static final String MAPPER_LOCATION = "classpath:mapper/master/*.xml";
  /**
   * 连接数据库信息 这个其实更好的是用配置中心完成
   */

  @Value("${spring.datasource.druid.master.url}")
  private String url;

  @Value("${spring.datasource.druid.master.username}")
  private String username;

  @Value("${spring.datasource.druid.master.password}")
  private String password;

  @Value("${spring.datasource.druid.master.driverClassName}")
  private String driverClassName;
  /**
   * 下面的配置信息可以读取配置文件，其实可以直接写死 如果是多数据源的话 还是考虑读取配置文件
   */

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

  @Bean(name = "masterDataSource")
  @Primary
  // 标志这个 Bean 如果在多个同类 Bean 候选时，该 Bean 优先被考虑。
  public DataSource masterDataSource() {
    DruidDataSource dataSource = new DruidDataSource();
    dataSource.setUrl(url);
    dataSource.setUsername(username);
    dataSource.setPassword(password);
    dataSource.setDriverClassName(driverClassName);

    // 具体配置
    dataSource.setInitialSize(initialSize);
    dataSource.setMinIdle(minIdle);
    dataSource.setMaxActive(maxActive);
    dataSource.setMaxWait(maxWait);
    dataSource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
    dataSource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
    dataSource.setValidationQuery(validationQuery);
    dataSource.setTestWhileIdle(testWhileIdle);
    dataSource.setTestOnBorrow(testOnBorrow);
    dataSource.setTestOnReturn(testOnReturn);
    dataSource.setPoolPreparedStatements(poolPreparedStatements);
    dataSource.setMaxPoolPreparedStatementPerConnectionSize(maxPoolPreparedStatementPerConnectionSize);
    /**
     * 这个是用来配置 druid 监控sql语句的 非常有用 如果你有两个数据源 这个配置哪个数据源就监控哪个数据源的sql 同时配置那就都监控
     */
    try {
      dataSource.setFilters(filters);
    } catch (SQLException e) {
      e.printStackTrace();
    }
    dataSource.setConnectionProperties(connectionProperties);
    return dataSource;
  }

  @Bean(name = "masterTransactionManager")
  @Primary
  public DataSourceTransactionManager masterTransactionManager() {
    return new DataSourceTransactionManager(masterDataSource());
  }

  @Bean(name = "masterSqlSessionFactory")
  @Primary
  public SqlSessionFactory masterSqlSessionFactory(@Qualifier("masterDataSource") DataSource masterDataSource)
      throws Exception {
    final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
    sessionFactory.setDataSource(masterDataSource);
    sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver()
        .getResources(MasterDataSourceConfig.MAPPER_LOCATION));

    // //分页插件
    // Interceptor interceptor = new PageInterceptor();
    // Properties properties = new Properties();
    // //数据库
    // properties.setProperty("helperDialect", "mysql");
    // //是否将参数offset作为PageNum使用
    // properties.setProperty("offsetAsPageNum", "true");
    // //是否进行count查询
    // properties.setProperty("rowBoundsWithCount","true");
    // //是否分页合理化 // properties.setProperty("reasonable", "false");
    // interceptor.setProperties(properties);
    // sessionFactory.setPlugins(new Interceptor[] {interceptor});

    return sessionFactory.getObject();
  }
}
