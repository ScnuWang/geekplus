package cn.geekview.geek_spider;

import cn.geekview.geek_spider.entity.domain.TdreamTask;
import cn.geekview.geek_spider.entity.mapper.TdreamTaskMapper;
import cn.geekview.geek_spider.util.Constant;
import org.joda.time.DateTime;
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
        task.setPkId(1);
        DateTime dateTime = new DateTime();
        task.setCrawlTime(dateTime.toDate());
        task.setCrawlStatus(1);
        task.setNextCrawlTime(dateTime.plusMinutes(Constant.TWENTY_FOUR_HOURS).toDate());
        taskMapper.updateCrawlStatusByPrimaryKey(task);
//        taskMapper.insert(task);
    }

}
