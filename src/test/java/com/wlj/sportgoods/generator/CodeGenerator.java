package com.wlj.sportgoods.generator;


import java.util.Scanner;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

public class CodeGenerator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入要生成代码的数据库表名：");
        String tableName = scanner.nextLine();
        AutoGenerator generator = new AutoGenerator();
        // 数据源配置
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setDbType(DbType.MYSQL)
                .setUrl("jdbc:mysql://127.0.0.1:3306/sport_goods?useUnicode=true&characterEncoding=utf8&useSSL=true&serverTimezone=UTC&useSSL=false")
                .setDriverName("com.mysql.cj.jdbc.Driver")
                .setUsername("root")
                .setPassword("135223");
        generator.setDataSource(dataSourceConfig);
        // 全局配置
        GlobalConfig globalConfig = new GlobalConfig();
        globalConfig.setOutputDir(System.getProperty("user.dir") + "/src/main/java")
                .setAuthor("wlj")
                .setOpen(false)
                .setFileOverride(true)
                .setServiceName("%sService")
                .setSwagger2(false);
        generator.setGlobalConfig(globalConfig);
        // 包配置
        PackageConfig packageConfig = new PackageConfig();
        packageConfig.setParent("com.wlj.sportgoods.user");
        generator.setPackageInfo(packageConfig);
        // 策略配置
        StrategyConfig strategyConfig = new StrategyConfig();
        strategyConfig.setNaming(NamingStrategy.underline_to_camel)
                .setColumnNaming(NamingStrategy.underline_to_camel)
                .setInclude(tableName) // 生成的表
                .setEntityLombokModel(true)
                .setRestControllerStyle(true);
        generator.setStrategy(strategyConfig);
        // 执行生成
        generator.execute();
    }
}


