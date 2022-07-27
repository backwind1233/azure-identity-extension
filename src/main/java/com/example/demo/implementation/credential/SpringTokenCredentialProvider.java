package com.example.demo.implementation.credential;

import com.azure.core.credential.TokenCredential;
import com.example.demo.identity.credential.TokenCredentialProvider;
import com.example.demo.identity.credential.TokenCredentialProviderOptions;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.Objects;

public class SpringTokenCredentialProvider implements TokenCredentialProvider, ApplicationContextAware {

    private static String DEFAULT_TOKEN_CREDENTIAL_BEAN_NAME = "springCloudAzureDefaultCredential";
    private static ApplicationContext globalApplicationContext;
    private ApplicationContext applicationContext;

    private String tokenCredentialBeanName = DEFAULT_TOKEN_CREDENTIAL_BEAN_NAME;

    public SpringTokenCredentialProvider() {

    }

    public SpringTokenCredentialProvider(TokenCredentialProviderOptions options) {
        String beanName = options.getTokenCredentialBeanName();
        if (beanName != null && !beanName.isBlank()) {
            this.tokenCredentialBeanName = beanName;
        }
    }

    public TokenCredential get() {
        ApplicationContext context = getApplicationContext();
        Objects.requireNonNull(context);
        return context.getBean(this.tokenCredentialBeanName, TokenCredential.class);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public static void setGlobalApplicationContext(ApplicationContext applicationContext) {
        globalApplicationContext = applicationContext;
    }

    private ApplicationContext getApplicationContext() {
        return this.applicationContext == null ? globalApplicationContext : this.applicationContext;
    }
}
