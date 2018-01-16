package cn.geekview.geek_analysis;

import cn.geekview.service.impl.*;
import cn.geekview.util.Constant;
import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ServiceTest {
    @Autowired
    private TdreamWebsiteServiceImpl website;

    @Autowired
    private TdreamProductServiceImpl product;

    @Autowired
    private TdreamTbProductServiceImpl tdreamTbProductService;

    @Autowired
    private TdreamSnProductServiceImpl tdreamSnProductService;

    @Autowired
    private TdreamJdProductServiceImpl tdreamJdProductService;

    @Autowired
    private TdreamXmProductServiceImpl tdreamXmProductService;


    @Test
    public void test(){
        DateTime dateTime = new DateTime();
        tdreamTbProductService.analysis(Constant.ONE_HOUR,dateTime.toDate());
        tdreamJdProductService.analysis(Constant.ONE_HOUR,dateTime.toDate());
        tdreamSnProductService.analysis(Constant.ONE_HOUR,dateTime.toDate());
        tdreamXmProductService.analysis(Constant.ONE_HOUR,dateTime.toDate());
//        Runnable r1 = new Runnable(){
//            @Override
//            public void run() {
//                tdreamTbProductService.analysis(dateTime.toDate());
//            }
//        };
//        Runnable r2 =  new Runnable(){
//            @Override
//            public void run() {
//                tdreamJdProductService.analysis(dateTime.toDate());
//            }
//        };
//        Runnable r3 =  new Runnable(){
//            @Override
//            public void run() {
//                tdreamSnProductService.analysis(dateTime.toDate());
//            }
//        };
//        Runnable r4 = new Runnable(){
//            @Override
//            public void run() {
//                tdreamXmProductService.analysis(dateTime.toDate());
//            }
//        };
//        new Thread(r1).start();
//        new Thread(r2).start();
//        new Thread(r3).start();
//        new Thread(r4).start();
    }

    @Test
    public void test1(){
        System.out.println(tdreamTbProductService.queryProductPriceTrend(60,"20072095").size());
    }
}
