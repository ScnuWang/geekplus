package cn.geekview;

import cn.geekview.util.CommonUtils;
import org.joda.time.DateTime;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class BasicTest {
    @Test
    public void test() throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd 12:00:00");
        System.out.println(new DateTime(dateFormat.format(new Date())));
    }


}
