# work3



## 技术栈

* mysql
* maven
* mybatis



## 项目结构



### mysql

* 商品表（isdeleted字段用于解决商品的删除）
* 订单表
* 订单商品表，用于存储订单于商品之间的关系



### java

- pojo
  - Orders 订单的实体类
  - Goods 商品的实体类
  - GoodsOrders 中间表的实体类

* dao
  * 用于操作三张表的对应接口
* exception
  * NoProductException（添加订单中不存在商品）
  * PriceNotSuitableException（添加订单中价格不合理）
  * ProductNotExistException（添加订单中商品不存在）

* utils
  * MybatisUtils:获取Sqlsession对象
  * OrderUtils：由于订单的增删改查较为麻烦，所以创建了一个订单工具类来处理订单的增删改查
* resources
  - 各个mapper接口对应的xml文件
  - mybatis-config.xml配置文件
  - db.propeties 其中放置了连接数据库要用的数据，方便连接数据库
* test
  * OrderTest：测试订单的增删改查功能
  * Product：测试商品的增删改查功能

### 项目功能

* 实现了订单的增删改查，价格排序，按时间排序
