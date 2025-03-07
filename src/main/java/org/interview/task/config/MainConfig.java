package org.interview.task.config;

import org.aeonbits.owner.Config;

@Config.Sources("file:src/main/resources/config.properties")
public interface MainConfig extends Config{

    @Config.Key("baseBitmexUrl")
    String baseBitmexUrl();

    @Config.Key("baseSearchUrl")
    String baseSearchUrl();
}
