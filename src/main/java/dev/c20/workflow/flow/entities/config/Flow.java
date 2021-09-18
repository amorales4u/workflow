package dev.c20.workflow.flow.entities.config;

import java.util.LinkedList;
import java.util.List;

public class Flow {
    String defaultGoto = null;
    List<FlowWhen> when = new LinkedList<>();

}
