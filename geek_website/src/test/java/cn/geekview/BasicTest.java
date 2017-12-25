package cn.geekview;

import cn.geekview.util.CommonUtils;
import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class BasicTest {
    @Test
    public void test(){
        System.out.println(CommonUtils.checkPassword("abd123"));
    }

    public static boolean checkEmail(String email){
        boolean flag = false;
        try{
            String check = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
            Pattern regex = Pattern.compile(check);
            Matcher matcher = regex.matcher(email);
            flag = matcher.matches();
        }catch(Exception e){
            flag = false;
        }
        return flag;
    }
}
