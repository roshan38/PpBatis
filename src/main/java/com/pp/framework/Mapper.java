package com.pp.framework;

import java.util.ArrayList;
import java.util.List;

public class Mapper {
    private String namespace;
    private List<Select> selects = new ArrayList<>();
    private List<Update> updates = new ArrayList<>();

    @Override
    public String toString() {
        return "Mapper{" +
                "namespace='" + namespace + '\'' +
                ", selects=" + selects +
                ", updates=" + updates +
                '}';
    }

    public String getNamespace() {
        return namespace;
    }

    void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public List<Select> getSelects() {
        return selects;
    }

    void setSelects(List<Select> selects) {
        this.selects = selects;
    }

    public List<Update> getUpdates() {
        return updates;
    }

    void setUpdates(List<Update> updates) {
        this.updates = updates;
    }
}
