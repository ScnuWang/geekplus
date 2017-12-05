package cn.geekview.geek_spider.util;

import java.math.BigDecimal;

/**
 * 公共常量类
 */
public class Constant {
    /**
     * 抓取频率常量
     */
    public static final Integer FIVE_MINUTES = 5;
    public static final Integer ONE_HOUR = 60;
    public static final Integer TWO_HOURS = 2*60;
    public static final Integer TWENTY_FOUR_HOURS = 24*60;

    /**
     * 抓取平台编号
     */
    public static final Integer WEBSITE_ID_TAOBAO = 1;
    public static final Integer WEBSITE_ID_JINGDONG = 2;
    public static final Integer WEBSITE_ID_SUNING = 7;
    public static final Integer WEBSITE_ID_XIAOMI = 15;

    /**
     * 抓取状态
     */
    public static final Integer CRAWL_STATUAS_WAITING = 1;
    public static final Integer CRAWL_STATUAS_SUCCESS = 2;
    public static final Integer CRAWL_STATUAS_EXCEPTION = 3;
    public static final Integer CRAWL_STATUAS_STOP = 4;

    public static final String CNY = "CNY";
    public static final double MSEC_DAY = 24*60*60*1000;
    /**
     * 人民币汇率
     */
    public static final BigDecimal CNY_EXCHANGE_RATE = new BigDecimal(1);

}
