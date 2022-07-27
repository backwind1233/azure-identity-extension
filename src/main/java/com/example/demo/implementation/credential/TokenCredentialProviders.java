package com.example.demo.implementation.credential;

import com.azure.core.exception.AzureException;
import com.example.demo.identity.credential.TokenCredentialProvider;
import com.example.demo.identity.credential.TokenCredentialProviderOptions;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class TokenCredentialProviders {

    private static Class<? extends TokenCredentialProvider> defaultProviderClass = DefaultTokenCredentialProvider.class;

    private TokenCredentialProviders() {

    }

    public static TokenCredentialProvider createInstance() {
        return createInstance(null);
    }

    /**
     * 1. get provider impl class name from options
     * 2. if not, use the default one
     * 3. create instance of the class
     * 4. config the instance with options
     * 5. return the instance
     */
    public static TokenCredentialProvider createInstance(TokenCredentialProviderOptions options) {
        if (options == null) {
            options = new TokenCredentialProviderOptions();
        }

        Class<? extends TokenCredentialProvider> clazz = getProviderClass(options.getTokenCredentialProviderClassName());
        if (clazz == null) {
            clazz = defaultProviderClass;
        }

        TokenCredentialProvider instance = instantiateClass(clazz, options);

        boolean isCacheTokenCredential = Boolean.TRUE.equals(options.isCacheTokenCredential());
        if (isCacheTokenCredential) {
            instance = new CacheableTokenCredentialProvider(options, instance);
        }

        return instance;
    }

    public static void setDefaultProviderClass(Class<? extends TokenCredentialProvider> clazz) {
        defaultProviderClass = clazz;
    }

    private static <T> T instantiateClass(Class<T> clazz, Object... args) {
        try {
            // TODO not use this paramater class
            Constructor<?> ctor = clazz.getDeclaredConstructor(TokenCredentialProviderOptions.class);
            // TODO pass the args or call default ctor
            return (T) ctor.newInstance(args);
        } catch (InstantiationException e) {
            // TODO
            throw new AzureException(e);
        } catch (IllegalAccessException e) {
            throw new AzureException(e);
        } catch (InvocationTargetException e) {
            throw new AzureException(e);
        } catch (NoSuchMethodException e) {
            throw new AzureException(e);
        }
    }

    private static Class<? extends TokenCredentialProvider> getProviderClass(String providerClassName) {
        if (providerClassName != null && !providerClassName.isBlank()) {
            try {
                Class<?> providerClazz = Class.forName(providerClassName);
                if (!TokenCredentialProvider.class.isAssignableFrom(providerClazz)) {
                    throw new AzureException("Provided class [" + providerClassName + "] is not a TokenCredentialProvider");
                }
                return (Class<? extends TokenCredentialProvider>) providerClazz;
            } catch (ClassNotFoundException e) {
                throw new AzureException("The provided class [" + providerClassName + "] can't be found", e);
            }
        }
        return null;
    }
}
