package com.telecom.sensors;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Setter
@Getter
@Component
@ConfigurationProperties(prefix = "directories")
public class Configuration {
    private List<String> process;
    private String treated;
    private String archive;
}
