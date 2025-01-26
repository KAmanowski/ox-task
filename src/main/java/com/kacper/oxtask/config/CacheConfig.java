package com.kacper.oxtask.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
public class CacheConfig {

  public static final String MANUFACTURER_EXISTS = "manufacturerExists";
  public static final String RETAILER_EXISTS = "retailerExists";

  @Bean
  @Primary
  public CacheManager cacheManager() {
    CaffeineCacheManager cacheManager = new CaffeineCacheManager();

    createCache(cacheManager, MANUFACTURER_EXISTS, 20, 120);
    createCache(cacheManager, RETAILER_EXISTS, 20, 120);

    return cacheManager;
  }

  private void createCache(CaffeineCacheManager cacheManager, String name, int maximumSize, long expireAfterWriteMinutes) {
    cacheManager.registerCustomCache(name,
        Caffeine.newBuilder()
            .maximumSize(maximumSize)
            .expireAfterWrite(expireAfterWriteMinutes, TimeUnit.MINUTES)
            .build());
  }
}
