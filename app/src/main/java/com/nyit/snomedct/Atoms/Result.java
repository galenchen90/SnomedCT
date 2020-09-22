
package com.nyit.snomedct.Atoms;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Result {

    @SerializedName("classType")
    @Expose
    private String classType;
    @SerializedName("ui")
    @Expose
    private String ui;
    @SerializedName("suppressible")
    @Expose
    private String suppressible;
    @SerializedName("obsolete")
    @Expose
    private String obsolete;
    @SerializedName("rootSource")
    @Expose
    private String rootSource;
    @SerializedName("termType")
    @Expose
    private String termType;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("concept")
    @Expose
    private String concept;
    @SerializedName("sourceConcept")
    @Expose
    private String sourceConcept;
    @SerializedName("sourceDescriptor")
    @Expose
    private String sourceDescriptor;
    @SerializedName("attributes")
    @Expose
    private String attributes;
    @SerializedName("parents")
    @Expose
    private String parents;
    @SerializedName("ancestors")
    @Expose
    private Object ancestors;
    @SerializedName("children")
    @Expose
    private String children;
    @SerializedName("descendants")
    @Expose
    private Object descendants;
    @SerializedName("relations")
    @Expose
    private String relations;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("language")
    @Expose
    private String language;
    @SerializedName("contentViewMemberships")
    @Expose
    private List<ContentViewMembership> contentViewMemberships = null;

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

    public String getSuppressible() {
        return suppressible;
    }

    public void setSuppressible(String suppressible) {
        this.suppressible = suppressible;
    }

    public String getObsolete() {
        return obsolete;
    }

    public void setObsolete(String obsolete) {
        this.obsolete = obsolete;
    }

    public String getRootSource() {
        return rootSource;
    }

    public void setRootSource(String rootSource) {
        this.rootSource = rootSource;
    }

    public String getTermType() {
        return termType;
    }

    public void setTermType(String termType) {
        this.termType = termType;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getConcept() {
        return concept;
    }

    public void setConcept(String concept) {
        this.concept = concept;
    }

    public String getSourceConcept() {
        return sourceConcept;
    }

    public void setSourceConcept(String sourceConcept) {
        this.sourceConcept = sourceConcept;
    }

    public String getSourceDescriptor() {
        return sourceDescriptor;
    }

    public void setSourceDescriptor(String sourceDescriptor) {
        this.sourceDescriptor = sourceDescriptor;
    }

    public String getAttributes() {
        return attributes;
    }

    public void setAttributes(String attributes) {
        this.attributes = attributes;
    }

    public String getParents() {
        return parents;
    }

    public void setParents(String parents) {
        this.parents = parents;
    }

    public Object getAncestors() {
        return ancestors;
    }

    public void setAncestors(Object ancestors) {
        this.ancestors = ancestors;
    }

    public String getChildren() {
        return children;
    }

    public void setChildren(String children) {
        this.children = children;
    }

    public Object getDescendants() {
        return descendants;
    }

    public void setDescendants(Object descendants) {
        this.descendants = descendants;
    }

    public String getRelations() {
        return relations;
    }

    public void setRelations(String relations) {
        this.relations = relations;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public List<ContentViewMembership> getContentViewMemberships() {
        return contentViewMemberships;
    }

    public void setContentViewMemberships(List<ContentViewMembership> contentViewMemberships) {
        this.contentViewMemberships = contentViewMemberships;
    }

}
