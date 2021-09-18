package dev.c20.workflow.flow.entities.config;

import java.util.Map;
import java.util.TreeMap;

public class Config {

    String startFolder;
    String clientContext;
    String clientService;
    Boolean saveHistory;
    String keyCounter;
    String keyFormat;
    String indexOn;
    String editor;
    String preview;
    Map<String, String> perms = new TreeMap<>();

}

