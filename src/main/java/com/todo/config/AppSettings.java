package com.todo.config;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.google.common.cache.CacheBuilder;

@Component
public class AppSettings{
	
	@Value("${authentication.oauth.accessTokenValiditityInSeconds}")
	private Integer PROP_TOKEN_VALIDITY_SECONDS;
	
	public  <K, V> Map<K, V> makeLoginAttempMap() {
		return CacheBuilder
				.newBuilder()
				.expireAfterWrite(10, TimeUnit.SECONDS)
				.<K, V>build() // not using a cache loader
				.asMap();
	}
	public  <K, V> Map<K, V> makeSessionExpireMap() {
		return CacheBuilder
				.newBuilder()
				.expireAfterWrite(PROP_TOKEN_VALIDITY_SECONDS-120, TimeUnit.SECONDS)
				.<K, V>build() // not using a cache loader
				.asMap();
	}

}
