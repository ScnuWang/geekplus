package cn.geekview;

import cn.geekview.entity.mapper.TdreamUserMapper;
import cn.geekview.entity.model.TdreamUser;
import cn.geekview.service.impl.MailServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MapperTest {

    @Autowired
    private TdreamUserMapper userMapper;

    @Autowired
    private MailServiceImpl mailService;


    @Test
    public void test(){
        TdreamUser user =new TdreamUser();
        user.setEmail("jason@geekview.cn");
        user.setActiveCode("e68f00dd-7bbd-4153-84d1-8d245f1e0cb0");
        System.out.println(userMapper.queryByEmailAndActiveCode(user));
    }

}
