package cn.geekview.geek_spider.service.impl;

import cn.geekview.geek_spider.entity.mapper.TdreamTaskMapper;
import cn.geekview.geek_spider.entity.mapper.TdreamTbItemMapper;
import cn.geekview.geek_spider.entity.mapper.TdreamTbProductMapper;
import cn.geekview.geek_spider.entity.model.TdreamTask;
import cn.geekview.geek_spider.entity.model.TdreamTbItem;
import cn.geekview.geek_spider.entity.model.TdreamTbProduct;
import cn.geekview.geek_spider.service.TdreamCrawlService;
import cn.geekview.geek_spider.util.CommonUtils;
import cn.geekview.geek_spider.util.Constant;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service("TdreamTbServiceImpl")
public class TdreamTbServiceImpl implements TdreamCrawlService {

    protected Logger logger = Logger.getLogger(this.getClass());
    //只抓取众筹中的，在地上上添加status=1
    private static String preCrawlProductlistUrl = "https://hstar-hi.alicdn.com/dream/ajax/getProjectList.htm?projectType=&type=6&sort=1&status=1&pageSize=100&page=";
    //抓取全部项目的URL
    private static String preCrawlAllProductlistUrl = "https://hstar-hi.alicdn.com/dream/ajax/getProjectList.htm?projectType=&type=6&sort=1&pageSize=100&page=";

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
    private TdreamTaskServiceImpl taskService;

    @Autowired
    private MailServiceImpl mailService;

