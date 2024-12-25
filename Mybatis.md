

# Mybatis



# 1、简介









# 2、第一个Mybatis程序





## 2.1、搭建数据库

```sql
create database mybatis;
use mybatis;
CREATE TABLE `user`(
	`id` INT(20) NOT NULL,
	`name` VARCHAR(20) DEFAULT NULL ,
	`pwd` VARCHAR(30) DEFAULT NULL,
	PRIMARY KEY(`id`) 
	

)ENGINE = INNODB CHARSET =utf8mb4

INSERT INTO `user` (`id` ,`name` ,pwd) VALUES(1,'李笔芯',123456),(2,'楚天矫',20021020)
```





## 2.2、创建一个项目以及模块

1、创建一个maven项目，检查maven配置

2、删除src，相当于创建父工程

3、导入依赖

* junit
* mybatis
* mysql驱动

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <!--父工程-->
    <groupId>com.kaung</groupId>
    <artifactId>mybatis-study</artifactId>
    <packaging>pom</packaging>
    <version>1.0-SNAPSHOT</version>
    <modules>
        <module>mybatis-01</module>
    </modules>

    <!--导入依赖-->
    <dependencies>
        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version>4.12</version>
            <scope>test</scope>
        </dependency>

        <dependency>
            <groupId>org.mybatis</groupId>
            <artifactId>mybatis</artifactId>
            <version>3.5.6</version>
        </dependency>

        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>8.0.33</version>
        </dependency>

    </dependencies>


    <!--在build中配置resources，来防止我们资源导出失败的问题-->
    <build>
        <resources>
            <resource>
                <directory>src/main/resources</directory>
                <includes>
                    <include>**/*.properties</include>
                    <include>**/*.xml</include>
                </includes>
                <filtering>true</filtering>
            </resource>
            <resource>
                <directory>src/main/java</directory>
                <includes>
                    <include>**/*.properties</include>
                    <include>**/*.xml</include>
                </includes>
                <filtering>true</filtering>
            </resource>
        </resources>
    </build>



    <properties>
        <maven.compiler.source>17</maven.compiler.source>
        <maven.compiler.target>17</maven.compiler.target>
    </properties>


</project>
```



4、在src的main目录下的resource创建一个xml文件（mybatis-comfig.xml）用来配置资源

注意：

* ==每一个Mapper.XML都需要在Mybatis核心配置文件中注册！！！==
* url中必须设置字符集编码为==utf-8==,不然写中文注释会报错  ，或者把xml中encoding=uft-8 改成utf8

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
    <environments default="development">
        <environment id="development">
            <transactionManager type="JDBC"/>
            <dataSource type="POOLED">
                <property name="driver" value="com.mysql.cj.jdbc.Driver"/>
                <property name="url" value="jdbc:mysql://localhost:3306/mybatis?characterEncoding=utf-8&amp;serverTimezone=UTC&amp;useSSL=false"></property>
                <property name="username" value="root"/>
                <property name="password" value="123456"/>
            </dataSource>
        </environment>
    </environments>
    <!--每一个Mapper.XML都需要在Mybatis核心配置文件中注册！！！-->
    <mappers>
        <mapper resource="com/kuang/dao/UserMapper.xml"/>
    </mappers>
</configuration>
```



5、创建Mybatis工具类获取sqlsession对象（用来执行sql的对象）

```java
public class MybatisUtils {
    private static SqlSessionFactory sqlSessionFactory;

    static{
        try {
            String resource = "mybatis-config.xml";
            InputStream inputStream = Resources.getResourceAsStream(resource);
            //创建工厂对象
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //获取sqlSession对象
    public static SqlSession getSqlSession(){
        return  sqlSessionFactory.openSession();
    }
}
```





## 2.3、编写代码

1、pojo类

```java
public class User {
    private int id;
    private String name;
    private String pwd;

    public User() {
    }

    public User(int id, String name, String pwd) {
        this.id = id;
        this.name = name;
        this.pwd = pwd;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
    
}
```



2、Dao接口（Mapper接口）

```java
public interface UserDao {
    //查询所有
     List<User> selectList();
}

```



3、接口实现类由原来的impl实现类转换成Mapper配置文件（UserDaoImpl  ->UserMapper   ）

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.ac.cn//DTD Mapper 3.0//EN"
        "https://mybatis.ac.cn/dtd/mybatis-3-mapper.dtd">

