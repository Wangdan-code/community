# 第一章 初识Spring Boot
开发环境：
构建工具：Apache Maven
集成开发工具：IntelliJ IDEA
数据库：MySQL、Redis
应用服务器：Apache Tomcat
版本控制器：Git
## 一、安装
### maven安装
官网https://maven.apache.org/上下载maven安装包 windows平台对应的binary不带源码的版本 （第二个）
解压缩后修改conf/setting文件中的mirros 添加**阿里云镜像仓库**
在高级环境设置中 环境系统变量path添加 bin地址 
打开cmd 输入mvn -version 就可以出现版本号
**mvn命令：**
1、五分钟创建项目
2、编译命令 在pom文件夹那一层 mvn compile
3、重新编译：先mvn clean 然后再mvn compile
4、编译完测试：先mvn clean 然后mvn test （test的命令包含compile）
### 集成开发工具IntelliJ IDEA安装
以前已经安装过了

创建maven项目
create project
选择maven archetype 修改名字和location archetype模板选择quickstart 然后creat就可以了
自动创建好项目后，右边maven标签中可以点击clean、compile、test命令

maven安装包：
1、可以在阿里云网站上搜索：https://maven.aliyun.com/mvn/search 中搜索仓库的名字mysql 缺点：看不到哪个好
2、https://mvnrepository.com网站上搜索 可以查看包使用的人数 具体可以点击进去查看对应的maven命令 将其复制粘贴到pom文件中dependency标签下

### spring Initializr创建sprgboot项目的引导工具
网址：https://start.spring.io
选择maven java sprigboot版本为2.1.5默认
填写Group ：com.nowcoder.community
包名：com.nowcoder.community
jar包 java版本 还需要添加aspects功能相关包（没找到 估计是这个artifactId）、web相关包、thymeleaf、devtools
点击生成项目会自动下载zip包 解压缩到某一个目录下
使用idea打开对应的community

src文件夹下面的main下面的resources配置文件
运行：main下面的java文件下的main函数 右键运行 编译
运行信息中tomcat start 8080端口
打开浏览器输入localhost:8080 出现whitelabel error page 原因：项目啥都没写

出现错误：java: 警告: 源发行版 17 需要目标发行版 17
SpringBoot的版本选择的是3.0及以上，因为SpirngBoot3后最低支持JDK17了
springboot版本选择2.7.13 版本就可以
出现错误：idea打开maven新项目 main函数右键无run
等待 网太慢 还没有下载好
**spring boot核心内容：**
起步依赖：pom文件中dependencie标签下面都是依赖包
自动配置
端点监控
**Tips：端口8080冲突：**
修改resources中的application.properties
填入 server.port = 8080
给项目加一级路径：server.servlet.context-path = /community



