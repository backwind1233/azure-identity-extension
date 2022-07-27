package com.example.demo.identity;

import com.azure.core.credential.AccessToken;
import com.azure.core.util.Configuration;
import com.azure.core.util.logging.ClientLogger;
import com.example.demo.identity.credential.TokenCredentialProvider;
import com.example.demo.identity.credential.TokenCredentialProviderOptions;
import com.example.demo.identity.token.AccessTokenResolver;
import com.example.demo.identity.token.AccessTokenResolverOptions;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicBoolean;


/**
 * 1. get a token credential
 * 2. get an access token from token credential
 * 3. get the password from access token
 */
public class AzureAuthenticationTemplate {

    private static final ClientLogger LOGGER = new ClientLogger(AzureAuthenticationTemplate.class);
    public static final String PROP_GET_ACCESS_TOKEN_TIMEOUT_IN_SECONDS = "azure.getAccessTokenTimeoutInSeconds";

    private final AtomicBoolean isInitialized = new AtomicBoolean(false);

    private TokenCredentialProvider tokenCredentialProvider;

    private AccessTokenResolver accessTokenResolver;

    public AzureAuthenticationTemplate() {
        this.tokenCredentialProvider = null;
        this.accessTokenResolver = null;
    }

    public AzureAuthenticationTemplate(TokenCredentialProvider tokenCredentialProvider,
                                       AccessTokenResolver accessTokenResolver) {
        LOGGER.info("Initializing AzureAuthenticationTemplate in constructor");
        this.tokenCredentialProvider = tokenCredentialProvider;
        this.accessTokenResolver = accessTokenResolver;
        this.isInitialized.set(true);
    }

    public AzureAuthenticationTemplate(Configuration configuration) {
        this(TokenCredentialProvider.createDefault(new TokenCredentialProviderOptions(configuration)),
                AccessTokenResolver.createDefault(new AccessTokenResolverOptions(configuration)));
    }

    protected AccessTokenResolver getAccessTokenResolver() {
        return accessTokenResolver;
    }

    protected TokenCredentialProvider getTokenCredentialProvider() {
        return tokenCredentialProvider;
    }

    protected void init(Configuration configuration) {
        if (isInitialized.compareAndSet(false, true)) {
            LOGGER.info("Initializing AzureAuthenticationTemplate");
            this.tokenCredentialProvider = TokenCredentialProvider.createDefault(new TokenCredentialProviderOptions(configuration));

            if (getAccessTokenResolver() == null) {
                this.accessTokenResolver = AccessTokenResolver.createDefault(new AccessTokenResolverOptions(configuration));
            }
        } else {
            LOGGER.info("Already initialized.");
        }
    }

    protected Mono<String> getTokenAsPasswordAsync(){
        if (!isInitialized.get()) {
            throw new IllegalStateException("must call init() first");
        }
        return Mono.fromSupplier(getTokenCredentialProvider())
                   .flatMap(getAccessTokenResolver())
                   .filter(token -> !token.isExpired())
                   .map(AccessToken::getToken);
    }

    protected String getTokenAsPassword() {
        return getTokenAsPasswordAsync().block(getBlockTimeout());
    }

    protected Duration getBlockTimeout(){
        return Duration.ofSeconds(30);
    }

}
