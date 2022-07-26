package com.example.demo.identity.credential;

public class TokenCredentialProviders {
    private static String defaultProviderClass = DefaultTokenCredentialProvider.class.getName();
    public static TokenCredentialProvider createInstance(Configuration configuration) {

        options.getProvderclass();


        /**
         * 1. get provider impl class name from options
         * 2. if not, use the default one
         * 3. create instance of the class
         * 4. config the instance with options
         * 5. return the instance
         */

        return new DefaultTokenCredentialProvider(configuration);
    }


    public static void setDefaultProviderClass(String className){

    }
}
