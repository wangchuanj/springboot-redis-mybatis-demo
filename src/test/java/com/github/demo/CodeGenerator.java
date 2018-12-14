package com.github.demo;

import cn.org.rapid_framework.generator.GeneratorFacade;
import cn.org.rapid_framework.generator.GeneratorProperties;
import org.junit.Test;

import java.util.Properties;

/**
 * 代码生成
 */
public class CodeGenerator {

    @Test
    public void generator() throws Exception {
        //表名，为空则全部
        String[] tableNames = new String[]{"users"};
        //输出目录
        String outRoot = "code\\";

        String template = "src\\test\\resources\\templates";
        Properties properties = new Properties();
        properties.setProperty("outRoot", outRoot);
        properties.setProperty("basepackage", "com.github.demo");
        properties.setProperty("jdbc.username", "zhao");
        properties.setProperty("jdbc.password", "zhao");
        properties.setProperty("jdbc.url", "jdbc:oracle:thin:@192.168.0.66:1521:zhaotest");
        properties.setProperty("jdbc.driver", "oracle.jdbc.driver.OracleDriver");
        properties.setProperty("java_typemapping.java.sql.Timestamp", "java.util.Date");
        properties.setProperty("java_typemapping.java.sql.Date", "java.util.Date");
        properties.setProperty("java_typemapping.java.sql.Time", "java.util.Date");
        GeneratorProperties.setProperties(properties);
        GeneratorFacade generatorFacade = new GeneratorFacade();
        if (null == tableNames || tableNames.length == 0) {
            generatorFacade.generateByAllTable(template);
        } else {
            for (String tableName : tableNames) {
                generatorFacade.generateByTable(tableName, template);
            }
        }
    }

}
