package cn.geekview.geek_analysis;

import cn.geekview.service.impl.*;
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
        DateTime dateTime = new DateTime(DateTime.now().getYear(),DateTime.now().getMonthOfYear(),DateTime.now().getDayOfMonth(),12,0,0);
        tdreamTbProductService.analysis(dateTime.toDate());
        tdreamJdProductService.analysis(dateTime.toDate());
        tdreamSnProductService.analysis(dateTime.toDate());
        tdreamXmProductService.analysis(dateTime.toDate());
    }
}
