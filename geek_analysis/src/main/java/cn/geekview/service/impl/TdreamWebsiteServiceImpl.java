package cn.geekview.service.impl;

import cn.geekview.entity.mapper.primary.TdreamProduct_PrimaryMapper;
import cn.geekview.entity.mapper.primary.TdreamTbProduct_PrimaryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *  平台整体分析
 */
@Service("TdreamWebsiteServiceImpl")
public class TdreamWebsiteServiceImpl {


    @Autowired
    private TdreamProduct_PrimaryMapper productPrimaryMapper;

    @Autowired
    private TdreamProductServiceImpl productService;

    @Autowired
    private TdreamTbProduct_PrimaryMapper tbProductPrimaryMapper;

    /**
     * 分析平台关键参数
     */
    public void analysis_website(){

    }

}
