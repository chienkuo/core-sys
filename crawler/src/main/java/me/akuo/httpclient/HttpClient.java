package me.akuo.httpclient;

import com.google.gson.Gson;
import org.apache.commons.io.IOUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.security.cert.X509Certificate;
import java.security.cert.CertificateException;

/**
 * Created by Akuo on 2017/4/20.
 */
public class HttpClient {
    private static Gson gson = new Gson();

    public static void main(String[] args) throws Exception {
        String url = "https://www.kuaidi100.com/autonumber/autoComNum?text=1008792580124";
        basicHttpsGetIgnoreCertificateValidation(url);
    }

    public static String basicHttpsGetIgnoreCertificateValidation(String url) throws Exception {
        // Create a trust manager that does not validate certificate chains
        TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {
                    public void checkClientTrusted(java.security.cert.X509Certificate[] x509Certificates, String s) throws CertificateException {

                    }

                    public void checkServerTrusted(java.security.cert.X509Certificate[] x509Certificates, String s) throws CertificateException {

                    }

                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }

                    public void checkClientTrusted(X509Certificate[] certs, String authType) {
                        // don't check
                    }

                    public void checkServerTrusted(X509Certificate[] certs, String authType) {
                        // don't check
                    }
                }
        };

        SSLContext ctx = SSLContext.getInstance("TLS");
        ctx.init(null, trustAllCerts, null);

        LayeredConnectionSocketFactory sslSocketFactory = new SSLConnectionSocketFactory(ctx);

        CloseableHttpClient httpclient = HttpClients.custom()
                .setSSLSocketFactory(sslSocketFactory)
                .build();

        HttpGet request = new HttpGet(url);
        request.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/57.0.2987.133 Safari/537.36");
        request.setHeader("Referer", "https://www.kuaidi100.com/");


        CloseableHttpResponse response = httpclient.execute(request);
        String responseBody = IOUtils.toString(response.getEntity().getContent(), "utf-8");
        System.out.println(responseBody);
        return responseBody;
    }
}
