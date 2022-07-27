package com.example.demo.identity.fakedb.jdbc.auth;

import com.azure.core.util.Configuration;
import com.example.demo.identity.AuthProperty;
import com.example.demo.identity.AzureAuthenticationTemplate;
import com.example.demo.identity.fakedb.jdbc.FakeDBJDBCAuthPlugin;

import java.util.Properties;

public class FakeDatabaseJDBCAuthPlugin extends AzureAuthenticationTemplate implements FakeDBJDBCAuthPlugin {


    public static final String PROP_AUTHORITY_HOST = "azure.authority.host"; //cache.token=true


    private final Properties properties;

    public FakeDatabaseJDBCAuthPlugin(Properties properties) {
//        super(tokenCredentialProvider, accessTokenResolver);
        this.properties = properties;
        Configuration configuration = Configuration.getGlobalConfiguration().clone();
        properties.entrySet().forEach(entry -> configuration.put(entry.getKey().toString(), entry.getValue().toString()));
        AuthProperty.SCOPES.setProperty(configuration, "https://ossrdbms-aad.database.windows.net");
        super.init(configuration);
    }

    @Override
    public String getPassword() {
        return getTokenAsPassword();
    }
}

