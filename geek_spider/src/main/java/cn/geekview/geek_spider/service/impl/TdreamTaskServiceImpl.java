package cn.geekview.geek_spider.service.impl;

import cn.geekview.geek_spider.entity.mapper.TdreamTaskMapper;
import cn.geekview.geek_spider.entity.model.TdreamTask;
import cn.geekview.geek_spider.util.Constant;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service("TdreamTaskServiceImpl")
public class TdreamTaskServiceImpl {

    @Autowired
    TdreamTaskMapper taskMapper;

    /**
     * 添加单个抓取任务
     */
    public int insertOneTask(TdreamTask task){
        return taskMapper.insert(task);
    }

    /**
     * 同时添加多个抓取任务
     */
    public int insertTaskList(List<TdreamTask> taskList){
        return taskMapper.insertTaskList(taskList);
    }

    /**
     * 处理抓取状态是等待抓取，但是下次抓取时间已经过期的任务
     * @param updateDateTime
     * @param websiteId
     */
    public void queryTaskListByCrawlStatus(Date updateDateTime,Integer websiteId){
        List<TdreamTask> listByCrawlStatus = taskMapper.queryTaskListByCrawlStatus(Constant.CRAWL_STATUAS_WAITING,updateDateTime,websiteId);
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
