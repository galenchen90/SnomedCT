package com.nyit.snomedct.ConceptResults;

import java.util.List;

public class ConceptObject {

    String id,conceptName,uri;
    List<AtomObject> synonyms;
    List<RelationConceptObject> relationContext;


    public ConceptObject(String id, String conceptName, String uri,List<AtomObject> synonyms) {
        this.id = id;
        this.conceptName = conceptName;
        this.uri=uri;
        this.synonyms=synonyms;

    }

    public ConceptObject(String id, String conceptName, String uri, List<AtomObject> synonyms, List<RelationConceptObject> relationContext) {
        this.id = id;
        this.conceptName = conceptName;
        this.uri = uri;
        this.synonyms = synonyms;
        this.relationContext = relationContext;
    }

    public ConceptObject(String id, String conceptName, String uri) {
        this.id = id;
        this.conceptName = conceptName;
        this.uri = uri;
    }

    public List<AtomObject> getSynonyms() {
        return synonyms;
    }


    public List<RelationConceptObject> getRelationContext() {
        return relationContext;
    }

    public void setRelationContext(List<RelationConceptObject> relationContext) {
        this.relationContext = relationContext;
    }

    public void setSynonyms(List<AtomObject> synonyms) {
        this.synonyms = synonyms;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getConceptName() {
        return conceptName;
    }

    public void setConceptName(String conceptName) {
        this.conceptName = conceptName;
    }
}
