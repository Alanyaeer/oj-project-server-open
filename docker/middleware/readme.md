# 一键部署中间件 - Windows/Mac
第一步: 打开idea， 点击docker-compose.yml文件
![img.png](./../img/img3.png)

第二步：点击左侧的启动按钮
![img.png](./../img/img3.png)
如下图所示，容器启动成功，具体情况，可以自行查看容器日志
![img_1.png](./../img/img_1.png)
第三步：重新启动Nacos服务
```
找到Nacos那一行，点击重新启动，如果你的mysql也是通过docker-compose部署的，这一步必须执行
```
![img_2.png](./../img/img_2.png)
# 一键部署中间件 - Linux

第一步：将本目录里面的docker-compose.yml存入虚拟机中，like this：

![](./../img/image-20240205143608557.png)

第二步：运行指令

```
docker-compose up -d
```

![image-20240205144626722](./../img/image-20240205144626722.png)

(已切换其他镜像，启动后自动开启管理面板)
~~第三步：进入 rabbitmq 容器并执行相关操作~~

```
docker exec -it 容器名 /bin/bash 
rabbitmq-plugins enable rabbitmq_management 
cd /etc/rabbitmq/conf.d/ 
echo management_agent.disable_metrics_collector = false > management_agent.disable_metrics_collector.conf 
exit
```

第四步：重新启动nacos的服务
```
Nacos最初启动的时候，mysql还没有载入sql文件。需要我们单独启动一次

docker-compose restart nacos
```
![img.png](./../img/img.png)
注意：

1. rabbitmq默认的用户名和密码都是guest

2. nacos默认的用户名和密码都是nacos

3. mysql的root的密码是123456

4. 如果各位想改的话，可以在docker-compose.yml上面进行相应的修改
