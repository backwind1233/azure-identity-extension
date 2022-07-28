package com.example.demo.implementation.credential.adapter;

import com.azure.identity.ManagedIdentityCredential;
import com.example.demo.identity.credential.TokenCredentialProviderOptions;

public class CacheableManageIdentityCredential extends CacheableTokenCredentialAdapter<ManagedIdentityCredential> {

    public CacheableManageIdentityCredential(TokenCredentialProviderOptions options,
                                             ManagedIdentityCredential delegate) {
        super(options, delegate);
    }

    @Override
    protected Descriptor[] getTokenCredentialKeyDescriptors() {
        return new Descriptor[] {
                Descriptor.AUTHORITY_HOST,
                Descriptor.CLIENT_ID
        };
    }
}
