package cn.geekview.geek_analysis;


import cn.geekview.entity.mapper.primary.TdreamTbProduct_PrimaryMapper;
import cn.geekview.entity.mapper.secondary.TdreamTbProduct_SecondaryMapper;
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
    private TdreamTbProduct_PrimaryMapper product_primaryMapper;

    @Autowired
    private TdreamTbProduct_SecondaryMapper product_secondaryMapper;

    @Test
    public void test(){
       List<TdreamTbProduct> productList =  product_primaryMapper.queryProductPriceTrend(60,"20071201");
       System.out.println(productList.toString());
    }

    @Test
    public void test1(){
       List<TdreamTbProduct> productList =  product_secondaryMapper.queryProductPriceTrend(60,"20071201");
       System.out.println(productList.toString());
    }
}
