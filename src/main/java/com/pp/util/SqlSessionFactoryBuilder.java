package com.pp.util;

import com.pp.framework.Configuration;

import java.io.InputStream;
import java.sql.SQLException;

public class SqlSessionFactoryBuilder {
    public static SqlSessionFactory build(InputStream inputStream) throws SQLException {
        Configuration configuration = new Configuration(inputStream);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactory(configuration);
        return sqlSessionFactory;
    }
}
