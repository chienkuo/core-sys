package me.akuo.httpclient;

import org.apache.commons.io.IOUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

/**
 * Created by Akuo on 2017/4/20.
 */
public class HttpClient {

    private HttpClient() {
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpClient.class);

    public static void main(String[] args) {
        String url = "https://www.kuaidi100.com/autonumber/autoComNum?text=1008792580124";
        basicHttpsGetIgnoreCertificateValidation(url);
    }

    public static String basicHttpsGetIgnoreCertificateValidation(String url) {
        // Create a trust manager that does not validate certificate chains
        TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {
                    public void checkClientTrusted(java.security.cert.X509Certificate[] x509Certificates, String s) throws CertificateException {
                        // don't check
                    }

                    public void checkServerTrusted(java.security.cert.X509Certificate[] x509Certificates, String s) throws CertificateException {
                        // don't check
                    }

                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }
                }
        };

        SSLContext ctx = null;
        try {
            ctx = SSLContext.getInstance("TLS");
        } catch (NoSuchAlgorithmException e) {
            LOGGER.error("error %s", e.getCause());
        }
        try {
            ctx.init(null, trustAllCerts, null);
        } catch (KeyManagementException e) {
            LOGGER.error("error", e);
        }


        LayeredConnectionSocketFactory sslSocketFactory = new SSLConnectionSocketFactory(ctx);

        CloseableHttpClient httpclient = HttpClients.custom()
                .setSSLSocketFactory(sslSocketFactory)
                .build();

        HttpGet request = new HttpGet(url);
        request.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/57.0.2987.133 Safari/537.36");
        request.setHeader("Referer", "https://www.kuaidi100.com/");


        CloseableHttpResponse response = null;
        try {
            response = httpclient.execute(request);
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
        String responseBody = null;
        try {
            responseBody = IOUtils.toString(response.getEntity().getContent(), "utf-8");
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
        if (LOGGER.isDebugEnabled())
            LOGGER.debug(responseBody);
        return responseBody;
    }
}
