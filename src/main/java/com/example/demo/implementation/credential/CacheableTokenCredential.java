package com.example.demo.implementation.credential;

import com.azure.core.credential.AccessToken;
import com.azure.core.credential.TokenCredential;
import com.azure.core.credential.TokenRequestContext;
import com.example.demo.identity.Cache;
import com.example.demo.identity.credential.TokenCredentialProviderOptions;
import com.example.demo.implementation.credential.adapter.CacheKeyDescriptor;
import reactor.core.publisher.Mono;

public class CacheableTokenCredential implements TokenCredential {

    private final TokenCredentialProviderOptions options;
    private final TokenCredential delegate;
    private Cache<String, AccessToken> cache;

    public CacheableTokenCredential(Cache<String, AccessToken> cache,
                                    TokenCredentialProviderOptions options,
                                    TokenCredential tokenCredential) {
        this.options = options;
        this.delegate = tokenCredential;
        this.cache = cache;
    }

    @Override
    public Mono<AccessToken> getToken(TokenRequestContext request) {
        if (delegate instanceof CacheKeyDescriptor) {
            String cacheKey = ((CacheKeyDescriptor<String, TokenRequestContext>) delegate).getCacheKey(request);

            return Mono.defer(() -> {
                AccessToken accessToken = cache.get(cacheKey);
                if (accessToken != null && !accessToken.isExpired()) {
                    return Mono.just(accessToken);
                } else {
                    return Mono.empty();
                }
            }).switchIfEmpty(Mono.defer(() -> this.delegate.getToken(request))
                    .doOnNext(token -> cache.put(cacheKey, token)));
        } else {
            return this.delegate.getToken(request);
        }
    }
}
