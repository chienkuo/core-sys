package me.akuo;

import com.google.gson.Gson;
import me.akuo.httpclient.HttpClient;

import java.io.*;
import java.util.List;
import java.util.Map;

/**
 * Created by Akuo on 2017/4/20.
 */
public class Main {
    private static Gson gson = new Gson();

    public static void main(String[] args) throws Exception {
        if (args.length < 1) {
            return;
        }
        InputStream is = null;
        try {
            is = new FileInputStream(args[0]);
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"), 512);
            // 读取一行，存储于字符串列表中
            for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                line = line.trim();
                // do something here
                doQuery(line);
            }

        } catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            try {
                if (is != null) {
                    is.close();
                    is = null;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void doQuery(String num) throws Exception {
        String url = "https://www.kuaidi100.com/autonumber/autoComNum?text=" + num;
        String resp1 = HttpClient.basicHttpsGetIgnoreCertificateValidation(url);
        Map<String, Object> map = gson.fromJson(resp1, Map.class);
        List list = (List) map.get("auto");
        if (list != null && list.size() > 0) {
            Map map1 = (Map) list.get(0);
            String comCode = (String) map1.get("comCode");
            String url2 = "https://www.kuaidi100.com/query?type=" + comCode + "&postid=" + num + "&id=1&valicode=&temp=" + System.currentTimeMillis();
            String resp2 = HttpClient.basicHttpsGetIgnoreCertificateValidation(url2);
            Map<String, Object> map2 = gson.fromJson(resp2, Map.class);
            if ("200".equals((String) map2.get("status"))) {
                System.out.println(num);
                List<Map> list2 = (List<Map>) map2.get("data");
                for (Map map3 : list2) {
                    System.out.println(map3.get("time") + " " + map3.get("location") + " " + map3.get("context"));
                }
            }
        }
    }

}
