## sample
   样例工程

## 项目包路径
   以com开头前缀，后接公司名、项目名(com.yunwuye.sample) 

## 项目名称：
    项目名+srv+公司名+inc+com(sample-srv-yunwuye-inc-com)
           ---sample-srv-yunwuye-inc-com-start    启动服务
           ---sample-srv-yunwuye-inc-com-client   共享服务接口定义，所有项目都依赖二方库
           ---sample-srv-yunwuye-inc-com-domain   共享领域服务对象，所有项目都依赖二方库
           ---sample-srv-yunwuye-inc-com-service  double服务，实现了Client接口
           ---sample-srv-yunwuye-inc-com-biz      业务服务层
           ---sample-srv-yunwuye-inc-com-mybatis  业务持久层

## portal项目名称：
    项目名+portal+公司名+inc+com(sample-portal-yunwuye-inc-com) 仅实现面向公网的服务Controller控制器

### 依赖
        ---sample-portal-yunwuye-inc-com-start    启动服务
        ---sample-portal-yunwuye-inc-com-controller    controller
### 管理控制台admin项目名称:
     项目名+admin+公司名+inc+com(sample-admin-yunwuye-inc-com)
         ---仅实现面向管理控制台的服务Controller控制器

## 启动服务
         --- 通过application.yml文件配置spring属性及相关配置项。
         --- spring.profiles.active: dynamic\dev\prod 分别支持不同方式多数据源启动
## 动态多个数据源实现
本系统在启动类入中增加 `@Import(DynamicDataSourceRegister.class)`,然后，在`DynamicDataSourceRegister.class`类实现`ImportBeanDefinitionRegistrar` 动态注册数据源,加载对应数据源：
           对应application-dynamic.yml配置 中的【master】数据源,通常用于存储用户、组织、字典等信息；
           对应application-dynamic.yml配中的【cluster】数据源,这是一个分布式库， 同时设置了一个key=second、与key=third两个物理库；
          通过GenericBeanDefinition生成对应`MultiRouteDataSource.class`类，将对`defaultDatasource =master` 与集群数据源`customDataSources = cluster` 注入到上面类`MultiRouteDataSource`里，然后就可以在上下文中进行通过key切换多数据源；
在切换库时可以根据`DataSourceContext.setRouterKey ("second");`方式具体调用需要的数据库

## 固定数据源实现
    MasterDataSourceConfig
    ClusterDataSourceConfig

## 本系统外部服务依赖

redis服务：
zookeeper服务：
