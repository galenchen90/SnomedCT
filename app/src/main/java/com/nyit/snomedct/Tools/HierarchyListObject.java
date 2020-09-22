package com.nyit.snomedct.Tools;

import com.nyit.snomedct.ConceptResults.AtomObject;

import java.util.List;

public class HierarchyListObject {

    String mapTitle;
    List<AtomObject> atomObjectList;

    public HierarchyListObject(String mapTitle, List<AtomObject> atomObjectList) {
        this.mapTitle = mapTitle;
        this.atomObjectList = atomObjectList;
    }

    public String getMapTitle() {
        return mapTitle;
    }

    public void setMapTitle(String mapTitle) {
        this.mapTitle = mapTitle;
    }

    public List<AtomObject> getAtomObjectList() {
        return atomObjectList;
    }

    public void setAtomObjectList(List<AtomObject> atomObjectList) {
        this.atomObjectList = atomObjectList;
    }
}
