# 图片转rtsp视频直播流工具

## 基本技术
###  后端：

| 名称                       | 版本          | 说明                                     |
| -------------------------- | ------------- | ---------------------------------------- |
| spring-boot-starter-parent | 2.2.2.RELEASE |                                          |
| HikariCP                   |               | 数据库连接池                             |
| tk.mybatis                 | 2.1.5         | mybatis通用模板                          |
| javacv-platform            | 1.5.4         | java版本视频处理工具，解码，转码，推流等 |
| springdoc-openapi-ui       | 1.5.2         | swagger openapi                          |
| mapstruct                  | 1.3.1.Final   | 对象映射转换工具                         |
| minio                      | 7.1.0         | 对象存储（暂未开启）                     |

### 前端：

| 名称       | 版本   | 说明                                                         |
| ---------- | ------ | ------------------------------------------------------------ |
| vue        | 2.6.10 |                                                              |
| element-ui | 2.13.2 |                                                              |
| axios      | 0.18.1 |                                                              |
| vue-router | 3.0.6  |                                                              |
| vuex       | 3.1.0  |                                                              |
| @vue/cli   | 4.5.13 |                                                              |
| ...        | ...    | 其他依赖可查看package.json。路径src/main/webapp/easycv-admin |

### 环境

| 名称               | 版本             | 说明                                                         |
| ------------------ | ---------------- | ------------------------------------------------------------ |
| jdk                | 1.8              |                                                              |
| maven              | 3.6.3            |                                                              |
| docker             |                  |                                                              |
| node               | 12.18.0          |                                                              |
| mariadb            |                  |                                                              |
| EasyDarwin-windows | 8.1.0-1901141151 | 高性能开源RTSP流媒体服务器，[easyDarwin](https://github.com/EasyDarwin/EasyDarwin) |
| EasyDarwin-linux   | 8.1.0-1901141151 | 高性能开源RTSP流媒体服务器，[easyDarwin](https://github.com/EasyDarwin/EasyDarwin) |

## 环境部署：

1.安装mariadb数据库，jdk8、maven、node。略

给出一个mariadb的docker安装命令：

```
docker run --name mariadb -p 3306:3306 -e MYSQL_ROOT_PASSWORD=123456 -v /data/mariadb/data:/var/lib/mysql -d mariadb
```

> ​		--name启动容器设置容器名称为mariadb
>
> 　　-p设置容器的3306端口映射到主机3306端口
>
> 　　-e MYSQL_ROOT_PASSWORD设置环境变量数据库root用户密码为输入数据库root用户的密码
>
> 　　-v设置容器目录/var/lib/mysql映射到本地目录/data/mariadb/data
>
> 　　-d后台运行容器mariadb并返回容器id

mariadb数据按照完成后，初始化数据库，对应的数据库文件在`db/img.sql`文件中

2.下载[easyDarwin](https://github.com/EasyDarwin/EasyDarwin)对应环境（windows，linux）的软件，按照官方文档启动即可，默认端口10008，会看到推流拉流列表的界面

3.启动后端服务

后端启动环境变量说明：

| 名称                  | 默认值      | 说明                                   |
| --------------------- | ----------- | -------------------------------------- |
| MYSQL_HOST            | 127.0.0.1   |                                        |
| MYSQL_PORT            | 3306        |                                        |
| MYSQL_USERNAME        | root        |                                        |
| MYSQL_PASSWORD        | fBjDSpr9-da |                                        |
| EASY_DARWIN_IP        | 127.0.0.1   | easyDarwin对应的地址                   |
| EASY_DARWIN_PORT      | 10008       | easyDarwin对应的端口                   |
| EASY_DARWIN_RTSP_URL  | 127.0.0.1   | 图片推流的地址                         |
| EASY_DARWIN_RTSP_PORT | 554         | 图片推流的端口                         |
| ...                   | ...         | 更多配置查看`src/main/application.yml` |

4.启动前端服务

进入easy-admin目录，依次执行`npm insatll`

本地dev启动：`npm run dev`

进入页面：http://localhost:9527



## 打包

#### 后端：

1.构建镜像

maven命令打包出jar后，将jar包拷贝到`docker`文件夹中，执行`docker build -f DockerFile -t easycv:v1.0 .`构建镜像

2.运行镜像

```
docker run -p 9500:9500 --env MYSQL_HOST=ip --env MYSQL_PASSWORD=password --env EASY_DARWIN_IP=ip --env EASY_DARWIN_RTSP_URL=ip --name easyCV easycv:v1.0
```

需正确配置mariadb的用户名密码

#### 前端：

进入easycv-admin文件夹

修改`.env.production`文件的`VUE_APP_BASE_API`地址，改地址是请求后端服务的接口地址，修改ip地址即可

执行命令：`npm run build:prod`

打包完成后会出现一个文件名称为easycv的文件夹，这是打包后的静态文件，放到nginx反向代理的文件中

访问nginx对应的代理端口+easycv路径即可，如http://10.122.100.146/easycv



# 如何使用

使用方式很简单，点击`图片管理`=>`上传图片`

> 特别注意：目前只支持png的图片解析，上传其他图片在转流的过程中会出现不可预知的问题

![image-20210715181023724](https://github.com/mrxccc/easyCV/blob/main/doc/image/image-20210715181023724.png)

添加后，该图片的rtsp转流任务是默认是关闭的，需要手动启动

![image-20210715172230253](https://github.com/mrxccc/easyCV/blob/main/doc/image/image-20210715172230253.png)

列表中的rtsp地址就是一个使用图片进行转发的rtsp直播流