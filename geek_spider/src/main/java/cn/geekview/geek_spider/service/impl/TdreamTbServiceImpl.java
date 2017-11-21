package cn.geekview.geek_spider.service.impl;

import cn.geekview.geek_spider.entity.domain.TdreamTask;
import cn.geekview.geek_spider.entity.domain.TdreamTbItem;
import cn.geekview.geek_spider.entity.domain.TdreamTbProduct;
import cn.geekview.geek_spider.entity.mapper.TdreamTaskMapper;
import cn.geekview.geek_spider.entity.mapper.TdreamTbItemMapper;
import cn.geekview.geek_spider.entity.mapper.TdreamTbProductMapper;
import cn.geekview.geek_spider.service.TdreamCrawlService;
import cn.geekview.geek_spider.util.CommonUtils;
import cn.geekview.geek_spider.util.Constant;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service("TdreamTbServiceImpl")
public class TdreamTbServiceImpl implements TdreamCrawlService {

    protected Logger logger = Logger.getLogger(this.getClass());

    private static String crawlProductlistUrl = "https://hstar-hi.alicdn.com/dream/ajax/getProjectList.htm?projectType=&type=6&sort=1&pageSize=100&page=";

    private static String crawlProductDetailUrl = "https://hstar-hi.alicdn.com/dream/ajax/getProjectForDetail.htm?id=";

    private static String productUrl = "https://hi.taobao.com/market/hi/deramdetail.php?spm=a215p.1472805.0.0.L3CCgY&id=";

    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");

    @Autowired
    private TdreamTaskMapper taskMapper;

    @Autowired
    private TdreamTbProductMapper productMapper;

    @Autowired
    private TdreamTbItemMapper itemMapper;

    @Autowired
    private MailServiceImpl mailService;

    @Autowired
    private RedisServiceImpl redisService;
    /**
     * @param updateDateTime 如果updateDateTime为空，默认为当前时间
     * @param crawlFrequency 默认为24小时
     */
    @Override
    public void initTask(Date updateDateTime,Integer crawlFrequency) {
        if (crawlFrequency == 0){
            crawlFrequency = Constant.TWENTY_FOUR_HOURS;
        }
        //获取产品列表数据
        TdreamTask task = null;
        Map<String,TdreamTask> urlMap = new ConcurrentHashMap<>();//！！！！测试高并发情况
        int page= 1;
        while (true){
            try {
                crawlProductlistUrl = crawlProductlistUrl+page;
                String result = CommonUtils.httpRequest_Get(crawlProductlistUrl);
                JSONObject jsonObject = (JSONObject) JSONObject.parse(result);
                JSONArray jsonArray = jsonObject.getJSONArray("data");
                int size = jsonArray.size();
                for(int i=0;i<size;i++){
                    JSONObject entity = jsonArray.getJSONObject(i);
                    //获取单个产品的原始编号
                    String id = entity.getString("id");
                    if(!CommonUtils.isEmpty(id)){
                        //拼接单个产品的详细地址
                        String productUrl = crawlProductDetailUrl+id;
                        //将待抓取的地址放入任务列表中
                        task = urlMap.get(id);
                        if(task == null){
                            task = new TdreamTask();
                            task.setOriginalId(id);
                            task.setCrawlFrequency(crawlFrequency);
                            task.setCrawlStatus(1);
                            task.setCrawlUrl(productUrl);
                            DateTime dateTime = new DateTime(updateDateTime);
                            task.setCrawlTime(dateTime.toDate());
                            task.setNextCrawlTime(dateTime.plusMinutes(crawlFrequency).toDate());
                            task.setWebsiteId(1);
                            urlMap.put(id,task);
                        }else{
                            task.setCrawlUrl(productUrl);
                            urlMap.put(id,task);
                        }
                    }
                }
                if(jsonArray.size()<1) break;
                page++;
                if(page>1) break;
            } catch (Exception e) {
                e.printStackTrace();
                logger.error(e.getMessage());
                String subject = "初始化淘宝任务异常：" +updateDateTime+"------->"+crawlFrequency;
                String content = "平台：淘宝，抓取频率："+crawlFrequency+"抓取地址："+crawlProductlistUrl+"错误信息："+e.getMessage();
                mailService.sendMail(subject,e.getMessage());
            }
        }
        //将任务放入到数据库列表
        for (Map.Entry<String,TdreamTask> entry : urlMap.entrySet()) {
            task = entry.getValue();
            taskMapper.insert(task);
        }
    }

