# Classroom

#### 介绍
自习室管理和预约系统

#### 软件架构
软件架构说明
本系统为针对学校以及商用自习室定制的自习室预约和管理系统，采用mvc的设计模式分为多层。

根目录下存放了项目的主启动类和自行封装编译的集成代码生成方法


controller目录下为各个服务的调用接口；
service目录下是业务逻辑代码以及相关interface供controller调用；
entity目录下存放对应数据库表字段的实体类；
mapper目录下存放mybatis的接口文件，对应的xml存放于resouce/mapper；
common目录下存放自行封装的返回类型，以及在传参过程中需要的封装实体类
config目录下主要存放shiro，wagger等的一些配置类
shiro目录下存放登录token验证的相关代码
util目录下存放代码中抽象出的工具类

前端静态页面放在resources/static
#### 安装教程

1.  安装mysql数据库，在application.xml中修改数据库连接信息，在数据库面板运行resource/sql文件夹下的sql文件可生成数据库。
2.  安装redis，下载redis安装程序按步骤进行安装，安装完成后在终端输入redis-server启动redis
3.  通过git获取源代码并在本地启动运行即可

#### 使用说明

1.  用户功能
2.  教师功能
3.  管理员功能

#### 参与贡献

1.  框架搭建及后台实现 王君帅
2.  前端设计以及对接 张泽昊
3.  教师模块实现以及后期文档 许镒鹏


#### 特技

1.  使用 Readme\_XXX.md 来支持不同的语言，例如 Readme\_en.md, Readme\_zh.md
2.  springboot [https://spring.io/projects/spring-boot]
3.  Mysql [https://www.mysql.com/]
4.  Redis 官方:[https://redis.io/]  学习:[https://www.runoob.com/redis/redis-tutorial.html]
5.  Shiro安全框架  [https://github.com/greycode/shiro]
6.  Mybatis [https://mybatis.org/mybatis-3/zh/index.html]