package com.example.demo.identity.token;

import com.azure.core.credential.AccessToken;
import com.azure.core.credential.TokenCredential;
import reactor.core.publisher.Mono;

import java.util.function.Function;

public interface AccessTokenResolver extends Function<TokenCredential, Mono<AccessToken>> {

    static AccessTokenResolver createDefault(String tenantId, String... scopes) {
        return new AccessTokenResolverImpl(tenantId, scopes);
    }

    static AccessTokenResolver createDefault() {
        return new AccessTokenResolverImpl();
    }
}
