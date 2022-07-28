package com.example.demo.identity.credential;

import com.azure.core.credential.TokenCredential;
import com.example.demo.implementation.credential.provider.TokenCredentialProviders;

import java.util.function.Supplier;

@FunctionalInterface
public interface TokenCredentialProvider extends Supplier<TokenCredential> {

    default TokenCredential get(TokenCredentialProviderOptions options) {
        return get();
    }

    static TokenCredentialProvider createDefault(TokenCredentialProviderOptions options) {
        return TokenCredentialProviders.createInstance(options);
    }
}
