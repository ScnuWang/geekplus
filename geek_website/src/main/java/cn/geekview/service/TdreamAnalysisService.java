package cn.geekview.service;


import cn.geekview.entity.model.TdreamTbProduct;

import java.util.List;

public interface TdreamAnalysisService {

    List<TdreamTbProduct> queryProductPriceTrend(Integer crawlFrequence, String originalId);
}
