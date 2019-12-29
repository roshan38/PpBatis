package com.pp.framework;

public class Select {
    private String id;
    private String resultType;
    private String text;

    public String getId() {
        return id;
    }

    void setId(String id) {
        this.id = id;
    }

    public String getResultType() {
        return resultType;
    }

    public String getText() {
        return text;
    }

    void setText(String text) {
        this.text = text;
    }

    void setResultType(String resultType) {
        this.resultType = resultType;
    }

    @Override
    public String toString() {
        return "Select{" +
                "id='" + id + '\'' +
                ", resultType='" + resultType + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
