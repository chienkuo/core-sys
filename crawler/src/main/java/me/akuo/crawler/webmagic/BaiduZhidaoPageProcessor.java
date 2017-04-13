package me.akuo.crawler.webmagic;

/**
 * Created by Akuo on 2017/4/12.
 */

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

import java.io.IOException;

public class BaiduZhidaoPageProcessor implements PageProcessor {

    private Site site = Site.me().setRetryTimes(3).setSleepTime(100)
            .setUserAgent("Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/57.0.2987.133 Safari/537.36")
            .setUseGzip(true)
            .addHeader("Host", "zhidao.baidu.com")
            .addHeader("Accept-Encoding", "gzip")
            .addHeader("Accept-Language", "zh-CN,zh;q=0.8");

    public void process(Page page) {
        page.addTargetRequests(page.getHtml().links().regex("(https://zhidao\\.baidu\\.com/question/[0-9]{19}\\.html.*$)").all());

        String answerId = page.getHtml().xpath("//div[@class='wgt-best']/@id").toString();
        if (answerId != null && answerId.startsWith("best-answer-")) {
            System.out.println(page.getUrl().get());
            System.out.println(page.getHtml().xpath("//span[@class='ask-title']/text()").toString());
            answerId = answerId.replace("best-", "");
            System.out.println(page.getHtml().xpath("//div[@id='" + answerId + "']/div[@class='content']/html()").toString());

            /* synchronized (this){
                driver.get(page.getUrl().toString());
                WebElement bestDiv = driver.findElement(By.className("wgt-best"));
                String id = bestDiv.getAttribute("id");
                WebElement content = driver.findElement(By.id("best-content-" + id.replace("best-answer-", "")));
                page.putField("answer1", content.getText());
                WebElement title = driver.findElement(By.className("ask-title"));
                page.putField("title1", title.getText());
            }*/
        } else {
            page.setSkip(true);
        }
    }

    public Site getSite() {
        return site;
    }


    public static void main(String[] args) throws IOException {
        /*BaiduZhidaoPageProcessor baiduZhidao = new BaiduZhidaoPageProcessor();
        service = new ChromeDriverService.Builder()
                .usingDriverExecutable(new File("C:\\Users\\pc\\Downloads\\chromedriver.exe"))
                .usingAnyFreePort()
                .build();
        service.start();
        driver = new RemoteWebDriver(service.getUrl(),
                DesiredCapabilities.chrome());
        Spider.create(baiduZhidao).addUrl("https://zhidao.baidu.com/").thread(5).run();
        service.stop();
        driver.close();
        System.out.println(baiduZhidao.getAtomicLong());
        System.out.println(baiduZhidao.getSkipNum());*/
        Spider.create(new BaiduZhidaoPageProcessor()).thread(5)
                //.setScheduler(new RedisScheduler("localhost"))
                // .addPipeline(new FilePipeline("C:\\Users\\pc\\Downloads\\test"))
                .setDownloader(new SeleniumDownloader("C:\\Users\\pc\\Downloads\\chromedriver.exe"))
                .addUrl("https://zhidao.baidu.com/")
                .run();
    }
}
