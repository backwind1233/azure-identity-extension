package com.example.demo.identity.credential;

import com.azure.core.credential.TokenCredential;
import com.azure.identity.DefaultAzureCredentialBuilder;

class DefaultTokenCredentialProvider implements TokenCredentialProvider {
    @Override
    public TokenCredential get() {


        TokenCredential tokenCredential = new DefaultAzureCredentialBuilder()
            .build();

        return tokenCredential;
    }
}
