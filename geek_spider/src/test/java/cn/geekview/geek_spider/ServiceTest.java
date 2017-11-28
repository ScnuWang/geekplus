package cn.geekview.geek_spider;

import cn.geekview.geek_spider.service.impl.TdreamTbServiceImpl;
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

    @Test
    public void test1(){
        try {
//            tbService.initTask(new Date(), Constant.FIVE_MINUTES);
//            tbService.crawlTask(new DateTime(2017,11,24,12,0,0).toDate());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
