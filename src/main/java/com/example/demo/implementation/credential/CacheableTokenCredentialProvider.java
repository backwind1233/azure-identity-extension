package com.example.demo.implementation.credential;

import com.azure.core.credential.TokenCredential;
import com.example.demo.identity.credential.TokenCredentialProvider;
import com.example.demo.identity.credential.TokenCredentialProviderOptions;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

class CacheableTokenCredentialProvider implements TokenCredentialProvider {
    private static Map<TokenCredentialProviderOptions, TokenCredential> cache = new ConcurrentHashMap<>();
    private final TokenCredentialProviderOptions options;
    private final TokenCredentialProvider delegate;

    public CacheableTokenCredentialProvider(TokenCredentialProviderOptions options, TokenCredentialProvider delegate) {
        this.options = options;
        this.delegate = delegate;
    }

    @Override
    public TokenCredential get() {
        return cache.computeIfAbsent(this.options, this.delegate::get);
    }
}
