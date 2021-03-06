package org.springdemo.config;


import net.sf.ehcache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;

/**
 * @author zacconding
 * @Date 2018-01-25
 * @GitHub : https://github.com/zacscoding
 */
@Configuration
@EnableCaching
@Profile("ehcache_java_config")
public class CacheConfig {
    /* ================
	 * 1) Simple Cache Manager
	 * ==> uses a java.util.concurrent.ConcurrentHashMap as its cache store
	 * ==>
	 ================== */

//    // Declare a cache manager
//	@Bean
//	public CacheManager cacheManager() {
//		//  java.util.concurrent.ConcurrentHashMap 를 캐시 저장소로 사용
//		return new ConcurrentMapCacheManager();
//	}


	/* ================
	 * 2) EhCacheCacheManager
	 * docs :
	 * http://www.ehcache.org/documentation/3.3/
	 ================== */

    // Configure EhCacheCacheManager
    @Bean
    public EhCacheCacheManager cacheManager(CacheManager cm) {
        return new EhCacheCacheManager(cm);
    }

    // EhCacheManagerFactoryBean
    @Bean
    public EhCacheManagerFactoryBean ehcache() {
        EhCacheManagerFactoryBean ehCacheFactoryBean = new EhCacheManagerFactoryBean();

        ehCacheFactoryBean.setConfigLocation(new ClassPathResource("cache/ehcache.xml"));

        return ehCacheFactoryBean;
    }
}
