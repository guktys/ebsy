package com.example.EbayMaven;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class JavaHttpsClient {
    public static void main(String[] args) throws Exception {
        // Target URL
        String httpsURL = "https://api.ebay.com/wsapi";
        URL url = new URL(httpsURL);

        // Create SSLContext with TLSv1.2
        SSLContext sslContext = SSLContext.getInstance("TLSv1.2");
        sslContext.init(null, getTrustManagers(), null);

        // Make connection using HttpsURLConnection
        HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
        conn.setSSLSocketFactory(sslContext.getSocketFactory());

        // Read the response
        InputStream inputStream = conn.getInputStream();
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String inputLine;

        while ((inputLine = bufferedReader.readLine()) != null) {
            // Read the response
            System.out.println(inputLine);
        }
        bufferedReader.close();
    }

    /**
     * Get trust managers that trust all certificates.
     */
    private static TrustManager[] getTrustManagers() {
        return new TrustManager[] {
                new X509TrustManager() {
                    @Override
                    public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType) {}
                    @Override
                    public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType) {}
                    @Override
                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }
                }
        };
    }
}
