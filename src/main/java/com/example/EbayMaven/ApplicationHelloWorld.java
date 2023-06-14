package com.example.EbayMaven;

import java.io.IOException;
import java.util.Calendar;

import com.ebay.sdk.ApiContext;
import com.ebay.sdk.ApiCredential;
import com.ebay.sdk.helper.ConsoleUtil;
import com.ebay.sdk.call.GeteBayOfficialTimeCall;

import java.net.URL;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

public class ApplicationHelloWorld {

    public static void main(String[] args) {
        try {
            System.out.print("\n");
            System.out.print("+++++++++++++++++++++++++++++++++++++++\n");
            System.out.print("+ Welcome to eBay SDK for Java Sample +\n");
            System.out.print("+  - ConsoleAddItem                   +\n");
            System.out.print("+++++++++++++++++++++++++++++++++++++++\n");
            System.out.print("\n");

            // [Step 1] Initialize eBay ApiContext object
            System.out.println("===== [1] Account Information ====");
            ApiContext apiContext = getApiContext();

            // [Step 2] Create call object and execute the call
            GeteBayOfficialTimeCall apiCall = new GeteBayOfficialTimeCall(apiContext);

            // Apply SSL protocol settings
            applySslSettings();

            System.out.println("Begin to call eBay API, please wait ... ");
            Calendar cal = apiCall.geteBayOfficialTime();
            System.out.println("End to call eBay API, show call result ...");

            // [Step 3] Handle the result returned
            System.out.println("Official eBay Time: " + cal.getTime().toString());
        } catch (Exception e) {
            System.out.println("Failed to get eBay official time.");
            e.printStackTrace();
        }
    }

    private static ApiContext getApiContext() throws IOException {
        String input;
        ApiContext apiContext = new ApiContext();

        ApiCredential cred = apiContext.getApiCredential();

        // Set your OAuth token here
        String oAuthToken = "v^1.1#i^1#p^3#f^0#I^3#r^0#t^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";
        cred.seteBayToken(oAuthToken);

        input = ConsoleUtil.readString("Enter eBay SOAP server URL (e.g., https://api.ebay.com/wsapi): ");
        apiContext.setApiServerUrl(input);

        return apiContext;
    }

    private static void applySslSettings() throws IOException {
        try {
            SSLContext sslContext = SSLContext.getInstance("TLSv1.2");
            sslContext.init(null, null, null);
            SSLParameters sslParameters = sslContext.getSupportedSSLParameters();
            sslParameters.setProtocols(new String[]{"TLSv1.2"});
            SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
            HttpsURLConnection.setDefaultSSLSocketFactory(sslSocketFactory);
            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });
        } catch (Exception e) {
            throw new IOException("Failed to apply SSL settings.", e);
        }
    }
}
