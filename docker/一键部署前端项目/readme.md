# 部署前端项目

## 第一步：运行以下指令

```
npm run build
```

生成dist文件夹

![image-20240206114644534](./../img/image-20240206114644534.png)

## 第二步：将dist文件夹整个复制到虚拟机的某个目录中

![image-20240206114806277](./../img/image-20240206114806277.png)



## 第三步：执行命令，记住把your_image_name换成自己想要换的名字

```
docker build -t your_image_name .
```

当然，如果各位没有nginx镜像的话，可能会出错。所以先把nginx下载过来

```
docker pull  nginx
```

![image-20240206115336540](./../img/image-20240206115336540.png)

因为没有default.conf文件，我们打开项目，将nginx.conf改成default.conf保存到当前目录即可

![image-20240206115740098](./../img/image-20240206115740098.png)

![image-20240206115847685](./../img/image-20240206115847685.png)



镜像的维护者信息为 那个cyl也要进行修改

然后再执行下面命令：

```
docker build -t oj_web .
```

![image-20240206120016223](./../img/image-20240206120016223.png)

## 第四步：利用生成好的镜像创建容器

```
 docker run --name web01 -d -p 80:80 oj_web
```

![image-20240206120406543](./../img/image-20240206120406543.png)

访问浏览器即可。

![image-20240206121624529](./../img/image-20240206121624529.png)
