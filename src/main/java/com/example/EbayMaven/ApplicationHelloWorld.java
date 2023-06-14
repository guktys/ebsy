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
        String oAuthToken = "v^1.1#i^1#f^0#r^0#p^1#I^3#t^H4sIAAAAAAAAAOVYe2wURRjv9YUVKoSHPDXnIonQ7O7s7t31dqEXry8o9AV3lIIKmdudu663j2N3tr0jUUtNCATDH8YEiERrojUqGCWxBAiagBH1LwMFE6NEg5EGkYgJSEI0zl2Pcq2kFHqJTbx/Ljvzfd98v998j5kB3aVly3as2vFnuWtKYW836C50ubipoKy0pOKRosIFJQUgR8DV2/1kd3FP0eAKG+paQlqH7IRp2Mid1DXDljKDVZRjGZIJbdWWDKgjW8KyFAo2NUo8A6SEZWJTNjXK3VBbRYm8HPVAL4eEiOLzyl4yaty2GTarKAj8oo9XhKhHBAjw6XnbdlCDYWNo4CqKB7xAAx/NecI8kEClxImMx8NtotxtyLJV0yAiDKACGXeljK6V4+vYrkLbRhYmRqhAQ7A+1BJsqK1rDq9gc2wFsjyEMMSOPfKrxlSQuw1qDhp7GTsjLYUcWUa2TbGBoRVGGpWCt515APczVHNRUYYCD3lR4X1+6MsLlfWmpUM8th/pEVWhoxlRCRlYxal7MUrYiDyPZJz9aiYmGmrd6b+1DtTUqIqsKqquOrgx2NpKBWLIUKGC4nQY2ZgOVbfTEVAJoz4/EAkiheNFEWQXGbKUpXjUKjWmoahpwmx3s4mrEfEYjeCFEyVvDi9EqMVosYJRnPYmlz8+y58gEjn29g46uMNI7ynSCQnuzOe92R/WxthSIw5GwxZGT2ToISmTSKgKNXoyE4fZ0EnaVVQHxgmJZbu6upgugTGtGMsDwLHtTY0huQPpkMrKpnM9aav3VqDVDBQZEU1blXAqQXxJkjglDhgxKuABgiB6sryPdCswevRfAzmY2ZHZkK/sUFDUi0TAV3pljvPKfD6yI5ANUDbtB4rAFK1DK45wQoMyomUSZ46OLFWRBG+UF/xRRCs+MUp7xGiUjngVslgUIYBQJCKL/v9Lkow3zENIthDOW5znJcabVntCHbAuWOv1d21s2tCsrRW8IW9ybX3F+nDMqK7z1dRYalvnttp4rGq8mXBX8DWaSpgJk/XzSUA61ydOwirTxkiZELyQbCZQq6mpcmpybbBgKa3QwqlqJ0W+Q0jTyN+EoAYTiYb8Veu8gLyPQvFgmPPbof6D7nRXVHY6aCcXqrS+TQzAhMqk+w8jmzprQnLwYNO5Toa3ZLx2jyE4LMRGnBQTc0hMEE8UcvYbt5JKCjlDWpkyfpWhRklAjF+FXCwUR8YPtFCmIzOETTXWge37WjM5EVIijhYfv4qCoDahEFXJ9WJSBShBOgRZVYbuBUwGN2N3yoyFbNOxyJWIaUkflcNmHBnk8IEtU9OQ1cZNuOzquoNhREOTrf7moRapMPdkVNzjGpwEuDif31dZKfCCOCFscubss2WydZB8ds37uP2wI99hAgWZH9fjOgF6XEcLXS5QCWiuAiwtLVpfXDSNskndYWxoKBEzyagwypCSZ0DsWIiJo1QCqlZhqUv9bkC+mfMC1PscmDf8BlRWxE3NeRACi+7MlHDT55bzAvBxHkJeJSduAovvzBZzjxbPrhig2HVff391z3HOpP96ec7i5V3XQfmwkMtVUkDCtcDZdf3dp3fe6HnGP//KmjNrhBZ0eP9XvS0/n1zZeuHs8nf2vvbT5R9/2P6xf+DAlNP8nv5fv7nIlQ/+7Vs+/VLrqatHHjp8vG3nt/tu+j76VD+Eb7z1xZXg43NfeOrYQf293QtdnY3TPt81AH8P4fOfxQvPz9999ksvdXT1s/GWws369O2LSotvvX6k9uIbrpnGh8mHO4+dbm89u5td17dtZrlv6f4ZfZcP3fpFWWStPEDP8iYX15/rW9IPZgzG+s68z39ibG1fcuHCJbOxeO+1/qULu+fNOVm2Xvzt1UP6MtxPndLCb7544+A141xHomfBB29vZme/UlTSsG8rfUI79cdjVs8T13ZueGnWAd+toW38B51PkLCbEwAA";
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