## 二、Spring
### 全家桶
Spring Framework框架
Spring Boot 用来构建一切
Spring Cloud 微服务 协调一切 将多个子项目协调在一起工作
Spring Cloud Data Flow 数据集成 连接一切 多客户端应用，可以将不同终端的数据整合在一起
网址：https://spring.io
更详细的内容在：projects
后续可能用到的子模块：
spring data
spring security
spring amqp：消息队列
### Spring Framework - 官网上标志GA的正式版本
Spring Core 核心 两种思想：Ioc、AOP
Spring Data Access 管理事务Transactions Spring MyBatis
Web Servlet：Spring MVC
Integration集成：Email、Scheduling、AMQP、Security
### Spring IoC 
控制反转：Inversion of Control 一种面向对象编程的设计思想
依赖注入：Dependency Injection IoC思想的实想方式
IoC容器Container：实现依赖注入的关键，本质上是一个工厂
分析代码：
CommunityApplication文件中main方法run 自动创建spring容器 自动扫描配置类所在的包bean 装配的
Controller Service Repository三种注解都可以实现
spring容器的顶层接口是BeanFactory，常用的子接口ApplicationContext
#### 示例
写代码：在测试类test文件夹中 CommunityApplicationTests.java
1、加注解**ContextConfiguration**(classes = CommunityApplication.class) 修改配置类为main下的java文件
2、实现接口 implements **ApplicationContextAware**
并实现接口中的**setApplicationContext**方法
传入的是一个aplicationcontext的容器
在方法中定义一个private ApplicationContext 的applicationContext
在set方法中this.context = context
这样在其他部分就能访问到传入的测试容器
3、写测试方法**testApplicationContext**（） 输出context看有没有值
4、创建一个dao包 
包下创建一个接口Interface 起名随意**AlphaDao**
接口中补充方法 String select（）；没有参数 返回字符串
实现类：新建一个class **AlphaDaoHibernatelmpl** 接口 实现select方法 return "Hibernate"
为了让tests能够访问到alphadao类 需要给类加注解**@Repository**
点击运行 可以看到test类编译结果返回了字符串Hibernate
5、不使用Hibernate 改为Mybatis
新建实现类：class **AlphaDaoMyBatisImpl** 实现类的方法select返回MyBatis字符串
同时添加注解@Repository
此时同时存在两个接口：AlphaDaoHibernatelmpl、AlphaDaoMyBatisImpl
test访问的时候会不知道调用哪一个Bean 因此给MyBatis添加注解**@Primary**
如果test其他地方像调用Hibernate，需要给Hibernate的Repository注解添加参数（"alphaHibernate"）重命名 然后在test类中getBean(s:"alphaHibernate",AlphaDao.classs);
6、spring容器管理bean，还能管理Bean的生命周期、作用域
新建bean 用来处理业务 新建service组件 
service下创建一个class 类名为**AlphaService**
类添加注解Service 增加方法init（） 输出字符串"初始化"
通过容器 自动化调用方法：
在init方法前面添加注解**@PostConstruct** 注解的意思：方法会在构造之后调用
在init方法前添加构造函数，输出字符串"实例化"
在init方法后面添加销毁函数destory() 输出字符串"销毁" 方法需要添加注解PreDestory 意思是：方法在被销毁之前调用
测试：
在test新建方法**testBeanManagement**
方法内定义一个AlphaService alphaService = applicationContext的getBean方法 出入的是AlphaService.class
然后打印alphaService
运行test方法 被容器管理的Bean是单列的
可以看到实例化、初始化、打印出来Bean、销毁
程序开始的时候Bean被实例化 程序结束的时候被销毁->只被实例化一次 销毁一次
如果想每次调用Bean都实例化 需要给Bean的类加注解Scope("prototype") 这种情况很少见

#### 补充知识
声明bean的注解：**@Component、@controller、@service、@repository**
1、@Component、@controller把普通pojo实例化到spring容器中
2、@Service注解带参数： 不带参数：
3、@repository注解 用于标注数据访问层，带参数/不带参数

#### 手写配置类 装配第三方bean
新建一个包config配置
新建一个类AlphaConfig 添加注解@**Configuration** 表明这是配置类不是普通类
添加方法SimpleDateFormat表示：方法返回的对象会被装配到bean中
先注解**@Bean** 
方法返回类型为SimpleDateFormat 方法名为simpleDateFormat也是Bean的名字
方法返回return new SimpleDateFormat( pattern:"yyyy-MM-dd HH:mm:ss")
测试类中再写一个测试方法**testBeanConfig**
方法中先声明SimpleDateFormat类型的实例simpleDateFormat = getBean
然后输出simpleDateFormat.format(new Date())
执行方法可以得到当前的年月日时分秒
以上都是spring主动获取bean容器用
笨拙的方法

#### 依赖注入
test类中
只需要在实例前声明 添加注解**Autowired**
private AlphaDao alphaDao
添加测试方法testDI() 在测试方法中直接使用程序变量 打印出来alphaDao
如果实例Dao有很多Bean 一个MyBatis 一个Hibernate 可以添加注解Qualifier("alphaHibernate")

项目中真实使用：
controller来处理浏览器的请求，调用service业务组件处理业务，业务组件会调用Dao访问数据库 彼此互相依赖

例如 业务servicez中注入Dao实现数据库查询
在service.java文件中
@Autowired
private AlphaDao alphaDao
写一个方法：public String find(){return  alphaDao.select();}

