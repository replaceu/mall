docker官方文档地址： https://docs.docker.com/engine/install/centos/

## centos 7.5 docker安装教程

官方安装地址: https://docs.docker.com/install/linux/docker-ce/centos/

1、查看Linux版本

```sh
cat /etc/redhat-release
```

2、yum安装gcc相关

```sh
yum -y install gcc gcc-c++
```

3、卸载旧版本

```sh
yum -y remove docker docker-common docker-selinux docker-engine
```

4、安装需要的软件包

```sh
yum install -y yum-utils device-mapper-persistent-data lvm2
```

5、设置stable镜像仓库

```sh
yum-config-manager --add-repo http://mirrors.aliyun.com/docker-ce/linux/centos/docker-ce.repo
```

6、更新yum软件包索引

```sh
yum makecache fast
```

7、启动docker

```sh
systemctl start docker  # 启动
systemctl status docker # 查看docker 状态
```

8、配置镜像加速















docker 开机自启

```cmd
sudo systemctl enable docker.service
```



systemctl list-unit-files
