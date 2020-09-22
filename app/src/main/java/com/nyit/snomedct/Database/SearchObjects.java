package com.nyit.snomedct.Database;

public class SearchObjects {
    String time, keyword;

    public SearchObjects(String time, String keyword) {
        this.time = time;
        this.keyword = keyword;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
