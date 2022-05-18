# Classroom



# # # #

Self-study room management and reservation system



#### Software architecture

Software architecture description

This system is a self-study room reservation and management system customized for schools and commercial self-study rooms. It is divided into multiple layers using the design pattern of MVC.



The root directory holds the project's main startup class and the self-encapsulated integrated code generation methods





Invoking interfaces for each service in the controller directory;

In the service directory are the business logic codes and related interfaces for the controller to call;

Entity class containing the corresponding database table fields in the entity directory;

The interface file of MyBatis is stored in the mapper directory, and the corresponding XML is stored in resouce/mapper.

The common directory holds the self-encapsulated return types, as well as the encapsulated entity classes needed during the argument pass

The config directory mainly stores Shiro, Wagger and other configuration classes

The code for login token verification is stored in the shiro directory

The util directory holds the utility classes abstracted from the code



The front-end static page is placed in Resources/Static

#### Installation Tutorials



1. Install the MySQL database, modify the database connection information in Application.xml, and run the SQL file under Resource/SQL folder in the database panel to generate the database.

2. Install Redis, download the Redis installer and install it step by step. After the installation, enter Redis-server in the terminal to start Redis

3. Get the source code through git and start running locally



#### Instructions for Use



1. User functions

2. Teacher function

3. Administrator function



#### Participation and contribution



1. Framework construction and background implementation of Wang Junshuai

2. Front-end design and docking Zhang Zehao

3. Teacher module realization and later documentation Xu Yipeng





# # # # special effects



1. Use Readme\ _xxx. md to support different languages, such as Readme\_en.md, Readme\_zh.md

2. springboot [https://spring.io/projects/spring-boot]

3. Mysql [https://www.mysql.com/]

4. Redis official: [https://redis.io/] : [https://www.runoob.com/redis/redis-tutorial.html]

5. Shiro security framework [https://github.com/greycode/shiro]

6. Mybatis [https://mybatis.org/mybatis-3/zh/index.html]