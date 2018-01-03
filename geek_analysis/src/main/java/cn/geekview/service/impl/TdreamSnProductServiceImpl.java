package cn.geekview.service.impl;


import cn.geekview.entity.mapper.primary.TdreamSnProduct_PrimaryMapper;
import cn.geekview.entity.mapper.primary.TdreamTbProduct_PrimaryMapper;
import cn.geekview.entity.mapper.secondary.TdreamTbProduct_SecondaryMapper;
import cn.geekview.entity.model.TdreamSnProduct;
import cn.geekview.entity.model.TdreamTbProduct;
import cn.geekview.util.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("TdreamSnProductServiceImpl")
public class TdreamSnProductServiceImpl {

    @Autowired
    private TdreamSnProduct_PrimaryMapper productPrimaryMapper;

    @Autowired
    private TdreamProductServiceImpl productService;

    /**
     * 查询产品记录并插入或修改t_dream_product
     */
    public void insertOrUpdateProduct(){
        //获取平台最新的所有的产品数据
        List<TdreamSnProduct> products = productPrimaryMapper.queryAllProduct();
        for (TdreamSnProduct product : products) {
            productService.insertOrUpdate(Constant.WEBSITE_ID_SUNING,product);
        }
    }








}
