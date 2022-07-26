package com.example.demo.identity.token;

import com.azure.core.credential.AccessToken;
import com.azure.core.credential.TokenCredential;
import com.azure.core.credential.TokenRequestContext;
import reactor.core.publisher.Mono;

import java.util.Objects;

 class AccessTokenResolverImpl implements AccessTokenResolver {

    private static final String DEFAULT_DB_SCOPE = "https://ossrdbms-aad.database.windows.net";
    private final String[] scopes;
    private final String tenantId;

     AccessTokenResolverImpl(String tenantId, String... scopes) {
        this.tenantId = tenantId;
        this.scopes = scopes;
    }

     AccessTokenResolverImpl() {
        this(null, DEFAULT_DB_SCOPE);
    }

    @Override
    public Mono<AccessToken> apply(TokenCredential tokenCredential) {

        Objects.requireNonNull(tokenCredential);
        TokenRequestContext request = new TokenRequestContext();
        request.setTenantId(tenantId);
        request.addScopes(scopes);
        return tokenCredential.getToken(request);
    }
}