    @Autowired
    private RedisServiceImpl redisService;
    /**
     * 单次扫描所有的项目的耗时太长了
     *  分为11个线程，然后前面10个线程，每个线程扫描10页的数据
     * @param updateDateTime 如果updateDateTime为空，默认为当前时间
     * @param crawlFrequency 默认为24小时
     */
    @Override
    public void initTask(Date updateDateTime,final Integer crawlFrequency) {
        long startTime = System.currentTimeMillis();
        Map<String,TdreamTask> urlMap = new ConcurrentHashMap<>();//！！！！测试高并发情况
        String firstUrl = preCrawlProductlistUrl+1;
        try {
            String result = CommonUtils.httpRequest_Get(firstUrl);
            JSONObject jsonObject = (JSONObject) JSONObject.parse(result);
            Integer total = jsonObject.getInteger("total");
            Integer page_num = (int)Math.ceil(total/100.0);
            List<String> list = new ArrayList<>();
            for (int i = 1; i <= page_num; i++) {
                list.add(preCrawlProductlistUrl+i);
            }
            Runnable runnable = new Runnable() {
                private int order = 1;
                public synchronized String getThreadName(){
                    return "_线程_"+(order++);
                }
                public synchronized String getUrl(){
                    if(list.size()>0)
                        return list.remove(0);
                    else
                        return null;
                }
                @Override
                public void run() {
                    String url = null;
                    String result = null;
                    String threadname = getThreadName();
                    while ((url = getUrl())!=null){
                        result = CommonUtils.httpRequest_Get(url);
                        JSONObject jsonObject = (JSONObject) JSONObject.parse(result);
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        int size = jsonArray.size();
                        if(size<1) break;
                        for(int i=0;i<size;i++){
                            JSONObject entity = jsonArray.getJSONObject(i);
                            //获取单个产品的原始编号
                            String id = entity.getString("id");
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
                                task.setWebsiteId(Constant.WEBSITE_ID_TAOBAO);
                                System.out.println("当前请求地址："+url+"线程名："+threadname+"项目数："+size);
                                urlMap.put(id,task);
                            }
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
                Thread.sleep(1000);
            }
        } catch (Exception e) {
            //将异常请求加入任务表
            e.printStackTrace();
            logger.error(e.getMessage());
//            String subject = "初始化淘宝任务异常：" +updateDateTime+"------->"+crawlFrequency;
//            String content = "平台：淘宝，抓取频率："+crawlFrequency+"抓取地址："+crawlProductlistUrl+"错误信息："+e.getMessage();
//            mailService.sendMail(subject,content);
        }
        //持久化到数据库
        /**
         * 1、判断任务表里是否已经存在相同的任务
         * 2、如果任务列表里面有抓取状态为等待抓取，但是下次抓取的时间已经过期，这种任务要重新激活，修改他的下次抓取时间
         *      为离当前最近的原本应该抓取的时间
         */
        List<TdreamTask> taskList = taskMapper.queryAllTaskListByWebsiteId(Constant.WEBSITE_ID_TAOBAO);
        for (Map.Entry<String,TdreamTask> entry : urlMap.entrySet()) {
            TdreamTask task = entry.getValue();
            //根据平台编号、项目原始ID、抓取频率判断相同的任务是否已经存在
            //将任务表缓存在Redis中，在插入之前判断是否已经存在
            if (!taskList.contains(task)){//覆写TdreamTask的equals方法
                taskMapper.insert(task);
            }
        }
        long time = System.currentTimeMillis()-startTime;
        System.out.println("淘宝初始化任务总共花费时间："+time/1000+"秒");
    }

    /**
     * @param updateDateTime 如果updateDateTime为空，默认为当前时间
     */
    @Override
    public void crawlTask(Date updateDateTime) {
        //预设抓取当前时间前后三分钟内将要被出发的任务
        long startTime = System.currentTimeMillis();
        List<TdreamTask>  taskList = taskMapper.queryTaskListByCrawlInterval(Constant.WEBSITE_ID_TAOBAO,Constant.CRAWL_STATUAS_WAITING,new DateTime(updateDateTime).plusMinutes(-3).toDate(),new DateTime(updateDateTime).plusMinutes(3).toDate());
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
                        try {
                            String result = CommonUtils.httpRequest_Get(ceawlUrl);
                            //解析结果数据
                            JSONObject jsonObject = (JSONObject) JSONObject.parse(result);
                            JSONObject rootObject = jsonObject.getJSONObject("data");
                            TdreamTbProduct product = new TdreamTbProduct();
                            product.setOriginalId(originalId);
                            product.setCrawlFrequency(crawlFrequency);
                            product.setProductName(rootObject.getString("name"));
                            String productdesc = rootObject.getString("content").trim();
                            product.setProductDesc(productdesc.length()>255?productdesc.substring(0,255):productdesc);
                            product.setProductUrl(productUrl+originalId);
                            product.setProductImage("https:"+rootObject.getString("image"));
                            product.setProductVideo(rootObject.getString("video"));
                            product.setProductQrcode("https:"+rootObject.getString("qrcode"));
                            try {
                                product.setBeginDate(dateFormat.parse(rootObject.getString("begin_date")));
                                product.setEndDate(dateFormat.parse(rootObject.getString("end_date")));
                            }catch (ParseException e){
                                product.setEndDate(null);
                                product.setBeginDate(null);
                            }
                            product.setUpdateDatetime(new DateTime(updateDateTime).toDate());
                            //设置任务状态，除了预热中、众筹中的项目均不再自动抓取
                            switch (rootObject.getInteger("status_value")){
                                case 4:product.setStatusValue(2);product.setProductStatus("众筹中");task.setCrawlStatus(1);break;
                                case 5:product.setStatusValue(4);product.setProductStatus("众筹失败");task.setCrawlStatus(2);break;
                                case 6:
                                case 8:
                                case 9:product.setProductStatus("众筹成功");product.setStatusValue(3);task.setCrawlStatus(2);break;
                                case 20:product.setProductStatus("预热中");product.setStatusValue(1);task.setCrawlStatus(1);
                                default:product.setProductStatus("众筹异常");product.setStatusValue(5);task.setCrawlStatus(3);
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
                            String persondesc = rootObject.getJSONObject("person").getString("desc").trim();
                            product.setPersonDesc(persondesc.length()>255?persondesc.substring(0,255):persondesc);
                            JSONArray items = rootObject.getJSONArray("items");
                            if(items.size()>0){
                                List<TdreamTbItem> itemList = new ArrayList<TdreamTbItem>();
                                for(int j=0;j<items.size();j++){
                                    JSONObject itemObj = items.getJSONObject(j);
                                    TdreamTbItem item = new TdreamTbItem();
                                    item.setOriginalItemId(itemObj.getString("item_id"));
                                    item.setItemTitle(itemObj.getString("title"));
                                    String itemDesc = itemObj.getString("desc").trim();
                                    item.setItemDesc(itemDesc.length()>255?itemDesc.substring(0,255):itemDesc);
                                    String itemImage = "https:"+itemObj.getString("images").trim();
                                    item.setItemImage(itemImage.length()>255?itemImage.substring(0,255):itemImage);
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
                            DateTime dateTime = new DateTime(updateDateTime);
                            task.setCrawlTime(dateTime.toDate());
                            task.setNextCrawlTime(dateTime.plusMinutes(task.getCrawlFrequency()).toDate());
                            taskMapper.updateCrawlStatusByPrimaryKey(task);
                        } catch (Exception e) {
                            //打印日志
                            logger.error(e.getMessage());
                            //发送错误报告邮件，最好是把所有的错误搜集一下，然后定时统一发送
//                String subject = "抓取淘宝任务异常";
//                String content = "平台：淘宝，原始编号："+originalId+",抓取频率："+crawlFrequency+"抓取地址："+ceawlUrl+"错误信息："+e.getMessage();
//                mailService.sendMail(subject,content);
                            //修改任务列表
                            task.setCrawlStatus(3);
                            if (e != null||e.getMessage()!=null) {
                                task.setReserve1(e.getMessage().trim().substring(0,e.getMessage().length()>1000?1000:e.getMessage().length()));
                            }
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
        long time = System.currentTimeMillis()-startTime;
        System.out.println("淘宝抓取项目总共花费时间："+time/1000+"秒");
        //处理抓取状态是等待抓取，但是下次抓取时间已经过期的任务
        taskService.queryTaskListByCrawlStatus(updateDateTime,Constant.WEBSITE_ID_TAOBAO);
    }

}
