package cn.geekview.entity.mapper.secondary;

import cn.geekview.entity.model.TdreamTbProduct;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TdreamTbProduct_SecondaryMapper {


    List<TdreamTbProduct> queryProductPriceTrend(@Param("crawlFrequence") Integer crawlFrequence, @Param("originalId") String orignalId);
}
