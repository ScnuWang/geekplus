package cn.geekview.geek_spider.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 公共工具类
 */
public class CommonUtils {
    public static String getNumber(String str){
        if (StringUtils.isEmpty(str)) return "0";
        String regEx="[^0-9]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return  m.replaceAll("").trim();
    }

    public static boolean isEmpty(String str){
        if(!StringUtils.isEmpty(str)){
            return ("null".equals(str.toLowerCase()));
        }
        return true;
    }

    public static String httpRequest_Get(String url){
        if (url==null){
            return "";
        }
        System.setProperty("https.protocols", "TLSv1.2");
        RestTemplate restTemplate = new RestTemplate();
        //请求头
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json, text/plain, */*");
        headers.set("Accept-Language", "zh-CN,zh;q=0.8");
        headers.set("Cache-Control", "max-age=0");
        headers.set("Connection", "keep-alive");
        headers.set("Host", "www.taobao.com");
        headers.set("If-None-Match", "W/\"749ba3252715ea927aa60ea709d6040f\"");
        headers.set("Upgrade-Insecure-Requests", "1");
        headers.set("Referer","www.baidu.com");
        headers.set("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/47.0.2526.111 Safari/537.36");
        headers.set("Cookie", "xmuuid=XMGUEST-284C0C70-BEDA-11E7-AEA3-9D4FF91D9C75; mstuid=1509522941717_4464; youpindistinct_id=15f80e6192e4b4-06cc67d85512f1-3b3e5906; UM_distinctid=15f9022cee357d-0eab207e5a8eec-3b3e5906-15f900-15f9022cee4791; CNZZDATA1267968936=657502239-1509947936-%7C1512462944; mijiasn=bdss_bdyd37; mjclient=pc; youpin_sessionid=1512468654818_16026282ae24c-03c76cca4ad3d2-3b3e5906; Hm_lvt_f60d40663f1e63b337d026672aca065b=1512464706,1512464721,1512466069,1512468655; Hm_lpvt_f60d40663f1e63b337d026672aca065b=1512468655");
        HttpEntity httpEntity = new HttpEntity(headers);
        ResponseEntity responseEntity = restTemplate.exchange(url, HttpMethod.GET,httpEntity,String.class);
        String result = ((String) responseEntity.getBody()).trim();
        return result;
    }

    private static Pattern numberPattern = Pattern.compile("(\\d+(\\.\\d+)?)");
    public static List<String> getNumberList(String str){
        List<String> list = new ArrayList<String>();
        if(!isEmpty(str)){
            if(str.indexOf(",")>-1){
                str = str.replace(",", "");
            }
            Matcher m = numberPattern.matcher(str);
            while(m.find()){
                list.add(m.group());
            }
        }
        return list;
    }

    public static String httpRequestForXiaoMi(String url,String reqJson){
        // 读取结果网页
        String result = "";
        StringBuffer buffer = new StringBuffer();
        HttpURLConnection hConnect = null;
        try {
            URL newUrl = new URL(url);
            hConnect = (HttpURLConnection) newUrl.openConnection();
            hConnect.setConnectTimeout(30000);
            hConnect.setReadTimeout(30000);
            hConnect.setRequestMethod("POST");// 提交模式
            hConnect.setDoOutput(true);// 是否输入参
            hConnect.setDoInput(true);
            hConnect.setRequestProperty("accept", "application/json");
            hConnect.setRequestProperty("connection", "Keep-Alive");
            hConnect.setRequestProperty("Accept-Encoding", "UTF-8");//这里编码不用浏览器上面的gzip, deflate
            hConnect.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            hConnect.setRequestProperty("Referer", "http://home.mi.com/ishop/main?f=b1&id=5");
            hConnect.setRequestProperty("user-agent", "Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2490.76 Mobile Safari/537.36");

            PrintWriter printWriter = new PrintWriter(hConnect.getOutputStream());
            // 发送请求参数
            printWriter.write(reqJson);
            // flush输出流的缓冲
            printWriter.flush();
            printWriter.close();
            hConnect.connect();
            if(hConnect.getResponseCode() == 200){
                hConnect.getContentType();
                // 读取内容
                BufferedReader rd = new BufferedReader(new InputStreamReader(hConnect.getInputStream(),"UTF-8"));
                String line = "";
                while((line=rd.readLine())!=null){
                    buffer.append(line.trim());
                };
                rd.close();
            }
            result = buffer.toString();
        } catch (Exception e) {
            System.out.println("连接请求报错："+e.getMessage());
            result = "-1";
        }finally{
            if(hConnect!=null){
                hConnect.disconnect();
            }
        }
        return result;
    }
}