controller注入service 模拟一个查询请求
在controller.java文件中添加
@Autowired
private AlphaService alphaService
写一个方法：public String getData(){return alphaService.find();}
浏览器实现的前提是得有路径 因此 需要加注解**RequestMapping**("/data") ResponseBody

## 三、Spring MVC
#### HTTP
手册地址https://developer.mozilla.org/zh-CN
HTTP概述：浏览器和服务器之间的交互
1、建立TCP连接
2、发送HTTP报文
3、服务器解读，返回报文信息
4、关闭连接/为后续请求重用连接
HTTP请求：
请求方式GET/POST
请求路径/
协议类型HTTP/1.1
头Host、语言、
Body业务数据
HTTP响应：
状态行：协议版本 状态码
响应头
响应体

#### SpringMVC
1、三层架构：表现层、业务层、数据访问层
2、MVC：在表现层：Model模型层、View视图层、Controller控制层
3、核心组件：
servlet engine引擎/服务器中包含 前端控制器**DispatcherServlet**处理mvc
根据注解找到controller，封装返回给Front controller
controller使用model调用view模板 生成html返回给controller controller返回给浏览器
4、**Thymeleaf模板**引擎 主要用来生成动态的HTML
给引擎两个文件：模板文件（包含表达式）model数据
常用语法：标准表达式、判断与循环、模板的布局
官网：https://thymeleaf.org/doc/tutorials/3.0/usingthymeleaf.html
**配置thymeleaf**：关闭模板的缓存 应用上线后可以打开缓存 为服务器减少压力
在application.properties文件中

