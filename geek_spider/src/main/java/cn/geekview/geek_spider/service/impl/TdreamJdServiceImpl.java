package cn.geekview.geek_spider.service.impl;

import cn.geekview.geek_spider.entity.mapper.TdreamJdItemMapper;
import cn.geekview.geek_spider.entity.mapper.TdreamJdProductMapper;
import cn.geekview.geek_spider.entity.mapper.TdreamTaskMapper;
import cn.geekview.geek_spider.entity.model.TdreamJdItem;
import cn.geekview.geek_spider.entity.model.TdreamJdProduct;
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
import java.net.ConnectException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service("TdreamJdServiceImpl")
public class TdreamJdServiceImpl implements TdreamCrawlService{

    protected Logger logger = Logger.getLogger(this.getClass());
    //众筹中的URL
    private static String preCrawlProductlistUrl = "https://z.jd.com/bigger/search.html?status=2&page=";
    //全部项目URL
    private static String preCrawlAllProductlistUrl = "https://z.jd.com/bigger/search.html?page=";

    private static String productUrl = "https://z.jd.com";

    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日");

    @Autowired
    private TdreamTaskMapper taskMapper;

    @Autowired
    private TdreamJdProductMapper productMapper;

    @Autowired
    private TdreamJdItemMapper itemMapper;

    @Autowired
    private TdreamTaskServiceImpl taskService;

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
            Integer total = Integer.valueOf(document.select(".l-statistics.fr strong").text());
            Integer page_num = (int) Math.ceil(total / 16.0);
            List<String> list = new ArrayList<>();
            for (int i = 1; i <= page_num; i++) {
                list.add(preCrawlProductlistUrl + i);
            }
            Runnable runnable = new Runnable() {
                private int order = 1;

                public synchronized String getThreadName() {
                    return "_线程_" + (order++);
                }

                public synchronized String getUrl() {
                    if (list.size() > 0) {
                        return list.remove(0);
                    } else {
                        return null;
                    }
                }

                @Override
                public void run() {
                    String url = null;
                    String result = null;
                    String threadname = getThreadName();
                    while ((url = getUrl()) != null) {
                        result = CommonUtils.httpRequest_Get(url);
                        Document parse = Jsoup.parse(result);
                        Elements projectList = parse.select(".link-pic");
                        for (Element element : projectList) {
                            String link = element.attr("href").trim();
                            if (!CommonUtils.isEmpty(link)) {
                                if (!link.startsWith("http://z.jd.com")) {
                                    link = productUrl + link;
                                }
                                String id = link.substring(link.lastIndexOf("/") + 1, link.lastIndexOf("."));
                                if (!CommonUtils.isEmpty(id)) {
                                    //将待抓取的地址放入任务列表中
                                    TdreamTask task = new TdreamTask();
                                    task.setOriginalId(id);
                                    task.setCrawlFrequency(crawlFrequency);
                                    task.setCrawlStatus(Constant.CRAWL_STATUAS_WAITING);
                                    task.setCrawlUrl(link);
                                    DateTime dateTime = new DateTime(updateDateTime);
                                    task.setCrawlTime(dateTime.toDate());
                                    task.setNextCrawlTime(dateTime.plusMinutes(crawlFrequency).toDate());
                                    task.setWebsiteId(Constant.WEBSITE_ID_JINGDONG);
                                    System.out.println("当前请求地址：" + url + "线程名：" + threadname);
                                    urlMap.put(id, task);
                                }
                            }
                        }
                    }
                }
            };
            ExecutorService executorService = Executors.newFixedThreadPool(100);
            for (int i = 0; i < 100; i++) {
                executorService.execute(runnable);
            }
            executorService.shutdown();
            while (true) {
                if (executorService.isTerminated()) {
                    break;
                }
                Thread.sleep(1000);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
        List<TdreamTask> taskList = taskMapper.queryAllTaskListByWebsiteId(Constant.WEBSITE_ID_JINGDONG);
        for (Map.Entry<String,TdreamTask> entry : urlMap.entrySet()) {
            TdreamTask task = entry.getValue();
            if (!taskList.contains(task)){
                taskMapper.insert(task);
            }
        }
        long time = System.currentTimeMillis()-startTime;
        System.out.println("京东初始化任务总共花费时间："+time/1000+"秒");
    }



