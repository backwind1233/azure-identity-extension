package com.example.demo.implementation;

import com.azure.core.credential.AccessToken;
import com.example.demo.identity.Cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class StaticAccessTokenCache implements Cache<String, AccessToken> {
    private static Map<String, AccessToken> cache = new ConcurrentHashMap<>();

    @Override
    public void put(String key, AccessToken value) {
        cache.put(key, value);
    }

    @Override
    public AccessToken get(String key) {
        return cache.get(key);
    }
}