<!--命名空间 绑定一个一个对应的Dao/Mapeper接口-->
<mapper namespace="com.kuang.dao.UserDao">
    <!-- id对应方法名字 resultType结果集对象类型(全限类名) -->
    <select id="selectList" resultType="com.kuang.pojo.User">
        select * from user
    </select>
</mapper>
```



## 2.4、编写测试类

```java
public class UserDaoTest {
    @Test
    public void selectTest(){
        //获取sqlsession(执行sql的对象)
        SqlSession sqlSession = MybatisUtils.getSqlSession();

        //执行sql
        UserDao mapper = sqlSession.getMapper(UserDao.class);
        List<User> users = mapper.selectList();

        System.out.println(users);

        //关闭sqlSession
        sqlSession.close();
    }
}
```



# 3、CRUD



## 1、namespace

指定实现的是哪个mapper接口中的方法



## 2、select

* **id**：对应接口中的方法名
* **resultType**：对应sql语句执行的返回值的数据类型，集合类型写它的泛型就行
* **parameterType**：对应参数类型



1、编写接口

```java
     //根据id查询用户
     User selectById(int id);
```



2、编写对应的mapper中的sql语句

```xml
    <select id="selectById" resultType="com.kuang.pojo.User" parameterType="int" >
        select * from user where id= #{id}
    </select>
```



## 3、insert，update，delete

* **必须提交事务**
* 返回值类型统一为int并且不需要填写parameType

1、编写接口

```java
     //添加用户
     int insertUser(User user);

     //修改用户
     int updateUser(User user);

     //删除用户
     int deleteById(int id);
