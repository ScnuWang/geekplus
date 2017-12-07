package cn.geekview.geek_spider.service.impl;

import cn.geekview.geek_spider.entity.mapper.TdreamTaskMapper;
import cn.geekview.geek_spider.entity.mapper.TdreamXmItemMapper;
import cn.geekview.geek_spider.entity.mapper.TdreamXmProductMapper;
import cn.geekview.geek_spider.entity.model.*;
import cn.geekview.geek_spider.service.TdreamCrawlService;
import cn.geekview.geek_spider.util.CommonUtils;
import cn.geekview.geek_spider.util.Constant;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service("TdreamXmServiceImpl")
public class TdreamXmServiceImpl implements TdreamCrawlService {

    protected Logger logger = Logger.getLogger(this.getClass());
    //只抓取众筹中的，在地上上添加status=1
    private static String crawlProductlistUrl = "https://home.mi.com/app/shopv3/pipe";

    private static String crawlProductDetailUrl = "https://home.mi.com/app/shop/pipe?gid=";

    private static String productUrl = "https://youpin.mi.com/detail?gid=";

    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");

    @Autowired
    private TdreamTaskMapper taskMapper;

    @Autowired
    private TdreamXmProductMapper productMapper;

    @Autowired
    private TdreamXmItemMapper itemMapper;

    @Autowired
    private TdreamTaskServiceImpl taskService;

    @Autowired
    private MailServiceImpl mailService;

    @Autowired
    private RedisServiceImpl redisService;
    /**
     * 单次扫描所有的项目的耗时太长了
     * @param updateDateTime 如果updateDateTime为空，默认为当前时间
     * @param crawlFrequency 默认为24小时
     */
    @Override
    public void initTask(Date updateDateTime,final Integer crawlFrequency) {
        long startTime = System.currentTimeMillis();
        Map<String,TdreamTask> urlMap = new ConcurrentHashMap<>();//！！！！测试高并发情况
        String result = "";
        String listParms  =null;
        try {
            listParms = "data="+URLEncoder.encode("{\"getHotList\":{\"model\":\"Shopv2\",\"action\":\"getHomeList\",\"parameters\":{\"id\":\"5\"}},\"getBanner\":{\"model\":\"Shopv2\",\"action\":\"getBanner\",\"parameters\":{}},\"Crowdfunding\":{\"model\":\"Crowdfunding\",\"action\":\"get\",\"parameters\":{}}}", "utf-8");
            listParms = "data=%7B%22request%22%3A%7B%22model%22%3A%22Homepage%22%2C%22action%22%3A%22BuildHome%22%2C%22parameters%22%3A%7B%22id%22%3A%2278%22%7D%7D%7D";
        } catch (UnsupportedEncodingException e1) {
            logger.error("编码众筹项目列表请求参数异常："+e1.getMessage());
        }
        result = CommonUtils.httpRequestForXiaoMi("https://home.mi.com/app/shopv3/pipe",listParms).trim();
        System.out.println(result);
        JSONObject jsonObject = (JSONObject) JSONObject.parse(result);
        JSONArray data = jsonObject.getJSONObject("result").getJSONObject("request").getJSONArray("data");
        System.out.println(data);
        for(int i=0;i<data.size();i++){
            JSONObject entity = data.getJSONObject(i);
            String id = entity.getString("gid");
            if(!CommonUtils.isEmpty(id)){
                //拼接单个产品的详细地址
                String productUrl = crawlProductDetailUrl+id;
                //将待抓取的地址放入任务列表中
                TdreamTask task = new TdreamTask();
                task.setOriginalId(id);
                task.setCrawlFrequency(crawlFrequency);
                task.setCrawlStatus(Constant.CRAWL_STATUAS_WAITING);
                task.setCrawlUrl(productUrl);
                DateTime dateTime = new DateTime(updateDateTime);
                task.setCrawlTime(dateTime.toDate());
                task.setNextCrawlTime(dateTime.plusMinutes(crawlFrequency).toDate());
                task.setWebsiteId(Constant.WEBSITE_ID_XIAOMI);
                urlMap.put(id,task);
            }
        }
        //持久化到数据库
        /**
         * 1、判断任务表里是否已经存在相同的任务
         * 2、如果任务列表里面有抓取状态为等待抓取，但是下次抓取的时间已经过期，这种任务要重新激活，修改他的下次抓取时间
         *      为离当前最近的原本应该抓取的时间
         */
        List<TdreamTask> taskList = taskMapper.queryAllTaskList();
        for (Map.Entry<String,TdreamTask> entry : urlMap.entrySet()) {
            TdreamTask task = entry.getValue();
            //根据平台编号、项目原始ID、抓取频率判断相同的任务是否已经存在
            //将任务表缓存在Redis中，在插入之前判断是否已经存在
            if (!taskList.contains(task)){//覆写TdreamTask的equals方法
                taskMapper.insert(task);
            }
        }
        long time = System.currentTimeMillis()-startTime;
        System.out.println(" 小米初始化任务总共花费时间："+time/1000+"秒");
    }

