package com.example.demo.identity.credential;

import com.azure.core.credential.TokenCredential;

import java.util.function.Supplier;

@FunctionalInterface
public interface TokenCredentialProvider extends Supplier<TokenCredential> {

    static TokenCredentialProvider createDefault() {
        return createDefault(null);
    }

    default void config(TokenCredentialProviderOptions options) {
    }

    default TokenCredential get(TokenCredentialProviderOptions options) {
        return get();
    }

    static TokenCredentialProvider createDefault(TokenCredentialProviderOptions options) {
        return TokenCredentialProviders.createInstance(options);
    }
}
