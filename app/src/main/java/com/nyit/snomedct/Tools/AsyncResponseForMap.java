package com.nyit.snomedct.Tools;

import com.nyit.snomedct.ConceptResults.AtomObject;

import java.util.List;

public interface AsyncResponseForMap {

        void processFinish(List<List<AtomObject>> listofLists);

}
