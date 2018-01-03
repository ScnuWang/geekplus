package cn.geekview.entity.mapper.primary;

import cn.geekview.entity.model.TdreamJdProduct;
import cn.geekview.entity.model.TdreamTbProduct;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TdreamJdProduct_PrimaryMapper {


    List<TdreamJdProduct> queryProductPriceTrend(@Param("crawlFrequence") Integer crawlFrequence, @Param("originalId") String orignalId);

    /**
     *  查询根据原始编号查询产品
     * @param orignalId 原始编号
     * @return
     */
    TdreamJdProduct queryByOriginalId(@Param("originalId") String orignalId);

    /**
     * 查询所有产品的最新数据
     */
    List<TdreamJdProduct> queryAllProduct();
}

