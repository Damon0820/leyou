package com.leyou.user.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "leyou.sms")
@Data
public class SmsConfig {
    String accessKeyId;

    String accessKeySecret;

    String signName;

    String templateCode;
}
