package com.example.demo.identity;

import com.azure.core.credential.AccessToken;
import com.azure.core.util.Configuration;
import com.example.demo.identity.credential.TokenCredentialProvider;
import com.example.demo.identity.token.AccessTokenResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicBoolean;


/**
 * 1. get a token credential
 * 2. get an access token from token credential
 * 3. get the password from access token
 */
public class AzureAuthenticationTemplate {

    private final Logger LOG = LoggerFactory.getLogger(AzureAuthenticationTemplate.class);
    public static final String PROP_GET_ACCESS_TOKEN_TIMEOUT_IN_SECONDS = "azure.getAccessTokenTimeoutInSeconds";

    private final AtomicBoolean isInitialized = new AtomicBoolean(false);

    private final TokenCredentialProvider tokenCredentialProvider;

    private final AccessTokenResolver accessTokenResolver;

    public AzureAuthenticationTemplate(TokenCredentialProvider tokenCredentialProvider,
                                       AccessTokenResolver accessTokenResolver) {
        this.tokenCredentialProvider = tokenCredentialProvider;
        this.accessTokenResolver = accessTokenResolver;
    }


    public AzureAuthenticationTemplate() {
        this(TokenCredentialProvider.createDefault(), AccessTokenResolver.createDefault());
    }

    protected AccessTokenResolver getAccessTokenResolver() {
        return accessTokenResolver;
    }

    protected TokenCredentialProvider getTokenCredentialProvider() {
        return tokenCredentialProvider;
    }




    protected void init(Configuration configuration) {

        if (isInitialized.compareAndSet(false, true)) {
            LOG.info("Initializing AzureAuthenticationTemplate");
        }

        if(getAccessTokenResolver()==null){
            accessTokenResolver = AccessTokenResolver.createDefault(configuration);
        }
    }

    protected Mono<String> getTokenAsPasswordAsync(){
        if (!isInitialized.get()) {
            throw new IllegalStateException("must call init() first");
        }
        return Mono.fromSupplier(getTokenCredentialProvider())
                   .flatMap(getAccessTokenResolver())
                   .filter(token -> !token.isExpired())
                   .map(AccessToken::getToken)
            ;
    }
    protected String getTokenAsPassword() {
        return getTokenAsPasswordAsync()
                   .block(getBlockTimeout())
            ;
    }

    protected Duration getBlockTimeout(){
        return Duration.ofSeconds(30);
    }

}
