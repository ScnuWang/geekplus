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
        try {
//            tbService.initTask(new Date(), Constant.FIVE_MINUTES);
//            jdService.initTask(new Date(), Constant.FIVE_MINUTES);
//            xmService.initTask(new Date(), Constant.FIVE_MINUTES);
//            snService.initTask(new Date(), Constant.FIVE_MINUTES);
//            tbService.crawlTask(new Date());
//            jdService.crawlTask(new Date());
//            xmService.crawlTask(new Date());
//            snService.crawlTask(new Date());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
