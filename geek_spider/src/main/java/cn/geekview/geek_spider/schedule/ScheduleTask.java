package cn.geekview.geek_spider.schedule;

import cn.geekview.geek_spider.service.impl.*;
import cn.geekview.geek_spider.util.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 任务调度
 */
@Component
public class ScheduleTask {

    @Autowired
    private TdreamTbServiceImpl tbService;

    @Autowired
    private TdreamJdServiceImpl jdService;

    @Autowired
    private TdreamSnServiceImpl snService;

    @Autowired
    private TdreamXmServiceImpl xmService;

    @Autowired
    private TdreamTaskServiceImpl taskService;



    //每天中午十二点触发
//    @Scheduled(cron = "0 0 12 * * ?")
//    public void initTask_TWENTY_FOUR_HOURS(){
//        Date dateTime = new DateTime(DateTime.now().getYear(),DateTime.now().getMonthOfYear(),DateTime.now().getDayOfMonth(),12,0,0).toDate();
//        tbService.initTask(dateTime, Constant.TWENTY_FOUR_HOURS);
//    }

    /**
     * 定时初始化任务
     */
    @Scheduled(cron = "0 0 0-23/1 * * ?")
    public void initTask_TWO_HOURS(){
        Runnable r1 = new Runnable(){
            @Override
            public void run() {
                tbService.initTask(new Date(), Constant.ONE_HOUR);
            }
        };
        Runnable r2 =  new Runnable(){
            @Override
            public void run() {
                jdService.initTask(new Date(), Constant.ONE_HOUR);
            }
        };
        Runnable r3 =  new Runnable(){
            @Override
            public void run() {
                snService.initTask(new Date(), Constant.ONE_HOUR);
            }
        };
        Runnable r4 = new Runnable(){
            @Override
            public void run() {
                xmService.initTask(new Date(),Constant.ONE_HOUR);
            }
        };
        new Thread(r1).start();
        new Thread(r2).start();
        new Thread(r3).start();
        new Thread(r4).start();
    }

    @Scheduled(cron = "0 0-59/5 * * * ?")
    public void crawl_Task_FIVE_MINUTES(){
        Runnable r1 = new Runnable(){
            @Override
            public void run() {
                tbService.crawlTask(new Date());
            }
        };
        Runnable r2 =  new Runnable(){
            @Override
            public void run() {
                jdService.crawlTask(new Date());
            }
        };
        Runnable r3 =  new Runnable(){
            @Override
            public void run() {
                snService.crawlTask(new Date());
            }
        };
        Runnable r4 = new Runnable(){
            @Override
            public void run() {
                xmService.crawlTask(new Date());
            }
        };
        new Thread(r1).start();
        new Thread(r2).start();
        new Thread(r3).start();
        new Thread(r4).start();
    }

}
