package cn.geekview.geek_spider.service;

import java.text.ParseException;
import java.util.Date;

public interface TdreamCrawlService {
    /**
     * 初始化任务
     */
    void initTask(Date updateDateTime,Integer crawlFrequency);
    /**
     * 抓取任务
     */
    void crawlTask(Date updateDateTime,Integer crawlIntervalTime);
}
