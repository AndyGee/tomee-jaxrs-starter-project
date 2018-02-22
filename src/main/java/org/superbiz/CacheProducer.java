package org.superbiz;

import javax.cache.Cache;
import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.configuration.MutableConfiguration;
import javax.cache.expiry.AccessedExpiryPolicy;
import javax.cache.expiry.Duration;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.inject.Singleton;

@ApplicationScoped
public class CacheProducer {

    @Produces
    @Singleton
    public CacheManager createCacheManager() {
        System.setProperty("hazelcast.jcache.provider.type", "server");
        return Caching.getCachingProvider().getCacheManager();
    }

    @Produces
    @Singleton
    public Cache<String, Color> createUserCache(final CacheManager cacheManager) {
        final MutableConfiguration<String, Color> config = new MutableConfiguration<>();
        config.setStoreByValue(true)
                .setTypes(String.class, Color.class)
                .setExpiryPolicyFactory(AccessedExpiryPolicy.factoryOf(Duration.TEN_MINUTES))
                .setStatisticsEnabled(false);
        return cacheManager.createCache("tomee", config);
    }

    public void close(@Disposes final CacheManager instance) {
        try {
            instance.close();
        } catch (final Throwable ignore) {
            //no-op
        }

        try {
            Caching.getCachingProvider().close();
        } catch (final Exception ignore) {
            //no-op
        }
    }
}