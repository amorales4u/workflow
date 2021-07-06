package dev.c20.workflow.flow.entities;

import dev.c20.workflow.flow.entities.config.Activity;
import dev.c20.workflow.flow.entities.config.Config;
import dev.c20.workflow.flow.entities.config.Flow;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Map;
import java.util.TreeMap;

@Accessors(fluent = true)
@Getter
@Setter
public class WorkflowConfig {

    String flowPath;
    Config config = new Config();
    Map<String, Activity> activities = new TreeMap<>();
    Map<String, Flow> flow = new TreeMap<>();

}
