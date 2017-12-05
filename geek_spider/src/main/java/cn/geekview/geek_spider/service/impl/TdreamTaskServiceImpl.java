package cn.geekview.geek_spider.service.impl;

import cn.geekview.geek_spider.entity.mapper.TdreamTaskMapper;
import cn.geekview.geek_spider.entity.model.TdreamTask;
import cn.geekview.geek_spider.util.Constant;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service("TaskServiceImpl")
public class TdreamTaskServiceImpl {

    @Autowired
    TdreamTaskMapper taskMapper;

    public void queryTaskListByCrawlStatus(Date updateDateTime){
        //处理抓取状态是等待抓取，但是下次抓取时间已经过期的任务,
        // 如果放入定时器，会出现抓取任务还没开始，任务的下次抓取时间已经被修改，故而任务一直不能被抓取 只需要一个平台处理即可
        List<TdreamTask> listByCrawlStatus = taskMapper.queryTaskListByCrawlStatus(Constant.CRAWL_STATUAS_WAITING,updateDateTime);
        long n = updateDateTime.getTime();
        for (TdreamTask tdreamTask : listByCrawlStatus) {
            long m = tdreamTask.getCrawlTime().getTime();
            //已经错过的抓取次数
            int t = (int)Math.ceil((n-m)/(tdreamTask.getCrawlFrequency()*60*1000.0));
            Date nextCrawlTime = new DateTime(tdreamTask.getCrawlTime()).plusMinutes(tdreamTask.getCrawlFrequency()*t).toDate();
            tdreamTask.setNextCrawlTime(nextCrawlTime);
            taskMapper.updateCrawlStatusByPrimaryKey(tdreamTask);
        }
    }
}
