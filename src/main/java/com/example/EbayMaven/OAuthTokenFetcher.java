package com.example.EbayMaven;

import com.ebay.auth.oauthclient.CredentialUtil;
import com.ebay.auth.oauthclient.model.Environment;
import com.ebay.auth.oauthclient.model.OAuthResponse;

public class OAuthTokenFetcher {

    public static void main(String[] args) {
        try {
            String clientId = "YOUR_CLIENT_ID";
            String clientSecret = "YOUR_CLIENT_SECRET";
            String ruName = "YOUR_RUNAME";

            // Set the environment to Sandbox
            Environment environment = Environment.SANDBOX;

            // Fetch OAuth token
            OAuthResponse response = CredentialUtil.fetchToken(clientId, clientSecret, ruName, environment);
            String oAuthToken = response.getAccessToken();

            // Print the OAuth token
            System.out.println("OAuth Token: " + oAuthToken);
        } catch (Exception e) {
            System.out.println("Failed to fetch OAuth token.");
            e.printStackTrace();
        }
    }
}
