package com.nyit.snomedct.ConceptResults;

public class RelationConceptObject {


    String id,conceptName,uri, relationcontext;

    public RelationConceptObject(String id, String conceptName, String uri, String relationConext) {
        this.id = id;
        this.conceptName = conceptName;
        this.uri = uri;
        this.relationcontext = relationConext;
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

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getRelationcontext() {
        return relationcontext;
    }

    public void setRelationcontext(String relationcontext) {
        this.relationcontext = relationcontext;
    }
}
