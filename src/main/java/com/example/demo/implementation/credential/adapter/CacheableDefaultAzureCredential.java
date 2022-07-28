package com.example.demo.implementation.credential.adapter;

import com.azure.identity.DefaultAzureCredential;
import com.example.demo.identity.credential.TokenCredentialProviderOptions;

public class CacheableDefaultAzureCredential extends CacheableTokenCredentialAdapter<DefaultAzureCredential> {

    public CacheableDefaultAzureCredential(TokenCredentialProviderOptions options,
                                           DefaultAzureCredential delegate) {
        super(options, delegate);
    }

    @Override
    protected Descriptor[] getTokenCredentialKeyDescriptors() {
        return new Descriptor[] {
                Descriptor.AUTHORITY_HOST,
                Descriptor.TENANT_ID,
                Descriptor.CLIENT_ID
        };
    }

}
