package cn.geekview.entity.mapper;

import cn.geekview.entity.model.TdreamTbProduct;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TdreamTbProductMapper {


    List<TdreamTbProduct> queryProductPriceTrend(@Param("crawlFrequence") Integer crawlFrequence, @Param("originalId") String orignalId);
}
