package com.pp.util;

import com.pp.exception.NoSuchMethodException;
import com.pp.framework.Configuration;
import com.pp.framework.Update;
import com.pp.framework.Mapper;
import com.pp.framework.Select;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SqlSession {
    private Connection connection;
    private Configuration configuration;

    SqlSession(Connection connection, Configuration configuration){
        this.connection = connection;
        this.configuration = configuration;
    }

    public <T> T getMapper(Class<T> mapperClass) {
        return (T) Proxy.newProxyInstance(mapperClass.getClassLoader(), new Class[]{mapperClass}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                String methodName = method.getName();
                List<Mapper> mappers = configuration.getMappers();
                String namespace = mapperClass.getName();
                for (Mapper mapper : mappers) {
                    if (namespace.equals(mapper.getNamespace())){
                        List<Select> selects = mapper.getSelects();
                        for (Select select : selects) {
                            String resultType = select.getResultType();
                            Class resultClass = Class.forName(resultType);
                            if (methodName.equals(select.getId())) {
                                String sql = replaceSql(select.getText());
                                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                                if (args != null) {
                                    for (int i = 0; i < args.length; i++) {
                                        preparedStatement.setObject(i, args[i]);
                                    }
                                }
                                ArrayList<Object> list = new ArrayList<>();
                                ResultSet resultSet = preparedStatement.executeQuery();
                                Field[] fields = resultClass.getDeclaredFields();
                                while (resultSet.next()){
                                    Object o = resultClass.getDeclaredConstructor().newInstance();
                                    for (Field field : fields) {
                                        field.setAccessible(true);
                                        field.set(o,resultSet.getObject(field.getName()));
                                    }
                                    list.add(o);
                                }
                                return list;
                            }
                        }
                        List<Update> updates = mapper.getUpdates();
                        for (Update update : updates) {
                            if (methodName.equals(update.getId())) {
                                String sql = replaceSql(update.getText());
                                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                                if (args != null) {
                                    for (int i = 0; i < args.length; i++) {
                                        preparedStatement.setObject(i+1,args[i]);
                                    }
                                }
                                int num = preparedStatement.executeUpdate();
                                return num;
                            }
                        }
                    }
                }
                throw new NoSuchMethodException("\""+methodName+"\" isn't be defined in mapper XML");
            }
        });
    }

    void setConnection(Connection connection) {
        this.connection=connection;
    }

    public void close(){
        if (connection!=null) {
            try {
                this.connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public String replaceSql(String sql){
        int start = sql.indexOf("#");
        int end = sql.indexOf("}");
        if (start != -1 && end != -1) {
            StringBuilder sb = new StringBuilder(sql);
            sb.replace(start, end+1, "?");
            return sb.toString();
        }else {
            return sql;
        }
    }
}
