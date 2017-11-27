package cn.geekview.geek_analysis;


import cn.geekview.entity.mapper.TdreamTbProductMapper;
import cn.geekview.entity.model.TdreamTbProduct;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class MapperTest {

    @Autowired
    private TdreamTbProductMapper productMapper;

    @Test
    public void test(){
       List<TdreamTbProduct> productList =  productMapper.queryProductPriceTrend(5,"20070923");
       System.out.println(productList.toString());
    }
}
