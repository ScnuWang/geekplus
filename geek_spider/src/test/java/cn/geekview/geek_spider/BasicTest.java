package cn.geekview.geek_spider;

import cn.geekview.geek_spider.util.CommonUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.joda.time.DateTime;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BasicTest {
        @Test
        public void test(){
            long n = new DateTime(2017,11,29,11,36,00).toDate().getTime();
            long m = new DateTime(2017,11,28,22,00,00).toDate().getTime();
            System.out.println((n-m)/(120*60*1000)+1);
        }


        @Test
        public void test2() throws InterruptedException {
            long startTime = System.currentTimeMillis();
            String url = "https://hstar-hi.alicdn.com/dream/ajax/getProjectList.htm?projectType=&type=6&sort=1&pageSize=100&page=1";
            String preUrl = "https://hstar-hi.alicdn.com/dream/ajax/getProjectList.htm?projectType=&type=6&sort=1&pageSize=100&page=";
            String result = CommonUtils.httpRequest_Get(url);
            JSONObject jsonObject = (JSONObject) JSONObject.parse(result);
            Integer total = jsonObject.getInteger("total");
            Integer page_num = (int)Math.ceil(total/100.0);
            List<String> list = new ArrayList<>();
            for (int i = 1; i <= page_num; i++) {
                list.add(preUrl+i);
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
                        System.out.println("当前请求地址："+url+"线程名："+threadname+"项目数："+size);
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
            System.out.println(System.currentTimeMillis()-startTime);
        }

        @Test
        public void test3(){
            System.out.println(Math.ceil(35/20));//1.0
            System.out.println(Math.ceil(35.0/20));//2.0
            System.out.println(Math.ceil(35/20.0));//2.0
        }
}
