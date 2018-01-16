package cn.geekview.geek_analysis;

import cn.geekview.entity.model.TdreamProduct;
import cn.geekview.entity.model.TdreamTbProduct;
import org.joda.time.DateTime;
import org.junit.Test;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

public class BasicTest {

    @Test
    public void test() throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd 12:00:00");
        SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd 12:00:00");
        System.out.println(dateFormat.parse(dateFormat.format(new Date())));
        new DateTime("");

    }
}