```

2、编写mapper.xml中的sql语句

```xml
	<insert id="insertUser" parameterType="com.kuang.pojo.User">
        insert into mybatis.user (id,name,pwd) values(#{id},#{name},#{pwd})
    </insert>

    <update id="updateUser" parameterType="com.kuang.pojo.User">
        update mybatis.user set name= #{name} ,pwd=#{pwd} where id= #{id}
    </update>

    <delete id="deleteById" parameterType="int">
        delete from user where id =#{id}
    </delete>
```





## 4、万能map

如果我们实体类的参数或表的字段过多，我们可以考虑使用map作为方法参数

```java
     //根据id和名字查询用户(使用map参数)
     User selectByIdAndName(Map<String,Object> map);
```



```xml
    <!-- 参数名字可以任意，但map中的键要与参数名字一致！！！ -->
    <select id="selectByIdAndName" parameterType="map" resultType="com.kuang.pojo.User">
        select * from user where id=#{keyId} and name=#{keyName}
    </select>
```



```java
    //通过map查询用户
    @Test
    public void selectByMap(){
        SqlSession sqlSession=MybatisUtils.getSqlSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        Map<String,Object> map=new HashMap<>();
        //键要与sql语句中的参数一致！！！
        map.put("keyId",1);
        map.put("keyName","李笔芯");

        //执行sql
        User user = mapper.selectByIdAndName(map);
        System.out.println(user);
    }
```





* **Map传递参数**：直接在sql中取出key即可  【parameter = map】
* **对象传递参数**：直接在sql中取对象的属性即可 【parameter = object】

* 只有一个**基本类型参数**的情况下，可以直接在sql中取到 

多个参数用Map，**或者注解**









# 4、配置解析



## 1、核心配置文件（mybatis-config.xml）

* **mybatis-config.xml**
* MyBatis 的配置文件包含了会深深影响 MyBatis 行为的设置和属性信息

- configuration（配置）
  - [properties（属性）](https://mybatis.p2hp.com/configuration.html#properties)
  - [settings（设置）](https://mybatis.p2hp.com/configuration.html#settings)
  - [typeAliases（类型别名）](https://mybatis.p2hp.com/configuration.html#typeAliases)
  - [typeHandlers（类型处理器）](https://mybatis.p2hp.com/configuration.html#typeHandlers)
  - [objectFactory（对象工厂）](https://mybatis.p2hp.com/configuration.html#objectFactory)
  - [plugins（插件）](https://mybatis.p2hp.com/configuration.html#plugins)
  - environments（环境配置）
    - environment（环境变量）
      - transactionManager（事务管理器）
      - dataSource（数据源）
  - [databaseIdProvider（数据库厂商标识）](https://mybatis.p2hp.com/configuration.html#databaseIdProvider)
  - [mappers（映射器）](https://mybatis.p2hp.com/configuration.html#mappers)





## 2、环境配置（Environment）

MyBatis 可以配置成适应多种环境

**不过要记住：尽管可以配置多个环境，但每个 SqlSessionFactory 实例只能选择一种环境。**



Mybatis默认的事务管理器就是JDBC   连接池：POOLED





## 3、属性（propertites）

编写一个配置文件（db.properties）

```prope
driver=com.mysql.cj.jdbc.Driver
url=jdbc:mysql://localhost:3306/mybatis?characterEncoding=utf-8&serverTimezone=UTC&useSSL=false
username=root
password=123456
```



在配置文件（mybatis-config.xml）中引入

```xml
    <!--引入外部配置文件-->
    <properties resource="db.properties">
        
    </properties>

    <environments default="development">
        <environment id="development">
            <transactionManager type="JDBC"/>
            <dataSource type="POOLED">
                <property name="driver" value="${driver}"/>
                <property name="url" value="${url}"></property>
                <property name="username" value="${username}"/>
                <property name="password" value="${password}"/>
            </dataSource>
        </environment>
    </environments>
```



* 可以直接引入外部文件
* 可以在其中增加一些属性配置

```xml
    <properties resource="db.properties">
        <property name="username" value="root"/>
        <property name="password" value="123456"/>
    </properties>
```

* 如果两个文件有同一个字段，**优先使用外部配置文件**



## 4、类型别名（typeAliases）

* 类型别名只是 Java 类型的缩写名称
* 其存在只是为了减少完全限定类名的冗余输入

==1、第一种方式（typeAlias）==

```xml
	<typeAliases>
        <typeAlias type="com.kuang.pojo.User" alias="User"></typeAlias>
    </typeAliases>
```

* 有了这个配置，**User**现在可以在任何可以使用的地方使用了com.kuang.pojo.User



==2、第二种方式（package)==   

```xml
    <typeAliases>
        <package name="com.kuang.pojo"/>
    </typeAliases>
```



指定 MyBatis 将从哪个包中搜索 bean，每一个在包 `domain.blog` 中的 Java Bean，**在没有注解的情况下，会使用 Bean 的首字母小写的非限定类名**来作为它的别名。 比如 `domain.blog.Author` 的别名为 `author`；**若有注解，则别名为其注解值**

  

在实体类比较少的时候，使用第一种方式

如果实体类十分多，可以使用第二种方式

第一种方式可以DIY

第二种则不行，如果非要改，可以在实体类上**添加注解**

```java
@Alias("User")
public class User {}
```



下面是一些为常见的 Java 类型内建的类型别名。它们都是不区分大小写的，注意，为了应对原始类型的命名重复，采取了特殊的命名风格。

| 别名                      | 映射的类型   |
| ------------------------- | ------------ |
| _byte                     | byte         |
| _char (since 3.5.10)      | char         |
| _character (since 3.5.10) | char         |
| _long                     | long         |
| _short                    | short        |
| _int                      | int          |
| _integer                  | int          |
| _double                   | double       |
| _float                    | float        |
| _boolean                  | boolean      |
| string                    | String       |
| byte                      | Byte         |
| char (since 3.5.10)       | Character    |
| character (since 3.5.10)  | Character    |
| long                      | Long         |
| short                     | Short        |
| int                       | Integer      |
| integer                   | Integer      |
| double                    | Double       |
| float                     | Float        |
| boolean                   | Boolean      |
| date                      | Date         |
| decimal                   | BigDecimal   |
| bigdecimal                | BigDecimal   |
| biginteger                | BigInteger   |
| object                    | Object       |
| date[]                    | Date[]       |
| decimal[]                 | BigDecimal[] |
| bigdecimal[]              | BigDecimal[] |
| biginteger[]              | BigInteger[] |
| object[]                  | Object[]     |
| map                       | Map          |
| hashmap                   | HashMap      |
| list                      | List         |
| arraylist                 | ArrayList    |
| collection                | Collection   |
| iterator                  | Iterator     |





## 5、设置（settings）

![image-20241107183217579](C:\Users\33822\AppData\Roaming\Typora\typora-user-images\image-20241107183217579.png)

```xml
    <settings>
        <setting name="logImpl" value="STDOUT_LOGGING"/>
        <!--开启驼峰命名自动映射-->
        <setting name="mapUnderscoreToCamelCase" value="true"/>
    </settings>
```







## 6、映射器（mappers）

告诉MyBatis 到哪里去找到sql语句

MapperRegistry：注册绑定我们的Mapper文件

方式一：

```xml
<!-- 使用相对于类路径的资源引用 -->
<mappers>
  <mapper resource="org/mybatis/builder/AuthorMapper.xml"/>
  <mapper resource="org/mybatis/builder/BlogMapper.xml"/>
  <mapper resource="org/mybatis/builder/PostMapper.xml"/>
</mappers>
```



方式二：使用class标签

```xml
<!-- 使用映射器接口实现类的完全限定类名 -->
<mappers>
  <mapper class="org.mybatis.builder.AuthorMapper"/>
  <mapper class="org.mybatis.builder.BlogMapper"/>
  <mapper class="org.mybatis.builder.PostMapper"/>
</mappers>
```

注意点：

* 接口和他的mapper配置必须**同名**
* 接口和他的mapper配置必须在**同一个包**下









## 7、生命周期和作用域

生命周期和作用域类别是至关重要的，因为错误的使用会导致非常严重的并发问题。



#### SqlSessionFactoryBuilder

* 一旦创建了 SqlSessionFactory，就不再需要它了
* 局部方法变量



#### SqlSessionFactory

* 可以想象为**数据库连接池**

* SqlSessionFactory 一旦被创建就应该在应用的运行期间一直存在
* 使用单例模式或者静态单例模式



#### SqlSession

* **连接到连接池的请求**
* SqlSession 的实例不是线程安全的，因此是不能被共享的，所以它的最佳的作用域是请求或方法作用域
* 用完之后，就关闭它，否则资源呢占用















# 5、解决属性名和字段名不一致的问题



第一种解决办法：添加别名，最简单最暴力

```xml
    <select id="selectList" resultType="User">
        select `id`,name,`pwd` as password from user
    </select>
```



第二种解决办法：将resultType改为resultMap













## 5.1、结果集映射（ResultMap）

字段映射属性

```xml
    <!--结果集映射-->
    <resultMap id="UserMap" type="User">
        <!-- column数据库中的字段 property实体类中的属性 -->
        <result column="id" property="id"/>
        <result column="name" property="name"/>
        <result column="pwd" property="password"/>
    </resultMap>
    
    <select id="selectList" resultMap="UserMap">
        select `id`,name,`pwd` from user
    </select>
```



* 你完全可以不用显式地配置它们（属性和字段原本就相等）

```xml
    <!--结果集映射-->
    <resultMap id="UserMap" type="User">
        <!-- column数据库中的字段 property实体类中的属性 -->
        <result column="pwd" property="password"/>
    </resultMap>

    <select id="selectList" resultMap="UserMap">
        select `id`,name,`pwd` from user
    </select>
```







# 6、日志（logImpl）





## 6.1、日志工厂

如果一个数据库操作出现了异常，我们需要排错，日志就是最好的助手

曾经：sout，debug

现在：日志工厂

* STDOUT_LOGGING

* SLF4J



**STDOUT_LOGGING标准日志输出**

```xml
    <settings>
        <setting name="logImpl" value="STDOUT_LOGGING"/>
    </settings>
```









## 6.2、Log4j

1、需要导包

```xml
<dependency>
    <groupId>log4j</groupId>
    <artifactId>log4j</artifactId>
    <version>1.2.17</version>
</dependency>
```



2、编写logj.properties

```properties
# Global logging configuration
# 设置日志输出级别以及输出目的地，可以设置多个输出目的地，开发环境下，日志级别要设置成DEBUG或者ERROR
# 前面写日志级别，逗号后面写输出目的地：我自己下面设置的目的地相对应，以逗号分开
# log4j.rootLogger = [level],appenderName1,appenderName2,…
log4j.rootLogger=DEBUG,CONSOLE,LOGFILE

#### 控制台输出 ####
log4j.appender.CONSOLE=org.apache.log4j.ConsoleAppender
# 输出到控制台
log4j.appender.CONSOLE.Target = System.out
# 指定控制台输出日志级别
log4j.appender.CONSOLE.Threshold = DEBUG
# 默认值是 true, 表示是否立即输出
log4j.appender.CONSOLE.ImmediateFlush = true
# 设置编码方式
log4j.appender.CONSOLE.Encoding = UTF-8
# 日志输出布局
log4j.appender.CONSOLE.layout=org.apache.log4j.PatternLayout
# 如果日志输出布局为PatternLayout 自定义级别，需要使用ConversionPattern指定输出格式
log4j.appender.CONSOLE.layout.ConversionPattern=%d{yyyy-MM-dd HH:mm:ss} %5p (%c:%L) - %m%n



#### 输出错误信息到文件 ####
log4j.appender.LOGFILE=org.apache.log4j.FileAppender
# 指定输出文件路径
#log4j.appender.LOGFILE.File =F://Intellij idea/logs/error.log
log4j.appender.LOGFILE.File =./logs/error.log 

#日志输出到文件，默认为true
log4j.appender.LOGFILE.Append = true
# 指定输出日志级别
log4j.appender.LOGFILE.Threshold = ERROR
# 是否立即输出，默认值是 true,
log4j.appender.LOGFILE.ImmediateFlush = true
# 设置编码方式
log4j.appender.LOGFILE.Encoding = UTF-8
# 日志输出布局
log4j.appender.LOGFILE.layout = org.apache.log4j.PatternLayout
# 如果日志输出布局为PatternLayout 自定义级别，需要使用ConversionPattern指定输出格式
log4j.appender.LOGFILE.layout.ConversionPattern = %-d{yyyy-MM-dd HH:mm:ss}  [ %t:%r ] - [ %p ]  %m%n
```



3、配置log4j为日志的使用

```xml
    <settings>
        <setting name="logImpl" value="LOG4J"/>
    </settings>
```



4、创建日志对象： **static final Logger logger = Logger.getLogger(XXX.class)**; //参数为当前所在类的类文件  (==导入包：org.apache.log4j.Logger==)











# 7、注解开发



## 7.1、面向接口编程

* 面向接口编程是开发程序的功能先定义接口,接口中定义约定好的功能方法声明,通过实现该接口进行功能的实现
* 软件的功能要进行升级或完善,开发人员只需要创建不同的新类重新实现该接口中所有方法



**面向接口编程的好处**

* **降低了程序的耦合性**.其能够最大限度的解耦，所谓解耦既是解耦合的意思，它和耦合相对。耦合就是联系，耦合越强，联系越紧密。在程序中紧密的联系并不是一件好的事情，因为两种事物之间联系越紧密，你更换其中之一的难度就越大，扩展功能和debug的难度也就越大。
* **易扩展.** 我们知道程序设计的原则是对修改关闭,对新增开放.面向接口编程扩展功能只需要创建新实现类重写接口方法进行升级扩展就可以,达到了在不修改源码的基础上扩展的目的.

- **易维护**



## 7.2、使用注解开发

1、注解在接口中实现

```java
public interface UserMapper {
     //查询所有用户
     @Select("select * from user ")
     List<User> selectList(); 
}
```



2、需要在核心配置文件中绑定接口

```xml
    <mappers>
       <mapper class="com.kuang.dao.UserMapper"></mapper>
    </mappers>
```





本质：**反射机制实现**



## 7.3、CRUD

可以在工具类创建sqlsession对象时设置自动提交事务

```java
    public static SqlSession getSqlSession(){
        //默认false(默认关闭自动提交)
        return  sqlSessionFactory.openSession(true);
    }
```



编写接口

```java
@Alias("User")
public interface UserMapper {
     //查询所有用户
     @Select("select * from user ")
     List<User> selectList();

     //通过id查询用户,基本数据类型和String类型都要加上@Param注解，sql中的参数名称与@param注解名称一致
     @Select("select * from user where id=#{Uid}")
     User selectById(@Param("Uid") int id);

     //添加用户
     @Insert("insert into user (`id`,`name`,`pwd`) values(#{id},#{name},#{pwd})")
     int insertUser(User user);

     //修改用户
     @Update("update `user` set name =#{name},pwd=#{pwd} where id=#{id}")
     int updateUser(User user);

     //删除用户
     @Delete("delete from user where id=#{id}")
     int delete(@Param("id") int id);

}
```



 **注意：我们必须要将接口绑定到我们的核心配置文件中**



关于**@Param()注解**

* 基本类型或这String类型，需要加上
* 引用类型不需要
* 我们在sql中引用的就是我们这里的@Param( )中设定的属性名



==#{}能够防止sql注入，${}不行==





# 8、Lombok

Lombok**能以简单的注解形式来简化java代码**，提高开发人员的开发效率。

使用方式：

* 添加插件

* 导入jar包

  ```xml
    <!-- https://mvnrepository.com/artifact/org.projectlombok/lombok -->
          <dependency>
              <groupId>org.projectlombok</groupId>
              <artifactId>lombok</artifactId>
              <version>1.18.34</version>
              <scope>provided</scope>
          </dependency>
  ```

* 在实体类上加注解

```java
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Teacher {
    private int id;
    private String name;
}
```





* ==@Data==：添加getter  and   setter方法   equals，hashcode  和toString方法

* ==@AllArgsConstructor==：添加全参构造

* ==@NoArgsConstrutor==:	添加无参构造

  




# 9、多表查询





## 9.1、一对一处理



例如：

* 多个学生，对应一个老师（这里的老师就是班主任）
* 对于学生这边而言， 关联...   一个学生，关联一个老师【一对一】
* 对于老师而言，集合，一个老师，有很多学生【一对多】



老师实体类

```java
public class Student {
    private int id;
    private String name;
}
```



学生实体类

```java
public class Student {
    private int id;
    private String name;
    private Teacher teacher; //数据库表中用tid 表示老师信息
}
```



### **第一种方式：按照结果集嵌套处理**



查询所有学生

```xml
    <select id="selectAll" resultMap="StudentMap">
        SELECT s.`id`,s.`name`,s.`tid` ,t.`name` tname FROM student s, teacher t
        WHERE s.`tid`=t.`id`
    </select>

    <resultMap id="StudentMap" type="Student">
        <result property="id" column="id"/>
        <result property="name" column="name"/>
        <!-- 一个对象就用association -->
        <association property="teacher" javaType="Teacher">
            <result property="id" column="tid"/>
            <result property="name" column="tname"/>
        </association>
    </resultMap>
```







### **第二种方式：按照查询嵌套处理**

这里不做详解，如果想了解参看狂神mybatis教程















## 9.2、一对多处理

例子：一个老师拥有多个学生

学生实体类

```java
public class Student {
    private int id;
    private String name;
    private int tid;
}
```

老师实体类

```java
public class Teacher {
    private int id;
    private String name;
    List<Student> list;
}
```



### 第一种方式：**按照结果嵌套处理**

```xml
    <resultMap id="TeacherMap" type="Teacher">
        <result column="tid" property="id"/>
        <result column="tname" property="name"/>
        <!--集合用collection 用ofType填泛型 -->
        <collection property="list" ofType="Student">
            <result property="id" column="id"/>
            <result property="name" column="name"/>
            <result property="tid" column="tid"/>
        </collection>
    </resultMap>

    <select id="selectAll" resultMap="TeacherMap">
        SELECT s.`id`,s.`name`,s.`tid` ,t.`name` tname FROM student s, teacher t
        WHERE s.`tid`=t.`id`
    </select>
```





### 第二种方式：按照查询嵌套处理

请看狂神，这里不介绍







## 小结

1、关联 - association 【多对一】

2、 集合 - collection   【一对多】

3、 javaType &  ofType

* JavaType 用来指定实体类中属性的类型
* ofType 用来映射到List或者集合中的pojo类型，泛型中的约束类型





## 9.3、多对多处理

例如：一个商品对应着多条订单，一个订单又对应着多件商品【多对多】

**多对多关系其实我们看成是双向的一对多关系。**

































# 11、动态SQL

 **什么是动态sql：根据不同的条件按生成不同的sql的语句**

动态 SQL 是 MyBatis 的强大特性之一。如果你使用过 JDBC 或其它类似的框架，你应该能理解根据不同条件拼接 SQL 语句有多痛苦，例如拼接时要确保不能忘记添加必要的空格，还要注意去掉列表最后一个列名的逗号。利用动态 SQL，可以彻底摆脱这种痛苦。

- if
- choose (when, otherwise)
- trim (where, set)
- foreach





## 11.1、搭建环境



```sql
CREATE TABLE `mybatis`.`blog` ( 
    `id` VARCHAR(50) NOT NULL COMMENT '博客id', 
    `title` VARCHAR(100) NOT NULL COMMENT '博客标题', 
    `author` VARCHAR(30) NOT NULL COMMENT '作者', 
    `create_time` DATETIME NOT NULL COMMENT '创建时间', 
    `views` INT(30) NOT NULL COMMENT '浏览量', PRIMARY KEY (`id`) 
) ENGINE=INNODB CHARSET=utf8mb4 COLLATE=utf8mb4_bin; 
```





## 11.2、IF

**如果test中的条件成立，则会在where语句后拼接，可拼接多个条件语句**

```xml
    <select id="selectIf" resultType="Blog" parameter="map">
        select * from blog where 1=1
        <if test="id!=null">
            and id=#{id}
        </if>
        <if test="title!=null">
            and title =#{title}
        </if>
    </select>
```













## choose（when，otherwise）

有时候，我们不想使用所有的条件，而只是想从多个条件中选择一个使用。针对这种情况，MyBatis 提供了 choose 元素，它有点像 Java 中的 **switch** 语句。

**从上往下执行，至多执行一条**

```xml
    <select id="selectChoose" resultType="Blog" parameterType="map">
        select * from blog where 1=1
        <choose>
            <when test="id!=null">
                and id=#{id}
            </when>
            <when test="title!=null">
                and title=#{title}
            </when>
            <otherwise>
                and views>5000
            </otherwise>
        </choose>
    </select>
```















## 11.3、trim（where，set）



### Where标签

***where*** 元素只会在子元素返回任何内容的情况下才插入 “WHERE” 子句。而且，若子句的开头为 “AND” 或 “OR”，*where* 元素会自动将它们去除

```xml
    <select id="selectIf" resultType="Blog" parameterType="map">
        select * from blog
        <where>
            <if test="id!=null">
                and id=#{id}
            </if>
            <if test="title!=null">
                and title =#{title}
            </if>
        </where>
    </select>
```



### Set标签

***set*** 元素会动态地在行首插入 SET 关键字，并会自动删掉额外的逗号（这些逗号是在使用条件语句给列赋值时引入的）。

```xml
    <update id="update" parameterType="map">
        update blog
        <!--set元素会动态地在行首插入 SET 关键字-->
        <set>
            <if test="title!=null">
                title=#{title},
            </if>
            <if test="author!=null">
                author=#{author},
            </if>
        </set>
        <where>
            <if test="id!=null">
                and id=#{id}
            </if>
        </where>
    </update>
```





### Trim标签

自定义 trim 元素可以实现你自己想要的拼接功能

比如，和 *where* 元素等价的自定义 trim 元素为：

```xml
<!--prefix表示前缀，会在行首插入WHERE关键字  prefixOverrides能够去除多余的前缀内容，如and  or-->
<trim prefix="WHERE" prefixOverrides="AND |OR ">
  ...
</trim>
```



或者，你可以通过使用*trim*元素来达到和set同样的效果：

```xml
<!--suffxoverrides用于去除多余的后缀内容，如,-->
<trim prefix="SET" suffixOverrides=",">
  ...
</trim>
```



==所谓的动态SQL，本质不过是根据一些逻辑条件，去拼接我们想要的sql语句==





## 11.4、ForEach



动态 SQL 的另一个常见使用场景是对集合进行遍历（尤其是在构建 IN 条件语句的时候）

例如

```sql
select * from blog where id in(?,?....,?) -- 参数为集合
```

```xml
    <select id="selectforeach" parameterType="map" resultType="Blog">
        select * from blog
        <where>
            <!-- collection为传入的集合参数 item为集合中的元素 -->
            <!-- open为开头，separator为间隔符 close为结尾 -->
            <foreach collection="list" item="id" open="id in(" separator="," close=")">
                <!-- 这里填写间隔符之前要拼接的语句 -->
                #{id}
            </foreach>
        </where>
    </select>
```



又比如

```sql
select * from where (id=? or id=? or.....or id=?)
```

```xml
    <select id="selectforeach2" parameterType="map" resultType="Blog">
        select * from blog
        <where>
            <foreach collection="list" open="(" separator="or" close=")" item="id">
                id=#{id}
            </foreach>
        </where>
    </select>
```





## 11.5、SQL片段

我们可以将 常用的sql语句提取出来，**方便复用**

1、使用sql标签抽取常用的部分

```xml
    <sql id="AllArgs">
        select * from blog
    </sql>
```



2、使用include标签引用sq片段

```xml
<select id="selectAll" resultType="Blog">
    <include refid="AllArgs"/>
</select>
```



==动态SQL就是在拼接SQL语句，我们只要保证SQL的正确性，按照sql的格式，去排列组合就可以了==

**建议：先在mysql中写出完整的sql**















