# docker有关操作笔记

## 1、docker 安装mysql

```
docker run -p 3306:3306 --name mysql \
-v /mydata/mysql/log:/var/log/mysql \
-v /mydata/mysql/data:/var/lib/mysql \
-v /mydata/mysql/conf:/etc/mysql \
-e MYSQL_ROOT_PASSWORD=root
-d mysql:5.7
```

- mysql 配置 `vi /mydata/mysql/conf/my.conf`

```conf
[client]
default-character-set=utf8

[mysql]
#设置mysql客户端默认字符集
default-character-set=utf8

[mysqld]
init_connect='SET collation_connection=utf8_unicode_ci'
init_connect='SET NAMES utf8'
# 服务端使用的字符集默认为8比特编码的latin1字符集
character-set-server=utf8
character-set-client=utf8
collation-server=utf8_general_ci
# 允许最大连接数
max_connections=200
# 创建新表时将使用的默认存储引擎
default-storage-engine=INNODB
# 跳过权限检查
skip-character-set-client-handshake
# 跳过域名解析
skip-name-resolve
```

- 查看软件安装的**目录**：`whereis mysql` 

- 应用开机自启： 前提是docker 开机自启 `docker update mysql  --restart=always`

## 2、docker 安装redis

```cmd
mkdir -p /mydata/reids/conf
touch  /mydata/reids/conf/redis.conf
```

```cmd
docker run -p 6379:6379 --name redis \
-v /mydata/redis/data:/data \
-v /mydata/redis/conf/redis.conf:/etc/redis/redis.conf \
-d redis reids-server /etc/redis/redis.conf 
```

redis.conf

```cmd
appendonly yes   # aof 持久化
```

客户端： ` docker exec  -it redis redis-cli`

