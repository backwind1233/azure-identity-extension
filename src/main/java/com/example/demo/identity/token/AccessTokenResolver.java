package com.example.demo.identity.token;

import com.azure.core.credential.AccessToken;
import com.azure.core.credential.TokenCredential;
import com.example.demo.implementation.token.CacheableAccessTokenResolver;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@FunctionalInterface
public interface AccessTokenResolver extends Function<TokenCredential, Mono<AccessToken>> {

    static AccessTokenResolver createDefault(AccessTokenResolverOptions options) {
        boolean isCacheAccessToken = options.isCacheAccessToken();
        AccessTokenResolverImpl accessTokenResolver = new AccessTokenResolverImpl(options);
        if (isCacheAccessToken) {
            return new CacheableAccessTokenResolver(options, accessTokenResolver);
        } else {
            return accessTokenResolver;
        }
    }

}
