package cn.geekview.geek_spider;

import cn.geekview.geek_spider.entity.model.TdreamJdItem;
import cn.geekview.geek_spider.util.CommonUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.joda.time.DateTime;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BasicTest {
        @Test
        public void test(){
            long n = new DateTime(2017,11,28,11,36,00).toDate().getTime();
            long m = new DateTime(2017,11,28,10,00,00).toDate().getTime();
            System.out.println((int)Math.ceil((n-m)/(120*60*1000.0))+1);
        }
        @Test
        public void test2() throws InterruptedException, ParseException {
            String result = CommonUtils.httpRequest_Get("https://zc.suning.com/project/detail.htm?projectId=17607");
            Document document = Jsoup.parse(result);
            //获取总的项目数
            Elements elements = document.select(".item-organizer.box img");
            System.out.println(elements);
        }

        @Test
        public void test3(){
            System.out.println(Math.ceil(35/20));//1.0
            System.out.println(Math.ceil(35.0/20));//2.0
            System.out.println(Math.ceil(35/20.0));//2.0
        }
}
