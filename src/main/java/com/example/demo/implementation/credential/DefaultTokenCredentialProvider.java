package com.example.demo.implementation.credential;

import com.azure.core.credential.TokenCredential;
import com.azure.identity.ClientCertificateCredentialBuilder;
import com.azure.identity.ClientSecretCredentialBuilder;
import com.azure.identity.DefaultAzureCredentialBuilder;
import com.azure.identity.ManagedIdentityCredentialBuilder;
import com.azure.identity.UsernamePasswordCredentialBuilder;
import com.example.demo.identity.credential.TokenCredentialProvider;
import com.example.demo.identity.credential.TokenCredentialProviderOptions;
import org.springframework.util.StringUtils;

class DefaultTokenCredentialProvider implements TokenCredentialProvider {

    private TokenCredentialProviderOptions options;

    DefaultTokenCredentialProvider() {
        this.options = new TokenCredentialProviderOptions();
    }

    DefaultTokenCredentialProvider(TokenCredentialProviderOptions options) {
        this.options = options;
    }

    @Override
    public TokenCredential get(TokenCredentialProviderOptions options) {
        if (options == null) {
            return new DefaultAzureCredentialBuilder().build();
        }

        final String tenantId = options.getTenantId();
        final String clientId = options.getClientId();
        final boolean isClientIdSet = StringUtils.hasText(clientId);
        String authorityHost = options.getIdentityConfiguration().getAuthorityHost();
        if (StringUtils.hasText(tenantId)) {
            String clientSecret = options.getClientSecret();
            if (isClientIdSet && StringUtils.hasText(clientSecret)) {
                return new ClientSecretCredentialBuilder().clientId(clientId)
                        .authorityHost(authorityHost)
                        .clientSecret(clientSecret)
                        .tenantId(tenantId)
                        .build();
            }

            String clientCertificatePath = options.getClientCertificatePath();
            if (StringUtils.hasText(clientCertificatePath)) {
                ClientCertificateCredentialBuilder builder = new ClientCertificateCredentialBuilder()
                        .authorityHost(authorityHost)
                        .tenantId(tenantId)
                        .clientId(clientId);

                if (StringUtils.hasText(options.getClientCertificatePassword())) {
                    builder.pfxCertificate(clientCertificatePath, options.getClientCertificatePassword());
                } else {
                    builder.pemCertificate(clientCertificatePath);
                }

                return builder.build();
            }
        }

        if (isClientIdSet && StringUtils.hasText(options.getUsername())
                && StringUtils.hasText(options.getPassword())) {
            return new UsernamePasswordCredentialBuilder().username(options.getUsername())
                    .authorityHost(authorityHost)
                    .password(options.getPassword())
                    .clientId(clientId)
                    .tenantId(tenantId)
                    .build();
        }

        if (options.isManagedIdentityEnabled()) {
            ManagedIdentityCredentialBuilder builder = new ManagedIdentityCredentialBuilder();
            if (isClientIdSet) {
                builder.clientId(clientId);
            }
            return builder.build();
        }

        return new DefaultAzureCredentialBuilder()
                .authorityHost(authorityHost)
                .tenantId(tenantId)
                .managedIdentityClientId(clientId)
                .build();
    }

    @Override
    public TokenCredential get() {
        return get(this.options);
    }
}
