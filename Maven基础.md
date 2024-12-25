# 1、Maven基础

好的课程：https://www.bilibili.com/video/BV1Ah411S7ZE?vd_source=cf73bcb2bca7d2b73f3f5936cfb6074d

## 1.1、Maven简介



**Maven是什么**

* Maven的本质是一个**项目管理工具**，将项目开发和管理过程抽象成一个项目对象模型（pom）
* **POM（Project Obeject  Model）**：项目对象模型，pom.xml文件就代表着一个项目m，==把一个项目以一个对象的形式进行管理==

![image-20241031231614859](C:\Users\33822\AppData\Roaming\Typora\typora-user-images\image-20241031231614859.png)

**Maven的作用**

* **项目构建**：提供一系列自动化项目构建方式
* **依赖管理**：管理jar包，避免资源间的版本冲突

* **统一开发结构**：提供标准统一的项目结构

![image-20241031231942575](C:\Users\33822\AppData\Roaming\Typora\typora-user-images\image-20241031231942575.png)

## 1.2、下载和安装



下载地址：https://maven.apache.org/download.cgi

Maven配置：

​	1、添加到环境变量

​	2、设置自己的本地仓库

​	3、配置镜像资源，加快下载速度



## 1.3、Maven基础概念



### 1.3.1、仓库

* **仓库**：用于存储资源，包含各种jar包
* **仓库分类**： 
  * **本地仓库**：自己电脑上的仓库，==可以连接远程仓库获取资源==
  * **远程仓库：**非本机电脑上的仓库，==为本地仓库提供资源==
    * 中央仓库：Maven公司团队管理的仓库，存储所有开源资源
    * 私服：部门或公司范围内存储资源的仓库，可以包含一些中央仓库不拥有的资源
* **私服的作用**：
  * 保存自己开发的jar，不想给别人用
  * 一定范围内共享资源，但近对内共享



### 1.3.2、坐标

**作用**：**用来查找仓库中的资源**

仓库就好比菜鸟驿站，坐标相当于取件码，资源相当于快递



**Maven坐标主要组成方式**

* **groupId**：项目隶属的组织名
* **artifactId**: 项目名
* **version**：版本号







## 1.4、第一个Maven项目









## 1.5、依赖管理



**依赖传递:** 依赖具有传递性，依赖可分为直接依赖和间接依赖



**可选依赖：**不想让别人知道自己用过这个依赖

```xml
<dependcy>
    
    
	<optional>true</optional>
</dependcy>
```



**排除依赖：**希望自己使用的依赖排除掉某个依赖（不需要版本号）

```xml
<dependcy>

	<exclusions>
    	<exclusion>
            <groupid>  </groupid>
            <artifactid>  </artifactid>
        </exclusion>
    </exclusions>
</dependcy>
```



**依赖范围（scope）**

* 依赖的jar默认情况可以在任何地方使用，可以通过scope标签设定其作用范围

  

| scope           | 主代码 | 测试代码 | 打包 | 范例         |
| --------------- | ------ | -------- | ---- | ------------ |
| compile（默认） | yes    | yes      | yes  | log4j        |
| test            |        | yes      |      | junit        |
| provided        | yes    | yes      |      | servelet—api |
| runtimes        |        |          | yes  | jdbc         |













## 1.6、生命周期与插件