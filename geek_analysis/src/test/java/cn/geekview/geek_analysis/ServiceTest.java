package cn.geekview.geek_analysis;

import cn.geekview.service.impl.TdreamProductServiceImpl;
import cn.geekview.service.impl.TdreamWebsiteServiceImpl;
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


    @Test
    public void test(){
        product.insertOrUpdate(1,"20072767");
    }
}
