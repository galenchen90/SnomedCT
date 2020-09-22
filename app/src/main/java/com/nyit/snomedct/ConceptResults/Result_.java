
package com.nyit.snomedct.ConceptResults;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Result_ {

    @SerializedName("ui")
    @Expose
    private String ui;
    @SerializedName("rootSource")
    @Expose
    private String rootSource;
    @SerializedName("uri")
    @Expose
    private String uri;
    @SerializedName("name")
    @Expose
    private String name;

    public String getUi() {
        return ui;
    }

    public void setUi(String ui) {
        this.ui = ui;
    }

    public String getRootSource() {
        return rootSource;
    }

    public void setRootSource(String rootSource) {
        this.rootSource = rootSource;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
