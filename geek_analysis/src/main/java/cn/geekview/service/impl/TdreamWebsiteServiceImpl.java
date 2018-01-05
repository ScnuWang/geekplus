package cn.geekview.service.impl;

import cn.geekview.entity.mapper.primary.TdreamProduct_PrimaryMapper;
import cn.geekview.entity.mapper.primary.TdreamTbProduct_PrimaryMapper;
import cn.geekview.entity.model.TdreamProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *  平台整体分析
 */
@Service("TdreamWebsiteServiceImpl")
public class TdreamWebsiteServiceImpl {

    @Autowired
    private TdreamProductServiceImpl productService;

    /**
     * 分析平台关键参数:
     */
    public void analysis_website(){

    }

}
