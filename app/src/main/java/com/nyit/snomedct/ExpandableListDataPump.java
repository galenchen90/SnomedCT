package com.nyit.snomedct;

import com.nyit.snomedct.ConceptResults.AtomObject;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class ExpandableListDataPump {

    public static LinkedHashMap<String,List<AtomObject>> getData(List<AtomObject> Objects,List<AtomObject> relationObjects){

        LinkedHashMap<String,List<AtomObject>> expandableListDetail = new LinkedHashMap();
        List<AtomObject> map = new ArrayList<>();
        map.add(new AtomObject( "d","d","d","d"));

        expandableListDetail.put("Description", Objects);
        expandableListDetail.put("Relations", relationObjects);
        expandableListDetail.put("Hierarchy Map", map);

        return expandableListDetail;
    }
}
