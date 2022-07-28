package com.example.demo.implementation.credential.adapter;

public interface CacheKeyDescriptor<KEY, KEYContext> {

    KEY getCacheKey(KEYContext keyContext);

}
