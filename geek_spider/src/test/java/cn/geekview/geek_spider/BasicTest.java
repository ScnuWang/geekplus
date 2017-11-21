package cn.geekview.geek_spider;

import org.joda.time.DateTime;
import org.junit.Test;

import java.util.Date;

public class BasicTest {
        @Test
        public void test(){
            java.util.Date juDate = new Date();
            DateTime dt = new DateTime(juDate);
            DateTime dt1 = new DateTime(juDate);
            DateTime dt2 = new DateTime(juDate);
            System.out.println(dt);
            System.out.println(dt1);
            System.out.println(dt2);
        }
}
