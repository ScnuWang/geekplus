package cn.geekview.entity.mapper.primary;

import cn.geekview.entity.model.TdreamTbProduct;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface TdreamTbProduct_PrimaryMapper {


    List<TdreamTbProduct> queryProductPriceTrend(@Param("crawlFrequence") Integer crawlFrequence, @Param("originalId") String orignalId);

    /**
     *  查询根据原始编号查询产品
     * @param orignalId 原始编号
     * @return
     */
    TdreamTbProduct queryByOriginalId(@Param("originalId") String orignalId);
}

