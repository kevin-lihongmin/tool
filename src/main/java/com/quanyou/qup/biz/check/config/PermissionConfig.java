package com.quanyou.qup.biz.check.config;

import com.quanyou.qup.biz.check.constant.GlobalConst;
import com.quanyou.qup.permission.Config;
import com.quanyou.qup.permission.PermissionAspect;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * Permission Configuration
 *
 * @author 抓抓匠
 * @since 2020-05-18
 */
@Configuration
@RequiredArgsConstructor
public class PermissionConfig {
    private final RedisTemplate redisTemplate;

    @Bean
    public PermissionAspect permissionAspect() {
        return new PermissionAspect(config());
    }

    private Config config() {
        return new Config()
                .setDomain(GlobalConst.DOMAIN)
                .setApplication(GlobalConst.APPLICATION)
                .setCache(true)
                .setTimeout(60)
                .setRedisTemplate(redisTemplate);
    }
}
