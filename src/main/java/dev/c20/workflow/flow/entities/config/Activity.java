package dev.c20.workflow.flow.entities.config;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Activity {
    String description;
    Integer order = 0;
    String type;
    String showOnlyBy;
    Map<String,ListDataType> listDataAs = new TreeMap<>();
    String editor;
    String listView;
    String previewView;
    List<CompleteLabel> completeLabels = new LinkedList<>();
    Map<String,String> perms = new TreeMap<>();

}
