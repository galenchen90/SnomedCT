package com.nyit.snomedct.Tools;

import com.nyit.snomedct.ConceptResults.AtomObject;

import java.util.List;

public interface AsyncResponseForParents {

        void processFinish(List<AtomObject> objectList);

}
