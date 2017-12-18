package cn.geekview;

import org.junit.Test;

public class BasicTest {
    @Test
    public void test(){
        String email = "scnu_wang@163.com";
        System.out.println(email.split("@")[0]);
    }
}
