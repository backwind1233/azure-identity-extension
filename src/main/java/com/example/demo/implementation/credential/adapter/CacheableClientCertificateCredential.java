package com.example.demo.implementation.credential.adapter;

import com.azure.identity.ClientCertificateCredential;
import com.example.demo.identity.credential.TokenCredentialProviderOptions;

public class CacheableClientCertificateCredential extends CacheableTokenCredentialAdapter<ClientCertificateCredential> {

    public CacheableClientCertificateCredential(TokenCredentialProviderOptions options,
                                                ClientCertificateCredential delegate) {
        super(options, delegate);
    }

    @Override
    protected Descriptor[] getTokenCredentialKeyDescriptors() {
        return new Descriptor[] {
                Descriptor.AUTHORITY_HOST,
                Descriptor.TENANT_ID,
                Descriptor.CLIENT_CERTIFICATE_PATH,
                Descriptor.CLIENT_CERTIFICATE_PASSWORD
        };
    }
}
