package com.leyou.auth.properties;

import com.leyou.auth.utils.RsaUtils;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.security.PrivateKey;
import java.security.PublicKey;

@Component
@ConfigurationProperties(prefix = "leyou.jwt")
@Data
public class JwtProperties {
    private String secret; // 密钥
    private  String pubKeyPath; // 公钥
    private  String priKeyPath; // 私钥
    private int expire; // 过期时间
    private PublicKey publicKey; // 公钥
    private PrivateKey privateKey; // 私钥
    private String cookieName;
    private String cookieMaxAge;
    /**
     * @PostContruct：在构造方法执行之后执行该方法
     */
    @PostConstruct
    public void init(){
        try {
            File pubKey = new File(pubKeyPath);
            File priKey = new File(priKeyPath);
            if (!pubKey.exists() || !priKey.exists()) {
                // 生成公钥和私钥
                RsaUtils.generateKey(pubKeyPath, priKeyPath, secret);
            }
            // 获取公钥和私钥
            this.publicKey = RsaUtils.getPublicKey(pubKeyPath);
            this.privateKey = RsaUtils.getPrivateKey(priKeyPath);
        } catch (Exception e) {
            System.out.println("初始化公钥和私钥失败！");
            throw new RuntimeException();
        }
    }
}
