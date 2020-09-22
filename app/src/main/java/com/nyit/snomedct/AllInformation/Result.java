
package com.nyit.snomedct.AllInformation;

import java.util.List;
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
    @SerializedName("obsolete")
    @Expose
    private Boolean obsolete;
    @SerializedName("rootSource")
    @Expose
    private String rootSource;
    @SerializedName("atomCount")
    @Expose
    private Integer atomCount;
    @SerializedName("cVMemberCount")
    @Expose
    private Integer cVMemberCount;
    @SerializedName("attributes")
    @Expose
    private String attributes;
    @SerializedName("atoms")
    @Expose
    private String atoms;
    @SerializedName("descendants")
    @Expose
    private String descendants;
    @SerializedName("ancestors")
    @Expose
    private String ancestors;
    @SerializedName("parents")
    @Expose
    private String parents;
    @SerializedName("children")
    @Expose
    private String children;
    @SerializedName("relations")
    @Expose
    private String relations;
    @SerializedName("definitions")
    @Expose
    private String definitions;
    @SerializedName("concepts")
    @Expose
    private String concepts;
    @SerializedName("defaultPreferredAtom")
    @Expose
    private String defaultPreferredAtom;
    @SerializedName("subsetMemberships")
    @Expose
    private List<SubsetMembership> subsetMemberships = null;
    @SerializedName("contentViewMemberships")
    @Expose
    private List<ContentViewMembership> contentViewMemberships = null;
    @SerializedName("name")
    @Expose
    private String name;

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

    public Boolean getObsolete() {
        return obsolete;
    }

    public void setObsolete(Boolean obsolete) {
        this.obsolete = obsolete;
    }

    public String getRootSource() {
        return rootSource;
    }

    public void setRootSource(String rootSource) {
        this.rootSource = rootSource;
    }

    public Integer getAtomCount() {
        return atomCount;
    }

    public void setAtomCount(Integer atomCount) {
        this.atomCount = atomCount;
    }

    public Integer getCVMemberCount() {
        return cVMemberCount;
    }

    public void setCVMemberCount(Integer cVMemberCount) {
        this.cVMemberCount = cVMemberCount;
    }

    public String getAttributes() {
        return attributes;
    }

    public void setAttributes(String attributes) {
        this.attributes = attributes;
    }

    public String getAtoms() {
        return atoms;
    }

    public void setAtoms(String atoms) {
        this.atoms = atoms;
    }

    public String getDescendants() {
        return descendants;
    }

    public void setDescendants(String descendants) {
        this.descendants = descendants;
    }

    public String getAncestors() {
        return ancestors;
    }

    public void setAncestors(String ancestors) {
        this.ancestors = ancestors;
    }

    public String getParents() {
        return parents;
    }

    public void setParents(String parents) {
        this.parents = parents;
    }

    public String getChildren() {
        return children;
    }

    public void setChildren(String children) {
        this.children = children;
    }

    public String getRelations() {
        return relations;
    }

    public void setRelations(String relations) {
        this.relations = relations;
    }

    public String getDefinitions() {
        return definitions;
    }

    public void setDefinitions(String definitions) {
        this.definitions = definitions;
    }

    public String getConcepts() {
        return concepts;
    }

    public void setConcepts(String concepts) {
        this.concepts = concepts;
    }

    public String getDefaultPreferredAtom() {
        return defaultPreferredAtom;
    }

    public void setDefaultPreferredAtom(String defaultPreferredAtom) {
        this.defaultPreferredAtom = defaultPreferredAtom;
    }

    public List<SubsetMembership> getSubsetMemberships() {
        return subsetMemberships;
    }

    public void setSubsetMemberships(List<SubsetMembership> subsetMemberships) {
        this.subsetMemberships = subsetMemberships;
    }

    public List<ContentViewMembership> getContentViewMemberships() {
        return contentViewMemberships;
    }

    public void setContentViewMemberships(List<ContentViewMembership> contentViewMemberships) {
        this.contentViewMemberships = contentViewMemberships;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
