package com.example.demo.identity;

import com.azure.core.util.Configuration;
import com.azure.identity.AzureAuthorityHosts;

import java.util.Objects;

public class AzureIdentityConfiguration {

    private String authorityHost = AzureAuthorityHosts.AZURE_PUBLIC_CLOUD;

    public AzureIdentityConfiguration() {

    }

    public AzureIdentityConfiguration(Configuration configuration) {
        this.authorityHost = AuthProperty.AUTHORITY_HOST.get(configuration);
    }

    public String getAuthorityHost() {
        return authorityHost;
    }

    public void setAuthorityHost(String authorityHost) {
        this.authorityHost = authorityHost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AzureIdentityConfiguration)) return false;
        AzureIdentityConfiguration that = (AzureIdentityConfiguration) o;
        return Objects.equals(authorityHost, that.authorityHost);
    }

    @Override
    public int hashCode() {
        return Objects.hash(authorityHost);
    }
}
