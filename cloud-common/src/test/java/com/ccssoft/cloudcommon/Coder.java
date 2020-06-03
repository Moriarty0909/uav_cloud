package com.ccssoft.cloudcommon;

//import com.baomidou.mybatisplus.annotation.DbType;
//import com.baomidou.mybatisplus.annotation.FieldFill;
//import com.baomidou.mybatisplus.annotation.IdType;
//import com.baomidou.mybatisplus.core.toolkit.StringPool;
//import com.baomidou.mybatisplus.generator.AutoGenerator;
//import com.baomidou.mybatisplus.generator.InjectionConfig;
//import com.baomidou.mybatisplus.generator.config.*;
//import com.baomidou.mybatisplus.generator.config.po.TableFill;
//import com.baomidou.mybatisplus.generator.config.po.TableInfo;
//import com.baomidou.mybatisplus.generator.config.rules.DateType;
//import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
//import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
//import org.springframework.data.redis.connection.DataType;
//import javax.xml.crypto.Data;
//import java.util.ArrayList;
//import java.util.List;

/**
 * @author moriarty
 * @date 2020/5/28 17:56
 */
//public class Coder {
//    public static void main(String[] args) {
//        // 代码生成器
//        AutoGenerator mpg = new AutoGenerator();
//
//        // 全局配置
//        GlobalConfig gc = new GlobalConfig();
//        String projectPath = System.getProperty("user.dir");
//        System.out.println(projectPath);
//        gc.setOutputDir(projectPath + "/cloud-task/src/main/java");
//        gc.setAuthor("moriarty");
//        //是否打开资源管理器
//        gc.setOpen(false);
//        gc.setServiceName("%sService");
//        gc.setServiceImplName("%sServiceImpl");//去前缀I
//        gc.setIdType(IdType.AUTO);
//        gc.setDateType(DateType.ONLY_DATE);
//
//        // gc.setSwagger2(true); 实体属性 Swagger2 注解
//        mpg.setGlobalConfig(gc);
//
//
//        // 数据源配置
//        DataSourceConfig dsc = new DataSourceConfig();
//        dsc.setUrl("jdbc:mysql://183.56.219.211:33066/uav_cloud?useUnicode=true&useSSL=false&characterEncoding=utf8&serverTimezone=GMT%2B8");
//        // dsc.setSchemaName("public");
//        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
//        dsc.setUsername("root");
//        dsc.setPassword("123456");
//        dsc.setDbType(DbType.MYSQL);
//        mpg.setDataSource(dsc);
//
//        // 包配置
//        PackageConfig pc = new PackageConfig();
////        pc.setModuleName("cloud-airspace");
//        pc.setParent("com.ccssoft.cloudtask");
//        pc.setEntity("entity");
//        pc.setMapper("dao");
//        pc.setService("service");
//        pc.setController("controller");
//        mpg.setPackageInfo(pc);
//
//
//        // 策略配置
//        StrategyConfig strategy = new StrategyConfig();
//        strategy.setInclude("task_natrue");//设置要映射的表名
//        strategy.setNaming(NamingStrategy.underline_to_camel);
//        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
//        strategy.setEntityLombokModel(true);//自动生成lombok
//        //逻辑删除
//        strategy.setLogicDeleteFieldName("delete");
//        //自动填充配置
//        TableFill gmtCreate = new TableFill("gmt_create", FieldFill.INSERT);
//        TableFill gmtModified = new TableFill("gmt_modified", FieldFill.INSERT_UPDATE);
//        List<TableFill> list = new ArrayList<>();
//        list.add(gmtCreate);
//        list.add(gmtModified);
//        strategy.setTableFillList(list);
//
//        //乐观锁
////        strategy.setVersionFieldName("version");
//
//        strategy.setRestControllerStyle(true);
//
////        strategy.setControllerMappingHyphenStyle(true)
//        mpg.setStrategy(strategy);
//
//        mpg.execute();
//    }
//}