    @Override
    public void crawlTask(Date updateDateTime) {
        //预设抓取当前时间前后三分钟内将要被出发的任务
        long startTime = System.currentTimeMillis();
        List<TdreamTask>  taskList = taskMapper.queryTaskListByCrawlInterval(Constant.WEBSITE_ID_JINGDONG,Constant.CRAWL_STATUAS_WAITING,new DateTime(updateDateTime).plusMinutes(-3).toDate(),new DateTime(updateDateTime).plusMinutes(3).toDate());
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
                        TdreamJdProduct product = new TdreamJdProduct();
                        List<String> numberList = new ArrayList<String>();
                        product.setOriginalId(originalId);
                        product.setCrawlFrequency(crawlFrequency);
                        product.setForeverValue(0);
                        product.setProductUrl(ceawlUrl);
                        product.setCurrencySign(Constant.CNY);
                        product.setUpdateDatetime(updateDateTime);
                        Elements elements = doc.select("#projectError");
                        if(elements.size()>0){
                            product.setProductStatus("众筹异常");
                            product.setStatusValue(5);
                        }else{
                            elements = doc.select(".project-img i");
                            if(elements.size()>0){
                                String status = elements.get(0).className();
                                if(status.contains("zc-green-ing")){
                                    product.setProductStatus("众筹中");
                                    product.setStatusValue(2);
                                    task.setCrawlStatus(1);
                                }else if(status.contains("zc-success")||status.contains("xm-success")||status.contains("zc-blue-anke")){
                                    product.setProductStatus("众筹成功");
                                    product.setStatusValue(3);
                                    task.setCrawlStatus(2);
                                }else if(status.contains("xm-gray-fail")||status.contains("zc-sprite")
                                        ||status.contains("zc-gray-fail")||status.contains("zc-orange-refund")){
                                    product.setProductStatus("众筹失败");
                                    product.setStatusValue(4);
                                    task.setCrawlStatus(2);
                                }else if(status.contains("zc-green-infinite")){
                                    product.setProductStatus("永久众筹");
                                    product.setStatusValue(2);
                                    product.setForeverValue(1);
                                    task.setCrawlStatus(1);
                                }else if(status.contains("zc-orange-preheat")||status.contains("zc-gray-preheat-over")){
                                    product.setProductStatus("预热中");
                                    product.setStatusValue(1);
                                    task.setCrawlStatus(1);
                                }else if(status.contains("zc-gray-none")||status.contains("zc-gray-over")){
                                    if(product.getEndDate()==null){
                                        product.setProductStatus("众筹异常");
                                        product.setStatusValue(5);
                                        task.setCrawlStatus(2);
                                    }else{
                                        product.setProductStatus("待定");
                                        product.setStatusValue(0);
                                        task.setCrawlStatus(2);
                                    }
                                }else{
                                    product.setProductStatus(status);
                                    product.setStatusValue(5);
                                    task.setCrawlStatus(3);
                                }
                            }else{
                                product.setProductStatus("众筹异常");
                                product.setStatusValue(5);
                                task.setCrawlStatus(3);
                            }
                        }
                        elements = doc.select(".project-img img");
                        if(elements.size()>0){
                            product.setProductImage("https:"+elements.get(0).attr("src")); ;//图片
                        }else{
                            product.setProductImage(null);
                        }
                        elements = doc.select(".project-introduce .p-title");
                        if(elements.size()>0){
                            product.setProductName(elements.get(0).text().trim()); ;//项目名称
                        }
                        elements = doc.select(".project-introduce .p-num");
                        if(elements.size()>0){
                            numberList = CommonUtils.getNumberList(elements.get(0).text().trim());
                            if(numberList!=null&&numberList.size()>0){
                                product.setOriginalRasiedAmount(new BigDecimal(numberList.get(0)));
                                product.setRasiedAmount(product.getOriginalRasiedAmount().multiply(Constant.CNY_EXCHANGE_RATE));
                            }
                        }
                        elements = doc.select(".project-introduce .fr");
                        if(elements.size()>0){
                            numberList = CommonUtils.getNumberList(elements.get(0).text().trim());
                            if(numberList!=null&&numberList.size()>0){
                                product.setSupportCount(Integer.parseInt(numberList.get(0)));
                            }
                        }
                        elements = doc.select(".project-introduce .fl.percent");
                        if(elements.size()>0){
                            numberList = CommonUtils.getNumberList(elements.get(0).text().trim());
                            if(numberList!=null&&numberList.size()>0){
                                product.setFinishPercent(Integer.parseInt(numberList.get(0)));
                            }
                        }
                        elements = doc.select(".p-target .f_red");//这里如果使用
                        //永久众筹的页面展示不一样
                        if (product.getForeverValue()==1){
                            product.setEndDate(null);
                            product.setOriginalTargetAmount(new BigDecimal(0));
                            product.setTargetAmount(new BigDecimal(0));
                            product.setRemainDay(0);
                        }else {
                            if (elements.size()>0){
                                product.setEndDate(simpleDateFormat.parse(elements.get(0).text().trim()));
                            }
                            if(elements.size()>1){
                                //目标金额
                                numberList = CommonUtils.getNumberList(elements.get(1).text().trim());
                                if(numberList!=null&&numberList.size()>0){
                                    product.setOriginalTargetAmount(new BigDecimal(numberList.get(0)));
                                    product.setTargetAmount(product.getOriginalTargetAmount().multiply(Constant.CNY_EXCHANGE_RATE));
                                }
                            }
                            if(elements.size() > 2 ){
                                String days = elements.get(2).text().trim();
                                if(!CommonUtils.isEmpty(days)){
                                    int remainDay = Integer.parseInt(days);
                                    //剩余天数
                                    product.setRemainDay(remainDay);
                                }
                            }
                        }
                        if(product.getRemainDay()==null&&product.getEndDate()!=null){
                            if(product.getEndDate().getTime() <= System.currentTimeMillis()){
                                product.setRemainDay(0);
                            }else{
                                int days = (int)Math.ceil(((product.getEndDate().getTime()-System.currentTimeMillis())/Constant.MSEC_DAY));
                                product.setRemainDay(days);
                            }
                        }
                        elements = doc.select(".promoters-name a");
                        if(elements.size()>0){
                            product.setPersonName(elements.get(0).attr("title"));
                        }
                        elements = doc.select(".promoters-img img");
                        if(elements.size()>0){
                            product.setPersonImage(elements.get(0).attr("src"));
                        }
                        elements = doc.select(".pjc-name");
                        if(elements.size()>0){
                            String personDesc = elements.get(0).text().trim();
                            product.setPersonDesc(personDesc.length()>255?personDesc.substring(0,255):personDesc);
                        }else{
                            elements = doc.select(".promoters-title");
                            if(elements.size()>0){
                                String personDesc = elements.get(0).text().trim();
                                product.setPersonDesc(personDesc.length()>255?personDesc.substring(0,255):personDesc);
                            }
                        }
                        //子项目
                        Elements divs = doc.select(".box-grade");
                        if(divs.size()>0){
                            List<TdreamJdItem> itemList = new ArrayList<TdreamJdItem>();
                            for(Element itemObj : divs){
                                TdreamJdItem item = new TdreamJdItem();
                                Elements nodes = itemObj.select(".t-price");
                                if(nodes.size()>0){
                                    String price = nodes.get(0).text();
                                    if(price.contains("无私奉献")||price.contains("最新话题")||price.contains("风险说明")
                                            ||price.contains("最近浏览")||price.contains("关于支持与退款"))continue;
                                    numberList = CommonUtils.getNumberList(price);
                                    if(numberList!=null&&numberList.size()>0){
                                        item.setOriginalItemPrice(new BigDecimal(numberList.get(0)));
                                        item.setItemPrice(item.getOriginalItemPrice().multiply(Constant.CNY_EXCHANGE_RATE));
                                    }
                                }
                                nodes = itemObj.select(".t-people");
                                if(nodes.size()>0){
                                    numberList = CommonUtils.getNumberList(nodes.get(0).text());
                                    if(numberList!=null&&numberList.size()>0){
                                        item.setItemSupport(Integer.parseInt(numberList.get(0)));
                                    }
                                }
                                nodes = itemObj.select(".limit-num");
                                if(nodes.size()>0){
                                    numberList = CommonUtils.getNumberList(nodes.get(0).text());
                                    if(numberList!=null&&numberList.size()>0){
                                        item.setItemTotal(Integer.parseInt(numberList.get(0)));
                                    }
                                }
                                nodes = itemObj.select("input");
                                if(nodes.size()>0){
                                    String price = nodes.get(0).val();
                                    numberList = CommonUtils.getNumberList(price);
                                    if(numberList!=null&&numberList.size()>0){
                                        item.setOriginalItemPrice(new BigDecimal(numberList.get(0)));
                                        item.setItemPrice(item.getOriginalItemPrice().multiply(Constant.CNY_EXCHANGE_RATE));
                                    }
                                }
                                nodes = itemObj.select(".box-intro");
                                if(nodes.size()>0){
                                    String desc = nodes.get(0).text().trim();
                                    if(!CommonUtils.isEmpty(desc)){
                                        item.setItemDesc(desc.length()>255?desc.substring(0,255):desc);
                                    }
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
        ExecutorService executorService = Executors.newFixedThreadPool(100);
        for (int i = 0; i < 100; i++) {
            executorService.execute(runnable);
        }
        executorService.shutdown();
        while (true){
            if (executorService.isTerminated()){
                break;
            }
        }
        long time = System.currentTimeMillis()-startTime;
        System.out.println("京东抓取项目总共花费时间："+time/1000+"秒");
        //处理抓取状态是等待抓取，但是下次抓取时间已经过期的任务
        taskService.queryTaskListByCrawlStatus(updateDateTime,Constant.WEBSITE_ID_JINGDONG);
    }
}
