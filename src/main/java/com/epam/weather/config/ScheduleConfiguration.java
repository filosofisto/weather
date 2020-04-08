package com.epam.weather.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
@ConditionalOnProperty(
        name = "schedule.enabled",
        havingValue = "true",
        matchIfMissing = true
)
public class ScheduleConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(CacheConfig.class);

    @CacheEvict(allEntries = true, cacheNames = { "weather" })
    @Scheduled(fixedDelay = 1000*60*5)
//    @Scheduled(fixedDelay = 10*1000)
    public void cacheEvict() {
        logger.info("Cache evicting!");
    }
}
