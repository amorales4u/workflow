package dev.c20.workflow.flow.entities.config;

import java.util.LinkedList;
import java.util.List;

public class FlowWhen {
    List<String> when = new LinkedList<>();
    String gotoActivity;
}