    /**
     *
     * @param updateDateTime 如果updateDateTime为空，默认为当前时间
     * @param crawlIntervalTime 抓取间隔时间
     */
    @Override
    public void crawlTask(Date updateDateTime,Integer crawlIntervalTime) {
        //频率为24小时的任务从数据库查询，其他频率先从Redis中查询,以及修改redis



        List<TdreamTask> taskList = taskMapper.queryTaskList(1,1,new DateTime().plusMinutes(crawlIntervalTime).toDate(),new DateTime().plusMinutes(-crawlIntervalTime).toDate());
        for (TdreamTask task : taskList) {
            String ceawlUrl = task.getCrawlUrl();
            String originalId = task.getOriginalId();
            Integer crawlFrequency = task.getCrawlFrequency();
            try {
                String result = CommonUtils.httpRequest_Get(ceawlUrl);
                //解析结果数据
                JSONObject jsonObject = (JSONObject) JSONObject.parse(result);
                JSONObject rootObject = jsonObject.getJSONObject("data");
                TdreamTbProduct product = new TdreamTbProduct();
                product.setOriginalId(originalId);
                product.setCrawlFrequency(crawlFrequency);
                product.setProductName(rootObject.getString("name"));
                product.setProductDesc(rootObject.getString("content"));
                product.setProductUrl(productUrl+originalId);
                product.setProductImage("https:"+rootObject.getString("image"));
                product.setProductVideo(rootObject.getString("video"));
                product.setProductQrcode("https:"+rootObject.getString("qrcode"));
                product.setBeginDate(dateFormat.parse(rootObject.getString("begin_date")));
                product.setEndDate(dateFormat.parse(rootObject.getString("end_date")));
                product.setUpdateDatetime(new DateTime(updateDateTime).toDate());
                switch (rootObject.getInteger("status_value")){
                    case 4:product.setStatusValue(2);product.setProductStatus("众筹中");break;
                    case 5:product.setStatusValue(4);product.setProductStatus("众筹失败");break;
                    case 6:
                    case 8:
                    case 9:product.setProductStatus("众筹成功");product.setStatusValue(3);break;
                    case 20:product.setProductStatus("预热中");product.setStatusValue(1);
                    default:product.setProductStatus("众筹异常");product.setStatusValue(5);
                }
                product.setForeverValue(0);//是否为永久众筹（1：是  0：否）
                product.setFocusCount(rootObject.getInteger("focus_count"));
                product.setSupportCount(rootObject.getInteger("support_person"));
                product.setCurrencySign(Constant.CNY);
                product.setOriginalTargetAmount(rootObject.getBigDecimal("target_money"));
                product.setOriginalRasiedAmount(rootObject.getBigDecimal("curr_money"));
                product.setTargetAmount(rootObject.getBigDecimal("target_money"));
                product.setRasiedAmount(rootObject.getBigDecimal("curr_money"));
                product.setFinishPercent(rootObject.getInteger("finish_per"));
                product.setRemainDay(rootObject.getInteger("remain_day"));
                product.setPersonName(rootObject.getJSONObject("person").getString("name"));
                product.setPersonImage(rootObject.getJSONObject("person").getString("image"));
                product.setPersonDesc(rootObject.getJSONObject("person").getString("desc"));
                JSONArray items = rootObject.getJSONArray("items");
                if(items.size()>0){
                    List<TdreamTbItem> itemList = new ArrayList<TdreamTbItem>();
                    for(int j=0;j<items.size();j++){
                        JSONObject itemObj = items.getJSONObject(j);
                        TdreamTbItem item = new TdreamTbItem();
                        item.setOriginalItemId(itemObj.getString("item_id"));
                        item.setItemTitle(itemObj.getString("title"));
                        item.setItemDesc(itemObj.getString("desc"));
                        item.setItemImage("https:"+itemObj.getString("images"));
                        item.setCurrencySign(Constant.CNY);
                        item.setOriginalItemPrice(itemObj.getBigDecimal("price"));
                        item.setItemPrice(itemObj.getBigDecimal("price"));
                        item.setItemSupport(itemObj.getInteger("support_person"));
                        item.setItemTotal(itemObj.getInteger("total"));
                        item.setUpdateDatetime(new DateTime(updateDateTime).toDate());
                        itemList.add(item);
                    }
                    product.setItemList(itemList);
                }
                //持久化到数据库
                productMapper.insert(product);
                if(product.getItemList()!=null&&product.getItemList().size()>0){
                    itemMapper.insertRecordList(product.getPkId(),product.getItemList());
                }
                //根据主键修改任务的状态
                task.setCrawlStatus(1);
                DateTime dateTime = new DateTime(updateDateTime);
                task.setCrawlTime(dateTime.toDate());
                task.setNextCrawlTime(dateTime.plusMinutes(task.getCrawlFrequency()).toDate());
                taskMapper.updateCrawlStatusByPrimaryKey(task);
            } catch (Exception e) {
                //打印日志
                logger.error(e.getMessage());
                //发送邮件
                String subject = "抓取淘宝任务异常";
                String content = "平台：淘宝，原始编号："+originalId+",抓取频率："+crawlFrequency+"抓取地址："+ceawlUrl+"错误信息："+e.getMessage();
                mailService.sendMail(subject,content);
                //修改任务列表
                task.setCrawlStatus(3);
                DateTime dateTime = new DateTime(updateDateTime);
                task.setCrawlTime(dateTime.toDate());
                task.setNextCrawlTime(dateTime.plusMinutes(task.getCrawlFrequency()).toDate());
                taskMapper.updateCrawlStatusByPrimaryKey(task);
            }
        }
    }

}
