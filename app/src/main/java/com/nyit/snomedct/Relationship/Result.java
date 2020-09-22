
package com.nyit.snomedct.Relationship;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Result {

    @SerializedName("classType")
    @Expose
    private String classType;
    @SerializedName("ui")
    @Expose
    private String ui;
    @SerializedName("suppressible")
    @Expose
    private Boolean suppressible;
    @SerializedName("sourceUi")
    @Expose
    private String sourceUi;
    @SerializedName("obsolete")
    @Expose
    private Boolean obsolete;
    @SerializedName("sourceOriginated")
    @Expose
    private Boolean sourceOriginated;
    @SerializedName("rootSource")
    @Expose
    private String rootSource;
    @SerializedName("relationLabel")
    @Expose
    private String relationLabel;
    @SerializedName("additionalRelationLabel")
    @Expose
    private String additionalRelationLabel;
    @SerializedName("groupId")
    @Expose
    private String groupId;
    @SerializedName("attributeCount")
    @Expose
    private Integer attributeCount;
    @SerializedName("relatedId")
    @Expose
    private String relatedId;
    @SerializedName("relatedIdName")
    @Expose
    private String relatedIdName;

    public String getClassType() {
        return classType;
    }

    public void setClassType(String classType) {
        this.classType = classType;
    }

    public String getUi() {
        return ui;
    }

    public void setUi(String ui) {
        this.ui = ui;
    }

    public Boolean getSuppressible() {
        return suppressible;
    }

    public void setSuppressible(Boolean suppressible) {
        this.suppressible = suppressible;
    }

    public String getSourceUi() {
        return sourceUi;
    }

    public void setSourceUi(String sourceUi) {
        this.sourceUi = sourceUi;
    }

    public Boolean getObsolete() {
        return obsolete;
    }

    public void setObsolete(Boolean obsolete) {
        this.obsolete = obsolete;
    }

    public Boolean getSourceOriginated() {
        return sourceOriginated;
    }

    public void setSourceOriginated(Boolean sourceOriginated) {
        this.sourceOriginated = sourceOriginated;
    }

    public String getRootSource() {
        return rootSource;
    }

    public void setRootSource(String rootSource) {
        this.rootSource = rootSource;
    }

    public String getRelationLabel() {
        return relationLabel;
    }

    public void setRelationLabel(String relationLabel) {
        this.relationLabel = relationLabel;
    }

    public String getAdditionalRelationLabel() {
        return additionalRelationLabel;
    }

    public void setAdditionalRelationLabel(String additionalRelationLabel) {
        this.additionalRelationLabel = additionalRelationLabel;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public Integer getAttributeCount() {
        return attributeCount;
    }

    public void setAttributeCount(Integer attributeCount) {
        this.attributeCount = attributeCount;
    }

    public String getRelatedId() {
        return relatedId;
    }

    public void setRelatedId(String relatedId) {
        this.relatedId = relatedId;
    }

    public String getRelatedIdName() {
        return relatedIdName;
    }

    public void setRelatedIdName(String relatedIdName) {
        this.relatedIdName = relatedIdName;
    }

}