    /**
     * @param updateDateTime 如果updateDateTime为空，默认为当前时间
     */
    @Override
    public void crawlTask(Date updateDateTime) {
        //预设抓取当前时间前后三分钟内将要被出发的任务
        long startTime = System.currentTimeMillis();
        List<TdreamTask>  taskList = taskMapper.queryTaskListByCrawlInterval(Constant.WEBSITE_ID_XIAOMI,Constant.CRAWL_STATUAS_WAITING,new DateTime(updateDateTime).plusMinutes(-3).toDate(),new DateTime(updateDateTime).plusMinutes(3).toDate());
        Runnable runnable = new Runnable() {
            public synchronized TdreamTask getTask(){
                if(taskList.size()>0){
                    return taskList.remove(0);
                } else{
                    return null;
                }
            }
            @Override
            public void run() {
                TdreamTask task = null;
                while ((task=getTask())!=null){
                        String ceawlUrl = task.getCrawlUrl();
                        String originalId = task.getOriginalId();
                        Integer crawlFrequency = task.getCrawlFrequency();
                        String detailParms = null;
                        try {
                            detailParms = "data="+URLEncoder.encode("{\"getDetail\":{\"model\":\"Shopv2\",\"action\":\"getDetail\",\"parameters\":{\"gid\":"+originalId+"}},\"getAct\":{\"model\":\"Activity\",\"action\":\"getAct\",\"parameters\":{\"gid\":"+originalId+"}},\"getCrowdfunding\":{\"model\":\"Crowdfunding\",\"action\":\"get\"}}", "utf-8");
                        } catch (UnsupportedEncodingException e1) {
                            logger.error("编码众筹项目详情请求参数异常："+e1.getMessage());
                            e1.printStackTrace();
                        }
                    try {
                            String result = CommonUtils.httpRequestForXiaoMi(ceawlUrl,detailParms).trim();
                            //解析结果数据
                            JSONObject jsonObject = (JSONObject) JSONObject.parse(result);
                            JSONObject dataObject = jsonObject.getJSONObject("result").getJSONObject("getDetail").getJSONObject("data");
                            JSONObject goodObject = dataObject.getJSONObject("good");
                            JSONObject crowdfundingObject = goodObject.getJSONObject("crowdfunding");
                            JSONArray items = dataObject.getJSONArray("props");
                            TdreamXmProduct product = new TdreamXmProduct();
                            product.setOriginalId(originalId);
                            product.setCrawlFrequency(crawlFrequency);
                            product.setProductName(goodObject.getString("name"));
                            product.setProductDesc(goodObject.getString("summary"));
                            product.setProductUrl(productUrl+originalId);
                            product.setProductImage(goodObject.getString("pic_url"));
                            product.setProductVideo(null);
                            product.setProductQrcode(null);
                            product.setBeginDate(null);
                            product.setEndDate(null);
                            product.setUpdateDatetime(new DateTime(updateDateTime).toDate());
                            //设置任务状态，除了预热中、众筹中的项目均不再自动抓取
                            switch(crowdfundingObject.getString("flag_name")){
                                case "筹款中": product.setStatusValue(2);product.setProductStatus("众筹中");task.setCrawlStatus(1);break;
                                case "失败": product.setStatusValue(4);product.setProductStatus("众筹失败");task.setCrawlStatus(2);break;
                                case "成功":product.setProductStatus("众筹成功");product.setStatusValue(3);task.setCrawlStatus(2);break;
                                case "预热中":product.setProductStatus("预热中");product.setStatusValue(1);task.setCrawlStatus(1);
                                default:product.setProductStatus("众筹异常");product.setStatusValue(5);task.setCrawlStatus(3);
                            }
                            product.setForeverValue(0);//是否为永久众筹（1：是  0：否）
                            product.setFocusCount(0);
                            product.setSupportCount(crowdfundingObject.getInteger("saled"));
                            product.setCurrencySign(Constant.CNY);
                            product.setOriginalTargetAmount(new BigDecimal(crowdfundingObject.getInteger("target_count")).multiply(new BigDecimal(goodObject.getBigInteger("price_min").divide(new BigInteger("100")))));
                            product.setOriginalRasiedAmount(new BigDecimal(goodObject.getBigInteger("saled_fee").divide(new BigInteger("100"))));
                            product.setTargetAmount(product.getOriginalTargetAmount());
                            product.setRasiedAmount(product.getOriginalRasiedAmount());
                            product.setFinishPercent(crowdfundingObject.getInteger("flag_percent"));
                            product.setRemainDay(0);
                            product.setPersonName(null);
                            product.setPersonImage(null);
                            product.setPersonDesc(null);

                            int supporttotal = 0;
                            int supPerson = 0;
                            int focuscount = 0;
                            if(items.size()>0){
                                List<TdreamXmItem> itemList = new ArrayList<TdreamXmItem>();
                                for(int j=0;j<items.size();j++){
                                    JSONObject itemObj = items.getJSONObject(j);
                                    TdreamXmItem item = new TdreamXmItem();
                                    item.setOriginalItemId(itemObj.getString("pid"));
                                    item.setItemTitle(itemObj.getString("name"));
                                    item.setItemDesc(itemObj.getString("summary"));
                                    item.setItemImage(itemObj.getString("img"));
                                    item.setCurrencySign(Constant.CNY);
                                    item.setOriginalItemPrice(itemObj.getBigDecimal("price").divide(new BigDecimal(100)));
                                    BigDecimal itemprice = itemObj.getBigDecimal("price").divide(new BigDecimal(100));
                                    item.setItemPrice(itemprice);

                                    supPerson = itemObj.getInteger("saled");
                                    //获取总的支持人数
                                    supporttotal = supporttotal + supPerson;
                                    //对于一元支持档视为关注的人数
                                    if(itemprice.multiply(itemprice)==itemprice){
                                        focuscount = supPerson;
                                    }
                                    item.setItemSupport(itemObj.getInteger("saled"));
                                    item.setItemTotal(itemObj.getInteger("total_limit"));
                                    item.setUpdateDatetime(new DateTime(updateDateTime).toDate());
                                    itemList.add(item);
                                }
                                product.setItemList(itemList);
                            }
                            product.setSupportCount(supporttotal);
                            product.setFocusCount(focuscount);
                            //持久化到数据库
                            productMapper.insert(product);
                            if(product.getItemList()!=null&&product.getItemList().size()>0){
                                itemMapper.insertRecordList(product.getPkId(),product.getItemList());
                            }
                            //根据主键修改任务的状态
                            DateTime dateTime = new DateTime(updateDateTime);
                            task.setCrawlTime(dateTime.toDate());
                            task.setNextCrawlTime(dateTime.plusMinutes(task.getCrawlFrequency()).toDate());
                            taskMapper.updateCrawlStatusByPrimaryKey(task);
                        } catch (Exception e) {
                            //打印日志
                            logger.error(e);
                            //发送错误报告邮件，最好是把所有的错误搜集一下，然后定时统一发送
//                String subject = "抓取淘宝任务异常";
//                String content = "平台：淘宝，原始编号："+originalId+",抓取频率："+crawlFrequency+"抓取地址："+ceawlUrl+"错误信息："+e.getMessage();
//                mailService.sendMail(subject,content);
                            //修改任务列表
                            task.setCrawlStatus(3);
                            DateTime dateTime = new DateTime(updateDateTime);
                            task.setCrawlTime(dateTime.toDate());
                            task.setNextCrawlTime(dateTime.plusMinutes(task.getCrawlFrequency()).toDate());
                            taskMapper.updateCrawlStatusByPrimaryKey(task);
                        }
                }
            }
        };
        ExecutorService executorService = Executors.newFixedThreadPool(50);
        for (int i = 0; i < 50; i++) {
            executorService.execute(runnable);
        }
        executorService.shutdown();
        while (true){
            if (executorService.isTerminated()){
                break;
            }
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
        }
        //放这里也不对，万一淘宝很快执行完，其他平台还未执行呢？
        taskService.queryTaskListByCrawlStatus(updateDateTime);
        long time = System.currentTimeMillis()-startTime;
        System.out.println("小米抓取项目总共花费时间："+time/1000+"秒");

    }

}
