package com.edenspring.framework.redis;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = RedisProperties.PREFIX)
public class RedisProperties {

    static final String PREFIX = "edenspring-framework.redis";

    /**
     * 应用前缀
     */
    @Value("${spring.application.name}")
    private String keyPrefix;

    private Lock lock = new Lock();

    @Getter
    @Setter
    public static class Lock {

        /**
         * 锁失效时间
         */
        private long expireAfter = 60;

    }

}
