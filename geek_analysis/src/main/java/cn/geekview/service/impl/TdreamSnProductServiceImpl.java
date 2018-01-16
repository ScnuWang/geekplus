package cn.geekview.service.impl;


import cn.geekview.entity.mapper.primary.TdreamSnProduct_PrimaryMapper;
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
     *  平台整体分析
     * @param crawlFrequency 抓取频率
     * @param updateDateTime 更新时间
     */
    public void analysis(Integer crawlFrequency , Date updateDateTime){
        insertOrUpdateProductTable(crawlFrequency,updateDateTime);
        websiteDataProcess();
    }

    /**
     * 查询产品记录并插入或修改t_dream_product
     */
    public void insertOrUpdateProductTable(Integer crawlFrequency , Date updateDateTime){
         /*
            1. 获取平台最新抓取的所有的产品数据
            2. 获取离当前时间上一次抓取的时间点，左右时间范围为抓取时间间隔的一半
            3. 这样就只处理上线时仍在抓取的项目数据，对于之前的数据单独处理，这样可以提高后续每次分析的效率
            4. 每个小时处理一次
         */
        DateTime dateTime = new DateTime(updateDateTime);
        DateTime date = new DateTime(dateTime.getYear(),dateTime.getMonthOfYear(),dateTime.getDayOfMonth(),dateTime.getHourOfDay(),0,0);
        //获取离当前时间上一次抓取的时间点，左右时间范围为抓取时间间隔的一半
        List<TdreamTbProduct> products = productPrimaryMapper.queryProduct_Newest(date.plusMinutes(-crawlFrequency/2).toDate(),date.plusMinutes(crawlFrequency/2).toDate());
        for (TdreamTbProduct product : products) {
            productService.insertOrUpdate(Constant.WEBSITE_ID_SUNING,product);
        }
    }

    /**
     *  平台数据处理
     *  更新时间   ！！！！！！！固定为每天中午12点，方便后续处理，因为每天处理一次
     *                                  如果要查看数据实际更新时间，可以参考update_time字段
     */
    public void websiteDataProcess(){

        //根据平台编号查询平台数据
        TdreamWebsite website  = websiteService.queryWebsiteDataByWebsiteId(Constant.WEBSITE_ID_SUNING);
        //平台名称
        website.setWebsiteName("苏宁众筹");

        DateTime updateDateTime = new DateTime(DateTime.now().getYear(),DateTime.now().getMonthOfYear(),DateTime.now().getDayOfMonth(),12,0,0);

        //更新时间
        website.setUpdateDatetime(updateDateTime.toDate());

        //成功的项目个数
        Integer successProductCounts  = productService.queryProductsByWebsiteIdAndStatus(Constant.WEBSITE_ID_SUNING,Constant.FUNDING_STATUAS_SUCCESS);
        Integer fildProductCounts = productService.queryProductsByWebsiteIdAndStatus(Constant.WEBSITE_ID_SUNING,Constant.FUNDING_STATUAS_FAIL);
        //平均完成率 : 成功的项目数除以成功加失败的项目数
        website.setAverageFinish(new BigDecimal(successProductCounts * 100).divide(new BigDecimal(successProductCounts+fildProductCounts), RoundingMode.HALF_UP));
        //查询前一天的总金额，如果前一天为空视为0
        TdreamWebsite website_totalAmount = websiteService.queryByUpdateDateTimeAndwebsiteId(Constant.WEBSITE_ID_SUNING,updateDateTime.plusDays(-1).toDate());
        //日增长金额
        if (website_totalAmount !=null){
            website.setAmountIncreaseDay(website.getTotalAmount().add(website_totalAmount.getTotalAmount().multiply(new BigDecimal("-1"))));
        }else {
            website.setAmountIncreaseDay(website.getTotalAmount());
        }
        /*
        方案：
            1. 可以每次调用都插入一条记录，这样可以记录平台整体的变化过程，数据的粒度到一个小时，但是会产生很多数据
            2. 每天只插入一次，后续都是更新，数据的粒度只能到天，由于对于整个平台来说，粒度要求暂时不需要太过细
            所以选择方案2
         */
        websiteService.insertOrUpdate(website);
    }

    /**
     * 根据抓取频率，原始编号获取所有的抓取记录
     * @param crawlFrequence
     * @param orignalId
     * @return
     */
    public List<TdreamSnProduct> queryProductPriceTrend(Integer crawlFrequence,String orignalId){
        return productPrimaryMapper.queryProductPriceTrend(crawlFrequence,orignalId);
    }
}
