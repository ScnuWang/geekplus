package cn.geekview.entity.mapper.primary;

import cn.geekview.entity.model.TdreamTbProduct;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TdreamTbProduct_PrimaryMapper {


    List<TdreamTbProduct> queryProductPriceTrend(@Param("crawlFrequence") Integer crawlFrequence, @Param("originalId") String orignalId);
}

