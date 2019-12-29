package com.pp.framework;

public class Environment {
    private String id;
    private String transactionManagerType;
    private String dataSourcesType;
    private String driver;
    private String url;
    private String username;
    private String password;

    @Override
    public String toString() {
        return "Environment{" +
                "id='" + id + '\'' +
                ", transactionManagerType='" + transactionManagerType + '\'' +
                ", dataSourcesType='" + dataSourcesType + '\'' +
                ", driver='" + driver + '\'' +
                ", url='" + url + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    void setId(String id) {
        this.id = id;
    }

    public String getTransactionManagerType() {
        return transactionManagerType;
    }

    void setTransactionManagerType(String transactionManagerType) {
        this.transactionManagerType = transactionManagerType;
    }

    public String getDataSourcesType() {
        return dataSourcesType;
    }

    void setDataSourcesType(String dataSourcesType) {
        this.dataSourcesType = dataSourcesType;
    }

    public String getDriver() {
        return driver;
    }

    void setDriver(String driver) {
        this.driver = driver;
    }

    public String getUrl() {
        return url;
    }

    void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    void setPassword(String password) {
        this.password = password;
    }
}
