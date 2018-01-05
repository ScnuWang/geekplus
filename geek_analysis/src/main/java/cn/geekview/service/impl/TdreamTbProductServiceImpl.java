package cn.geekview.service.impl;


import cn.geekview.entity.mapper.primary.TdreamTbProduct_PrimaryMapper;
import cn.geekview.entity.mapper.primary.TdreamWebsite_PrimaryMapper;
import cn.geekview.entity.mapper.secondary.TdreamTbProduct_SecondaryMapper;
import cn.geekview.entity.model.TdreamProduct;
import cn.geekview.entity.model.TdreamTbProduct;
import cn.geekview.entity.model.TdreamWebsite;
import cn.geekview.util.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service("TdreamTbProductServiceImpl")
public class TdreamTbProductServiceImpl {

    @Autowired
    private TdreamTbProduct_PrimaryMapper productPrimaryMapper;

    @Autowired
    private TdreamWebsite_PrimaryMapper websitePrimaryMapper;

    @Autowired
    private TdreamProductServiceImpl productService;

    /**
     * 查询产品记录并插入或修改t_dream_product
     */
    public void analysis(Date updateDateTime){
        //获取平台最新的所有的产品数据
//        List<TdreamTbProduct> products = productPrimaryMapper.queryAllProduct();
//        for (TdreamTbProduct product : products) {
//            productService.insertOrUpdate(Constant.WEBSITE_ID_TAOBAO,product);
//        }
        //根据平台编号查询平台数据
        TdreamWebsite website  = websitePrimaryMapper.queryWebsiteDataByWebsiteId(Constant.WEBSITE_ID_TAOBAO);
        website.setWebsiteName("淘宝众筹");
        //跟新时间
        website.setUpdateDatetime(new Date());
        //！！！！注：查询回来的AverageFinish是已完成的项目数
        website.setAverageFinish(website.getAverageFinish()/website.getTotalProducts());
        //！！！！注：查询回来的AmountIncreaseDay是当天的总金额
        website.setAmountIncreaseDay(website.getAmountIncreaseDay().add(website.getTotalAmount().multiply(new BigDecimal("-1"))));
        websitePrimaryMapper.insert(website);
    }








}
