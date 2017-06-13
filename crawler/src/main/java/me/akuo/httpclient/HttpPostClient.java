package me.akuo.httpclient;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.HttpHost;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by Akuo on 2017/4/20.
 */
public class HttpPostClient {


    private HttpPostClient() {
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpPostClient.class);

    public static void main(String[] args) throws InterruptedException {
        final String url = "http://23.235.162.8/order.asp";
        ExecutorService executor = Executors.newFixedThreadPool(1000);
        Runnable runnable = new Runnable() {
            public void run() {
                post(url);
            }};
        for (int i = 0; i < 900*10000; i++){
            executor.submit( runnable);
        }
        executor.shutdown();
        if (!executor.awaitTermination(1, TimeUnit.HOURS)) {
            LOGGER.info("Still waiting after 100ms: calling System.exit(0)...");
            System.exit(0);
        }
        LOGGER.info("Exiting normally...");
    }

    private static PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
    static {
        // Increase max total connection to 200
        cm.setMaxTotal(5000);
        // Increase default max connection per route to 20
        cm.setDefaultMaxPerRoute(1500);
        // Increase max connections for localhost:80 to 50
        HttpHost localhost = new HttpHost("23.235.162.8", 80);
        cm.setMaxPerRoute(new HttpRoute(localhost), 1000);
    }


    private static CloseableHttpClient httpClient = HttpClients.custom()
            .setConnectionManager(cm)
            .setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.86 Safari/537.36")
            .disableAutomaticRetries()
            .build();
    public static String post(String url) {
        // Create a trust manager that does not validate certificate chains
        //HttpHost proxy = new HttpHost("127.0.0.1", 8888);
        /*CloseableHttpClient httpclient = HttpClients.custom()
                .setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.86 Safari/537.36")
                .disableAutomaticRetries()
                .build();*/
        HttpPost request = new HttpPost(url);
        List<NameValuePair> params = new ArrayList<NameValuePair>();

        params.add(new BasicNameValuePair("a", RandomStringUtils.random(Integer.valueOf(RandomStringUtils.randomNumeric(2)))));
        params.add(new BasicNameValuePair("b", RandomStringUtils.random(Integer.valueOf(RandomStringUtils.randomNumeric(1)))));
        params.add(new BasicNameValuePair("c", RandomStringUtils.random(Integer.valueOf(RandomStringUtils.randomNumeric(1)))));
        params.add(new BasicNameValuePair("d", RandomStringUtils.random(Integer.valueOf(RandomStringUtils.randomNumeric(1)))));
        params.add(new BasicNameValuePair("e", RandomStringUtils.random(Integer.valueOf(RandomStringUtils.randomNumeric(1)))));
        params.add(new BasicNameValuePair("f", RandomStringUtils.random(Integer.valueOf(RandomStringUtils.randomNumeric(1)))));
        params.add(new BasicNameValuePair("curl", "Z2F"));
        params.add(new BasicNameValuePair("flags", "0"));
        params.add(new BasicNameValuePair("forcedownlevel", "0"));
        params.add(new BasicNameValuePair("formdir", "1"));
        if (LOGGER.isDebugEnabled())
            LOGGER.debug(params.toString());
        try {
            request.setEntity(new UrlEncodedFormEntity(params));
        } catch (UnsupportedEncodingException e) {
            LOGGER.error("error",e);
        }

        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(request);
        } catch (IOException e) {
            LOGGER.error("error",e);
        }
        String responseBody = null;
        if(response == null){
            return null;
        }
        try {
            responseBody = IOUtils.toString(response.getEntity().getContent(), "gb2312");
            request.releaseConnection();;
            response.close();
            //httpClient.close();
        } catch (IOException e) {
            LOGGER.error("error", e);
        }
        if (LOGGER.isDebugEnabled())
            LOGGER.debug(responseBody);
        return responseBody;
    }
}
