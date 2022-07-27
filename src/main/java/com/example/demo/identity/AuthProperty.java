package com.example.demo.identity;

import com.azure.core.util.Configuration;
import com.azure.identity.AzureAuthorityHosts;

import java.util.Properties;

public enum AuthProperty {

    CLIENT_ID("azure.clientId"),
    CLIENT_SECRET("azure.clientSecret"),
    CLIENT_CERTIFICATE_PATH("azure.clientCertificatePath"),
    CLIENT_CERTIFICATE_PASSWORD("azure.clientCertificatePassword"),
    USERNAME("azure.username"),
    PASSWORD("azure.password"),
    MANAGED_IDENTITY_ENABLED("azure.managedIdentityEnabled"),
    AUTHORITY_HOST("azure.authorityHost", AzureAuthorityHosts.AZURE_PUBLIC_CLOUD),

    TENANT_ID("azure.tenantId"),
    CLAIMS("azure.claims"),
    SCOPES("azure.scopes"),

    // TODO define this here?,
    GET_TOKEN_TIMEOUT("azure.accessTokenTimeoutInSeconds"),
    TOKEN_CREDENTIAL_PROVIDER_CLASS_NAME("azure.tokenCredentialProviderClassName"),
    TOKEN_CREDENTIAL_BEAN_NAME("azure.tokenCredentialBeanName"),
    CACHE_TOKEN_CREDENTIAL("azure.cacheTokenCredential"),
    CACHE_ACCESS_TOKEN("azure.cacheAccessToken")
    ;

    String name;
    String defaultValue;

    AuthProperty(String name) {
        this.name = name;
    }

    AuthProperty(String name, String defaultValue) {
        this.name = name;
        this.defaultValue = defaultValue;
    }

    public String get(Configuration configuration) {
        return configuration.get(this.name, defaultValue);
    }

    public Boolean getBoolean(Configuration configuration) {
        return Boolean.parseBoolean(get(configuration));
    }

    public Integer getInteger(Configuration configuration) {
        return Integer.parseInt(get(configuration));
    }

    public void setProperty(Configuration configuration, String value) {
        if (value == null) {
            configuration.remove(this.name);
        } else {
            configuration.put(this.name, value);
        }
    }

    public void setProperty(Properties properties, String value) {
        if (value == null) {
            properties.remove(this.name);
        } else {
            properties.put(this.name, value);
        }
    }

}
