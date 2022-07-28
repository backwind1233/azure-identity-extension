package com.example.demo.implementation.credential.adapter;

import com.azure.identity.ClientSecretCredential;
import com.example.demo.identity.credential.TokenCredentialProviderOptions;

public class CacheableClientSecretCredential extends CacheableTokenCredentialAdapter<ClientSecretCredential> {

    public CacheableClientSecretCredential(TokenCredentialProviderOptions options,
                                           ClientSecretCredential delegate) {
        super(options, delegate);
    }

    @Override
    protected Descriptor[] getTokenCredentialKeyDescriptors() {
        return new Descriptor[] {
                Descriptor.AUTHORITY_HOST,
                Descriptor.TENANT_ID,
                Descriptor.CLIENT_ID,
                Descriptor.CLIENT_SECRET
        };
    }

}
