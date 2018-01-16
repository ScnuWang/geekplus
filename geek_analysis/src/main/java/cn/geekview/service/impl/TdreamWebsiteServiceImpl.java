package cn.geekview.service.impl;

import cn.geekview.entity.mapper.primary.TdreamWebsite_PrimaryMapper;
import cn.geekview.entity.model.TdreamProduct;
import cn.geekview.entity.model.TdreamWebsite;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 *  平台整体分析
 */
@Service("TdreamWebsiteServiceImpl")
public class TdreamWebsiteServiceImpl {

    @Autowired
    private TdreamWebsite_PrimaryMapper websitePrimaryMapper;


    /**
     *  查询某个时间段内的每天的众筹金额增长
     * @param updateDatetime
     * @param days
     * @return
     */
    public Map queryByDateRange(Date updateDatetime,Integer days){
        List<TdreamWebsite> websiteList =  websitePrimaryMapper.queryByDateRange(new DateTime(updateDatetime).plusDays(-days).toDate(),updateDatetime);
        Map websiteMap = new HashMap();
        LinkedList taobaolist = new LinkedList();
        LinkedList jingdonglist = new LinkedList();
        LinkedList suninglist = new LinkedList();
        LinkedList xiaomilist = new LinkedList();
        LinkedList datelist = new LinkedList();
        for (TdreamWebsite website : websiteList) {
            switch (website.getWebsiteId()){
                case 1:taobaolist.add(website.getAmountIncreaseDay());break;
                case 2:jingdonglist.add(website.getAmountIncreaseDay());break;
                case 7:suninglist.add(website.getAmountIncreaseDay());break;
                case 15:xiaomilist.add(website.getAmountIncreaseDay());break;
            }
            if (!datelist.contains(website.getUpdateDatetime())){
                datelist.add(website.getUpdateDatetime());
            }
        }
        websiteMap.put("taobao",taobaolist);
        websiteMap.put("jingdong",jingdonglist);
        websiteMap.put("suning",suninglist);
        websiteMap.put("xiaomi",xiaomilist);
        websiteMap.put("updateDatetime",datelist);
        return websiteMap;
    }



    /**
     *  根据时间和平台编号查询
     * @param websiteId 平台编号
     * @param updateDatetime 更新时间
     * @return
     */
    public TdreamWebsite queryByUpdateDateTimeAndwebsiteId(Integer websiteId , Date updateDatetime){
        return websitePrimaryMapper.queryByUpdateDateTimeAndwebsiteId(websiteId,updateDatetime);
    }

    /**
     *  根据时间查询所有平台的数据
     * @param updateDatetime 更新时间
     * @return
     */
    public List<TdreamWebsite> queryByUpdateDateTime(Date updateDatetime){
        return websitePrimaryMapper.queryByUpdateDateTime(updateDatetime);
    }

    /**
     *  插入数据或者更新数据  更新时间固定为每天中午12点，方便后续处理，因为每天处理一次
     */
    public  void insertOrUpdate(TdreamWebsite website){
        //根据更新时间和平台编号查询是否已经存在
         TdreamWebsite result_Website = websitePrimaryMapper.queryByUpdateDateTimeAndwebsiteId(website.getWebsiteId(),website.getUpdateDatetime());
        if (result_Website == null) {
            websitePrimaryMapper.insert(website);
        }else {
            //更新当天的数据记录
            websitePrimaryMapper.updateByUpdateDateTimeAndwebsiteId(website);
        }
    }


    /**
     *  根据平台编号统计（t_dream_product）平台相关总的数据
     * @param websiteId 平台编号
     * @return
     */
    public TdreamWebsite queryWebsiteDataByWebsiteId(Integer websiteId){
        return websitePrimaryMapper.queryWebsiteDataByWebsiteId(websiteId);
    }
}
