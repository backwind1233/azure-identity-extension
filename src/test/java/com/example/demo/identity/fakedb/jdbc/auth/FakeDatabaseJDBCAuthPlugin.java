package com.example.demo.identity.fakedb.jdbc.auth;

import com.azure.core.util.Configuration;
import com.example.demo.identity.AzureAuthenticationTemplate;
import com.example.demo.identity.fakedb.jdbc.FakeDBJDBCAuthPlugin;

import java.util.Properties;

public class FakeDatabaseJDBCAuthPlugin extends AzureAuthenticationTemplate implements FakeDBJDBCAuthPlugin {


    public static final String PROP_AUTHORITY_HOST = "azure.authority.host"; //cache.token=true


    private final Properties properties;

    public FakeDatabaseJDBCAuthPlugin(Properties properties) {
//        super(tokenCredentialProvider, accessTokenResolver);
        this.properties = properties;
        super.init(Configuration.getGlobalConfiguration().clone());
    }

    @Override
    public String getPassword() {
        return getTokenAsPassword();
    }
}

