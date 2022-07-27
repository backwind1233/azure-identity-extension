package com.example.demo.identity.credential;

import com.azure.core.util.Configuration;
import com.example.demo.identity.AuthProperty;
import com.example.demo.identity.AzureIdentityConfiguration;

import java.util.Objects;

public class TokenCredentialProviderOptions {

    private String tenantId;
    private String clientId;
    private String clientSecret;
    private String clientCertificatePath;
    private String clientCertificatePassword;
    private String username;
    private String password;
    private boolean managedIdentityEnabled;
    private String tokenCredentialProviderClassName;
    private String tokenCredentialBeanName;
    private boolean cacheTokenCredential;

    private AzureIdentityConfiguration identityConfiguration;

    public TokenCredentialProviderOptions() {

    }

    public TokenCredentialProviderOptions(Configuration configuration) {
        this.tenantId = AuthProperty.TENANT_ID.get(configuration);
        this.clientId = AuthProperty.CLIENT_ID.get(configuration);
        this.clientSecret = AuthProperty.CLIENT_SECRET.get(configuration);
        this.clientCertificatePath = AuthProperty.CLIENT_CERTIFICATE_PATH.get(configuration);
        this.clientCertificatePassword = AuthProperty.CLIENT_CERTIFICATE_PASSWORD.get(configuration);
        this.username = AuthProperty.USERNAME.get(configuration);
        this.password = AuthProperty.PASSWORD.get(configuration);
        this.managedIdentityEnabled = Boolean.TRUE.equals(AuthProperty.MANAGED_IDENTITY_ENABLED.getBoolean(configuration));
        this.tokenCredentialProviderClassName = AuthProperty.TOKEN_CREDENTIAL_PROVIDER_CLASS_NAME.get(configuration);
        this.tokenCredentialBeanName = AuthProperty.TOKEN_CREDENTIAL_BEAN_NAME.get(configuration);
        this.cacheTokenCredential = Boolean.TRUE.equals(AuthProperty.CACHE_TOKEN_CREDENTIAL.getBoolean(configuration));
        this.identityConfiguration = new AzureIdentityConfiguration(configuration);
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public String getClientCertificatePath() {
        return clientCertificatePath;
    }

    public void setClientCertificatePath(String clientCertificatePath) {
        this.clientCertificatePath = clientCertificatePath;
    }

    public String getClientCertificatePassword() {
        return clientCertificatePassword;
    }

    public void setClientCertificatePassword(String clientCertificatePassword) {
        this.clientCertificatePassword = clientCertificatePassword;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isManagedIdentityEnabled() {
        return managedIdentityEnabled;
    }

    public void setManagedIdentityEnabled(boolean managedIdentityEnabled) {
        this.managedIdentityEnabled = managedIdentityEnabled;
    }

    public String getTokenCredentialProviderClassName() {
        return tokenCredentialProviderClassName;
    }

    public void setTokenCredentialProviderClassName(String tokenCredentialProviderClassName) {
        this.tokenCredentialProviderClassName = tokenCredentialProviderClassName;
    }

    public String getTokenCredentialBeanName() {
        return tokenCredentialBeanName;
    }

    public void setTokenCredentialBeanName(String tokenCredentialBeanName) {
        this.tokenCredentialBeanName = tokenCredentialBeanName;
    }

    public boolean isCacheTokenCredential() {
        return cacheTokenCredential;
    }

    public void setCacheTokenCredential(boolean cacheTokenCredential) {
        this.cacheTokenCredential = cacheTokenCredential;
    }

    public AzureIdentityConfiguration getIdentityConfiguration() {
        return identityConfiguration;
    }

    public void setIdentityConfiguration(AzureIdentityConfiguration identityConfiguration) {
        this.identityConfiguration = identityConfiguration;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TokenCredentialProviderOptions)) return false;
        TokenCredentialProviderOptions that = (TokenCredentialProviderOptions) o;
        return managedIdentityEnabled == that.managedIdentityEnabled && cacheTokenCredential == that.cacheTokenCredential && Objects.equals(tenantId, that.tenantId) && Objects.equals(clientId, that.clientId) && Objects.equals(clientSecret, that.clientSecret) && Objects.equals(clientCertificatePath, that.clientCertificatePath) && Objects.equals(clientCertificatePassword, that.clientCertificatePassword) && Objects.equals(username, that.username) && Objects.equals(password, that.password) && Objects.equals(tokenCredentialProviderClassName, that.tokenCredentialProviderClassName) && Objects.equals(tokenCredentialBeanName, that.tokenCredentialBeanName) && Objects.equals(identityConfiguration, that.identityConfiguration);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tenantId, clientId, clientSecret, clientCertificatePath, clientCertificatePassword, username, password, managedIdentityEnabled, tokenCredentialProviderClassName, tokenCredentialBeanName, cacheTokenCredential, identityConfiguration);
    }
}
