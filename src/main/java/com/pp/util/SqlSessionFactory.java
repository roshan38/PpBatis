package com.pp.util;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.pp.framework.Configuration;
import com.pp.framework.Environment;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

public class SqlSessionFactory {
    private DataSource dataSource;
    private Configuration configuration;

    SqlSessionFactory(Configuration configuration){
        this.configuration = configuration;
        String defaultEnvironmentId = configuration.getDefaultEnvironmentId();
        List<Environment> environments = configuration.getEnvironments();
        for (Environment environment : environments) {
            if (environment.getId().equals(defaultEnvironmentId)){
                String dataSourcesType = environment.getDataSourcesType();
                if (dataSourcesType.equals("com.alibaba.druid.pool.DruidDataSource")){
                    Properties properties = new Properties();
                    properties.setProperty("driver",environment.getDriver());
                    properties.setProperty("url",environment.getUrl());
                    properties.setProperty("username",environment.getUsername());
                    properties.setProperty("password",environment.getPassword());
                    try {
                        dataSource=DruidDataSourceFactory.createDataSource(properties);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
            }
        }
    }

    public SqlSession getSqlSession() throws SQLException {
        Connection connection = dataSource.getConnection();
        return new SqlSession(connection,configuration);
    }
}
