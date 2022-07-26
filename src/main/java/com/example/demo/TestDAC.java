package com.example.demo;

import com.azure.core.credential.AccessToken;
import com.azure.core.credential.TokenRequestContext;
import com.azure.identity.DefaultAzureCredential;
import com.azure.identity.DefaultAzureCredentialBuilder;
import reactor.core.publisher.Mono;

public class TestDAC {
    public static void main(String[] args) {
        DefaultAzureCredential credential = new DefaultAzureCredentialBuilder().build();
        TokenRequestContext request = new TokenRequestContext().addScopes("https://management.azure.com");

      Mono<AccessToken> mono =  credential.getToken(request);

     String token1 = mono.block().getToken();
        System.out.println(token1);
        System.out.println("----------------------------------------");


        Mono<AccessToken> mono2 =  credential.getToken(request);

       String token2= mono.block().getToken();
        System.out.println(token2);
        System.out.println("----------------------------------------");
    }
}
