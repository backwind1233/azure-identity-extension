package com.example.demo.implementation.token;

import com.azure.core.credential.AccessToken;
import com.azure.core.credential.TokenCredential;
import com.example.demo.identity.token.AccessTokenResolver;
import com.example.demo.identity.token.AccessTokenResolverOptions;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CacheableAccessTokenResolver implements AccessTokenResolver {

    private static Map<Tuple2<TokenCredential, AccessTokenResolverOptions>, AccessToken> cache = new ConcurrentHashMap<>();
    private final AccessTokenResolverOptions options;
    private final AccessTokenResolver delegate;

    public CacheableAccessTokenResolver(AccessTokenResolverOptions options, AccessTokenResolver delegate) {
        this.options = options;
        this.delegate = delegate;
    }

    @Override
    public Mono<AccessToken> apply(TokenCredential tokenCredential) {
        Tuple2<TokenCredential, AccessTokenResolverOptions> key = Tuples.of(tokenCredential, this.options);

        return Mono.defer(() -> {
                    AccessToken accessToken = cache.get(key);
                    if (accessToken != null && !accessToken.isExpired()) {
                        return Mono.just(accessToken);
                    } else {
                        return Mono.empty();
                    }
                }).switchIfEmpty(Mono.defer(() -> this.delegate.apply(tokenCredential))
                .doOnNext(token -> cache.put(key, token)));
    }
}
