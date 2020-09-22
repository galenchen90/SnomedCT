
package com.nyit.snomedct.AllInformation;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SubsetMembership {

    @SerializedName("memberUri")
    @Expose
    private String memberUri;
    @SerializedName("uri")
    @Expose
    private String uri;
    @SerializedName("name")
    @Expose
    private String name;

    public String getMemberUri() {
        return memberUri;
    }

    public void setMemberUri(String memberUri) {
        this.memberUri = memberUri;
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
