package com.nyit.snomedct.UMLS;

public class TGTSingleton {

    String TGT;
    private static final TGTSingleton ourInstance= new TGTSingleton();
    public static TGTSingleton getInstance(){
        return ourInstance;
    }
    private TGTSingleton() {}
    public void setTGT(String TGT){
        this.TGT=TGT;
    }
    public String getTGT(){
        return TGT;
    }
}