ThymeleafProperties配置
spring.thymeleaf.cache = false
5、代码位置
springmvc解决的是视图层的问题
视图层的代码分为两部分：
模板引擎需要的模板放在resources文件夹下的**templates**包下
controller
service包是业务层
Dao包是数据访问层
#### 示例
在controller里添加http协议里面的获取数据、响应数据
添加方法 注解为**RequestMapping**("/http")
public void http(HttpServletRequest HttpServletResponse){
}
（1）需要获取请求数据
输出信息：
request的getMethod()方法 GET
request的getServerletPath()方法 /alpha/http
getHeaderNames()方法返回类型Enumeration类
（2）返回响应数据
response的setContentType
try( Printriter writer = response.getWriter();
){writer.write("<h1>牛客网</h1>");
}catch(IOException e){
e.printStackTrace();
}
运行程序打开浏览器
传参数：直接在网址后面加?code=123&name=zhangsan
#### Get请求 
向服务器获取数据 一般用于获取某些数据 两种传参的方式 
(1)一种是？参数=
访问数据 学生 ，当前页是1 每页限制显示20
添加注解**RequestMapping()**参数path metod=强制规定是**RequestMethod.GET**
**@ResponseBody**
添加方法getStudents 传入两个参数int current和int limit
可以给参数前面添加注解RequestParam(name = "current",required = false,defaultValue = "1）
浏览器中返回字符串“some students”
控制台可以输出limit和current的值
(2)把参数拼到路径当中
只查询一个学生 给出id 路径是/student/123
添加注解RequestMapping()参数path=“/path/(id)” method是get
响应还是用ResopnseBody
添加方法getStudent（）{return “a student”}
需要id变量 方法传入的参数
添加注解@PathVariable（“id”）int id 从路径中得到id

#### POST请求 
浏览器向服务器提交数据
（1）浏览器得打开一个表单的网页 在项目中创建一个静态网页 这一部分是放到resources文件下的static文件内
新建一个html文件夹 然后new 一个html文件命名为student
网页的标题改为增加学生 在body内添加一个表单form method为post、action需要加上路径/community/alpha/student
在浏览器中输入的路径为 /community/html/student.html
（2）在controller里新添加一个post请求函数
添加注解RequestMapping()参数path=“/student” method是**post**
响应还是用ResopnseBody
添加方法saveStudent（String name，int age）{return “success”}

#### 响应html数据
如何向浏览器返回响应数据 以前的例子都是字符串？
(1)需求：浏览器查询老师 服务器查询岛的数据响应给浏览器
在controller中
添加注解**RequestMapping()**参数path=“/teacher” method是get
返回的类型ModelAndView 方法名为getTeacher
在方法中：首先实例化ModelAndView mav= new ModeAAndView
mav.addObject（attributeName:"name",attributeValue:"张三"）
mav.addObject（attributeName:"age",attributeValue:23）
mav.setViewName("/demo/view");
模板需要放在templates下面的demo路径下**view.html**
return mav
view.html是**模板** 因此需要给html声明 在html标签内添加xmlns:th="http://www.thymeleaf.org"
在body里面显示 <p th:text="${name}"></p><p th:text="${age}"></p>
(2)需求：查询学校
添加注解RequestMapping()参数path=“/school” method是get
方法返回string 方法名为getSchool 参数为Model model
model.addAttribute(attributeName:"name",attributeValue:"北京大学")
model.addAttribute(attributeName:"age",attributeValue:80)
return "/demo/view"

#### 响应JSON数据
浏览器响应json数据（异步请求中：当前网页不刷新 但是浏览器访问了数据器）
（1）json数据
添加注解RequestMapping()参数path=“/emp” method是get
添加注解**ResopnseBody 不写默认是html数据**
方法返回的类型是Map<String,Object> 方法名为getEmp
方法内新建一个map map.put()  return emp 
json数据：{key-value结构，key-value结构}
（2）返回的数据是多个员工
方法返回为List<Map<String,Object>> = new ArrayList<>();
json数据：[{},{},{}]

## 四、Mybatis
### 安装
1、安装MySQL Server 8.0.16 第一个小可以直接选择下 解压缩在soft文件夹下
打开解压后的目录，根目录下放入my.ini
[mysql]
default-character-set = utf8
[mysqld]
port=3306
basedir=E:\project_soft\mysql-winx64
max_connections=20
character-set-server=utf8
default-storage-engine=INNODB
把bin目录放入到环境变量
窗口cmd右键以管理员身份运行，cd到bin目录下
命令一:mysqld --initialize --console
在生成提示信息中可以看到生成的随机密码 复制保存
命令二：mysqld install
命令三：net start mysql
访问命令：mysql -uroot -p
输入密码
修改密码：alter user root@localhost identified by 'wangdan';
退出：exit:
mysql创建库：create database community
查看库：show community
使用库：use community
导入表：source + 文件名 注意路径名是左斜线/ init_schema.sql
查看表 show tables
导入数据：source + 文件名 注意路径名是左斜线/ init_data.sql

### MySQL Workbench
安装MySQL Workbench客户端 双击一步一步安装就可以
打开配置 右键edit
密码需要改一下 default schema改为community
配置edit 可以修改字体Courier new 12
sql edit最后的选项删除

### 基础知识
MyBatis-Spring手册有中文版http://www.mybatis.org/mybatis-3
**MyBatis核心组件：**
SqlSessionFactory：用来创建SqlSession的工厂类
SqlSession：用来向数据库执行SQL
主配置文件：XML文件，对底层行为做出配置
Mapper接口：DAO接口
Mapper映射：用来编写SQL，并将SQL和实体类映射的组件，采用XML、注解可实现

### 示例
用户表中字段：id 名字 密码（加密后）salt（随机五位字符串 加密用）邮件 
type0代表普通用户 1管理员 2版主 
status 用户状态1表示激活 0表示没激活
activation_code 激活码
头像地址
注册时间
需求：**写代码对sql用户表增删改查**
pom文件导入mysql、mybatis的包
**配置**：application.properties
添加#DataSourceProperties
spring.datasource.driver-calss-name=com.sql.cj.jdbc.Driver
spring.datasource.url=jdbc:mysql://localhost:3306/coummunity?characterEncoding=utf-8&useSSL=false&serverTimezone=Hongkong
spring.datasource.username=root
spring.datasource.password=
连接池 可以管理连接对象，使得连接对象能够复用，减少创建连接的开销
spring.datasource.type=com.zaxxer.hikari.HikariDataSource
spring.datasource.hikari.maximum-pool-size=15 连接的上限，避免数据库因超负荷而崩溃
spring.datasource.hikari.minimum-idle=5 最小空闲连接
spring.datasource.hikari.idle-timeout=30000 超时时间
#MybatisProperties
mybatis.mapper-locations=classpath:mapper/*.xml
mybatis.type-aliases-package=com.nowcoder.community.entity
mybatis.configuration.useGeneratedKeys=true
mybatis.configuration.mapUnderscoreToCamelCase=true

### user表访问
#### 新建实体类
在entity新建一个class 名字为User
添加属性id、username、password、salt、email、type、status、activationCode、headerUrl、crateTime
添加属性的get、set方法：快捷键alt+insert 选择getter和setter 全部勾选
最后生成toString方法：快捷键alt+insert toString()
#### 数据访问组件UserMapper
dao下面新建一个数据访问组件 UserMapper 接口类 只写接口不写实体类
//注解Repository 可以装配bean
mybatis的注解是**Mapper**
在UserMapper接口中添加方法：
（1）根据id查询user
（2）根据name查询user
（3）根据email查询user
（4）增加用户 返回一个int
（5）修改用户状态 返回修改的条数int 传入id和状态
（6）更新头像路径 返回int 传入id和headerUrl
（7）更新密码 返回int 传入id和password

#### 配置mapper
在配置文件resources下面的mapper文件下创建一个user-mapper.xml
在Mybatis官网上查找 www.mybatis.org/mybatis-3/zh/getting-started.html
入门下面有主配置文件 下滑可以看到探知一映射的mysql配置文件
复制粘贴过来 mapper中namespace需要修改为usermapper名字
(1)**选择语句**：在mapper下面添加标签select
```html
<select id="selectById" resultType="User">
    select id,username,password,salt,email,type,status,activation_code,header_url,create_time
    from user
    where id = #{id}
</select>
```
**Tips：**字段过多 复用

```HTML
<sql id="selectFields">
    字段名
</sql>
```
把对应的字段名修改为

```html
<include refid="selectFields"></include>
```
（2）**增加语句**：在mapper下面添加标签insert
```html
<insert id="insertUser" parameterType="User" keyProperty="id">
    insert into user （<include refid="insertFields"></include>）
    values(#{username},#{password},#{salt},#{email},#{})
</insert>
```
（3）**更新语句**：在mapper下面添加标签update
```html
<update id="updateStatus">
    update user set status = #{status} where id = #{id}
</update>
```
#### 测试类MapperTests
写测试类 在test文件夹下新建一个类MapperTests
粘贴一下CommunityApplicationTests的注解
注入mapper
**@Autowired**
private UserMapper userMapper；

**@Test**
public void testSelectUser(){
	User user = userMapper.selectById(101);
	System.out.print(user);
}

**@Test**
public void testInsertUser(){
	User user = new User();
	user.setUsername("test");
	....
	System.out.print(userMapper.insertUser(user););
}

**@Test**
public void testUpdateUser(){
	int rows = userMapper.updateSt(150,1);
	System.out.print(rows);
}

**Tips：**

user-mapper.xml写错不明显 解决方法：
application.properties文件中修改logger

#logger

logging.level.com.nowcoder.community = debug
运行测试类中可以查看sql语句的详细信息

## 五、实操：开发社区首页
### 分析
开发流程：1次请求的执行过程，分步实现
请求会提交给视图层Controller -> 访问Service业务层 -> 访问数据库 数据访问组件Dao
开发流程DAO、Service、Controller
第一步：开发社区首页，显示前10个帖子
第二步：开发分页组件，分页显示所有帖子
**数据信息：**数据库中discuss_post表 查看ddl详细信息
id、user_id、title、content、type类型0表示普通1表示 置顶
status、create_time、comment_count、score

### 开发数据访问层
#### 新建实体类
1、实体类型
entity包下新建一个DiscussPost类
添加属性

#### 数据访问组件Mapper
2、开发DAO数据访问层
dao包下面新建一个接口DiscussPostMapper
添加注解mapper
添加方法：返回的是集合list 类型是DiscussPost 方法名称是selectDiscussPosts 参数是userId

```java
List<DiscussPost> selectDiscussPosts(int userId,int offset,int limit);
//@Param注解用于参数取别名，如果只有一个参数，并且在<if>里使用，就必须加别名
int selectDiscussPostRows(@Param("userId") int userId); //查询表的行数 注解起别名字
```

#### 配置文件mapper
mapper文件夹下新建一个discusspost-mapper.xml
在xml文件中实现对应的sql语句

```html
<select id="selectDiscussPosts" resultType="DiscussPost">
    select <include refid="selectFields"></include>
    from discuss_post
    where status !=2
    <if test="userId!= 0">
        and user_id = #{userId}
    </if>
    order by type desc,create_time desc
    limit #{offset},#{limit}
</select>
```
配置文件很容易写错 最好添加一个test类测试
测试类写完有个错误 预计原因是：xml文件写错了

#### 测试类
新建一个方法testSelectPosts()
后台显示10行数据：
```java
List<DiscussPost> list = discussPostMapper.selectDiscussPosts(0,0,10);
for(DiscussPost post:list){
    System.out.println(post);
}
```
 总行数：
```java
int rows = discussPostMapper.selectDiscussPostRows(0);
System.out.println(rows);
```
### 开发业务层
在service包下新建业务组件class **DiscussPostService**
添加注解@Service 让其能够被扫描到
方法中需要先添加mapper
```java
@Autowired
private DiscussPostMapper discussPostMapper；
    public List<DiscussPost> findDisscussPosts(int userId,int offset,int limit){
    return discussPostMapper.selectDiscussPosts(usedId,offert,limit);
}
public int findDisscussRows(int userId){
    return discussPostMapper.selectDiscussPostRows(userId);
}
```
针对DiscussPost中显示userId，查询根据外键userId查User表获取name
新建一个业务组件class **UserService**
添加注解@Service 让其能够被扫描到
方法中需要先添加mapper 
private UserMapper userMapper
方法为findUserById(int id)

### 开发视图层
配置文件 点击项目齿轮 不选Compact Middle Packages
static、templates下面需要多建几级包
资源css、html、img、js都是静态资源 复制粘贴到static文件夹下
资源site、**index.html首页**、mail复制粘贴到templates文件夹下
#### 开发controller
新建一个HomeController
添加注解@Controller
方法内注入Service
```java
@Autowired
private DiscussPostService discussPostService;

@Autowired
private UserService userService;
```
**添加GET方法**
```java
@RequestMapping(path = "/index",method = RequestMethod.*GET*)
public String getIndexPage(Model model){
    List< DiscussPost> list = discussPostService.findDisscussPosts(0,0,10);
    List<Map<String,Object>> discussPosts = new ArrayList<>();
    if(list != null){
        for(DiscussPost post :list){ //遍历循环list
            Map<String,Object> map = new HashMap<>();
            map.put("post",post);
            User user = userService.findUserById(post.getUserId());
            map.put("user",user);
            discussPosts.add(map);
        }
    }
    model.addAttribute("discussPosts",discussPosts);
    return "/index";
}
```
#### 分析index.html
首先添加html标签中 xmlns:th="http://www.thyemeleaf.org"
绝对路径修改th
```html
<link rel="stylesheet" th:href="@{/css/global.css}"/>
```
头部和尾部的部分不用动，修改内容部分的帖子列表
目前有10个li**静态固定 修改为动态**的话 保留一个li
在li标签上添加**th:each="map:${discussPosts}"** 循环discussPosts得到map
a标签：
img标签 修改 **th:src="${map.user.headerUrl}"**
帖子a标签内容 修改为**th:utext="${map.post.title}"**
**utext:**转义字符显示
置顶精华贴span标签 加判断 **th:if="${map.post.type==1}"**
用户名u标签 th:utext="${map.user.username}"
时间b标签 th:text="${map.post.createTime}" 这种显示会倒序

修改为${**#dates.format**(map.post.crateTime,'yyyy-MM-dd HH:mm:ss)}
修改结束，启动服务 CommunityApplication右键run
浏览器访问localhost:8080/community/index

#### 封装分页的组件
1、添加Page页 封装分页相关的信息 在entity文件夹下新建**page.class**
类中对象：
current当前页码、limit显示上限
数据的总数用于计算总页数rows、查询路径用来复用分页链接path
添加get、set方法
修改set方法：
setCurrent：判断current>=1
setLimit：判断limit>=1 && limit<=100
setRows：判断rows>=0
添加getOffset当前页的起始行、getTotal总页数、getFrom起始页码、getTo结束页码方法
2、修改HomeController 让其支持分页
（1）修改参数 传入page 并设置page的行数和路径
（2）修改方法 让其不用写死
3、修改index文件 
分页部分的代码：
（1）首页代码：
nav标签需要先判断page.rows需要大于0 th:if
li标签首页需要th:href="@{${page.path}(current=1)末页需要current={page.total}
上一页 下一页的disabled 判断是否为1 如果为1变为灰色 不可用

## 六、项目调试技巧
### 响应状态码的含义
网站https://developer.mozilla.org/zh-CN/Web/HTTP/Status
常见的：
**200**：请求成功
**302**：重定向 建议 访问location地址
4客户端响应 **404**：请求的资源未在服务器上发现-检查路径是否正确
5服务端响应 **500**：服务器遇到了不知道如何处理的情况

### 服务端断电调试技巧
在对应的地方加断点  右键debug
打开浏览器访问对应的index 可以看到浏览器一直在loading
让程序向下执行一行：F8
进入到当前行方法的内部：F7 按F8返回
程序继续执行 直到遇到下一个断点为止：F9
断点整体管理左下侧状态按钮方块下面药丸按钮可以统一取消断点

## 客户端断点调试技巧
浏览器上
在源代码中的js文件，打开index.js 发布按钮方法点击
浏览器右键检查打开调试工具elements主要是前端
console看输出结果 sources可以看对应的代码
让程序向下执行一行：F10
进入到当前行方法的内部：F11 
程序继续执行 直到遇到下一个断点为止：F8

### 设置日志级别，并将日志输出到不同的终端
默认日志工具logback 对应网址：https://logback.qos.ch
documentation里面的manual手册 级别：debug、info、warn、error
新建测试类-LoggerTest 引入注解
实例化日志接口private static final Logger 选择org.slf4j下的logger logger = LoggerFactory.getLogger(LoggerTests.class);
测试方法@Test

```java
public void testLogger(){
    System.out.prinln(logger.getName());
    logger.debug("debug log");
    logger.info("info log");
    logger.warn("warn log");
    logger.error("error log");
}
```
配置文件里面的配置debug 
执行测试方法
将日志存储在简单的文件里：在properties配置文件里 声明：
#logging.file=e:/project_work/data/nowcoder/community.log
复杂方法：准备好的logback-springbot.xml文件 放在resources文件下，修改xml文件
1、修改文件地址
2、error file：文件名log_error.log 存储超过5G 创建新文件 最长存储30天 追加 日志格式 
3、console：控制台打印 
4、保存的是debbug级别以上的log、info级别

## 版本控制
### 认识Git
官网https://git-scm.com book有中文版
改一下路径 其他一路默认next 
安装git bash（linux下的命令）、git cmd（windows下的命令）、git gui（可视化）
查看版本：git version
配置：git config --list 产看已有的
git config --global uer.name "Wangdan-code"
git config --global uer.email "15539354565@163.com"
### Git常见命令
1、代码存入本地仓库
首先先cd到对应的目录下
（1）git init
（2）git status查看状态
（3）git add * 边绿说明加入到了本地仓库
（4）git commit -m “Test1” 提交
修改一个文件 然后命令git status 可以看到红色文件
2、本地已经提交后的代码 存到远程仓库中
ssl安全传输，所以需要创建密钥 ssh-keygen -t rsa -C "15539354565@163.com"
 C:\Users\Administrator/.ssh/id_rsa.pub.文件下有这个密钥 添加到网址上 并新建项目 远程仓库的位置 https://github.com/Wangdan-code/mavendemo1.git
(1) git remote add origin https://github.com/Wangdan-code/mavendemo1.git
(2) git push -u origin master
输入密码和账号 
2021以后需要用token了 生成token 为Wangdan-code 
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
3、项目克隆到本地
git clone +git地址

### IDEA集成Git
(1)配置setting里面version control git里面的安装路径 选择git.exe
(2)VCS-创建git仓库
vcs上commit changes 选择test 备注：首次提交 红色的文件会变灰
