package com.leyou.gateway.properties;

import com.leyou.auth.utils.RsaUtils;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.security.PublicKey;

@Component
@ConfigurationProperties(prefix = "leyou.jwt")
@Data
public class JwtProperties {
    private String pubKeyPath;
    private PublicKey publicKey;
    private String cookieName;
    /**
     * @PostContruct：在构造方法执行之后执行该方法
     */
    @PostConstruct
    public void init(){
        try {
            // 获取公钥和私钥
            this.publicKey = RsaUtils.getPublicKey(pubKeyPath);
        } catch (Exception e) {
            System.out.println("初始化公钥失败！");
            throw new RuntimeException();
        }
    }

}
