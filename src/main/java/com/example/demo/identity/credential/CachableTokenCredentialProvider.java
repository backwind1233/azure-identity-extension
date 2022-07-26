package com.example.demo.identity.credential;

import com.azure.core.credential.TokenCredential;
import org.apache.el.parser.Token;

import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;

public class CachableTokenCredentialProvider implements TokenCredentialProvider {

    private static TokenCredentialProvider delegate;


    public static void setDelegate(TokenCredentialProvider delegate) {
        CachableTokenCredentialProvider.delegate = delegate;
    }


    @Override
    public TokenCredential get() {

        if (delegate != null) {
            return delegate.get();
        }
        return null;
    }
}
