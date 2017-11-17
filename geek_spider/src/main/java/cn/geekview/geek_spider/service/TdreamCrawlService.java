package cn.geekview.geek_spider.service;

import java.text.ParseException;
import java.util.Date;

public interface TdreamCrawlService {
    /**
     * 初始化任务
     */
    void initTask();
    /**
     * 抓取任务
     */
    void crawlTask(Integer websiteId, Date updateDateTime) throws ParseException;
}
