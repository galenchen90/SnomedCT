package com.nyit.snomedct.Database;

public class KeywordSingleton {

    String keyword;
    public static final KeywordSingleton ourInstance = new KeywordSingleton();

    public static KeywordSingleton getInstance(){
        return ourInstance;
    }
    private KeywordSingleton() {}


    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
