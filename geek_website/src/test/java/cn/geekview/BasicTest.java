package cn.geekview;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;

@Slf4j
public class BasicTest {
    @Test
    public void test() throws ParseException {
        log.debug("dhfkahsd");
        log.info("dfhaskldhfs");
        log.error("dfhsakfkdl");
        log.warn("fhdsaklfdls");
    }


}
