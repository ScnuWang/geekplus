package cn.geekview.geek_spider.service.impl;

import cn.geekview.geek_spider.entity.mapper.TdreamSnItemMapper;
import cn.geekview.geek_spider.entity.mapper.TdreamSnProductMapper;
import cn.geekview.geek_spider.entity.mapper.TdreamTaskMapper;
import cn.geekview.geek_spider.entity.model.TdreamSnItem;
import cn.geekview.geek_spider.entity.model.TdreamSnProduct;
import cn.geekview.geek_spider.entity.model.TdreamTask;
import cn.geekview.geek_spider.service.TdreamCrawlService;
import cn.geekview.geek_spider.util.CommonUtils;
import cn.geekview.geek_spider.util.Constant;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service("TdreamSnServiceImpl")
public class TdreamSnServiceImpl implements TdreamCrawlService{

    protected Logger logger = Logger.getLogger(this.getClass());
    private static String preCrawlProductlistUrl = "https://zc.suning.com/project/browseList.htm?t=02&pageNumber=";
    private static String productUrl = "https://zc.suning.com";

    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Autowired
    private TdreamTaskMapper taskMapper;

    @Autowired
    private TdreamSnProductMapper productMapper;

    @Autowired
    private TdreamSnItemMapper itemMapper;

    @Override
    public void initTask(Date updateDateTime, Integer crawlFrequency) {
        long startTime = System.currentTimeMillis();
        //获取总的项目数，计算总的页数，然后多线程抓取
        String firstUrl = preCrawlProductlistUrl+1;
        Map<String,TdreamTask> urlMap = new ConcurrentHashMap<>();
        try {
            String result = CommonUtils.httpRequest_Get(firstUrl);
            Document document = Jsoup.parse(result);
            //获取总的项目数
            Integer total = Integer.valueOf(document.select(".query-section-total span").text());
            Integer page_num = (int)Math.ceil(total/20.0);
            List<String> list = new ArrayList<>();
            for (int i = 1; i <= page_num; i++) {
                list.add(preCrawlProductlistUrl+i);
            }
            Runnable runnable  = new Runnable() {
                private int order = 1;
                public synchronized String getThreadName(){
                    return "_线程_"+(order++);
                }
                public synchronized String getUrl(){
                    if(list.size()>0){
                        return list.remove(0);
                    } else{
                        return null;
                    }
                }
                @Override
                public void run() {
                    String url = null;
                    String result = null;
                    String threadname = getThreadName();
                    while ((url = getUrl())!=null){
                        result = CommonUtils.httpRequest_Get(url);
                        Document parse = Jsoup.parse(result);
                        Elements projectList = parse.select(".item-list .item-placeholder a");
                        for (Element element : projectList) {
                            String link = element.attr("href").trim();
                            if(!CommonUtils.isEmpty(link)){
                                if(!link.startsWith("https://zc.suning.com")){
                                    link = productUrl + link;
                                }
                                String id = link.substring(link.lastIndexOf("=")+1,link.length());
                                if(!CommonUtils.isEmpty(id)){
                                    //将待抓取的地址放入任务列表中
                                    TdreamTask task = new TdreamTask();
                                    task.setOriginalId(id);
                                    task.setCrawlFrequency(crawlFrequency);
                                    task.setCrawlStatus(Constant.CRAWL_STATUAS_WAITING);
                                    task.setCrawlUrl(link);
                                    DateTime dateTime = new DateTime(updateDateTime);
                                    task.setCrawlTime(dateTime.toDate());
                                    task.setNextCrawlTime(dateTime.plusMinutes(crawlFrequency).toDate());
                                    task.setWebsiteId(Constant.WEBSITE_ID_SUNING);
                                    System.out.println("当前请求地址："+url+"线程名："+threadname);
                                    urlMap.put(id,task);
                                }
                            }
                        }
                    }
                }
            };
            ExecutorService executorService = Executors.newFixedThreadPool(15);
            for (int i = 0; i < 15; i++) {
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
            e.printStackTrace();
            logger.error(e.getMessage());
        }
        List<TdreamTask> taskList = taskMapper.queryAllTaskList();
        for (Map.Entry<String,TdreamTask> entry : urlMap.entrySet()) {
            TdreamTask task = entry.getValue();
            if (!taskList.contains(task)){
                taskMapper.insert(task);
            }
        }
        long time = System.currentTimeMillis()-startTime;
        System.out.println("苏宁初始化任务总共花费时间："+time/1000+"秒");
    }



    @Override
    public void crawlTask(Date updateDateTime) {
        //预设抓取当前时间前后三分钟内将要被出发的任务
        long startTime = System.currentTimeMillis();
        List<TdreamTask>  taskList = taskMapper.queryTaskListByCrawlInterval(Constant.WEBSITE_ID_SUNING,Constant.CRAWL_STATUAS_WAITING,new DateTime(updateDateTime).plusMinutes(-3).toDate(),new DateTime(updateDateTime).plusMinutes(3).toDate());
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
                        String result = CommonUtils.httpRequest_Get(ceawlUrl).trim();
                        //解析结果数据
                        Document doc = Jsoup.parse(result);
                        TdreamSnProduct product = new TdreamSnProduct();
                        List<String> numberList = new ArrayList<String>();
                        product.setOriginalId(originalId);
                        product.setCrawlFrequency(crawlFrequency);
                        product.setForeverValue(0);
                        product.setProductUrl(ceawlUrl);
                        product.setCurrencySign(Constant.CNY);
                        Elements elements = doc.select(".item-organizer.box img");
                        if(elements.size()>0){
                            product.setPersonName(elements.get(0).attr("alt"));
                            product.setPersonImage(elements.get(0).attr("src"));
                        }
                        elements = doc.select(".item-organizer.box .info");
                        if(elements.size()>0){
                            product.setPersonDesc(elements.get(0).text().trim());
                        }
                        elements = doc.select(".item-detail-title");
                        if(elements.size()>0){
                            product.setProductName(elements.text().trim());
                        }
                        elements = doc.select(".item-detail-intro-pic img");
                        if(elements.size()>0){
                            product.setProductImage(elements.get(0).attr("src"));
                        }
                        elements = doc.select(".item-detail-intro");
                        if(elements.size()>0){
                            product.setProductDesc(elements.text().trim());
                        }
                        elements = doc.select(".item-actor-num strong");
                        if(elements.size()>0){
                            numberList = CommonUtils.getNumberList(elements.get(0).text());
                            if(numberList!=null&&numberList.size()>0){
                                product.setSupportCount(Integer.parseInt(numberList.get(0)));
                            }
                        }

                        elements = doc.select(".item-money.relative .num");
                        if(elements.size()>0){
                            numberList = CommonUtils.getNumberList(elements.get(0).text());
                            if(numberList!=null&&numberList.size()>0){
                                product.setOriginalRasiedAmount(new BigDecimal(numberList.get(0)));
                                product.setRasiedAmount(product.getOriginalRasiedAmount().multiply(Constant.CNY_EXCHANGE_RATE));
                            }
                        }
                        elements = doc.select(".item-target strong");
                        if(elements.size()>0){
                            numberList = CommonUtils.getNumberList(elements.get(0).text());
                            if(numberList!=null&&numberList.size()>0){
                                product.setFinishPercent(Integer.parseInt(numberList.get(0)));
                            }
                        }
                        if(elements.size()>1){
                            numberList = CommonUtils.getNumberList(elements.get(1).text());
                            if(numberList!=null&&numberList.size()>0){
                                product.setOriginalTargetAmount(new BigDecimal(numberList.get(0)));
                                product.setTargetAmount(product.getOriginalTargetAmount().multiply(Constant.CNY_EXCHANGE_RATE));
                            }
                        }
                        elements = doc.select(".follow-box.fr i");
                        if(elements.size()>0){
                            numberList = CommonUtils.getNumberList(elements.get(0).text());
                            if(numberList!=null&&numberList.size()>0){
                                product.setFocusCount(Integer.parseInt(numberList.get(0)));
                            }
                        }
                        elements = doc.select(".item-detail-status");
                        if(elements.size()>0){
                            String status = elements.get(0).text().trim();
                            if(status.contains("中")){
                                product.setProductStatus("众筹中");
                                product.setStatusValue(2);
                                task.setCrawlStatus(1);
                            }else if(status.contains("即将")){
                                product.setProductStatus("预热中");
                                product.setStatusValue(1);
                                task.setCrawlStatus(1);
                            }else if(status.contains("成功")){
                                product.setProductStatus("众筹成功");
                                product.setStatusValue(3);
                                task.setCrawlStatus(2);
                            }else if(status.contains("失败")){
                                product.setProductStatus("众筹失败");
                                product.setStatusValue(4);
                                task.setCrawlStatus(2);
                            }else{
                                if(product.getRasiedAmount()==null||product.getTargetAmount()==null){
                                    product.setProductStatus("众筹异常");
                                    product.setStatusValue(5);
                                    task.setCrawlStatus(3);
                                }else if(product.getRasiedAmount().compareTo(product.getTargetAmount())>=0){
                                    product.setProductStatus("众筹成功");
                                    product.setStatusValue(3);
                                    task.setCrawlStatus(2);
                                }else{
                                    product.setProductStatus("众筹失败");
                                    product.setStatusValue(4);
                                    task.setCrawlStatus(2);
                                }
                            }
                        }else{
                            product.setProductStatus("众筹异常");
                            product.setStatusValue(5);
                            task.setCrawlStatus(3);
                        }
                        elements = doc.select(".item-support-risk dd.box");
                        if(elements.size()>0&&!CommonUtils.isEmpty(elements.get(0).text())){
                            Matcher m = Pattern.compile("([0-9]{4}-[0-9]{2}-[0-9]{2})").matcher(elements.get(0).text());
                            if(m.find()){
                                product.setEndDate(simpleDateFormat.parse(m.group()));
                                if((product.getStatusValue()==3||product.getStatusValue()==4)
                                        &&product.getEndDate().getTime()>System.currentTimeMillis()){
                                    product.getEndDate().setTime(System.currentTimeMillis());
                                }
                            }
                        }
                        elements = doc.select(".item-begin-time");
                        if(elements.size()>0){
                            numberList = CommonUtils.getNumberList(elements.get(0).text());
                            if(numberList!=null&&numberList.size()>0){
                                product.setRemainDay(Integer.parseInt(numberList.get(0)));
                            }
                            String days = elements.get(0).attr("data-days");
                            if(!CommonUtils.isEmpty(days)&&product.getEndDate()!=null){
                                Calendar calendar = Calendar.getInstance();
                                calendar.setTime(product.getEndDate());
                                calendar.add(Calendar.DATE, Integer.parseInt("-"+days));
                                product.setBeginDate(calendar.getTime());
                            }
                        }
                        elements = doc.select(".item-support-level dl");
                        if(elements.size()>0){
                            List<TdreamSnItem> itemList = new ArrayList<TdreamSnItem>();
                            for(Element itemObj :elements){
                                TdreamSnItem item = new TdreamSnItem();
                                Elements nodes = itemObj.select("dt .price");
                                if(nodes.size()>0){
                                    numberList = CommonUtils.getNumberList(nodes.get(0).text());
                                    if(numberList!=null&&numberList.size()>0){
                                        item.setOriginalItemPrice(new BigDecimal(numberList.get(0)));
                                        item.setItemPrice(item.getOriginalItemPrice().multiply(Constant.CNY_EXCHANGE_RATE));
                                    }
                                }
                                nodes = itemObj.select("dt .fr");
                                if(nodes.size()>0){
                                    numberList = CommonUtils.getNumberList(nodes.get(0).text());
                                    if(numberList!=null&&numberList.size()==1){
                                        item.setItemSupport(Integer.parseInt(numberList.get(0)));
                                    }else if(numberList!=null&&numberList.size()>1){
                                        item.setItemTotal(Integer.parseInt(numberList.get(0)));
                                        item.setItemSupport(Integer.parseInt(numberList.get(0))-Integer.parseInt(numberList.get(1)));
                                    }
                                }
                                nodes = itemObj.select(".f14");
                                if(nodes.size()>0){
                                    item.setItemDesc(nodes.get(0).text().trim());
                                }
                                item.setCurrencySign(Constant.CNY);
                                //如果关键字段为空的，直接跳过
                                if(item.getItemSupport()==null) continue;
                                if(item.getItemTotal()==null) continue;
                                if(item.getOriginalItemPrice()==null) continue;
                                item.setUpdateDatetime(updateDateTime);
                                itemList.add(item);
                            }
                            product.setItemList(itemList);
                        }
                        if(product.getRemainDay()==null){
                            if(product.getStatusValue() == 2){
                                int days = (int)Math.ceil(((product.getEndDate().getTime()-System.currentTimeMillis())/Constant.MSEC_DAY));
                                product.setRemainDay(days);
                            }else{
                                product.setRemainDay(0);
                            }
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
        ExecutorService executorService = Executors.newFixedThreadPool(100);
        for (int i = 0; i < 100; i++) {
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
        System.out.println("苏宁抓取项目总共花费时间："+time/1000+"秒");
    }
}
