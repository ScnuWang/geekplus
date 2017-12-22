package cn.geekview.geek_spider;

import cn.geekview.geek_spider.service.impl.TdreamJdServiceImpl;
import cn.geekview.geek_spider.service.impl.TdreamSnServiceImpl;
import cn.geekview.geek_spider.service.impl.TdreamTbServiceImpl;
import cn.geekview.geek_spider.service.impl.TdreamXmServiceImpl;
import cn.geekview.geek_spider.util.Constant;
import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.text.ParseException;
import java.util.Date;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ServiceTest {

    @Autowired
    private TdreamTbServiceImpl tbService;

    @Autowired
    private TdreamJdServiceImpl jdService;

    @Autowired
    private TdreamXmServiceImpl xmService;

    @Autowired
    private TdreamSnServiceImpl snService;

    @Test
    public void test1(){
        jdService.initTask(new Date(), 10);
    }

    @Test
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


    public void initTask(Date updateDateTime,Integer crawlFrequency){

        Runnable r2 =  new Runnable(){
            @Override
            public void run() {
                jdService.initTask(updateDateTime, crawlFrequency);
            }
        };

        new Thread(r2).start();
    }
}
