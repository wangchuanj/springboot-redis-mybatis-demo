# springboot-redis-mybatis-demo

### 一个基于springboot的独立项目框架
    集成了redis
    Tkmybatis
    logpack日志
    lombok编写基类
    阿里druid数据库连接池 项目访问 localhost/druid/index.html
    yml多环境配置
    (本人本地使用oracle数据库,如果使用mysql的话,pom文件添加MySQL JDBC)
### <!!!> 添加rapid-generator代码生成器<!!!>
    详情请看 * CodeGenerator
        //表名
        String[] tableNames = new String[]{"users"};
        //输出目录
        String outRoot = "code\\";`
    生成模版在:src/test/resources/templates/下 
        具体生成代码可以自己定制
    

    
    
    
