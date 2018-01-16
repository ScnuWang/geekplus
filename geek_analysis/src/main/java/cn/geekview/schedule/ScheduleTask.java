package cn.geekview.schedule;

import cn.geekview.service.impl.TdreamJdProductServiceImpl;
import cn.geekview.service.impl.TdreamSnProductServiceImpl;
import cn.geekview.service.impl.TdreamTbProductServiceImpl;
import cn.geekview.service.impl.TdreamXmProductServiceImpl;
import cn.geekview.util.Constant;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 任务调度
 */
@Component
public class ScheduleTask {

    @Autowired
    private TdreamTbProductServiceImpl tdreamTbProductService;

    @Autowired
    private TdreamSnProductServiceImpl tdreamSnProductService;

    @Autowired
    private TdreamJdProductServiceImpl tdreamJdProductService;

    @Autowired
    private TdreamXmProductServiceImpl tdreamXmProductService;


    /**
     *  定时抓取完毕之后半小时分析数据
     */
    @Scheduled(cron = "0 20 17 * * ?")
    public void analysis_task(){
        DateTime dateTime = new DateTime(DateTime.now().getYear(),DateTime.now().getMonthOfYear(),15,12,0,0);
        Runnable r1 = new Runnable(){
            @Override
            public void run() {
                tdreamTbProductService.analysis(Constant.ONE_HOUR,dateTime.toDate());
            }
        };
        Runnable r2 =  new Runnable(){
            @Override
            public void run() {
                tdreamJdProductService.analysis(Constant.ONE_HOUR,dateTime.toDate());
            }
        };
        Runnable r3 =  new Runnable(){
            @Override
            public void run() {
                tdreamSnProductService.analysis(Constant.ONE_HOUR,dateTime.toDate());
            }
        };
        Runnable r4 = new Runnable(){
            @Override
            public void run() {
                tdreamXmProductService.analysis(Constant.ONE_HOUR,dateTime.toDate());
            }
        };
        new Thread(r1).start();
        new Thread(r2).start();
        new Thread(r3).start();
        new Thread(r4).start();
    }

}
