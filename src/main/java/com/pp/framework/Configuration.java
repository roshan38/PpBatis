package com.pp.framework;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Configuration {
    private List<Environment> environments = new ArrayList<>();
    private List<Mapper> mappers = new ArrayList<>();
    private String defaultEnvironmentId;

    public Configuration(InputStream inputStream){
        try {
            Document document = new SAXReader().read(inputStream);
            Element root = document.getRootElement();
            setEnvironments(root);
            setMappers(root);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    private void setMappers(Element root) throws DocumentException {
        Element mappersEle = root.element("mappers");
        if (mappersEle!=null){
            List<Element> packageList = mappersEle.elements();
            if (packageList!=null&&packageList.size()!=0){
                for (Element pack : packageList) {
                    String packagePath = pack.attributeValue("name").replace(".","/");
                    String url = Configuration.class.getResource("/").getPath().replace("test-","");
                    StringBuilder sb = new StringBuilder(url);
                    packagePath=sb.append(packagePath).delete(0,1).toString();
                    System.out.println(packagePath);
                    File file = new File(packagePath);
                    searchMapper(file);
                }
            }
        }
    }

    private void setEnvironments(Element root) {
        Element environmentsEle = root.element("environments");
        if (environmentsEle != null) {
            defaultEnvironmentId=environmentsEle.attributeValue("default");
            List<Element> environmentEleList = environmentsEle.elements();
            if (environmentEleList != null && environmentEleList.size() != 0) {
                for (Element environmentEle : environmentEleList) {
                    Environment environment = new Environment();
                    environment.setId(environmentEle.attributeValue("id"));
                    environment.setTransactionManagerType(environmentEle.element("transactionManager").attributeValue("type"));
                    Element dataSource = environmentEle.element("dataSource");
                    environment.setDataSourcesType(dataSource.attributeValue("type"));
                    List<Element> properties = dataSource.elements();
                    for (Element property : properties) {
                        String name = property.attributeValue("name");
                        switch (name){
                            case "driver":
                                environment.setDriver(property.attributeValue("value"));
                            case "url":
                                environment.setUrl(property.attributeValue("value"));
                            case "username":
                                environment.setUsername(property.attributeValue("value"));
                            case "password":
                                environment.setPassword(property.attributeValue("value"));
                        }
                    }
                    environments.add(environment);
                }
            }
        }
    }

    public List<Environment> getEnvironments() {
        return environments;
    }

    public List<Mapper> getMappers() {
        return mappers;
    }

    public String getDefaultEnvironmentId() {
        return defaultEnvironmentId;
    }

    private void searchMapper(File file) throws DocumentException {
        if (file.isDirectory()){
            File[] files = file.listFiles();
            if (files != null && files.length != 0) {
                for (File childFile : files) {
                    searchMapper(childFile);
                }
            }
        }else {
            if (file.getName().endsWith(".xml")){
                Document mapperXml = new SAXReader().read(file);
                Mapper mapper = new Mapper();
                Element root = mapperXml.getRootElement();
                mapper.setNamespace(root.attributeValue("namespace"));
                List<Element> selectEleList = root.elements("select");
                if (selectEleList != null && selectEleList.size() != 0) {
                    for (Element selectEle : selectEleList) {
                        Select select = new Select();
                        select.setId(selectEle.attributeValue("id"));
                        select.setResultType(selectEle.attributeValue("resultType"));
                        select.setText(selectEle.getTextTrim());
                        mapper.getSelects().add(select);
                    }
                }
                Stream allStream = Stream.of();
                List delete = root.elements("delete");
                Stream deleteStream = null;
                if (delete!=null&&delete.size()!=0){
                    deleteStream=delete.stream();
                    allStream=Stream.concat(allStream,deleteStream);
                }
                List insert = root.elements("insert");
                Stream insertStream = null;
                if (insert!=null&&insert.size()!=0){
                    insertStream=insert.stream();
                    allStream=Stream.concat(allStream,insertStream);
                }
                List update = root.elements("update");
                Stream updateStream = null;
                if (update!=null&&update.size()!=0){
                    updateStream=update.stream();
                    allStream=Stream.concat(allStream,updateStream);
                }
                List<Element> allEleList = (List<Element>) allStream.collect(Collectors.toList());
                if (allEleList != null && allEleList.size() != 0) {
                    for (Element allEle : allEleList) {
                        Update all = new Update();
                        all.setId(allEle.attributeValue("id"));
                        all.setText(allEle.getTextTrim());
                        mapper.getUpdates().add(all);
                    }
                }
                mappers.add(mapper);
            }
        }
    }
}
