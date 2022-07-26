package com.example.demo.identity.fakedb.jdbc;



import org.postgresql.util.GT;
import org.postgresql.util.ObjectFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.DriverPropertyInfo;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.mockito.Mockito.mock;

public class FakeDBJDBCDriver implements DriverConstant, java.sql.Driver {

    private static final java.util.logging.Logger PARENT_LOGGER = java.util.logging.Logger.getLogger("com.example"
        + ".demo");
    private static final java.util.logging.Logger LOGGER = Logger.getLogger(FakeDBJDBCDriver.class.getName());

    static {
        try {
            // moved the registerDriver from the constructor to here
            // because some clients call the driver themselves (I know, as
            // my early jdbc work did - and that was based on other examples).
            // Placing it here, means that the driver is registered once only.
            register();
        } catch (SQLException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    public static void register() throws SQLException {
        if (isRegistered()) {
            throw new IllegalStateException(
                "Driver is already registered. It can only be registered once.");
        }
        FakeDBJDBCDriver registeredDriver = new FakeDBJDBCDriver();
        DriverManager.registerDriver(registeredDriver);
        FakeDBJDBCDriver.registeredDriver = registeredDriver;
    }
    public static boolean isRegistered() {
        return registeredDriver != null;
    }
    private static  FakeDBJDBCDriver registeredDriver;

    @Override
    public Connection connect(String url, Properties properties) throws SQLException {
        LOGGER.info(String.format("Connecting to the awesome FAKE DB with properties %s", properties));


        String jdbcURL = url == null ? properties.getProperty("jdbcUrl") : url;
        String username = properties.getProperty("user");
        String password = properties.getProperty("password");

        if (password == null || password.isEmpty()) {
            password = getPasswordFromAuthPlugin(properties);
        }
        if (jdbcURL == null || jdbcURL.isEmpty()) {
            throw new IllegalArgumentException("jdbcUrl is required");
        }
        if (username == null || username.isEmpty()) {
            throw new IllegalArgumentException("user is required");
        }
        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("password is required");
        }
        LOGGER.info(String.format("Connecting to the awesome FAKE DB with jdbcUrl %s, user %s, password %s", jdbcURL,
            username, password));
        return mock(Connection.class);
    }

    @Override
    public boolean acceptsURL(String url) throws SQLException {
        return url != null && url.startsWith("jdbc:fakedb://");
    }

    @Override
    public DriverPropertyInfo[] getPropertyInfo(String url, Properties info) throws SQLException {
        return new DriverPropertyInfo[0];
    }

    @Override
    public int getMajorVersion() {
        return 10;
    }

    @Override
    public int getMinorVersion() {
        return 110;
    }

    @Override
    public boolean jdbcCompliant() {
        return false;
    }

    @Override
    public java.util.logging.Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return PARENT_LOGGER;
    }

    private String getPasswordFromAuthPlugin(Properties properties) {
        String pluginClassName = properties.getProperty(AUTH_PLUGIN_NAME);
        if (pluginClassName != null && !pluginClassName.isEmpty()) {
            FakeDBJDBCAuthPlugin authPlugin;
            try {
                //Create new instance of auth plugin per connection
                authPlugin = ObjectFactory.instantiate(FakeDBJDBCAuthPlugin.class, pluginClassName, properties,
                    false, null);
            } catch (Exception ex) {
                String msg = GT.tr("Unable to load Authentication Plugin {0}", pluginClassName);
                LOGGER.log(Level.SEVERE, msg, ex);
                return null;
            }

            return authPlugin.getPassword();
        }
        return null;
    }
}
