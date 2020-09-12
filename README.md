# sample
样例工程

项目包路径以com开头前缀，后接公司名、项目名
com.yunwuye.sample 

项目名称：项目名+srv+公司名+inc+com
sample-srv-yunwuye-inc-com
   ---sample-srv-yunwuye-inc-com-start    启动服务
   ---sample-srv-yunwuye-inc-com-client   共享服务接口定义，所有项目都依赖二方库
   ---sample-srv-yunwuye-inc-com-domain   共享领域服务对象，所有项目都依赖二方库
   ---sample-srv-yunwuye-inc-com-service  double服务，实现了Client接口
   ---sample-srv-yunwuye-inc-com-biz      业务服务层
   ---sample-srv-yunwuye-inc-com-dal      业务持久层

portal项目名称：项目名+portal+公司名+inc+com
sample-portal-yunwuye-inc-com 仅实现面向公网的服务Controller控制器，依赖
   ---sample-portal-yunwuye-inc-com-start    启动服务
   ---sample-portal-yunwuye-inc-com-controller    controller
管理控制台admin项目名称：项目名+admin+公司名+inc+com
sample-admin-yunwuye-inc-com 仅实现面向管理控制台的服务Controller控制器

启动服务时通过application.yml文 件中指定spring。profiles。active: dynamic\dev分别支持不同方式多数据源启动
