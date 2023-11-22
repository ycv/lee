#### 前端 D:\Project\Aly\yanz\vueyz

```      admin 123
1. npm install
2. npm run serve
3. localhost:8011
```

### ysService        http://localhost:8012/

### 后端登录           http://localhost:8012/index.html

######                                                                  

## 项目介绍

统计数据

- [项目](https://sql.pvuu.qq.com)

### 项目技术栈

#### 后端技术栈

1. Spring Boot
2. Spring Security
3. MyBatis
4. MySQL
5. Redis
6. RabbitMQ
7. Spring Cache
8. WebSocket
9. ...

#### 前端技术栈

1. Vue
2. ElementUI
3. axios
4. vue-router
5. Vuex
6. WebSocket
7. vue-cli4
8. ...

### 项目效果图

## 快速部署

2. 数据库脚本使用 Flyway 管理，**不需要手动导入数据库脚本**，只需要提前在本地 MySQL 中创建一个空的数据库 vvv，并修改项目中关于数据的配置（resources 目录下的 application.properties
   文件中）即可
3. 提前准备好 Redis，在 项目的 application.properties 文件中，将 Redis 配置改为自己的
4. 提前准备好 RabbitMQ，在项目的 application.properties 文件中将 RabbitMQ 的配置改为自己的（**注意，RabbitMQ 需要分别修改 mailserver 和 yzserver 的配置文件**
   ）
5. 在 IntelliJ IDEA 中打开 yz 项目，启动 mailserver 模块
6. 运行 yzserver 中的 yz-web 模块

**OK，至此，服务端就启动成功了，此时我们直接在地址栏输入 `http://localhost:8012/index.html` 即可访问果要做二次开发，请继续看第七、八步。**

7. 进入到vueyz目录中，在命令行依次输入如下命令：

由于我在 vueyz 项目中已经配置了端口转发，将数据转发到 Spring Boot 上，因此项目启动之后，在浏览器中输入 `http://localhost:8011` 就可以访问我们的前端项目了，所有的请求通过端口转发将数据传到
Spring Boot 中（注意此时不要关闭 Sprin gBoot 项目）。

8. 最后可以用 WebStorm 等工具打开 vueyz 项目，继续开发，开发完成后，当项目要上线时，依然进入到 vueyz 目录，然后执行如下命令：

```
npm run build
```

该命令执行成功之后，vueyz 目录下生成一个 dist 文件夹，将该文件夹中的两个文件 static 和 index.html 拷贝到 Spring Boot 项目中 resources/static/ 目录下，然后就可以像第 6
步那样直接访问了（关于前后端分离部署，大家也可以参考这个[使用 Nginx 部署前后端分离项目，解决跨域问题](https://mp.weixin.qq.com/s/C7PIck3SIPPTcA3NX3ELoQ)）。

**步骤 7 中需要大家对 NodeJS、NPM 等有一定的使用经验，不熟悉的小伙伴可以先自行搜索学习下，推荐 [Vue 官方教程](https://cn.vuejs.org/v2/guide/)。**

## 文档

#### 相关文档：

管理员发送系统通知功能，页面在 **[Home页->右上角铃铛->系统通知]**

> 版本库 svn://39.107.237.117/yii

