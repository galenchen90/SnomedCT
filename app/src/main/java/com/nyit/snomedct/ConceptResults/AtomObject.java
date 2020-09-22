package com.nyit.snomedct.ConceptResults;

import java.io.Serializable;

public class AtomObject implements Serializable {
    String id,name,type,url,context;
    Boolean active;

    public AtomObject(String id, String name, String type, Boolean active) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.active = active;
    }

    public AtomObject(String id, String name,  String url, String context) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.context = context;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
