package cn.geekview;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;


public class BasicTest {
    @Test
    public void test(){
        System.out.println(DigestUtils.md5Hex("geekview").equals("09589ca8ca6a4e125ea27a974197256c"));
    }
}
