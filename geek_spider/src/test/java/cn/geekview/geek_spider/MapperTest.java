package cn.geekview.geek_spider;

import cn.geekview.geek_spider.entity.domain.TdreamTask;
import cn.geekview.geek_spider.entity.mapper.TdreamTaskMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class MapperTest {

    @Autowired
    private TdreamTaskMapper taskMapper;

    @Test
    public void test1(){
        TdreamTask task = new TdreamTask();
        task.setOriginalId("111");
        task.setCrawlFrequency(50);
        task.setCrawlStatus(1);
        task.setCrawlUrl("https://www.baidu.com");
        task.setCrawlTime(new Date());
        task.setNextCrawlTime(new Date());
        task.setWebsiteId(1);
        taskMapper.insert(task);
    }

}
