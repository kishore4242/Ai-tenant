package com.aitenant.web_service.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "secondary-replica")
@Configuration
@Setter
@Getter
public class SecondaryReplica {
    private String url;
    private String user;
    private String password;
    private String driverClassName;
    private int poolConnection;
}
