package com.wanfeng.apis.sdk;

import com.wanfeng.apis.sdk.client.wanfengInterFaceClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Hello world!
 */
@Configuration
public class wanfengSDKConfig {
    private String accessKey;
    private String secretKey;
    private String url;
    @Bean
    public wanfengInterFaceClient wfClient() {
        return new wanfengInterFaceClient(url, accessKey, secretKey);
    }

}
