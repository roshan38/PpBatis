package com.pp.framework;

public class Update {
    private String id;
    private String text;

    public String getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    void setText(String text) {
        this.text = text;
    }

    void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Update{" +
                "id='" + id + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
