package com.dare.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: shengyao
 * @Package: com.dare.config
 * @Date: 2020/10/23 15:15
 * @Description: minIo配置
 * @version: 1.0
 */
@Data
@Configuration
public class MinIoConfig {

    @Value(value = "${minIo.endpoint}")
    private String endpoint;
    @Value(value = "${minIo.accessKey}")
    private String accessKey;
    @Value(value = "${minIo.secretKey}")
    private String secretKey;
}
