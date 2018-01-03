package cn.geekview.geek_analysis;

import cn.geekview.service.impl.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

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
        tdreamTbProductService.insertOrUpdateProduct();
        tdreamJdProductService.insertOrUpdateProduct();
        tdreamSnProductService.insertOrUpdateProduct();
        tdreamXmProductService.insertOrUpdateProduct();
    }
}
