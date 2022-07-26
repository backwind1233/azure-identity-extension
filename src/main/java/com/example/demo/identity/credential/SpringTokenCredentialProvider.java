package com.example.demo.identity.credential;

import com.azure.core.credential.TokenCredential;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class SpringTokenCredentialProvider implements TokenCredentialProvider, ApplicationContextAware {

    private static ApplicationContext globalApplicationContext;
    private ApplicationContext applicationContext;

    private static String DEFAULT_TOKEN_CREDENTIAL_BEAN_NAME = "tokenCredential";




    @Override
    public TokenCredential get() {
        return null;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

    }
}
