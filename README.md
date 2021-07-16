# 图片转rtsp视频直播流工具

## 1.基本技术
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

## 2.环境部署：

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
![image-image-20210715172230253](/doc/image/image-20210715172230253.png)

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



## 3.打包

#### 3.1后端：

1.构建镜像

maven命令打包出jar后，将jar包拷贝到`docker`文件夹中，执行`docker build -f DockerFile -t easycv:v1.0 .`构建镜像

2.运行镜像
命令运行
```
docker run -p 9500:9500 --env MYSQL_HOST=ip --env MYSQL_PASSWORD=password --env EASY_DARWIN_IP=ip --env EASY_DARWIN_RTSP_URL=ip --name easyCV easycv:v1.0
```
主要参数配置：MYSQL_HOST、MYSQL_PASSWORD、EASY_DARWIN_IP、EASY_DARWIN_RTSP_URL

需正确配置mariadb的用户名密码

#### 3.2前端：
前端文件修改镜像需要修改接口地址

修改`.env.production`文件的`VUE_APP_BASE_API`地址，改地址是请求后端服务的接口地址，修改ip地址即可

进入easycv-admin文件夹，执行命令：`npm run build:prod`

打包完成后会出现一个文件名称为easycv的文件夹，这是打包后的静态文件，放到nginx反向代理的文件中

访问nginx对应的代理端口+easycv路径即可，如http://10.122.100.146/easycv

贵司的nginx已配置跨域和反向代理，直接将打包好的文件拷贝到`/mnt/data/sng-biz/nginx/web/portal/`目录

# 4.使用流程

### 4.1.获取图片

目前只支持png格式的图片进行解码转流，获取方式可以使用**vlc播放器**截取关键帧保存png图片（目前使用这种方式获取的图片播放质量最好，使用qq，微信、钉钉屏幕截图的方式保存的图片没有完全测试，有时候会有奇奇怪怪的问题）

**vlc播放器**截屏快捷键：`shift+s`

### 4.2.上传图片

使用方式很简单，点击`图片管理`=>`上传图片`

> 特别注意：目前只支持png的图片解析，上传其他图片在转流的过程中会出现不可预知的问题

![image-20210715181023724](/doc/image/image-20210715181023724.png)

### 4.3.开启任务

添加后，该图片的rtsp转流任务是默认是关闭的，需要手动启动

![image-20210715181219325](/doc/image/image-20210715181219325.png)

列表中的**rtsp地址**就是一个使用图片进行转发的rtsp直播流地址

# 5.关于图片存储访问与预览
图片是使用本地磁盘路径进行的静态资源映射，windows路径和linux路径格式不一样
在启动服务时使用`IMAGE_PATH`进行配置

## 5.1.默认路径
如果`IMAGE_PATH`变量没有配置，那么会取默认值`System.getProperty("java.io.tmpdir") + File.separator + "easycvImage" + File.separator`
>`System.getProperty("java.io.tmpdir")` 是操作系统的缓存临时目录

如果linux环境:通常是`/tmp`目录或`/var/tmp`
windows默认存储在: `C:\Users\登录用户\AppData\Local\Temp\`
## 5.2.使用docker容器启动
如果是用docker启动的服务，会默认存储在容器的`/data/image`目录下，且做了数据持久化Volume
如果想在本地映射该目录，可以在启动容器时选择挂载该Volume
如：`docker run -p 9500:9500 --env MYSQL_HOST=ip --env MYSQL_PASSWORD=password --env EASY_DARWIN_IP=ip --env EASY_DARWIN_RTSP_URL=ip -v /home/cjc/easycv/image/:/data/image --name easyCV easycv:v1.0`
>`-v /home/cjc/easycv/image/:/data/image` 就是将容器中`/data/image`目录映射到宿主机的`/home/cjc/easycv/image/`目录下面
# 7.其他

### 关于定时器

有一个默认的定时器，会1分钟执行一次录像任务的状态更新
所以，如果发现录像停止了，但数据状态显示还是`已开启`状态，不要担心，定时器到了会自动刷新状态，但这会有一定的延时

### 关于并发

每一路直播流都是一个现场，目前线程池的最大线程数50个，能否支持五十路直播流暂未测试，可在`application.yml`中修改线程池相关的配置

### 关于后端开发

后端的图片转rtsp直播流功能主要依赖[javacv](https://github.com/bytedeco/javacv/)、[EasyDarwin](https://github.com/EasyDarwin/EasyDarwin)

`javacv`负责处理图片解析，转码，推流等功能，`easyDarwin`是一个流媒体服务器，任何符合规则的流媒体都可接入该服务收流、推流

### 关于前端开发

前端使用 [vue-admin-tempate](https://panjiachen.github.io/vue-element-admin-site/zh/)作为开发模板，大部分功能都可按照vue-admin-tempate及搭配element ui进行开发
