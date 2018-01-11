package cn.geekview.service.impl;

import cn.geekview.entity.mapper.primary.TdreamWebsite_PrimaryMapper;
import cn.geekview.entity.model.TdreamWebsite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *  平台整体分析
 */
@Service("TdreamWebsiteServiceImpl")
public class TdreamWebsiteServiceImpl {

    @Autowired
    private TdreamWebsite_PrimaryMapper websitePrimaryMapper;

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
     *  插入数据
     */
    public  void insert(TdreamWebsite website){
        websitePrimaryMapper.insert(website);
    }


    /**
     *  根据平台编号查询平台相关总的数据
     * @param websiteId 平台编号
     * @return
     */
    public TdreamWebsite queryWebsiteDataByWebsiteId(Integer websiteId){
        return websitePrimaryMapper.queryWebsiteDataByWebsiteId(websiteId);
    }
}
