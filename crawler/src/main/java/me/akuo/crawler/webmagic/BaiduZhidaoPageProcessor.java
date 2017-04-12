package me.akuo.crawler.webmagic;

/**
 * Created by Akuo on 2017/4/12.
 */
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.concurrent.atomic.AtomicLong;

public class BaiduZhidaoPageProcessor implements PageProcessor {

    private Site site = Site.me().setRetryTimes(3).setSleepTime(100)
            .setUserAgent("Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/57.0.2987.133 Safari/537.36")
            .setUseGzip(true)
            .addHeader("Host","zhidao.baidu.com")
            .addHeader("Accept-Encoding","gzip")
            .addHeader("Accept-Language","zh-CN,zh;q=0.8");
    private AtomicLong atomicLong = new AtomicLong(0L);
    private AtomicLong skipNum = new AtomicLong(0L);

    public void process(Page page) {
        page.addTargetRequests(page.getHtml().links().regex("(https://zhidao\\.baidu\\.com/question/[0-9]{19}\\.html.*$)").all());
        site.addHeader("Referer", page.getUrl().toString());
        String answerId = page.getHtml().xpath("//div[@class='wgt-best']/@id").toString();
        if(answerId != null && answerId.startsWith("best-answer-")){
            page.putField("title", page.getHtml().xpath("//span[@class='ask-title']/text()").toString());
            answerId = answerId.replace("best-","");
            page.putField("answer", page.getHtml().xpath("//div[@id='"+answerId+"']/div[@class='content']/html()").toString());
            atomicLong.addAndGet(1L);
        }else {
            page.setSkip(true);
            skipNum.addAndGet(1L);
        }
    }

    public Site getSite() {
        return site;
    }

    public long getAtomicLong(){
        return atomicLong.get();
    }

    public long getSkipNum(){
        return skipNum.get();
    }

    public static void main(String[] args) {
        BaiduZhidaoPageProcessor baiduZhidao = new BaiduZhidaoPageProcessor();
        Spider.create(baiduZhidao).addUrl("https://zhidao.baidu.com/").thread(5).run();
        System.out.println(baiduZhidao.getAtomicLong());
        System.out.println(baiduZhidao.getSkipNum());
    }
}
