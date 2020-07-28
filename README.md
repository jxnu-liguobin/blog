## 简单粗糙丑陋的个人博客【推荐使用master分支】
### 分支说明
    feature1支不推荐，有BUG。 代码均有关键注释，类注释。项目中被优化替换的类使用了过期注解。
    master分支是我开发的项目完整版，包含搜狐畅游评论，使用的话需要替换自己的appid,里面我只删除了CA证书【一个密钥文件】，文件都在，用于测试。
    与feature1支不同的主要是修复分页的bug和评论是用的第三方插件

### 重要提醒：
    本项目是本人第一个完整的项目，完成慢，代码不规范，问题多，仅供新手参考。基于MIT协议，代码随便用。

### 主要技术与说明：  
    第一次写项目，考虑不周见谅。特别是图片缩略有问题  存在的问题都在笔记墙说了。本blog仅供新手学习.


    JDK:1.8 (开发环境是8，未使用1.8特性,发布目标1.7)修改->application-dev.properties, cmd-> java -jar blog.jar 即可运行项目 
    注：本地不要使用java -jar运行最好，需要改为发布的服务器路径而且坑多，使用eclipse运行application.java最好使用war则需要添加一个转换
    修改打包方方式具体百度 
    主要需要修改配置如下：增加个PV文件，sql url配置，https证书位置（我自己的会删除，CA的，也可以自己用jdk工具生成，不再赘述） 
    均需要保证路径C:/web/uploads/存在（tomcat的虚拟路径，已经写死在代码中，默认在服务器与本机创建c:\web与uploads,linux按着改分隔符吧）
    如：在web里新建文件夹filenames,filenames存放静态资源file,则访问方式为ip:port/filenames/file,以此类推，注意Shiro拦截）
    
    前端模板：来自国产开源框架Layui社区的分享“不落阁”，含百度分享等插件
    全文检索：Lucene
    前端模板引擎：Thymeleaf3.0（nekohtml）
    字体：font-awesome
    代码高亮：prettify
    后台：SpringBoot1.5.8、Mybatis3.4.5、Mysql5.6
    验证：jquery-validate、Shiro
    日志：默认logback
    单元测试：Junit4
    数据库连接池：Druid(含sql与spring监控)
    JSON工具 ：fastjson
    web服务器:Tomcat8.5内嵌式 使用Https
    发布方式：Jar
    代码托管：github
    依赖管理：maven
    开发工具：MyEclipse2013 UE sublimetext
    缓存：Myabti、Redis简单使用  
    主要功能，实体表，文章，文章分类，友情链接，点赞关系表，评论，留言。公告，博主信息表。多级评论暂用搜狐畅言实现。 
  
###  如果对您有帮助欢迎点个star, 预览地址www.dreamylost.cn
后台预览图：
![](https://github.com/jxnu-liguobin/blog/blob/master/src/main/resources/%E5%90%8E%E5%8F%B0%E9%A2%84%E8%A7%88.png)

    sql中文乱码是因为编码一开始忘记改，后来改完没有清空数据导致的乱码，全部清空数据，以UTF-8建表即可。
    本项目的Shiro部分因为第一次接触，实在没有写好 甚至配置都是错误冗余的，所有请忽略
    学习shiro的小伙伴可参考本人仓库SpringBoot-Base-System，学习权限控制Shiro
    注意：项目于2017.12.16终结，时间太久，可能不能提供过多帮助了。
