package cn.geekview.geek_spider;

import cn.geekview.geek_spider.service.impl.TdreamTbServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
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
//        tbService.initTask();
        try {
            tbService.crawlTask(1,new Date());
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
