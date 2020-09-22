package com.nyit.snomedct.Database;

public class FavoriteObject {
    String name, url, id, state;


    public FavoriteObject(String id,String name, String url,  String state) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.state = state;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
