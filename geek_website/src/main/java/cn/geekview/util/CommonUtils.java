package cn.geekview.util;

import org.apache.commons.lang3.StringUtils;
import org.lionsoul.jcseg.util.StringUtil;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

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
        headers.set("Cookie", "ki_t=1482987544992%3B1487728858942%3B1487729104639%3B4%3B16; ki_r=; D_SID=113.104.195.55:YisvIcHF4Z24s3TB2TKbitxWEwrmGlyymljvxklDswE; __ar_v4=MNA52ZMPS5A5HHGLKMZP3O%3A20170602%3A3%7CZIENZZYANBHC5MQ4WYNKZI%3A20170602%3A3%7C6RP73TXU3VCT7KLJC3P7EZ%3A20170602%3A3; __stripe_mid=35ce441a-7019-4221-b482-b0cba67f7fd4; romref=sch-baid; romref_referer_host=www.baidu.com; D_IID=02B91A75-51CD-3F94-A882-07B72A5961C2; D_UID=8506F25A-0F30-31AC-81E7-2864983C64A4; D_ZID=FDDDFB6A-67FA-340D-9C89-564B5A0B9899; D_ZUID=5D0201DE-2FF3-3A22-AD4E-8F28FE310CF0; D_HID=2FCF09D9-15AF-3626-BC88-5C189ABE314C; _ga=GA1.2.378922805.1482987529; _gid=GA1.2.772188293.1496800270; _ceg.s=or7ggq; _ceg.u=or7ggq; __hstc=223492548.37066d182471c31df2df71226efcf1aa.1482987568028.1496806912327.1496885823487.12; __hssrc=1; __hssc=223492548.1.1496885823487; hubspotutk=37066d182471c31df2df71226efcf1aa; locale=en; cohort=www.baidu.com%7Csch-baid%7Cshr-pica%7Csch-baid%7Cshr-pica%7Csch-baid; visitor_id=bba58762b2348bf353961ae7353f1e7ab5ff697484ba0a55993771e32402d354; analytics_session_id=2cab9c7316a54d91e73058b25c9e12748d7b3fda0ea06d63e02fb536b212e3d6; recent_project_ids=2001745%262132365%262115568%262107867%261905844%261637253%262115621%261931378%262063864%261787929%262024430%261978687%261993728%262016897%262023232%261994449%262017684%262022194%261319420%261625355; _session_id=742dc34425f25f0f10f5962f23becc4f");
        HttpEntity httpEntity = new HttpEntity(headers);
        ResponseEntity responseEntity = restTemplate.exchange(url, HttpMethod.GET,httpEntity,String.class);
        String result = ((String) responseEntity.getBody()).trim();
        return result;
    }


    public static boolean isMailAddress(String str){
        int atIndex = str.indexOf('@');
        if ( atIndex == -1 ) {
            return false;
        }

        if ( ! StringUtil.isLetterOrNumeric(str, 0, atIndex) ) {
            return false;
        }

        int ptIndex, ptStart = atIndex + 1;
        while ( (ptIndex = str.indexOf('.', ptStart)) > 0 ) {
            if ( ptIndex == ptStart ) {
                return false;
            }

            if ( ! StringUtil.isLetterOrNumeric(str, ptStart, ptIndex) ) {
                return false;
            }

            ptStart = ptIndex + 1;
        }

        if ( ptStart < str.length()
                && ! StringUtil.isLetterOrNumeric(str, ptStart, str.length()) ) {
            return false;
        }

        return true;
    }
}
