package cn.geekview.geek_spider.schedule;

import cn.geekview.geek_spider.service.impl.TdreamTbServiceImpl;
import cn.geekview.geek_spider.util.Constant;
import org.joda.time.DateTime;
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



    //每天中午十二点触发
    @Scheduled(cron = "0 0 12 * * ?")
    public void initTask_TWENTY_FOUR_HOURS(){
        Date dateTime = new DateTime(DateTime.now().getYear(),DateTime.now().getMonthOfYear(),DateTime.now().getDayOfMonth(),12,0,0).toDate();
        tbService.initTask(dateTime, Constant.TWENTY_FOUR_HOURS);
    }

    //每隔2个小时触发一次
    @Scheduled(cron = "0 0 0-23/2 * * ?")
    public void initTask_TWO_HOURS(){
        tbService.initTask(new Date(), Constant.TWO_HOURS);
    }

//    //每隔5分钟触发一次
//    @Scheduled(cron = "0 0-59/5 * * * ?")
//    public void initTask_FIVE_MINUTES(){
//        tbService.initTask(new Date(), Constant.FIVE_MINUTES);
//    }



    //每隔5分钟触发一次
    @Scheduled(cron = "0 0-59/5 * * * ?")
    public void crawlTask(){
        tbService.crawlTask(new Date());
    }



}
