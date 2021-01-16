package com.leyou.gateway.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConfigurationProperties(prefix = "leyou.filter")
@Data
public class FilterProperties {
    private List<String> allowPaths;
}
