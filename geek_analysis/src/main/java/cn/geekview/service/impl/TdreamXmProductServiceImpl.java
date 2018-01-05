package cn.geekview.service.impl;


import cn.geekview.entity.mapper.primary.TdreamXmProduct_PrimaryMapper;
import cn.geekview.entity.model.TdreamXmProduct;
import cn.geekview.util.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("TdreamXmProductServiceImpl")
public class TdreamXmProductServiceImpl {

    @Autowired
    private TdreamXmProduct_PrimaryMapper productPrimaryMapper;

    @Autowired
    private TdreamProductServiceImpl productService;

    /**
     * 查询产品记录并插入或修改t_dream_product
     */
    public void analysis(){
        //获取平台最新的所有的产品数据
        List<TdreamXmProduct> products = productPrimaryMapper.queryAllProduct();
        for (TdreamXmProduct product : products) {
            productService.insertOrUpdate(Constant.WEBSITE_ID_XIAOMI,product);
        }
    }








}
