package cn.geekview.service.impl;


import cn.geekview.entity.mapper.TdreamTbProductMapper;
import cn.geekview.entity.model.TdreamTbProduct;
import cn.geekview.service.TdreamAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("TdreamTbServiceImpl")
public class TdreamTbServiceImpl implements TdreamAnalysisService {

    @Autowired
    private TdreamTbProductMapper productMapper;

    public List<TdreamTbProduct> queryProductPriceTrend(Integer crawlFrequence, String originalId){
        return productMapper.queryProductPriceTrend(crawlFrequence,originalId);
    }

}
