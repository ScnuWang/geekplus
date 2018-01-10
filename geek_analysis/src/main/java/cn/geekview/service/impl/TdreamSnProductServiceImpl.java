package cn.geekview.service.impl;


import cn.geekview.entity.mapper.primary.TdreamSnProduct_PrimaryMapper;
import cn.geekview.entity.mapper.primary.TdreamTbProduct_PrimaryMapper;
import cn.geekview.entity.mapper.secondary.TdreamTbProduct_SecondaryMapper;
import cn.geekview.entity.model.TdreamSnProduct;
import cn.geekview.entity.model.TdreamTbProduct;
import cn.geekview.entity.model.TdreamWebsite;
import cn.geekview.util.Constant;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;

@Service("TdreamSnProductServiceImpl")
public class TdreamSnProductServiceImpl {

    @Autowired
    private TdreamSnProduct_PrimaryMapper productPrimaryMapper;

    @Autowired
    private TdreamWebsiteServiceImpl websiteService;

    @Autowired
    private TdreamProductServiceImpl productService;

    /**
     * 查询产品记录并插入或修改t_dream_product
     */
    public void analysis(Date updateDateTime){
        //获取平台最新的所有的产品数据
        List<TdreamSnProduct> products = productPrimaryMapper.queryAllProduct();
        for (TdreamSnProduct product : products) {
            productService.insertOrUpdate(Constant.WEBSITE_ID_SUNING,product);
        }


        //先实现，后期再逐步优化



        //根据平台编号查询平台数据
        TdreamWebsite website  = websiteService.queryWebsiteDataByWebsiteId(Constant.WEBSITE_ID_SUNING);
        //平台名称
        website.setWebsiteName("苏宁众筹");
        //更新时间
        website.setUpdateDatetime(updateDateTime);
        //成功的项目个数
        Integer successProductCounts  = productService.queryProductsByWebsiteIdAndStatus(Constant.WEBSITE_ID_SUNING,Constant.FUNDING_STATUAS_SUCCESS);
        Integer fildProductCounts = productService.queryProductsByWebsiteIdAndStatus(Constant.WEBSITE_ID_SUNING,Constant.FUNDING_STATUAS_FAIL);
        //平均完成率 : 成功的项目数除以成功加失败的项目数
        website.setAverageFinish(new BigDecimal(successProductCounts * 100).divide(new BigDecimal(successProductCounts+fildProductCounts), RoundingMode.HALF_UP));
        //查询前一天的总金额，如果前一天为空视为0
        TdreamWebsite website_totalAmount = websiteService.queryByUpdateDateTimeAndwebsiteId(Constant.WEBSITE_ID_SUNING,new DateTime(updateDateTime).plusDays(-1).toDate());
        //日增长金额
        if (website_totalAmount !=null){
            website.setAmountIncreaseDay(website.getTotalAmount().add(website_totalAmount.getTotalAmount().multiply(new BigDecimal("-1"))));
        }else {
            website.setAmountIncreaseDay(website.getTotalAmount());
        }
        websiteService.insert(website);
    }
}
